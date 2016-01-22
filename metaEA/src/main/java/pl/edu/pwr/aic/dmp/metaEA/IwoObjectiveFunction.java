package pl.edu.pwr.aic.dmp.metaEA;

import org.coinor.opents.Move;
import org.coinor.opents.ObjectiveFunction;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParametersSolution;

public class IwoObjectiveFunction implements ObjectiveFunction {

	private static final long serialVersionUID = 1L;

	@Override
	public double[] evaluate(Solution soln, Move move) {
		Core algorithm = ((DmpParametersSolution) soln).getAlgorithm();
		algorithm.setParameters(((DmpParametersSolution) soln).getParameters());
		double startTime = System.currentTimeMillis();
		algorithm.run();
		return new double[]{algorithm.getBestSpecimen().getRouteLength(),System.currentTimeMillis()-startTime};
	}

}
