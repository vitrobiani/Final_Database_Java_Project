import java.util.HashSet;
import java.util.Set;

public class PairSet {
    public Set<Pair> pairs;
    public PairSet(){
        pairs = new HashSet<>();
    }

    public String getKeys(){
        StringBuilder sb = new StringBuilder();
        for (Pair pair : pairs)
            sb.append(pair.getKey()).append(",\n");
        return sb.toString();
    }

    public void addPair(String key, Object value){
        Pair p = new Pair(key,value);
        pairs.add(p);
    }
    public void removePair(String key){
        pairs.removeIf(pair -> pair.getKey().equals(key));
    }
    public Object get(String key){
        for (Pair pair : pairs) {
            if (pair.getKey().equals(key)) {
                return pair.getValue();
            }
        }
        return null;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Pair pair : pairs)
            sb.append(pair.toString()).append("\n");
        return sb.toString();
    }
}
