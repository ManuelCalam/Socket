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
import java.net.Socket;

/**
 *
 * @author yahir
 */
public class Cliente {
    public static final int PUERTO = 1000;
    public static final String HOST = "25.39.100.34";

    public static void main(String[] args) throws IOException {

        System. out.println ("addr - " + HOST);
        Socket socket = new Socket (HOST, PUERTO) ;
            try{
                BufferedReader entrada = new BufferedReader (new InputStreamReader (socket.getInputStream()) );
                PrintWriter salida = new PrintWriter (new BufferedWriter (new OutputStreamWriter (socket.getOutputStream())), true) ;
                salida.println ("Usuario: " + "Hola server soy tu cliente que vale mil");
                String str = entrada.readLine();
                System.out.println(str);
                salida.println ("FIN") ;

        }finally{
            System.out.println ("cerrando ... ");
            socket.close ();
        }
    }
}
