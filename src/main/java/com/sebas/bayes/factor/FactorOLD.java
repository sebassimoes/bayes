package com.sebas.bayes.factor;

import com.sebas.bayes.variables.DiscreteVariable;

import java.util.Set;

public abstract class FactorOLD {

	protected DiscreteVariable<?> mainVariable;
	protected Set<DiscreteVariable<?>> distributionVariables;
	protected Distribution distribution;
	
	protected FactorOLD(DiscreteVariable<?> variable, Set<DiscreteVariable<?>> variables, Distribution distribution) {
		super();
		this.mainVariable = variable;
		this.distributionVariables = variables;
		this.distribution = distribution;
	}
	
	public boolean containsVariable(DiscreteVariable<?> variable) {
		boolean result = variable.equals(this.mainVariable);
		if (!result && this.distributionVariables != null) {
			result = this.distributionVariables.contains(variable);
		}
		return result;
	}
	
	public DiscreteVariable<?> mainVariable() {
		return this.mainVariable;
	}
	
	abstract public void distribution(Distribution distribution);
}
