package pl.artkak.app.utils;

import io.vertx.rxjava3.core.buffer.Buffer;
import org.junit.jupiter.api.Test;
import pl.artkak.app.model.TransactionRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JsonObjectMapperTest {


    @Test
    void simpleListDeserialization() {
        // setup
        TransactionRequest expected = new TransactionRequest();
        expected.setAmount(new BigDecimal("10.90"));
        expected.setDebitAccount("32309111922661937852684864");
        expected.setCreditAccount("06105023389842834748547303");

        // when

        List<TransactionRequest> result = JsonObjectMapper.deserializeList(TransactionRequest.class, Buffer.buffer("""
                [
                  {
                    "debitAccount": "32309111922661937852684864",
                    "creditAccount": "06105023389842834748547303",
                    "amount": 10.90
                  }
                ]
                """));
        // then
        assertThat(result)
                .isNotEmpty()
                .first().usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void simpleDeserialization() {
        // setup
        TransactionRequest expected = new TransactionRequest();
        expected.setAmount(new BigDecimal("10.90"));
        expected.setDebitAccount("32309111922661937852684864");
        expected.setCreditAccount("06105023389842834748547303");

        // when

        TransactionRequest result = JsonObjectMapper.deserialize(TransactionRequest.class, Buffer.buffer("""
                  {
                    "debitAccount": "32309111922661937852684864",
                    "creditAccount": "06105023389842834748547303",
                    "amount": 10.90
                  }
                """));

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}
