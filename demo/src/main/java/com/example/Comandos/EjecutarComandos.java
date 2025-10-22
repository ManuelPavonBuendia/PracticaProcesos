package com.example.Comandos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EjecutarComandos {
        public String  ejecucionComandos(String comando) throws IOException, InterruptedException {
        StringBuilder salida = new StringBuilder();
        Process proceso = Runtime.getRuntime().exec(comando);
        BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));

        String linea;
        while ((linea = reader.readLine()) != null) {
            salida.append(linea).append("\n");
        }

        proceso.waitFor();
        return salida.toString();
    }



}
