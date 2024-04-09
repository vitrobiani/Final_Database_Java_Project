import java.util.HashSet;
import java.util.Random;
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

    public String getRandomKey(){
        Random r = new Random();
        int randomIndex = r.nextInt(pairs.size());
        int i = 0;
        for (Pair pair : pairs) {
            if (i == randomIndex) {
                return pair.getKey();
            }
            i++;
        }
        return null;
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
