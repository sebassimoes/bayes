package com.sebas.bayes.factor;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Distribution implements Iterable<BigDecimal> {

	private List<BigDecimal> probabilities;
	
	private Distribution(List<BigDecimal> probabilities) {
		super();
		this.probabilities = probabilities;
	}
	
	public static Distribution createDistribution() {
		return new Distribution(Lists.newArrayList());
	}
	
	public static Distribution createDistribution(BigDecimal... probabilities) {
		return new Distribution(Arrays.asList(probabilities));
	}
	
	public int length() {
		return this.probabilities.size();
	}

	@Override
	public Iterator<BigDecimal> iterator() {
		return this.probabilities.iterator();
	}
	
}