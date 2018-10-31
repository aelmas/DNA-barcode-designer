
public class Othree {
		
	private int best;
	private int bestbins;	
	private char[][] bestseq;
	private int[][] Tms;
	
	public Othree(int bst, int bstbns, char[][] bstsq, int[][] tm){

		best = bst;
		bestseq = bstsq;
		Tms = tm;	
		bestbins = bstbns;
	}

	public Othree(){
			
	}

	
	public int getBest() {
		return best;
	}
	public int getBestBins() {
		return bestbins;
	}
	public char[][] getBestseq() {
		return bestseq;
	}
	public int[][] getTms() {
		return Tms;
	}

	public void setBest(int best) {
		this.best = best;
	}
	public void setBestBins(int bestbins) {
		this.bestbins = bestbins;
	}
	public void setBestseq(char[][] bestseq) {
		this.bestseq = bestseq;
	}
	public void setTms(int[][] Tms) {
		this.Tms = Tms;
	}
	
	
}

 