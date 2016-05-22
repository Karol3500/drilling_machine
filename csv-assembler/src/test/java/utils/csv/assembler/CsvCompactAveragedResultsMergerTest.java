package utils.csv.assembler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CsvCompactAveragedResultsMergerTest {

	CsvCompactAveragedResultsMerger merger = new CsvCompactAveragedResultsMerger();
	CsvFileReadingUtil util = new CsvFileReadingUtil();

	@Test
	public void shouldConvertParametersLineIntoValuesLine(){
		String lineFromCsv = "Number of cycles | 20";

		String converted = merger.convertParametersLineIntoValuesLine(lineFromCsv);

		assertEquals("20", converted);
	}

	@Test
	public void shouldReadParametersFromManyFiles(){
		List<String> expectedParams = Arrays.asList(
				"20 150 0.95 10", "20 200 0.95 10",	"20 300 0.95 10", "20 400 0.95 10",
				"20 500 0.95 10", "20 600 0.95 10",	"20 700 0.95 10", "20 800 0.95 10",
				"2 300 0.95 10", "5 300 0.95 10",	"10 300 0.95 10", "20 300 0.95 10",
				"30 300 0.95 10", "40 300 0.95 10",	"50 300 0.95 10", "10 300 0.95 5",
				"10 300 0.95 20", "10 300 0.95 30",	"10 300 0.95 40", "10 300 0.95 50");
		String[] filePaths = {
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_10.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_11.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_12.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_13.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_14.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_15.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_16.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_17.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_18.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_19.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_1.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_2.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_3.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_4.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_5.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_6.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_7.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_8.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_9.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp.csv"
		};

		List<String> readParams = merger.readParameters(filePaths);

		assertEquals(expectedParams.size(), readParams.size());
		for(String params : readParams){
			assertTrue(expectedParams.contains(params));
		}
	}

	@Test
	public void shouldProperlyWorkWithGAParameters(){
		String[] filePaths = {"src/test/java/resources/GeneticAlgorthm_a280.tsp.csv"};
		String expectedParams = "900 900 0.5 0.5 TOURNAMENT";

		List<String> readParameters = merger.readParameters(filePaths);

		assertEquals(expectedParams, readParameters.get(0));
	}

	@Test
	public void shouldProperlyReadParametersAndResults(){
		String[] filePaths = {
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_10.csv",
				"src/test/java/resources/manyResultFiles/SimulatedAnnealing_pr107.tsp_11.csv"
		};
		List<String> expectedParams = Arrays.asList("10 300 0.95 10","20 300 0.95 10");
		List<SingleResult> expectedResults1 = Arrays.asList(
				new SingleResult(97062.39042529104, 3.474),
				new SingleResult(94751.6794266008, 3.677),
				new SingleResult(94917.90893754024, 4.014),
				new SingleResult(95762.9435623979, 3.256),
				new SingleResult(94725.4278304608, 3.931),
				new SingleResult(96179.09757498009, 3.379),
				new SingleResult(96090.13895259153, 3.585),
				new SingleResult(94803.3961537457, 4.212),
				new SingleResult(96858.01766038037, 3.613),
				new SingleResult(94968.8864175742, 3.668)
		);
		List<SingleResult> expectedResults2 = Arrays.asList(
				new SingleResult(94997.77680573256, 5.963),
				new SingleResult(97290.9898892069, 6.309),
				new SingleResult(95395.00389220697, 6.207),
				new SingleResult(96355.25319989826, 6.221),
				new SingleResult(98291.24682874448, 5.733),
				new SingleResult(94818.1191992287, 6.088),
				new SingleResult(94651.91387320487, 6.879),
				new SingleResult(96564.01332223501, 6.492),
				new SingleResult(94416.72307915075, 5.932),
				new SingleResult(94823.41188230718, 6.177)
				);

		List<ParamsWithResultsHolder> res = merger.readAllParamsAndResults(filePaths);

		assertEquals(expectedParams.get(0), res.get(0).getParameters());
		assertEquals(expectedParams.get(1), res.get(1).getParameters());
		assertEquals(expectedResults1, res.get(0).getResults());
		assertEquals(expectedResults2, res.get(1).getResults());
	}
	
	@Test
	public void shouldConvertListOfParamsWithResultsHolderToLatexFormat(){
		List<ParamsWithResultsHolder> holders = createHolder();
		String expected = getExpectedLatexOutcome();
		
		String latexTable = merger.getParamsAndResultsFromHoldersAsLatex(holders);
		System.out.println("Expected:");
		System.out.println(expected);
		
		System.out.println("\n\nGot:");
		System.out.println(latexTable);
		
		assertEquals(expected, latexTable);
	}

	private List<ParamsWithResultsHolder> createHolder() {
		ParamsWithResultsHolder h1 = new ParamsWithResultsHolder();
		h1.setParameters("1 2 3");
		h1.setResults(Arrays.asList(new SingleResult(1d, 2d), new SingleResult(3d, 4d)));
		
		ParamsWithResultsHolder h2 = new ParamsWithResultsHolder();
		h2.setParameters("4 5 6");
		h2.setResults(Arrays.asList(new SingleResult(3d, 6d), new SingleResult(6d, 8d)));
		
		return Arrays.asList(h1, h2);
	}
	
	private String getExpectedLatexOutcome() {
		String hline = "\n\\hline\n";
		String end = "\\\\";
		
		return "\\begin{longtable}{|c|c|c|c|c|c|}" + 
				hline +
				"\\cellcolor{lightgray}ParamId &" + "\\cellcolor{lightgray}Best route lenght &" +
				"\\cellcolor{lightgray}Worst route length &" + "\\cellcolor{lightgray}Avg route length &" + 
				"\\cellcolor{lightgray}Avg exec time &" + "\\cellcolor{lightgray}D" + end +
				hline +
				"1 & 1.00 & 3.00 & 2.00 & 3.00 & 3.00" + end +
				hline + 
				"2 & 3.00 & 6.00 & 4.50 & 7.00 & 21.00" + end +
				hline +
				"\\end{longtable}";
	}
}
