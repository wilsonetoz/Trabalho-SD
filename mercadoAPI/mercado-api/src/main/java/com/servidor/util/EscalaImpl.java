package com.servidor.util;

import java.util.HashMap;
import java.util.Map;

public class EscalaImpl implements Escala {
    private final Map<String, String> escala = new HashMap<>();

    @Override
    public Map<String, String> obterEscala() {
        return new HashMap<>(escala);
    }

    @Override
    public void setEscala(String dia, String horario) {
        escala.put(dia, horario);
    }

    // Adiciona um getter para o Jackson serializar o objeto
    public Map<String, String> getEscala() {
        return escala;
    }

    // Adiciona um setter para o Jackson desserializar o objeto
    public void setEscala(Map<String, String> escala) {
        this.escala.clear();
        this.escala.putAll(escala);
    }
}