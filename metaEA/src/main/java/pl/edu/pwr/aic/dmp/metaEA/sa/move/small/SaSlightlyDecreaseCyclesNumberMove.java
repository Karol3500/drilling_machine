package pl.edu.pwr.aic.dmp.metaEA.sa.move.small;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.utils.SaParameters;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.metaEA.sa.parameters.SaParameter;

public class SaSlightlyDecreaseCyclesNumberMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		SaParameters params = (SaParameters)((DmpParametersSolution)soln).getParameters();
		params.setCyclesNumber(DmpParamsMoveUtil.getSlightlyDecreasedInt(
				params.getCyclesNumber(),
				(int)SaParameter.CYCLES_NUMBER.changeStep,
				(int)SaParameter.CYCLES_NUMBER.lowerBound));
	}
	
	@Override
	public int hashCode(){
		return SaParameter.CYCLES_NUMBER.toString().hashCode();
	}

}
