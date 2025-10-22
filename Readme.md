# Practica Informe

## Indice
### 1. [Comandos](#comandos)
- [EjecutarComandos.java](#11-ejecutarcomandosjava)
- [RegistrarSalida.java](#12-registrarsalidajava)

### 2. [Informes](#informes)
- [GeneradorInforme.java](#21-generadorinformejava)
- [GeneradorInformeMarkdown.java](#22-generadorinformemarkdownjava)

### 3. [Main.java](#mainjava)

### 4. [Testing](#testing)
- [testEjecutarComando()](#41-testejecutarcomando)
- [testEjecutarVariosComandos()](#42-testejecutarvarioscomandos)
- [testCrearInformeConMock()](#43-testcrearinformeconmock)

## Comandos
Tengo un paquete llamado comandos, dentro tengo lo siguiente:

- EjecutarComandos.java
- RegistrarSalida.java

### 1.1. EjecutarComandos.java
```java
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
```
Dentro de la clase **EjecutarComandos**, tenemos en la función ``ejecucionComandos()``, que lo hace es ejecutar el comando que le paso desde registarSalida, una vez ejecutado leemos la saldia del proceso y almacenamos esa salida linea a linea con **StringBuilder** y devolvemos la salida con un ``toString()``.

### 1.2. RegistrarSalida.java
```java
public class RegistrarSalida {
    EjecutarComandos ejecutor = new EjecutarComandos();
    public Map<String, String> obtenerResultadosComandos(String[]comandos) throws IOException, InterruptedException {
        
        Map<String, String> resultados = new LinkedHashMap<>();
        for (int i =0; i<comandos.length;i++) {
            String salida = ejecutor.ejecucionComandos(comandos[i]);
            resultados.put(comandos[i], salida);
        }
        return resultados;
    }
}
```
En la clase RegistrarSalida, tenemos la función ``obtenerResultadosComandos()`` que recibe una Array de String desde el main con los comandos a ejecutar. La función con esos comandos sera ir pasandole los comandos uno por uno a la función ``ejecucionComandos()`` de la clase **EjecutarComandos**, y lo que devuelva lo almacenamos en un **Map**.
Cada entrada del Map contiene:
 - **Clave**: el comando ejecutado.
 - **Valor**: la salida generada por ese comando.

## Informes

En el paquete informes tengo:
- GeneradorInforme.java
- GeneradorInformeMarkdown.java

### 2.1. GeneradorInforme.java
```java
public interface GeneradorInforme {
    void crearInforme(Map<String, String> resultados);
}

```
He creado la interfaz generador informe para indicar que si se quiere crear un informe independientemente si es pdf,markdown o un txt, se tenga que utilizar un Map para pasarle la información.

### 2.2 GeneradorInformeMarkdown.java

```java
public class GeneradorInformeMarkdown implements GeneradorInforme {

    @Override
    public void crearInforme(Map<String, String> resultados) {

        try (PrintWriter writer = new PrintWriter(new FileWriter("demo/src/main/java/com/example/InformeSistema.md"))) {
            writer.println("# INFORME DEL SISTEMA"+"\n");

            for (Map.Entry<String, String> entry : resultados.entrySet()) {
                writer.println("## Comando: " + entry.getKey());
                writer.println("\n" + entry.getValue() + "\n");
    
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
Esta clase implementa la interfaz GeneradorInforme y genera un informe en formato Markdown. EL metodo va escribiendo el contenido del Map que se le envia desde el main en un archivo llamado InformeSistema.md donde cada comando aparece como un encabezado seguido de su resultado.

## Main.java
```java
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        RegistrarSalida registrar = new RegistrarSalida();
        String[] comandos = {"ps", "df", "free"};

        Map<String, String> resultados = registrar.obtenerResultadosComandos(comandos);

        GeneradorInforme informe = new GeneradorInformeMarkdown();
        informe.crearInforme(resultados);
    }
}
```
Define comandos que vamos a ejecutar: "ps", "df", "free".

Ejecuta los comandos con la función RegistrarSalida y guardamos el resultado en el map resultado.

Genero un informe Markdown con el metodo crearInforme al que le pasare el map con el resultado y ya el metodo se encarga de hacer el archivo marckdown con el contenido del MAP.

## TESTING
### 4.1 ``testEjecutarComando()``
```java
@Test
public void testEjecutarComando() throws IOException, InterruptedException {
        String salida = ejecutor.ejecucionComandos("echo hola");
        assertTrue(salida.contains("hola"));
    }
```
1. Ejecuta el comando del sistema "echo hola" con la función ejecuciónComandos

2. Guarda la salida del comando en la variable salida.

3. Comprueba con assertTrue que esa salida contenga la palabra “hola”.

### 4.2 ``testEjecutarVariosComandos()``
```java
@test
public void testEjecutarVariosComandos() throws Exception, InterruptedException {
        String[] comandos = { "echo uno", "echo dos" };
        Map<String, String> resultados = registrar.obtenerResultadosComandos(comandos);

        assertEquals(2, resultados.size());
        assertTrue(resultados.get("echo uno").contains("uno"));
        assertTrue(resultados.get("echo dos").contains("dos"));
    }
```
1. Define un array con los coamndo que voy a ejecutar
```java
String[] comandos = { "echo uno", "echo dos" };
```
2. Llama al método registrar.obtenerResultadosComandos(comandos) que debería ejecutar cada comando y devolver un mapa **(Map<String, String>)** donde:

- La **clave** es el comando ejecutado.

- El **valor** es la salida de ese comando.

3. Comprueba con los assert que:

- El mapa tenga exactamente 2 resultados.

    - El resultado de "echo uno" contenga "uno".

    - El resultado de "echo dos" contenga "dos".

### 4.3 ``testCrearInformeConMock()``
```java
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
```
1. Creo un mock de la clase GeneradorInforme:
```java
GeneradorInforme mockInforme = mock(GeneradorInforme.class);
```
Esto simula el comportamiento de GeneradorInforme.

2. Creo un Map llamado resultados con salidas de ejemplo de comandos:

- "ps" 

- "df" 

- "free"

3. Llamo al método crearInforme al que le paso el MAp para que haga el infoeme:
```java
mockInforme.crearInforme(resultados);
```
4. Verifico con Mockito que el método crearInforme() fue llamado exactamente una vez con ese mismo mapa como argumento:
```java
verify(mockInforme, times(1)).crearInforme(resultados);
```
**Enlace al repositorio**: https://github.com/ManuelPavonBuendia/PracticaProcesos.git