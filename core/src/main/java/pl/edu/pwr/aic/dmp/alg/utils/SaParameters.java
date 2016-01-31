package pl.edu.pwr.aic.dmp.alg.utils;

import java.util.Arrays;
import java.util.List;

public class SaParameters implements Parameters {
	
	int cyclesNumber;
	double startTemperature;
	double coolingCoefficient;
	int permutationAttempts;

	@Override
	public List<? extends Object> getParameterNamesAsList() {
		return Arrays.asList("Number of cycles", "Start temperature",
				"Cooling coefficient", "Permutation attempts");
	}

	@Override
	public List<? extends Object> getParameterValuesAsList() {
		return Arrays.asList(cyclesNumber, startTemperature, coolingCoefficient, permutationAttempts);
	}
	
	@Override
	public Parameters setSaneDefaults() {
		cyclesNumber = 2;
		startTemperature = 300;
		coolingCoefficient = 0.95;
		permutationAttempts = 10;
		return this;
	}
	
	@Override
	public Object clone(){
		SaParameters clone = new SaParameters();
		clone.cyclesNumber = cyclesNumber;
		clone.startTemperature = startTemperature;
		clone.coolingCoefficient = coolingCoefficient;
		clone.permutationAttempts = permutationAttempts;
		return clone;
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof SaParameters)){
			return false;
		}
		SaParameters comp = (SaParameters) obj;
		if(comp.getCoolingCoefficient() != coolingCoefficient||
				comp.getCyclesNumber() != cyclesNumber||
				comp.getPermutationAttempts() != permutationAttempts||
				comp.getStartTemperature() != startTemperature)
			return false;
		return true;
	}
	
	@Override
    public int hashCode() {
    	return (int)(100 * coolingCoefficient) + cyclesNumber + permutationAttempts + (int)(100*startTemperature);
    }

	public int getCyclesNumber() {
		return cyclesNumber;
	}

	public void setCyclesNumber(int cyclesNumber) {
		this.cyclesNumber = cyclesNumber;
	}

	public double getStartTemperature() {
		return startTemperature;
	}

	public void setStartTemperature(double startTemperature) {
		this.startTemperature = startTemperature;
	}

	public double getCoolingCoefficient() {
		return coolingCoefficient;
	}

	public void setCoolingCoefficient(double coolingCoefficient) {
		this.coolingCoefficient = coolingCoefficient;
	}

	public int getPermutationAttempts() {
		return permutationAttempts;
	}

	public void setPermutationAttempts(int permutationAttempts) {
		this.permutationAttempts = permutationAttempts;
	}
}
