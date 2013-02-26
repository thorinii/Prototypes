/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asciiartanimatoreditor;

/**
 *
 * @author lachlan
 */
public class StringUtil {
	public static String insert(String outer, String insert, int where){
		StringBuilder sb = new StringBuilder(outer);
		
		sb.insert(where, insert);
		
		return sb.toString();
	}
	
	public static String remove(String outer, int where, int range){
		StringBuilder sb = new StringBuilder(outer);
		
		sb.delete(where, where + range);
		
		return sb.toString();
	}
}
