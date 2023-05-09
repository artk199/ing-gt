package pl.artkak.core.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AtmRequestType {

    FAILURE_RESTART(1),
    PRIORITY(2),
    SIGNAL_LOW(3),
    STANDARD(4);

    private final int priority;

}
