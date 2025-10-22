package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;
import java.util.Map;
import com.example.Comandos.*;

public class TestJUnit {

    private EjecutarComandos ejecutor;
    private RegistrarSalida registrar;
    @BeforeEach
    void setUp() {
        ejecutor = new EjecutarComandos();
        registrar = new RegistrarSalida();
    }

    @Test
    public void testEjecutarComando() throws IOException, InterruptedException {
        String salida = ejecutor.ejecucionComandos("echo hola");
        assertTrue(salida.contains("hola"));
    }

    @Test
    public void testEjecutarVariosComandos() throws Exception, InterruptedException {
        String[] comandos = { "echo uno", "echo dos" };
        Map<String, String> resultados = registrar.obtenerResultadosComandos(comandos);

        assertEquals(2, resultados.size());
        assertTrue(resultados.get("echo uno").contains("uno"));
        assertTrue(resultados.get("echo dos").contains("dos"));
    }

}
