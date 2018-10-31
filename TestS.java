import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


///// Parameters:
///// "k", "n", "C", "Run" in TestS.java.
///// "threshold" in ChangeS.java.
/////
///// to start from a particular iteration: "kk" in RecS.java. 
///// to restrict optimization to a tag database: set the file name for FnameTagDatabase and rename function names [ranseq --> ranseqOptTags] and [ranseqCollTags --> ranseq] in Util.java 


public class TestS {

	////////// PARAMETERS /////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	private static int k	= 10		;// number of tags
	private static int n	= 20	        ;// length of the tags
	private static int Run	= 1		;// number of repeated runs
	private static int C	= 2		;// annealing parameter	
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	private static int flagInit	      = 1			;// flag for specifying an initial dataset
	private static String FnameInitialSet = "InitialSet.txt"	;// filename for the sequences of initial dataset
	private static String FnameInitialMap = "InitialMap.txt"	;// filename for the heat map of initial dataset	
	///////////////////////////////////////////////////////////////

	
	private static int numSegments = 1;
	private static int segment = 0;

	public static void main(String[] args) throws Exception{
	    
        String numSegmentsProperty = System.getProperty("SGE_TASK_LAST");
        if (numSegmentsProperty != null && numSegmentsProperty.length() > 0) {
            try {
                numSegments = Integer.parseInt(numSegmentsProperty);
            } catch (NumberFormatException nfe) {
                System.out.println("WARNING: Couldn't parse number of segments property SGE_TASK_LAST=" + numSegmentsProperty + ", use 1");
            }
        }

        String segmentProperty = System.getProperty("SGE_TASK_ID");
        if (segmentProperty != null && segmentProperty.length() > 0) {
            try {
                segment = Integer.parseInt(segmentProperty) - 1;
            } catch (NumberFormatException nfe) {
                System.out.println("WARNING: Couldn't parse segment property SGE_TASK_ID=" + segmentProperty + ", use 0");
            }
        }
		
		
	System.out.println("Started");


	int[] best_vec = new int[Run];
	char[][] dataset = new char[2*k][n];
	int[][]  maxscores = new int[2*k][2*k];	
	char[][] bestseq_mat = new char[2*k][n*Run];
	int[][]  maxscores_mat = new int[2*k][2*k*Run];
	
	for (int i = 0; i < Run; i++){
		    
		if (flagInit == 0){
				
			// Random initial dataset	
			char[][] init_dataset = Util.ranseqgen(n,k);
		    System.out.println("RC dataset of Segment_"+segment);		
		    char[][] RCdataset = Util.getRCset(init_dataset);
		    dataset = Util.list(init_dataset, RCdataset);				    
				try {
					maxscores = Util.scrdatashift(dataset,segment);
				} catch (IOException e1) {
					System.out.println("Error");
					e1.printStackTrace();
				}
		}else {	
			// Predefined initial dataset
			dataset = Util.importTagData(FnameInitialSet); 
			maxscores = Util.importTmData(FnameInitialMap);
//			maxscores = Util.scrdatashift(dataset,segment);	// if the heat map is not known		
			k = dataset.length/2;
			n = dataset[0].length;
		}
		
		// SA optimization
	    Othree o3test = new Othree();
		try {
			o3test = RecS.anneal(maxscores,k,C,dataset,segment);
		} catch (Throwable e) {
			System.out.println("Error for Segment_"+segment);
			e.printStackTrace();
		}
	    
	    int best = o3test.getBest();
	    char[][] bestseq = o3test.getBestseq();
	    int[][] Tms = o3test.getTms();

	    best_vec[i] = best;
	    Util.setSubmatrix(bestseq_mat, bestseq, 0, 2*k-1, i*n, (i+1)*n-1 );
	    Util.setSubmatrix(maxscores_mat, Tms, 0, 2*k-1, i*2*k, (i+1)*2*k-1);

	}

	int[] valindx = Util.getMin(best_vec);
	final int sim_best = valindx[0];
	int sim = valindx[1];	
	final char[][] sim_bestseq = Util.getSubmatrix(bestseq_mat, 0, 2*k-1, sim*n , (sim+1)*n-1);
	final int[][] sim_Tms = Util.getSubmatrix(maxscores_mat, 0, 2*k-1, sim*2*k , (sim+1)*2*k-1); 
	final int sim_bins = Util.countBins(sim_Tms);
	int[] valrowcol = Util.maxOffDiagVRC(sim_Tms);
	final int maxoff = valrowcol[0]; 
	final int indx1 = valrowcol[1] + 1;
	final int indx2 = valrowcol[2] + 1;
	int[] valindxmin = Util.minDiagVI(sim_Tms);
	final int mindiag = valindxmin[0];
	final int indxmin = valindxmin[1] + 1;

	System.out.println(segment+"_sim_best: " + sim_best);	
	System.out.println(segment+"_sim_bins: " + sim_bins);	
    
	System.out.println(segment+"_sim_bestseq");
    for (int ib = 0; ib < sim_bestseq.length; ib++){
    System.out.println(sim_bestseq[ib]);
    }
    System.out.println(segment+"_sim_Tms");
    for (int ih = 0; ih < sim_Tms.length; ih++){
        for (int jh = 0; jh < sim_Tms[0].length; jh++){
        	System.out.print(sim_Tms[ih][jh] + " ");        	
        }
    	System.out.println("	");	
    }
    
	// Write into the file "Dataset_"+sim_best+".txt"
    try{
            FileWriter file = new FileWriter(System.getProperty("user.dir")+"/"+segment+"_Dataset_X_"+sim_best+"_Bins_"+sim_bins+".txt");
   	    BufferedWriter out = new BufferedWriter(file);
   	    	
   	    out.write(segment+"_Dataset"); out.newLine();
   	    	for (int is = 0; is < 2*k; is++){   	    		
   	    		out.write(sim_bestseq[is]);
   	    		out.newLine();
   	    	}
   	    out.write(segment+"_Tms"); out.newLine();
	    	for (int it = 0; it < 2*k; it++){   	    		
		    	for (int jt = 0; jt < 2*k; jt++){
	    			out.write(sim_Tms[it][jt] + " ");
		    	}
		    	out.newLine();
	   	    }
				if(indx1 <= k){
				if(indx2 <= k){
			    	out.newLine();
					out.write("The strongest cross-hybrid is "+maxoff+" centigrade between tag "+indx1+" and foreign antitag "+indx2+" . ");	
				}else{
			    	out.newLine();
					out.write("The strongest cross-hybrid is "+maxoff+" centigrade between tag "+indx1+" and tag "+(indx2-k)+" . ");					
				}
			}else{
				if(indx2 <= k){
			    	out.newLine();
					out.write("The strongest cross-hybrid is "+maxoff+" centigrade between antitag "+(indx1-k)+" and foreign antitag "+indx2+" . ");	
				}else{
			    	out.newLine();
					out.write("The strongest cross-hybrid is "+maxoff+" centigrade between tag "+(indx1-k)+" and tag "+(indx2-k)+" . ");					
				}					
			}
	    	out.newLine();
			out.write("The weakest self-hybrid is "+mindiag+" centigrade for tag-target "+indxmin+" . ");				
	    	out.newLine();
			out.write("The degree of interaction, the difference between weakest self-hybrid and strongest cross-hybrid, is X = "+(sim_best)+" centigrade. ");
			if (sim_best>0){
		    	out.newLine();
		    	out.write("The number of overlapping hybrids over X="+sim_best+" bins is "+sim_bins+".");
			}
		out.close();
   	    file.close();   	    
   	}
    
    catch (Exception e1){
    System.err.println(segment+"_Error: " + e1.getMessage());
    }
    
    
	}// end of main method
}// end of Class


