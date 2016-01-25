package pl.edu.pwr.aic.dmp.metaEA.iwo.move;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.metaEA.iwo.Parameters.IwoParameter;
import pl.edu.pwr.aic.dmp.utils.IwoParameters;

public class IwoDecreaseIterationsMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		IwoParameters parameters = (IwoParameters)((DmpParametersSolution)soln).getParameters();
		parameters.setNumberOfIterations(DmpParamsMoveUtil.getDecreasedInt(
				parameters.getNumberOfIterations(),
				(int)IwoParameter.NUMBER_OF_ITERATIONS.lb));
	}

	@Override
	public int hashCode(){
		return IwoParameter.NUMBER_OF_ITERATIONS.toString().hashCode();
	}
}
