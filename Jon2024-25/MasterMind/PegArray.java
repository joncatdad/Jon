/**
 *  This class creates and manages one array of pegs from the game MasterMind.
 *
 *  @author Jonathan Chen
 *  @since September 27, 2024
*/
public class PegArray {
    private Peg[] pegs;   // Array of Pegs
    private int exact;    // Number of exact matches
    private int partial;   // Number of partial matches
    // Constructor
    public PegArray(int size) {
        pegs = new Peg[size]; // Initialize the array with the specified size
        for (int i = 0; i < size; i++) {
            pegs[i] = new Peg(); // Initialize each peg
        }
        exact = 0; // Initialize matches
        partial = 0; // Initialize matches
    }
    // Accessor method to get a specific peg
    public Peg getPeg(int index) {
        return pegs[index];
    }
    // Method to get the number of exact matches against a master PegArray
    public int getExactMatches(PegArray master) {
        exact = 0; // Reset exact count
        for (int i = 0; i < pegs.length; i++) {
            if (pegs[i].getLetter() == master.getPeg(i).getLetter()) {
                exact++; // Increment exact match count
            }
        }
        return exact; // Return the exact matches found
    }
    // Method to get the number of partial matches against a master PegArray
    public int getPartialMatches(PegArray master) {
        partial = 0; // Reset partial count
        boolean[] ismasterChecked = new boolean[master.pegs.length]; // Track checked master pegs
        boolean[] hasguessChecked = new boolean[pegs.length]; // Track checked guess pegs
        // First, find exact matches and mark them
        for (int i = 0; i < pegs.length; i++) {
            if (pegs[i].getLetter() == master.getPeg(i).getLetter()) {
                hasguessChecked[i] = true; // Mark guess peg as checked
                ismasterChecked[i] = true; // Mark master peg as checked
            }
        }
        // Now find partial matches
        for (int i = 0; i < pegs.length; i++) {
            if (!hasguessChecked[i]) { // Only check unchecked guess pegs
                for (int j = 0; j < master.pegs.length; j++) {
                    if (!ismasterChecked[j] && pegs[i].getLetter() == master.getPeg(j).getLetter()) {
                        partial++; // Increment partial match count
                        ismasterChecked[j] = true; // Mark master peg as checked
                        break; // Break to avoid double counting
                    }
                }
            }
        }
        return partial; // Return the partial matches found
    }
    // Accessor for exact matches
    public int getExact() {
        return exact;
    }
    // Accessor for partial matches
    public int getPartial() {
        return partial;
    }
}
