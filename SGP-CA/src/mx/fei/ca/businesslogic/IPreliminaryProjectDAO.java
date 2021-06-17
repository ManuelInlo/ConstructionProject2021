package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.domain.PreliminaryProject;

public interface IPreliminaryProjectDAO {
    public boolean savedPreliminaryProject(PreliminaryProject preliminaryproject, int idProject, int idCollaborator) throws BusinessConnectionException;
    public boolean updatePreliminaryProject(PreliminaryProject preliminaryproject, int idProject, int idCollaborator)throws BusinessConnectionException;
    public PreliminaryProject findpreliminaryProjectById(int id) throws BusinessConnectionException;

}
