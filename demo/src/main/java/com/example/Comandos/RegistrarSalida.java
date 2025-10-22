package com.example.Comandos;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RegistrarSalida {
    EjecutarComandos ejecutor = new EjecutarComandos();
    public Map<String, String> obtenerResultadosComandos(String[] comandos) throws IOException, InterruptedException {
        
        Map<String, String> resultados = new LinkedHashMap<>();
        for (int i =0; i<comandos.length;i++) {
            String salida = ejecutor.ejecucionComandos(comandos[i]);
            resultados.put(comandos[i], salida);
        }
        return resultados;
    }
}
