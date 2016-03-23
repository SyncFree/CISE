package analyzer;

public class Pair<K, V> {
    private  K element0;
    private  V element1;
	 //override hashcode and equals
    
    public Pair() {
		// TODO Auto-generated constructor stub
	}
    public static <K, V> Pair<K, V> createPair(K element0, V element1) {
        return new Pair<K, V>(element0, element1);
    }

    
    public Pair(K element0, V element1) {
        this.element0 = element0;
        this.element1 = element1;
    }

   

	public K getElement0() {
        return element0;
    }

    public V getElement1() {
        return element1;
    }
    
    public boolean equals(Pair o) {
        if (o == null) return false;
        Pair pairo = (Pair) o;
        return this.element0.equals(pairo.getElement0()) &&
               this.element1.equals(pairo.getElement1());
      }
	
}


