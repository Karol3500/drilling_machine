package pl.edu.pwr.aic.dmp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by Karol on 2014-05-25.
 */
public class IwoCore extends Core {

	int metoda_selekcji;
	List<City> cities; // lista miast
	long start; // start licznika
	long stop; //stop licznika
	boolean detailedStatsOn,plotOn,mapOn;

	static final int SEL_INWERSJA = 1;
	static final int SEL_INVEROVER= 2;

	ArrayList<Specimen> populacja;
	Specimen bestOsobnik;
	double nonLinearCoefficient;
	double initStandardDeviation;
	double finalStandardDeviation;
	int minSeed;
	int maxSeed;
	int iloscOsobnikowWPopulacji;
	int maxIloscOsobnikowWPopulacji;
	int iloscIteracji;
	int bestPokolenie;
	int maksymalnaLiczbaZiaren;//na osobnika
	int dobry_wynik;
	double wynik_max;
	double wynik_min;
	double crossingProbability = 0.2;
	String message;

	IwoCore(MainPanel parent) {
		populacja = new ArrayList<Specimen>();

		iloscIteracji = Integer.parseInt(parent.iwoPanel.iterationCount.getText());
		iloscOsobnikowWPopulacji = Integer.parseInt(parent.iwoPanel.startWeedCount.getText());
		maxIloscOsobnikowWPopulacji = Integer.parseInt(parent.iwoPanel.maxWeedCount.getText());
		maksymalnaLiczbaZiaren = Integer.parseInt(parent.iwoPanel.maxSeedNumber.getText());
		maxSeed = Math.max(Integer.parseInt(parent.iwoPanel.maxSeedNumber.getText()), 5 );
		nonLinearCoefficient = Double.parseDouble(parent.iwoPanel.nonLinearCoefficient.getText());
		initStandardDeviation = Integer.parseInt(parent.iwoPanel.initStandardDeviation.getText());
		finalStandardDeviation = Integer.parseInt(parent.iwoPanel.finalStandardDeviation.getText());
		minSeed=maxSeed/5;

		if(parent.iwoPanel.srb1.isSelected()){
			metoda_selekcji = SEL_INWERSJA;
		} else if(parent.iwoPanel.srb2.isSelected()){
			metoda_selekcji = SEL_INVEROVER;
		}

		detailedStatsOn=parent.iwoPanel.statsOn.isSelected();
		plotOn=parent.iwoPanel.plotOn.isSelected();
		mapOn=parent.iwoPanel.mapOn.isSelected();

		abort = false;
		cities = new ArrayList<City>(parent.cities);

		Specimen zero=new Specimen(this);
		startCity= cities.get(0).clone();
		zero.setRoute(cities);
		zero.shuffleRoute();
		populacja.add(zero);
	}

	public void run() {
		start = System.currentTimeMillis(); // start licznika czasu
		initOsobniki(); // inicjalizuje poczatkowa populacje losowo

		Collections.sort(populacja);
		wynik_max = getMaxTrasa();
		wynik_min = getMinTrasa();
		bestOsobnik = populacja.get(0).clone();
		for (int pok = 0; !abort && pok < iloscIteracji; pok++) {//dopoki nie wykonano zadanej ilosci iteracji
			int distance = calculateDistance(pok,iloscIteracji);
			ArrayList<Specimen> newSpecimens = new ArrayList<>();
			for(int specIndex = 0; specIndex < populacja.size();specIndex++)//Dla kazdego osobnika w populacji
			{
				Specimen aktualny = populacja.get(specIndex);
				int seedNumber = calculateSeedNumber(specIndex,populacja);//oblicz  ilosc ziaren

				if(seedNumber> maxSeed)
					seedNumber = maxSeed;

				for(int childSpecimenCount = 0;childSpecimenCount<seedNumber;childSpecimenCount++)
				{
					Specimen childSpecimen = aktualny.clone();
					if(metoda_selekcji == SEL_INWERSJA) {
						for (int distanceCounter = 0; distanceCounter < distance; distanceCounter++) {
							childSpecimen.Inver();
						}
					}

					if(metoda_selekcji == SEL_INVEROVER){
						for (int distanceCounter = 0; distanceCounter < distance; distanceCounter++) {
							childSpecimen.InverOver(populacja,distance, crossingProbability);
						}
					}

					newSpecimens.add(childSpecimen);
				}
			}
			populacja.addAll(newSpecimens);
			newSpecimens.clear();
			Collections.sort(populacja);
			if(populacja.size()>maxIloscOsobnikowWPopulacji)
			{
				while(populacja.size()!=maxIloscOsobnikowWPopulacji)
				{
					populacja.remove(populacja.size()-1);
				}
			}
			if (wynik_max < getMaxTrasa()) {
				wynik_max = getMaxTrasa();
			}
			if (wynik_min > getMinTrasa()) {
				bestPokolenie=pok;
				bestOsobnik=populacja.get(0).clone();
				wynik_min = getMinTrasa();

			}

			if (detailedStatsOn) {
				String line = "Pokolenie #" + pok + " -> najlepsza: " + round(getMinTrasa(), 2) + " średnia: " + round(getAvgTrasa(), 2) + " najgorsza: " + round(getMaxTrasa(), 2);
				message += line;
			}
		}
		stop = System.currentTimeMillis(); // stop licznika czasu
		showEffects();
	}

