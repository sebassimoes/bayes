package com.sebas.bayes.variables;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 26/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public class VariableInstantiationTest {

	private static BooleanVariable booleanVariable;
	private static DynamicVariable dynamicVariable;

	@Before
	public void before() {
		booleanVariable = new BooleanVariable(1, "BOOL");
		dynamicVariable = new DynamicVariable(2, "Dyn", Sets.newHashSet("A", "B", "C"));
	}

	@Test
	public void instantiateBooleanWithTrueState() {
		VariableInstantiation<Boolean> instantiation = booleanVariable.instantiate(true);
		Assert.assertNotNull(instantiation);
		Assert.assertEquals("BOOL", instantiation.getVariable().getLabel());
		Assert.assertTrue(instantiation.getValue());
	}

	@Test
	public void instantiateBooleanWithFalseState() {
		VariableInstantiation<Boolean> instantiation = booleanVariable.instantiate(false);
		Assert.assertNotNull(instantiation);
		Assert.assertEquals("BOOL", instantiation.getVariable().getLabel());
		Assert.assertFalse(instantiation.getValue());
	}

	@Test(expected = InvalidVariableInstantiationException.class)
	public void instantiateBooleanWithNull() {
		VariableInstantiation<Boolean> instantiation = booleanVariable.instantiate(null);
	}

	@Test
	public void instantiateDynamicWithValidState() {
		VariableInstantiation<String> instantiation = dynamicVariable.instantiate("A");
		Assert.assertEquals("A", instantiation.getValue());
		instantiation = dynamicVariable.instantiate("B");
		Assert.assertEquals("B", instantiation.getValue());
	}

	@Test(expected = InvalidVariableInstantiationException.class)
	public void instantiateDynamicWithInvalidState() {
		VariableInstantiation<String> instantiation = dynamicVariable.instantiate("I");
	}
}