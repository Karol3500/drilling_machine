package pl.edu.pwr.aic.dmp.alg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Core extends Thread {
	boolean abort;
	Integer drillChangeInterval;
	City startCity;
	protected List<City> cities;
	protected long start;
	protected long stop;
	protected Specimen bestSpecimen;
	protected int bestGeneration;
	protected String message;
	protected boolean detailedStatsOn;
	
	public void calculateInterval(
			double drillDurabilityInM,
			double drillDiameterInMm,
			double movePerRotation,
			double holeDeepnessInMm,
			double spindleSpeedInRotPerMin){
		
		double vc = (Math.PI * drillDiameterInMm * spindleSpeedInRotPerMin) / 1000d;
		drillChangeInterval=(int)Math.floor(drillDurabilityInM * (vc/movePerRotation) / holeDeepnessInMm);
	}
	
	protected Core(List<City> cities, boolean detailedStatsOn){
		this.cities = new ArrayList<City>(cities);
		this.detailedStatsOn = detailedStatsOn;
	}
	
	public void abort() {
		abort = true;
	}
	
	public int getInterwal(){
		return drillChangeInterval;
	}
	
	public City getstartCity(){
		return startCity;
	}

	public void showEffects() {
		// zapis wynik�w do logu i przebiegu
		addLine("============================================================================================================");
		addDate();
		String temp = "";
		String algorithm ="";
		if (abort == true) {
			temp = "(because of abort) ";
		}
		addLine(">>> Algorithm "+algorithm+" finished " + temp + "with result:");
		addPhrase("Algorithm working time: " + (stop-start)/1000.0+" s");
		newLine();
		addLine("Drill change interval: " + drillChangeInterval);
		addLine("Route length: " + bestSpecimen.getRate());
		String tempS = "";
		addLine("Generation with best specimen found: " + bestGeneration + tempS);
		addPhrase("Best found route: " + bestSpecimen.showRoute());
		newLine();
	
		addLine("============================================================================================================");
		System.gc();
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
	
}
