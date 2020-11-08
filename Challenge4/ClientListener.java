import java.io.*;
import java.net.Socket;

public class ClientListener extends Thread 
{
    private Socket socket;

    public ClientListener(Socket _socket) 
    {
        socket = _socket;
    }

    /**
     * Run the 'Listen' method on this thread.
     */
    public void run() 
    {
        try {
            Listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wait for a broadcasted message from the server, 
     * recieve the message and then print it to the console.
     */
    private void Listen() throws IOException
    {
        while (true)
        {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println(reader.readLine());
        }
    }
}