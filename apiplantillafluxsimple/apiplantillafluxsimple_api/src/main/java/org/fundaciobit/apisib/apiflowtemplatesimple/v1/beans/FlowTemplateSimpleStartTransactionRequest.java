package org.fundaciobit.apisib.apiflowtemplatesimple.v1.beans;

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
public class FlowTemplateSimpleStartTransactionRequest  {
  
  // XYZ ZZZ 

  public static final String VIEW_FULLSCREEN = "fullview";
  
  public static final String VIEW_IFRAME = "iframe";
  
  String transactionID;
  
  String returnUrl;

  String view;

  /**
   * 
   */
  public FlowTemplateSimpleStartTransactionRequest() {
    super();
  }

  /**
   * @param transactionID
   * @param fileInfoSignatureArray
   */
  public FlowTemplateSimpleStartTransactionRequest(String transactionID,
      String returnUrl, String view) {

    this.transactionID = transactionID;
    this.returnUrl = returnUrl;
    this.view = view;
  }


  public String getReturnUrl() {
    return returnUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }

  public String getView() {
    return view;
  }

  public void setView(String view) {
    this.view = view;
  }
  
  public String getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(String transactionID) {
    this.transactionID = transactionID;
  }


}
