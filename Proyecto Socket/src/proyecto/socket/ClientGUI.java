package proyecto.socket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientGUI extends JFrame {
    private final int id;
    private final JTextField numField;
    private final JLabel resultLabel;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    public ClientGUI(int id) {
        setLocationRelativeTo(null);
        this.id = id;
        setTitle("Client " + id);
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        numField = new JTextField(10);
        
        inputPanel.add(new JLabel("Numero:"));
        inputPanel.add(numField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));

        String[] operations = {"+", "-", "*", "/", "%"};
        for (String operation : operations) {
            JButton button = new JButton(operation);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendNumberAndOperation(operation);
                }
            });
            buttonPanel.add(button);
        }

        resultLabel = new JLabel("Resultado: ");
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        try {
            socket = new Socket("25.39.100.34", 1000);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        setVisible(true);
    }

    private void sendNumberAndOperation(String operation) {
        try {
            double number = Double.parseDouble(numField.getText());

            dos.writeUTF(String.valueOf(number));
            dos.writeUTF(operation);

            String response = dis.readUTF();
            resultLabel.setText("Result: " + response);

        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new ClientGUI(0);
    }
}
