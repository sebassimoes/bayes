package com.sebas.bayes.conditioning.recursive;

import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {


		List<String> aa = Arrays.asList("A", "B");
		List<String> bb = Arrays.asList("1", "2", "3");
		List<List<String>> strs = Arrays.asList(aa, bb);

/*
		DTreeBuilder builder = new DTreeBuilder(Cutset.cutset("a", "b"));

		builder.addLeftNode(Cutset.cutset("a")).addLeftLeaf("a")
				.addRightLeaf("b");
		builder.addRightNode(Cutset.cutset("c", "d")).addLeftLeaf("c")
				.addRightNode(Arrays.asList()).addLeftLeaf("d")
				.addRightLeaf("e");

		System.out.println(builder.buildDTree());

		List<String> strs = Arrays.asList("elem1", "elem2", "elem3", "elem4");

		int size = strs.stream().reduce(0, (acc, e) -> acc + 1, (acc, e) -> acc + 1).intValue();
		System.out.println(size);
		*/
	}

}
