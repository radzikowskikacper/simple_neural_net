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
	final int numberOfInputs = 2;
	final int numberOfNeutronsInFirstHiddenLayer = 3;
    private Input inputData;
	private ArrayList<Layer> hiddenLayers;
	private Layer outputLayer;
	private Input desiredoutput;


    public Controller()
    {
        try
        {
            inputData = new Input("dane.txt", new int[]{1, 0, 0, 1, 0});
			desiredoutput = new Input( "dane.txt", new int[]{0, 0, 0, 0, 1} );
        }
        catch(FileNotFoundException e)
        {
            System.out.println( "File not found" );
        }
		desiredoutput.normalizeInput();
		hiddenLayers = new ArrayList<Layer>(  );
		hiddenLayers.add(new Layer(numberOfInputs, numberOfNeutronsInFirstHiddenLayer));
		outputLayer = new Layer( numberOfNeutronsInFirstHiddenLayer, 1 );
    }

	/**
	 * Funkcja wyliczająca wynik na podstawie danych wejściowych
	 * @return wyjście sieci neuronowej
	 * @throws IOException
	 */
    public Double forward(Input in) throws IOException
    {
        ArrayList<Double> row = null;
        try{
          row  = in.getNextInput();
        }
        catch(IOException e)
        {
            System.out.println("Koniec danych");
            return -2d;
        }
		hiddenLayers.get( 0 ).loadInput( row );
		hiddenLayers.get( 0 ).calculate();

		ArrayList<Double> output = new ArrayList<Double>(  );

		if( hiddenLayers.size() > 1 )
		{
			for ( int i = 0; i < hiddenLayers.size()-1; ++i )
			{
				for ( int j = 0; j < hiddenLayers.get( i ).getNeutrons().size(); ++j)
				{
					output.add( hiddenLayers.get( i ).getNeutrons().get( j ).getOutput() );
				}
				hiddenLayers.get( i+1 ).loadInput( output );
				hiddenLayers.get( i+1 ).calculate();
				output.clear();
			}
		}
			for ( int j = 0; j < hiddenLayers.get( hiddenLayers.size()-1 ).getNeutrons().size(); ++j)
			{
				output.add( hiddenLayers.get( hiddenLayers.size()-1 ).getNeutrons().get( j ).getOutput() );
			}
			outputLayer.loadInput( output );
			outputLayer.calculate();

		return getNetworkOutput();
    }

	/**
	 * Funkcja ucząca. Wpuszcza do wejścia sieci dane  i na podstawie wyjscia koryguje wagi pomiędzy neuronami ( algorytm wstecznej propagacji błędu )
	 * @return średni błąd kwadratowy
	 */
	public Double train(double alpha, double momentum) throws IOException
	{
		double quadError = 0;
		double localError = 0;
		double sum = 0;
		double tempSum = 0;
		double delta = 0;
		double updatedDelta = 0;
		double output = 0;
		double desiredOutput = desiredoutput.getNextInput().get( 0 );

		forward( inputData );

		//wsteczna propagacja dla warstwy wyjściowej
		output = getNetworkOutput();
		localError = (desiredOutput - output) * output * (1 - output);
		quadError = (desiredOutput - output) * (desiredOutput - output);

		for ( int i = 0; i < outputLayer.getNeutrons().get( 0 ).getWeights().size(); ++i )
		{
			delta = outputLayer.getNeutrons().get( 0 ).getDeltaValues().get( i );
			updatedDelta = alpha * localError * outputLayer.getInput().get( i ) + delta * momentum;
			outputLayer.getNeutrons().get( 0 ).getWeights().set( i, outputLayer.getNeutrons().get( 0 ).getWeights().get( i )+updatedDelta );
			outputLayer.getNeutrons().get( 0 ).getDeltaValues().set( i, updatedDelta );
			sum += outputLayer.getNeutrons().get( 0 ).getWeights().get( i )*localError;
		}
		outputLayer.getNeutrons().get( 0 ).setBias( outputLayer.getNeutrons().get( 0 ).getBias() + alpha * localError );


		//kontynuacja wstecznej propagacji dla warstw ukrytych
		for ( int i = hiddenLayers.size() - 1; i >= 0; --i )
		{
			for ( int j = 0; j < hiddenLayers.get( i ).getNeutrons().size(); ++j)
			{
				output = hiddenLayers.get( i ).getNeutrons().get( j ).getOutput();
				localError = output * (1-output) * sum;
				for ( int k = 0; k < hiddenLayers.get( i ).getNeutrons().get( j ).getWeights().size(); ++k )
				{
					delta = hiddenLayers.get( i ).getNeutrons().get( j ).getDeltaValues().get( k );
					updatedDelta = alpha * localError * hiddenLayers.get( i ).getInput().get( k ) + delta * momentum;
					hiddenLayers.get( i ).getNeutrons().get( j ).getWeights().set( k, hiddenLayers.get( i ).getNeutrons().get( j ).getWeights().get( k )+updatedDelta );
					hiddenLayers.get( i ).getNeutrons().get( j ).getDeltaValues().set( k, updatedDelta );
					tempSum+=hiddenLayers.get( i ).getNeutrons().get( j ).getWeights().get( k ) * localError;
				}
				hiddenLayers.get( i ).getNeutrons().get( j ).setBias( hiddenLayers.get( i ).getNeutrons().get( j ).getBias() + alpha * localError );
			}
			sum = tempSum;
			tempSum = 0;
		}

		//return the general error divided by 2
		return quadError / 2;
	}

	public Double getNetworkOutput()
	{
		return outputLayer.getNeutrons().get( 0 ).getOutput();
	}

	public Input getInputData()
	{
		return inputData;
	}

	public Input getDesiredoutput()
	{
		return desiredoutput;
	}

	public static void main(String[] args) throws IOException
	{
        Controller c = new Controller();
		for ( int j = 0; j < 3000; ++j)
		{
			for(int i =0; i < c.getInputData().getNumberOfRows(); ++i)
			{
				c.train( 0.3, 0.9 );
			}
			c.getInputData().goToFirst();
			c.getDesiredoutput().goToFirst();
		}

		c.getInputData().goToFirst();
		System.out.println(c.forward( c.getInputData() ));
		System.out.println( c.forward( c.getInputData() ) );
		System.out.println(c.forward( c.getInputData() ));
		System.out.println( c.forward( c.getInputData() ) );
		System.out.println(c.forward( c.getInputData() ));
		System.out.println( c.forward( c.getInputData() ) );
		System.out.println(c.forward( c.getInputData() ));
		System.out.println( c.forward( c.getInputData() ) );
		System.out.println(c.forward( c.getInputData() ));
		System.out.println( c.forward( c.getInputData() ) );

    }




}
