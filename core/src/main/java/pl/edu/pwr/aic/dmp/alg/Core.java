package pl.edu.pwr.aic.dmp.alg;

import java.util.Date;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.UnitResult;
import pl.edu.pwr.aic.dmp.utils.Parameters;

public abstract class Core extends Thread {
	boolean abort;
	City startCity;
	int drillChangeInterval;
	protected List<City> cities;
	protected long startTime;
	protected long stopTime;
	protected Specimen bestSpecimen;
	protected int bestGeneration;
	protected String message;
	protected UnitResult result;
	protected String algorithmName;
	protected Parameters algorithmParameters;
	
	protected Core(){
		result = new UnitResult();
	}
	
	public void setParameters(Parameters algorithmParameters){
		this.algorithmParameters = algorithmParameters;
	}
	
	@Override
	public void run(){
		startTime=System.currentTimeMillis();
		setupSaneParametersIfNotGiven();
		runAlg();
		stopTime=System.currentTimeMillis();
		result.setExecutionTimeInSeconds((stopTime-startTime)/1000d);
		result.setBestRouteLength(bestSpecimen.getRouteLength());
		result.setPermutation(bestSpecimen.getBestRoute());
		
		showEffects();
	}
	
	abstract void runAlg();
	
	public void abort() {
		abort = true;
	}
	
	public City getstartCity(){
		return startCity;
	}

	public void showEffects() {
		message="";
		addLine("============================================================================================================");
		addDate();
		String temp = "";
		if (abort == true) {
			temp = "(because of abort) ";
		}
		addLine(">>>"+algorithmName+" finished " + temp + "with result:");
		addPhrase("Algorithm working time: " + result.getExecutionTimeInSeconds() + " s");
		newLine();
		addLine("Drill change interval: " + drillChangeInterval);
		addLine("Route length: " + result.getBestRouteLength());
		String tempS = "";
		addLine("Generation with best specimen found: " + bestGeneration + tempS);
		addPhrase("Best found route: " + result.getPermutation());
		newLine();
	
		addLine("============================================================================================================");
		System.out.println(message);
	}

	public void addPhrase(String s) {
		message += s;
	}

	public void addDate() {
		message += new Date();
	}

	public void addLine(String s) {
		addPhrase(s);
		newLine();
	}

	public void newLine() {
		message += "\n";
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public UnitResult getResult() {
		return result;
	}
	
	private void setupSaneParametersIfNotGiven() {
		if(!areProperParametersGiven()){
			this.algorithmParameters.setSaneDefaults();
		}
	}

	public int getDrillChangeInterval() {
		return drillChangeInterval;
	}

	public void setDrillChangeInterval(int drillChangeInterval) {
		this.drillChangeInterval = drillChangeInterval;
	}

	protected abstract boolean areProperParametersGiven() ;
}
