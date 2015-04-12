package pl.edu.pwr.aic.dmp;

public abstract class Core extends Thread {
	boolean abort;
	Integer drillChangeInterval;
	City startCity;
	
	public void calculateInterval(
			double drillDurabilityInM,
			double drillDiameterInMm,
			double movePerRotation,
			double holeDeepnessInMm,
			double spindleSpeedInRotPerMin){
		
		double vc = (Math.PI * drillDiameterInMm * spindleSpeedInRotPerMin) / 1000d;
		drillChangeInterval=(int)Math.floor(drillDurabilityInM * (vc/movePerRotation) / holeDeepnessInMm);
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
	
}
