package pl.edu.pwr.aic.dmp.metaEA;
import org.coinor.opents.Move;
import org.coinor.opents.ObjectiveFunction;
import org.coinor.opents.Solution;

import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.metaEA.Parameters.DmpParametersSolution;
import pl.edu.pwr.aic.dmp.utils.IwoParameters;

public class AlgorithmTuner {
	@SuppressWarnings("unused")
	public static void main(String[] args){
        // Initialize our objects
//        java.util.Random r = new java.util.Random( 12345 );
//        double[][] customers = new double[20][2];
//        for( int i = 0; i < 20; i++ )
//            for( int j = 0; j < 2; j++ )
//                customers[i][j] = r.nextDouble()*200;
//        
		Solution s =  new DmpParametersSolution(new IwoCore(), new IwoParameters().setSaneDefaults());
		Move m = new DmpParamsMove();
        ObjectiveFunction objFunc = new IwoObjectiveFunction();
//        Solution initialSolution  = new MySolution( customers );
//        MoveManager   moveManager = new MyMoveManager();
//        TabuList         tabuList = new SimpleTabuList( 7 ); // In OpenTS package
//        
//        TabuSearch tabuSearch = new SingleThreadedTabuSearch(
//                initialSolution,
//                moveManager,
//                objFunc,
//                tabuList,
//                new BestEverAspirationCriteria(), // In OpenTS package
//                false ); // maximizing = yes/no; false means minimizing
	}
}
