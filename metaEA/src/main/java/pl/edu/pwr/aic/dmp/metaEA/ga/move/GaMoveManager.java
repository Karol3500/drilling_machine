package pl.edu.pwr.aic.dmp.metaEA.ga.move;

import org.coinor.opents.Move;
import org.coinor.opents.MoveManager;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.ga.move.random.GaDecreaseCrossingProbabilityMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.random.GaDecreaseGenerationsCountMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.random.GaDecreaseMutationProbabilityMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.random.GaDecreasePopulationCountMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.random.GaIncreaseCrossingProbabilityMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.random.GaIncreaseGenerationsCountMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.random.GaIncreaseMutationProbabilityMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.random.GaIncreasePopulationCountMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.small.GaSmallDecreaseCrossingProbabilityMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.small.GaSmallDecreaseGenerationsCountMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.small.GaSmallDecreaseMutationProbabilityMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.small.GaSmallDecreasePopulationCountMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.small.GaSmallIncreaseCrossingProbabilityMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.small.GaSmallIncreaseGenerationsCountMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.small.GaSmallIncreaseMutationProbabilityMove;
import pl.edu.pwr.aic.dmp.metaEA.ga.move.small.GaSmallIncreasePopulationCountMove;

public class GaMoveManager implements MoveManager {
	private static final long serialVersionUID = 1L;

	@Override
	public Move[] getAllMoves(Solution solution) {
		return new Move[] {
				new GaDecreaseCrossingProbabilityMove(),
				new GaIncreaseCrossingProbabilityMove(),
				new GaDecreaseGenerationsCountMove(),
				new GaIncreaseGenerationsCountMove(),
				new GaDecreaseMutationProbabilityMove(),
				new GaIncreaseMutationProbabilityMove(),
				new GaDecreasePopulationCountMove(),
				new GaIncreasePopulationCountMove(),
				new GaSmallDecreaseCrossingProbabilityMove(),
				new GaSmallIncreaseCrossingProbabilityMove(),
				new GaSmallDecreaseGenerationsCountMove(),
				new GaSmallIncreaseGenerationsCountMove(),
				new GaSmallDecreaseMutationProbabilityMove(),
				new GaSmallIncreaseMutationProbabilityMove(),
				new GaSmallDecreasePopulationCountMove(),
				new GaSmallIncreasePopulationCountMove()
		};
	}

}
