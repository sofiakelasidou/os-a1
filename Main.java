import java.util.ArrayList;

class Main {

    public static void main (String [] args) {

        // I/O file name.
        String fileName = "3-madoutputter";

        // Read file, set memory capacity, and get an array with the instructions.
        IOFile ioFile = new IOFile(fileName);
        Memory firstFit = new Memory(new FirstFit());
        Memory bestFit = new Memory(new BestFit());
        Memory worstFit = new Memory(new WorstFit());
        ArrayList<String> instructions = ioFile.readInputFile(firstFit, bestFit, worstFit);

        // Execute commands for first fit, best fit, and  worst fit
        ArrayList<CommandExecutor> commandExecutors = new ArrayList<>();
        commandExecutors.add(new CommandExecutor(firstFit));
        commandExecutors.add(new CommandExecutor(bestFit));
        commandExecutors.add(new CommandExecutor(worstFit));

        for (CommandExecutor commandExecutor : commandExecutors) {
            int instructionNumber = 1;
            for (String line : instructions) {
                String[] instruction = line.split(";");
                String command = instruction[0];

                switch (command) {
                    case "A":
                        commandExecutor.allocate(instructionNumber, instruction[1], instruction[2]);
                        break;
                    case "D":
                        commandExecutor.deallocate(instructionNumber, instruction[1]);
                        break;
                    case "C":
                        commandExecutor.compact();
                        break;
                    case "O":
                        commandExecutor.intermediateOutput(ioFile.getOutputForStrategy(commandExecutor));
                }
                instructionNumber++;
            }
        }

        // Write results into output files
        ioFile.writeOutputFile(commandExecutors);
        
    }

}
