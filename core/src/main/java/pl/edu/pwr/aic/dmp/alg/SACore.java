package pl.edu.pwr.aic.dmp.alg;
import java.math.BigDecimal;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.SAParameters;
import pl.edu.pwr.aic.dmp.utils.Machine;

public class SACore extends Core{
	Specimen currentSpecimen;
	Specimen mutatedSpecimen;
	SAParameters params;
	double len;		//dlugosc aktualnej trasy
	double bestLen;	//dlugosc najlepszej trasy
	int cAmount;
	int bestCycle; // nr cyklu z bestSpecimenm osobnikiem
	public SACore(List<City> cities,
			SAParameters params,
			boolean detailedStatsOn,
			Machine m) {
		super(cities,detailedStatsOn,m);
		this.params = params;
		currentSpecimen = new Specimen(this);
		mutatedSpecimen = new Specimen(this);
		bestSpecimen = new Specimen(this);
		cAmount = cities.size()-1;
		bestLen = Double.MAX_VALUE;
		len = Double.MAX_VALUE;
		bestCycle = -1;
	}

	public void run() {
		startCity= cities.get(0).clone();
		start=System.currentTimeMillis(); // start licznika czasu
		for (int ep=0; !abort && ep<params.getCyclesNumber(); ep++) {
			simulate(ep);
		}
		stop=System.currentTimeMillis(); // stop licznika czasu
		result.setExecutionTimeInSeconds((stop-start)/1000d);
		showEffects();
	}

	//losowa inicjalizacja trasy
	void makeTour() {
		currentSpecimen=new Specimen(this);
		currentSpecimen.setRoute(cities);
		currentSpecimen.shuffleRoute();
		len = currentSpecimen.getRate();
	}

	//odwraca losowo wybrana podtrase i
	//wstawia za losowo wybranym miastem
	//odwraca losowo wybrany segment
	double mutacja1(double len) {
		City[] cMut=new City[cAmount];
		int p1,p2; //punkty rozciecia
		int sw=0, i,j,k=0;
		p1 = (int)(cAmount*Math.random());
		do {
			p2 = (int)(Math.random()*cAmount); 
		} while (Math.abs(p1-p2)<2||Math.abs(p1-p2)==cAmount-1);
		if (p2<p1) {
			sw=p1; p1=p2; p2=sw;
		}
		i=0;
		do {
			if (i<p1 || i>p2) {
				cMut[k++]=currentSpecimen.getCity(i++);
				//neigh[k++] = trasa[i++];
			}
			else
				for (j=p2; j>=p1; j--) {
					cMut[k++]=currentSpecimen.getCity(j);
					//neigh[k++] = trasa[j];
					i++;
				}
		} while(i<cAmount);
		mutatedSpecimen=new Specimen(this);
		mutatedSpecimen.setRoute(cMut);
		len = mutatedSpecimen.getRate();

		return len;
	}    

	/*
	 * wlasciwy algorytm SA
	 */ 
	double simulate(int ep) {
		int i=0;

		double tempLen; //dlugosc mutanta;
		double T=params.getStartTemperature();


		//wyznaczam startowa trase
		makeTour();


		boolean success = false;
		boolean found   = false;
		boolean done    = false;
		int fail = 0;	//liczy porazki
		int succ;		//liczy sukcesy
		while (!done) {
			i = 0;
			succ = 0;
			success = false;
			found = false;
			while (!success) {
				tempLen = mutacja1(len);
				if (accept(tempLen, len,T)) {
					currentSpecimen=mutatedSpecimen.clone();
					//trasa = copy(neigh);
					len = tempLen;
					if (tempLen<bestLen) {
						bestSpecimen=mutatedSpecimen.clone();
						//bestT = copy(neigh);
						bestCycle=ep;
						bestLen = tempLen;
						found = true;
						succ++;
					}
				}
				i++; 

				success = (i>100*cAmount||succ>10*cAmount);
			}
			T = T*params.getCoolingCoefficient();
			if (found) {
				fail = 0;
			} else fail++;
			done = (fail==params.getPermutationAttempts());
		}

		if(detailedStatsOn){
			String line = "Cykl #" + ep + " -> długość trasy: " + round(bestLen,2) + " Temperatura końcowa: "+round(T,2);
			addLine(line);
		}
		return bestLen;
	}



	boolean accept(double tempLen, double len, double T) {
		boolean yes = false;
		if (tempLen<len) {
			yes = true;
		} else {
			yes = (Math.random() < Math.exp(-(tempLen-len)/T));
		}
		return yes;
	}



	public double round(double d,int pos){
		return new BigDecimal(d).setScale(pos, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}