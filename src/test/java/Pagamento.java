import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;

public class Pagamento {

    @ParameterizedTest
    @CsvSource({
        "100.0, 30.0, true",
        "50.0, 80.0, false",
        "10.0, 10.0, true"
    })
    void pagamentoComCarteiraVerificadaENaoBloqueada(double inicial, double valor, boolean esperado) {
        DigitalWallet digitalWallet = new DigitalWallet("Deivyson", inicial);
        digitalWallet.verify();
        digitalWallet.unlock();

        assumeTrue(digitalWallet.isVerified() && !digitalWallet.isLocked());

        assertEquals(esperado, digitalWallet.pay(valor));
    }

    @ParameterizedTest
    @ValueSource(doubles = { -10.0, 0.0, -0.1 })
    void deveLancarExcecaoParaPagamentoInvalido(double valor) {
        DigitalWallet digitalWallet = new DigitalWallet("Deivyson", 100);
        digitalWallet.unlock();
        digitalWallet.verify();

        assumeTrue(digitalWallet.isVerified() && !digitalWallet.isLocked());

        assertThrows(IllegalArgumentException.class, () -> {
            digitalWallet.pay(valor);
        });
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        DigitalWallet digitalWallet = new DigitalWallet("Deivyon", 100);

        assertThrows(IllegalStateException.class, () -> {
            digitalWallet.pay(10);
        });
    }
}
