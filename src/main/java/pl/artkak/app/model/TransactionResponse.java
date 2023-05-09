package pl.artkak.app.model;

import com.dslplatform.json.CompiledJson;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@CompiledJson
public class TransactionResponse {

    private String account;
    private int debitCount;
    private int creditCount;
    private BigDecimal balance;

}
