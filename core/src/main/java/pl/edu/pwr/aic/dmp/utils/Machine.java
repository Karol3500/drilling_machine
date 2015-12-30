package pl.edu.pwr.aic.dmp.utils;

public class Machine {
	
	protected int drillChangeInterval;
	MachineParameters machineParameters = null;
	
	public Machine(MachineParameters p){
		this.machineParameters = p;
		recalculateDrillChangeInterval();
	}
	
	protected void recalculateDrillChangeInterval(){
		
		double vc = (Math.PI * machineParameters.getDrillDiameterInMm() 
				* machineParameters.getSpindleSpeedInRotPerMin()) / 1000d;
		drillChangeInterval=(int)Math.floor(machineParameters.getDrillDurabilityInM()
				* (vc/machineParameters.getMovePerRotation()) / machineParameters.getHoleDeepnessInMm());
	}

	public int getDrillChangeInterval() {
		return drillChangeInterval;
	}

	public MachineParameters getMachineParameters() {
		return machineParameters;
	}

	public void setMachineParameters(MachineParameters machineParameters) {
		this.machineParameters = machineParameters;
		recalculateDrillChangeInterval();
	}
	
}
