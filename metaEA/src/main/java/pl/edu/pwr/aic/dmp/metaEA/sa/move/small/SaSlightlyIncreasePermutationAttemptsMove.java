package pl.edu.pwr.aic.dmp.metaEA.sa.move.small;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.utils.SaParameters;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.metaEA.sa.parameters.SaParameter;

public class SaSlightlyIncreasePermutationAttemptsMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		SaParameters params = (SaParameters)((DmpParametersSolution)soln).getParameters();
		params.setPermutationAttempts(DmpParamsMoveUtil.getSlightlyIncreasedInt(
				params.getPermutationAttempts(),
				(int)SaParameter.PERMUTATION_ATTEMPTS.changeStep,
				(int)SaParameter.PERMUTATION_ATTEMPTS.upperBound));
	}
	
	@Override
	public int hashCode(){
		return 1 + SaParameter.PERMUTATION_ATTEMPTS.toString().hashCode();
	}

}
