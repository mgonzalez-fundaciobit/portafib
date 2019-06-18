package org.fundaciobit.apisib.apifirmaasyncsimple.v2.beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author anadal(u80067)
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmaAsyncSimpleSignerInfo {

  /**
   * - eEMGDE.Firma.RolFirma (eEMGDE17.2): Esquemas desarrollados a nivel local y que pueden
   * incluir valores como válida, autentica, refrenda, visa, representa, testimonia, etc..
   */
  protected String eniRolFirma;

  /**
   * eEMGDE.Firma.Firmante.NombreApellidos (eEMGDE17.5.1): Texto libre. Nombre o razón social
   * de los firmantes.
   */
  protected String eniSignerName;

  /**
   * eEMGDE.Firma.Firmante (eEMGDE17.5.2). Número Identificacion Firmantes (NIF)
   */
  protected String eniSignerAdministrationId;

  /**
   * eEMGDE.Firma.NivelFirma (eEMGDE17.5.4) Indicador normalizado que refleja el grado de
   * confianza de la firma utilizado. Ejemplos: Nick, PIN ciudadano, Firma electrónica
   * avanzada, Claves concertadas, Firma electrónica avanzada basada en certificados, CSV, ..
   */
  protected String eniSignLevel;

  protected Date signDate;

  protected String serialNumberCert;

  protected String issuerCert;

  protected String subjectCert;

  /**
   * eEMGDE.Firma.InformacionAdicional (eEMGDE17.5.5) Ofrecer cualquier otra información que se
   * considere útil acerca del firmante.
   * */
  protected List<FirmaAsyncSimpleKeyValue> additionalInformation = null;

  public FirmaAsyncSimpleSignerInfo() {
    super();
  }

  public FirmaAsyncSimpleSignerInfo(String eniRolFirma, String eniSignerName,
      String eniSignerAdministrationId, String eniSignLevel, Date signDate,
      String serialNumberCert, String issuerCert, String subjectCert,
      List<FirmaAsyncSimpleKeyValue> additionalInformation) {
    super();
    this.eniRolFirma = eniRolFirma;
    this.eniSignerName = eniSignerName;
    this.eniSignerAdministrationId = eniSignerAdministrationId;
    this.eniSignLevel = eniSignLevel;
    this.signDate = signDate;
    this.serialNumberCert = serialNumberCert;
    this.issuerCert = issuerCert;
    this.subjectCert = subjectCert;
    this.additionalInformation = additionalInformation;
  }

  public String getEniRolFirma() {
    return eniRolFirma;
  }

  public void setEniRolFirma(String eniRolFirma) {
    this.eniRolFirma = eniRolFirma;
  }

  public String getEniSignerName() {
    return eniSignerName;
  }

  public void setEniSignerName(String eniSignerName) {
    this.eniSignerName = eniSignerName;
  }

  public String getEniSignerAdministrationId() {
    return eniSignerAdministrationId;
  }

  public void setEniSignerAdministrationId(String eniSignerAdministrationId) {
    this.eniSignerAdministrationId = eniSignerAdministrationId;
  }

  public String getEniSignLevel() {
    return eniSignLevel;
  }

  public void setEniSignLevel(String eniSignLevel) {
    this.eniSignLevel = eniSignLevel;
  }

  public List<FirmaAsyncSimpleKeyValue> getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(List<FirmaAsyncSimpleKeyValue> additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public Date getSignDate() {
    return signDate;
  }

  public void setSignDate(Date signDate) {
    this.signDate = signDate;
  }

  public String getSerialNumberCert() {
    return serialNumberCert;
  }

  public void setSerialNumberCert(String serialNumberCert) {
    this.serialNumberCert = serialNumberCert;
  }

  public String getIssuerCert() {
    return issuerCert;
  }

  public void setIssuerCert(String issuerCert) {
    this.issuerCert = issuerCert;
  }

  public String getSubjectCert() {
    return subjectCert;
  }

  public void setSubjectCert(String subjectCert) {
    this.subjectCert = subjectCert;
  }

  @Override
  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("        + eniRolFirma:\t" + getEniRolFirma());
    str.append("\n").append("        + eniSignerName:\t" + getEniSignerName());
    str.append("\n").append(
        "        + eniSignerAdministrationId:\t" + getEniSignerAdministrationId());
    str.append("\n").append("        + eniSignLevel:\t" + getEniSignLevel());
    str.append("\n").append(
        "        + Sign Date:\t" + new SimpleDateFormat().format(getSignDate()));
    str.append("\n").append("        + Subject Cert:\t" + getSubjectCert());
    str.append("\n").append("        + Issuer Cert:\t" + getIssuerCert());

    List<FirmaAsyncSimpleKeyValue> additionInformation = getAdditionalInformation();

    if (additionInformation != null && additionInformation.size() != 0) {
      str.append("\n").append("        + INFORMACIO ADDICIONAL:");
      for (FirmaAsyncSimpleKeyValue firmaSimpleKeyValue : additionInformation) {
        str.append("\n").append(
            "          >> KEY[" + firmaSimpleKeyValue.getKey() + "]: "
                + firmaSimpleKeyValue.getValue());
      }
    }

    return str.toString();

  }

}
