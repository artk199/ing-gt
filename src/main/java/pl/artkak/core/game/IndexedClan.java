package pl.artkak.core.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class IndexedClan {

    private final Integer id;
    private final Clan clan;

    public int getPoints() {
        return clan.getPoints();
    }

    public int getNumberOfPlayers() {
        return clan.getNumberOfPlayers();
    }

}
