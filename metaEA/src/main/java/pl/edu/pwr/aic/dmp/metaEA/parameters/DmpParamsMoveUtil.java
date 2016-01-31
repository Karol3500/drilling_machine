package pl.edu.pwr.aic.dmp.metaEA.parameters;

import java.util.Random;

public class DmpParamsMoveUtil {

	static Random random = new Random();
	
	public static int getRandomlyIncreasedInt(int parameter, int upperBound) {
		return parameter + new Random().nextInt(upperBound-parameter);
	}

	public static int getRandomlyDecreasedInt(int parameter, int lowerBound) {
		if(parameter == lowerBound){
			return parameter;
		}
		return parameter - new Random().nextInt(parameter-lowerBound);
	}

	public static double getRandomlyIncreasedDouble(double parameter, double upperBound) {
		return parameter + (upperBound - parameter) * random.nextDouble();
	}

	public static double getRandomlyDecreasedDouble(double parameter, double lowerBound) {
		if(parameter == lowerBound){
			return parameter;
		}
		return lowerBound + (parameter - lowerBound) * random.nextDouble();
	}
	
	public static int getSlightlyIncreasedInt(int parameter, int step, int upperBound) {
		if(parameter + step <= upperBound){
			return parameter + step;
		}
		return upperBound;
	}

	public static int getSlightlyDecreasedInt(int parameter, int step, int lowerBound) {
		if(parameter - step >= lowerBound){
			return parameter - step;
		}
		return lowerBound;
	}
	
	public static double getSlightlyIncreasedDouble(double parameter, double step, double upperBound) {
		if(parameter + step <= upperBound){
			return parameter + step;
		}
		return upperBound;
	}

	public static double getSlightlyDecreasedDouble(double parameter, double step, double lowerBound) {
		if(parameter - step >= lowerBound){
			return parameter - step;
		}
		return lowerBound;
	}
}