package pl.edu.pwr.aic.dmp.metaEA.parameters;

import java.util.Random;

public class DmpParamsMoveUtil {

	public static int getIncreasedInt(int parameter, int upperBound) {
		return parameter + new Random().nextInt(upperBound-parameter);
	}

	public static int getDecreasedInt(int parameter, int lowerBound) {
		if(parameter == lowerBound){
			return parameter;
		}
		return parameter - new Random().nextInt(parameter-lowerBound);
	}

	public static double getIncreasedDouble(double parameter, double upperBound) {
		return parameter + (upperBound - parameter) * new Random().nextDouble();
	}

	public static double getDecreasedDouble(double parameter, double lowerBound) {
		if(parameter == lowerBound){
			return parameter;
		}
		return lowerBound + (parameter - lowerBound) * new Random().nextDouble();
	}
}