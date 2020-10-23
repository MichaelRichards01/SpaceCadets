import java.util.HashMap;

public class Program
{
    private String[] sourceCode; // bb program source code
    private HashMap<String, Integer> programVariables; // stores the bb program variables
    private int programCounter; // current position of the program

    public Program(String[] _sourceCode)
    {
        sourceCode = _sourceCode;
        programVariables = new HashMap<String, Integer>();
        programCounter = 0;
    }


    /**
     * Loops through each instruction in the source code.
     */
    public void InterpretProgram()
    {
        System.out.println("\n[START]");

        while (programCounter < sourceCode.length)
        {
            InterpretInstruction(programCounter);

            programCounter++;
        }
        System.out.println("[END]\n");
        System.out.println("Program executed correctly!");
    }

    /**
     * Seperates instructions into their components, such as command and operands.
     * 
     * Checks for syntax/format errors.
     * 
     * If the word command is recognised and the syntax is correct, execute the instruction.
     */
    private void InterpretInstruction(int instructionIndex)
    {
        String instruction = sourceCode[instructionIndex];

        System.out.println("[" + programCounter + "]\t" + instruction);

        String lastInstructionCharacter = instruction.substring(instruction.length() - 1);
        if (!";".equals(lastInstructionCharacter)) HaltProgram("Missing ;");

        instruction = instruction.replaceAll(";$", "");
        String[] instructionComponents = instruction.split("\\s+");
        String command = instructionComponents[0];
        command = command.replace("-", "");

        if ("clear".equals(command))
        {
            if (instructionComponents.length == 2) 
            {
                String targetVariable = instructionComponents[1];

                ExecuteClear(targetVariable);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("incr".equals(command))
        {
            if (instructionComponents.length == 2) 
            {
                String targetVariable = instructionComponents[1];

                ExecuteIncrease(targetVariable);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("decr".equals(command))
        {
            if (instructionComponents.length == 2) 
            {
                String targetVariable = instructionComponents[1];

                ExecuteDecrease(targetVariable);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("while".equals(command))
        {
            if (instructionComponents.length == 5)
            {
                if ("not".equals(instructionComponents[2]) 
                    && "0".equals(instructionComponents[3]) 
                    && "do".equals(instructionComponents[4]))
                {
                    String targetVariable = instructionComponents[1];

                    ExecuteWhileLoop(targetVariable);
                }
                else HaltProgram("Incorrect instruction format");
            }
            else HaltProgram("Incorrect instruction format");
        }
        else HaltProgram("Unknown command " + command);

        DisplayVariables();
    }

//region Commands
    /**
     * Execute a clear instruction on the passed variable.
     */
    private void ExecuteClear(String _variable)
    {
        boolean variableExists = programVariables.containsKey(_variable);

        if (variableExists) programVariables.replace(_variable, 0);
        else programVariables.put(_variable, 0);
    }

    /**
     * Execute an increase instruction on the passed variable.
     */
    private void ExecuteIncrease(String _variable)
    {
        boolean variableExists = programVariables.containsKey(_variable);

        if (variableExists) 
        {
            int currentValue = programVariables.get(_variable);
            int newValue = currentValue + 1;
            programVariables.replace(_variable, newValue);
        }
        else HaltProgram(_variable + " was not declared");
    }

    /**
     * Execute a decrease instruction on the passed variable.
     */
    private void ExecuteDecrease(String _variable)
    {
        boolean variableExists = programVariables.containsKey(_variable);

        if (variableExists) 
        {
            int currentValue = programVariables.get(_variable);
            int newValue = currentValue - 1;
            programVariables.replace(_variable, newValue);
        }
        else HaltProgram(_variable + " was not declared");
    }

    /**
     * Record the start index and indentation of the while loop.
     * 
     * Loop through each instruction and execute it until the passed variable is 0.
     * 
     * Set the program counter to the start index if a "end;" command of the
     * correct indentation is found.
     */
    private void ExecuteWhileLoop(String _variable)
    {
        int startIndex = programCounter;
        int loopIndentation = CountIndentation(startIndex) + 1;
        int value = programVariables.get(_variable);

        while (value != 0)
        {
            programCounter = startIndex + 1;

            boolean gotoStart = false;
            while(!gotoStart)
            {
                int instructionIndentation = CountIndentation(programCounter);

                if (instructionIndentation == loopIndentation) 
                {
                    InterpretInstruction(programCounter);
                    programCounter++;
                }
                else if (EndOfLoop(programCounter)) gotoStart = true;
                else HaltProgram("Incorrect indentation or missing end command");
            }

            value = programVariables.get(_variable);
        }

        System.out.println("[" + programCounter + "]\t" + sourceCode[programCounter]);
    }

    /**
     * Counts the indentation of an instruction.
     */
    private int CountIndentation(int instructionIndex)
    {
        String instruction = sourceCode[instructionIndex];
        int indentation = 0;
        for (char character : instruction.toCharArray()) 
        {
            if ('-' == character) indentation++;
            else break;
        }

        return indentation;
    }

    /**
     * Returns true if the end of the while loop has been reached.
     */
    private boolean EndOfLoop(int _index)
    {
        String instruction = sourceCode[_index];

        if (instruction.contains("end;")) return true;
        return false;
    }
//endregion

    /**
     * Halt the program on an error.
     */
    private void HaltProgram(String _errorMessage)
    {
        System.out.println("\nProgram halted at instruction " + programCounter);
        System.out.println("Error - " + _errorMessage);

        System.exit(0);
    }

    /**
     * Outputs the value of each program variable.
     */
    private void DisplayVariables()
    {
        System.out.print("\t\t\t[Vars]\t");
        for (HashMap.Entry<String, Integer> variable : programVariables.entrySet()) 
        {
            System.out.print(variable.getKey() + ":" + variable.getValue() + "\t");
        }
        System.out.println("");
    }
}
