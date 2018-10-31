import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.io.*;
import java.lang.*;
//import java.lang.Object;
//import java.io.InputStream;
//import java.io.FilterInputStream;
//import java.io.BufferedInputStream;
//import com.c5corp.c5dem.Reader


public class Util {
	
	
	////////////// PARAMETERS /////////////////////////////////////////
	///////////////////////////////////////////////////////////////////	
	private static String A0 = "1E-5"		;// Initial Concentrations
	private static String B0 = "1E-5"		;// Initial Concentrations
	private static String Na = "25E-3"		;// Salt Conditions Na+
	private static String Mg = "10E-3"		;// Salt Conditions Mg++	
	private static String NucAcid = "DNA"	        ;// Type of Nucleic Acid (DNA or RNA)
	///////////////////////////////////////////////////////////////////	
	
	
	////////////// SET PATH ///////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	private static String bin_path		   = "/usr/local/bin"		;// where UNAFold is installed (the location of "hybrid2.pl" file.
	///////////////////////////////////////////////////////////////////	
	////////////// SET TAG DATABASE (OPTIONAL) ////////////////////////
	private static String FnameTagDatabase = "TagModuleTags.txt"		;// (OPTIONAL) to restrict the optimization for a tag database
	// (OPTIONAL) update the function names [ranseq --> ranseqOptTags] and [ranseqCollTags --> ranseq]
	///////////////////////////////////////////////////////////////////
	
	
	public static int[] zeros(int cols){
	int[] ary = new int[cols];
		for (int i = 0; i < cols; i++){
			ary[i] = 0;
		}
	return ary;
	}
	public static char[][] spaces(int rows, int cols){
		char[][] ary = new char[rows][cols];
			for (int i = 0; i < rows; i++){
				for (int j = 0; j < cols; j++){
				ary[i][j] = ' ';
				}
			}
		return ary;
	}
	public static int[] ones(int cols){
		int[] ary = new int[cols];
			for (int i = 0; i < cols; i++){
				ary[i] = 1;
			}
		return ary;
	}
	
	public static int[] intrement(int start, int end){		
		
		int[] array = new int[end-start+1];        

		    for (int i = start; i < end+1 ; i++)    
		    array[i-start] = i;
	return array;    
	}
	public static int[] intrementX(int rd, int ii, int l) {
		int[] ary = new int[rd-1];
		int i = 0;
		int j = 0;
		while (i < rd){			
			if ( (i != ii)  && (i != ii+l)){
				ary[j] = i;
				j = j + 1;
			}
			i = i + 1;
		}
		
	return ary;
	}

	public static int[] subarray(int[] input, int start, int end){		
		
		int[] output = new int[end-start+1];        

		    for (int i = start; i < end+1 ; i++)    
		    output[i-start] = input[i];
	return output;    
	}
	public static char[] subarray(char[] input, int start, int end){		
		
		char[] output = new char[end-start+1];        

		    for (int i = start; i < end+1 ; i++)    
		    output[i-start] = input[i];
	return output;    
	}
	
	public static int[][] getSubmatrix(int[][] input, int rowstart, int rowend, int colstart, int colend){		
		
		int[][] output = new int[rowend-rowstart+1][colend-colstart+1];        

		    for (int r = rowstart; r < rowend+1 ; r++){    
			    for (int c = colstart; c < colend+1 ; c++){    
		    	
			    output[r-rowstart][c-colstart] = input[r][c];
			    }
		    }
	return output;    
	}
	public static int[][] setSubmatrix(int[][] input, int[][] query, int rowstart, int rowend, int colstart, int colend){		
		
		    for (int r = rowstart; r < rowend+1 ; r++){    
			    for (int c = colstart; c < colend+1 ; c++){    
		    	
			    input[r][c] = query[r-rowstart][c-colstart];
			    }
		    }
	return input;    
	}
	public static char[][] getSubmatrix(char[][] input, int rowstart, int rowend, int colstart, int colend){		
		
		char[][] output = new char[rowend-rowstart+1][colend-colstart+1];        

		    for (int r = rowstart; r < rowend+1 ; r++){    
			    for (int c = colstart; c < colend+1 ; c++){    
		    	
			    output[r-rowstart][c-colstart] = input[r][c];
			    }
		    }
	return output;    
	}
	public static char[][] setSubmatrix(char[][] input, char[][] query, int rowstart, int rowend, int colstart, int colend){		
		
		    for (int r = rowstart; r < rowend+1 ; r++){    
			    for (int c = colstart; c < colend+1 ; c++){    
		    	
			    input[r][c] = query[r-rowstart][c-colstart];
			    }
		    }
	return input;    
	}
	
	public static int[] intconc(int[] ary1, int[] ary2){		
		
		int[] output = new int[ary1.length + ary2.length];
		   
		System.arraycopy(ary1, 0, output, 0, ary1.length);
		System.arraycopy(ary2, 0, output, ary1.length, ary2.length);
				
	return output;    
	}

	public static char[] charconc(char[] ary1, char[] ary2){		
		
		char[] output = new char[ary1.length + ary2.length];
		   
		System.arraycopy(ary1, 0, output, 0, ary1.length);
		System.arraycopy(ary2, 0, output, ary1.length, ary2.length);
				
	return output;    
	}
	
	public static char[][] getRCset(char[][] dataset){		
		int rows = dataset.length;
		int cols = dataset[0].length;
	    char[][] RCdataset = new char[rows][cols];
	    
	    for (int i = 0; i < rows; i++){
	    char[]	RCseq= Util.seqrcomplement(dataset[i]);
	    System.out.println(RCseq);	
	    RCdataset[i] = RCseq; 	    	
	    }
	return RCdataset;
	}
	
