package com.example;

import com.example.Comandos.RegistrarSalida;
import com.example.informes.GeneradorInforme;
import com.example.informes.GeneradorInformeMarkdown;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        RegistrarSalida registrar = new RegistrarSalida();
        String[] comandos = {"ps", "df", "free"};

        // Ejecutar comandos y obtener Map comando → resultado
        Map<String, String> resultados = registrar.obtenerResultadosComandos(comandos);

        // Generar informe usando la interfaz
        GeneradorInforme informe = new GeneradorInformeMarkdown();
        informe.crearInforme(resultados);
    }
}