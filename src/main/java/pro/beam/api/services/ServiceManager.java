package pro.beam.api.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ServiceManager {
    protected final Set<AbstractBeamService> services = new HashSet<>();

    @SuppressWarnings("unchecked")
    public <T extends AbstractBeamService> T get(Class<T> type) {
        for (AbstractBeamService service : this.services) {
            if (service.getClass() == type) {
                return (T) service;
            }
        }

        return null;
    }

    public boolean register(AbstractBeamService service) {
        return this.services.add(service);
    }

    public boolean unregister(AbstractBeamService service) {
        return this.services.remove(service);
    }

    public void unregisterAll() {
        for (Iterator<AbstractBeamService> it = this.services.iterator(); it.hasNext(); ) {
            AbstractBeamService service = it.next();
            this.unregister(service);
        }
    }
}
