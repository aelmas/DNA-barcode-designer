//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.util.Date;
import java.util.Random;
import java.util.*;

public class ChangeS {

	////////// PARAMETERS /////////////////////
	private static double threshold = 0.01	;// PT threshold for convergence
	///////////////////////////////////////////
	
	static Random gen = new Random();	
	static 	Ofive o5 = new Ofive();
	
	public static Ofive anneal(int ii, int sm0, int bin0, int[][] maxscores, int C, int kk, char[][] dataset, int inBest, int segment) throws Throwable{
		
//		int tolerance = 5;
		int converged = 0;
		int transition = 0;
		int rd = dataset.length;
		int l = rd/2;
		int n = dataset[0].length;	
		char[] sr = new char[n];
		char[][] srset = new char[1][n];
		int[] dir_dir_rev = Util.zeros(rd);
		int[] rev_dir_rev = Util.zeros(rd);
		    kk = kk+1; //annealing step
		    int neo = 0;
		    while (neo == 0){
//		           sr = Util.ranseq(n);
		    	   System.out.println("New sequence: ");
		    	   srset = Util.ranseqgen(n,1); 
		    	   sr = srset[0];
//		    	   System.out.println(sr);		    	   
		           		neo = 1;
		                for (int j = 0; j < rd; j++){
		                   if (Arrays.equals(sr,dataset[j])){ 
		                        neo = 0; break;
		                    }
		                }
		    } // new unique sequence is drawn
		        
		        int[] notii = Util.intrementX(rd,ii,l);
		        for (int lp = 0; lp < notii.length; lp++){
		        	int ind = notii[lp];         
		            dir_dir_rev[ind] = Util.scrTm(sr,dataset[ind], segment);
		            rev_dir_rev[ind] = Util.scrTm(Util.seqrcomplement(sr), dataset[ind], segment);
		        }
	        dir_dir_rev[ii] = Util.scrTm(sr,sr,segment); 
	        int maxbp = Util.scrTm(sr,Util.seqrcomplement(sr),segment);
	        dir_dir_rev[ii+l] = maxbp;
	        rev_dir_rev[ii] = maxbp;
	        rev_dir_rev[ii+l] = Util.scrTm(Util.seqrcomplement(sr), Util.seqrcomplement(sr),segment);
	        int[] dir_rev_dir = Util.fftshift(dir_dir_rev);
	        int[] rev_rev_dir = Util.fftshift(rev_dir_rev);

	        int[][] newmaxscores = Util.copy(maxscores);  
	        newmaxscores[ii] = dir_rev_dir; 
	        newmaxscores[ii+l] = rev_rev_dir;
	        Util.updateCol(newmaxscores,ii,rev_dir_rev);  
	        Util.updateCol(newmaxscores,ii+l,dir_dir_rev);  

	        int newscr = Util.diagScore(newmaxscores);
	        System.out.println(segment+"_sm0: " + sm0);
	        System.out.println(segment+"_newscr: " + newscr);
	        
        	int newbins = Util.countBins(newmaxscores);	        	        
        	System.out.println(segment+"_bin0: " + bin0);
        	System.out.println(segment+"_newbins: " + newbins);	 
        	
			char[][] newdataset = Util.copy(dataset);
			newdataset[ii] = sr;
	        newdataset[ii+l] = Util.seqrcomplement(sr);	

//	        int difference = newscr - inBest;		
		
	        // Simulated Annealing Probability
	        double Tk = (C/Math.log(1+kk));
	        double Pt = 0;	
			double PT = 0;
	        
			double ran_num = gen.nextDouble();
			PT = Math.exp(-Math.max(0,2*(newscr-sm0))/Tk);
	        if (PT > ran_num){
	        	Pt = Math.exp(-Math.max(0,Math.ceil((double)(newbins-bin0)/(double)10))/Tk); // bin enhancement
	        }
                System.out.println(segment+"_PT: " + PT);
	        System.out.println(segment+"_Pt: " + Pt);
	        
	        // Transition
//	        double ran_num = gen.nextDouble();
	        if (Pt > ran_num){
	        	transition = 1;
	        }else{ transition = 0;
	        }
	        System.out.println("Transition: " + transition);
	        
	        if (kk>Math.pow( threshold, (-C) )){converged = 1;} // PT convergence satisfied via 1˚C difference in score

	if  (transition == 0){ 
	    o5.setSm0(sm0);
	    o5.setBin0(bin0);
	    o5.setMaxscores(maxscores);
		o5.setDataset(dataset);

	}else{  		
	        System.out.println(ii + "th sequence is changed in Segment_"+segment);	        
		    o5.setSm0(newscr);
		    o5.setBin0(newbins);
		    o5.setMaxscores(newmaxscores);
			o5.setDataset(newdataset);
	}

    o5.setKk(kk);
    o5.setConverged(converged);	
    
	return o5;
	}	
}
