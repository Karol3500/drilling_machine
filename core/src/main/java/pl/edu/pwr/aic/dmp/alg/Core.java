package pl.edu.pwr.aic.dmp.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.Parameters;
import pl.edu.pwr.aic.dmp.utils.UnitResult;

public abstract class Core extends Thread {
	City startCity;
	int drillChangeInterval;
	protected List<City> cities;
	private long startTime;
	private long stopTime;
	protected Specimen bestSpecimen;
	protected int bestGeneration;
	private String message;
	private UnitResult result;
	protected String algorithmName;
	protected Parameters algorithmParameters;
	protected List<Specimen> tournament;
	protected List<Specimen> ranking;
	protected List<Specimen> population;
	protected double bestRouteLength = Double.MAX_VALUE;
	
	@Override
	public void run(){
		prepareFreshSetup();
		runAlg();
		stopTime=System.currentTimeMillis();
		result.setExecutionTimeInSeconds((stopTime-startTime)/1000d);
		result.setBestRouteLength(bestSpecimen.getRouteLength());
		result.setBestRoute(bestSpecimen.getRoute());
	}
	
	public void prepareFreshSetup(){
		startTime = System.currentTimeMillis();
		stopTime = 0;
		result = new UnitResult();
		setupSaneParametersIfNotGiven();
	}
	
	abstract void runAlg();

	public void showEffects() {
		message="";
		addLine("============================================================================================================");
		addDate();
		String temp = "";
		addLine(">>>"+algorithmName+" finished " + temp + "with result:");
		addPhrase("Algorithm working time: " + result.getExecutionTimeInSeconds() + " s");
		newLine();
		addPhrase("Parameters: " + algorithmParameters);
		newLine();
		addLine("Drill change interval: " + drillChangeInterval);
		addLine("Route length: " + result.getBestRouteLength());
		String tempS = "";
		addLine("Generation with best specimen found: " + bestGeneration + tempS);
		addPhrase("Best found route: " + result.getBestRoute());
		newLine();
	
		addLine("============================================================================================================");
		System.out.println(message);
	}

	@Override
	public Object clone(){
		Core c = this.getNewInstance();
		c.setDrillChangeInterval(drillChangeInterval);
		c.setCities(cities);
		c.startCity = startCity;
		c.algorithmParameters = algorithmParameters;
		return c;
	}
	
	protected abstract Core getNewInstance();

	private void addPhrase(String s) {
		message += s;
	}

	private void addDate() {
		message += new Date();
	}

	private void addLine(String s) {
		addPhrase(s);
		newLine();
	}

	private void newLine() {
		message += "\n";
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public Specimen getBestSpecimen() {
		return bestSpecimen;
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

	public Parameters getAlgorithmParameters() {
		return algorithmParameters;
	}
	
	public void setAlgorithmParameters(Parameters algorithmParameters){
		this.algorithmParameters = algorithmParameters;
	}
	
	public City getStartCity() {
		return startCity;
	}

	public void setStartCity(City startCity) {
		this.startCity = startCity;
	}
	
	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	
	protected abstract boolean areProperParametersGiven() ;

	protected List<Specimen> getClonedPopulation() {
		List<Specimen> wynik= getNewSpecimenList();
		for(Specimen os:population){
			wynik.add(os.clone());
		}
		return wynik;
	}

	protected List<Specimen> getNewSpecimenList() {
		return new ArrayList<Specimen>();
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
	
	public Specimen getInitialSolution(){
		return new Specimen(cities, startCity, drillChangeInterval);
	}
	
	public Specimen getRandomSolution(){
		Specimen specimen = new Specimen(cities, startCity, drillChangeInterval);
		specimen.shuffleRoute();
		return specimen;
	}
	
	protected List<Specimen> reduceShuffledPopulationUsingTournament(
			List<Specimen> previouslyShuffledSpecimens, int maxPopulationSize) {
		while(shouldHalfOfPopulationBeRemovedBecauseOfTooManyTournaments(previouslyShuffledSpecimens.size(),maxPopulationSize)){
			wipeHalfPopulation(previouslyShuffledSpecimens);
		}
		int specForRem = calculateNumberOfSpecimensForRemoval(previouslyShuffledSpecimens.size(),maxPopulationSize);
		if(specForRem == 0){
			return previouslyShuffledSpecimens;
		}
		ArrayList<Specimen> specForRemoval = new ArrayList<>(specForRem);
		int specimensPerTournament = previouslyShuffledSpecimens.size()/specForRem;
	    for (int i = 0; i < specForRem-1; i++) {
	    	int fromIndex = i*specimensPerTournament;
			int toIndex = (i+1)*specimensPerTournament;
			determineLooser(previouslyShuffledSpecimens.subList(fromIndex, toIndex), specForRemoval);
	    }
	    determineLooser(previouslyShuffledSpecimens.subList((specForRem-1)*specimensPerTournament, previouslyShuffledSpecimens.size()), specForRemoval);
	    previouslyShuffledSpecimens.removeAll(specForRemoval);
		return previouslyShuffledSpecimens;
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
		int sForRem = calculateNumberOfSpecimensForRemoval(populationSize, maxPopulationSize);
		if(sForRem == 0 || maxPopulationSize > sForRem){
			return false;
		}
		return true;
	}

	private int calculateNumberOfSpecimensForRemoval(int populationSize, int maxPopulationSize) {
		if(populationSize > maxPopulationSize){
			return populationSize - maxPopulationSize;
		}
		return 0;
	}

	protected List<Specimen> initPopulation(int populationCount) {
		Specimen zero=new Specimen(cities, startCity, drillChangeInterval);
		List<Specimen> population = getNewSpecimenList();
		cloneAndShuffleSpecimenForPopulationCountTimes(populationCount, zero, population);
		return population;
	}

	private void cloneAndShuffleSpecimenForPopulationCountTimes(int populationCount, Specimen zero,
			List<Specimen> population) {
		for (int i = 0; i < populationCount; i++) {
			Specimen newSpecimen = zero.clone();
			newSpecimen.shuffleRoute();
			population.add(newSpecimen);
		}
	}

	protected void setBestSpecimenAndIterationIfFound(int iteration, List<Specimen> population) {
		Collections.sort(population);
		
		if (bestRouteLength > getMinRoute(population)) {
			bestGeneration=iteration;
			bestSpecimen=population.get(0).clone();
			bestRouteLength = bestSpecimen.getRouteLength();
		}
	}

	double getMinRoute(List<Specimen> population) {
		return population.get(0).getRouteLength();
	}
}
