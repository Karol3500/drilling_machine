package pl.edu.pwr.aic.dmp.metaEA.ga.move.random;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.utils.GaParameters;
import pl.edu.pwr.aic.dmp.metaEA.ga.parameters.GaParameter;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;

public class GaDecreaseGenerationsCountMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		GaParameters params = (GaParameters)((DmpParametersSolution)soln).getParameters();
		params.setGenerationsCount(DmpParamsMoveUtil.getRandomlyDecreasedInt(
				params.getGenerationsCount(),
				(int)GaParameter.GENERATIONS_COUNT.lowerBound));
	}
	
	@Override
	public int hashCode(){
		return GaParameter.GENERATIONS_COUNT.toString().hashCode();
	}

}
