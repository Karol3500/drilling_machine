package pl.edu.pwr.aic.dmp.alg;

import java.util.Collections;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;

public class IwoCore extends Core {

	private double worstResult;
	private double bestResult;
	private IwoParameters params;

	public IwoCore() {
		algorithmName = "IWO Algorithm";
		population = getNewSpecimenList();
		abort = false;
	}

	@Override
	void runAlg() {
		params = (IwoParameters) algorithmParameters;
		startCity= cities.get(0).clone();
		Specimen zero=new Specimen(cities, startCity, drillChangeInterval);
		zero.shuffleRoute();
		population.add(zero);
		initSpecimen();

		Collections.sort(population);
		worstResult = getMaxRoute();
		bestResult = getMinRoute();
		bestSpecimen = population.get(0).clone();
		for (int iter = 0; !abort && iter < params.getNumberOfIterations(); iter++) {
			int distance = calculateCurrentNumberOfTransformationsPerSeed(iter,params.getNumberOfIterations());
			List<Specimen> newSpecimens = getNewSpecimenList();
			for(int specIndex = 0; specIndex < population.size();specIndex++)
			{
				Specimen actual = population.get(specIndex);
				int seedNumber = Math.min(calculateSeedNumber(specIndex,population), params.getMaxSeedNumber());
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
			performSelection();
			Collections.sort(population);
			if (worstResult < getMaxRoute()) {
				worstResult = getMaxRoute();
			}
			if (bestResult > getMinRoute()) {
				bestGeneration=iter;
				bestSpecimen=population.get(0).clone();
				bestResult = getMinRoute();
			}
		}
	}

	/**
	 * Watch out, sorts the population.
	 */
	private void performSelection() {
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

	/**
	 *  Important! For proper work requires population to be sorted.
	 */
	private int calculateSeedNumber(int specimenIndex, List<Specimen> population){
		double specRate = (population.get(specimenIndex)).getRouteLength();
		double minRate  = population.get(population.size()-1).getRouteLength();
		double maxRate  = population.get(0).getRouteLength();
		return params.getMinSeedNumber() + (int)java.lang.Math.floor((minRate - specRate) * 
				((params.getMaxSeedNumber() - params.getMinSeedNumber()) / (minRate - maxRate)));
	}

	private void initSpecimen() {
		Specimen zero=population.get(0);
		zero.shuffleRoute();
		population=getNewSpecimenList();
		population.add(zero);
		for (int i = 0; i < params.getMinSpecimenInPopulation(); i++) {
			Specimen newSpecimen = population.get(0).clone();
			newSpecimen.shuffleRoute();
			population.add(newSpecimen);
		}
	}

	private double getMinRoute() {
		return population.get(0).getRouteLength();
	}

	private double getMaxRoute() {
		return population.get(population.size() - 1).getRouteLength();
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
