package pl.edu.pwr.aic.dmp.alg;
import java.math.BigDecimal;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.RandomParameters;
import pl.edu.pwr.aic.dmp.utils.Machine;

public class RandomCore extends Core{
	double[][] lengths; // macie� d�ugo�ci tras mi�dzy poszczeg�lnymi miastami
	double currentLen; // warto�� funkcji oceny najepszego osobnika
	int bestCycle; // nr cyklu z najlepszym osobnikiem
	Specimen currentSpecimen; // obecny osobnik
	RandomParameters params;
	double bestLen;
	double cycleLen[];

	public RandomCore(List<City> cities, RandomParameters params, boolean detailedStatsOn, Machine m) {
		super(cities,detailedStatsOn,m);
		bestLen = Double.POSITIVE_INFINITY;
		currentLen = Double.POSITIVE_INFINITY;
		bestCycle = -1;
		this.params = params;
		cycleLen = new double[params.getCyclesNumber() + 1];
	}

	public void run() {
		startCity= cities.get(0).clone();
		currentSpecimen = new Specimen(this);
		currentSpecimen.setRoute(cities);
		start=System.currentTimeMillis(); // start licznika czasu
		// rozpoczynamy obliczenia
		int i = 0;
		while (!abort && i <= params.getCyclesNumber()) {
			generateSpecimen(i); // budowa i-tej populacji
			i++;
		}
		stop=System.currentTimeMillis(); // stop licznika czasu
		showEffects();
	}

	public void generateSpecimen(int n) {
		currentSpecimen.shuffleRoute();
		currentLen=currentSpecimen.getRate();

		if(currentLen<bestLen){
			bestSpecimen=currentSpecimen;
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