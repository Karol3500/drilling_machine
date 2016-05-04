package pl.edu.pwr.aic.dmp.utils.export;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.utils.UnitResult;
import pl.edu.pwr.aic.dmp.utils.export.CsvExporterUtil;
import pl.edu.pwr.aic.dmp.utils.export.CsvUnitSolutionExporter;

public class CsvUnitSolutionExporterTest {

	List<String> createdFiles = new ArrayList<>();
	
	@Test
	public void shouldWriteUnitResultToFile() throws WriteException, IOException{
		CsvUnitSolutionExporter exporter = new CsvUnitSolutionExporter();
		List<String> expectedRes = prepareExpectedResult();
		exporter.createNewFile("UnitResultTest");
		createdFiles.add(exporter.fileName);

		exporter.writeUnitResult(prepareUnitResult());

		List<String> actualLines = Files.readAllLines(Paths.get(exporter.fileName));
		assertEquals(expectedRes, actualLines);
	}
	
	@Test
	public void shouldWriteManyUnitResultsToFile() throws WriteException, IOException{
		CsvUnitSolutionExporter exporter = new CsvUnitSolutionExporter();
		List<UnitResult> results = prepareUnitResults();
		
		List<String> expectedRes = prepareExpectedResultForManyURes();
		exporter.createNewFile("UnitResultTest");
		createdFiles.add(exporter.fileName);

		exporter.writeUnitResults(results);

		List<String> actualLines = Files.readAllLines(Paths.get(exporter.fileName));
		assertEquals(expectedRes, actualLines);
	}

	private List<UnitResult> prepareUnitResults() {
		UnitResult res = prepareUnitResult();
		UnitResult res2 = prepareUnitResult();
		UnitResult res3 = prepareUnitResult();
		return Arrays.asList(res, res2, res3);
	}

	private List<String> prepareExpectedResult() {
		List<String> expectedRes = Arrays.asList(CsvUnitSolutionExporter.BEST_ROUTE_LENGTH + 
				CsvExporterUtil.CSV_DELIMITER
				+ CsvUnitSolutionExporter.EXEC_TIME + CsvExporterUtil.CSV_DELIMITER
				+ CsvUnitSolutionExporter.PERMUTATION,
				5d + CsvExporterUtil.CSV_DELIMITER + 100d + CsvExporterUtil.CSV_DELIMITER +"5 15 54 4 1 2 3 5 6");
		return expectedRes;
	}
	
	private List<String> prepareExpectedResultForManyURes() {
		List<String> expectedRes = Arrays.asList(CsvUnitSolutionExporter.BEST_ROUTE_LENGTH + 
				CsvExporterUtil.CSV_DELIMITER
				+ CsvUnitSolutionExporter.EXEC_TIME + CsvExporterUtil.CSV_DELIMITER
				+ CsvUnitSolutionExporter.PERMUTATION,
				5d + CsvExporterUtil.CSV_DELIMITER + 100d + CsvExporterUtil.CSV_DELIMITER +"5 15 54 4 1 2 3 5 6",
				5d + CsvExporterUtil.CSV_DELIMITER + 100d + CsvExporterUtil.CSV_DELIMITER +"5 15 54 4 1 2 3 5 6",
				5d + CsvExporterUtil.CSV_DELIMITER + 100d + CsvExporterUtil.CSV_DELIMITER +"5 15 54 4 1 2 3 5 6");
		return expectedRes;
	}

	private UnitResult prepareUnitResult() {
		UnitResult res = new UnitResult();
		res.setBestRouteLength(5d);
		res.setExecutionTimeInSeconds(100);
		res.setBestRoute(Arrays.asList(5,15,54,4,1,2,3,5,6));
		return res;
	}
	
	@After
	public void removeCreatedFiles() throws IOException{
		for(String file : createdFiles){
			Files.delete(Paths.get(file));
		}
	}
}
