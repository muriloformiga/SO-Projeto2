package projetopaginacao;

import java.util.ArrayList;
import static projetopaginacao.SubsPages.*;

/**
 *
 * @author werthen
 */
public class Optimo {    
    
    // sabe-se qual processo vai demorar mais para ser utilizado
    // usando para teste 5 processos 
    // com ordem de utilização 2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2
	
    static int OrdemUtilizacao[] = { 2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2};
    static int QuantidadePaginas = OrdemUtilizacao.length;
    int TamPage = 3;
    static int falta = 0;
    
    static ArrayList<Integer> page = new ArrayList<Integer>();
        
    public static int QualSubstituir(int pos){
        int subs = 0, a = 1000000,b = 1000000,c = 1000000;
        for (int i = pos; i < QuantidadePaginas; i ++){
            if (page.get(0) == OrdemUtilizacao[i] && a == 1000000){                             
                a = i;                
            }
            if (page.get(1) == OrdemUtilizacao[i] && b == 1000000){                             
                b = i;                
            }
            if (page.get(2) == OrdemUtilizacao[i] && c == 1000000){                         
                c = i;            
            }             
        }
        if( a >= b && a >= c)
           return 0;
        if( b >= a && b >= c)
           return 1;
        if( c >= b && c >= a)
           return 2;
        
       return -1; 
    }
    
}
