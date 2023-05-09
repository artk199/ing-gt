package pl.artkak.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.artkak.core.game.Clan;

import java.util.List;

@Getter
@AllArgsConstructor
public class GameQueueRequest {

    private final Integer groupCount;
    private final List<Clan> clans;

}
