package com.sebas.bayes.variables;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 26/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public final class VariableInstantiation<T> {

	private final DiscreteVariable<T> variable;
	private final T value;

	private VariableInstantiation(DiscreteVariable variable, T value) {
		this.variable = variable;
		this.value = value;
	}

	public static <T> VariableInstantiation<T> instantiate(DiscreteVariable<T> variable, T value) {
		// validate value //
		if (!variable.validState(value)) {
			throw new InvalidVariableInstantiationException(variable, value);
		}
		return new VariableInstantiation<>(variable, value);
	}

	public DiscreteVariable getVariable() {
		return this.variable;
	}

	public T getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return "VariableInstantiation{" +
				"variable=" + variable +
				", value=" + value +
				'}';
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final VariableInstantiation<?> that = (VariableInstantiation<?>) o;

		if (variable != null ? !variable.equals(that.variable) : that.variable != null)
			return false;
		return value != null ? value.equals(that.value) : that.value == null;

	}

	@Override
	public int hashCode() {
		int result = variable != null ? variable.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
