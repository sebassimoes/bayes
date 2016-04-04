package com.sebas.bayes.conditioning.recursive;

import java.util.List;
import java.util.Stack;

public class DTreeBuilder {

	private Stack<Element> elements;
	private List<String> rootCutset;

	public DTreeBuilder(final List<String> rootCutset) {
		this.rootCutset = rootCutset;
		this.elements = new Stack<>();
	}

	public DTreeBuilder addLeftNode(final List<String> nodeCutset) {
		elements.push(Element.leftNode(nodeCutset));
		return this;
	}

	public DTreeBuilder addRightNode(final List<String> nodeCutset) {
		elements.push(Element.rightNode(nodeCutset));
		return this;
	}

	public DTreeBuilder addLeftLeaf(final String variable) {
		elements.push(Element.leftLeaf(variable));
		return this;
	}

	public DTreeBuilder addRightLeaf(final String variable) {
		elements.push(Element.rightLeaf(variable));
		return this;
	}

	public DTree buildDTree() {
		final Stack<DTree> pendingLeft = new Stack<>();
		final Stack<DTree> pendingRight = new Stack<>();
		while (!this.elements.isEmpty()) {
			final Element element = this.elements.pop();
			DTree leafOrNode = null;
			if (element.type == Type.LEAF) {
				leafOrNode = new DTreeLeaf(element.variable);
			} else {
				leafOrNode = new DTreeNode(element.cutset, pendingLeft.pop(), pendingRight.pop());
			}
			if (element.direction == Direction.RIGHT) {
				pendingRight.push(leafOrNode);
			} else {
				pendingLeft.push(leafOrNode);
			}
		}
		final DTree rootNode = new DTreeNode(this.rootCutset, pendingLeft.pop(), pendingRight.pop());
		return rootNode;
	}

	private static enum Direction {
		LEFT, RIGHT;
	}

	private static enum Type {
		NODE, LEAF;
	}

	private static class Element {
		Direction direction;
		Type type;
		String variable;
		List<String> cutset;

		private Element(Direction d, Type t, String variable,
				List<String> cutset) {
			super();
			this.direction = d;
			this.type = t;
			this.variable = variable;
			this.cutset = cutset;
		}

		static Element leftNode(List<String> cutset) {
			return new Element(Direction.LEFT, Type.NODE, null, cutset);
		}

		static Element rightNode(List<String> cutset) {
			return new Element(Direction.RIGHT, Type.NODE, null, cutset);
		}

		static Element leftLeaf(String v) {
			return new Element(Direction.LEFT, Type.LEAF, v, null);
		}

		static Element rightLeaf(String v) {
			return new Element(Direction.RIGHT, Type.LEAF, v, null);
		}

		@Override
		public String toString() {
			return "Element [direction=" + direction + ", type=" + type + "]";
		}
	}

}
