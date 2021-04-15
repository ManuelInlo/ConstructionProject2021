
package mx.fei.ca.domain;


public class Collaborator {
    private int idCollaborator;
    private String name;
    private String position;
    private ReceptionWork receptionWork;

    public Collaborator(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public int getIdCollaborator() {
        return idCollaborator;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public ReceptionWork getReceptionWork() {
        return receptionWork;
    }
   
    public void setIdCollaborator(int idCollaborator) {
        this.idCollaborator = idCollaborator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setReceptionWork(ReceptionWork receptionWork) {
        this.receptionWork = receptionWork;
    }
 
}
