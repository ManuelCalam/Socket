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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yahir
 */
public class HiloClienteParlante extends Thread {
    

    public boolean client0finished = false;
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;
    private int id;

    public HiloClienteParlante (int id) {
        this.id = id;
    }
    @Override
    public synchronized void run() {
        while(!client0finished && id == 1){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloClienteParlante.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Scanner scanner = new Scanner(System.in);
        while(!client0finished && id == 1){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloClienteParlante.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

     
        String respuesta="";

        
        try {
            sk = new Socket ("25.39.100.34", 1000);
            dos = new DataOutputStream(sk.getOutputStream());
            dis = new DataInputStream(sk.getInputStream());
            System. out.println (id + " envia saludo");
            //dos.writeUTF("hola");
            
            
            System.out.print("Cliente " + id + " - Ingresa un número: ");
            double numero1 = scanner.nextDouble();
            
            dos.writeUTF(String.valueOf(numero1));
            respuesta = dis.readUTF();
            System. out.println ("Cliente " + id + " - Servidor devuelve saludo: " + respuesta);
            
            System.out.println("s");
            scanner.nextLine(); 
            //notify();
            
            
            System.out.print("Cliente " +id + " - Ingresa la operación (+, -, *, /, %): ");
            double operacion = scanner.nextDouble();
            
            dos.writeUTF(String.valueOf(operacion));
            respuesta = dis.readUTF();
            System. out.println ("Cliente " + id + " - Servidor devuelve saludo: " + respuesta);
            
            scanner.nextLine(); 
            
            
            
                
            client0finished = true;
            
            
            dis.close();
            dos.close();
            sk.close();
        } catch (IOException ex) {
            Logger.getLogger(HiloClienteParlante.class.getName()).log (Level.SEVERE, null, ex);
        }
        
        notifyAll();
    }
}
