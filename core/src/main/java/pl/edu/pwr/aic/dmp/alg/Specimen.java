package pl.edu.pwr.aic.dmp.alg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Specimen implements Comparable<Specimen>, Cloneable{
	private List<City> cities;

	private double rate;
	private double rouletteProbablity;
	private boolean isRateActual;
	private int drillChangeInterval;
	private City startCity;

	Specimen(List<City> cities, City startingCity, int drillChangeInterval){
		this.cities = cities;
		this.startCity = startingCity;
		this.isRateActual=false;
		this.drillChangeInterval = drillChangeInterval;
	}

	public List<Integer> getRoute(){
		return convertListOfCitiesToListOfCityNumbers(getRouteAsCities());
	}

	private List<Integer> convertListOfCitiesToListOfCityNumbers( List<City> cities) {
		List<Integer> route = new ArrayList<Integer>();
		for(City c : cities){
			route.add(c.getNumber());
		}
		return route;
	}
	
	private List<City> getRouteAsCities(){
		List<City> route = new ArrayList<>();
		route.add(startCity);
		for(int i=0;i<cities.size();i++){
			if(((i+1) % drillChangeInterval)==0){
				route.add(cities.get(i));
				route.add(startCity);
			} else {
				route.add(cities.get(i));
			}
		}
		route.add(startCity);
		return route;
	}

	public List<City> getCities(){
		return cities;
	}

	private void swapCities(int i, int j){
		City c1=cities.get(i);
		City c2=cities.get(j);
		cities.set(i, c2);
		cities.set(j, c1);
	}

	void shuffleRoute(){
		Collections.shuffle(cities);   
		isRateActual=false;
	}

	void addCity(City m){
		cities.add(m);
		isRateActual=false;
	}
	void deleteCity(int x){
		cities.remove(x);
		isRateActual=false;
	}
	void deleteCity(City m){
		for(int i=0;i<cities.size();i++){
			if(cities.get(i).getNumber()==m.getNumber()){
				cities.remove(i);
				break;
			}
		}
		isRateActual=false;
	}

	void setCity(int x, City t){
		cities.set(x, t);
		isRateActual=false;
	}
	
	public double getRouteLength(){
		if(isRateActual){
			return rate;
		}
		double routeLength=0d;
		
		List<City> route = getRouteAsCities();
		for(int i = 0; i < route.size()-1; i++){
			routeLength += getRouteLengthBetweenCities(route.get(i), route.get(i+1));
		}

		isRateActual=true;
		rate=routeLength;
		return rate;
	}

	private double getRouteLengthBetweenCities(City city, City city2) {
		return Math.sqrt(Math.pow(city.getX()-city2.getX(), 2) + Math.pow(city.getY()-city2.getY(), 2));
	}

	public double getP_Roulette(){
		return rouletteProbablity;
	}

	public void setP_Roulette(double p){
		rouletteProbablity=p;
	}

	public Specimen clone(){

		Specimen spec = new Specimen(new ArrayList<City>(cities), startCity , drillChangeInterval);
		spec.rate=rate;
		spec.isRateActual=isRateActual;
		spec.rouletteProbablity=rouletteProbablity;

		return spec;
	}

	@Override
	public int compareTo(Specimen t) {
		double diff=getRouteLength()-t.getRouteLength();
		int comp;
		if(diff>0){
			comp=1;
		} else if(diff<0){
			comp=-1;
		} else {
			comp=0;
		}
		return comp;
	}

	Specimen inver(Specimen specimen) {
		int startCity = ThreadLocalRandom.current().nextInt(cities.size()-1);
				int endCity = ThreadLocalRandom.current().nextInt(cities.size()-startCity)+startCity;//Possibly could be changed to nextInt(startCity, cities.size())
				for ( int start = startCity, end = endCity ;
						start < startCity+((endCity-startCity)/2);
						start++, end-- )
				{
					specimen.swapCities(start, end );
				}
				specimen.isRateActual=false;
		return specimen;
	}
	
	Specimen returnMutatedClone() {
		return inver(this.clone());
	}
}
