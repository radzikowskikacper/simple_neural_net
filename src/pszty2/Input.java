package pszty2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Input
{

	private Vector<Vector<Double>> data;
	int currentRow;
	int[] p;


    public Input(String fname) throws FileNotFoundException
    {
        data = new Vector<Vector<Double>>();
        Scanner sc = new Scanner( new File( fname ) );
        while( sc.hasNextLine() )
        {
            data.add( new Vector<Double>() );
            String s = sc.nextLine();
            Scanner temp = new Scanner( s );
            while( temp.hasNext() )
            {
                data.get(currentRow).add( Double.parseDouble( temp.next() ) );
            }
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
	public List<Double> getNextInput() throws IOException
	{		
		if(currentRow >= data.size())
			throw new IOException("End of rows");
	
		List<Double> ret = new ArrayList<Double>();
		
		for(int i = 0; i < p.length; ++i)
			if(p[i] == 1)
				ret.add(data.get(currentRow).get(i));
		
		++currentRow;
		
		return ret;
	}
	
	public void goToFirst()
	{
		currentRow = 0;
	}
	
	/**
	 * 
	 * @param pattern
	 * @throws Exception
	 */
	public void applyPattern(int[] pattern)
	{
		p = pattern;
	}
}
