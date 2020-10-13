import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class FetchName {
    private static final String BASE_URL = "https://www.ecs.soton.ac.uk/people/";

    public static void main(String[] args) throws IOException 
    {
        while (true)
        {
            String emailID = GetEmailID(); // ask user to enter email id
            String url = GetURL(emailID); // construct url to target web page
            String name = FetchName(url); // scan target web page for person's name

            OutputName(name); // output name to user
        }
    }

    private static String GetEmailID()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Email ID: ");
        String emailID = scanner.nextLine(); // collect user input
        emailID.replaceAll("[-+.^:,;]","");

        return emailID;
    }

    private static String GetURL(String _emailID) { return BASE_URL + _emailID; }

    private static String FetchName(String _url) throws IOException
    {
        URL urlObject = new URL(_url); // create url object
        Scanner scanner = new Scanner(urlObject.openStream()); // open input stream to web page

        boolean nameIsFound = false;
        String name = "";

        while (scanner.hasNextLine() && !nameIsFound) // cycle through each line of the web page's source code untill name if found
        {
            String currentLine = scanner.nextLine(); // scan next line of the web page's source code
            int startIndex = currentLine.indexOf("property=\"name\"");
            int endIndex = currentLine.indexOf("<em property");

            if (startIndex != -1) // if the start index is not -1 then the name property has been found
            {
                startIndex += 16;
                name = currentLine.substring(startIndex, endIndex); // extract the person's name
                nameIsFound = true;
            }
        }

        return name;
    }

    private static void OutputName(String _name)
    {
        System.out.print("--- ");

        if (_name == "") { System.out.println("n/a"); }
        else { System.out.println(_name); } // output person's name

        System.out.println();
    }
}