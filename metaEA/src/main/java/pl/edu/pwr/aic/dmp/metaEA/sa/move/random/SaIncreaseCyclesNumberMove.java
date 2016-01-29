package pl.edu.pwr.aic.dmp.metaEA.sa.move.random;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.metaEA.sa.parameters.SaParameter;
import pl.edu.pwr.aic.dmp.utils.SaParameters;

public class SaIncreaseCyclesNumberMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		SaParameters params = (SaParameters)((DmpParametersSolution)soln).getParameters();
		params.setCyclesNumber(DmpParamsMoveUtil.getRandomlyIncreasedInt(
				params.getCyclesNumber(),
				(int)SaParameter.CYCLES_NUMBER.upperBound));
	}
	
	@Override
	public int hashCode(){
		return 1 + SaParameter.CYCLES_NUMBER.toString().hashCode();
	}

}
