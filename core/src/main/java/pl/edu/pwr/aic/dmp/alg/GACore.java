package pl.edu.pwr.aic.dmp.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.GaParameters;
import pl.edu.pwr.aic.dmp.alg.utils.SelectionMethod;

public class GACore extends Core{

	private GaParameters params;
	
	public GACore() {
		algorithmName = "GeneticAlgorthm";
		population = new ArrayList<Specimen>();
		tournament = new ArrayList<Specimen>();
		ranking = new ArrayList<Specimen>();
		abort = false;
	}

	@Override
	void runAlg() {
		params = (GaParameters)algorithmParameters;
		population = initPopulation(params.getPopulationCount());

		Collections.sort(population);
		bestSpecimen = population.get(0).clone();

		for (int pok = 0; !abort && pok < params.getGenerationsCount(); pok++) {
			List<Specimen> populacja_kolejna = getNewSpecimenList();
			
			while (populacja_kolejna.size() < population.size()) {
				Specimen k1 = selectSpecimen(params.getSelectionMethod());
				Specimen k2 = null;

				if (params.getPopulationCount() > (populacja_kolejna.size() + 1) && Math.random() < params.getCrossingProbability()) {
					k2 = selectSpecimen(SelectionMethod.TOURNAMENT);
					cross(k1, k2);
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
			populacja_kolejna = new ArrayList<Specimen>();
			setBestSpecimenAndIterationIfFound(pok,population);
		}
	}

	private Specimen selectSpecimen(SelectionMethod method) {
		if (method == SelectionMethod.ROULETTE) {
			return selectionRoulette();
		} else{
			return selectionTournament();
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

	private void cross(Specimen o1, Specimen o2) {
		int startindex = (int) (Math.random() * (o1.getCities().size())); //[0,1) * 10= [0,9]
		int endindex = startindex + (int) (Math.random() * (o1.getCities().size() - startindex));
		Specimen ox_o1=o1.clone();
		Specimen ox_o2=o2.clone();

		for(int j=endindex+1;j<o1.getCities().size();j++){
			ox_o1.addCity(o2.getCities().get(j).clone());
			ox_o2.addCity(o1.getCities().get(j).clone());
		}

		for(int j=0;j<startindex;j++){
			ox_o1.addCity(o2.getCities().get(j).clone());
			ox_o2.addCity(o1.getCities().get(j).clone());
		}

		for (int i = startindex; i <= endindex; i++) {
			City temp = o1.getCities().get(i);
			o1.setCity(i, o2.getCities().get(i));
			ox_o1.deleteCity(o2.getCities().get(i));
			o2.setCity(i, temp);
			ox_o2.deleteCity(temp);
		}

		for(int i=endindex+1;i<o1.getCities().size();i++){
			o1.setCity(i, ox_o1.getCities().get(0));
			ox_o1.deleteCity(0);
			o2.setCity(i, ox_o2.getCities().get(0));
			ox_o2.deleteCity(0);
		}

		for(int i=0;i<startindex;i++){
			o1.setCity(i, ox_o1.getCities().get(0));
			ox_o1.deleteCity(0);
			o2.setCity(i, ox_o2.getCities().get(0));
			ox_o2.deleteCity(0);
		}
	}

	private void mutation(Specimen o) {
		int startindex = (int) (Math.random() * (o.getCities().size() - 1)); //[0,1) * 10= [0,9]
		int endindex = startindex + 1 + (int) (Math.random() * (o.getCities().size() - 1 - startindex));

		ArrayList<City> odwracaneMiasta = new ArrayList<City>();
		for (int i = startindex; i <= endindex; i++) {
			odwracaneMiasta.add(o.getCities().get(i));
		}

		for (int i = 0; startindex + i <= endindex; i++) {
			o.setCity(startindex + i, odwracaneMiasta.get(odwracaneMiasta.size() - 1 - i));
		}
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
