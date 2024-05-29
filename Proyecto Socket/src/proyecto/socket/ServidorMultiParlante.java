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
 * @author 
 */
public class ServidorMultiParlante {

    public static double num1 = 0, num2 = 0;
    public static double [] ops = new double[2];
    public double resultado = 0;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSession;

    public ServidorMultiParlante (Socket socket, int id) {
        this.socket = socket;
        this.idSession = id;
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
    
    public void run()  {
        String accion = "";
        try {
                accion = dis.readUTF();

                if(idSession == 0){
                    num1 = Double.parseDouble(accion);
                    dos.writeUTF(String.valueOf("El servidor dice, num1 del cliente: " + num1));
                }
                else if(idSession == 1){
                    num2 = Double.parseDouble(accion);
                    dos.writeUTF(String.valueOf("El servidor dice, num2 del cliente: " + num2));
                }

                //if (accion. equals ("hola")) {
                    //System. out.println ("El cliente con idSesion "+this.idSessio+" saluda");
                    //dos.writeUTF("adios");
                //}

                try {
                    wait();
                } catch (Exception e) {
                }

                accion = dis.readUTF();
                
                if(idSession == 0){
                    ops[0] = Double.parseDouble(accion);
                    dos.writeUTF(String.valueOf("El servidor dice, op1 del cliente: " + ops[idSession]));
                }
                else if(idSession == 1){
                    ops[1] = Double.parseDouble(accion);
                    dos.writeUTF(String.valueOf("El servidor dice, op2 del cliente: " + ops[idSession]));
                }



                



                /*if(num1 != 0){
                    System.out.println("Cliente " + this.idSessio + "escogió " + num1);
                    dos.write(num1);
                }*/
           
            
            
        } catch (IOException ex) {
            Logger. getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        for(int i = 0; i<2; i++){
            switch((int)(ops[i])){
                case 1:
                    resultado = num1+num2;
                    break;
                case 2:
                    resultado = num1-num2;
                    break;
                case 3:
                    resultado = num1*num2;
                    break;
                case 4:
                    resultado = num1/num2;
                    break;
                case 5:
                    resultado = num1%num2;
                    break;
                default:
                    System.out.println("Operación inválida");
                    
            }
            System.out.println("El resultado de la op #"+(idSession+1)+" es: "+ resultado);
            
            
        }
        
        
        //desconnectar();
    }
    
}

