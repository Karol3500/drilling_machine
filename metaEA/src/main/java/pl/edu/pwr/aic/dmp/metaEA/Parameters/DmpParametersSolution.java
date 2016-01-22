package pl.edu.pwr.aic.dmp.metaEA.Parameters;

import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.utils.Parameters;

public class DmpParametersSolution implements Solution {

	private static final long serialVersionUID = 1L;

	Parameters parameters;
	Core algorithm;
	
	double[] objectiveValue;
	
	public DmpParametersSolution(Core alg, Parameters params){
		this.algorithm = alg;
		this.parameters = params;
		objectiveValue = new double[2];
	}
	
	@Override
	public double[] getObjectiveValue() {
		return objectiveValue;
	}

	@Override
	public void setObjectiveValue(double[] objValue) {
		this.objectiveValue = objValue;
	}
	
	@Override
	public Object clone(){
		return parameters.clone();
	}

}
