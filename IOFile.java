import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

public class IOFile {

    private String filePath;

    /**
     * Constructor.
     * @param fileName the names of the I/O files.
     */
    public IOFile(String fileName) {
        filePath = System.getProperty("user.dir") + "/" + fileName;
    }

    /**
     * Read the input file, set capacity to memory objects, and return array with instructions.
     * @param memory1 memory used for executing specific strategy.
     * @param memory2 memory used for executing specific strategy.
     * @param memory3 memory used for executing specific strategy.
     * @return the instructions to execute for each strategy.
     */
    public ArrayList<String> readInputFile(Memory memory1, Memory memory2, Memory memory3) {
        ArrayList<String> instructions = new ArrayList<>();
        try {
            File file = new File(filePath + ".in");
            Scanner scan = new Scanner(file);
            int capacity = Integer.valueOf(scan.nextLine());
            memory1.setCapacity(capacity);
            memory2.setCapacity(capacity);
            memory3.setCapacity(capacity);
            while (scan.hasNext()) {
                instructions.add(scan.nextLine());
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instructions;
    }

    /**
     * Return the intermediate or final output for a specific strategy.
     * @param commandExecutor for specific strategy.
     * @return a string with the (intermediate or final) output.
     */
    public String getOutputForStrategy(CommandExecutor commandExecutor) {
        Memory memory = commandExecutor.getMemory();
        String errors = commandExecutor.getErrors();
        String strategy = commandExecutor.getStrategy();

        String output = strategy + "\n";

        output += "Allocated blocks\n";
        if (memory.getBlocks().isEmpty()) {
            output += "None" + "\n";
        }
        for (Block block : memory.getBlocks()) {
            output += block.getId() + ";" + block.getStartAddress() + ";" + block.getEndAddress() + "\n";
        }

        output += "Free blocks\n";
        if (memory.getFreeBlocks().isEmpty()) {
            output += "None" + "\n";
        }
        for (Block block : memory.getFreeBlocks()) {
            output += block.getStartAddress() + ";" + block.getEndAddress() + "\n";
        }

        output += "Fragmentation\n";
        BigDecimal fragmentation = BigDecimal.valueOf(memory.getFragmentation());
        fragmentation = fragmentation.setScale(6, RoundingMode.HALF_UP);
        output += fragmentation.toString() + "\n";

        if (errors.isEmpty()) {
            output += "Errors\n" + "None" + "\n" + "\n";
        } else {
            output += "Errors\n" + errors + "\n";
        }
    
        return output;
    }

    /**
     * Write to the intermediate and final output files.
     * @param commandExecutors the executors for each allocation strategy.
     */
    public void writeOutputFile(ArrayList<CommandExecutor> commandExecutors) {
        for (CommandExecutor commandExecutor : commandExecutors) {

            // Write in the intermediate output files.
            int intermediateNumber = 1;
            for (String intermediateOutput : commandExecutor.getIntermediateOutputs()) {
                try {
                    FileWriter file = new FileWriter(filePath + ".out" + Integer.toString(intermediateNumber), true);
                    PrintWriter printer = new PrintWriter(new BufferedWriter(file));
                    printer.print(intermediateOutput);
                    printer.close();
                } catch (IOException e) {
                    e. printStackTrace (); 
                }
                intermediateNumber++;
            }

            // Write in the final output files.
            try {
                FileWriter file = new FileWriter(filePath + ".out", true);
                PrintWriter printer = new PrintWriter(new BufferedWriter(file));
                printer.print(getOutputForStrategy(commandExecutor));
                printer.close();
            } catch (IOException e) {
                e. printStackTrace (); 
            }

        }
    }

}
