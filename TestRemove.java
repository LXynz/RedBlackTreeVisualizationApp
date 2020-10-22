// --== CS400 File Header Information ==--
// Name: Haining Qiu
// Email: hqiu37@wisc.edu
// Team: CT
// TA: Mu Cai
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains JUnit5 test cases for difference removing situations. Note that the order of
 * test cases is not the same as the order in the remove method, because for testing purpose this
 * class will test the easiest case first and the most complicated case last. Do not modify these
 * test cases. If you want to test your own cases, please use TestVisualization class.
 * 
 * @author Haining Qiu
 *
 */
class TestRemove {

  @AfterEach
  public void line() {
    for (int i = 0; i < 30; ++i) {
      System.out.print("*");
    }
    System.out.println();
  }

  /**
   * Remove Case 1: removing a red node with no children.
   */
  @Test
  public void testCase1() {
    VisualizationApp tree = new VisualizationApp();
    tree.insert(2);
    tree.insert(1);
    tree.insert(3);
    tree.remove(1);
    ///////////////////
    String correct = "[2, 3]";
    System.out.println(tree.drawTree());
    System.out.println("Your tree: " + tree);
    if (!tree.toString()
        .equals(correct)) {
      fail("Correct tree: " + correct);
    }
  }
  
  /**
   * Remove Case 2: removing a black node with one children.
   */
  @Test
  public void testCase2() {
    VisualizationApp tree = new VisualizationApp();
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(5);
    tree.insert(6);
    tree.remove(6);
    ///////////////////
    String correct = "[2, 1, 4, 3, 5]";
    System.out.println(tree.drawTree());
    System.out.println("Your tree: " + tree);
    if (!tree.toString()
        .equals(correct)) {
      fail("Correct tree: " + correct);
    }
  }

  /**
   * Remove Case 3.1: removing a red node with two children.
   */
  @Test
  public void testCase3_1() {
    VisualizationApp tree = new VisualizationApp();
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(5);
    tree.remove(4);
    ///////////////////
    String correct = "[2, 1, 5, 3]";
    System.out.println(tree.drawTree());
    System.out.println("Your tree: " + tree);
    if (!tree.toString()
        .equals(correct)) {
      fail("Correct tree: " + correct);
    }
  }
  /**
   * Remove Case 3.2: removing a black node with two children.
   */
  @Test
  public void testCase3_2() {
    VisualizationApp tree = new VisualizationApp();
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(5);
    tree.remove(2);
    ///////////////////
    String correct = "[3, 1, 4, 5]";
    System.out.println(tree.drawTree());
    System.out.println("Your tree: " + tree);
    if (!tree.toString()
        .equals(correct)) {
      fail("Correct tree: " + correct);
    }
  }
  
  /**
   * Remove Case 4.1: removing a black node with no children, sibling of double black is red.
   */
  @Test
  public void testCase4_1() {
    VisualizationApp tree = new VisualizationApp();
    tree.insert(5);
    tree.insert(2);
    tree.insert(1);
    tree.insert(4);
    tree.insert(7);
    tree.insert(6);
    tree.remove(1);
    tree.remove(6);
    tree.remove(7);
    ///////////////////
    String correct = "[2, 5, 4]";
    System.out.println(tree.drawTree());
    System.out.println("Your tree: " + tree);
    if (!tree.toString()
        .equals(correct)) {
      fail("Correct tree: " + correct);
    }
  }

  /**
   * Remove Case 4.2: removing a black node with no children, sibling of double black is black and
   * has two black children or two null children.
   */
  @Test
  public void testCase4_2() {
    VisualizationApp tree = new VisualizationApp();
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(5);
    tree.insert(6);
    tree.insert(7);
    tree.insert(8);
    tree.insert(9);
    tree.remove(3);
    ///////////////////
    String correct = "[4, 2, 6, 1, 5, 8, 7, 9]";
    System.out.println(tree.drawTree());
    System.out.println("Your tree: " + tree);
    if (!tree.toString()
        .equals(correct)) {
      fail("Correct tree: " + correct);
    }
  }
  
  /**
   * Remove Case 4.3: removing a black node with no children, sibling of double black is black and
   * has one red child on the same side as the double black and the other child being black or null.
   */
  @Test
  public void testCase4_3() {
    VisualizationApp tree = new VisualizationApp();
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(5);
    tree.insert(6);
    tree.insert(7);
    tree.insert(8);
    tree.insert(9);
    tree.remove(9);
    tree.remove(5);
    ///////////////////
    String correct = "[4, 2, 7, 1, 3, 6, 8]";
    System.out.println(tree.drawTree());
    System.out.println("Your tree: " + tree);
    if (!tree.toString()
        .equals(correct)) {
      fail("Correct tree: " + correct);
    }
  }
  
  /**
   * Remove Case 4.4: removing a black node with no children, sibling of double black is black and
   * has one red children on the opposite side as the double black (regardless of the other child).
   */
  @Test
  public void testCase4_4() {
    VisualizationApp tree = new VisualizationApp();
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(5);
    tree.insert(6);
    tree.insert(7);
    tree.insert(8);
    tree.remove(5);
    ///////////////////
    String correct = "[4, 2, 7, 1, 3, 6, 8]";
    System.out.println(tree.drawTree());
    System.out.println("Your tree: " + tree);
    if (!tree.toString()
        .equals(correct)) {
      fail("Correct tree: " + correct);
    }
  }
  
  /**
   * Tests if the exception is thrown as expected when removing non-existing data.
   */
  @Test
  public void testIllegalArgument() {
    VisualizationApp tree = new VisualizationApp();
    tree.insert(1);
    try {
      tree.remove(2);
      fail("IllegalArgumentException not thrown as expected.");
    } catch (IllegalArgumentException e) {
      System.out.println("IllegalArgumentException correctly thrown.");
    }
  }
}