package pl.artkak.app.model;

import com.dslplatform.json.CompiledJson;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@CompiledJson
public class AtmServiceTasksResponse {

    private final int region;
    private final int atmId;

}