	public static char[][] list(char set1[][], char set2[][]) {
		int r1 = set1.length;
		int c1 = set1[0].length;
		int r2 = set2.length;
		int c2 = set2[0].length;
		
	    char[][] allset = new char[r1+r2][Math.max(c1,c2)];
	    if (c1 != c2) {
	        throw new IllegalArgumentException("Column lenghts are not equal, cannot perform");
	    }
	    for (int i = 0; i < r1; i++) {
	        allset[i] = set1[i];
	    }
	    for (int i = 0; i < r1; i++) {
	        allset[i+r1] = set2[i];
	    }
	    return allset;
	}
	public static int[][] list(int set1[][], int set2[][]) {
		int r1 = set1.length;
		int c1 = set1[0].length;
		int r2 = set2.length;
		int c2 = set2[0].length;
		
	    int[][] allset = new int[r1+r2][Math.max(c1,c2)];
	    if (c1 != c2) {
	        throw new IllegalArgumentException("Column lenghts are not equal, cannot perform");
	    }
	    for (int i = 0; i < r1; i++) {
	        allset[i] = set1[i];
	        allset[i+r1] = set2[i];
	    }
	    return allset;
	}
	public static char[][] delist(char set[][]) {
		int r = set.length;
	    char[][] rset = new char[r-1][];
	    for (int i = 0; i < r-1; i++) {
	        rset[i] = set[i];
	    }
	    return rset;
	}
	public static int[][] delist(int set[][]) {
		int r = set.length;
	    int[][] rset = new int[r-1][];
	    for (int i = 0; i < r-1; i++) {
	        rset[i] = set[i];
	    }
	    return rset;
	}
	
	public static char[][] list(char set1[][], char set2[]) {
		int r1 = set1.length;
		int c1 = set1[0].length;
		int r2 = 1;
		int c2 = set2.length;
		
	    char[][] allset = new char[r1+r2][Math.max(c1,c2)];
	    if (c1 != c2) {
	        throw new IllegalArgumentException("Column lenghts are not equal, cannot perform");
	    }
	    for (int i = 0; i < r1; i++) {
	        allset[i] = set1[i];
	    }
        allset[r2+r1-1] = set2;
	    return allset;
	}	
	public static char[][] list(char set1[], char set2[]) {
		int c1 = set1.length;
		int c2 = set2.length;
		
	    char[][] allset = new char[2][Math.max(c1,c2)];
	    if (c1 != c2) {
	        throw new IllegalArgumentException("Column lenghts are not equal, cannot perform");
	    }
	        allset[0] = set1;
	        allset[1] = set2;
	    
	    return allset;
	}	
	
	
	
	public static int[] getMax(int[] Arr){

		int[] valindx = new int[2];
		int maxval = Arr[0];
		int index = 0;
		
		for(int i = 0; i < Arr.length; i++){
			
			if(maxval < Arr[i]){
				
				maxval = Arr[i];
				index = i;
			}			
		}
	valindx[0] = maxval;
	valindx[1] = index;
	return valindx;
	}
	public static int[] getMin(int[] Arr){

		int[] valindx = new int[2];
		int maxval = Arr[0];
		int index = 0;
		
		for(int i = 0; i < Arr.length; i++){
			
			if(maxval > Arr[i]){
				
				maxval = Arr[i];
				index = i;
			}			
		}
	valindx[0] = maxval;
	valindx[1] = index;
	return valindx;
	}
	public static int[] maxMax(int[][] Mat){
		int rows = Mat.length;
		int cols = Mat[0].length;
		int[] rowsmaxval = new int[rows];
		int[] colindx = new int[rows];
		int[] valrowcol = new int[3];
	for (int r = 0; r < rows; r++){

		int value = Mat[r][0];
		int index = 0;
		
		for(int i = 0; i < cols; i++){
			
			if(value < Mat[r][i]){				
				value = Mat[r][i];
				index = i;
			}			
		}
		rowsmaxval[r] = value;
		colindx[r] = index;
	}
	int[] valindx = Util.getMax(rowsmaxval);
	valrowcol[0] = valindx[0];
	int maxrow = valindx[1];
	valrowcol[1] = maxrow;
	valrowcol[2] = colindx[maxrow];
	return valrowcol;			
	}
	public static int maxMaxUT(int[][] Mat){
		int rows = Mat.length;
		int cols = Mat[0].length;
		int maxval = Mat[0][1];
		for (int r = 0; r < rows-1; r++){
			for (int c = r+1; c < cols; c++){
				if(maxval < Mat[r][c]){
					maxval = Mat[r][c];
				}
			}
		}
	return maxval;
	}
	
	public static double getMax(String[] Arr){

		double maxval = Double.parseDouble( Arr[0]);
		
		for(int i = 0; i < Arr.length; i++){			
			if(maxval <  Double.parseDouble(Arr[i])){				
				maxval =  Double.parseDouble(Arr[i]);
			}			
		}
	return maxval;
	}
	
	public static int[] minMin(int[][] Mat){
		int rows = Mat.length;
		int cols = Mat[0].length;
		int[] rowsminval = new int[rows];
		int[] colindx = new int[rows];
		int[] valrowcol = new int[3];
		for (int r = 0; r < rows; r++){

			int value = Mat[r][0];
			int index = 0;
			
			for(int i = 0; i < cols; i++){
				
				if(value > Mat[r][i]){				
					value = Mat[r][i];
					index = i;
				}			
			}
			rowsminval[r] = value;
			colindx[r] = index;
		}
	int[] valindx = Util.getMin(rowsminval);
	valrowcol[0] = valindx[0];
	int minrow = valindx[1];
	valrowcol[1] = minrow;
	valrowcol[2] = colindx[minrow];
	return valrowcol;			
	}
	
