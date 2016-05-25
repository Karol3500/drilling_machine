package pl.edu.pwr.aic.dmp.alg;
import pl.edu.pwr.aic.dmp.alg.utils.RandomParameters;

public class RandomCore extends Core{
	Specimen currentSpecimen;
	RandomParameters params;

	public RandomCore() {
		algorithmName = "Random";
	}

	@Override
	void runAlg() {
		bestRouteLength = Double.MAX_VALUE;
		params = (RandomParameters) algorithmParameters;
		currentSpecimen = new Specimen(cities, startCity, drillChangeInterval);
		for(int i = 0; i < params.getCyclesNumber(); i++) {
			currentSpecimen.shuffleRoute();
			if(currentSpecimen.getRouteLength() < bestRouteLength){
				bestSpecimen=currentSpecimen;
				bestRouteLength=currentSpecimen.getRouteLength();
				bestGeneration=i;
			}
		}
	}

	public void generateSpecimen(int n) {
		currentSpecimen.shuffleRoute();
		if(currentSpecimen.getRouteLength() < bestRouteLength){
			bestSpecimen=currentSpecimen;
			bestRouteLength=currentSpecimen.getRouteLength();
			bestGeneration=n;
		}
	}
	
	@Override
	protected boolean areProperParametersGiven() {
		if (algorithmParameters != null && algorithmParameters instanceof RandomParameters)
			return true;
		return false;
	}

	@Override
	protected Core getNewInstance() {
		return new RandomCore();
	}
	
	
}