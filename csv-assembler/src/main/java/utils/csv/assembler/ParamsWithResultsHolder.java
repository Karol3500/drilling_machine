package utils.csv.assembler;

import java.util.List;

public class ParamsWithResultsHolder implements Comparable<ParamsWithResultsHolder>{
	String parameters;
	List<SingleResult> results;
	
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public List<SingleResult> getResults() {
		return results;
	}
	public void setResults(List<SingleResult> results) {
		this.results = results;
	}
	@Override
	public int compareTo(ParamsWithResultsHolder o) {
		return o.getParameters().compareTo(parameters);
	}
	
}
