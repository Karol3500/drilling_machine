package pl.edu.pwr.aic.dmp.metaEA.iwo.move;

import org.coinor.opents.Move;
import org.coinor.opents.MoveManager;
import org.coinor.opents.Solution;

public class IwoMoveManager implements MoveManager {

	private static final long serialVersionUID = 1L;

	@Override
	public Move[] getAllMoves(Solution solution) {
		return new Move[]{
			new IwoDecreaseFinalTransformationsPerSeedMove(),
			new IwoDecreaseInitialTransformationsPerSeedMove(),
			new IwoDecreaseIterationsMove(),
			new IwoDecreaseMaxSeedNumberMove(),
			new IwoDecreaseMinSeedNumberMove(),
			new IwoDecreaseMinSpecimenInPopulationMove(),
			new IwoDecreaseNonLinearCoefficientMove(),
			new IwoIncreaseFinalTransformationsPerSeedMove(),
			new IwoIncreaseInitialTransformationsPerSeedMove(),
			new IwoIncreaseIterationsMove(),
			new IwoIncreaseMaxSeedNumberMove(),
			new IwoIncreaseMinSeedNumberMove(),
			new IwoIncreaseMinSpecimenInPopulationMove(),
			new IwoIncreaseNonLinearCoefficientMove()
		};
	}

}
