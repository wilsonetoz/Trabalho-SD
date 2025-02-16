package com.util;

import java.io.Serializable;

public interface Escala extends Serializable {
    void definirEscala(String horario);

    String obterEscala();
}
