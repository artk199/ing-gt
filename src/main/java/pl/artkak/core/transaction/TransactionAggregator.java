package pl.artkak.core.transaction;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;

import static java.util.Comparator.comparing;

public class TransactionAggregator {

    private final HashMap<String, AccountBalance> accounts = new HashMap<>();

    public void apply(String debitAccount, String creditAccount, BigDecimal amount) {
        getAccountBalance(creditAccount).credit(amount);
        getAccountBalance(debitAccount).debit(amount);
    }

    public Collection<AccountBalance> getSortedAccountBalances() {
        return accounts.values().stream().sorted(comparing(AccountBalance::getAccount)).toList();
    }

    private AccountBalance getAccountBalance(String creditAccount) {
        return accounts.computeIfAbsent(creditAccount, AccountBalance::new);
    }

}
