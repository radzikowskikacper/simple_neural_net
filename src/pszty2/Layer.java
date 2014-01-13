package pszty2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;




public class Layer
 {
	 /**
      * List of nodes contained in specific layer
      */
    private ArrayList<Neutron> neutrons;

	 /**
      * List of raw input from previous layer's output. When calculating nodes output, this has to be multiplied by weights.
      */
    private ArrayList<Double> input;


     /**
      * Public layer constructor initialising an array of nodes, throws IllegalArgumentException when inputCount out of range.
      * @param numberOfInputs Number of nodes from previous layer - which equals to output count from previous layer. Has to be greater than 0.
      * @param numberOfNeutrons Number of nodes created in this layer.
      * @exception java.lang.IllegalArgumentException
      */
    public Layer(int numberOfInputs, int numberOfNeutrons)
	{
        neutrons = new ArrayList<Neutron>();
        input = new ArrayList<Double>();
        for(int i=0; i < numberOfNeutrons; i++)
        {
            neutrons.add(new Neutron(numberOfInputs));
        }

    }

	public void calculate()
	{
		for( int i = 0; i < neutrons.size(); ++i )
		{
			double sum = 0;
			for ( int j = 0; j < neutrons.get( i ).getWeights().size(); ++j )
			{
				sum += neutrons.get( i ).getWeights().get( j ) * input.get( j ); //apply input * weight
			}
			sum += neutrons.get( i ).getBias();

			neutrons.get( i ).setOutput( 1/(1+Math.exp( -sum )) );
		}
	}

	public void loadInput(ArrayList<Double> input)
	{
		 this.input = input;
	}

	 public ArrayList<Neutron> getNeutrons()
	 {
		 return neutrons;
	 }

	 public ArrayList<Double> getInput()
	 {
		 return input;
	 }
 }