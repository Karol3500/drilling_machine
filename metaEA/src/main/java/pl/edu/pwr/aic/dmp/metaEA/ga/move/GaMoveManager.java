package pl.edu.pwr.aic.dmp.metaEA.ga.move;

import org.coinor.opents.Move;
import org.coinor.opents.MoveManager;
import org.coinor.opents.Solution;

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
				new GaIncreasePopulationCountMove()
		};
	}

}
