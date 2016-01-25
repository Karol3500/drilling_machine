package pl.edu.pwr.aic.dmp.metaEA.iwo.move;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParamsMoveUtil;
import pl.edu.pwr.aic.dmp.metaEA.iwo.Parameters.IwoParameter;
import pl.edu.pwr.aic.dmp.utils.IwoParameters;

public class IwoIncreaseMaxSpecimenInPopulationMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		IwoParameters params = (IwoParameters)((DmpParametersSolution)soln).getParameters();
		params.setMaxSpecimenInPopulation(DmpParamsMoveUtil.getIncreasedInt(
				params.getMaxSpecimenInPopulation(),
				(int)IwoParameter.MAX_SPECIMEN_IN_POPULATION.upperBound));
	}

	@Override
	public int hashCode(){
		return 1 + IwoParameter.MAX_SPECIMEN_IN_POPULATION.toString().hashCode();
	}
}
