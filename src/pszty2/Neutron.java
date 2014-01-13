package pszty2;

import java.util.ArrayList;
import java.util.List;

public class Neutron
{
    /**
     *Wyjście z neuronu
     */
    private double output;

	/**
     * Lista przechowująca wagi dochodzące do danego neuronu
     */
    private ArrayList<Double> weights;

	/**
	 * Lista przechowująca różniece wag pomiędzy iteracjami
	 */
	private ArrayList<Double> deltaValues;

    /**
     * Bias dochodzący do danego neuronu
     */
    private double bias;

	/**
     * Konstruktor neuronu. Inicjuje jego dochodzące wagi losowymi wartościami.
     * @param numberOfWeights ilość wag dochodzących do neuronu
     */
    public Neutron(int numberOfWeights)
    {
        // utworzenie listy dochodzących wag o zadanej wielkości
        weights = new ArrayList<Double>();

		//otworzenie listy różnic wag
		deltaValues = new ArrayList<Double>();

        //bias
        bias = -1+2*Math.random();


        //inicjowanie wag wlosowymi artościami z przedziału <-1, 1>
        for ( int i = 0; i < numberOfWeights; ++i)
        {
            //inicjowanie wagi losową wartością
            weights.add(-1+2*Math.random());

			//wyczyszczenie listy różnic
			deltaValues.add((double)0);
        }
    }

	public ArrayList<Double> getWeights()
	{
		return weights;
	}

	public ArrayList<Double> getDeltaValues()
	{
		return deltaValues;
	}

	public double getBias()
	{
		return bias;
	}

	public void setBias( double bias )
	{
		this.bias = bias;
	}

	public double getOutput()
	{
		return output;
	}

	public void setOutput( double output )
	{
		this.output = output;
	}
}
