package pl.edu.pwr.aic.dmp.metaEA.Parameters;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;

public class DmpParamsMoveUtilTest {

	@Test
	public void shouldProperlyIncreaseIntParameter(){
		for(int i=0; i<50; i++){
			int result = DmpParamsMoveUtil.getRandomlyIncreasedInt(5, 10);
			assertTrue("Should be >= 5, is: " +result, result >= 5);
			assertTrue("Should be < 10, is: " +result, result < 10);
		}
	}
	
	@Test
	public void shouldProperlyDecreaseIntParameter(){
		int param = 1;
		int lowerBound = 1;
		for(int i=0;i<50;i++){
			int result = DmpParamsMoveUtil.getRandomlyDecreasedInt(param, lowerBound);
			assertDecreasingResult(param, lowerBound, result);
		}
	}
	
	@Test
	public void shouldProperlyIncreaseDoubleParameter(){
		double param = 1d;
		double upperBound = 1.5d;
		for(int i=0; i<50; i++){
			double result = DmpParamsMoveUtil.getRandomlyIncreasedDouble(param, upperBound);
			assertIncreasingResult(param, upperBound, result);
		}
	}
	
	@Test
	public void shouldProperlyDecreaseDoubleParameter(){
		double param = 1d;
		double lowerBound = 0.5d;
		for(int i=0; i<50; i++){
			double result = DmpParamsMoveUtil.getRandomlyDecreasedDouble(param, lowerBound);
			assertDecreasingResult(param, lowerBound, result);
		}
	}

	private void assertIncreasingResult(double param, double upperBound, double result) {
		assertTrue("Should be >=" + param + ", is: " + result, result >= param);
		assertTrue("Should be <=" + upperBound + ", is " +result, result <=upperBound);
	}
	
	private void assertDecreasingResult(double param, double lowerBound, double result) {
		assertTrue("Should be >=" + lowerBound + ", is: " + result, result >=lowerBound);
		assertTrue("Should be <=" + param + ", is " +result, result <=param);
	}
}
