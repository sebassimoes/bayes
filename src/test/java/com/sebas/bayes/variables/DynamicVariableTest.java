package com.sebas.bayes.variables;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 26/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public class DynamicVariableTest {

	private static DynamicVariable variableWithABCState;

	@Before
	public void before() {
		final Set<String> states = Sets.newHashSet("A", "B", "C");
		variableWithABCState = new DynamicVariable(1, "ABC", states);
	}

	@Test
	public void getStates() {
		Assert.assertEquals(Sets.newHashSet("A", "B", "C"), variableWithABCState.getStates());
		Assert.assertEquals(3, variableWithABCState.dimension());
	}

	@Test
	public void validState() {
		Assert.assertTrue(variableWithABCState.validState("A"));
		Assert.assertTrue(variableWithABCState.validState("B"));
		Assert.assertTrue(variableWithABCState.validState("C"));
		Assert.assertFalse(variableWithABCState.validState("D"));
	}

}