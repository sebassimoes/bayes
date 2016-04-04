package com.sebas.bayes.conditioning.recursive;

import java.util.List;

public class DTreeLeaf implements DTree {

	DTreeLeaf(final String variable) {
		super();
		this.variable = variable;
	}
	
	private String variable;
	
	@Override
	public List<String> getCutset() {
		return null;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public DTree left() {
		return null;
	}

	@Override
	public DTree right() {
		return null;
	}

	@Override
	public String variable() {
		return this.variable;
	}
	
	@Override
	public String toString() {
		return String.format("Leaf [variable=%s]", this.variable);
	}

}
