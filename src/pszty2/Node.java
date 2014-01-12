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
     * Konstruktor neuronu. Inicjuje jego dochodzące wagi losowymi wartościami.
     * @param numberOfWeights ilość wag dochodzących do neuronu
     */
    public Node(int numberOfWeights)
    {
        // utworzenie listy dochodzących wag o zadanej wielkości
        weights = new ArrayList<Double>( numberOfWeights );

        //inicjowanie wag wlosowymi artościami z przedziału <-1, 1>
        for ( int i = 0; i < weights.size(); ++i)
        {
            //inicjowanie wagi losową wartością
            weights.set(i, -1+2*Math.random());
        }
    }
}
