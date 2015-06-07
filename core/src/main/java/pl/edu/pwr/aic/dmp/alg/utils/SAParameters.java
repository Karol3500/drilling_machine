package pl.edu.pwr.aic.dmp.alg.utils;

import pl.edu.pwr.aic.dmp.utils.Parameters;

public class SAParameters implements Parameters {
	
	int cyclesNumber;
	double startTemperature;
	double coolingCoefficient;
	int permutationAttempts;
	
	@Override
	public void setSaneDefaults() {
		cyclesNumber = 50;
		startTemperature = 300;
		coolingCoefficient = 0.95;
		permutationAttempts = 10;
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
