package werthen.murilo;

public class Main {

	public static void main (String[] args) {

		// Round Robin: alpha = 50; quantum = 10; memory capacity = 20.
		LRU lru = new LRU(50, 10, 30);
		lru.startScheduling();
		
		// *** Total de Faltas: 30828 : 264026 *** 20
		// *** Total de Faltas: 30762 : 265116 *** 30
		
		// *** Total de Faltas: 61014 : 558540 *** 20
		// *** Total de Faltas: 60971 : 559010 *** 30
		
		// *** Total de Faltas: 136620 : 1394575 *** 20
		// *** Total de Faltas: 136595 : 1394703 *** 30
		
		// *** Total de Faltas: 271875 : 2827140 *** 20
		// *** Total de Faltas: 271865 : 2827141 *** 30
		
		Otimo otm = new Otimo(50, 10, 20);
		otm.startScheduling();
	}
}
