import java.util.*;
import java.util.stream.Collectors;

public class TallerPruebasSoftware {

    // Ejercicio 1: Clase TestCase
    static class TestCase {
        private int id;
        private String nombre;
        private String estado; // "PASSED", "FAILED", "BLOCKED"

        public TestCase(int id, String nombre, String estado) {
            this.id = id;
            this.nombre = nombre;
            setEstado(estado);
        }

        // Getters y Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getEstado() { return estado; }

        public void setEstado(String estado) {
            if (estado.equals("PASSED") || estado.equals("FAILED") || estado.equals("BLOCKED")) {
                this.estado = estado;
            } else {
                System.out.println("Error: Estado no válido. Debe ser PASSED, FAILED o BLOCKED");
            }
        }

        @Override
        public String toString() {
            return String.format("ID: %d, Nombre: %s, Estado: %s", id, nombre, estado);
        }
    }

    // Ejercicio 2: Clase TestSuite
    static class TestSuite {
        private List<TestCase> casosDePrueba = new ArrayList<>();

        public void agregarCaso(TestCase caso) {
            casosDePrueba.add(caso);
        }

        public void eliminarCaso(int id) {
            casosDePrueba.removeIf(caso -> caso.getId() == id);
        }

        public void mostrarCasos() {
            System.out.println("\nCasos en TestSuite:");
            casosDePrueba.forEach(System.out::println);
        }
    }

    // Ejercicio 3: Gestión de resultados con HashMap
    static class GestorResultados {
        private Map<Integer, String> resultados = new HashMap<>();

        public void agregarResultado(int id, String estado) {
            if (validarEstado(estado)) {
                resultados.put(id, estado);
            }
        }

        public void actualizarResultado(int id, String nuevoEstado) {
            if (resultados.containsKey(id) && validarEstado(nuevoEstado)) {
                resultados.put(id, nuevoEstado);
            }
        }

        public void mostrarResultados() {
            System.out.println("\nResultados almacenados:");
            resultados.forEach((k, v) -> System.out.printf("Caso %d: %s%n", k, v));
        }

        private boolean validarEstado(String estado) {
            if (estado.equals("PASSED") || estado.equals("FAILED") || estado.equals("BLOCKED")) {
                return true;
            }
            System.out.println("Error: Estado no válido");
            return false;
        }
    }

    // Ejercicio 4: Validación de entradas
    static class ValidadorEstados {
        public static String validarEstado(Scanner sc, String estadoInicial) {
            String estado = estadoInicial;
            while (true) {
                if (estado.equals("PASSED") || estado.equals("FAILED") || estado.equals("BLOCKED")) {
                    return estado;
                }
                System.out.print("Estado no válido. Ingrese PASSED, FAILED o BLOCKED: ");
                estado = sc.nextLine().toUpperCase();
            }
        }
    }

    // Ejercicio 7: Herencia - PruebaAutomatizada
    static class PruebaAutomatizada extends TestCase {
        private String framework;

        public PruebaAutomatizada(int id, String nombre, String estado, String framework) {
            super(id, nombre, estado);
            this.framework = framework;
        }

        public void mostrarFramework() {
            System.out.printf("Caso %s ejecutado con %s%n", getNombre(), framework);
        }

        @Override
        public String toString() {
            return super.toString() + ", Framework: " + framework;
        }
    }

    // Ejercicio 8: Polimorfismo - Reportes
    static abstract class ReporteTest {
        public abstract void generarReporte();
    }

    static class ReporteHTML extends ReporteTest {
        @Override
        public void generarReporte() {
            System.out.println("Generando reporte en formato HTML...");
        }
    }

    static class ReportePDF extends ReporteTest {
        @Override
        public void generarReporte() {
            System.out.println("Generando reporte en formato PDF...");
        }
    }

    // Ejercicio 9: Abstracción - Casos de prueba
    static abstract class CasoDePruebaAbstracto {
        public abstract void ejecutarPrueba();
    }

    static class PruebaManual extends CasoDePruebaAbstracto {
        @Override
        public void ejecutarPrueba() {
            System.out.println("Ejecutando prueba manual...");
        }
    }

    static class PruebaAuto extends CasoDePruebaAbstracto {
        @Override
        public void ejecutarPrueba() {
            System.out.println("Ejecutando prueba automatizada con Selenium...");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== EJERCICIO 1 ===");
        TestCase caso1 = new TestCase(1, "Login Test", "PASSED");
        TestCase caso2 = new TestCase(2, "Register Test", "FAILED");
        System.out.println(caso1);
        System.out.println(caso2);

        System.out.println("\n=== EJERCICIO 2 ===");
        TestSuite suite = new TestSuite();
        suite.agregarCaso(caso1);
        suite.agregarCaso(caso2);
        suite.agregarCaso(new TestCase(3, "Search Test", "BLOCKED"));
        suite.mostrarCasos();
        suite.eliminarCaso(2);
        System.out.println("Después de eliminar caso 2:");
        suite.mostrarCasos();

        System.out.println("\n=== EJERCICIO 3 ===");
        GestorResultados gestor = new GestorResultados();
        gestor.agregarResultado(1, "PASSED");
        gestor.agregarResultado(2, "FAILED");
        gestor.agregarResultado(3, "BLOCKED");
        gestor.agregarResultado(4, "PASSED");
        gestor.agregarResultado(5, "INVALIDO"); // Mostrará error
        gestor.mostrarResultados();
        gestor.actualizarResultado(2, "PASSED");
        System.out.println("Después de actualizar caso 2:");
        gestor.mostrarResultados();

        System.out.println("\n=== EJERCICIO 4 ===");
        System.out.print("Ingrese estado de prueba: ");
        String estado = sc.nextLine().toUpperCase();
        String estadoValidado = ValidadorEstados.validarEstado(sc, estado);
        System.out.println("Estado validado: " + estadoValidado);

        System.out.println("\n=== EJERCICIO 5 ===");
        List<String> resultadosEjecucion = Arrays.asList("PASSED", "FAILED", "BLOCKED", "PASSED", "FAILED");
        Map<String, Long> conteo = resultadosEjecucion.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        System.out.println("Total de pruebas ejecutadas: " + resultadosEjecucion.size());
        conteo.forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("\n=== EJERCICIO 6 ===");
        try {
            System.out.println("10 / 2 = " + (10 / 2));
            System.out.println("8 / 0 = " + (8 / 0));
        } catch (ArithmeticException e) {
            System.out.println("Error: División por cero no permitida.");
        }
        try {
            System.out.println("15 / 3 = " + (15 / 3));
        } catch (ArithmeticException e) {
            System.out.println("Error: División por cero no permitida.");
        }

        System.out.println("\n=== EJERCICIO 7 ===");
        PruebaAutomatizada auto = new PruebaAutomatizada(10, "Login Auto", "PASSED", "Selenium");
        System.out.println(auto);
        auto.mostrarFramework();

        System.out.println("\n=== EJERCICIO 8 ===");
        ReporteTest html = new ReporteHTML();
        ReporteTest pdf = new ReportePDF();
        html.generarReporte();
        pdf.generarReporte();

        System.out.println("\n=== EJERCICIO 9 ===");
        CasoDePruebaAbstracto manual = new PruebaManual();
        CasoDePruebaAbstracto automatizada = new PruebaAuto();
        manual.ejecutarPrueba();
        automatizada.ejecutarPrueba();
    }
}