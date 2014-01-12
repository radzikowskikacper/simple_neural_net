package pszty2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




public class Layer
 {
    private Layer nextLayer = null;
    public void setNextLayer(Layer l)
    {
        nextLayer  = l;
    }

    private Layer previousLayer = null;
    public void setPreviousLayer(Layer l)
    {
        previousLayer = l;
    }

    private Double output = null;
    public Double getOutput()
    {
        return output;
    }
     /**
      * Type of neurons in layer, could be hidden layer or output layer.
      */
    private LAYER_TYPE LT;

     /**
      * List of nodes contained in specific layer
      */
    private List<Node> nodeList = new ArrayList<Node>();


     /**
      * List of raw input from previous layer's output. When calculating nodes output, this has to be multiplied by weights.
      */
    private List<Double> inputList = new ArrayList<Double>();


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
    public Layer(int inputCount, int noOfNodes, LAYER_TYPE LT)
    {
        this.inputCount = inputCount;
        this.noOfNodes = noOfNodes;
        this.LT = LT;
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

     /**
      * This function calculates the layer output. It calls calculateX methods on each of its neurons(nodes) and combines it into an output vector and updates next layers input.
      */
     public void calculateOutput()
     {
         Iterator<Node> it = nodeList.iterator();
         List<Double> l = new ArrayList<Double>();

         if(LT == LAYER_TYPE.HIDDEN_LAYER)
         {
            while(it.hasNext())
            {
                 l.add(it.next().calculateHiddenOutput(inputList));
            }

            if(nextLayer != null)
                nextLayer.importInputList(l);

         }
         else if(LT == LAYER_TYPE.OUTPUT_LAYER)
         {
             while(it.hasNext())
             {
                 l.add(it.next().calculateYOutput(inputList));
                 output = l.get(0);
             }

         }



     }



 }

enum LAYER_TYPE
{
    HIDDEN_LAYER, OUTPUT_LAYER
}