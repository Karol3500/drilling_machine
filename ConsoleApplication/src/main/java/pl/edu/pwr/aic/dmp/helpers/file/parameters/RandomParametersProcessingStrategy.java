package pl.edu.pwr.aic.dmp.helpers.file.parameters;

import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.alg.utils.RandomParameters;
import pl.edu.pwr.aic.dmp.helpers.FileParsingStrategy;

public class RandomParametersProcessingStrategy extends FileParsingStrategy {

	@Override
	protected Parameters parseParameters(List<String> params) {
		RandomParameters p = new RandomParameters();
		p.setCyclesNumber(Integer.valueOf(params.get(0)));
		return p;
	}

}
