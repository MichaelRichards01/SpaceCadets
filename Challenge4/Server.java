import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Server 
{
    private static ServerSocket serverSocket;

    // store all server connections to clients
    private static ArrayList<ServerConnection> serverConnections;
    private static int lastUserID;

    // store all user messages
    private static ArrayList<String> messages;

    // store all event messages
    private static ArrayList<String> events;

    public static void main(String[] args) throws IOException 
    {
        serverConnections = new ArrayList<ServerConnection>();
        messages = new ArrayList<String>();
        events = new ArrayList<String>();

        int openPort = 1;
        try { openPort = Integer.parseInt(args[0]); }
        catch (Exception e) {HaltServer("Missing/invalid port");}

        if (openPort > 1000 || openPort < 1) HaltServer("Port outside range [1-1000]");

        try { SetupServer(openPort); }
        catch (Exception e) { HaltServer("Was unable setup server on port " + openPort); }

        ConnectClients();
    }

    /**
     * Setup a server socket on the assigned port
     * and then display server information.
     */
    private static void SetupServer(int _openPort) throws IOException
    {
        serverSocket = new ServerSocket(_openPort);

        Display();
    }

    /**
     * Wait for a new client to connect to the socket,
     * then create a new user and new server connection.
     * 
     * Assign the new user to the new server connection.
     * 
     * This server connection will run on a seperate thread and 
     * will listen for message sent be the user of this client.
     * 
     * Updates the server information output.
     */
    private static void ConnectClients() throws IOException
    {
        while (true)
        {
            Socket clientSocket = serverSocket.accept();

            // get join time of client
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();   
            String joinTime = dtf.format(now);

            // assign user id
            int newUserID = lastUserID + 1;

            // recieve username from client
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String newUserName = reader.readLine();

            // instantiate new user
            User newUser = new User(newUserID, newUserName);
            lastUserID = newUserID;

            events.add("Client " + newUserID + " has connected.");

            ServerConnection newServerConnection = new ServerConnection(clientSocket, joinTime, newUser);
            newServerConnection.start();

            serverConnections.add(newServerConnection);

            Display();
        }
    }

    /**
     * Remove client's server connection from the list 
     * of connections if client socket dies.
     */
    public static void DisconnectClient(ServerConnection _serverConnection) throws IOException
    {
        serverConnections.remove(_serverConnection);
        events.add("Client " + _serverConnection.GetUser().GetID() + " has disconnected.");
        Display();
    }

    /**
     * Output information about the server to the console,
     * including server host address and port as well as
     * connected clients, messages and events.
     */
    public static void Display() throws IOException
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 

        System.out.println("[ SERVER : " + serverSocket.getInetAddress()
                         + " : " + serverSocket.getLocalPort() + " ]");

        DisplayClients();;
        DisplayMessages();
        DisplayEvents();
    }

    /**
     * Display the user ID, user name and join time
     * for each connected client on the server.
     */
    private static void DisplayClients()
    {
        System.out.println("\nClients\n-------\n");

        for (ServerConnection serverConnection : serverConnections)
        {
            System.out.println("[ " + serverConnection.GetUser().GetID() + " : "
                             + serverConnection.GetUser().GetName() + " : " 
                             + serverConnection.GetJoinTime() + " ]");  
        }
    }

    /**
     * Display all messages from user that have been
     * broadcatsed on the server.
     */
    private static void DisplayMessages()
    {
        System.out.println(("\nMessages\n--------\n"));

        for (String message : messages) System.out.println(message);
    }

    /**
     * Display all message of events that have happened on the server.
     */
    private static void DisplayEvents()
    {
        System.out.println("\nEvents\n------\n");

        for (String event : events) System.out.println(event);
    }

    /**
     * Take the passed message from the user and broadcast it each 
     * client connected to the server apart from the client sending it.
     * 
     * Also, add this message to list of messages on the server.
     * 
     * Updates the server information output.
     */
    public static void BroadcastMessage(User _user, String _message) throws IOException
    {
        String message = "[ " + _user.GetID() + " : " + _user.GetName() + " ] " + _message;

        for (ServerConnection serverConnection : serverConnections)
        {
            if (serverConnection.GetUser().GetID() != _user.GetID())
            {
                serverConnection.SendMessage(message);
            }
        }

        messages.add(message);
        Display();
    }

    private static void HaltServer(String _error)
    {
        System.out.println("Error: " + _error);
        System.out.println("\nCommand: Server <host port>\n"); 
        System.exit(0);
    }
}
