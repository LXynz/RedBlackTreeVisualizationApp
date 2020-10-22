import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

//--== CS400 File Header Information ==--
//Name: Boshan Chen, Haining Qiu, Eric Choi
//Email: bchen275@wisc.edu, hqiu37@wisc.edu, hchoi256@wisc.edu
//Team: CT
//Role: Backend Developer, Test Engineer 1, Test Engineer 2
//TA: Mu Cai
//Lecturer: Florian, Gary Dahl
//Notes to Grader: <optional extra notes>

/**
 * This class inherits from the HashTableMap class and provides a majortiy of
 * back-end functions that can be called using a User Interface provided by the
 * front-end developers. It is able to loads all data from the JSON file
 * provided by Data Wrangler into an array of LinkedList, to remove data in the
 * array while also deleting from the file, and to add data into both the array
 * and the file.
 * 
 * @author Boshan Chen
 * @author Haining Qiu
 * @author Eric Choi
 *
 */
public class VisualizationApp extends RedBlackTree<Integer> {

    public boolean save() {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        String s = path.toString();
        String name = s + "/" + "RBT.txt";
        File file = new File(name);
        try {
                dataExchange.printToTextFile(super.root);
                return true;
        } catch (Exception e) {
            return false;
        }
    }
   
    public boolean load() {
        int i = 0;
        Integer[] nodes = dataExchange.retrieveFromTextFile();
        if (nodes == null) return false;
        while(nodes.length > i) {
            insert(nodes[i]);
            ++i;
        }
        
        return true;
    }
    
    /**
     * This method clears the data in the txt file.
     */
    public void clearFile() {
      dataExchange.clearFile();
    }
    
    /**
     * Makes a call to the drawHelper method and return the complete tree visualization.
     * 
     * @return a string containing the complete tree visualization
     */
    public String drawTree() {
        return this.root == null ? "Empty Tree"
            : this.root.drawHelper(new StringBuilder(), new StringBuilder()).toString();
      }
    
    /**
     * Performs a naive removal from a binary search tree: removing the input data value that is
     * contained by an existing node in a leaf position within the tree. Balancing and
     * property-enforcing is performed in helper method. Null pointer CANNOT be removed nor passed as
     * an argument to this method.
     * 
     * @param data data to be removed from this tree
     * @return the node removed from thie tree
     * @throws NullPointerException when this tree is empty
     * @throws IllegalArgumentException when the data is not contained in the tree
     */
    public Node<Integer> remove(Integer data) throws NullPointerException, IllegalArgumentException {
      if (root == null)
        throw new NullPointerException();
      Node<Integer> nodeRemove = new Node<Integer>(data);
      return removeHelper(nodeRemove, root);
    }

    
    /**
     * Recursive helper method for finding the node to be removed in the RBT and remove that node from
     * the tree while maintaining all properties by calling the property-enforcing method.
     * 
     * @param nodeRemove node to be removed
     * @param subtree is the reference to a node within this tree in which the nodeRemove should be
     *                   located
     * @return the node removed from the tree
     */
    private Node<Integer> removeHelper(Node<Integer> nodeRemove, Node<Integer> subtree) {
        int compare = nodeRemove.data.compareTo(subtree.data);
        if (compare == 0) { // Node is found in the RBT
          enforceRBTreePropertiesAfterRemove(subtree);
          return subtree;
        }
        else if (compare < 0) { // Node should be in the left subtree
          if (subtree.leftChild == null) { // Node does not exist in this RBT
            throw new IllegalArgumentException();
          } else
            removeHelper(nodeRemove, subtree.leftChild); // Recurse on the left child
        } else { // Node should be in the right subtree
          if (subtree.rightChild == null) { // Node does not exist in this RBT
            throw new IllegalArgumentException();
          } else
            removeHelper(nodeRemove, subtree.rightChild); // Recurse on the right child
        }
        return null; // This method will not reach this line
      }
    
    /**
     * Enforces the RBT properties after a removal of either a red node or a black node
     * 
     * @param removed node that should be removed
     */  
    private void enforceRBTreePropertiesAfterRemove(Node<Integer> removed) {
         if (removed.leftChild != null && removed.rightChild != null) {
             // Case 1: removing a node, either red or black, with two children
             // Replace this node with the smallest node in the right subtree
             Node<Integer> smallestInRight = removed.rightChild;
             while (smallestInRight.leftChild != null) {
               smallestInRight = smallestInRight.leftChild;
             }
             // The data is replaced with the data stored in the smallest node in the right subtree
             // This "removes" the node (i.e. clears the data) without changing any relationship nor any
             // colors
             removed.data = smallestInRight.data;
             // Recurse on the smallest node in the right subtree to remove that node from the tree
             // Note that in this recursion, a case 3 or a case 4 should be encountered
             enforceRBTreePropertiesAfterRemove(smallestInRight);
           } else if (removed.leftChild != null || removed.rightChild != null) {
             // Case 2: removing a black node with exactly one red child (the only possible case)
             if (removed.leftChild != null) {
               // The data is replaced with the data stored in the left child
               // This "removes" the node (i.e. clears the data) without changing any relationship nor any
               // colors
               removed.data = removed.leftChild.data;
               // Recurse on the left child to remove left child node
               enforceRBTreePropertiesAfterRemove(removed.leftChild);
             } else {
               // The data is replaced with the data stored in the right child
               // This "removes" the node (i.e. clears the data) without changing any relationship nor any
               // colors
               removed.data = removed.rightChild.data;
               // Recurse on the right child to remove right child node
               // Note that in this recursion, a case 3 or a case 4 should be encountered
               enforceRBTreePropertiesAfterRemove(removed.rightChild);
             }
           } else if (!removed.isBlack) {
             // Case 3: remove a red node with no children
             if (removed.isLeftChild()) {
               removed.parent.leftChild = null;
             } else {
               removed.parent.rightChild = null;
             }
           } else {
             // Case 4: remove a black node with no children
             resolveDoubleBlack(removed);
             // Once double black is resolved in the tree, the node to be removed is safe to be removed
             if (removed.parent == null) // if the node removed is root
               root = null;              // then set root to null
             else if (removed.isLeftChild()) {
               removed.parent.leftChild = null;
             } else {
               removed.parent.rightChild = null;
             }
           }
         }
    
