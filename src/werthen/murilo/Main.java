package werthen.murilo;

public class Main {

	public static void main (String[] args) {

		// Round Robin: alpha = 50; quantum = 10.
		RoundRobin rr = new RoundRobin(50, 10);
		rr.startScheduling();
	}
}
