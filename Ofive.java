
public class Ofive {
		
	private int sm0;
	private int bin0;
	private int kk;
	private char[][] dataset;	
	private int[][] maxscores;
	private int converged;
	
	public Ofive(int sm, int bn, int k, char[][] ds, int[][] ms, int c){

		sm0 = sm;
		bin0 = bn;
		kk = k;
		dataset = ds;
		maxscores = ms;
		converged = c;
	}

	public Ofive(){
			
	}

	
	public int getSm0() {
		return sm0;
	}
	public int getBin0() {
		return bin0;
	}
	public int getKk() {
		return kk;
	}
	public char[][] getDataset() {
		return dataset;
	}
	public int[][] getMaxscores() {
		return maxscores;
	}
	public int getConverged() {
		return converged;	
	}
	public void setSm0(int sm0) {
		this.sm0 = sm0 ;
	}
	public void setBin0(int bin0) {
		this.bin0 = bin0 ;
	}
	public void setKk(int kk) {
		this.kk= kk ;
	}
	public void setDataset(char[][] dataset) {
		this.dataset = dataset;
	}
	public void setMaxscores(int[][]  maxscores) {
		this.maxscores = maxscores ;
	}
	public void setConverged(int converged) {
		this.converged = converged;	
	}
	
	
}

 