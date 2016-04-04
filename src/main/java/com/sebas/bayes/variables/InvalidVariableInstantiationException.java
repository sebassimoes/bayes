package com.sebas.bayes.variables;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 26/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public class InvalidVariableInstantiationException extends RuntimeException {

	public InvalidVariableInstantiationException(final DiscreteVariable variable, final Object state) {
		super(String.format("The state %s is invalid for the variable {}", state, variable.getLabel()));
	}

}
