package werthen.murilo;

public class LRU extends Scheduling {

	public LRU (int alpha, int quantum, int memoryCapacity) {
		super(alpha, quantum, memoryCapacity);
	}

	@Override
	public void changePage (ProcessPage pp) {
		ProcessPage lastRefer;
		for (int i = 0; i < mainMemory.size(); i++) {
			int lastIndex = 0;
			for (int j = refer.size() - 1; j >= 0; j--) {
				if (mainMemory.get(i).getIndexPage() == refer.get(j).getIndexPage()) {
					lastIndex++;
				}
			}
		}
	}
}
