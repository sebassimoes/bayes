package com.sebas.bayes.factor;

import com.sebas.bayes.variables.DiscreteVariable;

import java.util.Set;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 26/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public class InconsistentInstantiationVariablesException extends RuntimeException {
	public InconsistentInstantiationVariablesException(final Set<DiscreteVariable<?>> factorVariables) {
		super(String.format("The given instantiation variables set is invalid for this factor. The set must contain " +
				"exactly the following variables: %s", factorVariables));
	}
}
