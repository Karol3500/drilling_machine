package pl.edu.pwr.aic.dmp.utils;

import pl.edu.pwr.aic.dmp.alg.SelectionMethod;
import pl.edu.pwr.aic.dmp.utils.Parameters;

public class GAParameters implements Parameters {

	int populationCount;
	int generationsCount;
	double mutationProbability;
	double crossingProbability;
	SelectionMethod selectionMethod = SelectionMethod.RANKING;
	
	@Override
	public void setSaneDefaults() {
		populationCount = 800;
		generationsCount = 400;
		crossingProbability = 0.65;
		mutationProbability = 0.2;
		selectionMethod = SelectionMethod.RANKING;
	}
	
	@Override
	public Object clone(){
		GAParameters clone = new GAParameters();
		clone.populationCount = populationCount;
		clone.generationsCount = generationsCount;
		clone.mutationProbability = mutationProbability;
		clone.crossingProbability = crossingProbability;
		clone.selectionMethod = selectionMethod;
		return clone;
	}

	public int getPopulationCount() {
		return populationCount;
	}

	public void setPopulationCount(int populationCount) {
		this.populationCount = populationCount;
	}

	public int getGenerationsCount() {
		return generationsCount;
	}

	public void setGenerationsCount(int specimenCount) {
		this.generationsCount = specimenCount;
	}

	public double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	public double getCrossingProbability() {
		return crossingProbability;
	}

	public void setCrossingProbability(double crossingProbability) {
		this.crossingProbability = crossingProbability;
	}

	public SelectionMethod getSelectionMethod() {
		return selectionMethod;
	}

	public void setSelectionMethod(SelectionMethod selectionMethod) {
		this.selectionMethod = selectionMethod;
	}
	
}
