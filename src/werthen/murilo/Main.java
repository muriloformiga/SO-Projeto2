package werthen.murilo;

public class Main {

	public static void main (String[] args) {

		// Round Robin: alpha = 50; quantum = 10; memory capacity = 20.
		LRU lru = new LRU(50, 10, 30);
		lru.startScheduling();
	}
}
