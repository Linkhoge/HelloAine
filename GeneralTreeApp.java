// Student Name     : Ariel Fajimiyo
// Student Id Number: C00300811@setu.ie
// Date             : 07/12/25
// Purpose          : A test driver program for my general tree implementation using arrays

/*
   part 3 - general tree using arrays
   classification: primates
*/

class Node {
    String data;        // species name
    int leftMostChild;  // first child index
    int rightSibling;   // next sibling index
    int parent;         // parent index

    /**
     * creates a node for the general tree
     */
    public Node(String data, int lmc, int rs, int p) {
        this.data = data;
        this.leftMostChild = lmc;
        this.rightSibling = rs;
        this.parent = p;
    }
}

public class GeneralTreeApp {

    // the tree stored here (array based LMC–RMS)
    static Node[] A = new Node[20];
    static int nodeCount;

    /**
     * main test driver for all tree operations
     */
    public static void main(String[] args) {

        buildPrimatesTree();   // load test data

        System.out.println("Descendants of Primates (direct):");
        String[] res1 = GetDescendants("Primates", false);
        printArray(res1, lastR);

        System.out.println("\nDescendants of Primates (all):");
        String[] res2 = GetDescendants("Primates", true);
        printArray(res2, lastR);

        System.out.println("\nSiblings of Saki:");
        String[] res3 = GetRelatedSpecies("Saki");
        printArray(res3, lastR);

        System.out.println("\nAncestor of Human:");
        System.out.println(GetAncestor("Human"));
    }

    // remembers how many items were added to the last result array
    static int lastR = 0;

    /**
     * builds the primates classification tree
     * using the LMC–RMS representation
     */
    static void buildPrimatesTree() {

        // root
        A[0] = new Node("Primates", 1, -1, -1);

        // level 1
        A[1] = new Node("New World Monkeys", 4, 2, 0);
        A[2] = new Node("Hominids", 7, 3, 0);
        A[3] = new Node("Old World Monkeys", 9, -1, 0);

        // new world monkeys
        A[4] = new Node("Capuchin", -1, 5, 1);
        A[5] = new Node("Saki", -1, 6, 1);
        A[6] = new Node("Marmoset", -1, -1, 1);

        // hominids
        A[7] = new Node("Human", -1, 8, 2);
        A[8] = new Node("Chimpanzee", -1, -1, 2);

        // old world monkeys
        A[9]  = new Node("Baboon", -1, 10, 3);
        A[10] = new Node("Macaque", -1, 11, 3);
        A[11] = new Node("Proboscis", -1, -1, 3);

        nodeCount = 12;
    }

    /**
     * returns the direct children or all descendants of a species
     */
    static String[] GetDescendants(String species, boolean includeAll) {

        String[] Result = new String[20];
        int R = 0;

        int index = FindIndex(species);
        int child = A[index].leftMostChild;

        while (child != -1) {

            Result[R] = A[child].data;
            R++;

            if (includeAll == true) {
                R = GetAllBelow(child, Result, R);
            }

            child = A[child].rightSibling;
        }

        lastR = R;   // save size for printing
        return Result;
    }

    /**
     * recursive helper that collects everything below a node
     */
    static int GetAllBelow(int nodeIndex, String[] Result, int R) {

        int child = A[nodeIndex].leftMostChild;

        while (child != -1) {
            Result[R] = A[child].data;
            R++;
            R = GetAllBelow(child, Result, R);

            child = A[child].rightSibling;
        }

        return R;
    }

    /**
     * returns all siblings of the given species
     */
    static String[] GetRelatedSpecies(String species) {

        String[] Result = new String[20];
        int R = 0;

        int index = FindIndex(species);
        int parent = A[index].parent;

        if (parent == -1) { // -1 means no parent (root)
            lastR = R;
            return Result;
        }

        int sibling = A[parent].leftMostChild;

        while (sibling != -1) {
            if (sibling != index) {
                Result[R] = A[sibling].data;
                R++;
            }

            sibling = A[sibling].rightSibling;
        }

        lastR = R;
        return Result;
    }

    /**
     * returns the parent of a species, or null if it has none
     */
    static String GetAncestor(String species) {

        int index = FindIndex(species);
        int parent = A[index].parent;

        if (parent == -1)
            return null;

        return A[parent].data;
    }

    /**
     * finds the array index of a species name
     */
    static int FindIndex(String species) {

        for (int i = 0; i < nodeCount; i++) {
            if (A[i].data.equals(species)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * prints only the used part of a result array
     */
    static void printArray(String[] arr, int size) {

        if (size == 0) {
            System.out.println("no results");
            return;
        }

        for (int i = 0; i < size; i++) {
            System.out.println("- " + arr[i]);
        }
    }
}
