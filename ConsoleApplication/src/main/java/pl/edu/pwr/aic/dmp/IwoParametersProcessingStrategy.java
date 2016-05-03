package pl.edu.pwr.aic.dmp;

import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.helpers.FileParsingStrategy;

public class IwoParametersProcessingStrategy extends FileParsingStrategy {

	@Override
	protected Parameters parseParameters(List<String> params) {
		IwoParameters parameters = new IwoParameters();
		parameters.setNumberOfIterations(Integer.valueOf(params.get(0)));
		parameters.setMinSpecimenInPopulation(Integer.valueOf(params.get(1)));
		parameters.setMaxSpecimenInPopulation(Integer.valueOf(params.get(2)));
		parameters.setMinSeedNumber(Integer.valueOf(params.get(3)));
		parameters.setMaxSeedNumber(Integer.valueOf(params.get(4)));
		parameters.setNonLinearCoefficient(Double.valueOf(params.get(5)));
		parameters.setInitialTransformationsPerSeed(Integer.valueOf(params.get(6)));
		parameters.setFinalTransformationsPerSeed(Integer.valueOf(params.get(7)));
		return parameters;
	}

}
