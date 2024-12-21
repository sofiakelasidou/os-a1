public class Block {
    
    private int id;
    private int startAddress;
    private int endAddress;

    /**
     * Constructor.
     * @param id of the block.
     */
    public Block(int id) {
        this.id = id;
    }

    /**
     * Constructor with allocation.
     * @param id of the block.
     * @param startAddress of the block.
     * @param endAddress of the block.
     */
    public Block(int id, int startAddress, int endAddress) {
        this.id = id;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    /**
     * Allocate the block.
     * @param startAddress of the block in the memory.
     * @param endAddressof the block in the memory.
     */
    public void allocate(int startAddress, int endAddress) {
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    /**
     * Return the id of the block.
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Return the start address of the block in the memory.
     * @return the start address.
     */
    public int getStartAddress() {
        return startAddress;
    }

    /**
     * Return the end address of the block in the memory.
     * @return the end address.
     */
    public int getEndAddress() {
        return endAddress;
    }

    /**
     * Return the size of the block.
     * @return the size.
     */
    public int getSize() {
        return endAddress - startAddress + 1;
    }
}
