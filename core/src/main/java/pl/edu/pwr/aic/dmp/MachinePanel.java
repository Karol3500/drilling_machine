package pl.edu.pwr.aic.dmp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class MachinePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -2385801152153763104L;

	// elementy interfejsu
	Graphics g = getGraphics();
	JLabel legend;
	JTextField drillEnduranceTextField;
	JTextField drillDiameterTextField;
	JTextField Vc;
	JTextField spindleSpeedTextField;
	JTextField movePerRotationTextField;
	JTextField holeDeepnessTextField;
	JLabel lTL,lDc,lVc,ln,lFn,ld;
	JPanel machineSettings;

	JPanel instructions;

	public MachinePanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets =  new Insets(6,5,0,5);
		gbc.gridx = 0;
		gbc.gridy = 99;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		JLabel placeholder = new JLabel("");
		add(placeholder, gbc);
		gbc.weighty = 0.0;

		//kontener na pozosta�e ustawienia
		machineSettings = new JPanel();
		machineSettings.setLayout(new GridBagLayout());
		GridBagConstraints gbcOS = new GridBagConstraints();
		gbcOS.fill = GridBagConstraints.NONE;
		gbcOS.anchor = GridBagConstraints.WEST;
		gbcOS.insets =  new Insets(3,5,3,5);
		gbcOS.gridx = 99;
		gbcOS.gridy = 4;
		gbcOS.weightx = 1.0;
		gbcOS.weighty = 1.0;
		JLabel placeholderx = new JLabel("");
		machineSettings.add(placeholderx, gbcOS);
		gbcOS.weightx = 0.0;
		gbcOS.weighty = 0.0;

		drillEnduranceTextField = new JTextField("4", 5);
		lTL = new JLabel("TL (m)  | Trwałość wiertła");
		lTL.setLabelFor(drillEnduranceTextField);
		gbcOS.gridx = 0;
		gbcOS.gridy = 0;
		machineSettings.add(drillEnduranceTextField, gbcOS);
		gbcOS.gridx = 1;
		gbcOS.gridy = 0;
		machineSettings.add(lTL, gbcOS);

		drillDiameterTextField = new JTextField("20", 5);
		lDc = new JLabel("Dc (mm)  | Średnica wiertła");
		lDc.setLabelFor(drillDiameterTextField);
		gbcOS.gridx = 0;
		gbcOS.gridy = 1;
		machineSettings.add(drillDiameterTextField, gbcOS);
		gbcOS.gridx = 1;
		gbcOS.gridy = 1;
		machineSettings.add(lDc, gbcOS);

		spindleSpeedTextField = new JTextField("3184", 5);
		ln = new JLabel("n (obr/min)  | Prędkość obrotowa wrzeciona");
		ln.setLabelFor(spindleSpeedTextField);
		gbcOS.gridx = 0;
		gbcOS.gridy = 2;
		machineSettings.add(spindleSpeedTextField, gbcOS);
		gbcOS.gridx = 1;
		gbcOS.gridy = 2;
		machineSettings.add(ln, gbcOS);

		movePerRotationTextField = new JTextField("0.80", 5);
		lFn = new JLabel("Fn (mm/obr)  | Posuw na obrót");
		lFn.setLabelFor(movePerRotationTextField);
		gbcOS.gridx = 0;
		gbcOS.gridy = 3;
		machineSettings.add(movePerRotationTextField, gbcOS);
		gbcOS.gridx = 1;
		gbcOS.gridy = 3;
		machineSettings.add(lFn, gbcOS);

		holeDeepnessTextField = new JTextField("50", 5);
		ld = new JLabel("d (mm)  | Głębokość otworów");
		ld.setLabelFor(holeDeepnessTextField);
		gbcOS.gridx = 0;
		gbcOS.gridy = 4;
		machineSettings.add(holeDeepnessTextField, gbcOS);
		gbcOS.gridx = 1;
		gbcOS.gridy = 4;
		machineSettings.add(ld, gbcOS);

		TitledBorder otherSetBorder = BorderFactory.createTitledBorder("Ustawienia maszyny wiertniczej");
		machineSettings.setBorder(otherSetBorder);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(machineSettings, gbc);

		instructions = new JPanel();
		instructions.setLayout(new GridBagLayout());
		GridBagConstraints gbci = new GridBagConstraints();
		gbci.fill = GridBagConstraints.NONE;
		gbci.anchor = GridBagConstraints.WEST;
		gbci.insets =  new Insets(3,5,3,5);
		gbci.gridx = 99;
		gbci.gridy = 4;
		gbci.weightx = 1.0;
		gbci.weighty = 1.0;
		JLabel placeholder1 = new JLabel("");
		instructions.add(placeholder1, gbci);
		gbci.weightx = 0.0;
		gbci.weighty = 0.0;

		TitledBorder instrSetBorder = BorderFactory.createTitledBorder("Instrukcja użytkowania programu");
		instructions.setBorder(instrSetBorder);
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(instructions, gbc);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);

	}

	public void actionPerformed(ActionEvent arg0) {

	}
}
