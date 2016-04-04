package com.sebas.bayes.variables;

import java.util.Set;

/**
 * Bayesian Networks Java Implementation.
 * Created by sebas.simoes on 26/03/2016.
 * <p>
 * The source code is intelectual property of sebas.simoes@gmail.com
 * <p>
 * Revision: $SCM_REVISION, $SCM_REVISION_DATE
 */
public class DynamicVariable extends AbstractDiscreteVariable<String> {

	private Set<String> states;

	public DynamicVariable(final long id, final String label, final Set<String> validStates) {
		super(id, label);
		this.states = validStates;
	}

	@Override
	public Set<String> getStates() {
		return this.states;
	}
}
