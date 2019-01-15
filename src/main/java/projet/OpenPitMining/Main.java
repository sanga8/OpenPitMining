package projet.OpenPitMining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InputStream test = new FileInputStream(new File("test.txt"));
	
		BufferedReader ar = new BufferedReader(new InputStreamReader(test));
		int i = 0;
		int j = 0;

		for (String actualLine = ar.readLine(); actualLine != null; actualLine = ar.readLine()) {
			String[] coords = actualLine.split(" ");
			i++;
			j=coords.length;
		}

		//System.out.println("i " + i);
		//System.out.println("j " + j);
		
		String network[][] = new String[i][j];
		int x=0;
		
		InputStream is = new FileInputStream(new File("test.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		for (String actualLine = br.readLine(); actualLine != null; actualLine = br.readLine()) {
			String [] coords = actualLine.split(" ");
			for(int y=0;y<j;y++) {
				network[x][y]=coords[y];
			}
			x++;
		}
		
		for(int i1=0;i1<network.length;i1++) {
			for(int j1=0;j1<network[i1].length;j1++) {
				System.out.print(network[i1][j1]+" ");
			}
			System.out.println();
		}

	}

}
