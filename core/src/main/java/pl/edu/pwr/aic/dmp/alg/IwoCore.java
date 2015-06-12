package pl.edu.pwr.aic.dmp.alg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.utils.Machine;


/**
 * Created by Karol on 2014-05-25.
 */
public class IwoCore extends Core {

	List<Specimen> population;
	int dobry_wynik;
	double wynik_max;
	double wynik_min;
	IwoParameters params;

	public IwoCore(List<City> cities, boolean detailedStatistics, IwoParameters params, Machine m) {
		super(cities,detailedStatistics,m);
		setupParameters(params);
		population = new ArrayList<Specimen>();
		abort = false;

		Specimen zero=new Specimen(this);
		startCity= cities.get(0).clone();
		zero.setRoute(cities);
		zero.shuffleRoute();
		population.add(zero);
	}

	private void setupParameters(IwoParameters params) {
		if(params == null){
			this.params.setSaneDefaults();
		}
		else{
			this.params = params;
		}
	}

	public void run() {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("IWO STARTED");
		start = System.currentTimeMillis(); // start licznika czasu
		initOsobniki(); // inicjalizuje poczatkowa populacje losowo

		Collections.sort(population);
		wynik_max = getMaxTrasa();
		wynik_min = getMinTrasa();
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
						childSpecimen.Inver();
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
			if (wynik_max < getMaxTrasa()) {
				wynik_max = getMaxTrasa();
			}
			if (wynik_min > getMinTrasa()) {
				bestGeneration=pok;
				bestSpecimen=population.get(0).clone();
				wynik_min = getMinTrasa();

			}

			if (detailedStatsOn) {
				String line = "Pokolenie #" + pok + " -> najlepsza: " + round(getMinTrasa(), 2) + " średnia: " + round(getAvgTrasa(), 2) + " najgorsza: " + round(getMaxTrasa(), 2);
				message += line;
			}
		}
		stop = System.currentTimeMillis(); // stop licznika czasu
		result.setExecutionTimeInSeconds((stop-start)/1000d);
		showEffects();
		System.out.println("IWO FINISHED\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("Długość trasy: " + bestSpecimen.getRate());
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
			fitness.add(individual.getRate());
		}
		return fitness;
	}

	int calculateSeedNumber(int specimenIndex, List<Specimen> populacja){
		double specRate = (populacja.get(specimenIndex)).getRate();
		double minRate  = populacja.get(populacja.size()-1).getRate();
		double maxRate  = populacja.get(0).getRate();
		return (params.getMinSeedNumber() + (int)java.lang.Math.floor((minRate - specRate) * 
				((params.getMaxSeedNumber() - params.getMinSeedNumber()) / (minRate - maxRate))));
	}

	ArrayList<Specimen> clonePopulacja(){
		ArrayList<Specimen> wynik= new ArrayList<Specimen>();
		for(Specimen os:population){
			wynik.add(os.clone());
		}
		return wynik;
	}

	void initOsobniki() {
		Specimen zero=population.get(0);
		zero.shuffleRoute();
		population=new ArrayList<Specimen>();
		population.add(zero);
		for (int i = 0; i < params.getMinSpecimenInPopulation(); i++) {
			Specimen nowy = population.get(0).clone();
			nowy.shuffleRoute();
			population.add(nowy);
		}
	}

	protected void finish() {
		population = new ArrayList<Specimen>();
	}

	void printPopulation() {

		for (int i = 0; i < population.size(); i++) {
			System.out.println("SPECIMEN " + i + "=" +" MARK: "+round(population.get(i).getRate(),3)+ " ROULETTE PROBABILITY: "+population.get(i).getP_Roulette());
		}
	}

	double getMinTrasa() {
		if(population.get(0).getRate()<1){
			System.out.println("ZERO ROUTE:"+population.get(0).showRoute());
		}
		return population.get(0).getRate();
	}

	double getMaxTrasa() {
		return population.get(population.size() - 1).getRate();
	}

	double getAvgTrasa() {
		double srednia = 0;
		for (Specimen os : population) {
			srednia += os.getRate();
		}
		return (srednia / population.size());
	}

	public double round(double d,int pos){
		return new BigDecimal(d).setScale(pos, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
