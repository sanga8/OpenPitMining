package projet.OpenPitMining;

import java.util.Comparator;
import java.util.HashMap;
public class Comparateur<V,T> implements Comparator<V>{
	private T toCompare;
	
	public Comparateur(T toCompare){
		this.toCompare = toCompare;
	}
	public int compare(V o1, V o2) {
		if (  ((Integer)((HashMap)toCompare).get(o1))  >  ((Integer)((HashMap)toCompare).get(o2))) {
			return 1;
		}
		else if (((Integer)((HashMap)toCompare).get(o1))  <=  ((Integer)((HashMap)toCompare).get(o2))) {
			return -1;
		}
		return 0;
	}
}