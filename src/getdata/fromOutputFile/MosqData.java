package getdata.fromOutputFile;

public class MosqData {
	double Evals;
	double Prob;
	public double getEvals() {
		return Evals;
	}
	public void setEvals(double evals) {
		Evals = evals;
	}
	public double getProb() {
		return Prob;
	}
	public void setProb(double prob) {
		Prob = prob;
	}
	@Override
	public String toString() {
		return "MosqData [Evals=" + Evals + ", Prob=" + Prob + "]";
	}

	
	
	
}
