/*
* File: Main.java
* Author: John Kucera
* Date: 2/26/2021
* Purpose: This Java program is designed to implement a demand paging virtual Memory
* simulator. It generates a menu that allows the user to input a reference string or
* randomly generate. Then, the user can simulate different algorithms on that
* reference string, such as FIFO, OPT, LRU, and LFU. The simulations are
* output as diagrams in the console.
*/

// importing necessary Java classes
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

// Main class
public class Main {
  Scanner input = new Scanner(System.in);

  // Main constructor, loops through menu
  public Main() {
    int menuSelection = 0;
    ArrayList<Integer> referenceString = new ArrayList<>();
    
    // Print welcome message
    System.out.println("\n****** Welcome to the Demand Paging Virtual Memory Simulator! ******");

    // Loop menu after each task
    while (true) {
      try {
        // Print menu, scan in user selection
        System.out.print("\n0 - Exit\n" +
                        "1 - Read reference string\n" +
                        "2 - Generate reference string\n" +
                        "3 - Display current reference string\n" +
                        "4 - Simulate FIFO\n" +
                        "5 - Simulate OPT\n" +
                        "6 - Simulate LRU\n" +
                        "7 - Simulate LFU\n" +
                        "Select option:\n\n");
        menuSelection = Integer.parseInt(input.nextLine().trim());
        
        // Perform task based on user selection
        switch (menuSelection) {
          // 0: Exit
          case 0:
            input.close();
            System.out.println("\nExiting program.");
            System.exit(0);
          
          // 1: Read reference string
          case 1:
            referenceString = readReferenceString();
            break;

          // 2: Generate reference string
          case 2:
            referenceString = generateReferenceString();
            break;

          // 3: Display current reference string
          case 3:
            // Check for reference string
            if (referenceString.isEmpty()) {
              System.out.println("\nNo reference string has been set. Please try again.");
            } // end of if
            else {
              System.out.println("\nCurrent reference string: " + referenceString);
            } // end of else
            break;

          // 4: Simulate FIFO
          case 4:
            // Check for reference string
            if (referenceString.isEmpty()) {
              System.out.println("\nNo reference string has been set. Please try again.");
            } // end of if
            else {
              fifo(referenceString);
            } // end of else
            break;
          
          // 5: Simulate OPT
          case 5:
            // Check for reference string
            if (referenceString.isEmpty()) {
              System.out.println("\nNo reference string has been set. Please try again.");
            } // end of if
            else {
              opt(referenceString);
            } // end of else
            break;
          
          // 6: Simulate LRU
          case 6:
            // Check for reference string
            if (referenceString.isEmpty()) {
              System.out.println("\nNo reference string has been set. Please try again.");
            } // end of if
            else {
              lru(referenceString);
            } // end of else
            break;
          
          // 7: Simulate LFU
          case 7:
            // Check for reference string
            if (referenceString.isEmpty()) {
              System.out.println("\nNo reference string has been set. Please try again.");
            } // end of if
            else {
              lfu(referenceString);
            } // end of else
            break;
          
          // Invalid Input
          default:
            System.out.println("\nInvalid input. Only integers 0 to 7 are accepted. Please try again.");
            break;
        } // end of switch
      } // end of try
      catch (NumberFormatException e) {
        System.out.println("\nInvalid input. Only integers 0 to 7 are accepted. Please try again.");
      } // end of catch
    } // end of while
  } // end of method

  // readReferenceString method: for Option 1
  private ArrayList<Integer> readReferenceString() {
    // Variable Initialization
    ArrayList<Integer> newString = new ArrayList<>();
    int length = 0;
    int i = 0;
    int referenceValue = 0;

    while (true) {
      try {
        // Prompt for length of reference string, must be positive
        System.out.println("\nEnter the length of the new reference string: \n");
        length = Integer.parseInt(input.nextLine().trim());
        if (length <= 0) {
          System.out.println("\nInvalid input. Only positive integers are accepted. Please try again.");
        } // end of if

        // Prompt for values to be input one at a time
        else {
          while (i < length) {
            try {
              System.out.println("\nEnter a value into the reference string one at a time: " + "\n");
              referenceValue = Integer.parseInt(input.nextLine().trim());
              
              // Add input value into reference string IF it is valid
              if (0 <= referenceValue && referenceValue <= 9) {
                newString.add(i, referenceValue);
                i++;
                System.out.println("\nCurrent reading of reference string: " + newString);
              } // end of if
              else {
                System.out.println("\nInvalid input. Only integers 0 to 9 are accepted. Please try again.");
              } // end of else
            } // end of try
            catch (NumberFormatException e) { // error for string value
              System.out.println("\nInvalid input. Only integers 0 to 9 are accepted. Please try again.");
            } // end of catch
          } // end of while
          return newString;
        } // end of else
      } // end of try
      catch (NumberFormatException e) { // error for string length
        System.out.println("\nInvalid input. Only positive integers are accepted. Please try again.");
      } // end of catch
    } // end of while
  } // end of method

