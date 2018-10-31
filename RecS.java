import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class RecS {

	static Random gen = new Random();
	
	public static Othree anneal(int[][] maxscores, int k, int C, char[][] dataset, int segment) throws Throwable{
	
	Othree o3 = new Othree();	
	int converged = 0;
	
	///////////////////
	int kk = 1			;// initial step number
	//	int[] seqidx = {24,27,33,39,53,57,73,79,95,16,55,56,86};// index of the sequences to replace
		int[] seqidx = {39,53,73};// index of the sequences to replace	
	///////////////////
		//// tune (<) or (<=)  for writing data ( (o5rec.getSm0() <= o3.getBest())
	
	// initialize output parameters	
	o3.setBestseq(dataset);	
	System.out.println(segment+"_Initial bestseq");
    for (int ib = 0; ib < o3.getBestseq().length; ib++){
    System.out.println(o3.getBestseq()[ib]);
    }
    
    o3.setTms(maxscores);
    System.out.println(segment+"_Initial Tms");
    for (int ih = 0; ih < o3.getTms().length; ih++){
        for (int jh = 0; jh < o3.getTms()[0].length; jh++){
        	System.out.print(o3.getTms()[ih][jh] + " ");        	
        }
    	System.out.println("	");	
    }
    
	int sm0 = Util.diagScore(maxscores); 
	int inBest = Util.diagScore(maxscores);
	o3.setBest( sm0 );
	System.out.println(segment+"_Initial best: " + o3.getBest());

	int bin0 = Util.countBins(maxscores); 
	o3.setBestBins( bin0 );
	System.out.println(segment+"_Initial number of overlapping bins: " + o3.getBestBins());

	
    try{
	    FileWriter file = new FileWriter(System.getProperty("user.dir")+"/"+segment+"_bestsofar_X_"+sm0+"_Bins_"+bin0+".txt");
   	    BufferedWriter out = new BufferedWriter(file);
   	    	
   	    out.write(segment+"_Best dataset so far"); out.newLine();
   	    	for (int is = 0; is < 2*k; is++){   	    		
   	    		out.write(o3.getBestseq()[is]);
   	    		out.newLine();
   	    	}
   	    out.write(segment+"_Tms"); out.newLine();
	    	for (int it = 0; it < 2*k; it++){   	    		
		    	for (int jt = 0; jt < 2*k; jt++){
	    			out.write(o3.getTms()[it][jt] + " ");
		    	}
		    	out.newLine();
	   	    }
			out.close();
	   	    file.close();   
	    }
	    catch (Exception e1){
	        System.err.println(segment+"_Error: " + e1.getMessage());
	    }	
	        
	while (converged == 0){
		int ii = 0;
		// pick randomly with prob. 0.1
		if (gen.nextDouble() < 0.1){ 
			ii = gen.nextInt(k);     //%pick which sequences to change randomly b.w. (0,5)
		}else{
		//	pick from the seqidx with prob. 0.9
			ii = seqidx[gen.nextInt(seqidx.length)]; 
		}
		
	    System.out.println(kk + "th step of optimization for sequence "+ii+" in Segment_"+segment);	    
	    Ofive o5rec = ChangeS.anneal(ii,sm0,bin0,maxscores,C,kk,dataset,inBest,segment); 
	    
	    //Update output parameters if bin0 < bestBins
	    if ( (o5rec.getSm0() <= o3.getBest()) || ( (o5rec.getSm0() == o3.getBest()) && (o5rec.getBin0() < o3.getBestBins()) ) ){
	    	o3.setBestBins( o5rec.getBin0());
	    	o3.setBest( o5rec.getSm0());
	    	o3.setBestseq( o5rec.getDataset() );
	    	o3.setTms( o5rec.getMaxscores() );
	        
	    	System.out.println(segment+"_the new best: " + o3.getBest());	        
	        
		    try{
			    FileWriter file = new FileWriter(System.getProperty("user.dir")+"/"+segment+"_bestsofar_X_"+o5rec.getSm0()+"_Bins_"+o5rec.getBin0()+".txt");
		   	    BufferedWriter out = new BufferedWriter(file);
		   	    	
		   	    out.write(segment+"_Best dataset so far"); out.newLine();
		   	    	for (int is = 0; is < 2*k; is++){   	    		
		   	    		out.write(o3.getBestseq()[is]);
		   	    		out.newLine();
		   	    	}
		   	    out.write(segment+"_Tms"); out.newLine();
			    	for (int it = 0; it < 2*k; it++){   	    		
				    	for (int jt = 0; jt < 2*k; jt++){
			    			out.write(o3.getTms()[it][jt] + " ");
				    	}
				    	out.newLine();
			   	    }
					out.close();
			   	    file.close();   
			    }
			    catch (Exception e1){
			        System.err.println(segment+"_Error: " + e1.getMessage());
			    }	
	        
	    }
	    else{
	    	// Best set is not updated
	    }
	    sm0 = o5rec.getSm0();
	    bin0 = o5rec.getBin0();
	    kk = o5rec.getKk();
	    dataset = o5rec.getDataset();
	    maxscores = o5rec.getMaxscores();
	    converged = o5rec.getConverged();
	    
	}// end of while loop
		
	System.out.println(segment+"_Ultimate best: " + o3.getBest());
	System.out.println(segment+"_Ultimate bestseq");
    for (int ib = 0; ib < o3.getBestseq().length; ib++){
    System.out.println(o3.getBestseq()[ib]);
    }
	System.out.println(segment+"_Ultimate Tms");
    for (int ih = 0; ih < o3.getTms().length; ih++){
        for (int jh = 0; jh < o3.getTms()[0].length; jh++){
        	System.out.print(o3.getTms()[ih][jh] + " ");        	
        }
    	System.out.println("	");	
    }
    
    System.out.println(segment+"_returning output");
    
    return o3;	
	}
}
