package pl.edu.pwr.aic.dmp.utils;

import pl.edu.pwr.aic.dmp.alg.SelectionMethod;
import pl.edu.pwr.aic.dmp.utils.Parameters;

public class GaParameters implements Parameters {

	int populationCount;
	int generationsCount;
	double mutationProbability;
	double crossingProbability;
	SelectionMethod selectionMethod = SelectionMethod.TOURNAMENT;
	
	@Override
	public Parameters setSaneDefaults() {
		populationCount = 800;
		generationsCount = 400;
		crossingProbability = 0.65;
		mutationProbability = 0.2;
		selectionMethod = SelectionMethod.RANKING;
		return this;
	}
	
	@Override
	public Object clone(){
		GaParameters clone = new GaParameters();
		clone.populationCount = populationCount;
		clone.generationsCount = generationsCount;
		clone.mutationProbability = mutationProbability;
		clone.crossingProbability = crossingProbability;
		clone.selectionMethod = selectionMethod;
		return clone;
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("populationCount: " + populationCount + "\n");
		s.append("generationsCount: " + generationsCount + "\n");
		s.append("mutationProbability: " + mutationProbability + "\n");
		s.append("crossingProbability: " + crossingProbability + "\n");
		s.append("selectionMethod: " + selectionMethod);
		return s.toString();
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof GaParameters)){
			return false;
		}
		GaParameters comp = (GaParameters) obj;
		if(comp.getCrossingProbability() != crossingProbability||
				comp.getGenerationsCount() != generationsCount||
				comp.getMutationProbability() != mutationProbability||
				comp.getPopulationCount() != populationCount||
				comp.getSelectionMethod() != selectionMethod)
			return false;
		return true;
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
