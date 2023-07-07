import java.util.*;

class GarbageCollector {

    static List<Integer> theGarbageCollector;
    static int lastPrinted;

    // initialize garbage collector
    public static void initializeGC() {
        theGarbageCollector = new ArrayList<Integer>();
    }

    // make space for new garbage to be collected
    public static void allocateGC() {
        theGarbageCollector.add(1);
    }

    // add a record variable to gc list
    public static void addVarToGC(String id) {
        
        Memory.Value value = Memory.getLocalOrGlobal(id);

        // if the variable was initialized, add it to the gc tracker
        if (value.recordVal != null) {
            // System.out.println("take " + value.recordVal[0]);
            int tracker = theGarbageCollector.get(0);
            tracker++;
            theGarbageCollector.set(0, tracker);
        }
    }

    // take away a record variable from the gc list
    public static void takeVarFromGC(String id) {
        Memory.Value value = Memory.getLocalOrGlobal(id);

        // if the variable was initialized, take it to the gc tracker
        if (value.recordVal != null) {
            int tracker = theGarbageCollector.get(0);
            tracker--;
            theGarbageCollector.set(0, tracker);
        }
    }

    public static void checkIfIdIsAtZero(String id) {
        Memory.Value value = Memory.getLocalOrGlobal(id);
        if (value.recordVal != null) {
            int tracker = theGarbageCollector.get(0);
            if (tracker == 0) {
                printGC(allGarbage());
            }
        }
    }

    // print the gc
    public static void printGC(int gcCount) {
        if (lastPrinted != gcCount) {
            System.out.println("gc:" + gcCount);
            lastPrinted = gcCount;
        }
    }

    // replaces theGarbageCollector.size() to account for all Garbage instances
    public static int allGarbage() {
        int sum = 0;
        for (int tracker: theGarbageCollector) {
            if (tracker != 0) {
                sum++;
            }
        }
        return sum;
    }

    // handle printing down the list until you hit 0
    public static void endOfProcedure() {
        int totalSize = allGarbage();
        while (totalSize > 0) {
            totalSize--;
            // System.out.print("eofP ");
            printGC(totalSize);
        }
    }
}