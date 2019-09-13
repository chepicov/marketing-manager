package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ConnectedClient implements Runnable {

    private Socket clientSocket;
    private ObjectOutputStream soos;
    private ObjectInputStream sois;
    private String command;
    private Thread t;
    private DatabaseHandler dbHandler;

    public ConnectedClient(Socket clientSocket) throws IOException
    {
        this.clientSocket = clientSocket;
        soos = new ObjectOutputStream(this.clientSocket.getOutputStream());
        sois = new ObjectInputStream(this.clientSocket.getInputStream());
        dbHandler = Server.getDbHandler();
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                String message = (String)sois.readObject();
                Server.showMessage(message);
                receiveCommand(message);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private String receiveCommand(String request)
    {
        String[] data = parseClientData(request);
        switch (command) {
            case "addCategory": addCategory(data); break;
            case "addAds": addAds(data); break;
            case "getCategories": getCategories(); break;
            case "getProducts": getProducts(data); break;
            case "addProduct": addProduct(data); break;
            case "updateProduct": updateProduct(data); break;
            case "removeProduct": removeProduct(data); break;
            case "getAds": getAds(data); break;
            case "getAllAds": getAllAds(); break;
            case "getOrders": getOrders(); break;
            case "getProductByName": getProductByName(data); break;
            case "getCategoryByName": getCategoryByName(data); break;
            case "getAdsByProduct": getAdsByProduct(data); break;
            case "onAdsClick": onAdsClick(data); break;
            case "signin": signin(data); break;
            case "signup": signup(data); break;
            case "addOrder": addOrder(data); break;
            case "getAllProducts": getAllProducts(); break;
            case "quit": quit(); break;
        }
        return request;
    }

    private String[] parseClientData(String str)
    {
        String[] strMas = str.split(Pattern.quote(";"));
        command = strMas[0];
        return Arrays.copyOfRange(strMas, 1, strMas.length);
    }

    private void addCategory(String[] data) {
        Server.showMessage("Adding category request!");
        dbHandler.addCategory(data);
        sendMessage("");
    }

    private void addOrder(String[] data) {
        Server.showMessage("Adding order request!");
        dbHandler.addOrder(data);
        sendMessage("");
    }

    private void addProduct(String[] data) {
        Server.showMessage("Adding product request!");
        dbHandler.addProduct(data);
        sendMessage("");
    }

    private void updateProduct(String[] data) {
        Server.showMessage("Updating product request!");
        //dbHandler.addProduct(data);
        sendMessage("");
    }

    private void removeProduct(String[] data) {
        Server.showMessage("Removing product request!");
        //dbHandler.addProduct(data);
        sendMessage("");
    }

    private void addAds(String[] data) {
        Server.showMessage("Adding ads request!");
        dbHandler.addAds(data);
        sendMessage("AD");
    }
    private void getCategories() {
        Server.showMessage("Get categories request!");
        String message = dbHandler.getCategories();
        System.out.println(message);
        sendMessage(message);
    }

    private void getAdsByProduct(String[] data) {
        Server.showMessage("Get ads by name request!");
        String message = dbHandler.getAdsByProduct(data);
        if (message.equals("")) {
            System.out.println("Not found");
            sendMessage("null");
            return;
        }
        sendMessage(message);
    }

    private void getProductByName(String[] data) {
        Server.showMessage("Get product by name request!");
        String message = dbHandler.getProductByName(data);
        if (message.equals("")) {
            System.out.println("Not found");
            sendMessage("null");
            return;
        }
        sendMessage(message);
    }

    private void getCategoryByName(String[] data) {
        Server.showMessage("Get category by name request!");
        String message = dbHandler.getCategoryByName(data);
        if (message.equals("")) {
            System.out.println("Not found");
            sendMessage("null");
            return;
        }
        sendMessage(message);
    }
    private void getProducts(String[] data) {
        Server.showMessage("Get products request!");
        String message = dbHandler.getProducts(data);
        if (message.equals("")) {
            System.out.println("Not found");
            sendMessage("null");
            return;
        }
        sendMessage(message);
    }
    private void getOrders() {
        Server.showMessage("Get orders request!");
        String message = dbHandler.getOrders();
        if (message.equals("")) {
            System.out.println("Not found");
            sendMessage("null");
            return;
        }
        sendMessage(message);
    }
    private void getAllProducts() {
        Server.showMessage("Get products request!");
        String message = dbHandler.getAllProducts();
        if (message.equals("")) {
            System.out.println("Not found");
            sendMessage("null");
            return;
        }
        sendMessage(message);
    }

    private void getAds(String[] data) {
        Server.showMessage("Get ads request!");
        String message = dbHandler.getAds(data);
        System.out.println(message);
        sendMessage(message);
    }

    private void getAllAds() {
        Server.showMessage("Get all ads request!");
        String message = dbHandler.getAllAds();
        System.out.println(message);
        sendMessage(message);
    }

    private void onAdsClick(String[] data) {
        Server.showMessage("Inc ads clicks!");
        dbHandler.onAdsClick(data);
        sendMessage("");
    }

    private void signin(String[] data) {
        Server.showMessage("Get signin request!");
        String message = dbHandler.signin(data);
        if (message.equals("")) {
            System.out.println("Not found");
            sendMessage("null");
            return;
        }
        System.out.println(message);
        sendMessage(message);
    }

    private void signup(String[] data) {
        Server.showMessage("Get signup request!");
        dbHandler.signup(data);
        sendMessage("");
    }


    private void quit()
    {
        Server.clientDisconnected(clientSocket.getPort());
        try {
            soos.writeObject("rm");
        }
        catch (IOException ex)
        {

        }
        t.stop();
    }

    private void sendMessage(String message)
    {
        try
        {
            soos.writeObject(message);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
