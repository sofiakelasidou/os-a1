import java.util.ArrayList;

public class Memory {

    private int capacity;
    private ArrayList<Block> blocks;
    private AllocationStrategy allocationStrategy;

    /**
     * Constructor.
     * @param allocationStrategy for executing allocation.
     */
    public Memory(AllocationStrategy allocationStrategy) {
        blocks = new ArrayList<>();
        this.allocationStrategy = allocationStrategy;
    }

    /**
     * Set a max capacity for the memory.
     * @param capacity of the memory.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Return the total capacity of memory.
     * @return the capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Add a block to the memory's alocated blocks.
     * @param block to add to memory.
     * @param startAdress of allocation.
     * @param endAddress of allocation.
     */
    public void addBlock(Block block, int startAdress, int endAddress) {
        block.allocate(startAdress, endAddress);
        blocks.add(block);
    }

    /**
     * Return the allocated blocks from the memory.
     * @return a copy of the blocks array.
     */
    public ArrayList<Block> getBlocks() {
        ArrayList<Block> copyBlocks = new ArrayList<>();
        for (Block b : blocks) {
            copyBlocks.add(b);
        }
        return copyBlocks;
    }

    /**
     * Return the allocated blocks sorted by start address.
     * @return the allocated blocks
     */
    public ArrayList<Block> getSortedBlocks() {
        ArrayList<Block> sortedBlocks = new ArrayList<>();
        int startLowestBlock;
        Block lowestBlock = new Block(-1);
        for (int i = 0; i < blocks.size(); i++) {
            startLowestBlock = capacity;
            for (Block b : blocks) {
                if (b.getStartAddress() < startLowestBlock && !sortedBlocks.contains(b)) {
                    lowestBlock = b;
                    startLowestBlock = b.getStartAddress();
                }
            }
            sortedBlocks.add(lowestBlock);
        }

        return sortedBlocks;
    }

    /**
     * Allocate a block with given id and bytes.
     * @param id of the block.
     * @param bytes of the block.
     * @return true if allocated, otherwise false.
     */
    public boolean allocateBlock(int id, int bytes) {

        // Return false if not enough capacity to allocate block
        if (bytes > capacity) {
            return false;
        }

        // If memory is empty and big enough alocate the block
        if (blocks.isEmpty()) {
            addBlock(new Block(id), 0, bytes - 1);
            return true;
        }

        // If memory is not empty use allocation strategy
        return allocationStrategy.allocateBlock(id, bytes, this);
    }

    /**
     * Deallocate the block with the given id.
     * @param id of the block.
     * @return true if the block has been removed, otherwise false.
     */
    public boolean deallocateBlock(int id) {
        for (Block block : blocks) {
            if (block.getId() == id) {
                return blocks.remove(block);
            }
        }
        return false;
    }

    /**
     * Compact the memory.
     * Move all allocated blocks towards the lowest memory address (0).
     */
    public void compact() {
        int startAddress = 0;
        int endAddress;
        ArrayList<Block> sortedBlocks = getSortedBlocks();
        for (Block block : sortedBlocks) {
            endAddress = startAddress + block.getSize() - 1;
            block.allocate(startAddress, endAddress);
            startAddress = endAddress + 1;
        }
    }

    /**
     * Return the size of the largest free block in memory.
     * @return the size if the max available block.
     */
    public int getMaxAvailableBlock() {

        // Find the largest gap between the end of a block and the start of the next one. 
        int maxFreeBlock = 0;
        int startFreeBlock = 0;
        for (Block b : getSortedBlocks()) {
            if (b.getStartAddress() - startFreeBlock >= maxFreeBlock) {
                maxFreeBlock = b.getStartAddress() - startFreeBlock;
            }
            startFreeBlock = b.getEndAddress() + 1;
        }

        // If there is a free block at the end of memory, check if it is the largest.
        if (capacity - startFreeBlock >= maxFreeBlock) {
            maxFreeBlock = capacity - startFreeBlock;
        }
        return maxFreeBlock;
    }

    /**
     * Return the free blocks in the memory.
     * @return an ArrayList with the free blocks.
     */
    public ArrayList<Block> getFreeBlocks() {

        // Find free blocks between the end of a block and the start of the next.
        ArrayList<Block> freeBlocks = new ArrayList<>();
        int startAdress = 0;
        for (Block block : getSortedBlocks()) {
            if (startAdress < block.getStartAddress()) {
                freeBlocks.add(new Block(-1, startAdress, block.getStartAddress() - 1));
            }
            startAdress = block.getEndAddress() + 1;
        }

        // Check if the block ending at the highest adress in memory is free.
        if (startAdress < capacity - 1) {
            Block freeBlock = new Block(-1);
                freeBlock.allocate(startAdress, capacity - 1);
                freeBlocks.add(freeBlock);
        }
        return freeBlocks;
    }

    /**
     * Return the size of total free memory.
     * @return total free memory.
     */
    private int getTotalFreeMemory() {
        int totalFreeMemory = 0;
        for (Block freeBlock : getFreeBlocks()) {
            totalFreeMemory += freeBlock.getSize();
        }
        return totalFreeMemory;
    }

    /**
     * Return the fragmentation for the current state of the memory.
     * @return the fragmentation.
     */
    public double getFragmentation() {
        
        // If no memory is free return 0.
        if (getTotalFreeMemory() == 0) {
            return 0;
        }

        // Calculate and return fragmentation.
        return 1.0 - (double) getMaxAvailableBlock() / getTotalFreeMemory();
    }
    
    /**
     * Return the name of the allocation strategy.
     * @return the allocation strategy.
     */
    public String getStrategy() {
        return allocationStrategy.getStrategy();
    }
}
