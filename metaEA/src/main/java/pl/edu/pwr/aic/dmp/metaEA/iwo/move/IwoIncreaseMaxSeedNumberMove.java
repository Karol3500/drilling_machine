package pl.edu.pwr.aic.dmp.metaEA.iwo.move;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.metaEA.iwo.Parameters.IwoParameter;
import pl.edu.pwr.aic.dmp.utils.IwoParameters;

public class IwoIncreaseMaxSeedNumberMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		IwoParameters params = (IwoParameters)((DmpParametersSolution)soln).getParameters();
		params.setMaxSeedNumber(DmpParamsMoveUtil.getIncreasedInt(
				params.getMaxSeedNumber(),
				(int)IwoParameter.MAX_SEED_NUMBER.ub));
	}

	@Override
	public int hashCode(){
		return 1 + IwoParameter.MAX_SEED_NUMBER.toString().hashCode();
	}
}
