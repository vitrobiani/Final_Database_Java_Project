import java.util.Map;

public interface Creator<T> {
    public T create(PairSet set);
}
