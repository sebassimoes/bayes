package com.sebas.bayes;

import com.sebas.bayes.variables.DiscreteVariable;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class BayesianNetwork {

	private DirectedGraph<DiscreteVariable<?>, DefaultEdge> qualitativeModel;

	public void addDiscreteVariable(final DiscreteVariable<?> discreteVariable) {
		this.qualitativeModel.addVertex(discreteVariable);
	}

	//public void addCpt()

}
