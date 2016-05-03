package pl.edu.pwr.aic.dmp.helpers.file.parameters;

import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.GaParameters;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.alg.utils.SelectionMethod;
import pl.edu.pwr.aic.dmp.helpers.FileParsingStrategy;

public class GaParametersProcessingStrategy extends FileParsingStrategy {

	@Override
	protected Parameters parseParameters(List<String> params) {
		GaParameters parameters = new GaParameters();
		parameters.setPopulationCount(Integer.valueOf(params.get(0)));
		parameters.setGenerationsCount(Integer.valueOf(params.get(1)));
		parameters.setMutationProbability(Double.valueOf(params.get(2)));
		parameters.setCrossingProbability(Double.valueOf(params.get(3)));
		parameters.setSelectionMethod(parseSelectionMethod(params));
		return parameters;
	}

	private SelectionMethod parseSelectionMethod(List<String> params) {
		if(params.size() < 5 || !params.get(4).toLowerCase().equals("roulette")){
			return SelectionMethod.TOURNAMENT;
		}
		return SelectionMethod.ROULETTE;
	}
}
