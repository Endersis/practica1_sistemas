import java.io.*;
import java.net.*;

public class servidor {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Servidor esperando conexiones...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado");
                
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                String inputLine = in.readLine();
                System.out.println("Mensaje recibido: " + inputLine);
                
                out.println("Hola que tal");
                
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port 5000 or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
