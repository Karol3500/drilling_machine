package pl.edu.pwr.aic.dmp.metaEA.sa.parameters;

public enum SaParameter{
	CYCLES_NUMBER(1,50,5000),
	START_TEMPERATURE(0,50,2000),
	COOLING_COEFFICIENT(0,0.1,1),
	PERMUTATION_ATTEMPTS(1,5,100);
	
	public double lowerBound;
	public double upperBound;
	public double changeStep;
	
	SaParameter(double lowerBound, double changeStep, double upperBound){
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.changeStep = changeStep;
	}
}
