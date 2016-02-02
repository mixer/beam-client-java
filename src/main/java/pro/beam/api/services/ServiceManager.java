package pro.beam.api.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ServiceManager<T extends AbstractService> {
    protected final Set<T> services = new HashSet<>();

    @SuppressWarnings("unchecked")
    public <V extends T> V get(Class<V> type) {
        for (T service : this.services) {
            if (service.getClass() == type) {
                return (V) service;
            }
        }

        return null;
    }

    public boolean register(T service) {
        return this.services.add(service);
    }

    public boolean unregister(T service) {
        return this.services.remove(service);
    }

    public void unregisterAll() {
        for (Iterator<T> it = this.services.iterator(); it.hasNext(); ) {
            this.unregister(it.next());
        }
    }
}
