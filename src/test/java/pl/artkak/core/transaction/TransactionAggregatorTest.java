package pl.artkak.core.transaction;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class TransactionAggregatorTest {

    private TransactionAggregator transactionAggregator;

    @BeforeEach
    void setUp() {
        transactionAggregator = new TransactionAggregator();
    }

    @Test
    void exampleTest() {
        // setup
        transactionAggregator.apply("32309111922661937852684864", "06105023389842834748547303", new BigDecimal("10.90"));
        transactionAggregator.apply("31074318698137062235845814", "66105036543749403346524547", new BigDecimal("200.90"));
        transactionAggregator.apply("66105036543749403346524547", "32309111922661937852684864", new BigDecimal("50.10"));

        // when
        Collection<AccountBalance> accountBalances = transactionAggregator.getSortedAccountBalances();

        // then
        assertThat(accountBalances).extracting("account", "debitCount", "creditCount", "balance")
                .containsExactly(Tuple.tuple("06105023389842834748547303", 0, 1, new BigDecimal("10.90")),
                        Tuple.tuple("31074318698137062235845814", 1, 0, new BigDecimal("-200.90")),
                        Tuple.tuple("32309111922661937852684864", 1, 1, new BigDecimal("39.20")),
                        Tuple.tuple("66105036543749403346524547", 1, 1, new BigDecimal("150.80")));

    }

}
