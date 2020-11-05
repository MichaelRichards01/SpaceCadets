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
        else if ("set".equals(command))
        {
            if (instructionComponents.length == 3)
            {
                String targetVariable = instructionComponents[1];
                String setVariable = instructionComponents[2];

                ExecuteSet(targetVariable, setVariable);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("setval".equals(command))
        {
            if (instructionComponents.length == 3)
            {
                String targetVariable = instructionComponents[1];
                String setValue = instructionComponents[2];

                ExecuteSetValue(targetVariable, setValue);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("add".equals(command))
        {
            if (instructionComponents.length == 3)
            {
                String targetVariable = instructionComponents[1];
                String additionVariable = instructionComponents[2];

                ExecuteAddition(targetVariable, additionVariable);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("addval".equals(command))
        {
            if (instructionComponents.length == 3)
            {
                String targetVariable = instructionComponents[1];
                String additionValue = instructionComponents[2];

                ExecuteAddValue(targetVariable, additionValue);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("sub".equals(command))
        {
            if (instructionComponents.length == 3)
            {
                String targetVariable = instructionComponents[1];
                String subtractionVariable = instructionComponents[2];

                ExecuteSubtraction(targetVariable, subtractionVariable);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("subval".equals(command))
        {
            if (instructionComponents.length == 3)
            {
                String targetVariable = instructionComponents[1];
                String subtractionValue = instructionComponents[2];

                ExecuteSubValue(targetVariable, subtractionValue);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("mlt".equals(command))
        {
            if (instructionComponents.length == 3)
            {
                String targetVariable = instructionComponents[1];
                String multiplicationVariable = instructionComponents[2];

                ExecuteMultiplication(targetVariable, multiplicationVariable);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("mltval".equals(command))
        {
            if (instructionComponents.length == 3)
            {
                String targetVariable = instructionComponents[1];
                String multiplicationValue = instructionComponents[2];

                ExecuteMltValue(targetVariable, multiplicationValue);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("div".equals(command))
        {
            if (instructionComponents.length == 3)
            {
                String targetVariable = instructionComponents[1];
                String divisionVariable = instructionComponents[2];

                ExecuteDivision(targetVariable, divisionVariable);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("divval".equals(command))
        {
            if (instructionComponents.length == 3)
            {
                String targetVariable = instructionComponents[1];
                String divisionValue = instructionComponents[2];

                ExecuteDivValue(targetVariable, divisionValue);
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
        else if ("branch".equals(command))
        {
            if (instructionComponents.length == 2)
            {
                String targetLabel = instructionComponents[1];

                ExecuteBranch(targetLabel);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("bequal".equals(command))
        {
            if (instructionComponents.length == 4)
            {
                String targetLabel = instructionComponents[1];
                String variable1 = instructionComponents[2];
                String variable2 = instructionComponents[3];

                ExecuteBranchEqual(targetLabel, variable1, variable2);
            }
            else HaltProgram("Incorrect instruction format");
        }
        else if ("bless".equals(command))
        {
            if (instructionComponents.length == 4)
            {
                String targetLabel = instructionComponents[1];
                String variable1 = instructionComponents[2];
                String variable2 = instructionComponents[3];

                ExecuteBranchLessThan(targetLabel, variable1, variable2);
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
     * Executes an assignment of variable.
     */
    private void ExecuteSet(String _targetVariable, String _setVariable)
    {
        boolean variableExists = programVariables.containsKey(_targetVariable)
                              && programVariables.containsKey(_setVariable);

        if (variableExists)
        {
            int newValue = programVariables.get(_setVariable);
            programVariables.replace(_targetVariable, newValue);
        }
        else HaltProgram(_targetVariable + " or " + _setVariable + " was not declared");
    }

    /**
     * Executes an assignment of value.
     */
    private void ExecuteSetValue(String _variable, String _value)
    {
        boolean variableExists = programVariables.containsKey(_variable);

        if (variableExists)
        {
            try
            {
                Integer value = Integer.parseInt(_value);
                programVariables.replace(_variable, value);
            }
            catch (Exception e)
            {
                HaltProgram("The value '" + _value + "' can not be assigned to a variable");
            }
        }
        else HaltProgram(_variable + " was not declared");
    }

    /**
     * Execute an addition instruction on the passed varaibles.
     */
    private void ExecuteAddition(String _targetVariable, String _additionVariable)
    {
        boolean variableExists = programVariables.containsKey(_targetVariable)
                              && programVariables.containsKey(_additionVariable);

        if (variableExists)
        {
            int currentValue1 = programVariables.get(_targetVariable);
            int currentValue2 = programVariables.get(_additionVariable);
            int newValue = currentValue1 + currentValue2;
            programVariables.replace(_targetVariable, newValue);
        }
        else HaltProgram(_targetVariable + " or " + _additionVariable + " was not declared");
    }

    /**
     * Add a value to a program variable.
     */
    private void ExecuteAddValue(String _variable, String _value)
    {
        boolean variableExists = programVariables.containsKey(_variable);

        if (variableExists)
        {
            try
            {
                Integer additionValue = Integer.parseInt(_value);
                Integer currentValue = programVariables.get(_variable);
                Integer newValue = currentValue + additionValue;
                programVariables.replace(_variable, newValue);
            }
            catch (Exception e)
            {
                HaltProgram("The value '" + _value + "' can not be added to a variable");
            }
        }
        else HaltProgram(_variable + " was not declared");
    }

    /**
     * Execute an subtraction instruction on the passed varaibles.
     */
    private void ExecuteSubtraction(String _targetVariable, String _subtractionVariable)
    {
        boolean variableExists = programVariables.containsKey(_targetVariable)
                              && programVariables.containsKey(_subtractionVariable);

        if (variableExists)
        {
            int currentValue1 = programVariables.get(_targetVariable);
            int currentValue2 = programVariables.get(_subtractionVariable);
            int newValue = currentValue1 - currentValue2;
            programVariables.replace(_targetVariable, newValue);
        }
        else HaltProgram(_targetVariable + " or " + _subtractionVariable + " was not declared");
    }

    /**
     * Sub a value to a program variable.
     */
    private void ExecuteSubValue(String _variable, String _value)
    {
        boolean variableExists = programVariables.containsKey(_variable);

        if (variableExists)
        {
            try
            {
                Integer subtractionValue = Integer.parseInt(_value);
                Integer currentValue = programVariables.get(_variable);
                Integer newValue = currentValue - subtractionValue;
                programVariables.replace(_variable, newValue);
            }
            catch (Exception e)
            {
                HaltProgram("The value '" + _value + "' can not be subtracted from a variable");
            }
        }
        else HaltProgram(_variable + " was not declared");
    }

    /**
     * Execute an multiplication instruction on the passed varaibles.
     */
    private void ExecuteMultiplication(String _targetVariable, String _multiplicationVariable)
    {
        boolean variableExists = programVariables.containsKey(_targetVariable)
                              && programVariables.containsKey(_multiplicationVariable);

        if (variableExists)
        {
            int currentValue1 = programVariables.get(_targetVariable);
            int currentValue2 = programVariables.get(_multiplicationVariable);
            int newValue = currentValue1 * currentValue2;

            programVariables.replace(_targetVariable, newValue);
        }
        else HaltProgram(_targetVariable + " or " + _multiplicationVariable + " was not declared");
    }

    /**
     * Multiply a value to a program variable.
     */
    private void ExecuteMltValue(String _variable, String _value)
    {
        boolean variableExists = programVariables.containsKey(_variable);

        if (variableExists)
        {
            try
            {
                Integer multiplicationValue = Integer.parseInt(_value);
                Integer currentValue = programVariables.get(_variable);
                Integer newValue = currentValue * multiplicationValue;
                programVariables.replace(_variable, newValue);
            }
            catch (Exception e)
            {
                HaltProgram("The value '" + _value + "' can not multiply this variable");
            }
        }
        else HaltProgram(_variable + " was not declared");
    }

    /**
     * Execute an division instruction on the passed varaibles.
     */
    private void ExecuteDivision(String _targetVariable, String _divisionVariable)
    {
        boolean variableExists = programVariables.containsKey(_targetVariable)
                              && programVariables.containsKey(_divisionVariable);

        if (variableExists)
        {
            int currentValue1 = programVariables.get(_targetVariable);
            int currentValue2 = programVariables.get(_divisionVariable);

            if (currentValue2 == 0) HaltProgram("Cannot divide by 0");

            int newValue = currentValue1 / currentValue2;

            programVariables.replace(_targetVariable, newValue);
        }
        else HaltProgram(_targetVariable + " or " + _divisionVariable + " was not declared");
    }

    /**
     * Divide a value to a program variable.
     */
    private void ExecuteDivValue(String _variable, String _value)
    {
        boolean variableExists = programVariables.containsKey(_variable);

        if (variableExists)
        {
            try
            {
                Integer divisionValue = Integer.parseInt(_value);
                if (divisionValue == 0) HaltProgram("Cannot divide by 0");
                Integer currentValue = programVariables.get(_variable);
                Integer newValue = currentValue / divisionValue;
                programVariables.replace(_variable, newValue);
            }
            catch (Exception e)
            {
                HaltProgram("The value '" + _value + "' can not multiply this variable");
            }
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

    /**
     * Branch to target label.
     */
    private void ExecuteBranch(String _targetLabel)
    {
        boolean foundLabel = false;

        for (int i = 0; i < sourceCode.length; i++)
        {
            if (sourceCode[i].equals(_targetLabel))
            {
                programCounter = i;
                foundLabel = true;
            }
        }

        if (!foundLabel) HaltProgram(_targetLabel + " label doesn't exist");
    }

    /**
     * Branch to target label if variables are equal.
     */
    private void ExecuteBranchEqual(String _targetLabel, String _variable1, String _variable2)
    {
        boolean variableExists = programVariables.containsKey(_variable1)
                              && programVariables.containsKey(_variable2);

        if(variableExists)
        {
            int value1 = programVariables.get(_variable1);
            int value2 = programVariables.get(_variable2);

            if (value1 == value2)
            {
                boolean foundLabel = false;

                for (int i = 0; i < sourceCode.length; i++)
                {
                    if (sourceCode[i].equals(_targetLabel))
                    {
                        programCounter = i;
                        foundLabel = true;
                    }
                }

                if (!foundLabel) HaltProgram(_targetLabel + " label doesn't exist");
            }
        }
        else HaltProgram(_variable1 + " or " + _variable2 + " was not declared");
    }

    /**
     * Branch to target label if the first variable is less than the second.
     */
    private void ExecuteBranchLessThan(String _targetLabel, String _variable1, String _variable2)
    {
        boolean variableExists = programVariables.containsKey(_variable1)
                              && programVariables.containsKey(_variable2);

        if(variableExists)
        {
            int value1 = programVariables.get(_variable1);
            int value2 = programVariables.get(_variable2);

            if (value1 < value2)
            {
                boolean foundLabel = false;

                for (int i = 0; i < sourceCode.length; i++)
                {
                    if (sourceCode[i].equals(_targetLabel))
                    {
                        programCounter = i;
                        foundLabel = true;
                    }
                }

                if (!foundLabel) HaltProgram(_targetLabel + " label doesn't exist");
            }
        }
        else HaltProgram(_variable1 + " or " + _variable2 + " was not declared");
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
