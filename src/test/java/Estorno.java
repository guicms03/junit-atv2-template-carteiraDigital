package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class TesteDeEstornoCarteira {

    static Stream<Arguments> fornecerDadosParaEstorno() {
        return Stream.of(
            Arguments.of(100.0, 10.0, 110.0),
            Arguments.of(0.0, 5.0, 5.0),
            Arguments.of(50.0, 0.01, 50.01)
        );
    }

    @ParameterizedTest
    @MethodSource("fornecerDadosParaEstorno")
    void deveAdicionarEstornoAoSaldo(double saldoInicial, double valorEstornado, double saldoFinalEsperado) {
        DigitalWallet carteiraDigital = new DigitalWallet("GuilhermeTeste", saldoInicial);
        carteiraDigital.unlock();
        carteiraDigital.verify();
        assumeTrue(!carteiraDigital.isLocked() && carteiraDigital.isVerified());

        carteiraDigital.refund(valorEstornado);

        assertEquals(saldoFinalEsperado, carteiraDigital.getBalance(), 0.0001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10.0, 0.0, -0.1})
    void naoDevePermitirEstornoComValorInvalido(double valorInvalido) {
        DigitalWallet carteiraDigital = new DigitalWallet("GuilhermeTeste", 200.0);
        carteiraDigital.verify();
        carteiraDigital.unlock();
        assumeTrue(!carteiraDigital.isLocked() && carteiraDigital.isVerified());

        assertThrows(IllegalArgumentException.class, () -> carteiraDigital.refund(valorInvalido));
    }

    @Test
    void estornoNaoPermitidoSeCarteiraBloqueadaOuNaoVerificada() {
        DigitalWallet carteiraDigital = new DigitalWallet("GuilhermeTeste", 150.0);

        assertThrows(IllegalStateException.class, () -> carteiraDigital.refund(25.0));
    }
}
