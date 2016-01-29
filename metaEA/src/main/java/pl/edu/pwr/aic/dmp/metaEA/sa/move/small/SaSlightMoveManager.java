package pl.edu.pwr.aic.dmp.metaEA.sa.move.small;

import org.coinor.opents.Move;
import org.coinor.opents.MoveManager;
import org.coinor.opents.Solution;

public class SaSlightMoveManager implements MoveManager {
	private static final long serialVersionUID = 1L;

	@Override
	public Move[] getAllMoves(Solution solution) {
		return new Move[] {
				new SaSlightlyDecreaseCoolingCoefficientMove(),
				new SaSlightlyDecreaseCyclesNumberMove(),
				new SaSlightlyDecreasePermutationAttemptsMove(),
				new SaSlightlyDecreaseStartTemperatureMove(),
				new SaSlightlyIncreaseCoolingCoefficientMove(),
				new SaSlightlyIncreaseCyclesNumberMove(),
				new SaSlightlyIncreasePermutationAttemptsMove(),
				new SaSlightlyIncreaseStartTemperatureMove()
		};
	}

}
