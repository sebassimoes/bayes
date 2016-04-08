package com.sebas.bayes;

import com.google.common.collect.Sets;
import com.sebas.bayes.factor.Factor;
import com.sebas.bayes.variables.DiscreteVariable;
import com.sebas.bayes.variables.VariableInstantiationSet;
import com.sebas.bayes.variables.VariableInstantiationSets;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BayesianNetwork {

	private static final Logger LOGGER = LoggerFactory.getLogger(BayesianNetwork.class);

	private final DirectedAcyclicGraph<BayesianNode, DefaultEdge> dag;

	private BayesianNetwork(final DirectedAcyclicGraph<BayesianNode, DefaultEdge> dag) {
		super();
		this.dag = dag;
	}

	/**
	 *
	 * @param variables
	 * @return
	 */
	public static Builder builder(final Set<DiscreteVariable<?>> variables) {
		return new Builder(variables);
	}

	@Override
	public String toString() {
		return "BayesianNetwork{" +
				"dag=" + dag +
				'}';
	}

	static final class Builder {

		private final DirectedAcyclicGraph<BayesianNode, DefaultEdge> dag;
		private final Set<DiscreteVariable<?>> variables;


		private final Set<BayesianNode> nodes;

		private Builder(final Set<DiscreteVariable<?>> variables) {
			this.dag = new DirectedAcyclicGraph<BayesianNode, DefaultEdge>(new ClassBasedEdgeFactory<>(DefaultEdge.class));
			this.variables = variables;
			this.nodes = new HashSet<>(this.variables.size());
		}

		public Builder(final DiscreteVariable<?>... variables) {
			this(Sets.newHashSet(variables));
		}

		public <T> CPTBuilder<T> addCPT(DiscreteVariable<T> variable, Set<DiscreteVariable<?>> parents) {
			return new CPTBuilder<>(this, variable, parents);
		}

		public BayesianNetwork buildNetwork() throws DirectedAcyclicGraph.CycleFoundException {

			if (!this.nodes.stream().map(BayesianNode::getVariable).collect(Collectors.toSet()).equals(this.variables)) {
				// TODO sebas.simoes: throw checked? exception because the nodes variables set doesn't match the set of bayesian network variables.
				throw new RuntimeException("Variáveis n foram inicializadas!");
			}

			for (final BayesianNode node : this.nodes) {
				LOGGER.trace("connecting node {}", node.getVariable());
				this.connectNode(node);
			}

			return new BayesianNetwork(this.dag);
		}

		private void addNode(final BayesianNode node) {
			LOGGER.trace("add node {}", node.getVariable());
			this.nodes.add(node);
			// add to the dag.
			this.dag.addVertex(node);
		}

		private void connectNode(final BayesianNode node) throws DirectedAcyclicGraph.CycleFoundException {
			final DiscreteVariable<?> variable = node.getVariable();
			for (final DiscreteVariable<?> parent : node.getCptFactor().vars()) {
				if (!parent.equals(variable)) {
					LOGGER.trace("connect {} with {}", parent, variable);
					// get the parent node.
					final Optional<BayesianNode> optionalParentNode = this.nodes.parallelStream().
							filter(n -> n.getVariable().equals(parent)).findFirst();
					if (optionalParentNode.isPresent()) {
						final BayesianNode parentNode = optionalParentNode.get();
						// add the edge to the dag.
						this.dag.addDagEdge(parentNode, node);
					}
				}
			}
		}

		static class CPTBuilder<T> {

			private final DiscreteVariable<T> variable;
			private	final Builder networkBuilder;

			private Factor.FactorBuilder factorBuilder;

			CPTBuilder(final Builder networkBuilder, final DiscreteVariable<T> variable,
							  final Set<DiscreteVariable<?>> parents) {
				super();
				this.networkBuilder = networkBuilder;
				this.variable = variable;

				final Set<DiscreteVariable<?>> factorVariables = new HashSet<>(parents);
				// add the main variable, as it's a factor variable as well.
				factorVariables.add(variable);
				// initialize the factor builder with the variable label and all the factor variables.
				this.factorBuilder = new Factor.FactorBuilder(variable.getLabel(), factorVariables);
			}

			CPTBuilder cpt(final T value, final VariableInstantiationSet parentsInstantiation, final BigDecimal p) {
				this.factorBuilder.addRow(parentsInstantiation.addVariableInstantiation(this.variable.instantiate(value)), p);
				return this;
			}

			CPTBuilder proba(final T value, final BigDecimal p) {
				this.factorBuilder.addRow(VariableInstantiationSets.newVariableInstantiationSet(this.variable.instantiate(value)), p);
				return this;
			}

			Builder done() {
				// get the CPT as a factor.
				Factor factor = this.factorBuilder.getFactor();
				// add the new node to the network.
				this.networkBuilder.addNode(new BayesianNode(this.variable, factor));
				// return control to the network builder.
				return this.networkBuilder;
			}

		}

	}

}
