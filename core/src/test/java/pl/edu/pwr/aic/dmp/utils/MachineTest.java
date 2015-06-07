package pl.edu.pwr.aic.dmp.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.edu.pwr.aic.dmp.utils.Machine;
import pl.edu.pwr.aic.dmp.utils.MachineParameters;


public class MachineTest {
	@Test
	public void shouldCalculateDrillChangeInterval(){
		MachineParameters machineParams = new MachineParameters();
		machineParams.setSaneDefaults();
		
		Machine m = new Machine(machineParams);
				
		assertTrue(m.getDrillChangeInterval() == 20);
	}
}