package pl.edu.pwr.aic.dmp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.edu.pwr.aic.dmp.alg.City;
import pl.edu.pwr.aic.dmp.alg.Core;
import pl.edu.pwr.aic.dmp.alg.GACore;
import pl.edu.pwr.aic.dmp.alg.IwoCore;
import pl.edu.pwr.aic.dmp.alg.RandomCore;
import pl.edu.pwr.aic.dmp.alg.SACore;
import pl.edu.pwr.aic.dmp.mapUtils.FileParser;
import pl.edu.pwr.aic.dmp.mapUtils.MapReader;

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
	StatsPanel stats;
	PlotPanel plot;
	MapPanel map;
	MachinePanel machinePanel;
	SAPanel saPanel;
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

	// obraz t�a
	BufferedImage obraz = null;
	Image tlo = null;

	public MainPanel(Window parent, MachinePanel machinepanel, GAPanel gapanel, SAPanel sapanel, RandomPanel randpanel, IwoPanel iwoPanel,
			StatsPanel chart) {
		running = false;
		this.parent = parent;
		this.gapanel = gapanel;
		this.randpanel = randpanel;
		this.machinePanel=machinepanel;
		this.saPanel=sapanel;
		this.iwoPanel = iwoPanel;

		this.stats = chart;

		try {
			tlo = ImageIO.read(getClass().getResource("/bg.jpg"));
		} catch (Exception e) {
			System.out.println("Błąd wczytywania tła!");
		}
		setLayout(null);
		setBackground(new Color(0xffffff));

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
				alg = new GACore(cities,
						gapanel.statsOn.isSelected(),
						Integer.parseInt(gapanel.sAmount.getText()),
						Integer.parseInt(gapanel.genAmount.getText()),
						Double.parseDouble(gapanel.kMutation.getText())/100.0,
						Double.parseDouble(gapanel.kHybrid.getText())/100.0,
						gapanel.getSelectedSelectionMethod());
			} else if(selectedTab==2){
				alg = new SACore(cities,
						Integer.parseInt(saPanel.cycles.getText()),
						Double.parseDouble(saPanel.alpha.getText()),
						Double.parseDouble(saPanel.tStart.getText()),
						Integer.parseInt(saPanel.attempts.getText()),
						saPanel.statsOn.isSelected());
			} else if(selectedTab==3){
				alg = new RandomCore(cities,Integer.parseInt(randpanel.cycles.getText()),randpanel.statsOn.isSelected());
			}else if(selectedTab==4){
				alg = new IwoCore(cities,
						iwoPanel.statsOn.isSelected(),
						Integer.parseInt(iwoPanel.iterationCount.getText()),
						Integer.parseInt(iwoPanel.startWeedCount.getText()),
						Integer.parseInt(iwoPanel.maxWeedCount.getText()),
						Integer.parseInt(iwoPanel.maxSeedNumber.getText())/5,
						Integer.parseInt(iwoPanel.maxSeedNumber.getText()),
						Double.parseDouble(iwoPanel.nonLinearCoefficient.getText()),
						Integer.parseInt(iwoPanel.initStandardDeviation.getText()),
						Integer.parseInt(iwoPanel.finalStandardDeviation.getText()));
			}
			alg.calculateInterval(Double.parseDouble(machinePanel.drillEnduranceTextField.getText()),
					Double.parseDouble(machinePanel.drillDiameterTextField.getText()),
					Double.parseDouble(machinePanel.movePerRotationTextField.getText()),
					Double.parseDouble(machinePanel.holeDeepnessTextField.getText()),
					Double.parseDouble(machinePanel.spindleSpeedTextField.getText()));
			
			algRun = new Thread(alg);
			algRun.setPriority(Thread.MAX_PRIORITY);
			(algRun).start();
		} catch(Exception e){
			System.out.println("Unexpected exception occured while trying to perform algorithm. Details can be found in statistics.");
			running = false;
			stats.addLine("============================================================================================================");
			stats.addDate();
			stats.addLine(">>> Exception raised: "+e.getClass());
			stats.addLine("Error message: "+e.getMessage());
			e.printStackTrace();
			stats.addLine("============================================================================================================");
		}
	}

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