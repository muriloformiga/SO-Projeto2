/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        int Indsub = 0;        
       
        for (int i = pos; i < mainMemory.size(); i++ ){
                if(mainMemory.get(i).getTimePage() == mainMemory.get(i).getTimePage())
                    mainMemory.get(i).setProxRefer(i);
                        
        }    
        
        for (int i = 0; i <  mainMemory.size(); i++ ){            
            if (mainMemory.get(i).getProxRefer() > maior){
                maior = mainMemory.get(i).getProxRefer();
                Indsub = i;
            }
        }
        mainMemory.remove(Indsub);
	mainMemory.add(Indsub, pp);
        
    }
}