	private int calculateDistance(int aktualnaIteracja, int iloscIteracji) {
		return (int)Math.round(Math.pow((iloscIteracji - aktualnaIteracja)/iloscIteracji,nonLinearCoefficient)*(initStandardDeviation-finalStandardDeviation)+finalStandardDeviation);
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

	int calculateSeedNumber(int specimenIndex, ArrayList<Specimen> populacja)
	{
		double specRate = (populacja.get(specimenIndex)).getRate();
		double minRate  = populacja.get(populacja.size()-1).getRate();
		double maxRate  = populacja.get(0).getRate();
		return (minSeed + (int)java.lang.Math.floor((minRate - specRate) * ((maxSeed - minSeed) / (minRate - maxRate))));
	}

	ArrayList<Specimen> clonePopulacja(){
		ArrayList<Specimen> wynik= new ArrayList<Specimen>();
		for(Specimen os:populacja){
			wynik.add(os.clone());
		}
		return wynik;
	}

	void initOsobniki() {
		Specimen zero=populacja.get(0);
		zero.shuffleRoute();
		//System.out.println("OSOBNIK SHUFFLE:"+zero.showTrasa());
		populacja=new ArrayList<Specimen>();
		populacja.add(zero);
		for (int i = 0; i < iloscOsobnikowWPopulacji; i++) {
			Specimen nowy = populacja.get(0).clone();
			nowy.shuffleRoute();
			populacja.add(nowy);
		}
	}

	protected void finish() {
		populacja = new ArrayList<Specimen>();
	}

	void printPopulacja() {

		for (int i = 0; i < populacja.size(); i++) {
			//+ populacja.get(i).showRoute()
			System.out.println("OSOBNIK " + i + "=" +" OCENA: "+round(populacja.get(i).getRate(),3)+ " PRAWD RULETKI: "+populacja.get(i).getP_Roulette());
		}
	}

	double getMinTrasa() {
		if(populacja.get(0).getRate()<1){
			System.out.println("ZERO TRASA:"+populacja.get(0).showRoute());
		}
		return populacja.get(0).getRate();
	}

	double getMaxTrasa() {
		return populacja.get(populacja.size() - 1).getRate();
	}

	double getAvgTrasa() {
		double srednia = 0;
		for (Specimen os : populacja) {
			srednia += os.getRate();
		}
		return (srednia / populacja.size());
	}



	public void showEffects() {
		// zapis wynik�w do logu i przebiegu
		addLine("============================================================================================================");
		addDate();
		String temp = "";
		String metoda ="";
		if (abort == true) {
			temp = "(z powodu przerwania w) ";
		}
		addLine(">>> Algorytm IWO "+metoda+" zakończył " + temp + "z wynikiem:");
		addPhrase("Czas pracy algorytmu: " + (stop-start)/1000.0+" s");
		newLine();
		addLine("Interwał wymiany wiertła: " + drillChangeInterval);
		addLine("Długość trasy: " + bestOsobnik.getRate());
		String tempS = "";
		addLine("Pokolenie w którym znaleziono najlepszego osobnika: " + bestPokolenie + tempS);
		addPhrase("Najlepsza trasa: " + bestOsobnik.showRoute());
		newLine();

		addLine("============================================================================================================");
		System.gc();
	}

	public void addPhrase(String s){
		message += s;
	}

	public void addDate(){
		message += new Date();
	}

	public void addLine(String s){
		addPhrase(s);
		newLine();
	}

	public void newLine(){
		message += "\n";
	}

	public double round(double d,int pos){
		return new BigDecimal(d).setScale(pos, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
