package pl.edu.pwr.aic.dmp.metaEA;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.coinor.opents.Move;
import org.coinor.opents.ObjectiveFunction;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.metaEA.parameters.DmpParametersSolution;

class DmpObjectiveFunction implements ObjectiveFunction {

	private static final long serialVersionUID = 1L;

	@Override
	public double[] evaluate(Solution soln, Move move) {
		System.out.print(".");
		Solution solution = soln;
		if(move != null){
			solution = getSolutionCloneMoved(soln, move);
		}
		DmpParametersSolution sol = (DmpParametersSolution)solution; 
		Core algorithm = sol.getAlgorithm();
		algorithm.setAlgorithmParameters(sol.getParameters());
		double startTime = System.currentTimeMillis();
		return performExperiment(sol, algorithm, startTime);
	}

	private double[] performExperiment(DmpParametersSolution sol, Core algorithm, double startTime) {
		Future<?> future = prepareFutureObject(algorithm);
		try{
			future.get(AlgorithmTuner.maxSecondsPerTest,TimeUnit.SECONDS);
			if(sol.getObjectiveValue() == null){
				return new double[]{Double.MAX_VALUE};
			}
			double[] calculatedObjectiveValue = getCalculatedObjectiveValue(algorithm, startTime);
			SolutionsSingleton.addResult(prepareExperimentResult(sol, startTime, calculatedObjectiveValue[0]));
			return calculatedObjectiveValue;
		} catch(TimeoutException e) {
			return cancelTask(future);
		} catch(Exception e){
			return cancelTaskAndPrintStackTrace(future, e);
		}
	}

	private double[] getCalculatedObjectiveValue(Core algorithm, double startTime) {
		double routeLength = algorithm.getBestSpecimen().getRouteLength();
		double timeElapsed = System.currentTimeMillis()-startTime;
		return new double[]{getObjectiveValue(routeLength, timeElapsed)};
	}

	private double[] cancelTaskAndPrintStackTrace(Future<?> future, Exception e) {
		future.cancel(true);
		e.printStackTrace();
		return new double[]{Double.MAX_VALUE};
	}

	private double[] cancelTask(Future<?> future) {
		future.cancel(true);
		System.out.println("Evaluation took too long, abandoning.");
		return new double[]{Double.MAX_VALUE};
	}

	private Future<?> prepareFutureObject(Core algorithm) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<?> future = executor.submit(algorithm);
		return future;
	}

	private TuningExperimentResult prepareExperimentResult(DmpParametersSolution sol, double startTime, 
			double objFunVal) {
		return new TuningExperimentResult(sol.getParameters(),
				sol.getAlgorithm().getBestSpecimen().getRouteLength(),
				System.currentTimeMillis()-startTime,
				objFunVal);
	}

	private double getObjectiveValue(double routeLength, double timeElapsed) {
		//return (8*routeLength+2*timeElapsed)/10;
		return routeLength;
	}

	private Solution getSolutionCloneMoved(Solution sol, Move move) {
		Solution s = (Solution)sol.clone();
		move.operateOn(s);
		return s;
	}
}
