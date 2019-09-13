package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client
{
    private static final int PORT = 4763;
    private Socket clientSocket;
    private ObjectOutputStream coos;
    private ObjectInputStream cois;

    public Client()
    {
        try
        {
        clientSocket = new Socket("127.0.0.1", PORT);
        coos = new ObjectOutputStream(clientSocket.getOutputStream());
        cois = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public String sendMessage(String message)
    {
        String answer = "";
        try
        {
            coos.writeObject(message);
            answer = (String) cois.readObject();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
            answer = "Error!";
        }
        return answer;
    }

    public void close()
    {
        sendMessage("quit");
        try {
            coos.close();
            cois.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
