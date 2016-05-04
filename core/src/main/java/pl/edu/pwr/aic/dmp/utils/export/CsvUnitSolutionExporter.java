package pl.edu.pwr.aic.dmp.utils.export;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.utils.UnitResult;

public class CsvUnitSolutionExporter {

	protected static final String BEST_ROUTE_LENGTH = "Best route length";
	protected static final String EXEC_TIME = "Execution time[s]";
	protected static final String PERMUTATION = "Permutation";
	
	private CsvExporterUtil util;
	protected String fileName;
	
	public CsvUnitSolutionExporter(){
		util = new CsvExporterUtil();
	}
	
	public void createNewFile(String name) throws WriteException, IOException {
		fileName = util.createFile(name);
	}

	public void writeRow(List<Object> row) throws IOException {
		util.writeRow(row, fileName);
	}
	
	public void writeEmptyRow() throws IOException {
		util.writeRow(Arrays.asList("\n"), fileName);
	}

	public void writeUnitResult(UnitResult res) throws IOException {
		writeHeader();
		List<Object> dataRow = prepareDataRow(res);
		util.writeRow(dataRow, fileName);
	}

	private List<Object> prepareDataRow(UnitResult res) {
		List<Object> unitResultRow = new ArrayList<>();
		unitResultRow.add(res.getBestRouteLength());
		unitResultRow.add(res.getExecutionTimeInSeconds());
		unitResultRow.add(getStringPermutationRepresentation(res.getBestRoute()));
		return unitResultRow;
	}

	private String getStringPermutationRepresentation(List<Integer> permutation){
		if(permutation == null || permutation.isEmpty()){
			return "";
		}
		StringBuilder b = new StringBuilder();
		for(Integer i : permutation.subList(0, permutation.size()-1)){
			b.append(i);
			b.append(" ");
		}
		b.append(permutation.get(permutation.size()-1));
		return b.toString();
	}

	private void writeHeader() throws IOException {
		List<Object> unitResultCsvHeader = Arrays.asList(BEST_ROUTE_LENGTH, EXEC_TIME, PERMUTATION);
		util.writeRow(unitResultCsvHeader, fileName);
	}

	public void writeUnitResults(List<UnitResult> results) throws IOException {
		writeHeader();
		List<List<Object>> rows = new ArrayList<>();
		for(UnitResult u : results){
			rows.add(prepareDataRow(u));
		}
		util.writeRows(rows, fileName);
	}
}
