package com.sebas.bayes.factor;

import com.sebas.bayes.variables.VariableInstantiationSet;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

class FactorRow {

	private final VariableInstantiationSet instantiation;
	private final BigDecimal value;

	private FactorRow(final VariableInstantiationSet instantiation, final BigDecimal value) {
		super();
		this.instantiation = instantiation;
		this.value = value;
	}

	static FactorRow createFactorRow(final VariableInstantiationSet instantiation, final BigDecimal value) {
		if (value == null) {
			throw new InvalidParameterException("Factor value is null.");
		} else {
			if (value.compareTo(BigDecimal.ZERO) < 0) {
				throw new InvalidFactorValueException(value);
			}
		}
		return new FactorRow(instantiation, value);
	}

	public boolean contains(final VariableInstantiationSet variableInstantiationSet) {
		return this.instantiation.contains(variableInstantiationSet);
	}

	VariableInstantiationSet getInstantiation() {
		return instantiation;
	}

	BigDecimal getValue() {
		return value;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		this.instantiation.stream().sorted((o1, o2) -> o1.getVariable().getLabel().compareTo(o2.getVariable().getLabel
				())).forEach(variableInstantiation -> builder.append("|").append(StringUtils.center
				(variableInstantiation.getValue().toString(), 10)));
		builder.append("|").append(StringUtils.center(this.getValue().toString(), 10)).append("|");
		return builder.toString();
	}
}
