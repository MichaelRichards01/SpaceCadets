import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BareBonesInterpreter {
    public static void main(String[] args) throws FileNotFoundException {
        File programFile = GetProgramFile();

        InterpretProgram(programFile);
    }

    /**
     * Asks the user to enter the name of the source code file.
     * 
     * This file will be returned if it exists within the current directory.
     */
    private static File GetProgramFile() {
        Scanner scanner = new Scanner(System.in);

        File programFile = new File("");
        boolean foundFile = false;

        while (!foundFile) {
            System.out.print("Program: ");
            String programFileName = scanner.nextLine();

            programFile = new File(programFileName);

            if (programFile.exists())
                foundFile = true;
            else
                System.out.println("Error - Could not find that program file!\n");
        }

        return programFile;
    }

    /**
     * Scans the source code of the program file.
     * 
     * Instantiates a program object contains the 
     * source code in a string array (line by line).
     * 
     * Call fors the program to be interpreted.
     */
    private static void InterpretProgram(File _programFile) throws FileNotFoundException
    {
        Scanner scanner = new Scanner(_programFile);
        List<String> temp = new ArrayList<>();

        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            temp.add(line);
        }

        String[] sourceCode = new String[temp.size()];
        sourceCode = temp.toArray(sourceCode);

        Program program = new Program(sourceCode);
        program.InterpretProgram();
    }
}