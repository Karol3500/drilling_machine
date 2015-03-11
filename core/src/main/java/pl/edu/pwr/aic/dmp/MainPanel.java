package pl.edu.pwr.aic.dmp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.wroc.pwr.aic.dmp.mapUtils.FileParser;
import pl.wroc.pwr.aic.dmp.mapUtils.MapReader;

public class MainPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 3679573816029791253L;

	// zmienne pracy i stanu
	Window parent; // referencja okna g��wnego
	boolean running; // czy algorytm pracuje
	boolean fileLoaded; // czy za�adowano miasta
	String filePath; // �cie�ka pliku do odczytu
	int numberOfCities; // ilo�� miast
	int numberOfCitiesInTour;
	int[] citiesIdsInTour; 
	int selectedTab;
	List<City> cities;
	
	// referencje okien komunikacyjnych
	GAPanel gapanel;
	RandomPanel randpanel;
	BrutePanel brutepanel;
	StatsPanel stats;
	PlotPanel plot;
	MapPanel map;
	MachinePanel machinepanel;
	AntPanel antpanel;
	SAPanel sapanel;
	IwoPanel iwoPanel;

	// algorytm genetyczny
	Core alg;
	Thread algRun;

	// elementy interfejsu
	Graphics g = getGraphics();
	JButton buttonReadMap;
	JButton buttonRunAlgorithm;
	JButton buttonInterrupt;
	JButton buttonReadTour;
	JProgressBar pb;

	// obraz t�a
	BufferedImage obraz = null;
	Image tlo = null;

	public MainPanel(Window parent, MachinePanel machinepanel, GAPanel gapanel, AntPanel antpanel, SAPanel sapanel, RandomPanel randpanel, BrutePanel brutepanel, IwoPanel iwoPanel,
			StatsPanel chart, PlotPanel plot, MapPanel map) {

		// inicjacja zmiennych stanu
		running = false;

		// zebranie referencji do okien
		this.parent = parent;
		this.gapanel = gapanel;
		this.randpanel = randpanel;
		this.brutepanel = brutepanel;
		this.machinepanel=machinepanel;
		this.antpanel=antpanel;
		this.sapanel=sapanel;
		this.iwoPanel = iwoPanel;

		this.stats = chart;
		this.plot = plot;
		this.map = map;

		try {
			tlo = ImageIO.read(getClass().getResource("/bg.jpg"));
		} catch (Exception e) {
			System.out.println("Błąd wczytywania tła!");
		}
		setLayout(null);
		setBackground(new Color(0xffffff));

		// progress bar
		pb = new JProgressBar(0, 100);
		pb.setValue(0);
		pb.setStringPainted(true);
		pb.setBounds(435, 95, 365, 25);
		pb.setVisible(false);
		add(pb);

		// sekcja przycisk�w

		buttonReadMap = new JButton("Wczytaj mapę");
		buttonReadMap.setBounds(5, 95, 115, 25);
		add(buttonReadMap);
		buttonReadMap.addActionListener(this);

		buttonReadTour = new JButton("Wczytaj trasę");
		buttonReadTour.setBounds(125, 95, 115, 25);
		buttonReadTour.setEnabled(false);
		add(buttonReadTour);
		buttonReadTour.addActionListener(this);

		buttonRunAlgorithm = new JButton("Uruchom");
		buttonRunAlgorithm.setBounds(245, 95, 90, 25);
		buttonRunAlgorithm.setEnabled(false);
		add(buttonRunAlgorithm);
		buttonRunAlgorithm.addActionListener(this);

		buttonInterrupt = new JButton("Przerwij");
		buttonInterrupt.setBounds(340, 95, 90, 25);
		buttonInterrupt.setEnabled(false);
		add(buttonInterrupt);
		buttonInterrupt.addActionListener(this);

	}

	public void actionPerformed(ActionEvent event) {
		// Odebranie uchwytu od listenera i wykonanie adekwatnej operacji
		Object source = event.getSource();
		if (source == buttonReadMap) {
			// wczytywanie pliku z miastami
			fileLoaded = loadFile();
			if (fileLoaded) {
				buttonRunAlgorithm.setEnabled(true);
				buttonReadTour.setEnabled(true);
			}
		} else if (source == buttonRunAlgorithm) {
			// odpalenie algorytmu z wybranymi ustawieniami
			if (!running && fileLoaded) {
				selectedTab=parent.tabbedP.getSelectedIndex();
				if(selectedTab==0 ||  selectedTab>6){ // 2 tymczasowo nie do odpalenia
					JOptionPane.showMessageDialog(null,"Aby uruchomić symulację wybierz najpierw zakładkę któregoś algorytmu.");
				} else {
					buttonReadMap.setEnabled(false);
					buttonRunAlgorithm.setEnabled(false);
					buttonInterrupt.setEnabled(true);
					buttonReadTour.setEnabled(false);
					pb.setValue(0);
					pb.setVisible(true);
					running = true;
					runAlg();
				}
			}
		} else if (source == buttonInterrupt) {
			// wstrzymanie pracy algorytmu
			alg.abort();
			running = false;
			buttonReadMap.setEnabled(true);
			buttonRunAlgorithm.setEnabled(true);
			buttonInterrupt.setEnabled(false);
			pb.setVisible(false);
		}
		else if (source == buttonReadTour) {
			if (!running && fileLoaded) {
//				testRoute();
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(tlo, 0, 0, 808, 720, null);
	}

	public void runAlg() {

		try{
			if(selectedTab==1){
				alg = new GACore(this);
			} else if(selectedTab==2){
				alg = new AntCore(this);
			} else if(selectedTab==3){
				alg = new SACore(this);
			} else if(selectedTab==4){
				alg = new RandomCore(this);
			} else if(selectedTab==5) {
				alg = new BruteCore(this);
			}else if(selectedTab==6){
				alg = new IwoCore(this);
			}

			algRun = new Thread(alg);
			algRun.setPriority(Thread.MAX_PRIORITY);
			(algRun).start();
		} catch(Exception e){
			JOptionPane.showMessageDialog(null,"Próba uruchomienia algorytmu zakończyła się niespodziewanym błędem. Szczegóły w statystykach.");
			pb.setVisible(false);
			buttonReadMap.setEnabled(true);
			buttonRunAlgorithm.setEnabled(true);
			buttonInterrupt.setEnabled(false);
			buttonReadTour.setEnabled(true);
			running = false;
			stats.addLine("============================================================================================================");
			stats.addDate();
			stats.addLine(">>> Wystąpił wyjątek: "+e.getClass());
			stats.addLine("Komunikat błędu: "+e.getMessage());
			e.printStackTrace();
			stats.addLine("============================================================================================================");
		}
	}

//	public void testRoute() throws InputMismatchException {
//		JFileChooser fc = new JFileChooser("input");
//		FileNameExtensionFilter filter = new FileNameExtensionFilter(
//				"TXT & TOUR Text Files", "txt", "tour");
//		fc.setFileFilter(filter);
//		int fcReturn = fc.showOpenDialog(fc);
//		if (fcReturn == JFileChooser.APPROVE_OPTION) {
//
//			System.out.println("Rozpoczęto wczytywanie pliku");
//			FileReader fr = null;
//			try {
//				fr = new FileReader(fc.getSelectedFile().getPath());
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//				JOptionPane.showMessageDialog(null,"Błąd: Brak dostępu do pliku!");
//				//return false;
//				return;
//			}
//			filePath = fc.getSelectedFile().getPath();
//			Scanner sca = new Scanner(fr);
//
//			//System.out.println("Rozpocz�to parsowanie pliku");
//			try {
//				while (!sca.next().contains("DIMENSION")) {
//
//				}
//				while (!sca.hasNextInt()) {
//					sca.next();
//				}
//				numberOfCitiesInTour = sca.nextInt()-1;
//				if(numberOfCitiesInTour+1!=numberOfCities){
//					JOptionPane.showMessageDialog(null,"Błąd: Liczba punktów trasy nie zgadza się z mapą. Trasa ta nie jest przeznaczona dla tej mapy.");
//					sca.close();
//					return;
//				}
//
//				citiesIdsInTour = new int[numberOfCitiesInTour];
//
//				while (!sca.next().contains("TOUR_SECTION")) {
//
//				}
//				while (!sca.hasNextInt()) {
//					sca.next();
//				}
//			} catch (Exception e) {
//				JOptionPane.showMessageDialog(null,"Błąd: Nie znaleziono DIMENSION lub TOUR_SECTION w pliku!");
//				System.out.println("Nie znaleziono DIMENSION lub TOUR_SECTION!");
//				e.printStackTrace();
//				sca.close();
//				return;
//			}
//			int i = 0;
//			while (sca.hasNextInt()) {
//				try {
//					int id = sca.nextInt();
//					if(id!=1 && id!=-1){
//						citiesIdsInTour[i] = id;
//						i++;
//					}
//
//				} catch (Exception e) {
//					System.out.println("Wystąpił błąd przy wczytywaniu identyfikatorów trasy!");
//					e.printStackTrace();
//					JOptionPane.showMessageDialog(null,"Błąd: Nie udało się wczytać identyfikatorów trasy!");
//					sca.close();
//					return;
//				}
//			}
//			sca.close();
//			//System.out.println("tour:"+Arrays.toString(cTourIds));
//			System.out.println("Wczytano " + i + " punktów");
//
//
////			try{
////				City cTourList[]=new City[numberOfCitiesInTour];
////				//wyliczanie d�ugo�ci trasy
////				for(int j=0;j<numberOfCitiesInTour;j++){
////					int cityId=citiesIdsInTour[j];
////					double x=citiesCoordinates[cityId-1][0];
////					double y=citiesCoordinates[cityId-1][1];
////					cTourList[j]=new City(cityId,x,y);
////				}
////				RandomCore testcore=new RandomCore(this);
////				testcore.startCity= new City(citiesIds[0], citiesCoordinates[0][0], citiesCoordinates[0][1]);
////				Specimen tester=new Specimen(testcore);
////				tester.setRoute(cTourList);
////				double optymalna=tester.getRate();
////				JOptionPane.showMessageDialog(null,"Długość wczytanej trasy: "+round(optymalna,2));
////				stats.addLine("============================================================================================================");
////				stats.addDate();
////				stats.addLine(">>> Wczytano trasę z pliku: "+fc.getSelectedFile().getPath());
////				stats.addLine("Interwał wymiany wiertła: "+testcore.interwal_wymiany);
////				stats.addLine("Długość trasy: "+round(optymalna,2));
////				stats.addLine("============================================================================================================");
////			} catch (Exception e){
////				JOptionPane.showMessageDialog(null,"Błąd: Nie udało się obliczyć długości trasy, być może plik trasy jest wadliwy.");
////				System.out.println("Wystąpił błąd przy obliczaniu długości trasy, być może błąd w pliku trasy.");
////				e.printStackTrace();
////			}
//		}
//		return;
//	}

	public double round(double d,int pos){
		if(Double.isInfinite(d) || Double.isNaN(d)){
			return -1;
		} else {
			return new BigDecimal(d).setScale(pos, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}

	public boolean loadFile() throws InputMismatchException {
		JFileChooser fc = new JFileChooser("input");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TXT & TSP Text Files", "txt", "tsp");
		fc.setFileFilter(filter);
		int fcReturn = fc.showOpenDialog(fc);
		if (fcReturn == JFileChooser.APPROVE_OPTION) {
			filePath = fc.getSelectedFile().getPath();
			FileParser fileParser = new FileParser();
			Scanner scanner = MapReader.readFileAsScanner(filePath);
			if(scanner == null){
				JOptionPane.showMessageDialog(this,"Failure: Cannot access file.");
			}
			if(!fileParser.parseFile(scanner)){
				JOptionPane.showMessageDialog(this,"Failure: File malformed");
				return false;
			}
			
			numberOfCities = fileParser.getNumberOfCities();
			cities = fileParser.getCityList();
			System.out.println("Wczytano " + numberOfCities + " punktów");
			return true;
		}

		return false;
	}
}