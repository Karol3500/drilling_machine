package pl.edu.pwr.aic.dmp.utils;

import pl.edu.pwr.aic.dmp.utils.Parameters;

public class IwoParameters implements Parameters {

	private int numberOfIterations;
	private int minSpecimenInPopulation;
	private int maxSpecimenInPopulation;
	private int minSeedNumber;
	private int maxSeedNumber;
	private double nonLinearCoefficient;
	private int initialTransformationsPerSeed;
	private int finalTransformationsPerSeed;

	@Override
	public Parameters setSaneDefaults(){
		minSpecimenInPopulation = 10;
		maxSpecimenInPopulation = 100;
		numberOfIterations = 100;
		maxSeedNumber = 10;
		minSeedNumber = 2;
		nonLinearCoefficient = 1;
		initialTransformationsPerSeed = 15;
		finalTransformationsPerSeed = 2;
		return this;
	}

	@Override
	public Object clone(){
		IwoParameters clone = new IwoParameters();
		clone.numberOfIterations = numberOfIterations;
		clone.minSpecimenInPopulation = minSpecimenInPopulation;
		clone.maxSpecimenInPopulation = maxSpecimenInPopulation;
		clone.minSeedNumber = minSeedNumber;
		clone.maxSeedNumber = maxSeedNumber;
		clone.nonLinearCoefficient = nonLinearCoefficient;
		clone.initialTransformationsPerSeed = initialTransformationsPerSeed;
		clone.finalTransformationsPerSeed = finalTransformationsPerSeed;
		return clone;
	}

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("Number of iterations: " + numberOfIterations + "\n");
		sb.append(" Min specimen in population: " + minSpecimenInPopulation + "\n");
		sb.append("Max specimen in population: " + maxSpecimenInPopulation + "\n");
		sb.append("Min seed number: " + minSeedNumber + "\n");
		sb.append("Max seed number: " + maxSeedNumber+ "\n");
		sb.append("Nonlinear coefficient: " + nonLinearCoefficient+ "\n");
		sb.append("Initial transform per seed: " + initialTransformationsPerSeed + "\n");
		sb.append("Final transform per seed: " + finalTransformationsPerSeed);
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof IwoParameters)){
			return false;
		}
		IwoParameters comp = (IwoParameters) obj;
		if(comp.getFinalTransformationsPerSeed() != finalTransformationsPerSeed ||
				comp.getInitialTransformationsPerSeed() != initialTransformationsPerSeed||
				comp.getMaxSeedNumber() != maxSeedNumber||
				comp.getMaxSpecimenInPopulation() != maxSpecimenInPopulation||
				comp.getMinSeedNumber() != minSeedNumber||
				comp.getMinSpecimenInPopulation() != minSpecimenInPopulation||
				comp.getNonLinearCoefficient() != nonLinearCoefficient||
				comp.getNumberOfIterations() != numberOfIterations)
			return false;
		return true;
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
	public int getInitialTransformationsPerSeed() {
		return initialTransformationsPerSeed;
	}
	public void setInitialTransformationsPerSeed(int initialTransformations) {
		this.initialTransformationsPerSeed = initialTransformations;
	}
	public int getFinalTransformationsPerSeed() {
		return finalTransformationsPerSeed;
	}
	public void setFinalTransformationsPerSeed(int finalTransformations) {
		this.finalTransformationsPerSeed = finalTransformations;
	}
}
