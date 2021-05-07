
package mx.fei.ca.domain;


public class InvestigationProject {
    private int idProject;
    private String name;

    public InvestigationProject() {
        
    }

    public InvestigationProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdProject() {
        return idProject;
    }

    public String getName() {
        return name;
    }
    
    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
