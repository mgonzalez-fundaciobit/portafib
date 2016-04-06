package es.caib.portafib.logic.passarela;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 
 * @author anadal
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PassarelaCommonInfoSignature {

  String languageUI;

  String filtreCertificats;

  /**
   * Indica si la URL de retorn atacarà una zona protegida. Si val NULL la pàgina de 
   * redirecció anira a firmar directament. Si val diferent de NULL, en la pàgina de
   * redirecció obligara a fer login i a més comprovorà que sigui aquest usuari.
   */
  String username;

  String administrationID;

  String urlFinal;

  boolean browserSupportsJava;
  
  /**
   * Opcional. Si val null o buit llavors s'accepten tot els plugins
   */
  List<Long> acceptedPlugins;
  
  PassarelaPolicyInfoSignature policyInfoSignature = null;

  /**
   * 
   */
  public PassarelaCommonInfoSignature() {
  }



  /**
   * @param languageUI
   * @param filtreCertificats
   * @param username
   * @param administrationID
   * @param urlFinal
   * @param browserSupportsJava
   * @param policyInfoSignature
   */
  public PassarelaCommonInfoSignature(String languageUI, String filtreCertificats,
      String username, String administrationID, String urlFinal, boolean browserSupportsJava,
      PassarelaPolicyInfoSignature policyInfoSignature) {
    super();
    this.languageUI = languageUI;
    this.filtreCertificats = filtreCertificats;
    this.username = username;
    this.administrationID = administrationID;
    this.urlFinal = urlFinal;
    this.browserSupportsJava = browserSupportsJava;
    this.policyInfoSignature = policyInfoSignature;
  }



  public String getLanguageUI() {
    return languageUI;
  }

  public void setLanguageUI(String languageUI) {
    this.languageUI = languageUI;
  }

  public String getFiltreCertificats() {
    return filtreCertificats;
  }

  public void setFiltreCertificats(String filtreCertificats) {
    this.filtreCertificats = filtreCertificats;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUrlFinal() {
    return urlFinal;
  }

  public void setUrlFinal(String urlFinal) {
    this.urlFinal = urlFinal;
  }

  public boolean isBrowserSupportsJava() {
    return browserSupportsJava;
  }

  public void setBrowserSupportsJava(boolean browserSupportsJava) {
    this.browserSupportsJava = browserSupportsJava;
  }

  public String getAdministrationID() {
    return administrationID;
  }

  public void setAdministrationID(String administrationID) {
    this.administrationID = administrationID;
  }

  public PassarelaPolicyInfoSignature getPolicyInfoSignature() {
    return policyInfoSignature;
  }

  public void setPolicyInfoSignature(PassarelaPolicyInfoSignature policyInfoSignature) {
    this.policyInfoSignature = policyInfoSignature;
  }



  public List<Long> getAcceptedPlugins() {
    return acceptedPlugins;
  }


  public void setAcceptedPlugins(List<Long> acceptedPlugins) {
    this.acceptedPlugins = acceptedPlugins;
  }

}
