package pl.edu.pwr.aic.dmp.metaEA.sa.move;

import org.coinor.opents.Move;
import org.coinor.opents.MoveManager;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.sa.move.random.SaDecreaseCoolingCoefficientMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.random.SaDecreaseCyclesNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.random.SaDecreasePermutationAttemptsMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.random.SaDecreaseStartTemperatureMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.random.SaIncreaseCoolingCoefficientMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.random.SaIncreaseCyclesNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.random.SaIncreasePermutationAttemptsMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.random.SaIncreaseStartTemperatureMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.small.SaSmallDecreaseCoolingCoefficientMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.small.SaSmallDecreaseCyclesNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.small.SaSmallDecreasePermutationAttemptsMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.small.SaSmallDecreaseStartTemperatureMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.small.SaSmallIncreaseCoolingCoefficientMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.small.SaSmallIncreaseCyclesNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.small.SaSmallIncreasePermutationAttemptsMove;
import pl.edu.pwr.aic.dmp.metaEA.sa.move.small.SaSmallIncreaseStartTemperatureMove;

public class SaMoveManager implements MoveManager {
	private static final long serialVersionUID = 1L;

	@Override
	public Move[] getAllMoves(Solution solution) {
		return new Move[] {
				new SaSmallDecreaseCoolingCoefficientMove(),
				new SaSmallDecreaseCyclesNumberMove(),
				new SaSmallDecreasePermutationAttemptsMove(),
				new SaSmallDecreaseStartTemperatureMove(),
				new SaSmallIncreaseCoolingCoefficientMove(),
				new SaSmallIncreaseCyclesNumberMove(),
				new SaSmallIncreasePermutationAttemptsMove(),
				new SaSmallIncreaseStartTemperatureMove(),
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
