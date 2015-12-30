package pl.edu.pwr.aic.dmp.alg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Specimen implements Comparable<Specimen> {
	List<City> route;

	double rate;
	double rouletteProbablity;
	boolean isRateActual;
	Core alg;

	public Specimen(Core alg){
		route = new ArrayList<City>();
		isRateActual=false;
		this.alg=alg;
	}

	public List<Integer> getBestRoute(){
		List<Integer> bestRoute = new ArrayList<Integer>();
		bestRoute.add(alg.startCity.getNumber());
		for(int i=0;i<route.size();i++) {
			if(((i+1) % alg.getDrillChangeInterval())==0){
				bestRoute.add(alg.startCity.getNumber());
				bestRoute.add(route.get(i).getNumber());
			} else {
				bestRoute.add(route.get(i).getNumber());
			}
		}
		bestRoute.add(alg.startCity.getNumber());
		return bestRoute;
	}

	public void setRoute(City[] cArray){
		for(City c:cArray){
			route.add(c);
		}
	}

	public void setRoute(List<City> cArray){
		route=cArray;
	}

	public List<City> getRoute(){
		return route;
	}

	public void swapCities(int i, int j){
		City c1=route.get(i).clone();
		City c2=route.get(j).clone();
		route.set(i, c2);
		route.set(j, c1);
	}

	public void shuffleRoute(){
		Collections.shuffle(route);   
		isRateActual=false;
	}

	public void addCity(City m){
		route.add(m);
		isRateActual=false;
	}
	public void deleteCity(int x){
		route.remove(x);
		isRateActual=false;
	}
	public void deleteCity(City m){
		for(int i=0;i<route.size();i++){
			if(route.get(i).getNumber()==m.getNumber()){
				route.remove(i);
				break;
			}
		}
		isRateActual=false;
	}

	public void setCity(int x, City t){
		route.set(x, t);
		isRateActual=false;
	}
	public City getCity(int x){
		return route.get(x);
	}

	public double getRate(){
		if(isRateActual){
			return rate;
		}
		double routeLength=0.0;
		int i;

		routeLength+=Math.sqrt(Math.pow((double)(alg.startCity.getX()-route.get(0).getX()),2)+Math.pow((double)(alg.startCity.getY()-route.get(0).getY()),2));

		for(i=0;i<route.size()-1;i++) {
			if(((i+1) % alg.getDrillChangeInterval())==0){
				routeLength+=Math.sqrt(Math.pow((double)(route.get(i).getX()-alg.startCity.getX()),2)+Math.pow((double)(route.get(i).getY()-alg.startCity.getY()),2));
				routeLength+=Math.sqrt(Math.pow((double)(alg.startCity.getX()-route.get(i+1).getX()),2)+Math.pow((double)(alg.startCity.getY()-route.get(i+1).getY()),2));
			} else {
				routeLength+=Math.sqrt(Math.pow((double)(route.get(i).getX()-route.get(i+1).getX()),2)+Math.pow((double)(route.get(i).getY()-route.get(i+1).getY()),2));
			}
		}
		routeLength+=Math.sqrt(Math.pow((double)(route.get(i).getX()-alg.startCity.getX()),2)+Math.pow((double)(route.get(i).getY()-alg.startCity.getY()),2));

		isRateActual=true;
		rate=routeLength;
		return rate;
	}

	public int cityRepeats(City countedCity){
		int repetitions=0;
		for(City m:route){
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
		for(int i = 0; i < route.size(); i++){
			o.addCity(new City(getCity(i).getNumber(),getCity(i).getX(), getCity(i).getY()));
		}
		o.rate=rate;
		o.isRateActual=isRateActual;
		o.rouletteProbablity=rouletteProbablity;

		return o;
	}

	@Override
	public int compareTo(Specimen t) {
		double diff=getRate()-t.getRate();
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

	public void Inver() {
		Random generator = new Random();
		int startCity = generator.nextInt(route.size()-1);//i=0...trasa.size-2
				int endCity = generator.nextInt(route.size()-startCity)+startCity;//j=startCity...trasa.size()-1
				for ( int start = startCity, end = endCity ;
						start < startCity+((endCity-startCity)/2);
						start++, end-- )
				{
					swapCities(start, end );
				}
				isRateActual=false;
	}

	public void Inver2(int distance) {
		Random generator = new Random();
		int startCity = generator.nextInt(route.size()-1-distance);//i=0...trasa.size-2
				int endCity = startCity+distance;
		for ( int start = startCity, end = endCity ;
				start < startCity+((endCity-startCity)/2);
				start++, end-- )
		{
			swapCities(start, end );
		}
		isRateActual=false;
	}
}
