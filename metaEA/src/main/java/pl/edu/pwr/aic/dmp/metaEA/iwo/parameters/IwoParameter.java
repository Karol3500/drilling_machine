package pl.edu.pwr.aic.dmp.metaEA.iwo.parameters;

public enum IwoParameter{
	NUMBER_OF_ITERATIONS(1, 50, 2000),
	MIN_SPECIMEN_IN_POPULATION(1, 50, 2000),
	MAX_SPECIMEN_IN_POPULATION(1, 50, 2000),
	MIN_SEED_NUMBER(1, 50, 1000),
	MAX_SEED_NUMBER(1,50,1000),
	NON_LINEAR_COEFFICIENT(0.1,0.2,2),
	INIT_TRANSFORMATIONS_PER_SEED(1, 15, 1000),
	FINAL_TRANSFORMATIONS_PER_SEED(1, 15, 1000);
	
	public double lowerBound;
	public double upperBound;
	public double changeStep;
	
	IwoParameter(double lowerBound, double changeStep, double upperBound){
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.changeStep = changeStep;
	}
}
