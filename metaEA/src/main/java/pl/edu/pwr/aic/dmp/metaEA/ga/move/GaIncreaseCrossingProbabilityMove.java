package pl.edu.pwr.aic.dmp.metaEA.ga.move;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.ga.parameters.GaParameter;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.utils.GaParameters;

public class GaIncreaseCrossingProbabilityMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		GaParameters params = (GaParameters)((DmpParametersSolution)soln).getParameters();
		params.setCrossingProbability(DmpParamsMoveUtil.getIncreasedDouble(
				params.getCrossingProbability(),
				GaParameter.CROSSING_PROBABILITY.upperBound));
	}
	
	@Override
	public int hashCode(){
		return 1 + GaParameter.CROSSING_PROBABILITY.toString().hashCode();
	}

}
