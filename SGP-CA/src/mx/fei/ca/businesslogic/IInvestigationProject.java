/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.ca.businesslogic;

import mx.fei.ca.businesslogic.exceptions.BusinessConnectionException;
import mx.fei.ca.businesslogic.exceptions.BusinessDataException;
import mx.fei.ca.domain.InvestigationProject;

/**
 *
 * @author inigu
 */
public interface IInvestigationProject {
     public boolean savedInvestigationProject(InvestigationProject investigationproject, int keycode) throws BusinessConnectionException, BusinessDataException;
     public boolean updateInvestigationproject(InvestigationProject investigationproject, int keycode) throws BusinessConnectionException, BusinessDataException;
     public InvestigationProject findInvestigationProjectById(int idInvestigationproject) throws BusinessConnectionException;
     //maybe will be necessary more methods
}
