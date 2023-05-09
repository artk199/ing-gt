package pl.artkak.core.atm;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = {"region", "atmId"})
@ToString
public class IndexedAtmInfo {

    private final int id;
    private final int region;
    private final int atmId;

    public IndexedAtmInfo(int id, int region, int atmId) {
        this.id = id;
        this.region = region;
        this.atmId = atmId;
    }
}
