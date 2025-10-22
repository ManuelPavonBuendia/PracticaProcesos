package com.example.informes;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.Map;

public class GeneradorInformeMarkdown implements GeneradorInforme {
    LocalDateTime ahora = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm:ss");
    @Override
    public void crearInforme(Map<String, String> resultados) {

        try (PrintWriter writer = new PrintWriter(new FileWriter("demo/src/main/java/com/example/InformeSistema.md"))) {
            writer.println("# INFORME DEL SISTEMA"+"\n");
            writer.write("Ejecución realizada el día " + ahora.format(formatter) + "\n\n");

            for (Map.Entry<String, String> entry : resultados.entrySet()) {
                writer.println("## Comando: " + entry.getKey());
                writer.println("\n" + entry.getValue() + "\n");
    
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
