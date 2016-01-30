package pl.edu.pwr.aic.dmp.metaEA.parameters;

import org.coinor.opents.SolutionAdapter;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.utils.Parameters;

public class DmpParametersSolution extends SolutionAdapter{

	private static final long serialVersionUID = 1L;

	Parameters parameters;
	Core algorithm;
	
	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	public Core getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Core algorithm) {
		this.algorithm = algorithm;
	}
	
	public DmpParametersSolution(Core alg, Parameters params){
		this.algorithm = alg;
		this.parameters = params;
	}
	
	@Override
	public Object clone(){
		DmpParametersSolution dmpParametersSolution = new DmpParametersSolution((Core)algorithm.clone(), (Parameters)parameters.clone());
		dmpParametersSolution.setObjectiveValue(getClonedObjValOrNull());
		return dmpParametersSolution;
	}

	protected double[] getClonedObjValOrNull() {
		return this.getObjectiveValue() != null ? this.getObjectiveValue().clone() : null;
	}
	
	@Override
	public String toString(){
		return parameters.toString();
	}
}
