package pszty2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartek on 12.01.14.
 */


public class Controller
{
    final int numberOfLayers = 2;
    final int numberOfInputs = 2;
    Layer[] layerList = new Layer[numberOfLayers];
    int[] neuronCount = {3, 1};

    private Input in;

    public Controller()
    {
        try
        {
            in = new Input("dane.txt");
        }
        catch(FileNotFoundException e)
        {
            System.out.println( "File not found" );
        }
        int[] a = {1, 0, 0, 1, 0};
        in.applyPattern( a );

        layerList[0] = new Layer(numberOfInputs, neuronCount[0], LAYER_TYPE.HIDDEN_LAYER);

        layerList[1] = new Layer(neuronCount[0], neuronCount[1], LAYER_TYPE.OUTPUT_LAYER);

        /* to można zrobić lepiej, coby ręcznie nie wpisywać tylko w pętli */
        layerList[0].setNextLayer(layerList[1]);
        layerList[1].setPreviousLayer(layerList[0]);

        layerList[0].setPreviousLayer(null);
        layerList[1].setNextLayer(null);


    }

    public void FeedForward() throws IOException
    {
        List<Double> row = null;
        try{
          row  = in.getNextInput();
        }
        catch(IOException e)
        {
            System.out.println("Koniec danych");
            return;
        }

        layerList[0].importInputList(row);
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
            System.out.println("Chuj");
        }
    }




}