	public static int[] diag(int[][] Mat){
		int rows = Mat.length;
		int[] diagonal = new int[rows];
		
		for (int i = 0; i < rows; i++){
			diagonal[i] = Mat[i][i];
		}		
	return diagonal;
	}
	
	public static int minDiag(int[][] Mat){
		int rows = Mat.length;
		int minval = Mat[0][0];
		for (int i = 0; i < rows; i++){
			if (minval > Mat[i][i]){
				minval = Mat[i][i];
			}
		}		
	return minval;
	}
	public static int[] minDiagVI(int[][] Mat){
		int[] valindx = new int[2];
		int rows = Mat.length;
		int minval = Mat[0][0];
		int indx = 0;
		for (int i = 0; i < rows; i++){
			if (minval > Mat[i][i]){
				minval = Mat[i][i];
				indx = i;
			}
		}		
		valindx[0] = minval;
		valindx[1] = indx;
	return valindx;
	}
	public static int maxOffDiag(int[][] Mat){
		int rows = Mat.length;
		int maxval = Mat[0][0];
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < rows; j++){
				if (i!=j){
					if (maxval < Mat[i][j]){
						maxval = Mat[i][j];
					}
				}
				
			}
		}

	return (maxval);
	}
	public static int[] maxOffDiagVRC(int[][] Mat){
		int rows = Mat.length;
		int cols = Mat[0].length;
		int[] rowsmaxval = new int[rows];
		int[] colindx4rows = new int[rows];
		int[] valrowcol = new int[3];
	
	for (int r = 0; r < rows; r++){		
		int value;
		if (r == rows-1){value = Mat[r][r-1];
		}else{ value = Mat[r][r+1];}
		int index = r+1;
		
		for(int i = 0; i < cols; i++){
			
			if(r!=i){								
				if(value < Mat[r][i]){				
					value = Mat[r][i];
					index = i;
				}
			}
		}
		rowsmaxval[r] = value;
		colindx4rows[r] = index;
	}
	int[] valindx = Util.getMax(rowsmaxval);
	valrowcol[0] = valindx[0];
	int maxrow = valindx[1];
	valrowcol[1] = maxrow;
	valrowcol[2] = colindx4rows[maxrow];
	return valrowcol;			
	}
	
	public static int diagScore(int[][] Mat){
		int rows = Mat.length;
		int cols = Mat[0].length;		
		int maxval = Mat[0][1];
		int minval = Mat[0][0];

		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				if (i!=j){
					if (maxval < Mat[i][j]){
						maxval = Mat[i][j];
					}
				}else{
					if (minval > Mat[i][j]){
						minval = Mat[i][j];
					}
				}
			}
		}

	return (maxval - minval);
	}
	


	public static int countBins(int[][] Mat){
		int rows = Mat.length;
		int cols = Mat[0].length;		
		int maxval = Mat[0][1];
		int minval = Mat[0][0];
		int nbins = 0;
		
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				if (i!=j){
					if (maxval < Mat[i][j]){
						maxval = Mat[i][j];
					}
				}else{
					if (minval > Mat[i][j]){
						minval = Mat[i][j];
					}
				}
			}
		}

		if (maxval > minval){
			for (int i = 0; i < rows; i++){
				for (int j = 0; j < cols; j++){

					if (i<rows/2) {
					
						if (j<cols/2){
							if (i!=j & Mat[i][j] >= minval){nbins++;}
						}else if(i<=j-cols/2)
							if (Mat[i][j] >= minval){nbins++;}							
						
					}else if(j<cols/2){
						
						if(i-rows/2<=j){
							if (Mat[i][j] >= minval){nbins++;}						
						}
					}
				
				}
			}
		}
		
	return nbins;
	}
	
	public static int[][] offdiag(int[][] Mat){
		int rows = Mat.length;
		int[][] l = new int[rows-1][rows];
		
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < rows; j++){
				if (i>j){
					l[i-1][j] = Mat[i][j]; 
				}
			}
		}							
	return l;
	}
	
	
	public static SizePair getSize(int[][] Mat){
		
		SizePair pr = new SizePair(0,0);
		
		int rowSize = Mat.length;
		int columnSize = Mat[0].length;
		
		pr.setRowsize(rowSize);
		pr.setColsize(columnSize);
		
	return pr;
	}
	
	public static SizePair getSize(char[][] Mat){
		
		SizePair pr = new SizePair(0,0);
		
		int rowSize = Mat.length;
		int columnSize = Mat[0].length;
		
		pr.setRowsize(rowSize);
		pr.setColsize(columnSize);
		
	return pr;
	}
	
	public static int getRowsize(int[][] Mat){
		int rowSize = Mat.length;
		return rowSize;
	}
	public static int getColsize(int[][] Mat){
		int columnSize = Mat[0].length;
		return columnSize;
	}
	public static int getRowsize(char[][] Mat){
		int rowSize = Mat.length;
		return rowSize;
	}
	public static int getColsize(char[][] Mat){
		int columnSize = Mat[0].length;
		return columnSize;
	}
	
	
	public static int scr(char[] s2, char[] s1){		
		
		int score = 0;        
		int col = s1.length;

		    for (int i = 0; i < col; i++)
		    {
		    	
		        if (s1[i] == 'G')
		            if (s2[i] == 'C')
		                score=score+1;
		        
		        else if (s1[i] == 'C')
		            if (s2[i] == 'G')
		                score=score+1;
		        
		        else if (s1[i] == 'T')
		            if (s2[i] == 'A')
		                score=score+1;
		        
		        else if (s1[i] == 'A')
		            if (s2[i] == 'T')
		                score=score+1;		        
		        
		    }	    
		    
	return score;    
	}
	
	public static char[] seqrcomplement(char[] seq){		
		
		int col = seq.length;
		char[] rc = new char[col]; 
		    for (int i = 0; i < col; i++)
		    {
		    	
		        if 		 (seq[i] == 'G'){
		        		 rc[col-1-i] = 'C';
		        
		        }else if (seq[i] == 'C'){
		                 rc[col-1-i] = 'G';
		        
		        }else if (seq[i] == 'T'){
		            	 rc[col-1-i] = 'A';
		        
		    	}else if (seq[i] == 'A'){
		            	 rc[col-1-i] = 'T';
		    	}
		    }	    
		    
	return rc;    
	}
	

	public static int scrTm(char[] seq1, char[] seq2, int segment) throws IOException{		
				
	    String current_dir = null;
		try {
		    current_dir = System.getProperty("user.dir");
		} catch (Exception e) {
			e.printStackTrace();
		}
						
			// WRITE
		     try{ // Write file s1.seq
		    	    //FileWriter file1 = new FileWriter("/tmp/"+segment+"_s1.seq");
					FileWriter file1 = new FileWriter(current_dir+"/"+segment+"_s1.seq");
		    	        BufferedWriter out1 = new BufferedWriter(file1);
		    	    out1.write(seq1);
		    	    out1.close();
		    	    file1.close();
		    	    
		    	 }
		     catch (Exception e1){
		     System.err.println(segment+"_Error: " + e1.getMessage());
		     }
		     
		     try{ // Write file s2.seq
		    	    FileWriter file2 = new FileWriter(current_dir+"/"+segment+"_s2.seq");
		    	        BufferedWriter out2 = new BufferedWriter(file2);
		    	    out2.write(seq2);
		    	    out2.close();
		    	    file2.close();
		    	 }
		     catch (Exception e2){
		     System.err.println(segment+"_Error: " + e2.getMessage());
		     }
		
					
			// IMPORT from file
		    int imported = 0;
		    if (Arrays.equals(seq1,seq2)){
						// RUN PERL
					try{
//			Process proc = Runtime.getRuntime().exec("hybrid2.pl --NA=DNA --A0=1E-5 --B0=1E-5 --sodium=25E-3 --magnesium=10E-3 -x=A -E -P /tmp/"+segment+"_s1.seq "current_dir+"/"+segment+"_s2.seq");
			Process proc = Runtime.getRuntime().exec(bin_path+"/hybrid2.pl --NA="+NucAcid+" --A0="+A0+" --B0="+B0+" --sodium="+Na+" --magnesium="+Mg+" -x=A -E -P "+segment+"_s1.seq "+segment+"_s2.seq");
      
            StreamGobbler errorGobbler = new
                StreamGobbler(proc.getErrorStream(), "ERROR");

            StreamGobbler outputGobbler = new
                StreamGobbler(proc.getInputStream(), "OUTPUT");

            errorGobbler.start();
            outputGobbler.start();

            int exitVal = proc.waitFor();
                if (exitVal != 0){
                        System.out.println(segment+"_ErrorCode: " + exitVal);
                }
            }
                    catch(Exception perl){
                       	System.err.println("Error: " + perl.getMessage());
                    } // end of PERL

		    	try{imported = Util.importTm(+segment+"_s1-"+segment+"_s1.ens.TmCp"); ///READ Tm(Cp)    
//		    	try{imported = Util.importTmfirst("/tmp/"+segment+"_s1-"+segment+"_s1.ens.TmExt2"); ///RAD Tm(Ext)        
		    	}catch(Exception err1_importTm){
		    	System.err.println(segment+"_Error: " + err1_importTm.getMessage());		    	
		    	}
		    	
			}else{ 

                       // RUN PERL
                    try{
//			Process proc = Runtime.getRuntime().exec("hybrid2.pl --NA=DNA --A0=1E-5 --B0=1E-5 --sodium=25E-3 --magnesium=10E-3 -x=A -x=B -E -P /tmp/"+segment+"_s1.seq /tmp/"+segment+"_s2.seq");
			Process proc = Runtime.getRuntime().exec(bin_path+"/hybrid2.pl --NA="+NucAcid+" --A0="+A0+" --B0="+B0+" --sodium="+Na+" --magnesium="+Mg+" -x=A -x=B -E -P "+segment+"_s1.seq "+segment+"_s2.seq");
	  
            StreamGobbler errorGobbler = new
                StreamGobbler(proc.getErrorStream(), "ERROR");

            StreamGobbler outputGobbler = new
                StreamGobbler(proc.getInputStream(), "OUTPUT");

            errorGobbler.start();
            outputGobbler.start();

            int exitVal = proc.waitFor();
                if (exitVal != 0){
                        System.out.println(segment+"_ErrorCode: " + exitVal);
                }
            }
//                      }
                    catch(Exception perl){
                       	System.err.println("Error: " + perl.getMessage());
                    } // end of PERL

			    try{imported = Util.importTm(+segment+"_s1-"+segment+"_s2.ens.TmCp"); ///READ Tm(Cp) 
//		    	try{imported = Util.importTmfirst("/tmp/"+segment+"_s1-"+segment+"_s2.ens.TmExt2"); ///RAD Tm(Ext)         
			    }catch(Exception err2_importTm){		    	
			    System.err.println(segment+"_Error: " + err2_importTm.getMessage());		    	
			    }
			}
		    
	return imported;
	} // end of scrTm unpriviledged

	public static int scrTmPriviledged(final char[] seq1, final char[] seq2) throws IOException{		
		
//		final String sequence1 = null;
//		final String sequence2 = null;
//	    final String current_dir = null;
		//	    String current_dir = null;
//		final String bin_path = null;
//		try {
			//			current_dir = String.format("%s/%s", System.getProperty("user.dir"), Util.class.getPackage().getName().replace(".", "/"));
			//		    current_dir = System.getProperty("user.dir");
		final String bin_path = "/usr/local/bin/"; // where UNAFold installed
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
					    				
			// WRITE
		     try{ // Write file s1.seq
		    	  AccessController.doPrivileged(new PrivilegedAction<BufferedWriter>() {
		             public BufferedWriter run() {
					try {
						BufferedWriter out1 = new BufferedWriter( new FileWriter("s1.seq") );
						out1.write(seq1);
						out1.close();
					} catch (IOException er) {
						er.printStackTrace();
					}

		             return null;
		             }
		         });
		    	    
		     } catch (Exception e1){
		     System.err.println("Error: " + e1.getMessage());
		     }
		     
		     try{ // Write file s1.seq
		    	  AccessController.doPrivileged(new PrivilegedAction<BufferedWriter>() {
		             public BufferedWriter run() {
					try {
						BufferedWriter out2 = new BufferedWriter( new FileWriter("s2.seq") );
						out2.write(seq2);
						out2.close();
					} catch (IOException er2) {
						er2.printStackTrace();
					}

		             return null; 
		             }
		         });		    	    
		     } catch (Exception e2){
		     System.err.println("Error: " + e2.getMessage());
		     }
			
		
		// RUN PERL
		
	AccessController.doPrivileged(new PrivilegedAction<Void>() {
	    public Void run() {		
		    try{
		    Process proc = Runtime.getRuntime().exec("perl "+bin_path+"/hybrid2.pl --NA=DNA --A0=0.000010 --B0=0.000010 --sodium=0.025 --magnesium=0.010 --tmin=0 --tmax=90 --energyOnly s1.seq s2.seq");
            StreamGobbler errorGobbler = new 
                StreamGobbler(proc.getErrorStream(), "ERROR");            
            
            StreamGobbler outputGobbler = new 
                StreamGobbler(proc.getInputStream(), "OUTPUT");
                
            errorGobbler.start();
            outputGobbler.start();
                                    
            }
		    catch(Exception perl){		    
			System.err.println("Error: " + perl.getMessage());		    	
		    } // end of PERL
        return null; // nothing to return
        }
    }); //end of privileged action
					
			// IMPORT from file
    int Imprtd = AccessController.doPrivileged(new PrivilegedAction<Integer>() {
        public Integer run() {
		    int imported = 0;
		    if (Arrays.equals(seq1, seq2)){
		    	try{imported = Util.importTm("s1-s1.ens.TmCp"); ///READ Tm(Cp)    
//		    	try{imported = Util.importTmfirst(current_dir+"/bin/s1-s1.ens.TmExt2"); ///RAD Tm(Ext)        
		    	}catch(Exception err1_importTm){
		    	}
		    	
			}else{ 
			    try{imported = Util.importTm("s1-s2.ens.TmCp"); ///READ Tm(Cp) 
//		    	try{imported = Util.importTmfirst(current_dir+"/bin/s1-s2.ens.TmExt2"); ///RAD Tm(Ext)         
			    }catch(Exception err2_importTm){		    	
			    }
			}
		    System.out.println("Tm = " + imported);
		    return imported;
        }
     });	//end of privileged action	    
    return Imprtd;
	} // end of scrTm
	
	 public static void forceClose(Closeable c)  
	    {  
	        try  
	        {  
	            if (c != null)  
	                c.close();  
	        } catch (Exception e)  
	        {  
	            e.printStackTrace();  
	        }  
	    }  
	
	
	public static int checkconseq(char[] s2, char[] s1, int X){
		
		int quit = 0; 
		int yes = 0;
		X = X - 1;
		int col1 = s1.length;
		int col2 = s2.length;
		//non circular shift
		    for (int i = 0; i < col1-X; i++){		    	
		    	for (int j = 0; j < col2-X; j++){
		    		
		    		int score = Util.scr( subarray(s1,i,i+X) , subarray(s2,j,j+X) );        
		        	if (score > X){
		            	quit = 1; break;	    
		    		}
		    	}	    
		    	if (quit == 1){
		    	yes = 1; break;	    
		    	}
		    }
	return yes;
	}
	
	public static int hasXconseq(char[] s2, char[] s1, int X){
		int yes = Math.max(checkconseq(s2,s1,X),checkconseq(s1,s2,X));
		return yes;
	}
	
	public static boolean hasSingleRepetitive(char[] s, char nuc, int X){
		boolean yes = false;
		char[] seq = new char[X];
		for (int i = 0; i < X; i++){
			seq[i] = nuc;
		}
		for (int i = 0; i < s.length-X+1; i++){
			yes = Arrays.equals(subarray(s,i,i+X-1),seq);
			if (yes){
				break;
			}
		}
		return yes;
	}
	
	public static boolean hasDoubleRepetitive(char[] s, char[] nuc, int X){
		boolean yes = false;
		char[] seq = new char[X*nuc.length];
		for (int i = 0; i < (2*X); i++){
			seq[i] = nuc[0]; i++;
			seq[i] = nuc[1];
		}		
		for (int i = 0; i < s.length-2*X+1; i++){ 
			yes = Arrays.equals(subarray(s,i,i+2*X-1),seq);
			if (yes){
				break;
			}
		}
		return yes;
	}
	
	public static int scrshift(char[] s1, char[] s2){  //
		
		int col = s1.length;		
		int[] pairings = new int[col*2];

		// Reqular pairing 
		for (int pairing = 0; pairing < col*2-1; pairing++)
		{
		    int sm = 0;
		    for (int i = Math.max(1,pairing-col+1); i < Math.min(pairing,col)+1; i++) // # of shifts
		    {
		    	int j = pairing+1 - i;
		    	if ( s1[i] == s2[j] )
		    		sm = sm + 1;		    	
		    }
		    pairings[pairing] = sm;
		}

		
		int[] ary = Util.getMax(pairings);
		int	score = ary[1];
	return score;		
	}
		
	public static int[][] scrdatashift(char[][] data, int segment) throws IOException{
		
		int rd = data.length;
		int[][] mat = new int[rd][rd];
		int[] set =  intconc( Util.intrement(rd/2,rd-1), Util.intrement(0,rd/2-1) ); 

			for (int i = 0; i < rd; i++)
			{
				System.out.println(segment+"_Processing: " + i + " of " + (rd-1));

			    for (int ji = 0; ji < rd; ji++){
			    	int j = set[ji];
			    	int score = 0;
			    	try{
			    		if (i < rd/2){
			    			if       (j >= i){ score = Util.scrTm(data[i] , data[j], segment); 
			    			}else if (j <  i){ score = mat[j][i+rd/2];
			    			}
			    		}else if (i >= rd/2){ 
			    				if 		 (j >= i){ score = Util.scrTm(data[i] , data[j], segment); // rd/2 < j : antitag-tag and antitag-antitag	    					
			    				}else if (j < rd/2) {score = mat[j][i-rd/2]; // j < rd/2 : antitag-tag 
			    				}else if (j < i){score = mat[j][i-rd/2];} // rd/2 < j < i: antitag-antitag
			    		}
			     		mat[i][ji] = score;					    
			    		}
			    	catch(Exception err_scrTm){
			    		System.err.println(segment+"_Err: " + err_scrTm.getMessage());
			    	}
//				System.out.println(segment+"_Part: " + ji + "/" + (rd-1) + " of " + i);			    	
			    }
			}
			
	return mat;	
	}
	
