package pl.edu.pwr.aic.dmp.alg;

import java.util.Collections;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;

public class IwoCore extends Core {

	private IwoParameters params;

	public IwoCore() {
		algorithmName = "IWO Algorithm";
		population = getNewSpecimenList();
		abort = false;
	}

	@Override
	void runAlg() {
		params = (IwoParameters) algorithmParameters;
		population = initPopulation(params.getMinSpecimenInPopulation());
		Collections.sort(population);
		bestSpecimen = population.get(0).clone();
		for (int iter = 0; !abort && iter < params.getNumberOfIterations(); iter++) {
			int distance = calculateCurrentNumberOfTransformationsPerSeed(iter,params.getNumberOfIterations());
			List<Specimen> newSpecimens = getNewSpecimenList();
			for(int specIndex = 0; specIndex < population.size();specIndex++)
			{
				Specimen actual = population.get(specIndex);
				int seedNumber = Math.min(calculateSeedNumberOnSortedPopulation(specIndex,population), params.getMaxSeedNumber());
				for(int childrenCount = 0;childrenCount<seedNumber;childrenCount++)
				{
					Specimen children = actual.clone();
					for (int distanceCounter = 0; distanceCounter < distance; distanceCounter++) {
						children.inver(children);
					}

					newSpecimens.add(children);
				}
			}
			population.addAll(newSpecimens);
			performSelection(population);
			setBestSpecimenAndIterationIfFound(iter, population);
		}
	}

	private void performSelection(List<Specimen> population) {
		if(population.size()>params.getMaxSpecimenInPopulation())
		{
			Collections.shuffle(population);
			population = reduceShuffledPopulationUsingTournament(population, params.getMaxSpecimenInPopulation());
		}
	}

	private int calculateCurrentNumberOfTransformationsPerSeed(int currentIteration, int numberOfIterations) {
		return (int)Math.round(Math.pow((numberOfIterations - currentIteration)
				/numberOfIterations,params.getNonLinearCoefficient())
				*(params.getInitialTransformationsPerSeed()-params.getFinalTransformationsPerSeed())
				+params.getFinalTransformationsPerSeed());
	}

	private int calculateSeedNumberOnSortedPopulation(int specimenIndex, List<Specimen> population){
		double specRate = (population.get(specimenIndex)).getRouteLength();
		double minRate  = population.get(population.size()-1).getRouteLength();
		double maxRate  = population.get(0).getRouteLength();
		return params.getMinSeedNumber() + (int)java.lang.Math.floor((minRate - specRate) * 
				((params.getMaxSeedNumber() - params.getMinSeedNumber()) / (minRate - maxRate)));
	}

	@Override
	protected boolean areProperParametersGiven() {
		if (algorithmParameters != null && algorithmParameters instanceof IwoParameters)
			return true;
		return false;
	}

	@Override
	protected Core getNewInstance() {
		return new IwoCore();
	}
}
