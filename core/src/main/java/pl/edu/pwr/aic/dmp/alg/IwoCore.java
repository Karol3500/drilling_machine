package pl.edu.pwr.aic.dmp.alg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;


/**
 * Created by Karol on 2014-05-25.
 */
public class IwoCore extends Core {

	List<Specimen> population;
	int dobry_wynik;
	double wynik_max;
	double wynik_min;
	IwoParameters params;

	public IwoCore() {
		algorithmName = "IWO Algorithm";
		population = new ArrayList<Specimen>();
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
		wynik_max = getMaxRoute();
		wynik_min = getMinRoute();
		bestSpecimen = population.get(0).clone();
		for (int pok = 0; !abort && pok < params.getNumberOfIterations(); pok++) {
			int distance = calculateDistance(pok,params.getNumberOfIterations());
			ArrayList<Specimen> newSpecimens = new ArrayList<>();
			for(int specIndex = 0; specIndex < population.size();specIndex++)
			{
				Specimen aktualny = population.get(specIndex);
				int seedNumber = calculateSeedNumber(specIndex,population);
				seedNumber = seedNumber> params.getMaxSeedNumber() ? params.getMaxSeedNumber() : seedNumber;

				for(int childSpecimenCount = 0;childSpecimenCount<seedNumber;childSpecimenCount++)
				{
					Specimen childSpecimen = aktualny.clone();
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
			if (wynik_max < getMaxRoute()) {
				wynik_max = getMaxRoute();
			}
			if (wynik_min > getMinRoute()) {
				bestGeneration=pok;
				bestSpecimen=population.get(0).clone();
				wynik_min = getMinRoute();

			}
		}
	}

	private int calculateDistance(int aktualnaIteracja, int iloscIteracji) {
		return (int)Math.round(Math.pow((iloscIteracji - aktualnaIteracja)
				/iloscIteracji,params.getNonLinearCoefficient())
				*(params.getInitialStandardDeviation()-params.getFinalStandardDeviation())
				+params.getFinalStandardDeviation());
	}

	ArrayList<Double> calculateFitnessValue(ArrayList<Specimen> populacja)
	{
		ArrayList<Double> fitness = new ArrayList<>();
		for(Specimen individual : populacja)
		{
			fitness.add(individual.getRouteLength());
		}
		return fitness;
	}

	int calculateSeedNumber(int specimenIndex, List<Specimen> population){
		double specRate = (population.get(specimenIndex)).getRouteLength();
		double minRate  = population.get(population.size()-1).getRouteLength();
		double maxRate  = population.get(0).getRouteLength();
		return (params.getMinSeedNumber() + (int)java.lang.Math.floor((minRate - specRate) * 
				((params.getMaxSeedNumber() - params.getMinSeedNumber()) / (minRate - maxRate))));
	}

	ArrayList<Specimen> clonePopolation(){
		ArrayList<Specimen> result= new ArrayList<Specimen>();
		for(Specimen os:population){
			result.add(os.clone());
		}
		return result;
	}

	void initSpecimen() {
		Specimen zero=population.get(0);
		zero.shuffleRoute();
		population=new ArrayList<Specimen>();
		population.add(zero);
		for (int i = 0; i < params.getMinSpecimenInPopulation(); i++) {
			Specimen newSpecimen = population.get(0).clone();
			newSpecimen.shuffleRoute();
			population.add(newSpecimen);
		}
	}

	protected void finish() {
		population = new ArrayList<Specimen>();
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
