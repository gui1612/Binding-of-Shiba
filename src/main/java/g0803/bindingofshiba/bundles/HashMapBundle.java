package g0803.bindingofshiba.bundles;

import java.util.HashMap;
import java.util.Set;

public class HashMapBundle<T> implements Bundle<T> {

    private final HashMap<String, T> bundle = new HashMap<>();

    @Override
    public void register(String name, T data) {
        bundle.put(name, data);
    }

    @Override
    public T get(String name) {
        if (!bundle.containsKey(name))
            throw new IllegalArgumentException("Key " + name + " not found in bundle");

        return bundle.get(name);
    }

    @Override
    public Set<String> keys() {
        return bundle.keySet();
    }
}
