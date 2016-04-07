package com.sebas.bayes;

import com.sebas.bayes.variables.DiscreteVariable;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BayesianNetwork {

	private Set<DiscreteVariable<?>> variables;

	private DirectedAcyclicGraph<BayesianNode, DefaultEdge> dag;

	private BayesianNetwork(final Collection<DiscreteVariable<?>> variables) {
		this.dag = new DirectedAcyclicGraph<BayesianNode, DefaultEdge>(new ClassBasedEdgeFactory<>(DefaultEdge.class));
		this.variables = new HashSet<>(variables);
	}

	public void cpt(final DiscreteVariable<?> mainVariable, final BigDecimal... values) {

	}

	//public void addCpt()

	public static class Builder {

		public Builder(final Set<DiscreteVariable<?>> variables) {

		}

		public Builder(final DiscreteVariable<?>... variables) {

		}


	}

}
