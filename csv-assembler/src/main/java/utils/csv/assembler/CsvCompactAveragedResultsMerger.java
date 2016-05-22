package utils.csv.assembler;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CsvCompactAveragedResultsMerger {
	private static final String LINE_END = "\\\\";

	private static final String HLINE = "\n\\hline\n";

	private static final String CSV_SPLITTER = " \\| ";
	
	private static final String TABLE_SEPARATOR = " & ";
	
	CsvFileReadingUtil util = new CsvFileReadingUtil();
	
	public static void main(String args[]){
		args = new String[]{};
		
		CsvCompactAveragedResultsMerger merger = new CsvCompactAveragedResultsMerger();
		List<ParamsWithResultsHolder> holders = merger.readAllParamsAndResults(args);
		String table = merger.getParamsAndResultsFromHoldersAsLatex(holders);
		System.out.println(table);
	}
	
	public List<String> readParameters(String[] filePaths) {
		List<String> parameters = new ArrayList<>();
		
		for(String path : filePaths){
			List<String> allFileLines = util.readFileLines(path);
			List<String> parameterLines = util.getLinesContainingAlgorithmParameters(allFileLines);
			parameters.add(convertParametersLinesIntoSingleLine(parameterLines));
		}
		return parameters;
	}

	private String convertParametersLinesIntoSingleLine(List<String> parameterLines) {
		StringBuilder builder = new StringBuilder();
		for(String params : parameterLines.subList(0, parameterLines.size()-1)){
			if(params.equals("Algorithm parameters:")){
				continue;
			}
			builder.append(convertParametersLineIntoValuesLine(params));
			builder.append(" ");
		}
		builder.append(convertParametersLineIntoValuesLine(parameterLines.get(parameterLines.size()-1)));
		return builder.toString();
	}

	protected String convertParametersLineIntoValuesLine(String parameterLines) {
		String[] splitted = parameterLines.split(CSV_SPLITTER);
		return splitted[1].replace(".0", "");
	}

	public List<ParamsWithResultsHolder> readAllParamsAndResults(String[] filePaths) {
		List<ParamsWithResultsHolder> res = new ArrayList<>();
		for(String p : filePaths){
			ParamsWithResultsHolder h = new ParamsWithResultsHolder();
			List<String> allLines = util.readFileLines(p);
			h.setParameters(convertParametersLinesIntoSingleLine(util.getLinesContainingAlgorithmParameters(allLines)));
			List<String> linesContainingResults = util.getLinesContainingResults(allLines);
			h.setResults(convertStringsToResults(linesContainingResults));
			res.add(h);
		}
		return res;
	}
	
	protected List<SingleResult> convertStringsToResults(List<String> linesContainingResults) {
		List<SingleResult> res = new ArrayList<SingleResult>();
		for(String result : linesContainingResults.subList(1, linesContainingResults.size())){
			res.add(convertStringToResult(result));
		}
		return res; 
	}

	private SingleResult convertStringToResult(String result) {
		String[] params = result.split(CSV_SPLITTER);
		return new SingleResult(Double.valueOf(params[0]), Double.valueOf(params[1]));
	}

	public String getParamsAndResultsFromHoldersAsLatex(List<ParamsWithResultsHolder> holders) {
		StringBuilder tex = new StringBuilder();
		tex.append("\\begin{longtable}{|c|c|c|c|c|c|}");
		tex.append(HLINE);
		tex.append("\\cellcolor{lightgray}ParamId &" + "\\cellcolor{lightgray}Best route lenght &" +
				"\\cellcolor{lightgray}Worst route length &" + "\\cellcolor{lightgray}Avg route length &" + 
				"\\cellcolor{lightgray}Avg exec time &" + "\\cellcolor{lightgray}D");
		tex.append(LINE_END);
		tex.append(HLINE);
		for(ParamsWithResultsHolder h : holders){
			tex.append(getResultsLine(h, holders.indexOf(h)));
			tex.append(LINE_END);
			tex.append(HLINE);
		}
		tex.append("\\end{longtable}");
		System.out.println(tex.toString());
		return tex.toString();
	}

	private String getResultsLine(ParamsWithResultsHolder h, int indexOfHolder) {
		DecimalFormatSymbols s = new DecimalFormatSymbols(Locale.getDefault());
		s.setDecimalSeparator('.');
		DecimalFormat f = new DecimalFormat("0.00", s);
		StringBuilder res = new StringBuilder();
		res.append(indexOfHolder + 1);
		
		List<SingleResult> results = h.getResults();
		double bestRoute = Double.MAX_VALUE;
		double worstRoute = 0d;
		double avgRoute = 0d;
		double avgExecTime = 0d;
		double d = 0d;
		for(SingleResult r : results){
			if(r.getRoute() < bestRoute){
				bestRoute = r.getRoute();
			}
			if(r.getRoute() > worstRoute){
				worstRoute = r.getRoute();
			}
			avgRoute += r.getRoute();
			avgExecTime += r.getExecTime();
		}
		avgRoute /= results.size();
		avgExecTime /= results.size();
		d=bestRoute * avgExecTime;
		res.append(TABLE_SEPARATOR);
		res.append(f.format(bestRoute));
		res.append(TABLE_SEPARATOR);
		res.append(f.format(worstRoute));
		res.append(TABLE_SEPARATOR);
		res.append(f.format(avgRoute));
		res.append(TABLE_SEPARATOR);
		res.append(f.format(avgExecTime));
		res.append(TABLE_SEPARATOR);
		res.append(f.format(d));
		return res.toString();
	}
}
