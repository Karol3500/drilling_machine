package pl.edu.pwr.aic.dmp.alg.utils;

import java.util.Arrays;
import java.util.List;

public class RandomParameters implements Parameters {
	private int cyclesNumber;
	
	@Override
	public Parameters setSaneDefaults() {
		cyclesNumber = 200;
		return this;
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

	@Override
	public List<? extends Object> getParameterNamesAsList() {
		return Arrays.asList("Number of cycles");
	}

	@Override
	public List<? extends Object> getParameterValuesAsList() {
		return Arrays.asList(cyclesNumber);
	}
}