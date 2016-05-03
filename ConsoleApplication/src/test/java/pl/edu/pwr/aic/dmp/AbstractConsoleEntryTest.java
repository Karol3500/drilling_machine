package pl.edu.pwr.aic.dmp;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.helpers.FileParsingStrategy;

public class AbstractConsoleEntryTest {

	@Test
	public void shouldReadParametersProperlyForOneRow(){
		String testFilePath = "src/test/resources/abstractParameters";
		AbstractConsoleEntry entry = new AbstractConsoleEntry() {
		};

		TestParameters params = (TestParameters)entry.getParametersListsFromFile(testFilePath, new TestParamsFileParsingStrategy()).get(0);

		assertEquals("dog", params.stringParam);
		assertEquals(1.5d, params.doubleParam, 0d);
		assertEquals(1, params.intParam);
	}

	class TestParamsFileParsingStrategy extends FileParsingStrategy{

		@Override
		public Parameters parseParameters(List<String> params) {
			TestParameters testParams = new TestParameters();
			testParams.stringParam = String.valueOf(params.get(0));
			testParams.doubleParam = Double.valueOf(params.get(1));
			testParams.intParam = Integer.valueOf(params.get(2));
			return testParams;
		}
	}

	class TestFileParsingStrategy extends FileParsingStrategy{

		@Override
		protected Parameters parseParameters(List<String> params) {
			TestParameters result = new TestParameters();
			result.stringParam = params.get(0);
			result.doubleParam = Double.valueOf(params.get(1));
			result.intParam = Integer.valueOf(params.get(2));
			return result;
		}		
	}

	class TestParameters implements Parameters{
		public String stringParam;
		public double doubleParam;
		public int intParam;

		@Override
		public Parameters setSaneDefaults() {
			return null;
		}

		@Override
		public List<? extends Object> getParameterNamesAsList() {
			return null;
		}

		@Override
		public List<? extends Object> getParameterValuesAsList() {
			return null;
		}

		@Override
		public Object clone(){
			return null;
		}
	}
}
