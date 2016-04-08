package com.sebas.bayes;

import com.sebas.bayes.factor.Factor;
import com.sebas.bayes.variables.DiscreteVariable;

public class BayesianNode {

	private final DiscreteVariable<?> variable;
	private final Factor cptFactor;

	public BayesianNode(final DiscreteVariable<?> variable, final Factor cptFactor) {
		this.variable = variable;
		this.cptFactor = cptFactor;
	}

	public DiscreteVariable<?> getVariable() {
		return variable;
	}

	public Factor getCptFactor() {
		return cptFactor;
	}

	@Override
	public String toString() {
		return "BayesianNode{" +
				"variable=" + variable +
				'}';
	}
}
