package proyecto.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloClienteParlante extends Thread {

    private static boolean client0finished = false;
    private int id;
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;

    public HiloClienteParlante(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        synchronized (HiloClienteParlante.class) {
            while (id == 1 && !client0finished) {
                try {
                    HiloClienteParlante.class.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(HiloClienteParlante.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            Scanner scanner = new Scanner(System.in);
            String respuesta = "";

            try {
                sk = new Socket("25.39.100.34", 1000);
                dos = new DataOutputStream(sk.getOutputStream());
                dis = new DataInputStream(sk.getInputStream());
                System.out.println(id + " envia saludo");

                System.out.print("Cliente " + id + " - Ingresa un número: ");
                double numero1 = scanner.nextDouble();

                dos.writeUTF(String.valueOf(numero1));
                respuesta = dis.readUTF();
                System.out.println("Cliente " + id + " - Servidor devuelve saludo. " + respuesta);

                scanner.nextLine();

                System.out.print("Cliente " + id + " - Ingresa la operación (+, -, *, /, %): ");
                String operacion = scanner.next();
                
                dos.writeUTF(operacion);
                respuesta = dis.readUTF();
                System.out.println("Cliente " + id + " - Servidor devuelve saludo. " + respuesta);

                dis.close();
                dos.close();
                sk.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloClienteParlante.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (id == 0) {
                client0finished = true;
                HiloClienteParlante.class.notifyAll();
            }
        }
    }
}