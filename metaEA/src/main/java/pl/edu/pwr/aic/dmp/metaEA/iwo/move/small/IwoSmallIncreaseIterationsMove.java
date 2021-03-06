package pl.edu.pwr.aic.dmp.metaEA.iwo.move.small;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.metaEA.iwo.parameters.IwoParameter;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;

public class IwoSmallIncreaseIterationsMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		IwoParameters params = (IwoParameters)((DmpParametersSolution)soln).getParameters();
		params.setNumberOfIterations(DmpParamsMoveUtil.getSlightlyIncreasedInt(
				params.getNumberOfIterations(),
				(int)IwoParameter.NUMBER_OF_ITERATIONS.changeStep,
				(int)IwoParameter.NUMBER_OF_ITERATIONS.upperBound));
	}

	@Override
	public int hashCode(){
		return 1 + IwoParameter.NUMBER_OF_ITERATIONS.toString().hashCode();
	}
}
