package com.sebas.bayes.conditioning.recursive;

import java.util.ArrayList;
import java.util.List;

public class Cutset {

	public static List<String> cutset(String... vars) {
		List<String> cutset = new ArrayList<>();
		for (String var : vars) {
			cutset.add(var);
		}
		return cutset;
	}
	
}
