package pl.artkak.app.model;

import com.dslplatform.json.CompiledJson;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@CompiledJson
public class TransactionRequest {

    private String debitAccount;
    private String creditAccount;
    private BigDecimal amount;

}
