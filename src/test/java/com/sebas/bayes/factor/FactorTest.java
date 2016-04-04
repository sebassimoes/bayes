package com.sebas.bayes.factor;

import com.google.common.collect.Sets;
import com.sebas.bayes.variables.BooleanVariable;
import com.sebas.bayes.variables.DynamicVariable;
import com.sebas.bayes.variables.VariableInstantiationSets;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 28/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public class FactorTest {

	private static BooleanVariable varA;
	private static DynamicVariable varB;
	private static BooleanVariable varC;

	private static Factor factorA;
	private static Factor factorC;

	@Before
	public void setUp() {
		varA = new BooleanVariable(1, "A");
		varB = new DynamicVariable(2, "B", Sets.newHashSet("M", "F"));
		varC = new BooleanVariable(3, "C");

		factorA = new Factor.FactorBuilder("F(B)", Sets.newHashSet(varA, varB)).addRow(VariableInstantiationSets
				.newVariableInstantiationSet(varA.instantiate(true), varB.instantiate("M")), BigDecimal.valueOf(0.8))
				.addRow(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate(true), varB.instantiate
						("F")), BigDecimal.valueOf(0.6)).addRow(VariableInstantiationSets.newVariableInstantiationSet
						(varA.instantiate(false), varB.instantiate("M")), BigDecimal.valueOf(0.2)).addRow
						(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate(false), varB
								.instantiate("F")), BigDecimal.valueOf(0.4)).getFactor();
		factorC = new Factor.FactorBuilder("F(C)", Sets.newHashSet(varC)).addRow(VariableInstantiationSets
				.newVariableInstantiationSet(varC.instantiate(true)), BigDecimal.valueOf(0.3)).addRow
				(VariableInstantiationSets.newVariableInstantiationSet(varC.instantiate(false)), BigDecimal.valueOf(0.7)).getFactor();


		System.out.println(factorA);

		System.out.println(factorC);
	}

	@Test
	public void multiply() throws Exception {
		Factor factorAC = factorA.multiply(factorC);
		// TODO assert resuls
		System.out.println(factorAC);
	}

	@Test
	public void summingOut() {
		Factor f = factorA.summingOut(Sets.newHashSet(varB));
		// missing proper test
		System.out.println(f);
	}
}