  // generateReferenceString method: for Option 2
  private ArrayList<Integer> generateReferenceString() {
    // Variable Initialization
    ArrayList<Integer> randomString = new ArrayList<>();
    int length = 0;
    int i = 0;
    Random r = new Random();

    while (true) {
      try {
        // Prompt for length of reference string, must be positive
        System.out.println("\nEnter the length of the randomly generated reference string: \n");
        length = Integer.parseInt(input.nextLine().trim());
        if (length <= 0) {
          System.out.println("\nInvalid input. Only positive integers are accepted. Please try again.");
        } // end of if
        else {
          while (i < length) {
            randomString.add(i, r.nextInt(10));
            i++;
          } // end of while
          System.out.println("\nGenerated reference string: " + randomString);
          return randomString;
        } // end of else
      } // end of try
      catch (NumberFormatException e) { // error for string length
        System.out.println("\nInvalid input. Only positive integers are accepted. Please try again.");
      } // end of catch
    } // end of while
  } // end of method

  // createDiagram method: for Options 4-7, creates a String[][] object that contains data
  private String[][] createDiagram(int physicalFrames, ArrayList<Integer> refString) {
    // Create diagram based on number of physical strings and reference string
    String[][] diagram = new String[physicalFrames + 3][refString.size() + 1];

    // Insert labels of diagram
    diagram[0][0] = "Reference String:";
    diagram[physicalFrames + 1][0] = "Pages Faults:";
    diagram[physicalFrames + 2][0] = "Victim Frames:";
    for (int i = 0; i < physicalFrames; i++) {
      diagram[i + 1][0] = "Physical Frame " + i + ":";
    } // end of for

    // Insert string values
    for (int j = 0; j < refString.size(); j++) {
      diagram[0][j + 1] = String.valueOf(refString.get(j));
    } // end of for
    return diagram;
  } // end of method

  // printDiagram method: for Options 4-7, prints the String[][] object made for diagram
  private void printDiagram(String[][] diagram) {
    System.out.println("\nDEMAND PAGING DIAGRAM");
    for (String[] row : diagram) { // for each row
      for (int column = 0; column < row.length; column++) { // for each column in that row
        // Print leftmost label
        if (column == 0) {
          System.out.printf("%17s", row[0]);
        } // end of if

        // Print data
        else {
          // Contains value
          if (row[column] != null) {
            System.out.printf("%2s", row[column]);
          } // end of if

          // Does not contain value
          else {
            System.out.printf("%2s", " ");
          } // end of else
        } // end of else
      } // end of for
      System.out.print("\n"); // go to next row
    } // end of for
  } // end of method

  // getPhysicalFrames method: for Options 4-7, prompts user for number of physical frames and returns value
  private int getPhysicalFrames() {
    int physicalFrames = 0;
    // Prompt user for number of physical frames
    while (true) {
      try {
        System.out.println("\nEnter number of Physical Frames (1 to 8): \n");
        physicalFrames = Integer.parseInt(input.nextLine().trim());
        if (physicalFrames >= 1 && physicalFrames <= 8) {
          return physicalFrames;
        } // end of if
        else { // error handling
          System.out.println("\nInvalid input. Only integers 1 to 8 are accepted. Please try again.");
        } // end of else
      } // end of try
      catch (NumberFormatException e) { // error handling
        System.out.println("\nInvalid input. Only integers 1 to 8 are accepted. Please try again.");
      } // end of catch
    } // end of while
  } // end of method

