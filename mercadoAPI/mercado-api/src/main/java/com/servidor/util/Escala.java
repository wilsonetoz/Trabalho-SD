package com.servidor.util;

import java.util.Map;

public interface Escala {
    Map<String, String> obterEscala();

    void setEscala(String dia, String horario);
}