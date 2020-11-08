import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException 
    {
        // get the target server ip address/host address
        String targetServerHost = "";
        try { targetServerHost = args[0]; }
        catch (Exception e) { HaltClient("Missing host address"); }

        // get the target server port to connect on
        int targetServerPort = 0;
        try { targetServerPort = Integer.parseInt(args[1]); }
        catch (Exception e) { HaltClient("Missing/invalid port"); }

        // get username for client
        String userName = "";
        try { userName = args[2]; }
        catch (Exception e) { HaltClient("Missing username"); }

        // setup a socket connection to the server
        Socket socket = null;
        try
        {
            socket = new Socket(targetServerHost, targetServerPort);
            System.out.println("[ Connected to " + targetServerHost + ":" + targetServerPort + " ]\n");
        }
        catch (Exception e) 
        {
            HaltClient("Cannot connect to " + targetServerHost + ":" + targetServerPort);
        }

        // clear the console
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 

        // setup an input stream to the server using the socket
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        // send username to server
        printWriter.println(userName);
        printWriter.flush();

        // instantiate a client listener on a seperate thread to recieve server broadcasts 
        ClientListener clientListener = new ClientListener(socket);
        clientListener.start();

        // when the user types a message send it to the server
        while (true)
        {
            String message = scanner.nextLine();
            printWriter.println(message);
            printWriter.flush();
        }
    }

    private static void HaltClient(String _error)
    {
        System.out.println("Error: " + _error);
        System.out.println("\nCommand: Client <host address> <host port> <username>\n"); 
        System.exit(0);
    }
}