  // fifo method: for Option 4, simulates fifo algorithm on reference string
  private void fifo(ArrayList<Integer> inputString) {
    // Variable Initialization
    boolean pageFault = false;
    int currFrame = 0;
    int numFaults = 0;
    int victimFrame = -1;
    int physFrames = getPhysicalFrames();

    // Initialize String[][] for diagram
    ArrayList<Integer> physMemory = new ArrayList<>(physFrames);
    String[][] fifoDiagram = createDiagram(physFrames, inputString);
    System.out.println("\nFIFO (First In, First Out) Simulation: ");
    printDiagram(fifoDiagram);

    // for every value in reference string:
    for (int i = 0; i < inputString.size(); i++) {
      // Prompt user to press enter
      System.out.print("\nPress Enter to continue to the next step: ");
      input.nextLine();

      // IF value is not yet in memory, add it in
      if (!physMemory.contains(inputString.get(i))) {
        // IF memory has an empty space, add it to next empty frame
        if (physMemory.size() < physFrames) { 
          physMemory.add(currFrame, inputString.get(i));
          currFrame++;
          numFaults++;
          pageFault = true;
        } // end of if

        // IF memory has NO empty space, add it into "first-in" frame, moving that to victim
        else {
          if (currFrame >= physFrames) { // move to first physical frame
            currFrame = 0;
          } // end of if
          victimFrame = physMemory.get(currFrame);
          physMemory.set(currFrame, inputString.get(i));
          currFrame++;
          numFaults++;
          pageFault = true;
        } // end of else
      } // end of if

      // IF value is already in memory, don't do anything
      else {
        pageFault = false;
      } // end of else

      // for every value in physical memory, add to diagram
      for (int j = 0; j < physMemory.size(); j++) {
        fifoDiagram[j + 1][i + 1] = String.valueOf(physMemory.get(j));
      } // end of for
      
      // IF there is a page fault, indicate in diagram
      if (pageFault) {
        fifoDiagram[physFrames + 1][i + 1] = "F";
        // IF there is a victim frame, indicate in diagram
        if (victimFrame > -1) {
          fifoDiagram[physFrames + 2][i + 1] = String.valueOf(victimFrame);
        } // end of if
      } // end of if
      printDiagram(fifoDiagram); // Continue printing each step
    } // end of for
    System.out.println("\nTotal number of faults: " + numFaults);
  } // end of method
  
  // opt method: for Option 5, simulates OPT algorithm on reference string
  private void opt(ArrayList<Integer> inputString) {
    // Variable Initialization
    boolean pageFault = false;
    int currFrame = 0;
    int numFaults = 0;
    int victimFrame = -1;
    int physFrames = getPhysicalFrames();
    int latestUsed = -1;
    int currValue = 0;
    int storeValue = 0;

    // Make second copy of string to use OPT and "look into future" references
    ArrayList<Integer> storeString = new ArrayList<>();
    for (int i : inputString) {
      storeString.add(i);
    } // end of for

    // Initialize String[][] for diagram
    ArrayList<Integer> physMemory = new ArrayList<>(physFrames);
    String[][] optDiagram = createDiagram(physFrames, inputString);
    System.out.println("\nOPT (Optimal Page-Replacement) Simulation: ");
    printDiagram(optDiagram);

    // for every value in reference string:
    for (int i = 0; i < inputString.size(); i++) {
      // Prompt user to press enter
      System.out.print("\nPress Enter to continue to the next step: ");
      input.nextLine();

      // IF value is not yet in memory, add it in
      if (!physMemory.contains(inputString.get(i))) {
        // IF memory has an empty space, add it to next empty frame
        if (physMemory.size() < physFrames) { 
          physMemory.add(currFrame, inputString.get(i));
          currFrame++;
          numFaults++;
          pageFault = true;
          storeString.remove(inputString.get(i));
        } // end of if

        // IF memory has NO empty space, "look into future" and replace
        // the value that will be reference LATEST
        else {
          // Remove entering value from stored string
          storeValue = storeString.get(0);
          storeString.remove(0);
          // for each value in memory, assess how soon it will be
          // referenced in the future
          for (int value : physMemory) {
            currValue = storeString.indexOf(value);
            // IF value is never referenced again, it becomes victim
            if (currValue == -1) {
              victimFrame = value;
              break;
            } // end of if
            // See which value will be used latest, that latest becomes victim
            if (currValue > latestUsed) {
              latestUsed = currValue;
              victimFrame = value;
            } // end of if
          } // end of for
          physMemory.set(physMemory.indexOf(victimFrame), storeValue);
          numFaults++;
          pageFault = true;
          latestUsed = -1; // reset latest
        } // end of else
      } // end of if

      // IF value is already in memory, don't do anything
      else {
        storeString.remove(0);
        pageFault = false;
      } // end of else

      // for every value in physical memory, add to diagram
      for (int j = 0; j < physMemory.size(); j++) {
        optDiagram[j + 1][i + 1] = String.valueOf(physMemory.get(j));
      } // end of for
      
      // IF there is a page fault, indicate in diagram
      if (pageFault) {
        optDiagram[physFrames + 1][i + 1] = "F";
        // IF there is a victim frame, indicate in diagram
        if (victimFrame > -1) {
          optDiagram[physFrames + 2][i + 1] = String.valueOf(victimFrame);
        } // end of if
      } // end of if
      printDiagram(optDiagram); // Continue printing each step
    } // end of for
    System.out.println("\nTotal number of faults: " + numFaults);
  } // end of method

