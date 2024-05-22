/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yahir
 */
public class MultiThread_Servidor {
    
    static final int PUERTO = 1000;

    public static void main(String[] args) throws IOException {

        ServerSocket ss;
        System. out.print ("Inicializando servidor ... ");
        try {
            ss = new ServerSocket (PUERTO) ;
            System. out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System. out.println ("Nueva conexion entrante: "+ socket);
                ((ServidorMultiParlante) new ServidorMultiParlante (socket, idSession) ).run();
                idSession++;
            }

        } catch (IOException ex) {
        Logger.getLogger(MultiThread_Servidor.class.getName ()) .log(Level.SEVERE, null, ex);
                }
        }
}
