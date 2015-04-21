package pl.edu.pwr.aic.dmp.alg;
import java.math.BigDecimal;
import java.util.List;

public class RandomCore extends Core{
	double[][] lengths; // macie� d�ugo�ci tras mi�dzy poszczeg�lnymi miastami
	double currentLen; // warto�� funkcji oceny najepszego osobnika
	int bestCycle; // nr cyklu z najlepszym osobnikiem
	Specimen best; // najlepszy osobnik ze wszystkich
	Specimen currentSpecimen; // obecny osobnik
	int cycles; // liczba cykli

	// zmienne na potrzeby statystyki
	double bestLen;
	double cycleLen[];

	public RandomCore(List<City> cities, int cycles, boolean detailedStatsOn) {
		super(cities,detailedStatsOn);
		this.cycles = cycles;
		bestLen = Double.POSITIVE_INFINITY;
		currentLen = Double.POSITIVE_INFINITY;
		bestCycle = -1;
		cycleLen = new double[cycles + 1];
	}

	public void run() {
		startCity= cities.get(0).clone();
		currentSpecimen = new Specimen(this);
		currentSpecimen.setRoute(cities);
		start=System.currentTimeMillis(); // start licznika czasu
		// rozpoczynamy obliczenia
		int i = 0;
		try{
			while (!abort && i <= cycles) {
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
		currentSpecimen.shuffleRoute();
		currentLen=currentSpecimen.getRate();
		
		if(currentLen<bestLen){
			best=currentSpecimen;
			bestLen=currentLen;
			bestCycle=n;
		}

		if(detailedStatsOn){
			String line = "Los #" + n + " -> długość trasy: " + round(currentLen,2);
			addLine(line);
		}
	}

	public double round(double d,int pos){
		if(Double.isInfinite(d) || Double.isNaN(d)){
			return -1;
		} else {
			return new BigDecimal(d).setScale(pos, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}
}