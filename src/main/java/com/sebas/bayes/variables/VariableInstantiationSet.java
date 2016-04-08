package com.sebas.bayes.variables;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An
 */
public final class VariableInstantiationSet implements Iterable {

	private Set<VariableInstantiation<?>> variableInstantiations;

	public VariableInstantiationSet(final Collection<VariableInstantiation<?>> variableInstantiations) {
		super();
		if (variableInstantiations == null && variableInstantiations.isEmpty()) {
			throw new IllegalArgumentException("The variable instantiation set can not be null.");
		}
		this.variableInstantiations = new HashSet<>(variableInstantiations);
	}

	public VariableInstantiationSet addVariableInstantiation(final VariableInstantiation<?> variableInstantiation) {
		this.variableInstantiations.add(variableInstantiation);
		return new VariableInstantiationSet(this.variableInstantiations);
	}

	public VariableInstantiationSet removeVariable(final DiscreteVariable<?> variable) {
		return this.removeVariables(Arrays.asList(variable));
	}

	public VariableInstantiationSet removeVariables(final Collection<DiscreteVariable<?>> variables) {
		return new VariableInstantiationSet(this.stream().filter(i -> !variables.contains(i.getVariable())).collect
				(Collectors.toSet()));
	}

	/**
	 * Check if the given variable instantiation set is contained on this variable instantiation set.
	 * <p>
	 *     <pre>
	 *     Example: S = {A = 1, B = 0, C = 1}
	 *     {A = 1, B = 0} is contained on S
	 *     {A = 1} is contained on S
	 *	   {A = 0} is not contained on S
	 *	   {B = 0, C = 1} is contained on S
	 *	   {A = 1, B = 0, C = 1} is contained on S
	 *	   {A = 1, B = 0, C = 1, D = 0} is not contained on S
	 *     </pre>
	 *
	 * </p>
	 * @param variableInstantiationSet the set of variable instantiations to test
	 * @return <code>true</code> if the given set of variable instantiations is contained on this instance
	 */
	public boolean contains(final VariableInstantiationSet variableInstantiationSet) {
		return this.variableInstantiations.containsAll(variableInstantiationSet.variableInstantiations);
	}

	public boolean containsVariables(final Collection<DiscreteVariable<?>> variables) {
		return this.stream().map(i -> i.getVariable()).collect(Collectors.toSet()).containsAll(variables);
	}

	public boolean containsVariable(final DiscreteVariable<?> variable) {
		return this.containsVariables(Arrays.asList(variable));
	}

	public Stream<VariableInstantiation<?>> stream() {
		return this.variableInstantiations.stream();
	}

	@Override
	public Iterator iterator() {
		return this.variableInstantiations.iterator();
	}

	@Override
	public void forEach(final Consumer action) {
		this.variableInstantiations.forEach(action);
	}

	@Override
	public Spliterator spliterator() {
		return variableInstantiations.spliterator();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("Variable Instantiation: [");
		boolean first = true;
		for (final VariableInstantiation<?> variableInstantiation : this.variableInstantiations) {
			if (!first) {
				builder.append(",");
			}
			builder.append(variableInstantiation);
		}
		return builder.append("]").toString();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final VariableInstantiationSet that = (VariableInstantiationSet) o;

		return variableInstantiations != null ? variableInstantiations.equals(that.variableInstantiations) : that
				.variableInstantiations == null;

	}

	@Override
	public int hashCode() {
		return variableInstantiations != null ? variableInstantiations.hashCode() : 0;
	}
}
