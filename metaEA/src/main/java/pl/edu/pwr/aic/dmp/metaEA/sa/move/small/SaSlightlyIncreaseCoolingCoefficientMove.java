package pl.edu.pwr.aic.dmp.metaEA.sa.move.small;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.metaEA.sa.parameters.SaParameter;
import pl.edu.pwr.aic.dmp.utils.SaParameters;

public class SaSlightlyIncreaseCoolingCoefficientMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		SaParameters params = (SaParameters)((DmpParametersSolution)soln).getParameters();
		params.setCoolingCoefficient(DmpParamsMoveUtil.getSlightlyIncreasedDouble(
				params.getCoolingCoefficient(),
				SaParameter.COOLING_COEFFICIENT.changeStep,
				SaParameter.COOLING_COEFFICIENT.upperBound));
	}
	
	@Override
	public int hashCode(){
		return 1 + SaParameter.COOLING_COEFFICIENT.toString().hashCode();
	}

}
