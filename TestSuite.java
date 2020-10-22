// --== CS400 File Header Information ==--
// Name:                Eric Choi
// Email:               hchoi256@wisc.edu
// Team:                CT
// Role:                Test Engineer
// TA:                  Mu Cai
// Lecturer:            Gary
// Notes to Grader:     <optional extra notes>

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
public class TestSuite {
    public static VisualizationApp tree = null;

    @BeforeAll
    static void createInstance(){
	tree = new VisualizationApp();
    }
    
    @Test
    void testInsert() {
    	System.out.println("\nInsertion started: add 5, 3, 8, 6, 9, 10, 11, 12");
		tree.insert(5);
       	tree.insert(3);
       	tree.insert(8);
       	tree.insert(6);
       	tree.insert(9);
       	tree.insert(10);
       	tree.insert(11);
       	tree.insert(12);
       	System.out.println(tree);
       	System.out.println(tree.drawTree());
    }

//    @Test
//    void testRemove() {
//    	System.out.println("\nDeletion started: remove 10, 5");
//      	tree.remove(10);
//      	tree.remove(5);
//      	System.out.println(tree);
//       	System.out.println(tree.drawTree());
//       	tree.printToTextFile();
//       	System.out.println("read from text below:");
//       	tree.retrieveFromTextFile();
//    }
}
