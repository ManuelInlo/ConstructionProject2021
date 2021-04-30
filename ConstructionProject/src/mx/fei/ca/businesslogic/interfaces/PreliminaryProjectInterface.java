package mx.fei.ca.businesslogic.interfaces;

import java.sql.Date;
import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.PreliminaryProject;

/**
 *
 * @author inigu
 */
public interface PreliminaryProjectInterface {
    public int savePreliminaryProject(PreliminaryProject preliminaryproject)throws BusinessConnectionException,BusinessDataException;
    
    public int updatePreliminaryProject(PreliminaryProject preliminaryproject, int idPreliminaryProject
            , int idProject, int idCollaborator, String tittlePreliminaryProject/*, Date startDate*/
            , String preliminaryProjectCondition, String duration, String modality
            , String preliminaryProjectDescription)throws BusinessConnectionException;
    
    public int deletePreliminaryprojectById(int idPreliminaryproject) throws BusinessDataException, BusinessConnectionException;
}

