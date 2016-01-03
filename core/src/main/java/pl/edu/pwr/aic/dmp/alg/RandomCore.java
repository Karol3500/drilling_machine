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
	double bestLen;
	double cycleLen[];
	RandomParameters params;

	public RandomCore() {
		algorithmName = "Random";
		bestLen = Double.POSITIVE_INFINITY;
		currentLen = Double.POSITIVE_INFINITY;
		bestCycle = -1;
	}

	@Override
	void runAlg() {
		params = (RandomParameters) algorithmParameters;
		cycleLen = new double[params.getCyclesNumber() + 1];
		startCity= cities.get(0).clone();
		currentSpecimen = new Specimen(this);
		currentSpecimen.setRoute(cities);
		int i = 0;
		while (!abort && i <= params.getCyclesNumber()) {
			generateSpecimen(i);
			i++;
		}
	}

	public void generateSpecimen(int n) {
		currentSpecimen.shuffleRoute();
		currentLen=currentSpecimen.getRouteLength();

		if(currentLen<bestLen){
			bestSpecimen=currentSpecimen;
			bestLen=currentLen;
			bestCycle=n;
		}
	}

	public double round(double d,int pos){
		if(Double.isInfinite(d) || Double.isNaN(d)){
			return -1;
		} else {
			return new BigDecimal(d).setScale(pos, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}
	
	@Override
	protected boolean areProperParametersGiven() {
		if (algorithmParameters != null && algorithmParameters instanceof RandomParameters)
			return true;
		return false;
	}
}