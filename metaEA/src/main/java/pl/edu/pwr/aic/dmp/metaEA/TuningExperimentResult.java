package pl.edu.pwr.aic.dmp.metaEA;

import pl.edu.pwr.aic.dmp.alg.utils.Parameters;

public class TuningExperimentResult {
	
	Parameters params;
	double routeLength;
	double execTime;
	double objectiveFunctionResult;
	
	public TuningExperimentResult(Parameters algParameters,
			double routeLength, double execTimeInMs, double objectiveFunctionResult){
		this.params = algParameters;
		this.routeLength = routeLength;
		this.execTime = execTimeInMs;
		this.objectiveFunctionResult = objectiveFunctionResult;
	}
	
	@Override
	public String toString(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Parameters: \n");
		stringBuilder.append(params);
		stringBuilder.append("\n\nRoute length: ");
		stringBuilder.append(routeLength);
		stringBuilder.append("\nExecution time: ");
		stringBuilder.append(execTime);
		stringBuilder.append("\nObjective function result: ");
		stringBuilder.append(objectiveFunctionResult);
		return stringBuilder.toString();
	}

	public Parameters getParams() {
		return params;
	}

	public void setParams(Parameters params) {
		this.params = params;
	}

	public double getExecTime() {
		return execTime;
	}

	public void setExecTime(long execTime) {
		this.execTime = execTime;
	}

	public double getObjectiveFunctionResult() {
		return objectiveFunctionResult;
	}

	public void setObjectiveFunctionResult(long objectiveFunctionResult) {
		this.objectiveFunctionResult = objectiveFunctionResult;
	}

	public double getRouteLength() {
		return routeLength;
	}

	public void setRouteLength(long routeLength) {
		this.routeLength = routeLength;
	}
}
