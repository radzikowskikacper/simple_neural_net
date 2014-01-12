package pszty2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Input {
	private Vector<Vector<Double>> data;
	
	short rows, cols;
	short current_row;
	short[] p;

	/**
	 * 
	 * @param fname
	 * @param row_num
	 * @param col_num
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	
	public void LoadDataFromFile(String fname)
			throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(fname));

		String line;

		Scanner sc;
		
		data = new Vector<Vector<Double>>();
		
		while((line = br.readLine()) != null) 
		{
			sc = new Scanner(line);
			
			Vector<Double> temp = new Vector<Double>();

			while(sc.hasNextDouble())
			{
				temp.add(sc.nextDouble());
				
				if(rows == 0)
					cols++;
			}
			
			data.add(temp);
			
			sc.close();
			
			rows++;
		}

		br.close();
	}
	
	public List<Double> getNextInput() throws IOException
	{		
		if(current_row >= rows)
			throw new IOException("End of rows");
	
		List<Double> ret = new ArrayList<Double>();
		
		for(short i = 0; i < p.length; ++i)
			if(p[i] == 1)
				ret.add(data.get(current_row).get(i));
		
		current_row++;
		
		return ret;
	}
	
	public void goToFirst()
	{
		current_row = 0;
	
	}
	
	public void applyPattern(short[] pattern) throws Exception
	{
		if(pattern.length > cols)
			throw new Exception("Format większy od liczby kolumn");
		
		p = pattern;
	}
}
