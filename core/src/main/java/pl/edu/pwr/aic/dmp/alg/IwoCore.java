package pl.edu.pwr.aic.dmp.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;

public class IwoCore extends Core {
	
	IwoParameters params;
	
	public IwoCore(){
		algorithmName = "IWO";
	}

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
