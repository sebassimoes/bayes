package com.sebas.bayes.factor;

import com.sebas.bayes.variables.DiscreteVariable;

import java.util.Set;

public class CPT extends FactorOLD {

	protected CPT(DiscreteVariable<?> variable,
			Set<DiscreteVariable<?>> variables, Distribution distribution) {
		super(variable, variables, distribution);
	}

	@Override
	public void distribution(Distribution distribution) {

	}

	private int distributionSize() {
		int result = this.mainVariable.dimension();
		if (this.distributionVariables != null) {
			result *= this.distributionVariables.parallelStream().reduce(1,
					(aggr, v) -> aggr *= v.dimension(), (v1, v2) -> v1 * v2);
		}
		return result;
	}

	private boolean isValidDistribution(Distribution distribution) {
		return this.distributionSize() == distribution.length();
	}

}
