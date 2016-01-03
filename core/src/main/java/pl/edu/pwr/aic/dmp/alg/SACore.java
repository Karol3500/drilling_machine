package pl.edu.pwr.aic.dmp.alg;
import java.math.BigDecimal;

import pl.edu.pwr.aic.dmp.alg.utils.SAParameters;

public class SACore extends Core{
	SAParameters params;
	double bestLength;	//dlugosc najlepszej trasy
	int numberOfCitiesMinusOne;
	int bestCycle; // nr cyklu z bestSpecimenm osobnikiem
	public SACore() {
		algorithmName = "Simulated Annealing";
		bestSpecimen = new Specimen(this);
		bestLength = Double.MAX_VALUE;
		bestCycle = -1;
	}

	@Override
	void runAlg() {
		params = (SAParameters) algorithmParameters;
		numberOfCitiesMinusOne = cities.size()-1;
		startCity= cities.get(0).clone();
		for (int cycleNumber=0; !abort && cycleNumber<params.getCyclesNumber(); cycleNumber++) {
			simulate(cycleNumber);
		}
	}

	Specimen getInitialSpecimenWithDefaultValues() {
		Specimen specimen=new Specimen(this);
		specimen.setRoute(cities);
		specimen.shuffleRoute();
		return specimen;
	}
	
	double simulate(int currentCycleNumber) {
		double currentTemperature=params.getStartTemperature();
		Specimen currentSpecimen = getInitialSpecimenWithDefaultValues();
		boolean success = false;
		boolean found   = false;
		int failsCounter = 0;
		int successesCounter;
		while (!(failsCounter==params.getPermutationAttempts())) {
			int i=0;
			successesCounter = 0;
			success = false;
			found = false;
			while (!success) {
				Specimen mutatedSpecimen = currentSpecimen.returnMutatedClone();				
				if (shouldNewSolutionBeAccepted(mutatedSpecimen.getRouteLength(), 
						currentSpecimen.getRouteLength(), currentTemperature)) {
					currentSpecimen=mutatedSpecimen;
					if (currentSpecimen.getRouteLength()<bestLength) {
						bestSpecimen=currentSpecimen.clone();
						bestCycle=currentCycleNumber;
						bestLength = currentSpecimen.getRouteLength();
						found = true;
						successesCounter++;
					}
				}
				i++; 

				success = (i>100*numberOfCitiesMinusOne||successesCounter>10*numberOfCitiesMinusOne);
			}
			currentTemperature = currentTemperature*params.getCoolingCoefficient();
			if (found) {
				failsCounter = 0;
			} else failsCounter++;
		}
		return bestLength;
	}



	boolean shouldNewSolutionBeAccepted(double currentRouteLength, double bestRouteLength, double currentTemperature) {
		return currentRouteLength <= bestRouteLength ? true : 
			(Math.random() < probabilityOfStateAcceptance(currentRouteLength, bestRouteLength, currentTemperature));
	}

	private double probabilityOfStateAcceptance(double tempLen, double len, double temperature) {
		return Math.exp((len - tempLen)/temperature);
	}

	public double round(double d,int pos){
		return new BigDecimal(d).setScale(pos, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	@Override
	protected boolean areProperParametersGiven() {
		if (algorithmParameters != null && algorithmParameters instanceof SAParameters)
			return true;
		return false;
	}
}