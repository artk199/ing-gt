package pl.artkak.core.game;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

public class GameQueueService {

    private static final Comparator<IndexedClan> CLANS_COMPARATOR =
            Comparator.comparing(IndexedClan::getPoints).reversed()
                    .thenComparing(IndexedClan::getNumberOfPlayers)
                    .thenComparing(IndexedClan::getId);

    private PriorityQueue<IndexedClan> priorityQueue;
    private TreeMap<Integer, TreeSet<IndexedClan>> clansByNumberOfPlayers;

    public List<List<Clan>> group(int groupCount, List<Clan> clansRequest) {
        List<IndexedClan> clans = assignIdsToClans(clansRequest);
        initializeCollections(clans);
        return generateResult(groupCount);
    }

    private void initializeCollections(List<IndexedClan> clans) {
        priorityQueue = new PriorityQueue<>(clans.size(), CLANS_COMPARATOR);
        priorityQueue.addAll(clans);
        clansByNumberOfPlayers = clans.stream()
                .collect(groupingBy(
                        IndexedClan::getNumberOfPlayers,
                        TreeMap::new,
                        toCollection(() -> new TreeSet<>(CLANS_COMPARATOR))
                ));
    }

    private List<List<Clan>> generateResult(int groupCount) {
        List<List<Clan>> result = new LinkedList<>();
        while (!priorityQueue.isEmpty()) {

            IndexedClan clan = priorityQueue.poll();
            removeFromPlayerCounterMap(clansByNumberOfPlayers, clan);

            List<Clan> currentGroup = new LinkedList<>();
            currentGroup.add(clan.getClan());

            int remainingSlots = groupCount - clan.getNumberOfPlayers();

            fulfillCurrentGroup(currentGroup, remainingSlots);

            result.add(currentGroup);
        }
        return result;
    }

    private void fulfillCurrentGroup(List<Clan> currentGroup, int remainingSlots) {
        while (clansByNumberOfPlayers.floorKey(remainingSlots) != null) {
            Map.Entry<Integer, TreeSet<IndexedClan>> closestPlayerCountGroup = clansByNumberOfPlayers.floorEntry(remainingSlots);
            IndexedClan currentRemainingClan = getClanWithMostPoints(closestPlayerCountGroup);
            currentGroup.add(currentRemainingClan.getClan());
            remainingSlots = remainingSlots - currentRemainingClan.getNumberOfPlayers();
        }
    }

    private IndexedClan getClanWithMostPoints(Map.Entry<Integer, TreeSet<IndexedClan>> closestPlayerCountGroup) {
        TreeSet<IndexedClan> playerCountGroupValue = closestPlayerCountGroup.getValue();
        IndexedClan currentRemainingClan = playerCountGroupValue.pollFirst();
        if (playerCountGroupValue.isEmpty()) {
            clansByNumberOfPlayers.remove(closestPlayerCountGroup.getKey());
        }
        priorityQueue.remove(currentRemainingClan);
        return currentRemainingClan;
    }

    private List<IndexedClan> assignIdsToClans(List<Clan> clansRequest) {
        int currentId = 0;
        List<IndexedClan> indexedClans = new ArrayList<>(clansRequest.size());
        for (Clan clan : clansRequest) {
            indexedClans.add(new IndexedClan(currentId++, clan));
        }
        return indexedClans;
    }

    private void removeFromPlayerCounterMap(TreeMap<Integer, TreeSet<IndexedClan>> groupedByPlayers, IndexedClan clan) {
        Set<IndexedClan> clanSet = groupedByPlayers.get(clan.getNumberOfPlayers());
        clanSet.remove(clan);
        if (clanSet.isEmpty()) {
            groupedByPlayers.remove(clan.getNumberOfPlayers());
        }
    }

}
