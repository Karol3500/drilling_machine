package pl.edu.pwr.aic.dmp.alg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.GAParameters;
import pl.edu.pwr.aic.dmp.utils.Machine;


//parametry: populacja,pokolenia,p_mutacji, p_krzyzowania, metoda selekcji
public class GACore extends Core{

	ArrayList<Specimen> populacja;
	ArrayList<Specimen> turniej;
	ArrayList<Specimen> ranking;
	
	GAParameters params;
	
	int ranking_iter;
	int ranking_count;
	int dobry_wynik;
	double wynik_max;
	double wynik_min;
	public GACore(List<City> cities,
			boolean detailedStatsOn,
			GAParameters params,
			Machine m) {
		super(cities,detailedStatsOn,m);
		populacja = new ArrayList<Specimen>();
		turniej = new ArrayList<Specimen>();
		ranking = new ArrayList<Specimen>();
		this.params = params;
		abort = false;
		Specimen zero=new Specimen(this);
		startCity= cities.get(0).clone();
		zero.setRoute(cities);
		zero.shuffleRoute();
		populacja.add(zero);
	}

	public void run() {
		start=System.currentTimeMillis(); // start licznika czasu
		initOsobniki();

		Collections.sort(populacja);
		wynik_max = getMaxTrasa();
		wynik_min = getMinTrasa();
		bestSpecimen = populacja.get(0).clone();

		for (int pok = 0; !abort && pok < params.getGenerationsCount(); pok++) {
			ArrayList<Specimen> populacja_kolejna = new ArrayList<Specimen>();
			if(params.getSelectionMethod() == SelectionMethod.RANKING){
				ranking = new ArrayList<Specimen>();
				ranking_iter=0;
				int minimum=20;
				if(params.getPopulationCount()<minimum){
					minimum=params.getPopulationCount();
				}
				double losowi=Math.random()*params.getPopulationCount()/2;
				ranking_count = minimum + (int)(losowi);
			}

			if (params.getSelectionMethod() == SelectionMethod.ROULETTE) {
				double sumaOdwrotnosciOcen = 0.0;
				for (Specimen os : populacja) {
					sumaOdwrotnosciOcen += 1/os.getRate();
				}

				for (Specimen os : populacja) {
					os.setP_Roulette((1/os.getRate()) / sumaOdwrotnosciOcen);
				}
			}

			while (populacja_kolejna.size() < populacja.size()) {
				Specimen k1 = selekcja(params.getSelectionMethod());
				Specimen k2 = null;

				if (params.getPopulationCount() > (populacja_kolejna.size() + 1) && Math.random() < params.getCrossingProbability()) {  
					k2 = selekcja(params.getSelectionMethod());
					krzyzowanie(k1, k2);
				}

				if (Math.random() < params.getMutationProbability()) {
					mutacja(k1);
					populacja_kolejna.add(k1);
				} else {
					populacja_kolejna.add(k1);
				}

				if (k2 != null && Math.random() < params.getMutationProbability()) {
					mutacja(k2);
					populacja_kolejna.add(k2);
				} else if (k2 != null) {
					populacja_kolejna.add(k2);
				}
			}

			populacja = populacja_kolejna;
			Collections.sort(populacja);
			populacja_kolejna = new ArrayList<Specimen>();

			if (wynik_max < getMaxTrasa()) {
				wynik_max = getMaxTrasa();
			}

			if (wynik_min > getMinTrasa()) {
				bestGeneration=pok;
				bestSpecimen=populacja.get(0).clone();
				wynik_min = getMinTrasa();

			}

			if(detailedStatsOn){
				String line = "Pokolenie #" + pok + " -> najlepsza: " + round(getMinTrasa(),2)+ " średnia: "+round(getAvgTrasa(),2)+" najgorsza: "+round(getMaxTrasa(),2);
				message += line;
			}
		}

		stop=System.currentTimeMillis(); // stop licznika czasu
		result.setExecutionTimeInSeconds((stop-start)/1000d);
		result.setBestRouteLength(bestSpecimen.getRate());
		showEffects();
	}

	Specimen selekcja(SelectionMethod metoda) {
		if (metoda == SelectionMethod.ROULETTE) {
			return selekcja_ruletka();
		} else if (metoda == SelectionMethod.TOURNAMENT) {
			return selekcja_turniejowa();
		} else {
			return selekcja_rankingowa();

		}
	}

	Specimen selekcja_ruletka() {
		double los = Math.random();
		double currentSumaP = 0.0;
		int j;
		for (j = 0; j < populacja.size()-1; j++) {
			currentSumaP += populacja.get(j).getP_Roulette();
			if (los <= currentSumaP) {
				break;
			}
		}
		return populacja.get(j);
	}

	Specimen selekcja_turniejowa() {
		if(!turniej.isEmpty()){
			Specimen os=turniej.get(0);
			turniej.remove(0);
			return os;
		}

		int przedzial=2+(int)(Math.random()*((populacja.size()/2)-1));
		ArrayList<ArrayList<Specimen>> turnieje=new ArrayList<ArrayList<Specimen>>();
		turnieje.add(new ArrayList<Specimen>());

		ArrayList<Specimen> populacja_rand=clonePopulacja();
		Collections.shuffle(populacja_rand);
		int licznik_grupy=przedzial;
		for(int i=0;i<populacja_rand.size();i++){
			if(licznik_grupy==0){
				licznik_grupy=przedzial;
				turnieje.add(new ArrayList<Specimen>());
			}
			turnieje.get(turnieje.size()-1).add(populacja_rand.get(i));
			licznik_grupy--;
		}

		for(ArrayList<Specimen> grupa:turnieje){
			Collections.sort(grupa);
			turniej.add(grupa.get(0));
		}

		Specimen os=turniej.get(0);
		turniej.remove(0);
		return os;
	}

	Specimen selekcja_rankingowa() {

		if(ranking_iter>=ranking_count){
			ranking_iter=0;
		}

		if(ranking.isEmpty()){
			ranking=createRanking();
		}

		Specimen os=ranking.get(ranking_iter++);
		return os;
	}

	Specimen selekcja_rankingowa_old() {
		if(ranking.isEmpty()){
			ranking=clonePopulacja();
			Collections.sort(ranking);
		}

		Specimen os=ranking.get(0);
		ranking.remove(0);
		return os;
	}



	void krzyzowanie(Specimen o1, Specimen o2) {
		int startindex = (int) (Math.random() * (o1.getRoute().size())); //[0,1) * 10= [0,9]
		int endindex = startindex + (int) (Math.random() * (o1.getRoute().size() - startindex));
		Specimen ox_o1=new Specimen(this);
		Specimen ox_o2=new Specimen(this);

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

	void mutacja(Specimen o) {
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

	ArrayList<Specimen> createRanking(){
		ArrayList<Specimen> wynik= new ArrayList<Specimen>();
		for(int i=0;i<ranking_count;i++){
			wynik.add(populacja.get(i).clone());
		}
		return wynik;
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
		populacja=new ArrayList<Specimen>();
		populacja.add(zero);
		for (int i = 0; i < params.getPopulationCount(); i++) {
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

	public double round(double d,int pos){
		return new BigDecimal(d).setScale(pos, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
