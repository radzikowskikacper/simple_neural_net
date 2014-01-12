package pszty2;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    /**
     *Wyjście z neuronu
     */
    private double output;

    /**
     * Lista przechowująca wagi dochodzące do danego neuronu
     */
    private List<Double> weights;

    /**
     * Bias dochodzący do danego neuronu
     */
    private double bias;

    /**
     * Suma wag i weujść dochodzących do neuronu
     */
    private double sum;

    /**
     * Konstruktor neuronu. Inicjuje jego dochodzące wagi losowymi wartościami.
     * @param numberOfWeights ilość wag dochodzących do neuronu
     */
    public Node(int numberOfWeights)
    {
        // utworzenie listy dochodzących wag o zadanej wielkości
        weights = new ArrayList<Double>( numberOfWeights );

        //bias
        bias = -1+2*Math.random();

        //inicjowanie wag wlosowymi artościami z przedziału <-1, 1>
        for ( int i = 0; i < weights.size(); ++i)
        {
            //inicjowanie wagi losową wartością
            weights.set(i, -1+2*Math.random());
        }
    }

    /**
     * Oblicza wyjście końcowe
     * @param input lista z dochodzącymi wagami do danego neuronu
     * @return wyjście z sieci neuronowej
     */
    public Double calculateYOutput(List<Double> input)
    {
        return calculateSum( input );
    }

    /**
     * Oblicza wyjście neuronu w warstwie ukrytej
     * @param input lista z dochodzącymi wagami do danego neuronu
     * @return wyjście z neuronu
     */
    public Double calculateHiddenOutput( List<Double> input )
    {
        return activationFunc( calculateSum( input ) );
    }

    /**
     * Funkcja aktywacji dla warstwy ukrytej
     * @param sum suma iloczynu wag i wejść
     * @return wartość funkcji aktywacji dla danego neuronu
     */
    private Double activationFunc( double sum )
    {
        return 1/(1+Math.exp( -sum ));
    }

    /**
     * Suma wag
     * @param input lista z wagami
     * @return suma wag
     */
    private Double calculateSum(List<Double> input)
    {
        sum = bias;
        for (int i = 0; i < weights.size(); ++i)
        {
            sum += input.get( i ) * weights.get( i );
        }

        return sum;
    }
}
