/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demeteranalyser.faulty;

/**
 *
 * @author lachlan
 */
public class FaultyClass {

	public FaultyClass() {
	}
	
	public void faultyMethod(){
		PartnerInFaultiness pif = new PartnerInFaultiness();
		
		pif.target.trim();
	}
}
