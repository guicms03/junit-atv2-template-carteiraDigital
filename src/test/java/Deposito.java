package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TesteDepositoCarteira {

    private DigitalWallet carteira;

    @BeforeEach
    void inicializarCarteira() {
        carteira = new DigitalWallet("TesteUsuario", 0.0);
    }

    @ParameterizedTest
    @ValueSource(doubles = {5.0, 20.75, 1000.00})
    void deveAdicionarValorValidoAoSaldo(double valorDepositado) {
        double saldoAntes = carteira.getBalance();
        carteira.deposit(valorDepositado);
        double saldoEsperado = saldoAntes + valorDepositado;
        assertEquals(saldoEsperado, carteira.getBalance(), 0.0001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -5.5, -100.0})
    void naoDevePermitirDepositoComValorInvalido(double valorInvalido) {
        assertThrows(IllegalArgumentException.class, () -> carteira.deposit(valorInvalido));
    }
}
