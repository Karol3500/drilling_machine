package pl.edu.pwr.aic.dmp.alg;
import pl.edu.pwr.aic.dmp.alg.utils.SaParameters;

public class SACore extends Core{
	private SaParameters params;
	private double bestRouteLength;

	public SACore() {
		algorithmName = "SimulatedAnnealing";
		bestRouteLength = Double.MAX_VALUE;
	}

	@Override
	void runAlg() {
		params = (SaParameters) algorithmParameters;
		bestRouteLength = Double.MAX_VALUE;
		for (int cycleNumber=0; cycleNumber<params.getCyclesNumber(); cycleNumber++) {
			simulate(cycleNumber);
		}
	}

	void simulate(int currentCycleNumber) {
		double currentTemperature=params.getStartTemperature();
		Specimen currentSpecimen = getRandomSolution();
		boolean success = false;
		boolean found   = false;
		int failsCounter = 0;
		int successesCounter;
		while (failsCounter!=params.getPermutationAttempts()) {
			int i=0;
			successesCounter = 0;
			success = false;
			found = false;
			while (!success) {
				Specimen mutatedSpecimen = currentSpecimen.returnMutatedClone();				
				if (shouldNewSolutionBeAccepted(mutatedSpecimen.getRouteLength(), 
						currentSpecimen.getRouteLength(), currentTemperature)) {
					currentSpecimen=mutatedSpecimen;
					if (currentSpecimen.getRouteLength()<bestRouteLength) {
						bestSpecimen=currentSpecimen.clone();
						bestRouteLength = currentSpecimen.getRouteLength();
						found = true;
						successesCounter++;
					}
				}
				i++; 

				success = (i>100*(cities.size()-1)||successesCounter>10*(cities.size()-1));
			}
			currentTemperature = currentTemperature*params.getCoolingCoefficient();
			if (found) {
				failsCounter = 0;
			} else failsCounter++;
		}
	}

	private boolean shouldNewSolutionBeAccepted(double currentRouteLength, double bestRouteLength, double currentTemperature) {
		return currentRouteLength <= bestRouteLength ? true : 
			(Math.random() < probabilityOfStateAcceptance(currentRouteLength, bestRouteLength, currentTemperature));
	}

	private double probabilityOfStateAcceptance(double currentLength, double bestLength, double temperature) {
		return Math.exp((bestLength - currentLength)/temperature);
	}

	@Override
	protected boolean areProperParametersGiven() {
		if (algorithmParameters != null && algorithmParameters instanceof SaParameters)
			return true;
		return false;
	}

	@Override
	protected Core getNewInstance() {
		return new SACore();
	}
}