package pl.edu.pwr.aic.dmp.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import pl.edu.pwr.aic.dmp.alg.utils.UnitResult;
import pl.edu.pwr.aic.dmp.utils.Parameters;

public abstract class Core extends Thread {
	boolean abort;
	City startCity;
	int drillChangeInterval;
	protected List<City> cities;
	protected long startTime;
	protected long stopTime;
	protected Specimen bestSpecimen;
	protected int bestGeneration;
	protected String message;
	protected UnitResult result;
	protected String algorithmName;
	protected Parameters algorithmParameters;
	protected List<Specimen> tournament;
	protected List<Specimen> ranking;
	protected List<Specimen> population;
	private Random random;
	
	protected Core(){
		result = new UnitResult();
		random = new Random();
	}
	
	public void setParameters(Parameters algorithmParameters){
		this.algorithmParameters = algorithmParameters;
	}
	
	@Override
	public void run(){
		startTime=System.currentTimeMillis();
		setupSaneParametersIfNotGiven();
		runAlg();
		stopTime=System.currentTimeMillis();
		result.setExecutionTimeInSeconds((stopTime-startTime)/1000d);
		result.setBestRouteLength(bestSpecimen.getRouteLength());
		result.setPermutation(bestSpecimen.getBestRoute());
		
		showEffects();
	}
	
	abstract void runAlg();
	
	public void abort() {
		abort = true;
	}
	
	public City getstartCity(){
		return startCity;
	}

	public void showEffects() {
		message="";
		addLine("============================================================================================================");
		addDate();
		String temp = "";
		if (abort == true) {
			temp = "(because of abort) ";
		}
		addLine(">>>"+algorithmName+" finished " + temp + "with result:");
		addPhrase("Algorithm working time: " + result.getExecutionTimeInSeconds() + " s");
		newLine();
		addLine("Drill change interval: " + drillChangeInterval);
		addLine("Route length: " + result.getBestRouteLength());
		String tempS = "";
		addLine("Generation with best specimen found: " + bestGeneration + tempS);
		addPhrase("Best found route: " + result.getPermutation());
		newLine();
	
		addLine("============================================================================================================");
		System.out.println(message);
	}

	public void addPhrase(String s) {
		message += s;
	}

	public void addDate() {
		message += new Date();
	}

	public void addLine(String s) {
		addPhrase(s);
		newLine();
	}

	public void newLine() {
		message += "\n";
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public UnitResult getResult() {
		return result;
	}
	
	private void setupSaneParametersIfNotGiven() {
		if(!areProperParametersGiven()){
			this.algorithmParameters.setSaneDefaults();
		}
	}

	public int getDrillChangeInterval() {
		return drillChangeInterval;
	}

	public void setDrillChangeInterval(int drillChangeInterval) {
		this.drillChangeInterval = drillChangeInterval;
	}

	protected abstract boolean areProperParametersGiven() ;

	protected List<Specimen> getClonedPopulation() {
		List<Specimen> wynik= new LinkedList<Specimen>();
		for(Specimen os:population){
			wynik.add(os.clone());
		}
		return wynik;
	}

	protected Specimen selectionTournament() {
		if(!tournament.isEmpty()){
			Specimen os=tournament.get(0);
			tournament.remove(0);
			return os;
		}
	
		int przedzial=2+(int)(Math.random()*((population.size()/2)-1));
		ArrayList<ArrayList<Specimen>> turnieje=new ArrayList<ArrayList<Specimen>>();
		turnieje.add(new ArrayList<Specimen>());
	
		List<Specimen> populacja_rand=getClonedPopulation();
		Collections.shuffle(populacja_rand);
		int licznik_grupy=przedzial;
		for(int i=0;i<populacja_rand.size();i++){
			if(licznik_grupy==0){
				licznik_grupy=przedzial;
				turnieje.add(new ArrayList<Specimen>());
			}
			turnieje.get(turnieje.size()-1).add(populacja_rand.get(i));
			licznik_grupy--;
		}
	
		for(ArrayList<Specimen> grupa:turnieje){
			Collections.sort(grupa);
			tournament.add(grupa.get(0));
		}
	
		Specimen os=tournament.get(0);
		tournament.remove(0);
		return os;
	}
	
	protected List<Specimen> reduceShuffledPopulationUsingTournament(
			List<Specimen> specimens, int maxPopulationSize) {
		while(shouldHalfOfPopulationBeRemovedBecauseOfTooManyTournaments(specimens.size(),maxPopulationSize)){
			wipeHalfPopulation(specimens);
		}
		int specForRem = calculateNumberOfSpecimenForRemoval(specimens.size(),maxPopulationSize);
		if(specForRem == 0){
			return specimens;
		}
		ArrayList<Specimen> specForRemoval = new ArrayList<>(specForRem);
		int specimensPerTournament = specimens.size()/specForRem;
	    for (int i = 0; i < specForRem-1; i++) {
	    	int fromIndex = i*specimensPerTournament;
			int toIndex = (i+1)*specimensPerTournament;
			determineLooser(specimens.subList(fromIndex, toIndex), specForRemoval);
	    }
	    determineLooser(specimens.subList((specForRem-1)*specimensPerTournament, specimens.size()), specForRemoval);
	    specimens.removeAll(specForRemoval);
		return specimens;
	}

	private void determineLooser(List<Specimen> tournamentParticipators, ArrayList<Specimen> specForRemoval) {
		Collections.sort(tournamentParticipators);
		    Specimen worstSpecimen = tournamentParticipators.get(tournamentParticipators.size()-1);
			specForRemoval.add(worstSpecimen);
	}

	protected void wipeHalfPopulation(List<Specimen> specimens) {
		int i=0;
		while(i++ < specimens.size()-1){
			if(specimens.get(i-1).getRouteLength() < specimens.get(i).getRouteLength()){
				specimens.remove(i); 
			}
			else{
				specimens.remove(i-1);
			}
		}
	}

	private boolean shouldHalfOfPopulationBeRemovedBecauseOfTooManyTournaments(int populationSize, int maxPopulationSize) {
		int sForRem = calculateNumberOfSpecimenForRemoval(populationSize, maxPopulationSize);
		if(sForRem == 0 || maxPopulationSize > sForRem){
			return false;
		}
		return true;
	}

	private int calculateNumberOfSpecimenForRemoval(int populationSize, int maxPopulationSize) {
		if(populationSize > maxPopulationSize){
			return populationSize - maxPopulationSize;
		}
		return 0;
	}

	protected int calculateIndexOfSpecimenToBeRemoved(int populationSize, int specimensToBeRemoved, int removedSpecimenCounter) {
		double beginningOfRange = populationSize*removedSpecimenCounter/(double)specimensToBeRemoved;
		double endOfRange = populationSize*(removedSpecimenCounter+1)/(double)specimensToBeRemoved;
		int rand = (int)Math.ceil(random.nextInt(((int)Math.ceil(endOfRange - beginningOfRange))) + beginningOfRange)-removedSpecimenCounter;
		return rand;
	}
}
