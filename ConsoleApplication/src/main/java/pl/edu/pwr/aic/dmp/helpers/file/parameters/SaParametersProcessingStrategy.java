package pl.edu.pwr.aic.dmp.helpers.file.parameters;

import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.alg.utils.SaParameters;
import pl.edu.pwr.aic.dmp.helpers.FileParsingStrategy;

public class SaParametersProcessingStrategy extends FileParsingStrategy {

	@Override
	protected Parameters parseParameters(List<String> params) {
		SaParameters parameters = new SaParameters();
		parameters.setCyclesNumber(Integer.valueOf(params.get(0)));
		parameters.setStartTemperature(Double.valueOf(params.get(1)));
		parameters.setCoolingCoefficient(Double.valueOf(params.get(2)));
		parameters.setPermutationAttempts(Integer.valueOf(params.get(3)));
		return parameters;
	}

}
