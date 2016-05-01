package pl.edu.pwr.aic.dmp.metaEA.iwo.move.small;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.metaEA.iwo.parameters.IwoParameter;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;

public class IwoSmallDecreaseIterationsMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		IwoParameters parameters = (IwoParameters)((DmpParametersSolution)soln).getParameters();
		parameters.setNumberOfIterations(DmpParamsMoveUtil.getSlightlyDecreasedInt(
				parameters.getNumberOfIterations(),
				(int)IwoParameter.NUMBER_OF_ITERATIONS.changeStep,
				(int)IwoParameter.NUMBER_OF_ITERATIONS.lowerBound));
	}

	@Override
	public int hashCode(){
		return IwoParameter.NUMBER_OF_ITERATIONS.toString().hashCode();
	}
}
