package com.sebas.bayes.factor;

import com.sebas.bayes.variables.DiscreteVariable;

import java.math.BigDecimal;
import java.util.Set;

public interface FactorFactoryOLD {

	FactorOLD factorWithVariables(DiscreteVariable<?> mainVariable,
								  Set<DiscreteVariable<?>> conditionalVariables);

	FactorOLD factorWithVariables(DiscreteVariable<?> mainVariable,
								  Set<DiscreteVariable<?>> conditionalVariables, Distribution distribution);

	FactorOLD factorWithVariable(DiscreteVariable<?> mainVariable);

	FactorOLD factorWithVariable(DiscreteVariable<?> mainVariable,
								 BigDecimal probability);
}
