package pl.edu.pwr.aic.dmp.metaEA.iwo.move;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.iwo.parameters.IwoParameter;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.utils.IwoParameters;

public class IwoIncreaseMaxSeedNumberMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		IwoParameters params = (IwoParameters)((DmpParametersSolution)soln).getParameters();
		params.setMaxSeedNumber(DmpParamsMoveUtil.getRandomlyIncreasedInt(
				params.getMaxSeedNumber(),
				(int)IwoParameter.MAX_SEED_NUMBER.upperBound));
	}

	@Override
	public int hashCode(){
		return 1 + IwoParameter.MAX_SEED_NUMBER.toString().hashCode();
	}
}
