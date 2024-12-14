import java.util.Scanner;


/**
 *
 * @author H.K computers
 */
import java.util.Scanner;

public class FirstFitMemoryAllocation {

    static class Block {
        int size;
        boolean allocated;
        String allocatedProcess;

        Block(int size) {
            this.size = size;
            this.allocated = false;
            this.allocatedProcess = null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Predefined memory blocks (Fixed sizes)
        int[] blockSizes = {200, 300, 100,500};
        Block[] memoryBlocks = new Block[blockSizes.length];

        for (int i = 0; i < blockSizes.length; i++) {
            memoryBlocks[i] = new Block(blockSizes[i]);
        }

        // Step 2: Input process sizes
        System.out.println("Enter the number of processes:");
        int processCount = scanner.nextInt();
        int[] processSizes = new int[processCount];

        System.out.println("Enter the sizes of the processes:");
        for (int i = 0; i < processCount; i++) {
            processSizes[i] = scanner.nextInt();
            if (processSizes[i] <= 0) {
                System.out.println("Invalid input. Process size must be greater than zero.");
                i--; // Retry input
            }
        }

        String[] processNames = new String[processCount];
        for (int i = 0; i < processCount; i++) {
            processNames[i] = "Process " + (char) ('A' + i); // Generate process names as A, B, C, ...
        }

        // Step 3: Allocate processes using First Fit
        for (int i = 0; i < processSizes.length; i++) {
            int processSize = processSizes[i];
            boolean allocated = false;

            for (int j = 0; j < memoryBlocks.length; j++) {
                // Find the first unallocated block with sufficient size
                if (!memoryBlocks[j].allocated && memoryBlocks[j].size >= processSize) {
                    System.out.println("\n" + processNames[i] + " allocated " + processSize + " KB in Block " + (j + 1));
                    memoryBlocks[j].size -= processSize; // Reduce the size of the block by the size of the allocated process.
                    memoryBlocks[j].allocated = memoryBlocks[j].size == 0; // Mark as allocated if size is 0
                    memoryBlocks[j].allocatedProcess = processNames[i];
                    allocated = true;
                    break; // Exit loop once a block is found
                }
            }

            if (!allocated) {
                System.out.println(processNames[i] + " could not be allocated. No suitable block found.");
            }
        }

        // Step 4: Print final memory state
        System.out.println("\nFinal Memory State:");
        for (int i = 0; i < memoryBlocks.length; i++) {
            System.out.println("Block " + (i + 1) + ": " + memoryBlocks[i].size + " KB ("
                    + (memoryBlocks[i].allocated ? "Allocated" : "Free") + ")");
        }

        // Step 5: Print final memory allocation summary
        System.out.println("\nFinal Memory Allocation:");
        for (int i = 0; i < memoryBlocks.length; i++) {
            if (memoryBlocks[i].allocatedProcess != null) {
                System.out.println(memoryBlocks[i].allocatedProcess + ": Allocated in Block " + (i + 1));
            }
        }

        scanner.close();
    }
}