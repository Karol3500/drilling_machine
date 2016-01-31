package pl.edu.pwr.aic.dmp.metaEA.export;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.write.WriteException;
import pl.edu.pwr.aic.dmp.metaEA.TuningExperimentResult;
import pl.edu.pwr.aic.dmp.utils.export.CsvExporterUtil;

public class CsvTuningExperimentResultExporter {

	protected static String ROUTE_LENGTH = "Route length";
	protected static String EXEC_TIME = "Execution time";
	protected static String OBJ_FUN_VAL = "Objective fun val";
	
	public String fileName;
	CsvExporterUtil util;
	
	public CsvTuningExperimentResultExporter(){
		util = new CsvExporterUtil();
	}

	public void createFile(String fileName) throws WriteException, IOException {
		this.fileName = util.createFile(fileName);
	}

	public void write(TuningExperimentResult res) throws IOException {
		writeHeader(res);
		util.writeRow(prepareDataRow(res), fileName);
	}

	private List<Object> prepareDataRow(TuningExperimentResult res) {
		List<Object> unitResultRow = new ArrayList<>();
		unitResultRow.add(res.getRouteLength());
		unitResultRow.add(res.getExecTime());
		unitResultRow.add(res.getObjectiveFunctionResult());
		unitResultRow.addAll(res.getParams().getParameterValuesAsList());
		return unitResultRow;
	}

	private void writeHeader(TuningExperimentResult res) throws IOException {
		List<Object> header = new ArrayList<>();
		header.add(ROUTE_LENGTH);
		header.add(EXEC_TIME);
		header.add(OBJ_FUN_VAL);
		for(Object o : res.getParams().getParameterNamesAsList()){
			header.add(o);
		}
		util.writeRow(header, fileName);
	}

	public void writeMany(List<TuningExperimentResult> results) throws IOException {
		if(results == null || results.size() == 0){
			return;
		}
		writeHeader(results.get(0));
		List<List<Object>> rowsData = new ArrayList<>();
		for(TuningExperimentResult res : results){
			rowsData.add(prepareDataRow(res));
		}
		util.writeRows(rowsData, fileName);
	}
}
