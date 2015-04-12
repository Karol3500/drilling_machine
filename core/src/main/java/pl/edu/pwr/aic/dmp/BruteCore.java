package pl.edu.pwr.aic.dmp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BruteCore extends Core{
	List<City> cityList; // lista miast
	double currentLen; // warto�� funkcji oceny najepszego osobnika
	int bestCycle; // nr cyklu z najlepszym osobnikiem
	Specimen best; // najlepszy osobnik ze wszystkich
	Specimen currentSpecimen; // obecny osobnik
	long start; // start licznika
	long stop; //stop licznika
	boolean detailedStatsOn;
	Permutations<City> permutationEngine;

	String message = "";
	
	long permutationCount; // liczba permutacji

	double bestLen;
	double cycleLen[];


	public BruteCore(List<City> cities) {
		cityList = new LinkedList<City>(cities);

		BigInteger silnia = BigInteger.ONE;
		String s = "" + cityList.size();
		BigInteger n=new BigInteger(s);

		while (n.compareTo(BigInteger.ONE)>0) {
			silnia = silnia.multiply(n);
			n = n.subtract(BigInteger.ONE);
		}

		permutationCount=silnia.longValue();

		abort = false;
		bestLen = Double.POSITIVE_INFINITY;
		currentLen = Double.POSITIVE_INFINITY;
		bestCycle = -1;
	}

	public void run() {

		startCity= cityList.get(0).clone();

		permutationEngine=new Permutations<City>(cityList);

		start=System.currentTimeMillis(); // start licznika czasu
		// rozpoczynamy obliczenia
		int i = 0;

		try{
			while (!abort && permutationEngine.hasNext()) {
				generateSpecimen(i); // budowa i-tej populacji
				i++;
			}
			stop=System.currentTimeMillis(); // stop licznika czasu
			showEffects();

		}catch(Exception e){
			System.out.println("Wystąpił następujący błąd:");
			System.out.println(e.getClass().getName());
			System.out.println(e.getMessage());
			addLine("============================================================================================================");
			addLine("Wystąpił następujący błąd:");
			addLine(e.getClass().getName());
			addLine(e.getMessage());
			addLine("============================================================================================================");
			e.printStackTrace();
		}		
	}

	public void generateSpecimen(int n) {
		List<City> permutacja = permutationEngine.next();
		currentSpecimen=new Specimen(this);
		currentSpecimen.setRoute(permutacja);
		currentLen=currentSpecimen.getRate();

		if(currentLen<bestLen){
			best=currentSpecimen;
			bestLen=currentLen;
			bestCycle=n;
		}

		if(detailedStatsOn){
			String line = "Permutacja #" + n + " -> długość trasy: " + round(currentLen,2);
			addLine(line);
		}
	}

	public void showEffects() {
		// zapis wynik�w do logu i przebiegu
		addLine("============================================================================================================");
		addDate();
		String temp = "";
		if (abort == true) {
			temp = "(z powodu przerwania ";
		}
		addLine(">>> Algorytm BRUTALNY zakończył pracę " + temp + "z następującym wynikiem:");
		addPhrase("Czas pracy algorytmu: " + (stop-start)/1000.0+" s");
		newLine();
		addLine("Interwał wymiany wiertła: " + drillChangeInterval);
		addLine("Liczba wszystkich permutacji="+permutationCount);
		addLine("Długość trasy: " + bestLen);
		String tempS = "";
		addLine("Cykl w którym znaleziono najlepszą trasę: " + bestCycle + tempS);
		addPhrase("Najlepsza trasa: " + best.showRoute());
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
		if(Double.isInfinite(d) || Double.isNaN(d)){
			return -1;
		} else {
			return new BigDecimal(d).setScale(pos, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}
}