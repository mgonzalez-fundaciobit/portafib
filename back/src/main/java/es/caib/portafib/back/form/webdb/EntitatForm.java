package es.caib.portafib.back.form.webdb;

import java.util.List;
import org.fundaciobit.genapp.common.StringKeyValue;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import es.caib.portafib.back.form.PortaFIBBaseForm;
import es.caib.portafib.jpa.EntitatJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class EntitatForm extends PortaFIBBaseForm {
  
  private EntitatJPA entitat;
  
  
  private CommonsMultipartFile faviconID;
  private boolean faviconIDDelete;
  
  
  private CommonsMultipartFile logoWebID;
  private boolean logoWebIDDelete;
  
  
  private CommonsMultipartFile logoWebPeuID;
  private boolean logoWebPeuIDDelete;
  
  
  private CommonsMultipartFile logoSegellID;
  private boolean logoSegellIDDelete;
  
  
  private CommonsMultipartFile pdfAutoritzacioDelegacioID;
  private boolean pdfAutoritzacioDelegacioIDDelete;
  
  public EntitatForm() {
  }
  
  public EntitatForm(EntitatForm __toClone) {
    super(__toClone);
      this.entitat = __toClone.entitat;
    this.listOfUsuariAplicacioForUsuariAplicacioID = __toClone.listOfUsuariAplicacioForUsuariAplicacioID;
    this.listOfTraduccioForMotiuDelegacioID = __toClone.listOfTraduccioForMotiuDelegacioID;
    this.listOfTraduccioForFirmatPerFormatID = __toClone.listOfTraduccioForFirmatPerFormatID;
    this.listOfAlgorismeDeFirmaForAlgorismeDeFirmaID = __toClone.listOfAlgorismeDeFirmaForAlgorismeDeFirmaID;
    this.listOfValuesForPoliticaCustodia = __toClone.listOfValuesForPoliticaCustodia;
    this.listOfCustodiaInfoForCustodiaInfoID = __toClone.listOfCustodiaInfoForCustodiaInfoID;
    this.listOfValuesForPoliticaTaulaFirmes = __toClone.listOfValuesForPoliticaTaulaFirmes;
    this.listOfValuesForPosicioTaulaFirmes = __toClone.listOfValuesForPosicioTaulaFirmes;
    this.listOfValuesForSegellDeTempsViaWeb = __toClone.listOfValuesForSegellDeTempsViaWeb;
    this.listOfPluginForPluginID = __toClone.listOfPluginForPluginID;
    this.listOfPluginForPluginRubricaID = __toClone.listOfPluginForPluginRubricaID;
    this.listOfPluginForPluginValidaFirmesID = __toClone.listOfPluginForPluginValidaFirmesID;
    this.listOfPluginForPluginValidaCertificatID = __toClone.listOfPluginForPluginValidaCertificatID;
  }
  
  public EntitatForm(EntitatJPA entitat, boolean nou) {
    super(nou);
    this.entitat = entitat;
  }
  
  public EntitatJPA getEntitat() {
    return entitat;
  }
  public void setEntitat(EntitatJPA entitat) {
    this.entitat = entitat;
  }
  
  java.util.List<es.caib.portafib.model.entity.Idioma> idiomesTraduccio = null;

  public java.util.List<es.caib.portafib.model.entity.Idioma> getIdiomesTraduccio() {
    return idiomesTraduccio;
  }

  public void setIdiomesTraduccio(java.util.List<es.caib.portafib.model.entity.Idioma> idiomesTraduccio) {
    this.idiomesTraduccio = idiomesTraduccio;
  }
  
  public CommonsMultipartFile getFaviconID() {
    return faviconID;
  }
  
   public void setFaviconID(CommonsMultipartFile faviconID) {
    this.faviconID = faviconID;
  }
  public boolean isFaviconIDDelete() {
    return faviconIDDelete;
  }
  
  public void setFaviconIDDelete(boolean faviconIDDelete) {
    this.faviconIDDelete = faviconIDDelete;
   }
  public CommonsMultipartFile getLogoWebID() {
    return logoWebID;
  }
  
   public void setLogoWebID(CommonsMultipartFile logoWebID) {
    this.logoWebID = logoWebID;
  }
  public boolean isLogoWebIDDelete() {
    return logoWebIDDelete;
  }
  
  public void setLogoWebIDDelete(boolean logoWebIDDelete) {
    this.logoWebIDDelete = logoWebIDDelete;
   }
  public CommonsMultipartFile getLogoWebPeuID() {
    return logoWebPeuID;
  }
  
   public void setLogoWebPeuID(CommonsMultipartFile logoWebPeuID) {
    this.logoWebPeuID = logoWebPeuID;
  }
  public boolean isLogoWebPeuIDDelete() {
    return logoWebPeuIDDelete;
  }
  
  public void setLogoWebPeuIDDelete(boolean logoWebPeuIDDelete) {
    this.logoWebPeuIDDelete = logoWebPeuIDDelete;
   }
  public CommonsMultipartFile getLogoSegellID() {
    return logoSegellID;
  }
  
   public void setLogoSegellID(CommonsMultipartFile logoSegellID) {
    this.logoSegellID = logoSegellID;
  }
  public boolean isLogoSegellIDDelete() {
    return logoSegellIDDelete;
  }
  
  public void setLogoSegellIDDelete(boolean logoSegellIDDelete) {
    this.logoSegellIDDelete = logoSegellIDDelete;
   }
  public CommonsMultipartFile getPdfAutoritzacioDelegacioID() {
    return pdfAutoritzacioDelegacioID;
  }
  
   public void setPdfAutoritzacioDelegacioID(CommonsMultipartFile pdfAutoritzacioDelegacioID) {
    this.pdfAutoritzacioDelegacioID = pdfAutoritzacioDelegacioID;
  }
  public boolean isPdfAutoritzacioDelegacioIDDelete() {
    return pdfAutoritzacioDelegacioIDDelete;
  }
  
  public void setPdfAutoritzacioDelegacioIDDelete(boolean pdfAutoritzacioDelegacioIDDelete) {
    this.pdfAutoritzacioDelegacioIDDelete = pdfAutoritzacioDelegacioIDDelete;
   }
  private List<StringKeyValue> listOfUsuariAplicacioForUsuariAplicacioID;

  public List<StringKeyValue> getListOfUsuariAplicacioForUsuariAplicacioID() {
    return this.listOfUsuariAplicacioForUsuariAplicacioID;
  }

  public void setListOfUsuariAplicacioForUsuariAplicacioID(List<StringKeyValue> listOfUsuariAplicacioForUsuariAplicacioID) {
    this.listOfUsuariAplicacioForUsuariAplicacioID = listOfUsuariAplicacioForUsuariAplicacioID;
  }



  private List<StringKeyValue> listOfTraduccioForMotiuDelegacioID;

  public List<StringKeyValue> getListOfTraduccioForMotiuDelegacioID() {
    return this.listOfTraduccioForMotiuDelegacioID;
  }

  public void setListOfTraduccioForMotiuDelegacioID(List<StringKeyValue> listOfTraduccioForMotiuDelegacioID) {
    this.listOfTraduccioForMotiuDelegacioID = listOfTraduccioForMotiuDelegacioID;
  }



  private List<StringKeyValue> listOfTraduccioForFirmatPerFormatID;

  public List<StringKeyValue> getListOfTraduccioForFirmatPerFormatID() {
    return this.listOfTraduccioForFirmatPerFormatID;
  }

  public void setListOfTraduccioForFirmatPerFormatID(List<StringKeyValue> listOfTraduccioForFirmatPerFormatID) {
    this.listOfTraduccioForFirmatPerFormatID = listOfTraduccioForFirmatPerFormatID;
  }



  private List<StringKeyValue> listOfAlgorismeDeFirmaForAlgorismeDeFirmaID;

  public List<StringKeyValue> getListOfAlgorismeDeFirmaForAlgorismeDeFirmaID() {
    return this.listOfAlgorismeDeFirmaForAlgorismeDeFirmaID;
  }

  public void setListOfAlgorismeDeFirmaForAlgorismeDeFirmaID(List<StringKeyValue> listOfAlgorismeDeFirmaForAlgorismeDeFirmaID) {
    this.listOfAlgorismeDeFirmaForAlgorismeDeFirmaID = listOfAlgorismeDeFirmaForAlgorismeDeFirmaID;
  }



  private List<StringKeyValue> listOfValuesForPoliticaCustodia;

  public List<StringKeyValue> getListOfValuesForPoliticaCustodia() {
    return this.listOfValuesForPoliticaCustodia;
  }

  public void setListOfValuesForPoliticaCustodia(List<StringKeyValue> listOfValuesForPoliticaCustodia) {
    this.listOfValuesForPoliticaCustodia = listOfValuesForPoliticaCustodia;
  }



  private List<StringKeyValue> listOfCustodiaInfoForCustodiaInfoID;

  public List<StringKeyValue> getListOfCustodiaInfoForCustodiaInfoID() {
    return this.listOfCustodiaInfoForCustodiaInfoID;
  }

  public void setListOfCustodiaInfoForCustodiaInfoID(List<StringKeyValue> listOfCustodiaInfoForCustodiaInfoID) {
    this.listOfCustodiaInfoForCustodiaInfoID = listOfCustodiaInfoForCustodiaInfoID;
  }



  private List<StringKeyValue> listOfValuesForPoliticaTaulaFirmes;

  public List<StringKeyValue> getListOfValuesForPoliticaTaulaFirmes() {
    return this.listOfValuesForPoliticaTaulaFirmes;
  }

  public void setListOfValuesForPoliticaTaulaFirmes(List<StringKeyValue> listOfValuesForPoliticaTaulaFirmes) {
    this.listOfValuesForPoliticaTaulaFirmes = listOfValuesForPoliticaTaulaFirmes;
  }



  private List<StringKeyValue> listOfValuesForPosicioTaulaFirmes;

  public List<StringKeyValue> getListOfValuesForPosicioTaulaFirmes() {
    return this.listOfValuesForPosicioTaulaFirmes;
  }

  public void setListOfValuesForPosicioTaulaFirmes(List<StringKeyValue> listOfValuesForPosicioTaulaFirmes) {
    this.listOfValuesForPosicioTaulaFirmes = listOfValuesForPosicioTaulaFirmes;
  }



  private List<StringKeyValue> listOfValuesForSegellDeTempsViaWeb;

  public List<StringKeyValue> getListOfValuesForSegellDeTempsViaWeb() {
    return this.listOfValuesForSegellDeTempsViaWeb;
  }

  public void setListOfValuesForSegellDeTempsViaWeb(List<StringKeyValue> listOfValuesForSegellDeTempsViaWeb) {
    this.listOfValuesForSegellDeTempsViaWeb = listOfValuesForSegellDeTempsViaWeb;
  }



  private List<StringKeyValue> listOfPluginForPluginID;

  public List<StringKeyValue> getListOfPluginForPluginID() {
    return this.listOfPluginForPluginID;
  }

  public void setListOfPluginForPluginID(List<StringKeyValue> listOfPluginForPluginID) {
    this.listOfPluginForPluginID = listOfPluginForPluginID;
  }



  private List<StringKeyValue> listOfPluginForPluginRubricaID;

  public List<StringKeyValue> getListOfPluginForPluginRubricaID() {
    return this.listOfPluginForPluginRubricaID;
  }

  public void setListOfPluginForPluginRubricaID(List<StringKeyValue> listOfPluginForPluginRubricaID) {
    this.listOfPluginForPluginRubricaID = listOfPluginForPluginRubricaID;
  }



  private List<StringKeyValue> listOfPluginForPluginValidaFirmesID;

  public List<StringKeyValue> getListOfPluginForPluginValidaFirmesID() {
    return this.listOfPluginForPluginValidaFirmesID;
  }

  public void setListOfPluginForPluginValidaFirmesID(List<StringKeyValue> listOfPluginForPluginValidaFirmesID) {
    this.listOfPluginForPluginValidaFirmesID = listOfPluginForPluginValidaFirmesID;
  }



  private List<StringKeyValue> listOfPluginForPluginValidaCertificatID;

  public List<StringKeyValue> getListOfPluginForPluginValidaCertificatID() {
    return this.listOfPluginForPluginValidaCertificatID;
  }

  public void setListOfPluginForPluginValidaCertificatID(List<StringKeyValue> listOfPluginForPluginValidaCertificatID) {
    this.listOfPluginForPluginValidaCertificatID = listOfPluginForPluginValidaCertificatID;
  }



  
} // Final de Classe 
