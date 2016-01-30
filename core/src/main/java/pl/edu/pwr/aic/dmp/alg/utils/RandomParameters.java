package pl.edu.pwr.aic.dmp.alg.utils;

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
}