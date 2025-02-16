package com.util;

public class EscalaImpl implements Escala {
    private static final long serialVersionUID = 1L;
    private String horario;

    public EscalaImpl(String horario) {
        this.horario = horario;
    }

    @Override
    public void definirEscala(String horario) {
        this.horario = horario;
    }

    @Override
    public String obterEscala() {
        return horario;
    }

    @Override
    public String toString() {
        return "Escala{" +
                "horario= " + horario +
                '}';
    }
}
