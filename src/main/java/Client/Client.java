package Client;

import java.io.*;
import java.net.*;

public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your name");

        String name = consoleReader.readLine();


        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Connected to the chat server...");

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            Thread readThread = new Thread(() -> {
                try {
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                    String receivedMessage;
                    while ((receivedMessage = reader.readLine()) != null) {
                        System.out.println(receivedMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readThread.start();

            String message;
            while (true) {
                message = name+" : " +consoleReader.readLine();
                if ("quit".equalsIgnoreCase(message)) {
                    break;
                }
                writer.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
