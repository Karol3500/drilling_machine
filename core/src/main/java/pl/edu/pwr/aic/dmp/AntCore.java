package pl.edu.pwr.aic.dmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cern.jet.random.Uniform;

public final class AntCore extends Core {
	Record startRecord;
	WalkedWay bestway;
	int bestAnt;
	long start; // start licznika
	long stop; // stop licznika
	boolean detailedStatsOn;

	String message;
	// parametry
	// greedy
	public double ALPHA;// = -0.2d;
	// rapid selection
	public double BETA;// = 9.6d;

	// heuristic parameters
	public double Q;// = 0.0001d; // somewhere between 0 and 1
	public double PHEROMONE_PERSISTENCE;// = 0.3d; // between 0 and 1
	public double INITIAL_PHEROMONES;// = 0.8d; // can be anything

	// use power of 2
	public int numOfAgents;// = 2048 * 20;

	private static final int poolSize = Runtime.getRuntime()
			.availableProcessors();

	private Uniform uniform;

	private final ExecutorService threadPool = Executors
			.newFixedThreadPool(poolSize);

	private final ExecutorCompletionService<WalkedWay> agentCompletionService = new ExecutorCompletionService<WalkedWay>(
			threadPool);

	final double[][] matrix;
	double[] startDistances;
	final double[][] invertedMatrix;
	private final double[][] pheromones;
	private final Object[][] mutexes;

	public AntCore(double alpha, double beta, double q, double pheromonePersistence,
			double initialPheromones, int numberOfAgents, boolean detailedStatsOn, String filePath) {

		ALPHA = alpha;
		BETA = beta;
		Q = q;
		PHEROMONE_PERSISTENCE = pheromonePersistence;
		INITIAL_PHEROMONES = initialPheromones;
		numOfAgents = numberOfAgents;
		this.detailedStatsOn = detailedStatsOn;

		matrix = readMatrixFromFile(filePath);
		invertedMatrix = invertMatrix();
		pheromones = initializePheromones();
		mutexes = initializeMutexObjects();
		// (double min, double max, int seed)
		uniform = new Uniform(0, matrix.length - 1,
				(int) System.currentTimeMillis());
	}

	private final Object[][] initializeMutexObjects() {
		final Object[][] localMatrix = new Object[matrix.length][matrix.length];
		int rows = matrix.length;
		for (int columns = 0; columns < matrix.length; columns++) {
			for (int i = 0; i < rows; i++) {
				localMatrix[columns][i] = new Object();
			}
		}

		return localMatrix;
	}

	final double readPheromone(int x, int y) {
		// double p;
		// synchronized (mutexes[x][y]) {
		// p = pheromones[x][y];
		// }
		// return p;
		return pheromones[x][y];
	}

	final void adjustPheromone(int x, int y, double newPheromone) {
		synchronized (mutexes[x][y]) {
			final double result = calculatePheromones(pheromones[x][y],
					newPheromone);
			if (result >= 0.0d) {
				pheromones[x][y] = result;
			} else {
				pheromones[x][y] = 0;
			}
		}
	}

	private final double calculatePheromones(double current, double newPheromone) {
		final double result = (1 - PHEROMONE_PERSISTENCE) * current
				+ newPheromone;
		return result;
	}

	final void adjustPheromone(int[] way, double newPheromone) {
		synchronized (pheromones) {
			for (int i = 0; i < way.length - 1; i++) {
				pheromones[way[i]][way[i + 1]] = calculatePheromones(
						pheromones[way[i]][way[i + 1]], newPheromone);
			}
			pheromones[way[way.length - 1]][way[0]] = calculatePheromones(
					pheromones[way.length - 1][way[0]], newPheromone);
		}
	}

	private final double[][] initializePheromones() {
		final double[][] localMatrix = new double[matrix.length][matrix.length];
		int rows = matrix.length;
		for (int columns = 0; columns < matrix.length; columns++) {
			for (int i = 0; i < rows; i++) {
				localMatrix[columns][i] = INITIAL_PHEROMONES;
			}
		}

		return localMatrix;
	}

