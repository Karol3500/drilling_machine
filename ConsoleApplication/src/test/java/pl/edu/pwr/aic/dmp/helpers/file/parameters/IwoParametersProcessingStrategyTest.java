package pl.edu.pwr.aic.dmp.helpers.file.parameters;

import java.util.List;

import org.junit.Test;

import pl.edu.pwr.aic.dmp.IwoConsoleEntry;
import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.helpers.file.parameters.IwoParametersProcessingStrategy;

import static org.junit.Assert.assertEquals;

public class IwoParametersProcessingStrategyTest {
	
	@Test
	public void shouldCorrectlyParseIwoParametersFile(){
		String paramsFilePath = "src/test/resources/iwoParameters";
		IwoConsoleEntry entry = new IwoConsoleEntry();
		
		List<Parameters> params = entry.getParametersListsFromFile(paramsFilePath, new IwoParametersProcessingStrategy());

		assertParamsCorrect((IwoParameters)params.get(0), 600, 5, 100, 1, 10, 0.9, 5, 10);
		assertParamsCorrect((IwoParameters)params.get(1), 300, 1, 50, 2, 100, 0.91, 1, 100);
	}

	private void assertParamsCorrect(IwoParameters iwoParameters, int numberOfIterations,
			int minSpecimenInPopulation, int maxSpecimenInPopulation, int minSeedNumber, int maxSeedNumber,
			double nonLinearCoefficient, int initialTransformationsPerSeed, int finalTransformationsPerSeed) {
		assertEquals(numberOfIterations, iwoParameters.getNumberOfIterations());
		assertEquals(minSpecimenInPopulation, iwoParameters.getMinSpecimenInPopulation());
		assertEquals(maxSpecimenInPopulation, iwoParameters.getMaxSpecimenInPopulation());
		assertEquals(minSeedNumber, iwoParameters.getMinSeedNumber());
		assertEquals(maxSeedNumber, iwoParameters.getMaxSeedNumber());
		assertEquals(nonLinearCoefficient, iwoParameters.getNonLinearCoefficient(), 0d);
		assertEquals(initialTransformationsPerSeed, iwoParameters.getInitialTransformationsPerSeed());
		assertEquals(finalTransformationsPerSeed, iwoParameters.getFinalTransformationsPerSeed());
	}

}
