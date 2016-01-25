package pl.edu.pwr.aic.dmp.metaEA.iwo.move;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.metaEA.iwo.Parameters.IwoParameter;
import pl.edu.pwr.aic.dmp.utils.IwoParameters;

public class IwoDecreaseFinalTransformationsPerSeedMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		IwoParameters params = (IwoParameters)((DmpParametersSolution)soln).getParameters();
		params.setFinalTransformationsPerSeed(DmpParamsMoveUtil.getDecreasedInt(
				params.getFinalTransformationsPerSeed(),
				(int)IwoParameter.FINAL_TRANSFORMATIONS_PER_SEED.lowerBound));
	}
	
	@Override
	public int hashCode(){
		return IwoParameter.FINAL_TRANSFORMATIONS_PER_SEED.toString().hashCode();
	}

}
