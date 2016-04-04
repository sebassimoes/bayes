package com.sebas.bayes.variables;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

public final class VariableInstantiationSets {

	private VariableInstantiationSets() {
		// not instantiatable class.
	}

	public static VariableInstantiationSet newVariableInstantiationSet(final VariableInstantiation<?>... variableInstantiations) {
		return new VariableInstantiationSet(Arrays.asList(variableInstantiations));
	}

	public static List<VariableInstantiationSet> cartesianProduct(Collection<DiscreteVariable<?>> variables) {
		final List<VariableInstantiationSet> result = new ArrayList<>();
		final List<Set<VariableInstantiation<?>>> variablesInstantiations = variables.stream().map(v -> v
				.variableInstantiations()).collect(Collectors.toList());
		return Sets.cartesianProduct(variablesInstantiations).stream().map(l -> new VariableInstantiationSet(l))
				.collect(Collectors.toList());
	}

}