    /**
     * Recursively resolves the double black node situation in the RBT tree until the tree has no
     * double black tree and all properties are maintained.
     * 
     * @param doubleBlack the node that is deemed as a double black node
     */
    private void resolveDoubleBlack(Node<Integer> doubleBlack) {
        if (doubleBlack == root) return; // Root is double black thus done
        if (!doubleBlack.parent.leftChild.isBlack || !doubleBlack.parent.rightChild.isBlack) {
          // Case 1: the sibling of the double black node is red
          if (doubleBlack.isLeftChild()) {
            // Color swap double black's parent and sibling and rotate
            doubleBlack.parent.isBlack = false;
            doubleBlack.parent.rightChild.isBlack = true;
            rotate(doubleBlack.parent.rightChild, doubleBlack.parent);
          } else {
         // Color swap double black's parent and sibling and rotate
            doubleBlack.parent.isBlack = false;
            doubleBlack.parent.leftChild.isBlack = true;
            rotate(doubleBlack.parent.leftChild, doubleBlack.parent);
          }
        } else if ((doubleBlack.isLeftChild()
            && (doubleBlack.parent.rightChild.rightChild == null
                || doubleBlack.parent.rightChild.rightChild.isBlack)
            && (doubleBlack.parent.rightChild.leftChild == null
                || doubleBlack.parent.rightChild.leftChild.isBlack))
            || (!doubleBlack.isLeftChild()
                && (doubleBlack.parent.leftChild.rightChild == null
                    || doubleBlack.parent.leftChild.rightChild.isBlack)
                && (doubleBlack.parent.leftChild.leftChild == null
                    || doubleBlack.parent.leftChild.leftChild.isBlack))) {
          // Case 2: the sibling of the double black node is black, and the children of the sibling are
          // both black or null
          if (doubleBlack.isLeftChild()) {
            // Set sibling to red
            doubleBlack.parent.rightChild.isBlack = false;
          } else {
            // Set sibling to red
            doubleBlack.parent.leftChild.isBlack = false;
          }
          if (!doubleBlack.parent.isBlack) {
            // Set red parent to black and then done
            doubleBlack.parent.isBlack = true;
          } else {
            // Propagate double black up if parent is black, recursing on the double black parent
            resolveDoubleBlack(doubleBlack.parent);
          }
        } else if ((doubleBlack.isLeftChild()
        && (doubleBlack.parent.rightChild.leftChild != null
            && !doubleBlack.parent.rightChild.leftChild.isBlack)
        && (doubleBlack.parent.rightChild.rightChild == null
            || doubleBlack.parent.rightChild.rightChild.isBlack))
        || (!doubleBlack.isLeftChild()
            && (doubleBlack.parent.leftChild.rightChild != null
                && !doubleBlack.parent.leftChild.rightChild.isBlack)
            && (doubleBlack.parent.leftChild.leftChild == null
                || doubleBlack.parent.leftChild.leftChild.isBlack))) {
          // Case 3: the sibling of the double black node is black, and the same side (as double black)
          // child of the sibling is red and the other child is black or null
          // Convert this case into case 4 by rotation and color swap between sibling and its red child
          if (doubleBlack.isLeftChild()) {
            doubleBlack.parent.rightChild.isBlack = false;
            doubleBlack.parent.rightChild.leftChild.isBlack = true;
            rotate(doubleBlack.parent.rightChild.leftChild, doubleBlack.parent.rightChild);
          } else {
            doubleBlack.parent.leftChild.isBlack = false;
            doubleBlack.parent.leftChild.rightChild.isBlack = true;
            rotate(doubleBlack.parent.leftChild.rightChild, doubleBlack.parent.leftChild);
          }
          resolveDoubleBlack(doubleBlack); // Recurse on the original double black node for case 4
        } else {
          // case 4: the sibling of the double black node is black, and the opposite side (as double
          // black) child of the sibling is red
          // Change the only known red node to black, and color swap double black's parent and its
          // sibling and rotate them
          if (doubleBlack.isLeftChild()) {
            doubleBlack.parent.rightChild.rightChild.isBlack = true;
            doubleBlack.parent.rightChild.isBlack = doubleBlack.parent.isBlack;
            doubleBlack.parent.isBlack = true;
            rotate(doubleBlack.parent.rightChild, doubleBlack.parent);
          } else {
            doubleBlack.parent.leftChild.leftChild.isBlack = true;
            doubleBlack.parent.leftChild.isBlack = doubleBlack.parent.isBlack;
            doubleBlack.parent.isBlack = true;
            rotate(doubleBlack.parent.leftChild, doubleBlack.parent);
          }
        }
      }
    
}
