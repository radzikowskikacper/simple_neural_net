package pszty2;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Layer
 {
     /**
      * List of nodes contained in specific layer
      */
    private List<Node> nodeList = new ArrayList<Node>();


     /**
      * List of raw input from previous layer's output. When calculating nodes output, this has to be multiplied by weights.
      */
    private List<Double> inputList = null;


     /**
      * Variable needed when initialising nodes. Nodes need to have specified number of input weights.
      */
    private int inputCount;


     /**
      *  Needed when initialising nodes. May be useful later.
      */
    private int noOfNodes;


     /**
      * Public layer constructor initialising an array of nodes, throws IllegalArgumentException when inputCount out of range.
      * @param inputCount Number of nodes from previous layer - which equals to output count from previous layer. Has to be greater than 0.
      * @param noOfNodes Number of nodes created in this layer.
      * @exception java.lang.IllegalArgumentException
      */
    public Layer(int inputCount, int noOfNodes)
    {
        this.inputCount = inputCount;
        this.noOfNodes = noOfNodes;

        if(inputCount <= 0) throw new IllegalArgumentException();

        for(int i=0; i < noOfNodes; i++)
        {
            nodeList.add(new Node(inputCount));
        }

    }

     /**
      * This imports input list calculated in previous layer. This function should be called by a previous layer after successful output calculation.
      * @param listToImport List of previous layer's output. It's size must be equal to input count provided in constructor parameter inputCount.
      * @exception  java.lang.IllegalArgumentException
      *
      */
     public void importInputList(List<Double> listToImport)
     {
         if(listToImport.size() != inputCount) throw new IllegalArgumentException("imported size list does not match input count of current layer");

         inputList.clear();
         inputList.addAll(listToImport);

     }



 }