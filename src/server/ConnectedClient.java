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
                requestProcessing(message);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private String requestProcessing(String request)
    {
        String[] data = parseString(request);
        switch (command)
        {
            case "reg": registerAccount(data); break;
            case "login": loginAccount(data); break;
            case "addSouvenir": addSouvenir(data); break;
            case "addPresent": addPresent(data); break;
            case "addKanc": addKanc(data); break;
            case "updateUsers": tableUpdateUsers(); break;
            case "updateSouvenirs": tableUpdateSouvenirs(); break;
            case "updatePresents": tableUpdatePresents(); break;
            case "updateKanc": tableUpdateKanc(); break;
            case "updateSouvenirsOrder": tableUpdateSouvenirsOrder(); break;
            case "updatePresentsOrder": tableUpdatePresentsOrder(); break;
            case "updateKancOrder": tableUpdateKancOrder(); break;
            case "changeInfoUsers": changeInfoUsers(data); break;
            case "deleteInfoUsers": deleteInfoUsers(data); break;
            case "deleteSouvenirs": deleteInfoSouvenirs(data); break;
            case "deletePresents": deleteInfoPresents(data); break;
            case "deleteKanc": deleteInfoKanc(data); break;
            case "deleteSouvenirsOrder": deleteInfoSouvenirsOrder(data); break;
            case "deletePresentsOrder": deleteInfoPresentsOrder(data); break;
            case "deleteKancOrder": deleteInfoKancOrder(data); break;
            case "orderSouvenirs": orderSouvenirs(data); break;
            case "orderPresents": orderPresnts(data); break;
            case "orderKanc": orderKanc(data); break;
            case "refreshGraphicsSouvenirs": refreshGraphicsSouvenirs(); break;
            case "refreshGraphicsPresents": refreshGraphicsPresents(); break;
            case "refreshGraphicsKanc": refreshGraphicsKanc(); break;
            case "quit": quit(); break;
        }
        return request;
    }

    private void refreshGraphicsSouvenirs() {
        String message = dbHandler.refreshGraphicsSouvenirs();
        sendMessage(message);
    }

    private void refreshGraphicsPresents() {
        String message = dbHandler.refreshGraphicsPresents();
        sendMessage(message);
    }

    private void refreshGraphicsKanc() {
        String message = dbHandler.refreshGraphicsKanc();
        sendMessage(message);
    }

    private void orderSouvenirs(String[] data) {
        Server.showMessage("Souvenirs order request!");
        sendMessage(dbHandler.orderSouvenirs(data[0],data[1]));

    }

    private void orderPresnts(String[] data) {
        Server.showMessage("Presents order request!");
        sendMessage(dbHandler.orderPresents(data[0],data[1]));

    }

    private void orderKanc(String[] data) {
        Server.showMessage("Kanc order request!");
        sendMessage(dbHandler.orderKanc(data[0],data[1]));

    }

    private void deleteInfoSouvenirs(String[] data) {
        Server.showMessage("Souvenirs delete info request!");
        dbHandler.deleteInfoSouvenirs(data);
        sendMessage("SD");
    }

    private void deleteInfoPresents(String[] data) {
        Server.showMessage("Presents delete info request!");
        dbHandler.deleteInfoPresents(data);
        sendMessage("SD");
    }
    private void deleteInfoKanc(String[] data) {
        Server.showMessage("Kanctovari delete info request!");
        dbHandler.deleteInfoKanc(data);
        sendMessage("SD");
    }

    private void deleteInfoSouvenirsOrder(String[] data) {
        Server.showMessage("Souvenirs order delete info request!");
        dbHandler.deleteInfoSouvenirsOrder(data);
        sendMessage("SD");
    }

    private void deleteInfoPresentsOrder(String[] data) {
        Server.showMessage("Presents order delete info request!");
        dbHandler.deleteInfoPresentsOrder(data);
        sendMessage("SD");
    }

    private void deleteInfoKancOrder(String[] data) {
        Server.showMessage("Kanctovari order delete info request!");
        dbHandler.deleteInfoKancOrder(data);
        sendMessage("SD");
    }

    private void deleteInfoUsers(String[] data) {
        Server.showMessage("Users delete info request!");
        dbHandler.deleteInfoUsers(data);
        sendMessage("SD");
    }

    private void changeInfoUsers(String[] data) {
        Server.showMessage("Users change info request!");
        int passwordIndex = data.length - 1;
        data[passwordIndex] = Validator.hashing(data[passwordIndex]);
        dbHandler.changeInfoUsers(data);
        sendMessage("SC");
    }

    private String[] parseString(String str)
    {
        String[] strMas = str.split(Pattern.quote("|"));
        command = strMas[0];
        return Arrays.copyOfRange(strMas, 1, strMas.length);
    }

    private void registerAccount(String[] data)
    {
        Server.showMessage("Registration request!");
        int passwordIndex = data.length - 2;
        data[passwordIndex] = Validator.hashing(data[passwordIndex]);
        dbHandler.signUpUser(data);
        sendMessage("SR");
    }
    private void addSouvenir(String[] data) {
        Server.showMessage("Adding souvenir request!");
        dbHandler.addSouvenir(data);
        sendMessage("SR"); //???????
    }
    private void addPresent(String[] data) {
        Server.showMessage("Adding present request!");
        dbHandler.addPresent(data);
        sendMessage("SR");
    }

    private void addKanc(String[] data) {
        Server.showMessage("Adding kanctovar request!");
        dbHandler.addKanc(data);
        sendMessage("SR");
    }

    private void loginAccount(String[] data)
    {
        Server.showMessage("Login request!");
        int passwordIndex = data.length - 2;
        data[passwordIndex] = Validator.hashing(data[passwordIndex]);
        String message = dbHandler.loginUser(data[0],data[1],data[2]);
        sendMessage(message);
    }

    private void tableUpdateUsers()
    {
        Server.showMessage("Users table update request!");
        String message = dbHandler.tableUpdateUsers();
        sendMessage(message);
    }

    private void tableUpdateSouvenirs()
    {
        Server.showMessage("Souvenirs table update request!");
        String message = dbHandler.tableUpdateSouvenirs();
        sendMessage(message);
    }
    private void tableUpdatePresents()
    {
        Server.showMessage("Presents table update request!");
        String message = dbHandler.tableUpdatePresents();
        sendMessage(message);
    }

    private void tableUpdateKanc()
    {
        Server.showMessage("Kanctovari table update request!");
        String message = dbHandler.tableUpdateKanc();
        sendMessage(message);
    }

    private void tableUpdateSouvenirsOrder()
    {
        Server.showMessage("Souvenirs order table update request!");
        String message = dbHandler.tableUpdateSouvenirsOrder();
        sendMessage(message);
    }

    private void tableUpdatePresentsOrder()
    {
        Server.showMessage("Presents order table update request!");
        String message = dbHandler.tableUpdatePresentsOrder();
        sendMessage(message);
    }
    private void tableUpdateKancOrder()
    {
        Server.showMessage("Kanctovari order table update request!");
        String message = dbHandler.tableUpdateKancOrder();
        sendMessage(message);
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
