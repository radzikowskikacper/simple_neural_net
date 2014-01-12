package pszty2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartek on 12.01.14.
 */


public class Controller
{
    final int numberOfLayers = 2;
    final int NO_OF_INPUTS = 2;
    Layer[] layerList = new Layer[numberOfLayers];
    int[] neuronCount = {3, 1};

    Input in = new Input();

    public Controller()
    {
        short[] a = {1, 0, 0, 1, 1};
        try
        {
            File f = new File("SN3.txt");
            if(!f.exists()) System.out.println("Dupa");
            in.LoadDataFromFile("SN3.txt");
            in.applyPattern(a);
        }
        catch(Exception e)
        {}

        layerList[0] = new Layer(NO_OF_INPUTS,neuronCount[0], LAYER_TYPE.HIDDEN_LAYER);

        layerList[1] = new Layer(neuronCount[0], neuronCount[1], LAYER_TYPE.OUTPUT_LAYER);

        /* to można zrobić lepiej, coby ręcznie nie wpisywać tylko w pętli */
        layerList[0].setNextLayer(layerList[1]);
        layerList[1].setPreviousLayer(layerList[0]);

        layerList[0].setPreviousLayer(null);
        layerList[1].setNextLayer(null);


    }

    public void FeedForward() throws Exception
    {  List<Double> row = null;
        try{
          row  = in.getNextInput();
        }
        catch(Exception e)
        {
            System.out.println("dupaaaa");
        }
        List<Double> inputValues = new ArrayList<Double>();
        inputValues.addAll(row.subList(0, NO_OF_INPUTS));

        layerList[0].importInputList(inputValues);
        for(int i=0; i < numberOfLayers; ++i)
        {
            layerList[i].calculateOutput();
        }

        System.out.println(layerList[numberOfLayers -1].getOutput().toString());

    }





    public static void main(String[] args)
    {
        Controller c = new Controller();
        try{
            c.FeedForward();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + " " + e.getStackTrace());
        }
    }




}
