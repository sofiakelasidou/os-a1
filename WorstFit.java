public class WorstFit implements AllocationStrategy {

    @Override
    public boolean allocateBlock(int id, int bytes, Memory memory) {

        // Find the worst fitting (biggest) free block in memory.
        Block worstFitBlock = new Block(-1, 0, 0);
        for (Block freeBlock : memory.getFreeBlocks()) {
            if (freeBlock.getSize() >= bytes && freeBlock.getSize() > worstFitBlock.getSize()) {
                worstFitBlock = freeBlock;
            }
        }

        // If no free block found return false.
        if (worstFitBlock.getEndAddress() == 0) {
            return false;
        }

        // Otherwise allocate the block and retun true.
        memory.addBlock(new Block(id) , worstFitBlock.getStartAddress(), worstFitBlock.getStartAddress() + bytes - 1);
        return true;

    }

    @Override
    public String getStrategy() {
        return "Worst fit";
    }
    
}
