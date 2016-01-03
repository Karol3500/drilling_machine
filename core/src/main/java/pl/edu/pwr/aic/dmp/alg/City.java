package pl.edu.pwr.aic.dmp.alg;
/**
 *
 * @author Maciej
 */
public class City {

    private int number;
    private double x;
    private double y;


    public City(int numer,double x, double y){
        this.x = x;
        this.y = y;
        number=numer;
    }


    public City getCity(){
        return this;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
    
    public int getNumber(){
        return number;
    }
    
    public void setNumber(int numer){
        number=numer;
    }
    
    public boolean cequals(City m){
        return number==m.getNumber();
    }

    @Override
    public boolean equals(Object m){
        City c = (City) m;
        return number==c.getNumber();
    }

    public City clone(){
        return new City(number,x,y);
    }
    
    public String toString(){
    	return number+","+x+","+y;
    }

    
}
