package com.sebas.bayes.factor;

import com.sebas.bayes.variables.DiscreteVariable;

import java.math.BigDecimal;
import java.util.Set;

public class CPTFactoryOLD implements FactorFactoryOLD {

	@Override
	public FactorOLD factorWithVariables(DiscreteVariable<?> mainVariable,
										 Set<DiscreteVariable<?>> conditionalVariables) {
		return new CPT(mainVariable, conditionalVariables,
				Distribution.createDistribution());
	}

	@Override
	public FactorOLD factorWithVariables(DiscreteVariable<?> mainVariable,
										 Set<DiscreteVariable<?>> conditionalVariables,
										 Distribution distribution) {
		return new CPT(mainVariable, conditionalVariables, distribution);
	}

	@Override
	public FactorOLD factorWithVariable(DiscreteVariable<?> mainVariable) {
		return new CPT(mainVariable, null, Distribution.createDistribution());
	}

	@Override
	public FactorOLD factorWithVariable(DiscreteVariable<?> mainVariable,
										BigDecimal probability) {
		return new CPT(mainVariable, null, Distribution.createDistribution(
				probability, BigDecimal.ONE.subtract(probability)));
	}

}
