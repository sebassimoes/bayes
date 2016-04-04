package com.sebas.bayes.variables;

import com.google.common.collect.Sets;
import com.sebas.bayes.variables.BooleanVariable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 25/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public class BooleanVariableTest {

	private static BooleanVariable variableA;
	private static BooleanVariable variableB;
	private static BooleanVariable variableWithoutLabel;

	@Before
	public void beforeTest() {
		variableA = new BooleanVariable(1, "A");
		variableB = new BooleanVariable(2, "B");
		variableWithoutLabel = new BooleanVariable(3, null);
	}

	@Test
	public void getStates() throws Exception {
		final Set<?> states = variableA.getStates();
		Assert.assertEquals(Sets.newHashSet(true, false), states);
	}

	@Test
	public void getId() throws Exception {
		Assert.assertEquals(1, variableA.getId());
		Assert.assertEquals(2, variableB.getId());
		Assert.assertEquals(3, variableWithoutLabel.getId());
	}

	@Test
	public void getLabel() throws Exception {
		Assert.assertEquals("A", variableA.getLabel());
		Assert.assertEquals("B", variableB.getLabel());
		Assert.assertEquals("", variableWithoutLabel.getLabel());
	}

	@Test
	public void dimension() throws Exception {
		Assert.assertEquals("boolean variable expected to have dimanesion 2", 2, variableA.dimension());
		Assert.assertEquals("boolean variable expected to have dimanesion 2", 2, variableB.dimension());
	}
}