  // lru method: for Option 6, simulates LRU algorithm on reference string
  private void lru(ArrayList<Integer> inputString) {
    // Variable Initialization
    boolean pageFault = false;
    int currFrame = 0;
    int numFaults = 0;
    int victimFrame = -1;
    int physFrames = getPhysicalFrames();
    int leastRecent = -1;
    int currValue = 0;
    int[] recentlyUsed = new int[physFrames]; // keeping track of what values are used

    // Initialize String[][] for diagram
    ArrayList<Integer> physMemory = new ArrayList<>(physFrames);
    String[][] lruDiagram = createDiagram(physFrames, inputString);
    System.out.println("\nLRU (Least Recently Used) Simulation: ");
    printDiagram(lruDiagram);

    // for every value in reference string:
    for (int i = 0; i < inputString.size(); i++) {
      // Prompt user to press enter
      System.out.print("\nPress Enter to continue to the next step: ");
      input.nextLine();

      // IF value is not yet in memory, add it in
      if (!physMemory.contains(inputString.get(i))) {
        // IF memory has an empty space, add it to next empty frame
        if (physMemory.size() < physFrames) {
          physMemory.add(currFrame, inputString.get(i));
          numFaults++;
          pageFault = true;
          // Keeping track of recently used and reset
          for (int k = 0; k < recentlyUsed.length; k++) {
            recentlyUsed[k]++;
          } // end of for
          recentlyUsed[currFrame] = 1;
          currFrame++;
        } // end of if

        // IF memory has NO empty space, add it into "least recent" frame, moving that to victim
        else {
          // Reset variables to assess each time
          leastRecent = -1;
          currValue = 0;

          // for each frame in recently used:
          for (int m = 0; m < recentlyUsed.length; m++) {
            // if the value is less recent than current least recent, assign it
            if (recentlyUsed[m] > leastRecent) {
              currValue = m;
              leastRecent = recentlyUsed[m];
            } // end of if
          } // end of for

          // Least recent becomes victim, add in new value
          victimFrame = physMemory.get(currValue);
          physMemory.set(currValue, inputString.get(i));
          numFaults++;
          pageFault = true;

          // Record what has been recently used and reset current
          for (int n = 0; n < recentlyUsed.length; n++) {
            recentlyUsed[n]++;
          } // end of for
          recentlyUsed[physMemory.indexOf(inputString.get(i))] = 1;
        } // end of else
      } // end of if

      // IF value is already in memory, don't do anything
      else {
        pageFault = false;
        // Keeping track of recently used and reset current
        for (int o = 0; o < recentlyUsed.length; o++) {
          recentlyUsed[o]++;
        } // end of for
        recentlyUsed[physMemory.indexOf(inputString.get(i))] = 1;
      } // end of else

      // for every value in physical memory, add to diagram
      for (int j = 0; j < physMemory.size(); j++) {
        lruDiagram[j + 1][i + 1] = String.valueOf(physMemory.get(j));
      } // end of for
      
      // IF there is a page fault, indicate in diagram
      if (pageFault) {
        lruDiagram[physFrames + 1][i + 1] = "F";
        // IF there is a victim frame, indicate in diagram
        if (victimFrame > -1) {
          lruDiagram[physFrames + 2][i + 1] = String.valueOf(victimFrame);
        } // end of if
      } // end of if
      printDiagram(lruDiagram); // Continue printing each step
    } // end of for
    System.out.println("\nTotal number of faults: " + numFaults);
  } // end of method

