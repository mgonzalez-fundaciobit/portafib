package es.caib.portafib.logic.passarela;

import java.io.File;
import java.util.List;

import es.caib.portafib.logic.passarela.api.PassarelaSignatureResult;
import es.caib.portafib.logic.passarela.api.PassarelaSignatureStatus;
import es.caib.portafib.logic.passarela.api.PassarelaSignaturesSet;

import javax.ejb.Local;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NValidationException;


/**
 * 
 * @author anadal
 *
 */
@Local
public interface PassarelaDeFirmaWebLocal extends AbstractPassarelaDeFirmaLocal {

  public static final String JNDI_NAME = "portafib/PassarelaDeFirmaWebEJB/local";

  public static final String PASSARELA_CONTEXTPATH = "/public/passarela";
  
  public static final String PASSARELA_CONTEXTPATH_FINAL = "/final";

  public String startTransaction(
      PassarelaSignaturesSet signaturesSet, String entitatID, boolean fullView)
      throws I18NException, I18NValidationException;
  
  public PassarelaSignatureStatus getStatusTransaction(String transactionID) throws I18NException;
  
  public PassarelaSignaturesSetWebInternalUse getSignaturesSetFullByTransactionID(String transactionID)
      throws I18NException;

  public List<PassarelaSignatureResult> getSignatureResults(String transactionID) throws I18NException;

  public File getFitxerOriginalPath(String transactionID,String signID);

  public File getFitxerFirmatPath(String transactionID,String signID);
 
  public void closeTransaction(String transactionID);

}
