import java.util.Arrays;

public class Water-Jug-Riddle {
	static int AB = 4, AC = 5, AD = 5, BC = 3, BD = 3;
	static int minimumLength = 20;
	static int totalGoalNodes = 0;
	static int totalNumberNodes = 1;
	static int maxNumberNodes = 8000;

	public static void main(String[] args) {
		State initialState = new State(60,30,0,0,0,0,0,0,0);
		explore(initialState);
		System.out.println("The minimum Length = " + minimumLength);
		System.out.println("Total Goal Nodes = " + totalGoalNodes);
		System.out.println("Total Number of Nodes = " + totalNumberNodes);
	}
	
	/* Recursive function exploring the successors */
	public static void explore(State parents){
		if (totalNumberNodes == maxNumberNodes) return;
		if (parents.deadend()) return;
		if (parents.goal()){
			totalGoalNodes++;
			int totalLength = 0;
			if (parents.getAB() > 0) totalLength += AB;
			if (parents.getAC() > 0) totalLength += AC;
			if (parents.getAD() > 0) totalLength += AD;
			if (parents.getBC() > 0) totalLength += BC;
			if (parents.getBD() > 0) totalLength += BD;
			if (minimumLength > totalLength) minimumLength = totalLength;
			return;
		}
		totalNumberNodes++;

		/* try all possibilities */
		/* A to B */
		if (parents.getAB() < 45 && parents.getA() > 0 && parents.getB() < 30){
			State children = parents.copy();
			children.AtoB();
			explore(children);
		}
		/* A to C */
		if (parents.getAC() < 45 && parents.getA() > 0 && parents.getC() < 45){
			State children = parents.copy();
			children.AtoC();
			explore(children);
		}
		/* A to D */
		if (parents.getAD() < 45 && parents.getA() > 0 && parents.getD() < 45){
			State children = parents.copy();
			children.AtoD();
			explore(children);
		}
		/* B to A */
		if (parents.getAB() < 45 && parents.getB() > 0 && parents.getA() < 60){
			State children = parents.copy();
			children.BtoA();
			explore(children);
		}
		/* B to C */
		if (parents.getBC() < 45 && parents.getB() > 0 && parents.getC() < 45){
			State children = parents.copy();
			children.BtoC();
			explore(children);
		}
		/* B to D */
		if (parents.getBD() < 45 && parents.getB() > 0 && parents.getD() < 45){
			State children = parents.copy();
			children.BtoD();
			explore(children);
		}					
	}
}
/* Node object */
class State {
	
	private int increment = 15;
	private int[] tank; //0-A, 1-B, 2-C, 3-D
	private int[] pipe; //0-AB, 1-AC, 2-AD, 3-BC, 4-BD
	
	public State(int a, int b, int c, int d, int pAB, int pAC, int pAD, int pBC, int pBD){
		tank = new int[]{a, b, c, d};
		pipe = new int[]{pAB, pAC, pAD, pBC, pBD};
	}
	
	/* Operators */
	public void AtoB(){ tank[0]-=increment; tank[1]+=increment; pipe[0]+=increment;}
	public void AtoC(){ tank[0]-=increment; tank[2]+=increment; pipe[1]+=increment;}
	public void AtoD(){ tank[0]-=increment; tank[3]+=increment; pipe[2]+=increment;}
	public void BtoA(){ tank[1]-=increment; tank[0]+=increment; pipe[0]+=increment;}
	public void BtoC(){ tank[1]-=increment; tank[2]+=increment; pipe[3]+=increment;}
	public void BtoD(){ tank[1]-=increment; tank[3]+=increment; pipe[4]+=increment;}
	
	/* Getters */
	public int getA(){ return tank[0];}
	public int getB(){ return tank[1];}
	public int getC(){ return tank[2];}
	public int getD(){ return tank[3];}
	public int getAB(){ return pipe[0];}
	public int getAC(){ return pipe[1];}
	public int getAD(){ return pipe[2];}
	public int getBC(){ return pipe[3];}
	public int getBD(){ return pipe[4];}
	
	/* create a same State object */
	public State copy(){
		return new State(tank[0],tank[1],tank[2],tank[3],pipe[0],pipe[1],pipe[2],pipe[3],pipe[4]);
	}
	public boolean deadend(){
		int[] deadState = {45,45,45,45,45};
		return Arrays.equals(pipe, deadState);
	}
	public boolean goal(){
		int[] goalState = {0,0,45,45};
		return Arrays.equals(tank, goalState);
	}

}
