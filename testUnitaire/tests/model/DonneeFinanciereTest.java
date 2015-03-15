package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DonneeFinanciereTest {
	DonneeFinanciere df ;

	@Before
	public void setUp() throws Exception {
		 df = new DonneeFinanciere();
	}

	@Test
	public void testGetId() {
		df.setId(1);
		assertEquals("L'id de la donnée financière n'est pas correct ",1,df.getId());
	}
	@Test
	public void testSetId() {
		df.setId(1);
		assertEquals("L'id de la donnée financière n'est pas correct ",1,df.getId());
	}
	@Test
    public void testGetAnnee() {
		df.setAnnee(2014);
		assertEquals("L'année de la donnée financière n'est pas correct ",2014,df.getAnnee());
     }
	@Test
    public void testSetAnnee() {
		df.setAnnee(2014);
		assertEquals("L'année de la donnée financière n'est pas correct ",2014,df.getAnnee());
     }
      @Test
      public void testSetCa() {
    	  df.setCa(2000.123);
	   assertEquals(2000.123, df.getCa(), 0.001);
       }
      @Test
      public void testGetCa() {
    	  df.setCa(2000.123);
	   assertEquals(2000.123, df.getCa(), 0.001);
       }
      @Test
      public void testSetCoutRisque() {
    	  df.setCoutRisque(5.05);
	   assertEquals(5.05, df.getCoutRisque(), 0.001);
       }
      @Test
      public void testGetCoutRisque() {
    	  df.setCoutRisque(5.05);
	   assertEquals(5.05, df.getCoutRisque(), 0.001);
       }
      @Test
      public void testSetPnb() {
    	  df.setPnb(2000.123);
	   assertEquals(2000.123, df.getPnb(), 0.001);
       }
      @Test
      public void testGetPnb() {
    	  df.setPnb(2000.123);
	   assertEquals(2000.123, df.getPnb(), 0.001);
       }
      @Test
      public void testSetRcai() {
    	  df.setRcai(2000.123);
	   assertEquals(2000.123, df.getRcai(), 0.001);
       }
      @Test
      public void testGetRcai() {
    	  df.setRcai(2000.123);
	   assertEquals(2000.123, df.getRcai(), 0.001);
       }
      @Test
      public void testSetRex() {
    	  df.setRex(2000.123);
	   assertEquals(2000.123, df.getRex(), 0.001);
       }
      @Test
      public void testGetRex() {
    	  df.setRex(2000.123);
	   assertEquals(2000.123, df.getRex(), 0.001);
       }
      @Test
      public void testSetRn() {
    	  df.setRn(2000.123);
	   assertEquals(2000.123, df.getRn(), 0.001);
       }
      @Test
      public void testGetRn() {
    	  df.setRn(2000.123);
	   assertEquals(2000.123, df.getRn(), 0.001);
       }

}
