package com.sebas.bayes.inference;

import java.math.BigDecimal;

public interface Inference {

	BigDecimal evidenceProbability();

	BigDecimal priorMarginal();

	BigDecimal posteriorMarginal();

}
