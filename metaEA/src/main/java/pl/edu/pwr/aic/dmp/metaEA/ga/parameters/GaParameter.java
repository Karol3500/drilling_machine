package pl.edu.pwr.aic.dmp.metaEA.ga.parameters;

public enum GaParameter{
	NUMBER_OF_ITERATIONS(1,2000),
	MIN_SPECIMEN_IN_POPULATION(1,2000),
	MAX_SPECIMEN_IN_POPULATION(1,2000),
	MIN_SEED_NUMBER(1,1000),
	MAX_SEED_NUMBER(1,1000),
	NON_LINEAR_COEFFICIENT(0.1,2),
	INIT_TRANSFORMATIONS_PER_SEED(1, 1000),
	FINAL_TRANSFORMATIONS_PER_SEED(1, 1000);
	
	public double lowerBound;
	public double upperBound;
	
	GaParameter(double lowerBound, double upperBound){
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
}
