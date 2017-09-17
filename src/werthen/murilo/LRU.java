package werthen.murilo;

public class LRU extends Scheduling {

	public LRU (int alpha, int quantum, int memoryCapacity) {
		super(alpha, quantum, memoryCapacity);
	}

	@Override
	public void changePage (ProcessPage pp) {
		ProcessPage oldPageRefer = mainMemory.get(0);
		int oldIndexRefer = 0;
		for (int i = 0; i < mainMemory.size(); i++) {
			if (mainMemory.get(i).getRefer() > oldPageRefer.getRefer()) {
				oldPageRefer = mainMemory.get(i);
				oldIndexRefer = i;
			}
		}
		mainMemory.remove(oldIndexRefer);
		mainMemory.add(oldIndexRefer, pp);
	}
}
