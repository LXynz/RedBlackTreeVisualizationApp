// --== CS400 File Header Information ==--
// Name: Yoonsung Jeong, Wangjie Xu
// Email: yjeong48@wisc.edu, wxu57@wisc.edu
// Team: CT
// Role: Data Wrangler 2, Data Wrangler 1
// TA: Mu Cai
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * This class allows data to be saved into a txt file and data to be retrieved from a txt file for a
 * red black tree.
 * 
 * @author Yoonsung Jeong
 * @author Wangjie Xu
 *
 */
public class dataExchange {

  private final static String FILE_NAME = "RBT.txt"; // file name

  /**
   * This method serves to print the drawn tree to RBT.txt file
   */
  public static void printToTextFile(RedBlackTree.Node<Integer> root) {
    Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    String s = path.toString();
    try {
      File rbt = new File(s + "/" + FILE_NAME);
      rbt.createNewFile();// create a blank txt file
      BufferedWriter out = new BufferedWriter(new FileWriter(rbt));
      if (root == null) {
        out.write("[]"); //allows empty trees to be stored
        return;
      }
      out.write(root.toString());// print the RBT in level order to text file
      out.flush(); // clear the buffer of the data stream
      out.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method serves to read from RBT.txt file
   * 
   * @return Integer array of data from RBT.txt file so backend dev can use this array to insert
   *         each data into the tree.
   */
  public static Integer[] retrieveFromTextFile() {
    Integer[] parsedArr = null;
    Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    String s = path.toString();
    try {
      FileReader reader = new FileReader(s + "/" + FILE_NAME);
      String fileContent_initial = "";
      int character;
      while ((character = reader.read()) != -1) {
        fileContent_initial += ((char) character); //reading data from txt file into an array
      }
      if(fileContent_initial.equals("")) return null; //checks if the txt file is empty
      String fileContent_temp = fileContent_initial.replace("[", ""); 
      String fileContent = fileContent_temp.replace("]", "");
      String[] contentArr = fileContent.trim().split(", ");
      //extracts the values excluding the square brackets
      if (contentArr == null || contentArr[0] == null || contentArr[0].equals("")) return null;
      //checks if tree is empty
      parsedArr = new Integer[contentArr.length];
      for (int i = 0; i < parsedArr.length; i++) {
        parsedArr[i] = Integer.parseInt(contentArr[i]); //parsing String values to Integers
      }
      reader.close();

    } catch (FileNotFoundException e) {
      System.out.println("File Not Found.");
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return parsedArr;
  }
  
  /**
   * This method clears the data in the txt file
   * @throws IOException
   */
  public static void clearFile() {
    Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
    String s = path.toString();
    try {
      FileWriter toClear = new FileWriter(s + "/" + FILE_NAME);
      toClear.write("");
      toClear.flush();
      toClear.close();
    } catch (FileNotFoundException e) {
      System.out.println("File Not Found.");
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}