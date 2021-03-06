package pl.edu.pwr.aic.dmp.metaEA.iwo.move.small;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.metaEA.iwo.parameters.IwoParameter;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;

public class IwoSmallDecreaseMaxSeedNumberMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		IwoParameters params = (IwoParameters)((DmpParametersSolution)soln).getParameters();
		params.setMaxSeedNumber(DmpParamsMoveUtil.getSlightlyDecreasedInt(
				params.getMaxSeedNumber(),
				(int)IwoParameter.MAX_SEED_NUMBER.changeStep,
				params.getMinSeedNumber()));
	}

	@Override
	public int hashCode(){
		return IwoParameter.MAX_SEED_NUMBER.toString().hashCode();
	}
}
