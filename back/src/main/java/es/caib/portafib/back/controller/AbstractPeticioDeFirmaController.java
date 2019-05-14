package es.caib.portafib.back.controller;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import es.caib.portafib.back.controller.admin.GestioEntitatAdminController;
import es.caib.portafib.back.controller.webdb.PeticioDeFirmaController;
import es.caib.portafib.back.security.LoginInfo;
import es.caib.portafib.back.validator.PeticioDeFirmaAmbFitxerAFirmarWebValidator;
import es.caib.portafib.logic.PeticioDeFirmaLogicaLocal;
import es.caib.portafib.model.entity.PeticioDeFirma;
import es.caib.portafib.utils.ConstantsV2;

/**
 * Afegeix un Validador per no deixar passar FitxersAFirma NULLs
 * 
 * @author anadal
 *
 */
public abstract class AbstractPeticioDeFirmaController extends PeticioDeFirmaController {

  @EJB(mappedName = "portafib/PeticioDeFirmaLogicaEJB/local")
  protected PeticioDeFirmaLogicaLocal peticioDeFirmaLogicaEjb;

  @Autowired
  private PeticioDeFirmaAmbFitxerAFirmarWebValidator peticioDeFirmaAmbFitxerAFirmarWebValidator;

  @PostConstruct
  public void initValidador() {
    setWebValidator(this.peticioDeFirmaAmbFitxerAFirmarWebValidator);
  }

  @Override
  public void delete(HttpServletRequest request, PeticioDeFirma peticioDeFirma)
      throws Exception, I18NException {

    Set<Long> fitxers;
    fitxers = peticioDeFirmaLogicaEjb.deleteFullUsingUsuariEntitat(
        peticioDeFirma.getPeticioDeFirmaID(), LoginInfo.getInstance().getUsuariEntitatID());

    borrarFitxers(fitxers);
  }

  // #166
  @Override
  public List<StringKeyValue> getReferenceListForPosicioTaulaFirmesID(
      HttpServletRequest request, ModelAndView mav, Where where) throws I18NException {
    return GestioEntitatAdminController.staticGetReferenceListForPosicioTaulaFirmes();
  }

  // #199
  @Override
  public List<StringKeyValue> getReferenceListForTipusFirmaID(HttpServletRequest request,
      ModelAndView mav, Where where) throws I18NException {
    List<StringKeyValue> __tmp = staticGetReferenceListForTipusFirmaID();
    return __tmp;
  }

  // #199
  public static List<StringKeyValue> staticGetReferenceListForTipusFirmaID() {
    List<StringKeyValue> __tmp = new java.util.ArrayList<StringKeyValue>();

    __tmp.add(new StringKeyValue(String.valueOf(ConstantsV2.TIPUSFIRMA_PADES), "PAdES"));
    __tmp.add(new StringKeyValue(String.valueOf(ConstantsV2.TIPUSFIRMA_XADES), "XAdES"));
    __tmp.add(new StringKeyValue(String.valueOf(ConstantsV2.TIPUSFIRMA_CADES), "CAdES"));
    __tmp.add(new StringKeyValue(String.valueOf(ConstantsV2.TIPUSFIRMA_SMIME), "SMIME"));
    return __tmp;
  }
  
  //#199
  @Override
  public List<StringKeyValue> getReferenceListForAlgorismeDeFirmaID(HttpServletRequest request,
      ModelAndView mav, Where where)  throws I18NException {
   return staticGetReferenceListForAlgorismeDeFirmaID();
 }

  public static List<StringKeyValue> staticGetReferenceListForAlgorismeDeFirmaID() {
    List<StringKeyValue> __tmp = new java.util.ArrayList<StringKeyValue>();
    
     __tmp.add(new StringKeyValue(String.valueOf(ConstantsV2.SIGN_ALGORITHM_SHA1WITHRSA) , "SHA-1"));
     __tmp.add(new StringKeyValue(String.valueOf(ConstantsV2.SIGN_ALGORITHM_SHA256WITHRSA) , "SHA-256"));
     __tmp.add(new StringKeyValue(String.valueOf(ConstantsV2.SIGN_ALGORITHM_SHA384WITHRSA) , "SHA-384"));
     __tmp.add(new StringKeyValue(String.valueOf(ConstantsV2.SIGN_ALGORITHM_SHA512WITHRSA) , "SHA-512"));
     return __tmp;
  }
  
  //#199
  @Override
  public List<StringKeyValue> getReferenceListForPrioritatID(HttpServletRequest request,
      ModelAndView mav, Where where)  throws I18NException {
   List<StringKeyValue> __tmp = new java.util.ArrayList<StringKeyValue>();
   
   for (int i = 0; i < 10; i++) {
     __tmp.add(new StringKeyValue(String.valueOf(i) , i + " " + I18NUtils.tradueix("prioritat." + i)));
   }
   
   return __tmp;
 }
  
  //#199
  @Override
  public List<StringKeyValue> getReferenceListForTipusEstatPeticioDeFirmaID(HttpServletRequest request,
      ModelAndView mav, Where where)  throws I18NException {
   List<StringKeyValue> __tmp = new java.util.ArrayList<StringKeyValue>();
   
   for(int estat : ConstantsV2.TIPUSESTATPETICIODEFIRMA) {
     __tmp.add(new StringKeyValue(String.valueOf(estat) , I18NUtils.tradueix("tipusestatpeticiodefirma." + estat)));
   }
   return __tmp;
 }
  
  

}
