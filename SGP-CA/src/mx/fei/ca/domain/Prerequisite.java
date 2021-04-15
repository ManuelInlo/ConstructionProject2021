
package mx.fei.ca.domain;

public class Prerequisite {
    private int idPrerequisite;
    private String description;
    private String prerequisiteManager;

    public Prerequisite(String description, String prerequisiteManager) {
        this.description = description;
        this.prerequisiteManager = prerequisiteManager;
    }

    public int getIdPrerequisite() {
        return idPrerequisite;
    }

    public String getDescription() {
        return description;
    }

    public String getPrerequisiteManager() {
        return prerequisiteManager;
    }

    public void setIdPrerequisite(int idPrerequisite) {
        this.idPrerequisite = idPrerequisite;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrerequisiteManager(String prerequisiteManager) {
        this.prerequisiteManager = prerequisiteManager;
    }
    
}
