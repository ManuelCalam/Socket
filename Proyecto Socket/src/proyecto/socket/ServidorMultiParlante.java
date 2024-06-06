package proyecto.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorMultiParlante implements Runnable {
    private static final double[] numbers = new double[2];
    private static final String[] operations = new String[2];
    private static final AtomicInteger count = new AtomicInteger(0);
    private final Socket socket;
    private final int idSession;
    private DataOutputStream dos;
    private DataInputStream dis;

    public ServidorMultiParlante(Socket socket, int id) {
        this.socket = socket;
        this.idSession = id;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            double number = Double.parseDouble(dis.readUTF());
            String operation = dis.readUTF();

            synchronized (ServidorMultiParlante.class) {
                numbers[idSession] = number;
                operations[idSession] = operation;
                count.incrementAndGet();
                if (count.get() < 2) {
                    ServidorMultiParlante.class.wait();
                } else {
                    ServidorMultiParlante.class.notifyAll();
                }
            }

            synchronized (ServidorMultiParlante.class) {
                double result = calculateResult(idSession);
                dos.writeUTF(String.valueOf(result));
                disconnect();
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private double calculateResult(int session) {
        double num1 = numbers[0];
        double num2 = numbers[1];
        String operation = operations[session];

        switch (operation) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            case "%":
                return num1 % num2;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
    }
}
