package pl.edu.pwr.aic.dmp.alg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.edu.pwr.aic.dmp.utils.IwoParameters;

public class IwoCore extends Core {

	int dobry_wynik;
	double worstResult;
	double bestResult;
	IwoParameters params;

	public IwoCore() {
		algorithmName = "IWO Algorithm";
		population = getNewSpecimenList();
		abort = false;
	}

	@Override
	void runAlg() {
		params = (IwoParameters) algorithmParameters;
		Specimen zero=new Specimen(this);
		startCity= cities.get(0).clone();
		zero.setRoute(cities);
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

	List<Double> calculateFitnessValue(List<Specimen> population)
	{
		List<Double> fitness = new ArrayList<>(population.size());
		for(Specimen individual : population)
		{
			fitness.add(individual.getRouteLength());
		}
		return fitness;
	}

	int calculateSeedNumber(int specimenIndex, List<Specimen> population){
		double specRate = (population.get(specimenIndex)).getRouteLength();
		double minRate  = population.get(population.size()-1).getRouteLength();
		double maxRate  = population.get(0).getRouteLength();
		return params.getMinSeedNumber() + (int)java.lang.Math.floor((minRate - specRate) * 
				((params.getMaxSeedNumber() - params.getMinSeedNumber()) / (minRate - maxRate)));
	}

	List<Specimen> clonePopolation(){
		List<Specimen> result= getNewSpecimenList();
		for(Specimen os:population){
			result.add(os.clone());
		}
		return result;
	}

	void initSpecimen() {
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

	void printPopulation() {
		for (int i = 0; i < population.size(); i++) {
			System.out.println("SPECIMEN " + i + "=" +" MARK: "+round(population.get(i).getRouteLength(),3)+ " ROULETTE PROBABILITY: "+population.get(i).getP_Roulette());
		}
	}

	double getMinRoute() {
		return population.get(0).getRouteLength();
	}

	double getMaxRoute() {
		return population.get(population.size() - 1).getRouteLength();
	}

	double getAvgRoute() {
		double meanRate = 0;
		for (Specimen spec : population) {
			meanRate += spec.getRouteLength();
		}
		return (meanRate / population.size());
	}

	public double round(double d,int pos){
		return new BigDecimal(d).setScale(pos, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	@Override
	protected boolean areProperParametersGiven() {
		if (algorithmParameters != null && algorithmParameters instanceof IwoParameters)
			return true;
		return false;
	}
}
