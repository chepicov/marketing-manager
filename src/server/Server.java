package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server
{
    private static final int PORT = 4763;
    private static int clientsNumber = 0;
    private static HashMap<Integer,ConnectedClient> connectionMap = new HashMap<>();
    private static DatabaseHandler dbHandler;
    private static ServerSocket serverSocket;

    public static void main(String[] args)
    {
        Socket clientSocket;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server starting...");
            dbHandler = new DatabaseHandler();
            System.out.println("Connected to database!");
            while(true)
            {
                if(br.ready())
                {
                    String serverCommand = br.readLine();
                    if(serverCommand.equalsIgnoreCase("quit"))
                    {
                        System.out.println("Stopping server request!");
                        break;
                    }
                }
                clientSocket = serverSocket.accept();
                clientsNumber++;
                System.out.println("Connection established. Client port - " + clientSocket.getPort());
                System.out.println("There are " + clientsNumber + " clients on this server.");
                connectionMap.put(clientSocket.getPort(), new ConnectedClient(clientSocket));
            }

            while (clientsNumber > 0)
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                }
            }
            stopServer();
            br.close();
        }
        catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }

    public static synchronized void showMessage(String str)
    {
        System.out.println("Client message: " + str);
    }

    public static synchronized void clientDisconnected(int clientPort)
    {
        clientsNumber--;
        System.out.println("Client " + clientPort + " disconnected! There are " + clientsNumber +
                " clients on this server!");
        connectionMap.remove(clientPort);
    }

    public static synchronized DatabaseHandler getDbHandler()
    {
        return dbHandler;
    }

    public static void stopServer()
    {
        System.out.println("Main server starting close operation...");
        try
        {
            serverSocket.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
