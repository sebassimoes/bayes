package com.sebas.bayes;

import com.google.common.collect.Sets;
import com.sebas.bayes.variables.BooleanVariable;
import com.sebas.bayes.variables.VariableInstantiationSets;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;

public class BayesianNetworkTest {

	static BooleanVariable varA = new BooleanVariable(1, "A");
	static BooleanVariable varB = new BooleanVariable(2, "B");
	static BooleanVariable varC = new BooleanVariable(3, "C");

	@Test
	public void buildNetwork() throws Exception {

		BayesianNetwork network = BayesianNetwork.builder(Sets.newHashSet(varA, varB, varC)).
				addCPT(varA, new HashSet<>()).
				proba(true, BigDecimal.valueOf(0.6)).
				proba(false, BigDecimal.valueOf(0.4)).
				done().
				addCPT(varB, Sets.newHashSet(varA)).
				cpt(true, VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate(true)), BigDecimal.valueOf(0.9)).
				cpt(true, VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate(false)), BigDecimal.valueOf(0.1)).
				cpt(false, VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate(true)), BigDecimal.valueOf(0.2)).
				cpt(false, VariableInstantiationSets.newVariableInstantiationSet(varA.instantiate(false)), BigDecimal.valueOf(0.8)).
				done().
				addCPT(varC, Sets.newHashSet(varB)).
				cpt(true, VariableInstantiationSets.newVariableInstantiationSet(varB.instantiate(true)), BigDecimal.valueOf(0.3)).
				cpt(true, VariableInstantiationSets.newVariableInstantiationSet(varB.instantiate(false)), BigDecimal.valueOf(0.7)).
				cpt(false, VariableInstantiationSets.newVariableInstantiationSet(varB.instantiate(true)), BigDecimal.valueOf(0.5)).
				cpt(false, VariableInstantiationSets.newVariableInstantiationSet(varB.instantiate(false)), BigDecimal.valueOf(0.5)).
				done().
				buildNetwork();

		System.out.println(network);
	}

}