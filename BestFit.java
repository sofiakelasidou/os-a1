public class BestFit implements AllocationStrategy {

    @Override
    public boolean allocateBlock(int id, int bytes, Memory memory) {

        // Find the best fitting (smallest) free block in memory.
        Block bestFitBlock = new Block(-1, 0, memory.getCapacity() - 1);
        for (Block freeBlock : memory.getFreeBlocks()) {
            if (freeBlock.getSize() >= bytes && freeBlock.getSize() < bestFitBlock.getSize()) {
                bestFitBlock = freeBlock;
            }
        }

        // If no free block found return false.
        if (bestFitBlock.getSize() == memory.getCapacity()) {
            return false;
        }

        // Otherwise allocate the block and retun true.
        memory.addBlock(new Block(id) , bestFitBlock.getStartAddress(), bestFitBlock.getStartAddress() + bytes - 1);
        return true;
        
    }

    @Override
    public String getStrategy() {
        return "Best fit";
    }

}
