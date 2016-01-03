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
	private Core alg;

	public Specimen(Core alg){
		cities = new ArrayList<City>();
		isRateActual=false;
		this.alg=alg;
	}

	public List<Integer> getBestRoute(){
		List<Integer> bestRoute = new ArrayList<Integer>();
		bestRoute.add(alg.startCity.getNumber());
		for(int i=0;i<cities.size();i++) {
			if(((i+1) % alg.getDrillChangeInterval())==0){
				bestRoute.add(alg.startCity.getNumber());
				bestRoute.add(cities.get(i).getNumber());
			} else {
				bestRoute.add(cities.get(i).getNumber());
			}
		}
		bestRoute.add(alg.startCity.getNumber());
		return bestRoute;
	}

	public void setRoute(City[] cArray){
		for(City c:cArray){
			cities.add(c);
		}
	}

	public void setRoute(List<City> cArray){
		cities=cArray;
	}

	public List<City> getRoute(){
		return cities;
	}

	public void swapCities(int i, int j){
		City c1=cities.get(i).clone();
		City c2=cities.get(j).clone();
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
	public City getCity(int x){
		return cities.get(x);
	}

	public double getRouteLength(){
		if(isRateActual){
			return rate;
		}
		double routeLength=0d;
		int i;

		routeLength+=Math.sqrt(Math.pow((double)(alg.startCity.getX()-cities.get(0).getX()),2)+Math.pow((double)(alg.startCity.getY()-cities.get(0).getY()),2));

		for(i=0;i<cities.size()-1;i++) {
			if(((i+1) % alg.getDrillChangeInterval())==0){
				routeLength+=Math.sqrt(Math.pow((double)(cities.get(i).getX()-alg.startCity.getX()),2)+Math.pow((double)(cities.get(i).getY()-alg.startCity.getY()),2));
				routeLength+=Math.sqrt(Math.pow((double)(alg.startCity.getX()-cities.get(i+1).getX()),2)+Math.pow((double)(alg.startCity.getY()-cities.get(i+1).getY()),2));
			} else {
				routeLength+=Math.sqrt(Math.pow((double)(cities.get(i).getX()-cities.get(i+1).getX()),2)+Math.pow((double)(cities.get(i).getY()-cities.get(i+1).getY()),2));
			}
		}
		routeLength+=Math.sqrt(Math.pow((double)(cities.get(i).getX()-alg.startCity.getX()),2)+Math.pow((double)(cities.get(i).getY()-alg.startCity.getY()),2));

		isRateActual=true;
		rate=routeLength;
		return rate;
	}

	public int cityRepeats(City countedCity){
		int repetitions=0;
		for(City m:cities){
			if(countedCity.cequals(m)){
				repetitions++;
			}
		}
		return repetitions;
	}

	public double getP_Roulette(){
		return rouletteProbablity;
	}

	public void setP_Roulette(double p){
		rouletteProbablity=p;
	}

	public Specimen clone(){

		Specimen o = new Specimen(alg);
		for(int i = 0; i < cities.size(); i++){
			o.addCity(new City(getCity(i).getNumber(),getCity(i).getX(), getCity(i).getY()));
		}
		o.rate=rate;
		o.isRateActual=isRateActual;
		o.rouletteProbablity=rouletteProbablity;

		return o;
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
				isRateActual=false;
		return specimen;
	}
	
	public Specimen returnMutatedClone() {
		return inver(this.clone());
	}
}
