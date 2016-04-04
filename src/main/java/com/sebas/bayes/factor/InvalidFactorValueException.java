package com.sebas.bayes.factor;

import java.math.BigDecimal;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 26/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public class InvalidFactorValueException extends RuntimeException {

	public InvalidFactorValueException(final BigDecimal value) {
		super(String.format("Factor value must be greater or equal than 0. %s was suplied.", value));
	}

}
