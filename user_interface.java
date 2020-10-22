// --== CS400 File Header Information ==--
// Name: Nanzhen Yan
// Email: nyan7@wisc.edu
// Team: CT
// Role: Front End Developer
// TA: Mu Cai
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class user_interface {
  static private VisualizationApp tree;
  private static boolean first1 = false;

  /*
   * Please adjust the file path for your own RBT.txt.
   */
  private static boolean fileExist() {
    File file = new File("RBT.txt");
    if (file.exists())
      return true;
    else
      return false;
  }

  /*
   * This method will get the first char of string.
   */
  private static char firstChar(Scanner sc) {
    String input = sc.nextLine().toLowerCase();
    char first = '\0';
    if (input.length() == 0 || input == null) {
      return first;
    }
    return first = input.charAt(0);
  }

  private static boolean isInteger(String a) {
    int num;
    try {
      num = Integer.parseInt(a);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private static void line() {
    for (int i = 0; i < 50; ++i) {
      System.out.print("*-");
    }
    System.out.println("*");
  }

  private static void menu() {
    line();
    System.out.println("(C):Create a new tree.\n" + "(L):Load your previous tree from RBT.txt .\n"
        + "(I):Insert a now node in the Red Black Tree. It will need the value of node.\n"
        + "(R):Remove the node you already entered. It will need the value of node.\n"
        + "(M):Show the menu.\n" + "(S):Save your inserted value.\n"
        + "(D):Draw your Red Black Tree.\n" + "(Q):Quit.\n" + "(*):Clear all information.");
    line();
  }

  /**
   * This method will create a new Red Black Tree
   */
  private static void create() {
    tree = new VisualizationApp();
  }

  /**
   * Add an integer as a new node to the tree. this method should call insert() from backend. Return
   * false if there is already a same number since a RBT tree usually does not allow duplicate
   * nodes. Return true if add successfully.
   * 
   * @param i the integer to insert
   * @return true if add successfully, false otherwise.
   */
  public static boolean insert(Integer i) {
    try {
      tree.insert(i);
    } catch (IllegalArgumentException e) {
      System.out.println("Execption thrown: " + e);
      return false;
    }
    return true;
  }

  /**
   * Remove an existing node from the tree. This method should call remove() from backend. Return
   * false if there is not an existing node in the tree. Return true if delete successfully.
   * 
   * @param i the integer to delete
   * @return true if delete successfully, false otherwise.
   */
  public static boolean remove(Integer i) {
    try {
      tree.remove(i);
    } catch (Exception e) {
      System.out.println("Execption thrown: " + e);
      return false;
    }
    return true;

  }

  public static boolean isEmpty() {
    if (tree.root != null)
      return false;
    else
      return true;
  }

  /**
   * This method should delete all nodes in the tree, and also clear the file.
   */
  public static void clear(){
    while (!isEmpty()) {
      tree.remove(tree.root.data);
    }
    tree.clearFile();
  }

  /**
   * This method should call draw() from back end.
   * 
   */
  public static void draw() {
    System.out.println(tree.drawTree());
  }

  /**
   * This method should call save() from back end.
   * 
   * @return false if save() from back end return false, true otherwise.
   */
  public static boolean save() {
    if (tree.save()) {
      return true;
    } else
      return false;
  }

  /**
   * This method should call load() from back end. And this should not be a choice in the menu. This
   * method should be called when executing the program.
   * 
   * @return false if load() from back end return false, true otherwise.
   */
  public static boolean load() {
    create();
    if (fileExist()) {
      tree.load();
      return true;
    } else
      return false;
  }

  /**
   * Show the menu to user and call methods corresponding to user input. For example, if the user
   * input "insert 1", then this method should check "insert" and then pass "1" to insert() method.
   * This method should also prompt the user to try a new valid input when their last input is
   * invalid. My user interface for proj 1 can be used for reference.
   * 
   */
  public static void main(String[] args){

    System.out.println("Welcome to Red Black Tree Visualization Application\n"
        + "In this application you can creat your new RBT, and load your previous RBT at anytime.\n"
        + "After you creat or load your RBT, you can insert new value into the RBT, remove value, clear the RBT,\n"
        + "and draw the RBT. Our application can visualize RBT.");

    Scanner choice = new Scanner(System.in);
    char answer = '\0';
    boolean first = true;

    while (answer != 'q') {
      while (first) {
        line();
        System.out
            .println("If you want to use this application, you must create or load a tree first!");
        System.out.println("(C): Create a new tree.\n"
            + "(L): Load your previous tree from RBT.txt.\n" + "(Q): quit");
        answer = firstChar(choice);
        if (answer == 'c') {
          create();
          first = false;
        } else if (answer == 'l') {
          if (load()) {
            System.out.println("We have already loaded your tree!");
            first = false;
          } else
            System.out.println("There is no RBT.txt, please create a new tree!");
        } else if (answer == 'q')
          return;
        else
          System.out.println("Unknown action! Please try again!");
      }
      if (!first1) {
        System.out
            .println("Now you can use other functions of our Application! Here is the full menu!");
        menu();
        first1 = true;
      }

      System.out.println("What do you want to do?");
      answer = firstChar(choice);

      if (answer == 'l') {
        if (load())
          System.out.println("We have already loaded your tree!");
        else
          System.out.println("There is no RBT.txt, please create a new tree!");
      } else if (answer == 'c') {
        create();
      } else if (answer == 'i') {
        //////////// Insert//////////////
        System.out.println("How many values do you want to insert?");

        String answerTemp1 = "\0";
        answerTemp1 = choice.nextLine();
        while (!isInteger(answerTemp1)) {
          System.out.println("Please input an integer value! Please input your value again:");
          answerTemp1 = choice.nextLine();
        }
        int answer3 = Integer.parseInt(answerTemp1);

        for (int i = 0; i < answer3; i++) {
          System.out.print("Value " + (i + 1) + ":");

          String answerTemp2 = "\0";
          answerTemp2 = choice.nextLine();
          while (!isInteger(answerTemp2)) {
            System.out.println("Please input an integer value! Please input your value again:");
            answerTemp2 = choice.nextLine();
          }
          int newValue = Integer.parseInt(answerTemp2);

          if (insert(newValue)) {
            System.out.println("Added successfully");
          } else {
            System.out.println("Please input your value again!");
            i--;
          }
        }

      } else if (answer == 'r') {
        System.out.println("Plese input the value you want to remove: ");
        String answerTemp = "\0";
        answerTemp = choice.nextLine();
        while (!isInteger(answerTemp)) {
          System.out.println("Please input an integer value! Please input your value again:");
          answerTemp = choice.nextLine();
        }
        int valueDelete = Integer.parseInt(answerTemp);

        if (remove(valueDelete))
          System.out.println("Removed " + valueDelete + " successfully");
        else
          System.out.println("This value does not exist!");
      } else if (answer == 'd') {
        draw();
      } else if (answer == 'm') {
        line();
        menu();
      } else if (answer == 's') {
        if (save())
          System.out.println("Saved this RBT successfully!");
        else
          System.out.println("We can't save this RBT!");
      } else if (answer == '*') {
        clear();
      } else if (answer == 'q')
        return;
      else {
        System.out.println("Unknown action! Please try again!");
      }

    }
  }
}