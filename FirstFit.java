public class FirstFit implements AllocationStrategy {

    @Override
    public boolean allocateBlock(int id, int bytes, Memory memory) {

        // Allocate the first fitting free block in memory.
        for (Block freeBlock : memory.getFreeBlocks()) {
            if (freeBlock.getSize() >= bytes) {
                memory.addBlock(new Block(id), freeBlock.getStartAddress(), freeBlock.getStartAddress() + bytes - 1);
                return true;
            }
        }

        // If no free block found return false.
        return false;

    }

    @Override
    public String getStrategy() {
        return "First fit";
    }
    
}
