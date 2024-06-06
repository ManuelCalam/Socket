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
    private final JComboBox<String> operationBox;
    private final JLabel resultLabel;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    public ClientGUI(int id) {
        this.id = id;
        setTitle("Client " + id);
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        numField = new JTextField(10);
        operationBox = new JComboBox<>(new String[]{"+", "-", "*", "/", "%"});
        JButton sendButton = new JButton("Send");

        inputPanel.add(new JLabel("Numero:"));
        inputPanel.add(numField);
        inputPanel.add(new JLabel("Operación:"));
        inputPanel.add(operationBox);
        inputPanel.add(sendButton);

        resultLabel = new JLabel("Resultado: ");
        add(inputPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendNumberAndOperation();
            }
        });

        try {
            socket = new Socket("25.39.100.34", 1000);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        setVisible(true);
    }

    private void sendNumberAndOperation() {
        try {
            double number = Double.parseDouble(numField.getText());
            String operation = (String) operationBox.getSelectedItem();

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
