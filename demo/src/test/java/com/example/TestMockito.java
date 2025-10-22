package com.example;

import com.example.informes.GeneradorInforme;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestMockito {

      @Test
    
    public void testCrearInformeConMock() {

        GeneradorInforme mockInforme = mock(GeneradorInforme.class);

        Map<String, String> resultados = new LinkedHashMap<>();
        resultados.put("ps", "salida del comando ps");
        resultados.put("df -h", "salida del comando df");
        resultados.put("free -h", "salida del comando free");

        mockInforme.crearInforme(resultados);

        verify(mockInforme, times(1)).crearInforme(resultados);
    }
}