package pl.edu.pwr.aic.dmp.helpers.file.parameters;

import java.util.List;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pl.edu.pwr.aic.dmp.SaConsoleEntry;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.alg.utils.SaParameters;

public class SaParametersProcessingStrategyTest {

	@Test
	public void shouldProcessCorrectlySaParameters(){
		String paramsFilePath = "src/test/resources/saParameters";
		SaConsoleEntry entry = new SaConsoleEntry();

		List<Parameters> params = entry.getParametersListsFromFile(paramsFilePath, new SaParametersProcessingStrategy());
		assertParamsCorrect((SaParameters)params.get(0), 200, 0.1, 0.1, 3);
		assertParamsCorrect((SaParameters)params.get(1), 100, 0.5, 0.2, 4);
	}

	private void assertParamsCorrect(SaParameters saParameters, int cyclesNumber, double startTemperature,
			double coolingCoefficient, int permutationAttempts) {
		assertEquals(cyclesNumber, saParameters.getCyclesNumber());
		assertEquals(startTemperature, saParameters.getStartTemperature(), 0d);
		assertEquals(coolingCoefficient, saParameters.getCoolingCoefficient(), 0d);
		assertEquals(permutationAttempts, saParameters.getPermutationAttempts());
	}

}