  // lfu method: for Option 7, simulates LFU algorithm on reference string
  private void lfu(ArrayList<Integer> inputString) {
    // Variable Initialization
    boolean pageFault = false;
    int currFrame = 0;
    int numFaults = 0;
    int victimFrame = -1;
    int physFrames = getPhysicalFrames();
    int leastFrequent = -1;
    int frequency = 0;
    int currValue = 0;
    HashMap<Integer, Integer> frequentlyUsed = new HashMap<>();

    // Initialize String[][] for diagram
    ArrayList<Integer> physMemory = new ArrayList<>(physFrames);
    String[][] lfuDiagram = createDiagram(physFrames, inputString);
    System.out.println("\nLFU (Least Frequently Used) Simulation: ");
    printDiagram(lfuDiagram);

    // for every value in reference string:
    for (int i = 0; i < inputString.size(); i++) {
      // Prompt user to press enter
      System.out.print("\nPress Enter to continue to the next step: ");
      input.nextLine();

      // IF value is not yet in memory, add it in
      if (!physMemory.contains(inputString.get(i))) {
        // IF memory has an empty space, add it to next empty frame
        if (physMemory.size() < physFrames) {
          physMemory.add(currFrame, inputString.get(i));
          currFrame++;
          numFaults++;
          pageFault = true;
          // Keeping track of frequently used
          frequentlyUsed.put(inputString.get(i), 1);
        } // end of if

        // IF memory has NO empty space, add it into "least frequent" frame, moving that to victim
        else {
          // Reset variables to assess each time
          leastFrequent = frequentlyUsed.get(physMemory.get(0));
          currValue = 0;

          // for every frame in memory:
          for (int k = 0; k < physMemory.size(); k++) {
            // if value is less frequent than current least frequent
            if (frequentlyUsed.get(physMemory.get(k)) < leastFrequent) {
              currValue = k;
              leastFrequent = frequentlyUsed.get(physMemory.get(k));
            } // end of if
          } // end of for

          // Least frequent becomes victim, add in new value
          victimFrame = physMemory.get(currValue);
          physMemory.set(currValue, inputString.get(i));
          numFaults++;
          pageFault = true;

          // Add an occurrence to the current value
          // or add to hashmap if not already there
          if (frequentlyUsed.containsKey(inputString.get(i))) {
            frequency = frequentlyUsed.get(inputString.get(i));
            frequency++;
            frequentlyUsed.put(inputString.get(i), frequency);
          } // end of if
          else {
            frequentlyUsed.put(inputString.get(i), 1);
          } // end of else
        } // end of else
      } // end of if

      // IF value is already in memory, don't do anything
      else {
        pageFault = false;
        // Add occurence to the current value
        frequency = frequentlyUsed.get(inputString.get(i));
        frequency++;
        frequentlyUsed.put(inputString.get(i), frequency);
      } // end of else

      // for every value in physical memory, add to diagram
      for (int j = 0; j < physMemory.size(); j++) {
        lfuDiagram[j + 1][i + 1] = String.valueOf(physMemory.get(j));
      } // end of for
      
      // IF there is a page fault, indicate in diagram
      if (pageFault) {
        lfuDiagram[physFrames + 1][i + 1] = "F";
        // IF there is a victim frame, indicate in diagram
        if (victimFrame > -1) {
          lfuDiagram[physFrames + 2][i + 1] = String.valueOf(victimFrame);
        } // end of if
      } // end of if
      printDiagram(lfuDiagram); // Continue printing each step
    } // end of for
    System.out.println("\nTotal number of faults: " + numFaults);
  } // end of method

  // main method
  public static void main(String[] args) {
    Main menu = new Main();
  } // end of method
} // end of class