package es.caib.portafib.logic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.fundaciobit.plugins.barcode.IBarcodePlugin;
import org.fundaciobit.plugins.barcode.barcode128.BarCode128Plugin;
import org.fundaciobit.plugins.barcode.pdf417.Pdf417Plugin;
import org.fundaciobit.plugins.barcode.qrcode.QrCodePlugin;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.utils.Utils;
import org.junit.Test;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import es.caib.portafib.utils.Constants;
import es.caib.portafib.utils.SignBoxRectangle;

/**
 * 
 * @author anadal
 * 
 */
public class TestPDFUtils implements Constants {

  public static final Logger log = Logger.getLogger(TestPDFUtils.class);

  @Test
  public void testPositionCaixesDeFirmes() {
    
    String expected = "-- 1\n"
      +"signaturePositionOnPageLowerLeftX=106\n"
      +"signaturePositionOnPageLowerLeftY=664\n"
      +"signaturePositionOnPageUpperRightX=555\n"
      +"signaturePositionOnPageUpperRightY=713\n"
      +"\n"
      +"\n"
      +"-- 2\n"
      +"signaturePositionOnPageLowerLeftX=106\n"
      +"signaturePositionOnPageLowerLeftY=610\n"
      +"signaturePositionOnPageUpperRightX=555\n"
      +"signaturePositionOnPageUpperRightY=659\n"
      +"\n"
      +"\n"
      +"-- 3\n"
      +"signaturePositionOnPageLowerLeftX=106\n"
      +"signaturePositionOnPageLowerLeftY=556\n"
      +"signaturePositionOnPageUpperRightX=555\n"
      +"signaturePositionOnPageUpperRightY=605\n"
      +"\n"
      +"\n"
      +"-- 4\n"
      +"signaturePositionOnPageLowerLeftX=106\n"
      +"signaturePositionOnPageLowerLeftY=502\n"
      +"signaturePositionOnPageUpperRightX=555\n"
      +"signaturePositionOnPageUpperRightY=551\n"
      +"\n"
      +"\n"
      +"-- 5\n"
      +"signaturePositionOnPageLowerLeftX=106\n"
      +"signaturePositionOnPageLowerLeftY=448\n"
      +"signaturePositionOnPageUpperRightX=555\n"
      +"signaturePositionOnPageUpperRightY=497\n"
      +"\n"
      +"\n"
      +"-- 6\n"
      +"signaturePositionOnPageLowerLeftX=106\n"
      +"signaturePositionOnPageLowerLeftY=394\n"
      +"signaturePositionOnPageUpperRightX=555\n"
      +"signaturePositionOnPageUpperRightY=443\n\n\n";
    
    
    StringBuffer properties = new StringBuffer();
    for (int i = 0; i < 13; i++) {
      
      SignBoxRectangle sbr = PdfUtils.getPositionOfVisibleSignature(i +1);
      
      properties.append("-- " + (i+1) + "\n");
      properties.append("signaturePositionOnPageLowerLeftX=" + (int)sbr.llx + "\n");
      properties.append("signaturePositionOnPageLowerLeftY=" + (int)sbr.lly + "\n");
      properties.append("signaturePositionOnPageUpperRightX=" + (int)sbr.urx + "\n");
      properties.append("signaturePositionOnPageUpperRightY=" + (int)sbr.ury + "\n\n\n");
      
      

      log.info(" info.put(" + (i+1) + ", \"" 
          + (int)sbr.llx + "," + (int)sbr.lly + "," + (int)sbr.urx + "," + (int)sbr.ury 
          + "\");");
      
    }

    org.junit.Assert.assertEquals(expected, properties.toString());

  }
  
  
  
  
  @Test
  public void testProcessarPagines() {
    
    
    String[][] test = new String[][] {
     // PATRO     , // Darrera pagina = -1                           // Cap pagina = 0              // Primera pagina  = 1 
     { "-1, 0, 1, *, 3, 4-5, 8-", "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", "[1, 2, 3, 4, 5, 6, 7, 8, 9]", "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]"},
     { "-1, 0, 1, 4-5,3,  8-",    "[1, 3, 4, 5, 8, 9, 10]",          "[1, 3, 4, 5, 8, 9]",          "[1, 2, 4, 5, 6, 9, 10]" },
     { "-1, 0, 1, 3, ",           "[1, 3, 10]",                      "[1, 3, 9]",                   "[1, 2, 4, 10]"},
     // Llança error
     //{ " ,, -1, 0",               "[1, 10]",                         "[1, 9]",                      "[1, 10]" }
    };
    

    // Cap pagina
    for (int t = 0; t < test.length; t++) {
      
    
    String pagines = test[t][0];
    int[] posicioTaulaDeFirmes = new int[] {
        TAULADEFIRMES_DARRERAPAGINA,
        TAULADEFIRMES_SENSETAULA,
        TAULADEFIRMES_PRIMERAPAGINA
    };
    int lastPage = 10;
    
    for (int i = 0; i < posicioTaulaDeFirmes.length; i++) {
      Set<Integer> set;
      try {
        set = PdfUtils.processarPagines(pagines, posicioTaulaDeFirmes[i],
            posicioTaulaDeFirmes[i] == TAULADEFIRMES_SENSETAULA? (lastPage -1) : lastPage);
        String actual = Arrays.toString(set.toArray());
        //log.info("Actual: " + actual);
        //log.info("Expect: " + test[t][2 + posicioTaulaDeFirmes[i]]);
        
        Assert.assertEquals(test[t][2 + posicioTaulaDeFirmes[i]], actual);
        
      } catch (I18NException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
    }
    
  }
  
  
  public static void main1(String[] args) {

    try {
     
    File parent = new File("D:\\dades\\dades\\CarpetesPersonals\\Programacio\\PortaFIB\\applet\\web");

    File docOrig = new File(parent, "hola_amb_taula.pdf");
    File docFir = new File(parent, "hola_amb_taula_firmat.pdf");
    
    
    byte[] pdfData = FileUtils.readFileToByteArray(docFir);
    
    boolean result = PdfUtils.checkDocumentWhenFirstSign(new FileInputStream(docOrig), pdfData);
    
    log.info("Orig Len = " + docOrig.length());
    
    log.info("Result = " + result);
    
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  
  }
  
  
  public static void main(String[] args) {
    
    File f = new File("C:\\Documents and Settings\\anadal\\Escritorio\\DEMO\\hola.pdf");
    
    log.info("TIPUS MIME = " + Utils.getMimeType(f.getAbsolutePath()));
    
  }
  
  

  public static void main0(String[] args) {

    File parent = new File("C:\\Documents and Settings\\anadal\\Escritorio\\PortaSIB\\proves");

    File srcPDF = new File(parent, "holat.pdf");

    String dsfFileName = "hola_custodia.pdf";

    IBarcodePlugin[] barcode = new IBarcodePlugin[] { new Pdf417Plugin(), new QrCodePlugin(),
        new BarCode128Plugin() };

    for (int i = 0; i < barcode.length; i++) {

      try {

        File dstPDF = new File(barcode[i].getName() + dsfFileName);

        PdfReader reader = new PdfReader(new FileInputStream(srcPDF));
        FileOutputStream output = new FileOutputStream(dstPDF);

        PdfStamper stamper = new PdfStamper(reader, output);

        String url = "http://vd.caib.es/1399274075742"; //PINBAL00000000000000001179161128677562589045";

        //int position =  POSICIO_PAGINA_ABAIX;
        int position = POSICIO_PAGINA_ADALT;
        //int position = POSICIO_PAGINA_DRETA; 
        //int position = POSICIO_PAGINA_ESQUERRA;

        int posicioTaulaDeFirmes = Constants.TAULADEFIRMES_DARRERAPAGINA;
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
        // "dd/MM/yyyy HH:mm:ss");

        String missatge = " HOLA CARCOLA Data Validació: "  + url 
           + " DAta " + new Date().toString() 
           
           //+ " XXXXX  XXXXXXXXXXXXXX "
           
           + " Plugin: " + barcode.getClass().getName() 
           //+ " Special= {4}"

           //+ " XXXXXXXXXXXX XXXXXXXXXXXXX XXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXX XXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXX XXXXXXXXXXXXXX"
           ;
        
        String barcodeText = url;
        
        String pagines = "*";

        // IBarcodePlugin barcode = new PDF417IText();
        PdfUtils.addCustodiaInfo(reader, stamper, new StampCustodiaInfo(position, missatge,
            barcode[i], barcodeText, pagines), posicioTaulaDeFirmes);

        stamper.close();

        log.info(" --- FINAL ----");

      } catch (Exception e) {
        e.printStackTrace();
      } catch (I18NException e) {
        log.info(" Error = " + I18NLogicUtils.getMessage(e, new Locale("ca")));
        e.printStackTrace();
      }
    }

  }

  public static void main2(String[] args) {

    File parent = new File("C:\\Documents and Settings\\anadal\\Escritorio\\PortaSIB\\proves");

    File srcPDF = new File(parent, "hola.pdf");
    File dstPDF = new File("..\\applet\\web\\hola_amb_taula.pdf");

    log.info("TIPUS MIME = " + Utils.getMimeType("hola.pdf"));

    int numFirmes = 18;
    numFirmes = (numFirmes > APPLET_MAX_FIRMES_PER_TAULA) ? APPLET_MAX_FIRMES_PER_TAULA
        : numFirmes;
    log.info("Numero firmes a imprimir = " + numFirmes);
    int posicio = (int) TAULADEFIRMES_PRIMERAPAGINA;
    String signantLabel = "Signant";
    String resumLabel = "Resum de Firmes";
    String descLabel = "Descripció";
    String desc = "Ammmmmmmm Bmmmmmmmm "
        + "Cmmmmmmmm Dmmmmmmmm Emmmmmmmm Fmmmmmmmm Gmmmmmmmm "
        + "Hmmmmmmmm Immmmmmmm Jmmmmmmmm Kmmmmmmmm Lmmmmmmmm " + "Mmmmmmmmm Nmmmmmmmm...";
    log.info(desc.length());

    String titolLabel = "Títol";
    String titol = "Ammmmmmmmm Bmmmmmmmmm Cmmmmmmmmm Dmmmmmm";
    log.info(titol.length());
    // File logoFile = new File("logotaulafirmesfundaciobit.jpg");
    File logoFile = new File("logotaulafirmescaib.jpg");
    // File logoFile = new File("logotaulafirmesfundaciobit2.jpg");

    File[] attachments = new File[] { new File(parent, "hola.txt") };
    String[] attachmentsNames = new String[] { "hola.txt" };

    try {

      log.info(" INICI XX " + dstPDF.getAbsolutePath());
      // File logoFile = null;

      PdfUtils.add_TableSign_Attachments_CustodyInfo(srcPDF, dstPDF, attachments,
          attachmentsNames, null, new StampTaulaDeFirmes(numFirmes, posicio, signantLabel,
              resumLabel, descLabel, desc, titolLabel, titol, logoFile), null);

    } catch (Throwable e) {
      e.printStackTrace();
    }

  }

  public static void main3(String[] args) {

    try {

      File plantillaPdf = new File(
          "D:\\dades\\dades\\CarpetesPersonals\\Programacio\\PortaFIB\\scripts\\templates\\formulariDelegacio.pdf");

      // TODO Check Plantilla

      File dstPDF = new File("holaxx.pdf");
      if (dstPDF.exists()) {
        dstPDF.delete();
      }

      PdfReader reader = new PdfReader(new FileInputStream(plantillaPdf));
      PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dstPDF), '\0', false);
      AcroFields fields = stamper.getAcroFields();

      fields.setField("DESTINATARI.NOM", "nomDest");

      fields.setField("DESTINATARI.NIF", "destinatari.getNif()");

      fields.setField("DELEGAT.NOM", "Utils.getOnlyNom(delegat.getUsuariPersona())");

      fields.setField("DELEGAT.NIF", "delegat.getNif()");

      fields.setField("MOTIU", "delegacio.getMotiu()");

      fields.setField("DOCUMENTS", "documents");

      fields.setField("DEST", "nomDest");

      fields.setField("DATA", new SimpleDateFormat().format(new Date()));

      stamper.setFormFlattening(true);

      stamper.close();

    } catch (Exception e) {
      // TODO: handle exception
    }
  }

}