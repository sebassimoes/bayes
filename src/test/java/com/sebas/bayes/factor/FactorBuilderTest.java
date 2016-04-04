package com.sebas.bayes.factor;

import com.google.common.collect.Sets;
import com.sebas.bayes.factor.Factor.FactorBuilder;
import com.sebas.bayes.variables.BooleanVariable;
import com.sebas.bayes.variables.DynamicVariable;
import com.sebas.bayes.variables.VariableInstantiationSet;
import com.sebas.bayes.variables.VariableInstantiationSets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 26/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public class FactorBuilderTest {

	private static DynamicVariable varA;
	private static DynamicVariable varB;
	private static BooleanVariable varC;
	private static BooleanVariable varD;
	private static FactorBuilder builder;

	@Before
	public void setUp() throws Exception {
		varA = new DynamicVariable(1, "A", Sets.newHashSet("1", "2"));
		varB = new DynamicVariable(2, "B", Sets.newHashSet("M", "F", "U"));
		varC = new BooleanVariable(3, "C");
		varD = new BooleanVariable(4, "D");
		builder = new FactorBuilder("F(A)", Sets.newHashSet(varA, varB, varC));
	}

	@Test(expected = InconsistentInstantiationVariablesException.class)
	public void addRowNullInstantiation() throws Exception {
		builder.addRow(null, BigDecimal.ZERO);
	}

	@Test(expected = InconsistentInstantiationVariablesException.class)
	public void addRowMissingVariables() throws Exception {
		VariableInstantiationSet instantiation = VariableInstantiationSets.newVariableInstantiationSet(varA
				.instantiate("1"), varB.instantiate("M"));
		builder.addRow(instantiation, BigDecimal.ZERO);
	}

	@Test(expected = InconsistentInstantiationVariablesException.class)
	public void addRowExceedingVariables() throws Exception {
		VariableInstantiationSet instantiation = VariableInstantiationSets.newVariableInstantiationSet(varA
				.instantiate("1"), varB.instantiate("M"), varC.instantiate(true), varD.instantiate(false));
		builder.addRow(instantiation, BigDecimal.ZERO);
	}

	@Test(expected = DuplicatedInstantiationException.class)
	public void addDuplicatedRow() {
		VariableInstantiationSet instantiation1 = VariableInstantiationSets.newVariableInstantiationSet(varA
				.instantiate("1"), varB.instantiate("M"), varC.instantiate(true));
		VariableInstantiationSet instantiation2 = VariableInstantiationSets.newVariableInstantiationSet(varB
				.instantiate("M"), varA.instantiate("1"), varC.instantiate(true));
		builder.addRow(instantiation1, BigDecimal.ZERO);
		builder.addRow(instantiation2, BigDecimal.ONE);
	}

	@Test(expected = DuplicatedInstantiationException.class)
	public void addAnotherDuplicatedRow() {
		VariableInstantiationSet instantiation1 = VariableInstantiationSets.newVariableInstantiationSet(varA
				.instantiate("1"), varB.instantiate("M"), varC.instantiate(true));
		VariableInstantiationSet instantiation2 = VariableInstantiationSets.newVariableInstantiationSet(varB
				.instantiate("F"), varA.instantiate("1"), varC.instantiate(true));
		VariableInstantiationSet instantiation3 = VariableInstantiationSets.newVariableInstantiationSet(varB
				.instantiate("M"), varA.instantiate("1"), varC.instantiate(true));
		builder.addRow(instantiation1, BigDecimal.ZERO);
		builder.addRow(instantiation2, BigDecimal.ONE);
		builder.addRow(instantiation3, BigDecimal.ONE);
	}

	@Test(expected = InvalidFactorValueException.class)
	public void addNegativeFactorValue() {
		builder.addRow(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate("1"), varB.instantiate
				("M"), varC.instantiate(true)), BigDecimal.valueOf(-1.0));
	}

	@Test(expected = InvalidParameterException.class)
	public void addNullFactorValue() {
		builder.addRow(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate("1"), varB.instantiate
				("M"), varC.instantiate(true)), null);
	}

	@Test
	public void addFactorValueGreaterThanOne() {
		builder.addRow(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate("1"), varB.instantiate
				("M"), varC.instantiate(true)), BigDecimal.TEN);
		Assert.assertTrue(true);
	}

	@Test(expected = InvalidFactorTableSizeException.class)
	public void getFactorWithInvalidTableSize() {
		VariableInstantiationSet instantiation1 = VariableInstantiationSets.newVariableInstantiationSet(varA
				.instantiate("1"), varB.instantiate("M"), varC.instantiate(true));
		VariableInstantiationSet instantiation2 = VariableInstantiationSets.newVariableInstantiationSet(varB
				.instantiate("F"), varA.instantiate("1"), varC.instantiate(true));
		VariableInstantiationSet instantiation3 = VariableInstantiationSets.newVariableInstantiationSet(varB
				.instantiate("M"), varA.instantiate("2"), varC.instantiate(false));
		builder.addRow(instantiation1, BigDecimal.ZERO);
		builder.addRow(instantiation2, BigDecimal.ONE);
		builder.addRow(instantiation3, BigDecimal.ONE);

		builder.getFactor();
	}

	@Test
	public void getValidFactor() {
		Factor f = builder.addRow(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate("1"), varB
				.instantiate("M"), varC.instantiate(true)), BigDecimal.valueOf(0.333)).addRow
				(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate("1"), varB.instantiate("M"),
						varC.instantiate(false)), BigDecimal.valueOf(0.333)).addRow(VariableInstantiationSets
				.newVariableInstantiationSet(varA.instantiate("1"), varB.instantiate("F"), varC.instantiate(true)),
				BigDecimal.valueOf(0.333)).addRow(VariableInstantiationSets.newVariableInstantiationSet(varA
				.instantiate("1"), varB.instantiate("F"), varC.instantiate(false)), BigDecimal.valueOf(0.333)).addRow
				(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate("1"), varB.instantiate("U"),
						varC.instantiate(true)), BigDecimal.valueOf(0.333)).addRow(VariableInstantiationSets
				.newVariableInstantiationSet(varA.instantiate("1"), varB.instantiate("U"), varC.instantiate(false)),
				BigDecimal.valueOf(0.333)).addRow(VariableInstantiationSets.newVariableInstantiationSet(varA
				.instantiate("2"), varB.instantiate("M"), varC.instantiate(true)), BigDecimal.valueOf(0.333)).addRow
				(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate("2"), varB.instantiate("M"),
						varC.instantiate(false)), BigDecimal.valueOf(0.333)).addRow(VariableInstantiationSets
				.newVariableInstantiationSet(varA.instantiate("2"), varB.instantiate("F"), varC.instantiate(true)),
				BigDecimal.valueOf(0.333)).addRow(VariableInstantiationSets.newVariableInstantiationSet(varA
				.instantiate("2"), varB.instantiate("F"), varC.instantiate(false)), BigDecimal.valueOf(0.333)).addRow
				(VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate("2"), varB.instantiate("U"),
						varC.instantiate(true)), BigDecimal.valueOf(0.333)).addRow(VariableInstantiationSets
				.newVariableInstantiationSet(varA.instantiate("2"), varB.instantiate("U"), varC.instantiate(false)),
				BigDecimal.valueOf(0.333)).getFactor();

		Assert.assertEquals("F(A)", f.getLabel());
		System.out.println(f.toString());
	}
}