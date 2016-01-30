package pl.edu.pwr.aic.dmp.metaEA.sa.move.random;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.utils.SaParameters;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.metaEA.sa.parameters.SaParameter;

public class SaDecreaseStartTemperatureMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		SaParameters params = (SaParameters)((DmpParametersSolution)soln).getParameters();
		params.setStartTemperature(DmpParamsMoveUtil.getRandomlyDecreasedDouble(
				params.getStartTemperature(),
				SaParameter.START_TEMPERATURE.lowerBound));
	}
	
	@Override
	public int hashCode(){
		return SaParameter.START_TEMPERATURE.toString().hashCode();
	}

}
