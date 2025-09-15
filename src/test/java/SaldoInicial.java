package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TesteSaldoInicialCarteira {

    @ParameterizedTest
    @ValueSource(doubles = {15.0, 0.0, 100.5})
    void deveInicializarCarteiraComSaldoValido(double saldoInformado) {
        DigitalWallet carteira = new DigitalWallet("GuilhermeCliente", saldoInformado);
        assertEquals(saldoInformado, carteira.getBalance(), 0.0001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -50.5, -999.99})
    void naoDevePermitirSaldoInicialNegativo(double saldoInvalido) {
        assertThrows(IllegalArgumentException.class, () -> {
            new DigitalWallet("GuilhermeCliente", saldoInvalido);
        });
    }
}
