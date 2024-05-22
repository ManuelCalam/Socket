/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yahir
 */
public class ServidorMultiParlante {

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSessio;

    public ServidorMultiParlante (Socket socket, int id) {
        this.socket = socket;
        this.idSessio = id;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger. getLogger (ServidorMultiParlante.class.getName()).log(Level. SEVERE, null, ex) ;
        }
    }
    public void desconnectar() {
        try {
            socket.close ();
        } catch (IOException ex) {
            Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run() {
        String accion = "";
        try {
            accion = dis.readUTF();
            if (accion. equals ("hola")) {
            System. out.println ("El cliente con idSesion "+this.idSessio+" saluda");
            dos.writeUTF("adios");
            }
        } catch (IOException ex) {
            Logger. getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null, ex);
        }    
        desconnectar ();
    }
    
}
