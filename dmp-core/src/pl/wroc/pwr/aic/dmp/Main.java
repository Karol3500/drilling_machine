package pl.wroc.pwr.aic.dmp;


public class Main {

	public static void main(String[] args) {
		//wywo�anie w�tku g��wnego okna
		WinThread windowThread1 = new WinThread();
		(new Thread(windowThread1)).start();

	}

}
