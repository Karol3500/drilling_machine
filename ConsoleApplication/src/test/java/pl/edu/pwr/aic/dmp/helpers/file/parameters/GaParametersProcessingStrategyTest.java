package pl.edu.pwr.aic.dmp.helpers.file.parameters;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import pl.edu.pwr.aic.dmp.GaConsoleEntry;
import pl.edu.pwr.aic.dmp.alg.utils.GaParameters;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.alg.utils.SelectionMethod;

public class GaParametersProcessingStrategyTest {

	@Test
	public void shouldProperlyReadGaParameters(){
		String paramsFilePath = "src/test/resources/gaParameters";
		GaConsoleEntry entry = new GaConsoleEntry();
		
		List<Parameters> params = entry.getParametersListsFromFile(paramsFilePath, new GaParametersProcessingStrategy());

		assertParamsCorrect((GaParameters)params.get(0), 200, 200, 0.5, 0.2, SelectionMethod.ROULETTE);
		assertParamsCorrect((GaParameters)params.get(1), 100, 350, 0.2, 0.1, SelectionMethod.TOURNAMENT);
	}
	
	@Test
	public void shouldUseTournamentWhenNoSelectionMethodGiven(){
		String paramsFilePath = "src/test/resources/gaParametersNoSelMethod";
		GaConsoleEntry entry = new GaConsoleEntry();
		
		List<Parameters> params = entry.getParametersListsFromFile(paramsFilePath, new GaParametersProcessingStrategy());

		assertParamsCorrect((GaParameters)params.get(0), 200, 200, 0.5, 0.2, SelectionMethod.TOURNAMENT);
		assertParamsCorrect((GaParameters)params.get(1), 100, 350, 0.2, 0.1, SelectionMethod.TOURNAMENT);
	}

	private void assertParamsCorrect(GaParameters parameters, int populationCount, int generationsCount,
			double mutationProbability, double crossingProbability, SelectionMethod selectionMethod) {
		assertEquals(populationCount, parameters.getPopulationCount());
		assertEquals(generationsCount, parameters.getGenerationsCount());
		assertEquals(mutationProbability, parameters.getMutationProbability(), 0d);
		assertEquals(crossingProbability, parameters.getCrossingProbability(), 0d);
		assertEquals(selectionMethod, parameters.getSelectionMethod());
	}
	
}