public static int[][] scrdatashiftComb(char[][] data, int segment) throws IOException{
		
		int rd = data.length;
		int[][] mat = new int[rd][rd];

			for (int i = 0; i < rd-1; i++)
			{
				System.out.println("Processing: " + i + " of " + (rd-1));
				mat[i][i] = 0;
			    for (int j = i+1; j < rd; j++){
			    	
			    	try{
					    int score = Util.scrTm(data[i] , data[j], segment); 
					    mat[i][j] = score;					    
					    mat[j][i] = score;					    
			    	}
			    	catch(Exception err_scrTm){
			    		System.err.println("Err: " + err_scrTm.getMessage());
			    	}
			    	
				System.out.println("Part: " + j + "/" + (rd-1) + " of " + i);			    	
			    }
			}
		    mat[rd-1][rd-1] = Util.scrTm(data[rd-1] , data[rd-1], segment);					    
	return mat;	
	}

	public static int importTm(String filename) throws IOException {
		
		FileInputStream in = new FileInputStream(filename);		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine = null, tmp;

		while ((tmp = br.readLine()) != null)
		{
		strLine = tmp;
		}

		String lastLine = strLine;

		in.close();
		br.close();
		
	    String[] elements = lastLine.split("	");

	    double Tmd = getMax(elements);
		
		long Tml = Math.round(Tmd);
		int Tmi = (int) Tml;

		return Tmi;		
	}
	
	public static int importTmfirst(String filename) throws IOException{

	FileReader reader = new FileReader(filename);	
	BufferedReader br = new BufferedReader(reader); 
	String s = br.readLine(); 
	reader.close();	
    String[] elements = s.split("	");
    String Tm = elements[0];
	double Tmd = Double.parseDouble(Tm);
	long Tml = Math.round(Tmd);
	int Tmi = (int) Tml;
	return Tmi;
	}
	
	public static int[][] read2d(String filename) throws FileNotFoundException{
	Scanner input = new Scanner (new File(filename));
	int rows = 0;
	int columns = 0;
	while(input.hasNextLine())
	{
	    rows=rows+1;
	    Scanner colReader = new Scanner(input.nextLine());
	    while(colReader.hasNextInt())
	    {
	        columns=columns+1;
	    }
	}
	int[][] a = new int[rows][columns];

	input.close();

	// read in the data
	input = new Scanner(new File(filename));
	for(int i = 0; i < rows; i++)
	{
	    for(int j = 0; j < columns; j++)
	    {
	        if(input.hasNextInt())
	        {
	            a[i][j] = input.nextInt();
	        }
	    }
	}
	input.close();
	return a;
	}

	public static char[][] ranseqgen(int n, int L) throws IOException{
		
		char[] s = new char[n];
		char[][] set = new char[L][n]; 
//		int rl = 0;
		int i = 0;
		while (i < L){
//		i = i + 1; //% for backup use    
		    int neu = 0;
//		    int count = 0;
		    while (neu == 0){		            
//		    		count = count + 1; 
//		    		System.out.println(count);
////		            % backup
//		            if (count > 1000){		                
//		                i = i-1;
//		                set = Util.delist(set); //=set[1:end-1][]; //remove last line of set
//		                rl = rl - 1;
//		                count = 0;
//		            }		        
		               s = ranseq(n);
		       neu = 1;
		       
		       double cA = 0; double cC = 0; double cG = 0; double cT = 0;
		       for (int k = 0; k < s.length; k++){
		    	   if (s[k]=='A'){
		    		   cA = cA + 1;
		    	   }else if(s[k]=='C'){
		    		   cC = cC + 1;
		    	   }else if(s[k]=='G'){
		    		   cG = cG + 1;
		    	   }else if(s[k]=='T'){
		    		   cT = cT + 1;
		    	   }		    	   
		       }
		       
//		       // A and C compositions
//		       if ((cA/(cA+cC+cG+cT)) > 0.28 || (cC/(cA+cC+cG+cT)) > 0.28 || (cC/(cA+cC+cG+cT)) < 0.22) {
//		    	   neu = 0;
//		       }
		       // GC composition should be limited b.w. 40 and 60%
		       if ((cC+cG)/(cA+cC+cG+cT) < 0.35  || (cC+cG)/(cA+cC+cG+cT) > 0.65 ){
		    	   neu = 0;
		       }		       
		       // 5 single nucleotide repeats
		       if (hasSingleRepetitive(s,'A',5) || hasSingleRepetitive(s,'C',5) || hasSingleRepetitive(s,'G',5) || hasSingleRepetitive(s,'T',5) ){
		    	   neu = 0;
		       }
		       // 4 double nucleotide repeats
		       char[] AC = {'A','C'}; char[] AG = {'A','G'}; char[] AT = {'A','T'}; char[] CA = {'C','A'}; char[] CG = {'C','G'}; char[] CT = {'C','T'}; char[] GA = {'G','A'}; char[] GC = {'G','C'}; char[] GT = {'G','T'}; char[] TA = {'T','A'}; char[] TC = {'T','C'}; char[] TG = {'T','G'}; 
		       if (hasDoubleRepetitive(s,AC,4) || hasDoubleRepetitive(s,AG,4) || hasDoubleRepetitive(s,AT,4) || hasDoubleRepetitive(s,CA,4) || hasDoubleRepetitive(s,CG,4) || hasDoubleRepetitive(s,CT,4) || hasDoubleRepetitive(s,GA,4) || hasDoubleRepetitive(s,GC,4) || hasDoubleRepetitive(s,GT,4) || hasDoubleRepetitive(s,TA,4) || hasDoubleRepetitive(s,TC,4) || hasDoubleRepetitive(s,TG,4) ){
		    	   neu = 0;
		       }		       
		       // CCCC in the 5' half of the probe
		       if (hasSingleRepetitive(subarray(s,0,(int)(Math.ceil(s.length/2)-1)),'C',4)){
		    	   neu = 0;
		       }		       
		       // AAAA in anywhere of the probe
		       if (hasSingleRepetitive(s,'A',4)){
		    	   neu = 0;
		       }
		       // GGGG in anywhere of the probe
		       if (hasSingleRepetitive(s,'G',4)){
		    	   neu = 0;
		       }
//		       if (cG < 4){    //% constraint #6   
		            for (int j = 0; j < i; j++){
//		                        if (Util.hasXconseq(s,set[j],5) == 1) {//% constraint #5
//		                        neu = 0; break;
//		                        }
		                      if (Arrays.equals(s,set[j])){
		                      neu = 0; break;
		                      }
		            }
//		       }else {neu = 0;          //% constraint #6
//		       }                    //% constraint #6
		    
		    }

		    set[i] = s;		    
		    System.out.println(set[i]);
		    i = i+1;
		}    			
	return set;    
	}	
	
	
	static Random gen = new Random();	
	
	public static int[] randi(int range, int cols){
		int[] ary = new int[cols];
		for (int c = 0; c < cols; c++){
			ary[c] = gen.nextInt(range);
		}
	return ary;
	}	

	
	// to generate random tags (should have the name as 'ranseq')
