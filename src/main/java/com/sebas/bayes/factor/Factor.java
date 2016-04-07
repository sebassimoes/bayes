package com.sebas.bayes.factor;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.sebas.bayes.variables.DiscreteVariable;
import com.sebas.bayes.variables.VariableInstantiationSet;
import com.sebas.bayes.variables.VariableInstantiationSets;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Factor function which assigns a non-negative value to each combination of variables instantiation.
 */
public final class Factor {

	private final String label;
	private final Set<DiscreteVariable<?>> vars;
	private final List<FactorRow> rows;

	private Factor(final String label, final Set<DiscreteVariable<?>> vars, final List<FactorRow> rows) {
		super();
		this.label = label;
		this.vars = vars;
		this.rows = rows;
	}

	/**
	 * Factor multiplication.
	 *
	 * @param factor the factor to multiply
	 * @return the resulting factor
	 */
	public Factor multiply(final Factor factor) {
		if (factor == null) {
			// TODO change this! -> a new instance should be returned.
			return this;
		}
		final Set<DiscreteVariable<?>> newVariableSet = Sets.union(this.vars, factor.vars());
		final FactorBuilder builder = new FactorBuilder(String.format("(%s.%s)", this.getLabel(), factor.getLabel()),
				newVariableSet);
		final Collection<VariableInstantiationSet> allVariablesInstantiations = VariableInstantiationSets
				.cartesianProduct(newVariableSet);

		for (final VariableInstantiationSet inst : allVariablesInstantiations) {
			builder.addRow(inst, this.getInstantiationValue(inst).multiply(factor.getInstantiationValue(inst)));
		}
		return builder.getFactor();
	}

	/**
	 * Sum out a given set of variables from the factor.
	 *
	 * @param variables the variables to sum out from the factor
	 * @return the resulting factor
	 */
	public Factor summingOut(final Set<DiscreteVariable> variables) {
		final Set<DiscreteVariable<?>> newVariableSet = Sets.difference(this.vars, variables);
		final String setString = variables.stream().reduce("", (aggr, discreteVariable2) -> aggr.concat
				(discreteVariable2.getLabel()), (s, s2) -> s.concat(s2));
		final String newFactorLabel = String.format("(%s\\{%s})", this.getLabel(), setString);
		final FactorBuilder builder = new FactorBuilder(newFactorLabel, newVariableSet);

		// sum out the variables and add the new rows to the builder.
		final Collection<VariableInstantiationSet> allVariablesInstantiations = VariableInstantiationSets
				.cartesianProduct(newVariableSet);
		for (final VariableInstantiationSet variablesInstantiation : allVariablesInstantiations) {
			final BigDecimal factorRowsSum = this.rows.parallelStream().filter(row -> row.getInstantiation().contains
					(variablesInstantiation)).reduce(BigDecimal.ZERO, (aggr, row) -> row.getValue().add(aggr), (a, b)
					-> a.add(b));
			builder.addRow(variablesInstantiation, factorRowsSum);
		}
		return builder.getFactor();
	}

	/**
	 * Get the factor label.
	 *
	 * @return the factor label
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Get the factor set of variables.
	 *
	 * @return the set of variables
	 */
	public Set<DiscreteVariable<?>> vars() {
		return this.vars;
	}

	public boolean containsVar(final DiscreteVariable<?> variable) {
		return this.vars().contains(variable);
	}

	/**
	 * Get the list of factor rows. Each row has a distinct instantiation of the factor variables and the
	 * corresponding factor value.
	 *
	 * @return the list of factor rows
	 */
	public List<FactorRow> getRows() {
		return rows;
	}

	/**
	 * Get the factor value given an instantiation.
	 *
	 * @param instantiation the variables instantiation for which to get the factor value
	 * @return the factor value
	 */
	public BigDecimal getInstantiationValue(final VariableInstantiationSet instantiation) {
		final Optional<FactorRow> optionalRow = this.rows.stream().filter(factorRow -> instantiation.contains
				(factorRow.getInstantiation())).findFirst();
		return optionalRow.isPresent() ? optionalRow.get().getValue() : null;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("\n");
		this.vars.stream().sorted((o1, o2) -> o1.getLabel().compareTo(o2.getLabel())).forEach(discreteVariable ->
				builder.append("|").append(StringUtils.center(discreteVariable.getLabel(), 10)));
		builder.append("|").append(StringUtils.center(this.getLabel(), 10)).append("|");
		this.rows.forEach(factorRow -> builder.append("\n").append(factorRow.toString()));
		return builder.toString();
	}

	/**
	 * Builder for creation of {@link Factor} instances.
	 */
	public static class FactorBuilder {

		private String label;
		private Set<DiscreteVariable<?>> factorVariables;
		private List<FactorRow> factorRowsList;

		/**
		 * Create a new builder instance with the factor label and the set of variables belonging to the factor.
		 *
		 * @param label           the factor label
		 * @param factorVariables the factor variables
		 */
		public FactorBuilder(final String label, final Set<DiscreteVariable<?>> factorVariables) {
			super();
			this.label = Strings.nullToEmpty(label);
			this.factorVariables = factorVariables != null ? factorVariables : Sets.newHashSet();
			this.factorRowsList = new ArrayList<>();
		}

		/**
		 * Add a variable instantiation and the corresponding factor value to the builder. Note that some validations
		 * are performed in order to guarantee the factor invariants.
		 * <p><code>chainable</code></p>
		 *
		 * @param instantiation the variables instantiation to be added to the factor
		 * @param value         the instantiation factor value
		 * @return the builder
		 */
		public FactorBuilder addRow(final VariableInstantiationSet instantiation, final BigDecimal value) {
			if (instantiation == null || !this.isExpectedVariableSet(instantiation)) {
				throw new InconsistentInstantiationVariablesException(this.factorVariables);
			}
			if (!this.isInstantiationUnique(instantiation)) {
				throw new DuplicatedInstantiationException(instantiation);
			}
			this.factorRowsList.add(FactorRow.createFactorRow(instantiation, value));
			return this;
		}

		/**
		 * Build the {@link Factor} instance. Note that validations are performed in order to preserve the factor
		 * invariants.
		 *
		 * @return the new factor instance
		 */
		public Factor getFactor() {
			final int expectedSize = this.size();
			final int actualSize = this.factorRowsList.size();
			if (expectedSize != actualSize) {
				throw new InvalidFactorTableSizeException(expectedSize, actualSize);
			}
			return new Factor(this.label, this.factorVariables, this.factorRowsList);
		}

		private boolean isExpectedVariableSet(final VariableInstantiationSet instantiation) {
			return this.factorVariables.equals(instantiation.stream().map(variableInstantitation ->
					variableInstantitation.getVariable()).collect(Collectors.toSet()));
		}

		private boolean isInstantiationUnique(final VariableInstantiationSet instantiation) {
			return !this.factorRowsList.stream().anyMatch(factorRow -> factorRow.getInstantiation().equals
					(instantiation));
		}

		private Integer size() {
			return this.factorVariables.isEmpty() ? 0 : this.factorVariables.parallelStream().reduce(1, (aggr, v) ->
					aggr *= v.dimension(), (v1, v2) -> v1 * v2);
		}

	}

}
