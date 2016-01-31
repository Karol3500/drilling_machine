package pl.edu.pwr.aic.dmp.metaEA.export;

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
import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.metaEA.TuningExperimentResult;
import pl.edu.pwr.aic.dmp.utils.export.CsvExporterUtil;

public class CsvTuningExperimentResultExporterTest {

	List<String> createdFiles = new ArrayList<>();
	
	@Test
	public void shouldExportSingleExperimentResult() throws IOException, WriteException{
		CsvTuningExperimentResultExporter e = new CsvTuningExperimentResultExporter("tuningRes");
		TuningExperimentResult res = new TuningExperimentResult(new IwoParameters().setSaneDefaults()
				, 5d, 100, 15d);
		createdFiles.add(e.fileName);
		
		e.write(res);
		
		List<String> expectedLines = Arrays.asList(createHeaderString(res.getParams().getParameterNamesAsList()),
				getDataRow(res.getParams().getParameterValuesAsList()));
		List<String> actualLines = Files.readAllLines(Paths.get(e.fileName));
		
		assertEquals(expectedLines, actualLines);
	}
	
	@Test
	public void shouldExportManyExperimentResults() throws IOException, WriteException{
		CsvTuningExperimentResultExporter e = new CsvTuningExperimentResultExporter("tuningResMany");
		List<TuningExperimentResult> results = getFewResults();
		createdFiles.add(e.fileName);
		
		e.writeMany(results);
		
		List<String> expectedLines = Arrays.asList(createHeaderString(
				results.get(0).getParams().getParameterNamesAsList()),
				getDataRow(results.get(0).getParams().getParameterValuesAsList()),
				getDataRow(results.get(0).getParams().getParameterValuesAsList()),
				getDataRow(results.get(0).getParams().getParameterValuesAsList()));
		List<String> actualLines = Files.readAllLines(Paths.get(e.fileName));
		
		assertEquals(expectedLines, actualLines);
	}
	
	@Test
	public void shouldExportManyExperimentResultsIterativelyToOneFile() throws IOException, WriteException{
		CsvTuningExperimentResultExporter e = new CsvTuningExperimentResultExporter("tuningResMany");
		List<TuningExperimentResult> results = getFewResults();
		createdFiles.add(e.fileName);
		
		for(TuningExperimentResult res : results){
			e.write(res);
		}
		
		List<String> expectedLines = Arrays.asList(createHeaderString(
				results.get(0).getParams().getParameterNamesAsList()),
				getDataRow(results.get(0).getParams().getParameterValuesAsList()),
				getDataRow(results.get(0).getParams().getParameterValuesAsList()),
				getDataRow(results.get(0).getParams().getParameterValuesAsList()));
		List<String> actualLines = Files.readAllLines(Paths.get(e.fileName));
		
		assertEquals(expectedLines, actualLines);
	}
	
	private List<TuningExperimentResult> getFewResults() {
		TuningExperimentResult res = new TuningExperimentResult(new IwoParameters().setSaneDefaults()
				, 5d, 100, 15d);
		TuningExperimentResult res2 = new TuningExperimentResult(new IwoParameters().setSaneDefaults()
				, 5d, 100, 15d);
		TuningExperimentResult res3= new TuningExperimentResult(new IwoParameters().setSaneDefaults()
				, 5d, 100, 15d);
		List<TuningExperimentResult> results = Arrays.asList(res, res2, res3);
		return results;
	}

	private String getDataRow(List<? extends Object> params) {
		if(params == null || params.size() == 0){
			return "";
		}
		StringBuilder b = new StringBuilder();
		
		b.append(5d + CsvExporterUtil.CSV_DELIMITER + 100d + CsvExporterUtil.CSV_DELIMITER + 
		15d + CsvExporterUtil.CSV_DELIMITER);
		appendParametersToBuilder(params, b);
		
		return b.toString();
	}

	private void appendParametersToBuilder(List<? extends Object> params, StringBuilder b) {
		for(Object o : params.subList(0, params.size()-1)){
			b.append(o);
			b.append(CsvExporterUtil.CSV_DELIMITER);
		}
		b.append(params.get(params.size()-1));
	}
	
	private String createHeaderString(List<? extends Object> paramsNames) {
		if(paramsNames == null || paramsNames.size() == 0){
			return "";
		}
		
		StringBuilder b = new StringBuilder();
		b.append(CsvTuningExperimentResultExporter.ROUTE_LENGTH);
		b.append(CsvExporterUtil.CSV_DELIMITER);
		b.append(CsvTuningExperimentResultExporter.EXEC_TIME);
		b.append(CsvExporterUtil.CSV_DELIMITER);
		b.append(CsvTuningExperimentResultExporter.OBJ_FUN_VAL);
		b.append(CsvExporterUtil.CSV_DELIMITER);

		for(Object o : paramsNames.subList(0, paramsNames.size()-1)){
			b.append(o.toString());
			b.append(CsvExporterUtil.CSV_DELIMITER);
		}
		
		b.append(paramsNames.get(paramsNames.size()-1));
		return b.toString(); 
	}
	
	
	@After
	public void removeCreatedFiles() throws IOException{
		for(String file : createdFiles){
			Files.delete(Paths.get(file));
		}
	}
}
