package mx.fei.ca.domain;

/**
 *
 * @author inigu
 */
public class Collaborator {
    private int idCollaborator;
    private String nameCollaborator;
    private String position;
    
    public Collaborator(){
        //Default constructor
    }

    public Collaborator(int idCollaborator, String nameCollaborator, String position) {
        this.idCollaborator = idCollaborator;
        this.nameCollaborator = nameCollaborator;
        this.position = position;
    }

    public int getIdCollaborator() {
        return idCollaborator;
    }

    public void setIdCollaborator(int idCollaborator) {
        this.idCollaborator = idCollaborator;
    }

    public String getNameCollaborator() {
        return nameCollaborator;
    }

    public void setNameCollaborator(String nameCollaborator) {
        this.nameCollaborator = nameCollaborator;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Collaborator{" + "idCollaborator=" + idCollaborator + ", nameCollaborator=" + nameCollaborator + ", position=" + position + '}';
    }
    
    
}
