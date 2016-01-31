package pl.edu.pwr.aic.dmp.alg;

import java.util.ArrayList;
import java.util.Collections;

import pl.edu.pwr.aic.dmp.alg.utils.GaParameters;
import pl.edu.pwr.aic.dmp.alg.utils.SelectionMethod;

public class GACore extends Core{

	private GaParameters params;
	private int rankingIter;
	private int rankingCount;
	private double maxRoute;
	private double minRoute;
	
	public GACore() {
		algorithmName = "Genetic Algorthm";
		population = new ArrayList<Specimen>();
		tournament = new ArrayList<Specimen>();
		ranking = new ArrayList<Specimen>();
		abort = false;
	}

	@Override
	void runAlg() {
		params = (GaParameters)algorithmParameters;
		Specimen zero=new Specimen(cities, startCity, drillChangeInterval);
		startCity= cities.get(0).clone();
		zero.shuffleRoute();
		population.add(zero);
		initSpecimen();

		Collections.sort(population);
		maxRoute = getMaxRoute();
		minRoute = getMinRoute();
		bestSpecimen = population.get(0).clone();

		for (int pok = 0; !abort && pok < params.getGenerationsCount(); pok++) {
			ArrayList<Specimen> populacja_kolejna = new ArrayList<Specimen>();
			if(params.getSelectionMethod() == SelectionMethod.RANKING){
				ranking = new ArrayList<Specimen>();
				rankingIter=0;
				int minimum=20;
				if(params.getPopulationCount()<minimum){
					minimum=params.getPopulationCount();
				}
				double losowi=Math.random()*params.getPopulationCount()/2;
				rankingCount = minimum + (int)(losowi);
			}

			if (params.getSelectionMethod() == SelectionMethod.ROULETTE) {
				double sumaOdwrotnosciOcen = 0.0;
				for (Specimen os : population) {
					sumaOdwrotnosciOcen += 1/os.getRouteLength();
				}

				for (Specimen os : population) {
					os.setP_Roulette((1/os.getRouteLength()) / sumaOdwrotnosciOcen);
				}
			}

			while (populacja_kolejna.size() < population.size()) {
				Specimen k1 = selectSpecimen(params.getSelectionMethod());
				Specimen k2 = null;

				if (params.getPopulationCount() > (populacja_kolejna.size() + 1) && Math.random() < params.getCrossingProbability()) {  
					k2 = selectSpecimen(params.getSelectionMethod());
					crossing(k1, k2);
				}

				if (Math.random() < params.getMutationProbability()) {
					mutation(k1);
					populacja_kolejna.add(k1);
				} else {
					populacja_kolejna.add(k1);
				}

				if (k2 != null && Math.random() < params.getMutationProbability()) {
					mutation(k2);
					populacja_kolejna.add(k2);
				} else if (k2 != null) {
					populacja_kolejna.add(k2);
				}
			}

			population = populacja_kolejna;
			Collections.sort(population);
			populacja_kolejna = new ArrayList<Specimen>();

			if (maxRoute < getMaxRoute()) {
				maxRoute = getMaxRoute();
			}

			if (minRoute > getMinRoute()) {
				bestGeneration=pok;
				bestSpecimen=population.get(0).clone();
				minRoute = getMinRoute();
			}
		}
	}

	private Specimen selectSpecimen(SelectionMethod metoda) {
		if (metoda == SelectionMethod.ROULETTE) {
			return selectionRoulette();
		} else if (metoda == SelectionMethod.TOURNAMENT) {
			return selectionTournament();
		} else {
			return selectionRanking();

		}
	}

	private Specimen selectionRoulette() {
		double los = Math.random();
		double currentSumaP = 0.0;
		int j;
		for (j = 0; j < population.size()-1; j++) {
			currentSumaP += population.get(j).getP_Roulette();
			if (los <= currentSumaP) {
				break;
			}
		}
		return population.get(j);
	}

	private Specimen selectionRanking() {
		if(rankingIter>=rankingCount){
			rankingIter=0;
		}

		if(ranking.isEmpty()){
			ranking=createRanking();
		}

		Specimen os=ranking.get(rankingIter++);
		return os;
	}

	private void crossing(Specimen o1, Specimen o2) {
		int startindex = (int) (Math.random() * (o1.getRoute().size())); //[0,1) * 10= [0,9]
		int endindex = startindex + (int) (Math.random() * (o1.getRoute().size() - startindex));
		Specimen ox_o1=new Specimen(cities, startCity, drillChangeInterval);
		Specimen ox_o2=new Specimen(cities, startCity, drillChangeInterval);

		for(int j=endindex+1;j<o1.getRoute().size();j++){
			ox_o1.addCity(o2.getCity(j).clone());
			ox_o2.addCity(o1.getCity(j).clone());
		}

		for(int j=0;j<startindex;j++){
			ox_o1.addCity(o2.getCity(j).clone());
			ox_o2.addCity(o1.getCity(j).clone());
		}

		for (int i = startindex; i <= endindex; i++) {
			City temp = o1.getCity(i);
			o1.setCity(i, o2.getCity(i));
			ox_o1.deleteCity(o2.getCity(i));
			o2.setCity(i, temp);
			ox_o2.deleteCity(temp);
		}

		for(int i=endindex+1;i<o1.getRoute().size();i++){
			o1.setCity(i, ox_o1.getCity(0));
			ox_o1.deleteCity(0);
			o2.setCity(i, ox_o2.getCity(0));
			ox_o2.deleteCity(0);
		}

		for(int i=0;i<startindex;i++){
			o1.setCity(i, ox_o1.getCity(0));
			ox_o1.deleteCity(0);
			o2.setCity(i, ox_o2.getCity(0));
			ox_o2.deleteCity(0);
		}
	}

	private void mutation(Specimen o) {
		int startindex = (int) (Math.random() * (o.getRoute().size() - 1)); //[0,1) * 10= [0,9]
		int endindex = startindex + 1 + (int) (Math.random() * (o.getRoute().size() - 1 - startindex));

		ArrayList<City> odwracaneMiasta = new ArrayList<City>();
		for (int i = startindex; i <= endindex; i++) {
			odwracaneMiasta.add(o.getCity(i));
		}

		for (int i = 0; startindex + i <= endindex; i++) {
			o.setCity(startindex + i, odwracaneMiasta.get(odwracaneMiasta.size() - 1 - i));
		}
	}

	private ArrayList<Specimen> createRanking(){
		ArrayList<Specimen> wynik= new ArrayList<Specimen>();
		for(int i=0;i<rankingCount;i++){
			wynik.add(population.get(i).clone());
		}
		return wynik;
	}

	private void initSpecimen() {
		Specimen zero=population.get(0);
		zero.shuffleRoute();
		population=new ArrayList<Specimen>();
		population.add(zero);
		for (int i = 0; i < params.getPopulationCount(); i++) {
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
		if (algorithmParameters != null && algorithmParameters instanceof GaParameters)
			return true;
		return false;
	}

	@Override
	protected Core getNewInstance() {
		return new GACore();
	}
}
