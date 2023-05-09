package pl.artkak.app.model;

import com.dslplatform.json.CompiledJson;
import lombok.Getter;
import lombok.Setter;
import pl.artkak.core.atm.AtmRequestType;

@Getter
@Setter
@CompiledJson
public class AtmServiceTaskRequest {

    private int region;
    private AtmRequestType requestType;
    private int atmId;

}
