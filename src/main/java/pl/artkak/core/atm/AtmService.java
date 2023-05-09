package pl.artkak.core.atm;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AtmService {

    private final Map<IndexedAtmInfo, AtmRequestType> route = new HashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    public void addToRoute(int region, int atmId, AtmRequestType requestType) {
        IndexedAtmInfo infoOrdered = createAtmInfo(region, atmId);
        AtmRequestType existing = route.get(infoOrdered);
        if (existing == null || existing.getPriority() < requestType.getPriority()) {
            route.put(infoOrdered, requestType);
        }
    }

    public Collection<IndexedAtmInfo> getSortedResult() {
        Comparator<Map.Entry<IndexedAtmInfo, AtmRequestType>> finalComparator = createComparator();
        return route.entrySet()
                .stream()
                .sorted(finalComparator)
                .map(Map.Entry::getKey)
                .toList();
    }

    private IndexedAtmInfo createAtmInfo(int region, int atmId) {
        return new IndexedAtmInfo(currentId.getAndIncrement(), region, atmId);
    }

    private Comparator<Map.Entry<IndexedAtmInfo, AtmRequestType>> createComparator() {
        Comparator<Map.Entry<IndexedAtmInfo, AtmRequestType>> entryComparator =
                Comparator.comparing(e -> e.getKey().getRegion());
        Comparator<Map.Entry<IndexedAtmInfo, AtmRequestType>> comparator =
                entryComparator.thenComparingInt(o -> o.getValue().getPriority());
        return comparator.thenComparing(e -> e.getKey().getId());
    }

}
