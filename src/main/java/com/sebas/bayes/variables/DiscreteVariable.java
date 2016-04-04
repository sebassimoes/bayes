package com.sebas.bayes.variables;

import java.util.Set;

/**
 * Interface for Discrete Variables.
 * @author sebas.simoes
 *
 * @param <T> the type of discrete variables
 */
public interface DiscreteVariable<T> {

	/**
	 * Get the variable identifier.
	 * @return the variable identifier
	 */
	long getId();

	/**
	 * Get the variable label.
	 * @return the variable label
     */
	String getLabel();
	
	/**
	 * Get the possible variable states.
	 * @return the possible variable states
	 */
	Set<T> getStates();
	
	/**
	 * Get the variable dimension (the number of possible states).
	 * @return the number of possible states
	 */
	int dimension();

	/**
	 * Check if the given state is valid for this discrete variable.
	 * @param state the variable state
	 * @return <code>true</code> if the given state is a valid instantitation of the variable
	 */
	boolean validState(T state);

	/**
	 * Get an instantiation of the discrete variable.
	 * @param state the instantitation value
	 * @return the new variable instantiation
	 */
	VariableInstantiation<T> instantiate(T state);

	/**
	 * Get the set of valid variable instantiations.
	 * @return the set of valid variable instantiations.
	 */
	Set<VariableInstantiation<?>> variableInstantiations();
}
