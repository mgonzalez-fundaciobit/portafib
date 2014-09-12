package es.caib.portafib.logic;

import java.util.Set;

import es.caib.portafib.ejb.UsuariPersonaLocal;
import es.caib.portafib.jpa.UsuariPersonaJPA;

import javax.ejb.Local;

import org.fundaciobit.plugins.userinformation.UserInfo;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NValidationException;


/**
 * 
 * @author anadal
 * 
 */
@Local
public interface UsuariPersonaLogicaLocal extends UsuariPersonaLocal {
  
  String JNDI_NAME = "portafib/UsuariPersonaLogicaEJB/local";

  public UsuariPersonaJPA findByPrimaryKeyFull(String paramString);

  public Set<Long> deleteFull(String usuariPersonaID) throws I18NException;

  public UsuariPersonaJPA createFull(UsuariPersonaJPA usuariPersonaJPA) throws I18NException,
      I18NValidationException, Exception;

  public UserInfo checkAdministrationIDInUserInformationPlugin(String nif) throws I18NException;

  public UsuariPersonaJPA getUsuariPersonaIDByAdministrationID(String administrationID)
      throws I18NException;
  

}