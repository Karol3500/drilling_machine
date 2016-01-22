package pl.edu.pwr.aic.dmp.alg;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

public class CoreTest {
	
	Core c = new IwoCore();

	@Test
	public void shouldRemoveHalfSpecimen(){
		List<Specimen> s = new LinkedList<Specimen>();
		Specimen spec1 = mock(Specimen.class);
		Specimen spec2 = mock(Specimen.class);
		Specimen spec3 = mock(Specimen.class);
		Specimen spec4 = mock(Specimen.class);
		Specimen spec5 = mock(Specimen.class);
		when(spec1.getRouteLength()).thenReturn(1d);
		when(spec2.getRouteLength()).thenReturn(2d);
		when(spec3.getRouteLength()).thenReturn(4d);
		when(spec4.getRouteLength()).thenReturn(3d);
		when(spec5.getRouteLength()).thenReturn(5d);
		s.add(spec1);
		s.add(spec2);
		s.add(spec3);
		s.add(spec4);
		s.add(spec5);
		int originalSize = s.size();
		
		c.wipeHalfPopulation(s);
		
		assertEquals(Math.ceil(originalSize/2d), s.size(), 0d);
		assertSame(s.get(0), spec1);
		assertSame(s.get(1), spec4);
		assertSame(s.get(2), spec5);
	}
	
	@Test
	public void shouldReduceSlightlyBiggerThanAllowedPopulation(){
		List<Specimen> s = initializePopulation();

		c.reduceShuffledPopulationUsingTournament(s, 70);
		
		assertEquals(70, s.size());
	}
	
	@Test
	public void shouldReduceMuchBiggerThanAllowedPopulation(){
		List<Specimen> s = initializeBigPopulation();

		c.reduceShuffledPopulationUsingTournament(s, 70);
		
		assertEquals(70, s.size());
	}

	@Test
	public void shouldReturnCorrectIndexOfSpecForRemovalOfTheFirst(){
		testSpecimenForRemoval(60, 3, 0, 0, 20);
	}
	
	@Test
	public void shouldReturnCorrectIndexOfSpecForRemovalOfTheFourth(){
		testSpecimenForRemoval(20, 5, 3, 12, 15);
	}
	
	@Test
	public void shouldReturnCorrectIndexOfSpecForRemovalOfTheFifth(){
		testSpecimenForRemoval(20, 5, 4, 16, 19);
	}

	private void testSpecimenForRemoval(int populationSize, int specimensToBeRemoved, int removedSpecimenCounter, int lowBound, int upBound) {
		for(int i=removedSpecimenCounter;i<50;i++){
			int indx = c.calculateIndexOfSpecimenToBeRemoved(populationSize, specimensToBeRemoved, removedSpecimenCounter);
			assertTrue("Got value: " + (indx -removedSpecimenCounter) + ", expected > " + (lowBound-removedSpecimenCounter), (lowBound - removedSpecimenCounter) <= indx);
			assertTrue("Got value: " + (indx -removedSpecimenCounter) + ", expected < " + (upBound-removedSpecimenCounter), indx <= (upBound-removedSpecimenCounter));
		}
	}
	
	
	private List<Specimen> initializeBigPopulation() {
		List<Specimen> specimens = new LinkedList<>();
		for(int i=0; i< 1000; i++){
			Specimen s = Mockito.mock(Specimen.class);
			when(s.getRouteLength()).thenReturn((double)i);
			specimens.add(s);
		}
		return specimens;
	}
	
	private List<Specimen> initializePopulation() {
		List<Specimen> specimens = new LinkedList<>();
		for(int i=0; i< 100; i++){
			Specimen s = Mockito.mock(Specimen.class);
			when(s.getRouteLength()).thenReturn((double)i);
			specimens.add(s);
		}
		return specimens;
	}
}
