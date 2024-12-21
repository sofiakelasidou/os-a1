import java.util.ArrayList;

public class CommandExecutor {

    private Memory memory;
    private ArrayList<Integer> nonAlocatedIds;
    private ArrayList<String> intermediateOutputs;
    private String errors;

    /**
     * Constructor.
     * @param memory the main memory object.
     */
    public CommandExecutor(Memory memory) {
        this.memory = memory;
        nonAlocatedIds = new ArrayList<>();
        intermediateOutputs = new ArrayList<>();
        errors = "";
    }

    /**
     * Execute the "allocate" instruction.
     * @param instructionNumber the instruction's line number.
     * @param param1 first parameter of instruction.
     * @param param2 second parameter of instruction.
     */
    public void allocate(int instructionNumber, String param1, String param2) {
        int id = Integer.valueOf(param1);
        int bytes = Integer.valueOf(param2);

        if (!memory.allocateBlock(id, bytes)) {
            errors += "A;" + Integer.toString(instructionNumber) + ";";
            errors += Integer.toString(memory.getMaxAvailableBlock()) + "\n";
            nonAlocatedIds.add(id);
        }
    }

    /**
     * Execute the "deallocate" instruction.
     * @param instructionNumber the instruction's line number.
     * @param param1 first parameter of instruction.
     */
    public void deallocate(int instructionNumber, String param1) {
        int id = Integer.valueOf(param1);

        if (!memory.deallocateBlock(id)) {
            String errorReason;
            if (nonAlocatedIds.contains(id)) {
                errorReason = "1";
            } else {
                errorReason = "0";
            }
            errors += "D;" + Integer.toString(instructionNumber) + ";" + errorReason + "\n";
        }
    }

    /**
     * Execute the "compact" instruction.
     */
    public void compact() {
        memory.compact();
    }

    /**
     * Add intermediate output for strategy.
     * @param intermediateOutput the output.
     */
    public void intermediateOutput(String intermediateOutput) {
        intermediateOutputs.add(intermediateOutput);
    }

    /**
     * Return the intermediate outputs for the strategy.
     * @return an ArrayList of the outputs.
     */
    public ArrayList<String> getIntermediateOutputs() {
        ArrayList<String> copyIntermediateOutputs = new ArrayList<>();
        for (String outputs : intermediateOutputs) {
            copyIntermediateOutputs.add(outputs);
        }
        return copyIntermediateOutputs;
    }

    /**
     * Return the errors from the execution. 
     * @return the errors.
     */
    public String getErrors() {
        return errors;
    }

    /**
     * Return the name of the strategy.
     * @return the strategy.
     */
    public String getStrategy() {
        return memory.getStrategy();
    }

    /**
     * Return the main memory object used by the executor.
     * @return the memory object.
     */
    public Memory getMemory() {
        return memory;
    }
}
