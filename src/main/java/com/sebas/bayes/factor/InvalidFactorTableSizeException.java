package com.sebas.bayes.factor;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 26/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public class InvalidFactorTableSizeException extends RuntimeException {
	public InvalidFactorTableSizeException(final int expectedSize, final int actualSize) {
		super(String.format("The factor table has %d rows when %d were expected.", actualSize, expectedSize));
	}
}