//	public static char[] ranseqOptTags(int n){	
	public static char[] ranseq(int n){

		int[] randompart = new int[n];
		randompart = Util.randi(4,n);
		char[] sc = new char[n];
		
		for ( int i = 0; i < n; i++){
			if 	   (randompart[i] == 0){
				sc[i] = 'A';
			}else if (randompart[i]==1){
				sc[i] = 'T';
			}else if (randompart[i]==2){
				sc[i] = 'C';
			}else if (randompart[i]==3){
				sc[i] = 'G';
			}
		}
	return sc;
	}
		
		// to generate restricted tags (should have the name as 'ranseq')
//		public static char[] ranseq(int n) throws IOException{
        public static char[] ranseqCollTags(int n) throws IOException{
		
		int T = 8560;
//		FileInputStream in = new FileInputStream("/share/apps/bin/"+FnameTagDatabase);		
		FileInputStream in = new FileInputStream(FnameTagDatabase);		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String tmmp = null;

		int ii = gen.nextInt(T)+1;
		
		for (int i = 0; i < ii; i++){
		tmmp = br.readLine();	
		}
		
		// String to Char
		char[] sc = tmmp.toCharArray();
	return sc;
	}

	public static char ranseq(){
		
		char s = 0;
		int randompart = gen.nextInt(4);
		
			if 	   (randompart == 0){
				s = 'A';
			}else if (randompart == 1){
				s = 'T';
			}else if (randompart == 2){
				s = 'C';
			}else if (randompart == 3){
				s = 'G';
			}
		
	return s;
	}

