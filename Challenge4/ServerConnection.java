import java.net.*;
import java.io.*;

public class ServerConnection extends Thread {
    private Socket clientSocket;
    private String joinTime;
    private User user;

    public ServerConnection(Socket _clientSocket, String _joingtime, User _newUser) {
        clientSocket = _clientSocket;
        joinTime = _joingtime;
        user = _newUser;
    }

    public String GetJoinTime() { return joinTime; }
    public User GetUser() { return user; }

    /**
     * Run the 'Listen' method on this thread.
     */
    public void run() {
        try 
        {
            Listen();
        } 
        catch (IOException e) 
        {
            try
            {
                Server.DisconnectClient(this);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    /**
     * While the client socket is connected, wait for a message sent
     * by the user and broadcast it to other clients on the server.
     */
    private void Listen() throws IOException
    {
        while (clientSocket.isConnected())
        {
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String message = reader.readLine();

            Server.BroadcastMessage(user, message);
        }
    }

    /**
     * Used by the server to send broadcasted messages 
     * to user/client of the server connection.
     * 
     * The client listener thread with recieve the message.
     */
    public void SendMessage(String _message) throws IOException
    {
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
        printWriter.println(_message);
        printWriter.flush();
    }
}
