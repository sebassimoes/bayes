package com.sebas.bayes.conditioning.recursive;

import java.util.List;

public interface DTree {

	List<String> getCutset();
	
	boolean isLeaf();
	
	DTree left();
	
	DTree right();
	
	String variable();
}
