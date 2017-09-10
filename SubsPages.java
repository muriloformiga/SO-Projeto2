package projetopaginacao;

import static projetopaginacao.Optimo.*;

public class SubsPages {
    
    public static void Execution(){       
        page.add(OrdemUtilizacao[0]); //Adiciona primeiro processo
        for (int i = 1; i < QuantidadePaginas; i ++) {  
            if (buscaPage(OrdemUtilizacao[i]) == false){ 
                if(page.size() < 3){
                    page.add(OrdemUtilizacao[i]);
                }else{
                    int p = QualSubstituir(i);                   
                    page.set(p,OrdemUtilizacao[i]);
                    falta++;
                    System.out.println("OrdemUtilizacao: " + OrdemUtilizacao[i]);
                }                    
            } 
            for (int j = 0; j < page.size(); j++){
                System.out.print(page.get(j));
            }
            System.out.println();
        }
    }   
    
    public static boolean buscaPage(int valor){
        boolean tem = false;
        
        for ( int f = 0; f <page.size();f++ ){
            if(page.get(f) == valor){
                tem = true; 
                System.out.println("result busc "+ page.get(f)+" "+  valor + " " + tem);
                break;
            }            
        }        
        return tem;
    }    
    
}
