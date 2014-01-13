package pszty2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Input
{

	private ArrayList<ArrayList<Double>> data;
	private int currentRow;
	private int[] p;


    public Input(String fname, int[] pattern) throws FileNotFoundException
    {
        data = new ArrayList<ArrayList<Double>>();
		p = pattern;
        Scanner sc = new Scanner( new File( fname ) );
        while( sc.hasNextLine() )
        {
            data.add( new ArrayList<Double>() );
            String s = sc.nextLine();
            Scanner temp = new Scanner( s );
			for(int i = 0; i < p.length; ++i)
				if(p[i] == 1)
					data.get(currentRow).add( Double.parseDouble( temp.next() ) );
				else
					temp.next();
            ++currentRow;
        }
        currentRow = 0;
        sc.close();
    }
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public ArrayList<Double> getNextInput() throws IOException
	{		
		if(currentRow >= data.size())
			throw new IOException("End of rows");

		return data.get( currentRow++ );
	}
	
	public void goToFirst()
	{
		currentRow = 0;
	}

	public void normalizeInput()
	{
		for ( int i = 0; i < data.size(); ++i )
		{
			for ( int j = 0; j < data.get( i ).size(); ++j)
			{
				if ( data.get( i ).get( j ).equals( (double)1 ) || data.get( i ).get( j ).equals( (double)3 ) )
				{
					data.get( i ).set( j, 0d );
				}
				else
				{
					data.get( i ).set( j, 1d );
				}
			}
		}
	}

	public int getNumberOfRows()
	{
		return data.size();
	}
}
