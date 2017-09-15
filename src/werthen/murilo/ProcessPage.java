package werthen.murilo;

public class ProcessPage {

	private int idProcess;
    private int timePage;
    private int indexPage;
    
    public ProcessPage (int idProcess, int timePage, int indexPage) {
    	this.idProcess = idProcess;
    	this.timePage = timePage;
    	this.indexPage = indexPage;
    }
    
    public int getIdProcess () {
        return idProcess;
    }

    public void setIdProcess (int idProcess) {
        this.idProcess = idProcess;
    }

    public int getTimePage () {
        return timePage;
    }

    public void setTimePage (int timePage) {
        this.timePage = timePage;
    }   
    
    public int getIndexPage () {
        return indexPage;
    }

    public void setIndexPage (int indexPage) {
        this.indexPage = indexPage;
    }   
}
