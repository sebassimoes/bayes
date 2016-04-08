package com.sebas.bayes.inference.variableelimination;

import com.google.common.collect.Sets;
import com.sebas.bayes.factor.Factor;
import com.sebas.bayes.variables.BooleanVariable;
import com.sebas.bayes.variables.VariableInstantiationSets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

public class VePr1Test {

	static BooleanVariable varA = new BooleanVariable(1, "A");
	static BooleanVariable varB = new BooleanVariable(2, "B");
	static BooleanVariable varC = new BooleanVariable(3, "C");
	static Factor factorA, factorB, factorC;
	static Set<Factor> factors;

	@Before
	public void setup() {
		factorA = new Factor.FactorBuilder("A", Sets.newHashSet(varA)).addRow(VariableInstantiationSets
				.newVariableInstantiationSet(varA.instantiate(true)), BigDecimal.valueOf(0.6)).addRow
				(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate(false)), BigDecimal.valueOf(0.4)).getFactor();

		factorB = new Factor.FactorBuilder("B", Sets.newHashSet(varA, varB)).addRow(VariableInstantiationSets
				.newVariableInstantiationSet(varA.instantiate(true), varB.instantiate(true)), BigDecimal.valueOf(0.9))
				.addRow(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate(true), varB.instantiate
						(false)), BigDecimal.valueOf(0.1)).addRow(VariableInstantiationSets
						.newVariableInstantiationSet(varA.instantiate(false), varB.instantiate(true)), BigDecimal
						.valueOf(0.2)).addRow(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate
						(false), varB.instantiate(false)), BigDecimal.valueOf(0.8)).getFactor();

		factorC = new Factor.FactorBuilder("C", Sets.newHashSet(varB, varC)).addRow(VariableInstantiationSets
				.newVariableInstantiationSet(varB.instantiate(true), varC.instantiate(true)), BigDecimal.valueOf(0.3))
				.addRow(VariableInstantiationSets.newVariableInstantiationSet(varB.instantiate(true), varC.instantiate
						(false)), BigDecimal.valueOf(0.7)).addRow(VariableInstantiationSets
						.newVariableInstantiationSet(varB.instantiate(false), varC.instantiate(true)), BigDecimal
						.valueOf(0.5)).addRow(VariableInstantiationSets.newVariableInstantiationSet(varB.instantiate
						(false), varC.instantiate(false)), BigDecimal.valueOf(0.5)).getFactor();

		factors = Sets.newHashSet(factorA, factorB, factorC);
	}

	@Test
	public void run() throws Exception {
		Factor result = new VePr1().run(factors, varC, Arrays.asList(varB, varA));

		System.out.println(result);

		Assert.assertTrue(result.vars().size() == 1);
		Assert.assertEquals(result.vars(), Sets.newHashSet(varC));
		Assert.assertEquals(result.getInstantiationValue(VariableInstantiationSets.
				newVariableInstantiationSet(varC.instantiate(true))), BigDecimal.valueOf(0.376));
	}

	@Test
	public void run2() throws Exception {
		Factor result = new VePr1().run(factors, varC, Arrays.asList(varA, varB));

		System.out.println(result);

		Assert.assertTrue(result.vars().size() == 1);
		Assert.assertEquals(result.vars(), Sets.newHashSet(varC));
		Assert.assertEquals(result.getInstantiationValue(VariableInstantiationSets.
				newVariableInstantiationSet(varC.instantiate(true))), BigDecimal.valueOf(0.376));
	}

	@Test
	public void run3() {
		Factor priorA = new VePr1().run(factors, varA, Arrays.asList(varC, varB));
		Factor priorB = new VePr1().run(factors, varB, Arrays.asList(varA, varC));
		Factor priorC = new VePr1().run(factors, varC, Arrays.asList(varB, varA));

		System.out.println(priorA);
		System.out.println(priorB);
		System.out.println(priorC);
	}
}