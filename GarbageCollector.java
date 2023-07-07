import java.util.*;

class GarbageCollector {

    static List<Integer> theGarbageCollector;

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
            int tracker = theGarbageCollector.get(value.recordVal[0]);
            tracker++;
            theGarbageCollector.set(value.recordVal[0], tracker);
        }
    }

    // take away a record variable from the gc list
    public static void takeVarFromGC(String id) {
        Memory.Value value = Memory.getLocalOrGlobal(id);

        // if the variable was initialized, add it to the gc tracker
        if (value.recordVal != null) {
            int tracker = theGarbageCollector.get(value.recordVal[0]);
            tracker--;
            theGarbageCollector.set(value.recordVal[0], tracker);
        }
    }

    // print the gc
    public static void printGC() {
        System.out.println("gc:" + theGarbageCollector.size());
    }
}