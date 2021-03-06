package pl.edu.pwr.aic.dmp.metaEA.iwo.move.small;

import org.coinor.opents.Move;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.utils.IwoParameters;
import pl.edu.pwr.aic.dmp.metaEA.iwo.parameters.IwoParameter;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParamsMoveUtil;

public class IwoSmallDecreaseMaxSpecimenInPopulationMove implements Move {
	private static final long serialVersionUID = 1L;

	@Override
	public void operateOn(Solution soln) {
		IwoParameters params = (IwoParameters)((DmpParametersSolution)soln).getParameters();
		params.setMaxSpecimenInPopulation(DmpParamsMoveUtil.getSlightlyDecreasedInt(
				params.getMaxSpecimenInPopulation(),
				(int)IwoParameter.MAX_SPECIMEN_IN_POPULATION.changeStep,
				(int)IwoParameter.MAX_SPECIMEN_IN_POPULATION.lowerBound));
	}

	@Override
	public int hashCode(){
		return IwoParameter.MAX_SPECIMEN_IN_POPULATION.toString().hashCode();
	}
}
