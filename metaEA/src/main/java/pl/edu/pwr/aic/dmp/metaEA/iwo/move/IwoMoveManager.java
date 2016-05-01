package pl.edu.pwr.aic.dmp.metaEA.iwo.move;

import org.coinor.opents.Move;
import org.coinor.opents.MoveManager;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoDecreaseFinalTransformationsPerSeedMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoDecreaseInitialTransformationsPerSeedMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoDecreaseIterationsMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoDecreaseMaxSeedNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoDecreaseMinSeedNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoDecreaseMinSpecimenInPopulationMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoDecreaseNonLinearCoefficientMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoIncreaseFinalTransformationsPerSeedMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoIncreaseInitialTransformationsPerSeedMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoIncreaseIterationsMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoIncreaseMaxSeedNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoIncreaseMinSeedNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoIncreaseMinSpecimenInPopulationMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.random.IwoIncreaseNonLinearCoefficientMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallDecreaseFinalTransformationsPerSeedMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallDecreaseInitialTransformationsPerSeedMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallDecreaseIterationsMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallDecreaseMaxSeedNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallDecreaseMinSeedNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallDecreaseMinSpecimenInPopulationMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallDecreaseNonLinearCoefficientMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallIncreaseFinalTransformationsPerSeedMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallIncreaseInitialTransformationsPerSeedMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallIncreaseIterationsMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallIncreaseMaxSeedNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallIncreaseMinSeedNumberMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallIncreaseMinSpecimenInPopulationMove;
import pl.edu.pwr.aic.dmp.metaEA.iwo.move.small.IwoSmallIncreaseNonLinearCoefficientMove;

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
			new IwoIncreaseNonLinearCoefficientMove(),
			
			new IwoSmallDecreaseFinalTransformationsPerSeedMove(),
			new IwoSmallDecreaseInitialTransformationsPerSeedMove(),
			new IwoSmallDecreaseIterationsMove(),
			new IwoSmallDecreaseMaxSeedNumberMove(),
			new IwoSmallDecreaseMinSeedNumberMove(),
			new IwoSmallDecreaseMinSpecimenInPopulationMove(),
			new IwoSmallDecreaseNonLinearCoefficientMove(),
			new IwoSmallIncreaseFinalTransformationsPerSeedMove(),
			new IwoSmallIncreaseInitialTransformationsPerSeedMove(),
			new IwoSmallIncreaseIterationsMove(),
			new IwoSmallIncreaseMaxSeedNumberMove(),
			new IwoSmallIncreaseMinSeedNumberMove(),
			new IwoSmallIncreaseMinSpecimenInPopulationMove(),
			new IwoSmallIncreaseNonLinearCoefficientMove(),
		};
	}
}
