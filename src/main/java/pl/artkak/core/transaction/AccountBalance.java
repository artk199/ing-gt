package pl.artkak.core.transaction;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class AccountBalance {

    private final String account;
    private int debitCount;
    private int creditCount;
    private BigDecimal balance;

    public AccountBalance(String account) {
        this.account = account;
        this.balance = BigDecimal.ZERO;
        this.debitCount = 0;
        this.creditCount = 0;
    }

    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
        creditCount++;
    }

    public void debit(BigDecimal amount) {
        balance = balance.subtract(amount);
        debitCount++;
    }

}
