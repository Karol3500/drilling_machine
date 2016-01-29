package pl.edu.pwr.aic.dmp.metaEA.sa.move.random;

import org.coinor.opents.Move;
import org.coinor.opents.MoveManager;
import org.coinor.opents.Solution;

public class SaRandomMoveManager implements MoveManager {
	private static final long serialVersionUID = 1L;

	@Override
	public Move[] getAllMoves(Solution solution) {
		return new Move[] {
				new SaDecreaseCoolingCoefficientMove(),
				new SaDecreaseCyclesNumberMove(),
				new SaDecreasePermutationAttemptsMove(),
				new SaDecreaseStartTemperatureMove(),
				new SaIncreaseCoolingCoefficientMove(),
				new SaIncreaseCyclesNumberMove(),
				new SaIncreasePermutationAttemptsMove(),
				new SaIncreaseStartTemperatureMove()
		};
	}

}
