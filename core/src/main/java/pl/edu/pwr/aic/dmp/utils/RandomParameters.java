package pl.edu.pwr.aic.dmp.utils;

import pl.edu.pwr.aic.dmp.utils.Parameters;

public class RandomParameters implements Parameters {
	private int cyclesNumber;
	
	@Override
	public void setSaneDefaults() {
		cyclesNumber = 200;
	}
	
	@Override
	public Object clone(){
		RandomParameters clone = new RandomParameters();
		clone.cyclesNumber = cyclesNumber;
		return clone;
	}

	public int getCyclesNumber() {
		return cyclesNumber;
	}

	public void setCyclesNumber(int cyclesNumber) {
		this.cyclesNumber = cyclesNumber;
	}
}