package com.sebas.bayes.factor;

import com.sebas.bayes.variables.VariableInstantiationSet;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 26/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public class DuplicatedInstantiationException extends RuntimeException {
	public DuplicatedInstantiationException(final VariableInstantiationSet instantiation) {
		super(String.format("The factor already contains the instantiation %s.", instantiation));
	}
}
