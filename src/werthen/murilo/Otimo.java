package werthen.murilo;

public class Otimo extends Scheduling {

	public Otimo(int alpha, int quantum, int memoryCapacity) {
		super(alpha, quantum, memoryCapacity);
	}

	@Override
	public void changePage (ProcessPage pp) {
		int maior = 0;
		Process process;
		if (ready.size() > 0) {
			process = ready.get(0);
		} else {
			process = null;
		}
		for (int i = 0; i < ready.size(); i++ ){
			if( ready.get(i).getSubmitionTime() > maior ){
				maior = ready.get(i).getSubmitionTime();
				process = ready.get(i);                
			}
		}    
		for (int i = 0; i < process.getPages().size(); i++) {
			mainMemory.remove(process.getPages().get(i));       
		}

		mainMemory.add(0, pp);

	}
}
