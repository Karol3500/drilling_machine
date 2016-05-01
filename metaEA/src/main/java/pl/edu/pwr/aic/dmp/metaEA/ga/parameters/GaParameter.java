package pl.edu.pwr.aic.dmp.metaEA.ga.parameters;

public enum GaParameter{
	POPULATION_COUNT(1, 50, 2000),
	GENERATIONS_COUNT(1, 50, 2000),
	MUTATION_PROBABILITY(0,0.1,1),
	CROSSING_PROBABILITY(0,0.1,1);
	
	public double lowerBound;
	public double upperBound;
	public double changeStep;
	
	GaParameter(double lowerBound, double changeStep,double upperBound){
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.changeStep = changeStep;
	}
}
