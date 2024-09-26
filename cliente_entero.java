import java.io.*;
import java.net.*;
import java.util.Scanner;

public class cliente_entero {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5001);
            System.out.println("Conectado al servidor");
            
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
            
            while(true) {
                System.out.print("Ingrese un número entero (0 para salir): ");
                int number = scanner.nextInt();
                
                out.writeInt(number);
                System.out.println("Número enviado: " + number);
                
                if(number == 0) {
                    System.out.println("Terminando el programa");
                    break;
                }
                
                int response = in.readInt();
                System.out.println("Respuesta del servidor: " + response);
            }
            
            socket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