	private final double[][] readMatrixFromFile(String filePath) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(filePath)));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		final LinkedList<Record> records = new LinkedList<Record>();

		startRecord = null;
		boolean readAhead = false;
		String line;
		try {
			while ((line = br.readLine()) != null) {

				if (line.equals("EOF")) {
					break;
				}

				if (readAhead && !line.trim().equals("")) {
					Scanner sc = new Scanner(line);
					while (!sc.hasNextInt()) {
						sc.next();
					}
					double x = sc.nextDouble();
					double y = sc.nextDouble();

					// String[] split = line.trim().split(" ");
					if (startRecord == null) {
						startRecord = new Record(x, y);
						startCity = new City(1, startRecord.x, startRecord.y);
					} else {
						records.add(new Record(x, y));
					}
					sc.close();
				}

				if (line.equals("NODE_COORD_SECTION")) {
					readAhead = true;
				}
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		final double[][] localMatrix = new double[records.size()][records
		                                                          .size()];

		int rIndex = 0;
		for (Record r : records) {
			int hIndex = 0;
			for (Record h : records) {
				localMatrix[rIndex][hIndex] = calculateEuclidianDistance(r.x,
						r.y, h.x, h.y);
				hIndex++;
			}
			rIndex++;
		}
		startDistances = new double[records.size()];
		int sIndex = 0;
		for (Record s : records) {
			startDistances[sIndex] = calculateEuclidianDistance(startRecord.x,
					startRecord.y, s.x, s.y);
			sIndex++;
		}

		return localMatrix;
	}

	private final double[][] invertMatrix() {
		double[][] local = new double[matrix.length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				local[i][j] = invertDouble(matrix[i][j]);
			}
		}
		return local;
	}

	private final double invertDouble(double distance) {
		if (distance == 0)
			return 0;
		else
			return 1.0d / distance;
	}

	private final double calculateEuclidianDistance(double x1, double y1,
			double x2, double y2) {
		return Math
				.abs((Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))));
	}

	public void run() {
		WalkedWay bestDistance = null;

		int agentsSend = 0;
		int agentsDone = 0;
		int agentsWorking = 0;
		start = System.currentTimeMillis(); // start licznika czasu
		for (int agentNumber = 0; !abort && agentNumber < numOfAgents; agentNumber++) {
			agentCompletionService.submit(new AntAgent(this,
					getGaussianDistributionRowIndex()));
			agentsSend++;
			agentsWorking++;
			while (!abort && agentsWorking >= poolSize) {
				WalkedWay way = null;
				try {
					way = agentCompletionService.take().get();
					if (detailedStatsOn) {
						String line = "Mrówka #" + agentsDone
								+ " -> długość trasy: "
								+ round(way.distance, 2);
						addLine(line);
					}
				} catch (InterruptedException | ExecutionException e) {

					e.printStackTrace();
				}
				if (bestDistance == null
						|| way.distance < bestDistance.distance) {
					bestDistance = way;
					bestAnt = agentNumber;
				}

				agentsDone++;
				agentsWorking--;
			}
		}
		final int left = agentsSend - agentsDone;
		// System.out.println("Waiting for " + left +
		// " agents to finish their random walk!");

		for (int i = 0; !abort && i < left; i++) {
			WalkedWay way = null;
			try {
				way = agentCompletionService.take().get();
				int agentnum=agentsSend-left+i;
				if (detailedStatsOn) {
					String line = "Mrówka #" + agentnum
							+ " -> długość trasy: "
							+ round(way.distance, 2);
					addLine(line);
				}

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			if (bestDistance == null || way.distance < bestDistance.distance) {
				bestDistance = way;
				bestAnt = agentsDone + i;
			}
		}

		threadPool.shutdownNow();
		bestway = bestDistance;
		stop = System.currentTimeMillis(); // stop licznika czasu
		showEffects();
	}

	private final int getGaussianDistributionRowIndex() {
		return uniform.nextInt();
	}

	static class Record {
		double x;
		double y;

		public Record(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	static class WalkedWay {
		int[] way;
		double distance;

		public WalkedWay(int[] way, double distance) {
			super();
			this.way = way;
			this.distance = distance;
		}
	}

	public void showEffects() {
		// zapis wynik�w do logu i przebiegu
		addLine("============================================================================================================");
		addDate();
		String temp = "";
		if (abort == true) {
			temp = "(z powodu przerwania) ";
		}
		addLine(">>> Algorytm MRÓWKOWY zakończył pracę " + temp
				+ "z następującym wynikiem:");
		addPhrase("Czas pracy algorytmu: " + (stop - start) / 1000.0 + " s");
		newLine();
		addLine("Interwał wymiany wiertła: " + drillChangeInterval);
		// addLine("Liczba wys�anych mr�wek="+numOfAgents);
		addLine("Długość trasy: " + round(bestway.distance, 2));
		String tempS = "";
		addLine("Mrówka która znalazła najlepszą trasę: " + bestAnt + tempS);
		addPhrase("Najlepsza trasa: " + printRoute(bestway.way));
		newLine();

		addLine("============================================================================================================");
		System.gc(); 
	}

	public void addPhrase(String s){
		message += s;
	}

	public void addDate(){
		message += new Date();
	}

	public void addLine(String s){
		addPhrase(s);
		newLine();
	}

	public void newLine(){
		message += "\n";
	}

	public double round(double d, int pos) {
		if (Double.isInfinite(d) || Double.isNaN(d)) {
			return -1;
		} else {
			return new BigDecimal(d).setScale(pos, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		}
	}

	public String printRoute(int[] way) {
		if (way == null) {
			return "";
		}

		String s="";

		//start ze startowego
		s+="("+startCity.getNumber()+") ";

		/*		 for(int i=0;i<way.length;i++) {
			 s+=(way[i]+2)+" ";
		 }*/


		for(int i=0;i<way.length;i++) {
			if(((i+1) % drillChangeInterval)==0){
				//wykonaj powr�t do punktu startowego oraz przejdz do nast�pnego punktu z punktu startowego
				s+="<<"+startCity.getNumber()+">> ";
				s+=(way[i]+2)+" ";
			} else { 
				s+=(way[i]+2)+" ";
			}
		}
		//na koniec wracamy do punktu startowego
		s+="("+startCity.getNumber()+") ";


		return s;
	}

}