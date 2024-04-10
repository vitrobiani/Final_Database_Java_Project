import java.io.Serializable;

public class Pair implements Serializable {
    private String key;
    private Object value;
    public Pair(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    // getters and setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        if (value != null)
            this.value = value;
    }

    @Override
    public String toString() {
        return  key + " : " + value;
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pair pair = (Pair) obj;
        return key.equals(pair.key);
    }
}
