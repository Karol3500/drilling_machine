//package pl.edu.pwr.aic.dmp.alg;
//
//import java.util.Collections;
//import java.util.List;
//
//import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
//
//public class IwoCore extends Core {
//
//	private IwoParameters params;
//
//	public IwoCore() {
//		algorithmName = "IWO";
//		population = getNewSpecimenList();
//	}
//
//	@Override
//	void runAlg() {
//		params = (IwoParameters) algorithmParameters;
//		population = initPopulation(params.getMinSpecimenInPopulation());
//		Collections.sort(population);
//		bestSpecimen = population.get(0).clone();
//		for (int iter = 0; iter < params.getNumberOfIterations(); iter++) {
//			int distance = calculateCurrentNumberOfTransformationsPerSeed(iter,params.getNumberOfIterations());
//			List<Specimen> newSpecimens = getNewSpecimenList();
//			for(int specIndex = 0; specIndex < population.size();specIndex++)
//			{
//				Specimen actual = population.get(specIndex);
//				int seedNumber = Math.min(calculateSeedNumberOnSortedPopulation(specIndex,population), params.getMaxSeedNumber());
//				for(int childrenCount = 0;childrenCount<seedNumber;childrenCount++)
//				{
//					Specimen children = actual.clone();
//					for (int distanceCounter = 0; distanceCounter < distance; distanceCounter++) {
//						children.inver(children);
//					}
//
//					newSpecimens.add(children);
//				}
//			}
//			population.addAll(newSpecimens);
//			performSelection(population);
//			setBestSpecimenAndIterationIfFound(iter, population);
//		}
//	}
//
//	private void performSelection(List<Specimen> population) {
//		if(population.size()>params.getMaxSpecimenInPopulation())
//		{
//			Collections.shuffle(population);
//			population = reduceShuffledPopulationUsingTournament(population, params.getMaxSpecimenInPopulation());
//		}
//	}
//
//	private int calculateCurrentNumberOfTransformationsPerSeed(int currentIteration, int numberOfIterations) {
//		return (int)Math.round(Math.pow((numberOfIterations - currentIteration)
//				/numberOfIterations,params.getNonLinearCoefficient())
//				*(params.getInitialTransformationsPerSeed()-params.getFinalTransformationsPerSeed())
//				+params.getFinalTransformationsPerSeed());
//	}
//
//	private int calculateSeedNumberOnSortedPopulation(int specimenIndex, List<Specimen> population){
//		double specRate = (population.get(specimenIndex)).getRouteLength();
//		double minRate  = population.get(population.size()-1).getRouteLength();
//		double maxRate  = population.get(0).getRouteLength();
//		return params.getMinSeedNumber() + (int)java.lang.Math.floor((minRate - specRate) * 
//				((params.getMaxSeedNumber() - params.getMinSeedNumber()) / (minRate - maxRate)));
//	}
//
//	@Override
//	protected boolean areProperParametersGiven() {
//		if (algorithmParameters != null && algorithmParameters instanceof IwoParameters)
//			return true;
//		return false;
//	}
//
//	@Override
//	protected Core getNewInstance() {
//		return new IwoCore();
//	}
//}


package pl.edu.pwr.aic.dmp.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;

public class IwoCore extends Core {

	IwoParameters params;

	private int calculateDistance(int iteration, int totalIterationsNumber) {
		return (int)Math.round(Math.pow((totalIterationsNumber - iteration)
				/totalIterationsNumber,params.getNonLinearCoefficient())
				*(params.getInitialTransformationsPerSeed()-params.getFinalTransformationsPerSeed())
				+params.getFinalTransformationsPerSeed());
	}

	int calculateSeedNumber(Specimen spec, List<Specimen> populacja){
		double specRate = spec.getRouteLength();
		double minRate  = populacja.get(populacja.size()-1).getRouteLength();
		double maxRate  = populacja.get(0).getRouteLength();
		return (params.getMinSeedNumber() + (int)java.lang.Math.floor((minRate - specRate) * 
				((params.getMaxSeedNumber() - params.getMinSeedNumber()) / (minRate - maxRate))));
	}


	double getMinTrasa() {
		return population.get(0).getRouteLength();
	}

	double getMaxTrasa() {
		return population.get(population.size() - 1).getRouteLength();
	}

	@Override
	void runAlg() {
		bestRouteLength = Double.MAX_VALUE;
		params = (IwoParameters) algorithmParameters;
		population = initPopulation(params.getMinSpecimenInPopulation());

		Collections.sort(population);
		bestSpecimen = population.get(0).clone();
		for (int pok = 0; pok < params.getNumberOfIterations(); pok++) {
			int distance = calculateDistance(pok,params.getNumberOfIterations());
			ArrayList<Specimen> newSpecimens = new ArrayList<>();
			for(Specimen spec : population){
				int seedNumber = calculateSeedNumber(spec,population);
				seedNumber = seedNumber> params.getMaxSeedNumber() ? params.getMaxSeedNumber() : seedNumber;

				for(int childSpecimenCount = 0;childSpecimenCount<seedNumber;childSpecimenCount++)
				{
					Specimen childSpecimen = spec.clone();
					for (int distanceCounter = 0; distanceCounter < distance; distanceCounter++) {
						childSpecimen.inver(childSpecimen);
					}

					newSpecimens.add(childSpecimen);
				}
			}
			population.addAll(newSpecimens);
			newSpecimens.clear();
			Collections.sort(population);
			if(population.size()>params.getMaxSpecimenInPopulation())
			{
				while(population.size()!=params.getMaxSpecimenInPopulation())
				{
					population.remove(population.size()-1);
				}
			}
			if (bestRouteLength > getMinTrasa()) {
				bestGeneration=pok;
				bestSpecimen=population.get(0).clone();
				bestRouteLength = getMinTrasa();

			}
		}
	}

	@Override
	protected Core getNewInstance() {
		return new IwoCore();
	}

	@Override
	protected boolean areProperParametersGiven() {
		if (algorithmParameters != null && algorithmParameters instanceof IwoParameters)
			return true;
		return false;
	}
}
