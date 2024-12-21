public interface AllocationStrategy {

    /**
     * Alocate a new block into memory according to specific strategy.
     * @param id of the new block.
     * @param bytes of the new block.
     * @param memory the total memory.
     * @return true if the block was alocated successfully, otherwise false.
     */
    public boolean allocateBlock(int id, int bytes, Memory memory);

    /**
     * Return the name of the allocation strategy.
     * @return the allocation strategy.
     */
    public String getStrategy();

}
