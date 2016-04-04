package com.sebas.bayes.variables;

import com.google.common.base.Strings;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDiscreteVariable<T> implements DiscreteVariable<T> {

	protected final long id;
	protected final String label;

	protected AbstractDiscreteVariable(final long id, final String label) {
		super();
		this.id = id;
		this.label = Strings.nullToEmpty(label);
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public int dimension() {
		return this.getStates().size();
	}

	@Override
	public VariableInstantiation<T> instantiate(final T state) {
		return VariableInstantiation.instantiate(this, state);
	}

	@Override
	public Set<VariableInstantiation<?>> variableInstantiations() {
		return this.getStates().stream().map(state -> VariableInstantiation.instantiate
				(this, state)).collect(Collectors.toSet());
	}

	@Override
	public boolean validState(final T state) {
		return this.getStates().contains(state);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final AbstractDiscreteVariable<?> that = (AbstractDiscreteVariable<?>) o;

		if (id != that.id)
			return false;
		return label.equals(that.label);

	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + label.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "DiscreteVariable{" +
				"id=" + id +
				", label='" + label + '\'' +
				'}';
	}
}
