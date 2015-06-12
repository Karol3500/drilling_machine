package pl.edu.pwr.aic.dmp.alg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.edu.pwr.aic.dmp.alg.utils.UnitResult;
import pl.edu.pwr.aic.dmp.utils.Machine;

public abstract class Core extends Thread {
	boolean abort;
	City startCity;
	protected Machine machine;
	protected List<City> cities;
	protected long start;
	protected long stop;
	protected Specimen bestSpecimen;
	protected int bestGeneration;
	protected String message;
	protected boolean detailedStatsOn;
	protected UnitResult result;
	
	protected Core(List<City> cities, boolean detailedStatsOn, Machine m){
		this.cities = new ArrayList<City>(cities);
		this.detailedStatsOn = detailedStatsOn;
		this.machine = m;
		result = new UnitResult();
	}
	
	public void abort() {
		abort = true;
	}
	
	public City getstartCity(){
		return startCity;
	}

	public void showEffects() {
		// zapis wynik�w do logu i przebiegu
		addLine("============================================================================================================");
		addDate();
		String temp = "";
		String algorithm ="";
		if (abort == true) {
			temp = "(because of abort) ";
		}
		addLine(">>> Algorithm "+algorithm+" finished " + temp + "with result:");
		addPhrase("Algorithm working time: " + result.getExecutionTimeInSeconds() + " s");
		newLine();
		addLine("Drill change interval: " + machine.getDrillChangeInterval());
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

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}
}
