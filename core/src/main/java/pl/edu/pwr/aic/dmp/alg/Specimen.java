package pl.edu.pwr.aic.dmp.alg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Specimen implements Comparable<Specimen> {
	List<City> cities;

	private double rate;
	private double rouletteProbablity;
	private boolean isRateActual;
	int drillChangeInterval;
	City startCity;

	public Specimen(List<City> cities, City startingCity, int drillChangeInterval){
		this.cities = cities;
		this.startCity = startingCity;
		this.isRateActual=false;
		this.drillChangeInterval = drillChangeInterval;
	}

	public List<Integer> getBestRoute(){
		List<Integer> bestRoute = new ArrayList<Integer>();
		bestRoute.add(startCity.getNumber());
		for(int i=0;i<cities.size();i++) {
			if(((i+1) % drillChangeInterval)==0){
				bestRoute.add(startCity.getNumber());
				bestRoute.add(cities.get(i).getNumber());
			} else {
				bestRoute.add(cities.get(i).getNumber());
			}
		}
		bestRoute.add(startCity.getNumber());
		return bestRoute;
	}

	public List<City> getRoute(){
		return cities;
	}

	public void swapCities(int i, int j){
		City c1=cities.get(i);
		City c2=cities.get(j);
		cities.set(i, c2);
		cities.set(j, c1);
	}

	public void shuffleRoute(){
		Collections.shuffle(cities);   
		isRateActual=false;
	}

	public void addCity(City m){
		cities.add(m);
		isRateActual=false;
	}
	public void deleteCity(int x){
		cities.remove(x);
		isRateActual=false;
	}
	public void deleteCity(City m){
		for(int i=0;i<cities.size();i++){
			if(cities.get(i).getNumber()==m.getNumber()){
				cities.remove(i);
				break;
			}
		}
		isRateActual=false;
	}

	public void setCity(int x, City t){
		cities.set(x, t);
		isRateActual=false;
	}
	
	City getCity(int x){
		return cities.get(x);
	}

	public double getRouteLength(){
		if(isRateActual){
			return rate;
		}
		double routeLength=0d;
		try{
		routeLength+=Math.sqrt(Math.pow((double)(startCity.getX()-cities.get(0).getX()),2)
				+ Math.pow((double)(startCity.getY()-cities.get(0).getY()),2));
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Number of cities: " + cities.size());
			System.out.println("Cities: \n" + cities);
			System.out.println("StartCity x:" + startCity.getX());
			System.out.println("Cities(0) x:" + cities.get(0).getX());
			System.out.println("StartCity y:" + startCity.getY());
			System.out.println("Cities(0) y:" + cities.get(0).getY());
		}
		for(int i=0;i<cities.size()-1;i++) {
			if(((i+1) % drillChangeInterval)==0){
				routeLength+=Math.sqrt(Math.pow((double)(cities.get(i).getX()-startCity.getX()),2)+Math.pow((double)(cities.get(i).getY()-startCity.getY()),2));
				routeLength+=Math.sqrt(Math.pow((double)(startCity.getX()-cities.get(i+1).getX()),2)+Math.pow((double)(startCity.getY()-cities.get(i+1).getY()),2));
			} else {
				routeLength+=Math.sqrt(Math.pow((double)(cities.get(i).getX()-cities.get(i+1).getX()),2)+Math.pow((double)(cities.get(i).getY()-cities.get(i+1).getY()),2));
			}
		}
		routeLength+=Math.sqrt(Math.pow((double)(cities.get(cities.size()-1).getX()-startCity.getX()),2)+Math.pow((double)(cities.get(cities.size()-1).getY()-startCity.getY()),2));

		isRateActual=true;
		rate=routeLength;
		return rate;
	}

	public double getP_Roulette(){
		return rouletteProbablity;
	}

	public void setP_Roulette(double p){
		rouletteProbablity=p;
	}

	public Specimen clone(){

		Specimen spec = new Specimen(new ArrayList<City>(), startCity , drillChangeInterval);
		for(int i = 0; i < cities.size(); i++){
			spec.addCity(new City(getCity(i).getNumber(),getCity(i).getX(), getCity(i).getY()));
		}
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

	public Specimen inver(Specimen specimen) {
		Random generator = new Random();
		int startCity = generator.nextInt(cities.size()-1);
				int endCity = generator.nextInt(cities.size()-startCity)+startCity;
				for ( int start = startCity, end = endCity ;
						start < startCity+((endCity-startCity)/2);
						start++, end-- )
				{
					specimen.swapCities(start, end );
				}
				specimen.isRateActual=false;
		return specimen;
	}
	
	public Specimen returnMutatedClone() {
		return inver(this.clone());
	}
}
