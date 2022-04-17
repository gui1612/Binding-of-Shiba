package g0803.bindingofshiba.bundles;

import java.util.Set;

public interface Bundle<T> {
    void register(String name, T data);

    T get(String name);

    Set<String> keys();
}
