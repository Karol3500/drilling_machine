package pl.edu.pwr.aic.dmp.alg.utils;

public class MachineParameters implements Parameters {
	
	private double drillDurabilityInM;
	private double drillDiameterInMm;
	private double movePerRotation;
	private double holeDeepnessInMm;
	private double spindleSpeedInRotPerMin;
	
	@Override
	public Parameters setSaneDefaults(){
		setDrillDiameterInMm(20);
		setDrillDurabilityInM(4);
		setSpindleSpeedInRotPerMin(3184);
		setMovePerRotation(0.8);
		setHoleDeepnessInMm(50);
		return this;
	}
	
	@Override
	public Object clone(){
		MachineParameters clone = new MachineParameters();
		clone.drillDiameterInMm = drillDiameterInMm;
		clone.drillDurabilityInM = drillDurabilityInM;
		clone.movePerRotation = movePerRotation;
		clone.holeDeepnessInMm = holeDeepnessInMm;
		clone.spindleSpeedInRotPerMin = spindleSpeedInRotPerMin;
		return clone;
	}
	
	public double getDrillDurabilityInM() {
		return drillDurabilityInM;
	}
	public void setDrillDurabilityInM(double drillDurabilityInM) {
		this.drillDurabilityInM = drillDurabilityInM;
	}
	public double getDrillDiameterInMm() {
		return drillDiameterInMm;
	}
	public void setDrillDiameterInMm(double drillDiameterInMm) {
		this.drillDiameterInMm = drillDiameterInMm;
	}
	public double getMovePerRotation() {
		return movePerRotation;
	}
	public void setMovePerRotation(double movePerRotation) {
		this.movePerRotation = movePerRotation;
	}
	public double getHoleDeepnessInMm() {
		return holeDeepnessInMm;
	}
	public void setHoleDeepnessInMm(double holeDeepnessInMm) {
		this.holeDeepnessInMm = holeDeepnessInMm;
	}
	public double getSpindleSpeedInRotPerMin() {
		return spindleSpeedInRotPerMin;
	}
	public void setSpindleSpeedInRotPerMin(double spindleSpeedInRotPerMin) {
		this.spindleSpeedInRotPerMin = spindleSpeedInRotPerMin;
	}
}
