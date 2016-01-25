package pl.edu.pwr.aic.dmp.metaEA.ga.parameters;

public enum GaParameter{
	POPULATION_COUNT(1,5000),
	GENERATIONS_COUNT(1,5000),
	MUTATION_PROBABILITY(0,1),
	CROSSING_PROBABILITY(0,1);
	
	public double lowerBound;
	public double upperBound;
	
	GaParameter(double lowerBound, double upperBound){
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
}
