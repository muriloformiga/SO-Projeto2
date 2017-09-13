package werthen.murilo;

public class RoundRobin extends Scheduling {

	private int quantum;
	private int countQuantum = 0;

	public RoundRobin (int alpha, int quantum) {
		super(alpha);
		this.quantum = quantum;
	}

	@Override
	public void prepareProcess () {

		if (countQuantum % this.quantum == 0 || super.changeProcess) {
			if (super.changeProcess) {
				countQuantum = 0;
				super.prepareProcess();
			} else {
				if (running != null) {
					ready.add(running);
				}
				super.changeProcess = true;
				super.prepareProcess();
			}
		}
		countQuantum++;
	}
}
