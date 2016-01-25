package pl.edu.pwr.aic.dmp.metaEA.Parameters;

import java.util.Random;

import pl.edu.pwr.aic.dmp.metaEA.iwo.Parameters.IwoParameter;

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

	public static IwoParameter drawParam() {
		return IwoParameter.values()[new Random().nextInt(IwoParameter.values().length)];
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