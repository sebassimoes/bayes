package com.sebas.bayes.inference.variableelimination;

import com.google.common.collect.Sets;
import com.sebas.bayes.factor.Factor;
import com.sebas.bayes.variables.DiscreteVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VePr1 {

	public Factor run(Set<Factor> factors, DiscreteVariable<?> variable, List<DiscreteVariable<?>> eliminationOrder) {
		Set<Factor> state = new HashSet<>(factors);
		for (DiscreteVariable<?> variableToEliminate : eliminationOrder) {
			Set<Factor> fis = state.stream().filter(f -> f.containsVar(variableToEliminate)).collect(Collectors.toSet
					());
			Factor newFactor = fis.stream().reduce(null, (f1, f2) -> f2.multiply(f1));
			state.removeAll(fis);
			state.add(newFactor.summingOut(Sets.newHashSet(variableToEliminate)));
		}
		return state.stream().reduce(null, (f1, f2) -> f2.multiply(f1));
	}

}
