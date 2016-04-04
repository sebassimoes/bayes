package com.sebas.bayes.conditioning.recursive;

import java.util.List;

public class DTreeNode implements DTree {

	private DTree left;
	private DTree right;
	private List<String> cutset;

	DTreeNode(final List<String> cutset, final DTree leftTree,
			final DTree rightTree) {
		super();
		this.cutset = cutset;
		this.left = leftTree;
		this.right = rightTree;
	}

	@Override
	public List<String> getCutset() {
		return this.cutset;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public DTree left() {
		return this.left;
	}

	@Override
	public DTree right() {
		return this.right;
	}

	@Override
	public String variable() {
		return null;
	}

	@Override
	public String toString() {
		return String.format("Node [cutset=%s, leftTree=%s, rightTree=%s]",
				this.cutset, this.left.toString(), this.right.toString());
	}

}
