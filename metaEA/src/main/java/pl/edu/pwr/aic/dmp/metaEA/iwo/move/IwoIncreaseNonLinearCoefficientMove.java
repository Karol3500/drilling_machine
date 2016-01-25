package pl.edu.pwr.aic.dmp.metaEA.iwo.move;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.metaEA.iwo.Parameters.IwoParameter;
import pl.edu.pwr.aic.dmp.utils.IwoParameters;

public class IwoIncreaseNonLinearCoefficientMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		IwoParameters params = (IwoParameters)((DmpParametersSolution)soln).getParameters();
		params.setNonLinearCoefficient(DmpParamsMoveUtil.getIncreasedDouble(
				params.getNonLinearCoefficient(),
				(int)IwoParameter.NON_LINEAR_COEFFICIENT.lowerBound));
	}

	@Override
	public int hashCode(){
		return 1 + IwoParameter.NON_LINEAR_COEFFICIENT.toString().hashCode();
	}
}
