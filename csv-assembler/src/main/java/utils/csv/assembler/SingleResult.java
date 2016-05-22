package utils.csv.assembler;

public class SingleResult {
	double route;
	double execTime;
	
	public SingleResult(Double route, Double execTime) {
		this.route = route;
		this.execTime = execTime;
	}
	public double getRoute() {
		return route;
	}
	public void setRoute(double route) {
		this.route = route;
	}
	public double getExecTime() {
		return execTime;
	}
	public void setExecTime(double execTime) {
		this.execTime = execTime;
	}
	
	@Override
	public int hashCode() {
		return (int)(route*1000 + execTime*1000);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(isNullOrOtherClass(obj) || 
				paramsDontMatch((SingleResult)obj)){
			return false;
		}
		return true;
	}
	private boolean paramsDontMatch(SingleResult obj) {
		return obj.route != route || obj.execTime != execTime;
	}
	private boolean isNullOrOtherClass(Object obj) {
		return obj == null || !(obj instanceof SingleResult);
	}
}