//	public static char[] randseq1(int n){
//		char[] lastpart = {'G','A','A','G','A','C','A','C','C','T'}; 	
//		char[] sequence = Util.charconc(Util.ranseq(n-10), lastpart);	
//	return sequence;
//	}
//	public static char[] randseq2(int n){
//		char[] lastpart = {'G','G','T','C','T','C',Util.ranseq(),'C','T'}; 	
//		char[] sequence = Util.charconc(Util.ranseq(n-9), lastpart);	
//	return sequence;	
//	}
//	public static char[] randseq3(int n){		
//		char[] firstpart = {'G','G','G','A','C'};
//		char[] lastpart = {'C','T','C','C','T','C','T'};
//		char[] sequence = Util.charconc(Util.charconc( firstpart , Util.ranseq(n-12) ) , lastpart );
//	return sequence;	
//	}	
//	public static char[] randseq4(int n){
//		char[] lastpart = {'A','C','T'}; 	
//		char[] sequence = Util.charconc(Util.ranseq(n-3), lastpart);	
//	return sequence;
//	}
//	public static char[] randseq5(int n){
//		char[] lastpart = {'T','C','T'}; 	
//		char[] sequence = Util.charconc(Util.ranseq(n-3), lastpart);	
//	return sequence;
//	}
//	public static char[] randseq6(int n){
//		char[] lastpart = {'G','C','T'}; 	
//		char[] sequence = Util.charconc(Util.ranseq(n-3), lastpart);	
//	return sequence;
//	}
//	public static char[] randseq7(int n){
//		char[] lastpart = {'C','C','T'}; 	
//		char[] sequence = Util.charconc(Util.ranseq(n-3), lastpart);	
//	return sequence;
//	}

	public static int[] fftshift(int[] vector) {
		int cols = vector.length;
		int[] torvec = new int[cols];
		for (int i = 0; i < cols; i++){
			if (i < cols/2){
			torvec[i] = vector[i+cols/2];
			}else{
			torvec[i] = vector[i-cols/2];
			}
		}
		return torvec;
	}
	public static int[][] updateCol(int[][] Mat, int ii, int[] line) {
		for (int i = 0;  i < Mat.length; i++){
			Mat[i][ii] = line[i];
		}
		return Mat;
	}
	public static int[][] copy(int[][] inp) {
		int rows = inp.length;
		int cols = inp[0].length;
		int[][] out = new int[rows][cols];
		
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				int val = inp[i][j];	
				out[i][j] = val;			
			}
		}
		
		return out;
	}
	public static char[][] copy(char[][] inp) {
		int rows = inp.length;
		int cols = inp[0].length;
		char[][] out = new char[rows][cols];
		
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				char val = inp[i][j];	
				out[i][j] = val;			
			}
		}
		
		return out;
	}
	
	public static char[][] importTagCollection(String filename, int rowstart, int rowend, int type) throws IOException{
	// type = 0 for DOWNTAG;
	// type = 1 for UPTAG (default)
	// type = 2 UPTAGs + DOWNTAGs
		
		int col = 3;
		int rows = 0;
		if (type == 0){col = 4;}

		// Predetermine the size
		Scanner input = new Scanner (new File(filename));
		while (input.hasNextLine()){rows++;}
		
		// Read file
		FileInputStream in = new FileInputStream(filename);		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String tmp = null;
		char[][] TagColl = null;
		char[][] TagCollection = null;;

		if (type == 2){
			TagColl = new char[2*rows][1];			
		}else{
			TagColl = new char[rows][1];
		}
		
		int i = 0;
		while ((tmp = br.readLine()) != null)
		{i++;	
		
		String[] elements = tmp.split("	");
		char[] Tag = elements[col].toCharArray();
		TagColl[i] = Tag; 
		
			if (type == 2){
				char[] Tag2 = elements[4].toCharArray();
				TagColl[rows+i] = Tag2; 			
			}
		}

		in.close();
		br.close();			
				
		
		if (rowstart > 0 || rowend < rows){
			TagCollection = getSubmatrix(TagColl, rowstart, rowend, 0, 0);			
		}else{
			TagCollection = TagColl;
		}
		
	return TagCollection;	
	}
	
