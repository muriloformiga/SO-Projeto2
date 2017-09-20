
package werthen.murilo;

import static werthen.murilo.Scheduling.mainMemory;


public class Otimo extends Scheduling {

    public Otimo(int alpha, int quantum, int memoryCapacity) {
        super(alpha, quantum, memoryCapacity);
    }
    @Override
    public void changePage (ProcessPage pp) {
        // pos é a posição sequinte do array aos processos que estão na pagina, se começar do inicio acho q não da certo
        int maior = 0;
       ;               Process process = ready.get(0);
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
