import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.Scanner;

public class Asignacion_9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> contrasenas = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        System.out.println("Bienvenido al validador de contraseñas.");
        System.out.println("Ingrese las contraseñas que desea validar. Escriba 'salir' para terminar:");

        // Leer contraseñas del usuario
        while (true) {
            System.out.print("Ingrese una contraseña: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("salir")) {
                break;
            }
            contrasenas.add(input);
        }
        // Validar las contraseñas de manera concurrente
        for (String contrasena : contrasenas) {
            executor.execute(new ValidadorContrasena(contrasena));
        }

        // Apagar el pool de hilos después de completar las tareas
        executor.shutdown();

        System.out.println("Validación en proceso. Por favor, espere...");
        scanner.close();
    }
}
class ValidadorContrasena implements Runnable {
    private final String contrasena;

    public ValidadorContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public void run() {
        if (esContrasenaValida(contrasena)) {
            System.out.println("La contraseña '" + contrasena + "' es válida.");
        } else {
            System.out.println("La contraseña '" + contrasena + "' no cumple con los requisitos.");
        }
    }

    private boolean esContrasenaValida(String contrasena) {
        // Verificar la longitud mínima de 8 caracteres
        if (contrasena.length() < 8) return false;

        // Verificar la presencia de al menos un carácter especial
        if (!Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(contrasena).find()) return false;

        // Verificar la presencia de al menos 2 letras mayúsculas
        if (Pattern.compile("[A-Z]").matcher(contrasena).results().count() < 2) return false;

        // Verificar la presencia de al menos 3 letras minúsculas
        if (Pattern.compile("[a-z]").matcher(contrasena).results().count() < 3) return false;

        // Verificar la presencia de al menos 1 número
        if (!Pattern.compile("[0-9]").matcher(contrasena).find()) return false;

        return true;
    }
}
