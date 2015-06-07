package pl.edu.pwr.aic.dmp.alg.utils;

import pl.edu.pwr.aic.dmp.utils.Parameters;

public class IwoParameters implements Parameters {
	
	private int numberOfIterations;
	private int minSpecimenInPopulation;
	private int maxSpecimenInPopulation;
	private int minSeedNumber;
	private int maxSeedNumber;
	private double nonLinearCoefficient;
	private int initialStandardDeviation;
	private int finalStandardDeviation;

	@Override
	public void setSaneDefaults(){
		minSpecimenInPopulation = 10;
		maxSpecimenInPopulation = 100;
		numberOfIterations = 100;
		maxSeedNumber = 10;
		minSeedNumber = 2;
		nonLinearCoefficient = 1;
		initialStandardDeviation = 15;
		finalStandardDeviation = 2;
	}
	
	public int getNumberOfIterations() {
		return numberOfIterations;
	}
	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}
	public int getMinSpecimenInPopulation() {
		return minSpecimenInPopulation;
	}
	public void setMinSpecimenInPopulation(int minSpecimenInPopulation) {
		this.minSpecimenInPopulation = minSpecimenInPopulation;
	}
	public int getMaxSpecimenInPopulation() {
		return maxSpecimenInPopulation;
	}
	public void setMaxSpecimenInPopulation(int maxSpecimenInPopulation) {
		this.maxSpecimenInPopulation = maxSpecimenInPopulation;
	}
	public int getMinSeedNumber() {
		return minSeedNumber;
	}
	public void setMinSeedNumber(int minSeedNumber) {
		this.minSeedNumber = minSeedNumber;
	}
	public int getMaxSeedNumber() {
		return maxSeedNumber;
	}
	public void setMaxSeedNumber(int maxSeedNumber) {
		this.maxSeedNumber = maxSeedNumber;
	}
	public double getNonLinearCoefficient() {
		return nonLinearCoefficient;
	}
	public void setNonLinearCoefficient(double nonLinearCoefficient) {
		this.nonLinearCoefficient = nonLinearCoefficient;
	}
	public int getInitialStandardDeviation() {
		return initialStandardDeviation;
	}
	public void setInitialStandardDeviation(int initialStandardDeviation) {
		this.initialStandardDeviation = initialStandardDeviation;
	}
	public int getFinalStandardDeviation() {
		return finalStandardDeviation;
	}
	public void setFinalStandardDeviation(int finalStandardDeviation) {
		this.finalStandardDeviation = finalStandardDeviation;
	}
}
