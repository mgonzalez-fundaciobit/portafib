package org.fundaciobit.apisib.apifirmaasyncsimple.v2.beans;

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
public class FirmaAsyncSimpleSigner extends FirmaAsyncSimplePerson {

  /**
   * Dades d'un usuari extern
   */
  protected FirmaAsyncSimpleExternalSigner externalSigner;

  public FirmaAsyncSimpleSigner() {
    super();
  }

  public FirmaAsyncSimpleSigner(String positionInTheCompany, String administrationID,
      String username, String intermediateServerUsername,
      FirmaAsyncSimpleExternalSigner externalSigner) {
    super(positionInTheCompany, administrationID, username, intermediateServerUsername);
    this.externalSigner = externalSigner;
  }

  public FirmaAsyncSimpleExternalSigner getExternalSigner() {
    return externalSigner;
  }

  public void setExternalSigner(FirmaAsyncSimpleExternalSigner externalSigner) {
    this.externalSigner = externalSigner;
  }

}