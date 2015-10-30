package org.fundaciobit.plugins.signatureweb.api;


/**
 * 
 * @author anadal
 *
 */
public class PdfInfoSignature {
  
  public static final int SIGNATURESTABLELOCATION_WITHOUT = 0;
  public static final int SIGNATURESTABLELOCATION_FIRSTPAGE = 1;
  public static final int SIGNATURESTABLELOCATION_LASTPAGE = -1;
  

  protected int signaturesTableLocation = 0;
  
  protected PdfRubricRectangle pdfRubricRectangle = null;
  
  protected IRubricGenerator rubricGenerator = null;

  public int getSignaturesTableLocation() {
    return signaturesTableLocation;
  }

  public void setSignaturesTableLocation(int signaturesTableLocation) {
    this.signaturesTableLocation = signaturesTableLocation;
  }

  public PdfRubricRectangle getPdfRubricRectangle() {
    return pdfRubricRectangle;
  }

  public void setPdfRubricRectangle(PdfRubricRectangle pdfRubricRectangle) {
    this.pdfRubricRectangle = pdfRubricRectangle;
  }

  public IRubricGenerator getRubricGenerator() {
    return rubricGenerator;
  }

  public void setRubricGenerator(IRubricGenerator rubricGenerator) {
    this.rubricGenerator = rubricGenerator;
  }
  
  
  
  
}
