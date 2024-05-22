/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author yahir
 */
public class Servidor {
    
    // Elegir un Puerto del rango 1-1024:
    public static final int PUERTO = 1000;

    public static void main (String [] args) throws IOException {
        ServerSocket S = new ServerSocket (PUERTO) ;
        System. out .println ("Empezado: " + S) ;
        try{
            Socket socket = S.accept ();
            try{
                System.out.println("Conexion aceptada: " + socket);
                BufferedReader entrada = new BufferedReader (new InputStreamReader (socket.getInputStream()));
                    PrintWriter salida = new PrintWriter (new BufferedWriter (new OutputStreamWriter (socket. getOutputStream())), true);
                        while (true) {
                        String str = entrada.readLine ();
                        if (str. equals ("FIN") ) break;
                            System. out.println ("Reproduciendo" + " " + str);
                            salida.println(str);
                        }
                        

            }finally{
            System.out.println ("Cerrando .... ");
            socket.close();
            }

        }finally {
            S.close();
        }
    }
}
