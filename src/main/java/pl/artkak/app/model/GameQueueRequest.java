package pl.artkak.app.model;

import com.dslplatform.json.CompiledJson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.artkak.core.game.Clan;

import java.util.List;

@Getter
@AllArgsConstructor
@CompiledJson
public class GameQueueRequest {

    private final Integer groupCount;
    private final List<Clan> clans;

}
