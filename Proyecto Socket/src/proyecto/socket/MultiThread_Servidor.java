package proyecto.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiThread_Servidor {
    static final int PUERTO = 1000;

    public static void main(String[] args) {
        ServerSocket ss;
        System.out.print("Inicializando servidor ... ");
        try {
            ss = new ServerSocket(PUERTO);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket = ss.accept();
                System.out.println("Nueva conexion entrante: " + socket);
                new Thread(new ServidorMultiParlante(socket, idSession)).start();
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(MultiThread_Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
