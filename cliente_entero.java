import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

public class IntegerClient {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 5001);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Conectado al servidor");
            
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.order(ByteOrder.nativeOrder());  // Usar el orden de bytes nativo
            
            while (true) {
                System.out.print("Ingrese un número entero (0 para salir): ");
                int number = scanner.nextInt();
                
                // Enviar número
                buffer.putInt(0, number);
                out.write(buffer.array());
                System.out.println("Número enviado: " + number);
                
                if (number == 0) {
                    System.out.println("Terminando el programa");
                    break;
                }
                
                // Recibir respuesta
                in.read(buffer.array());
                int response = buffer.getInt(0);
                System.out.println("Respuesta del servidor: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
