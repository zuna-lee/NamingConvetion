package zuna.refactoring.similarity;

import zuna.model.MyClass;

public class Relationship {
	
	MyClass c1;
	MyClass c2;
	
	double input = 0.0;
	double output = 0.0;
	double avg = 0.0;
	
	
	public double getInput() {
		return input;
	}
	public void setInput(double input) {
		this.input = input;
	}
	public double getOutput() {
		return output;
	}
	public void setOutput(double output) {
		this.output = output;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	
}
