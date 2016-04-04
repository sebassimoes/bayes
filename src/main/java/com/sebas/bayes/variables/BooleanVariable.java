package com.sebas.bayes.variables;

import com.google.common.collect.Sets;

import java.util.Set;

public class BooleanVariable extends AbstractDiscreteVariable<Boolean> {

	public BooleanVariable(final long id, final String label) {
		super(id, label);
	}

	@Override
	public Set<Boolean> getStates() {
		return Sets.newHashSet(true, false);
	}

}
