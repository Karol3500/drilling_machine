package pl.edu.pwr.aic.dmp.alg;
import pl.edu.pwr.aic.dmp.alg.utils.SaParameters;

public class SACore extends Core{
	private SaParameters params;
	private double bestLength;
	
	public SACore() {
		algorithmName = "SimulatedAnnealing";
		bestLength = Double.MAX_VALUE;
	}

	@Override
	void runAlg() {
		bestSpecimen = new Specimen(cities, startCity, drillChangeInterval);
		params = (SaParameters) algorithmParameters;
		for (int cycleNumber=0; !abort && cycleNumber<params.getCyclesNumber(); cycleNumber++) {
			simulate(cycleNumber);
		}
	}

	private Specimen getInitialSpecimenWithDefaultValues() {
		Specimen specimen=new Specimen(cities, startCity, drillChangeInterval);
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
							bestLength = currentSpecimen.getRouteLength();
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
			return bestLength;
		}
	
//	private double simulate() {
//		double t = params.getStartTemperature();
//		Specimen solution =  getInitialSpecimenWithDefaultValues();
//		double iterTemp = t;
//
//		for (int i = 0; i < params.getCyclesNumber(); i ++) {
//			double routeLength = solution.getRouteLength();
//			Specimen mutant = solution.returnMutatedClone();
//			if (mutant.getRouteLength() < routeLength) {
//				if (shouldNewSolutionBeAccepted(mutant.getRouteLength(), 
//						solution.getRouteLength(), iterTemp)) {
//					solution = mutant;
//					if (solution.getRouteLength()<bestLength) {
//						bestSpecimen=solution.clone();
//						bestLength = solution.getRouteLength();
//					}
//					iterTemp = t * params.getCoolingCoefficient();
//				}
//			}
//		}
//		return solution.getRouteLength();
//	}

	private boolean shouldNewSolutionBeAccepted(double currentRouteLength, double bestRouteLength, double currentTemperature) {
		return currentRouteLength <= bestRouteLength ? true : 
			(Math.random() < probabilityOfStateAcceptance(currentRouteLength, bestRouteLength, currentTemperature));
	}

	private double probabilityOfStateAcceptance(double tempLen, double len, double temperature) {
		return Math.exp((len - tempLen)/temperature);
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