//	public static char[][] importTags(String filename, int rowstart, int rowend, int type) throws IOException{
	public static char[][] importTagData(String filename) throws IOException{
			
//			int col = 0;
			int rows = 0;

			// Predetermine the size
//			Scanner input = new Scanner (new File(filename));
//			while (input.hasNextLine()){rows++; System.out.println("scanning"+rows);}
			
			// Read file
			FileInputStream incount = new FileInputStream(filename);		
			BufferedReader brcount = new BufferedReader(new InputStreamReader(incount));
			while (brcount.readLine()!= null){rows++; System.out.println("scanning "+rows);}
			
			FileInputStream in = new FileInputStream(filename);		
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String tmp = null;
			char[][] Tags = null;
//			char[][] Data = null;;

				Tags = new char[rows][1];
			
			int i = 0;
			while ((tmp = br.readLine()) != null){	
//			for(int i = 0; i < rows; i++){
//			tmp = br.readLine();	
			String[] elements = tmp.split("	");
			char[] tag = elements[0].toCharArray();
			Tags[i] = tag; 
			System.out.println("Tag"+i);
			i++;
			}

			in.close();
			br.close();			
								
//			Data = getSubmatrix(Tags, 0, rows-1, 0, 0);			
			
		return Tags;	
		}
	
	public static int[][] importTmData(String filename) throws IOException{
			
			int cols = 0;
			int rows = 0;

			// Predetermine the size
//			Scanner input = new Scanner (new File(filename));
//			while (input.hasNextLine()){rows++; System.out.println("scanning"+rows);}
			
			// Read file
			String tmpc = null;
			FileInputStream incount = new FileInputStream(filename);		
			BufferedReader brcount = new BufferedReader(new InputStreamReader(incount));
			while ((tmpc = brcount.readLine()) != null){
					if (rows == 0) {String[] elements = tmpc.split(" ");
						cols = elements.length;
					}
				rows++; 
				System.out.println("scanning "+rows);
				}
			
			FileInputStream in = new FileInputStream(filename);		
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String tmp = null;
			int[][] Tms = null;
			int[][] Data = null;;

				Tms = new int[rows][cols];
			
			int r = 0;
			while ((tmp = br.readLine()) != null){
//			for(int r = 0; r < rows; r++){
//			tmp = br.readLine();	
			String[] elements = tmp.split(" ");
				for(int c = 0; c < cols; c++){
					int tm = Integer.parseInt(elements[c]);
					Tms[r][c] = tm; 
				}
				System.out.println("Tm at"+r);
			r++;	
			}
			
			in.close();
			br.close();								
			
			Data = getSubmatrix(Tms, 0, rows-1, 0, cols-1);
			
		return Data;	
		}	
	
	
}// end of Util class
	
