package org.fundaciobit.plugins.signatureserver.miniappletinserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.bouncycastle.tsp.TimeStampRequest;
import org.fundaciobit.plugins.signature.api.CommonInfoSignature;
import org.fundaciobit.plugins.signature.api.FileInfoSignature;
import org.fundaciobit.plugins.signature.api.ITimeStampGenerator;
import org.fundaciobit.plugins.signature.api.StatusSignature;
import org.fundaciobit.plugins.signature.api.StatusSignaturesSet;
import org.fundaciobit.plugins.signatureserver.api.AbstractSignatureServerPlugin;
import org.fundaciobit.plugins.signature.api.SignaturesSet;
import org.fundaciobit.plugins.signatureserver.miniappletutils.AbstractTriFaseSigner;
import org.fundaciobit.plugins.signatureserver.miniappletutils.MiniAppletInServerPAdESSigner;
import org.fundaciobit.plugins.signatureserver.miniappletutils.MiniAppletSignInfo;
import org.fundaciobit.plugins.signatureserver.miniappletutils.MiniAppletUtils;
import org.fundaciobit.plugins.signatureserver.miniappletutils.MiniAppletInServerXAdESSigner;
import org.fundaciobit.plugins.utils.CertificateUtils;
import org.fundaciobit.plugins.utils.PublicCertificatePrivateKeyPair;

/**
 *
 * @author anadal
 *
 */
public class MiniAppletInServerSignatureServerPlugin extends AbstractSignatureServerPlugin {

  private static final String FIELD_P12PASSWORD = "p12password";

  private static final String FIELD_P12FILENAME = "p12filename";

  private static final String FILENAME_PROPERTIES = "cert.properties";

  public static final String MINIAPPLETINSERVER_BASE_PROPERTIES = SIGNATURESERVER_BASE_PROPERTY
      + "miniappletinserver.";

  public static final String BASE_DIR = MINIAPPLETINSERVER_BASE_PROPERTIES + "base_dir";

  protected static File miniappletInServerBasePath = null;

  public File getPropertyBasePath() {
    if (miniappletInServerBasePath == null) {
      final File base = new File(getProperty(BASE_DIR), "SERVER_MINIAPPLETINSERVER");
      base.mkdirs();

      miniappletInServerBasePath = base;

      log.info("MiniAppletInServerSignatureServerPlugin = "
          + miniappletInServerBasePath.getAbsolutePath());

    }
    return miniappletInServerBasePath;
  }

  /**
   * 
   */
  public MiniAppletInServerSignatureServerPlugin() {
    super();
  }

  /**
   * @param propertyKeyBase
   * @param properties
   */
  public MiniAppletInServerSignatureServerPlugin(String propertyKeyBase, Properties properties) {
    super(propertyKeyBase, properties);
  }

  /**
   * @param propertyKeyBase
   */
  public MiniAppletInServerSignatureServerPlugin(String propertyKeyBase) {
    super(propertyKeyBase);
  }

  /**
   * 
   * @return true true indica que el plugin accepta generadors de Segell de
   *         Temps definits dins FileInfoSignature.timeStampGenerator
   */
  @Override
  public boolean acceptExternalTimeStampGenerator(String signType) {

    if (FileInfoSignature.SIGN_TYPE_PADES.equals(signType)) {
      return true;
    } else if (FileInfoSignature.SIGN_TYPE_XADES.equals(signType)) {
      // Per ara MiniApplet no suporta firma de XadesT
      return false;
    } else {
      log.warn("S'ha cridat a " + this.getClass().getName()
          + "::acceptExternalTimeStampGenerator amb un tipus de firma no controlat:"
          + signType);
      return false;
    }
  }

  /**
   * 
   * @return true, indica que el plugin internament ofereix un generador de
   *         segellat de temps.
   */
  @Override
  public boolean providesTimeStampGenerator(String signType) {
    return false;
  }

  /**
   * 
   * @return true indica que el plugin accepta generadors del imatges de la
   *         Firma Visible PDF definits dins
   *         FileInfoSignature.pdfInfoSignature.rubricGenerator.
   */
  @Override
  public boolean acceptExternalRubricGenerator() {
    return true;
  }

  /**
   * 
   * @return true, indica que el plugin internament ofereix un generador de
   *         imatges de la Firma Visible PDF.
   */
  @Override
  public boolean providesRubricGenerator() {
    return false;
  }

  @Override
  public boolean acceptExternalSecureVerificationCodeStamper() {
    return false;
  }

  @Override
  public boolean providesSecureVerificationCodeStamper() {
    return false;
  }

  @Override
  public String[] getSupportedSignatureTypes() {
    // TODO Falta CADes, ...
    return new String[] { FileInfoSignature.SIGN_TYPE_PADES, FileInfoSignature.SIGN_TYPE_XADES };
  }

  @Override
  public String[] getSupportedSignatureAlgorithms(String signType) {

    if (FileInfoSignature.SIGN_TYPE_PADES.equals(signType)
        || FileInfoSignature.SIGN_TYPE_XADES.equals(signType)) {

      return new String[] { FileInfoSignature.SIGN_ALGORITHM_SHA1,
          FileInfoSignature.SIGN_ALGORITHM_SHA256, FileInfoSignature.SIGN_ALGORITHM_SHA384,
          FileInfoSignature.SIGN_ALGORITHM_SHA512 };
    }
    return null;
  }

  @Override
  public List<String> getSupportedBarCodeTypes() {
    // Aquests Plugins No suporten estampació de CSV per si mateixos
    return null;
  }

  @Override
  public String getName(Locale locale) {
    return getTraduccio("pluginname", locale);
  }

  @Override
  protected String getSimpleName() {
    return "MiniAppletInServerSignatureServerPlugin";
  }

  @Override
  public boolean filter(SignaturesSet signaturesSet) {

    // Per ara esta un poc complicat revisar els certificats, ja que sempre s'ha
    // de
    // mostrar ja que l'usuari sempre te l'opció d'afegir Certificats

    // Requerim un username
    if (signaturesSet.getCommonInfoSignature().getUsername() != null) {
      return super.filter(signaturesSet);
    }

    return false;
  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------------------ FIRMAR --------------------------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  
  
  public static final Map<String, SignaturesSet> tmpCache = new HashMap<String, SignaturesSet>();

  @Override
  public SignaturesSet signDocuments(SignaturesSet signaturesSet, String timestampUrlBase) {
    
    try {
      // Guardam dins la cache pel tema del Segellat de Temps
      tmpCache.put(signaturesSet.getSignaturesSetID(), signaturesSet);
    

      final CommonInfoSignature commonInfoSignature = signaturesSet.getCommonInfoSignature();
  
      final String username = commonInfoSignature.getUsername();
      
      final String signaturesSetID =  signaturesSet.getSignaturesSetID();
  
      InfoCertificate cinfo;
  
      try {
        cinfo = getCertificateOfUser(username, commonInfoSignature.getLanguageUI());
      } catch (Exception e) {
  
        // No te certificats certificat
        // Locale locale = new Locale(commonInfoSignature.getLanguageUI());
        // String warn = getTraduccio("warn.notecertificats", locale);
  
        StatusSignaturesSet sss = signaturesSet.getStatusSignaturesSet();
        sss.setStatus(StatusSignaturesSet.STATUS_FINAL_ERROR);
        sss.setErrorMsg(e.getMessage());
        return signaturesSet;
      }
  
      File p12file = cinfo.p12File;
  
      String p12Password = cinfo.p12Password;
  
      // Check PAIR
      PublicCertificatePrivateKeyPair pair;
      try {
        FileInputStream fis = new FileInputStream(p12file);
        pair = CertificateUtils.readPKCS12(fis, p12Password);
        fis.close();
      } catch (Exception e) {
  
        // String msg = "Error llegint fitxer P12 (" + p12file.getAbsolutePath() + ")."
        //    + " Consulti amb l'Administrador. Error: " + e.getMessage();
        String msg = getTraduccio("error.fitxer.p12", 
            new Locale(commonInfoSignature.getLanguageUI()),
            p12file.getAbsolutePath(), e.getMessage());
  
        StatusSignaturesSet sss = signaturesSet.getStatusSignaturesSet();
        sss.setStatus(StatusSignaturesSet.STATUS_FINAL_ERROR);
        sss.setErrorMsg(msg);
        return signaturesSet;
      }
  
      FileInfoSignature[] fileInfoArray = signaturesSet.getFileInfoSignatureArray();
  
      // int errors = 0;
      X509Certificate certificate = pair.getPublicCertificate();
      PrivateKey privateKey = pair.getPrivateKey();
  
      signaturesSet.getStatusSignaturesSet().setStatus(StatusSignaturesSet.STATUS_IN_PROGRESS);
  
      long start;
  
      Locale locale = new Locale(signaturesSet.getCommonInfoSignature().getLanguageUI());
  
      for (int i = 0; i < fileInfoArray.length; i++) {
  
        start = System.currentTimeMillis();
        FileInfoSignature fileInfo = fileInfoArray[i];
        
        String timestampUrl;
        if (timestampUrlBase == null || fileInfo.getTimeStampGenerator() == null) {
          timestampUrl = null;
        } else {
          timestampUrl = timestampUrlBase + "/" + signaturesSetID + "/" + i;
        }
  
        try {
  
          MiniAppletSignInfo info;
          info = MiniAppletUtils.convertLocalSignature(commonInfoSignature, fileInfo,
              timestampUrl, certificate);
  
          if (FileInfoSignature.SIGN_TYPE_PADES.equals(fileInfo.getSignType())
              || FileInfoSignature.SIGN_TYPE_XADES.equals(fileInfo.getSignType())) {
  
            // FIRMA PADES o Xades
            StatusSignature ss = fileInfo.getStatusSignature();
  
            ss.setStatus(StatusSignature.STATUS_IN_PROGRESS);
  
            final String algorithm = info.getSignAlgorithm();
            byte[] signedData;
  
            if (FileInfoSignature.SIGN_TYPE_PADES.equals(fileInfo.getSignType())) {
  
              AbstractTriFaseSigner cloudSign = new MiniAppletInServerPAdESSigner(privateKey);
  
              signedData = cloudSign.fullSign(info.getDataToSign(), algorithm,
                  new Certificate[] { info.getCertificate() }, info.getProperties());
            } else {
  
              log.debug("Passa per XAdESSigner");
              MiniAppletInServerXAdESSigner xadesSigner = new MiniAppletInServerXAdESSigner();
  
  //            StringWriter sw = new StringWriter();
  //            info.getProperties().store(sw, "PropertiesDemo");
  //            log.info("XADES PROPERTIES:\n" + sw.toString() );
  
              signedData = xadesSigner.sign(info.getDataToSign(), algorithm, privateKey,
                  new Certificate[] { info.getCertificate() }, info.getProperties());
  
            }
  
            File firmat = null;
  
            firmat = File.createTempFile("MAISSigServerPlugin", "signedfile");
  
            FileOutputStream fos = new FileOutputStream(firmat);
            fos.write(signedData);
            fos.flush();
            fos.close();
            // Buidar memòria
            signedData = null;
  
            ss.setSignedData(firmat);
            ss.setStatus(StatusSignature.STATUS_FINAL_OK);
  
          } else {
            // TODO Falta CADes,
  
            // "Tipus de Firma amb ID {0} no està suportat pel plugin `{1}`
            String msg = getTraduccio("tipusfirma.desconegut", locale, fileInfo.getSignType(),
                this.getName(locale));
  
            StatusSignature ss = fileInfo.getStatusSignature();
            ss.setErrorMsg(msg);
            ss.setErrorException(null);
            ss.setStatus(StatusSignature.STATUS_FINAL_ERROR);
          }
  
          if (log.isDebugEnabled()) {
            log.debug("Firma amb ID " + fileInfo.getSignID() + " finalitzada en "
                + (System.currentTimeMillis() - start) + "ms ");
          }
  
        } catch (Throwable th) {
          // TODO Mirar certs tipus d'excepció
  
          String param = fileInfo.getName()
              + " (" + i + ")[" + th.getClass().getName() + "]:" + th.getMessage();
          String msg = getTraduccio("error.firmantdocument", locale, param);
              
  
          log.error(msg, th);
  
          StatusSignature ss = getStatusSignature(signaturesSet, i);
  
          ss.setStatus(StatusSignature.STATUS_FINAL_ERROR);
  
          ss.setErrorException(th);
  
          ss.setErrorMsg(msg);
        }
      }
  
      signaturesSet.getStatusSignaturesSet().setStatus(StatusSignaturesSet.STATUS_FINAL_OK);
      
    } finally {
      // Ho eliminam de la cache
      tmpCache.remove(signaturesSet.getSignaturesSetID());
    }

    return signaturesSet;

  }

  @Override
  public byte[] generateTimeStamp(String signaturesSetID,
      int signatureIndex, byte[] inputRequest) throws Exception  {

    final boolean isDebug = log.isDebugEnabled();
    
    SignaturesSet signaturesSet = tmpCache.get(signaturesSetID);
    if (signaturesSet == null) {      
      throw new Exception(" Dins la cache de SignaturesSet no hi ha cap element amb id "
          + signaturesSetID);
    }


    // DEL MINIAPPLET SEMPRE REBREM UNA TimeStampRequest encoded
    TimeStampRequest tsr = new TimeStampRequest(inputRequest);

    byte[] inputData = tsr.getMessageImprintDigest();

    BigInteger time = tsr.getNonce();
    
    FileInfoSignature fileInfo = signaturesSet.getFileInfoSignatureArray()[signatureIndex];

    ITimeStampGenerator timeStampGen = fileInfo.getTimeStampGenerator();

    if (timeStampGen == null) {
      throw new Exception("El generador de TimeStamp per la petició amb id "
          + signaturesSetID  +  " val null");
    } 

    try {
      final Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(time.longValue());
      byte[] returnedData = timeStampGen.getTimeStamp(inputData, cal);

      if (isDebug && returnedData != null) {
        log.info("requestTimeStamp:: returnedData LEN= " + returnedData.length);
        log.info("requestTimeStamp:: returnedData DATA= " + new String(returnedData));
      }

      return returnedData;
    } catch (Exception e) {
      throw new Exception("Error greu cridant el TimeStampGenerator: " + e.getMessage(), e);
    }

  }
  
  


  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ---------------------- U T I L I T A T S H T M L -------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  public File getUserNamePath(String username) {
    return new File(getPropertyBasePath(), username);
  }

  /**
   * 
   * @return Key del map apunta a un Directori
   */
  public InfoCertificate getCertificateOfUser(String username, String lang) throws Exception {

    File userPath = getUserNamePath(username);
    final Locale locale = new Locale(lang);
    
    if (!userPath.exists()) {
      // "No existeix el directori "
      throw new Exception(getTraduccio("error.directori.noexisteix", locale, 
          userPath.getAbsolutePath()));
    }

    File propsFile = new File(userPath, FILENAME_PROPERTIES);

    if (!propsFile.exists()) {
      // "No existeix el fitxer "
      throw new Exception(getTraduccio("error.fitxer.noexisteix", locale, 
          propsFile.getAbsolutePath()));
    }

    Properties prop = readPropertiesFromFile(propsFile);

    if (prop == null) {
      // "No s'han pogut llegir les propietats del fitxer "
      throw new Exception(
          getTraduccio("error.nopropietats", locale,propsFile.getAbsolutePath()));

    }

    String p12Password = prop.getProperty(FIELD_P12PASSWORD);
    if (p12Password == null || p12Password.trim().length() == 0) {
      // "No s'ha definit la propietat " " en el fitxer "
      throw new Exception(
          getTraduccio("error.nopropietatdefinida", locale, FIELD_P12PASSWORD,
          propsFile.getAbsolutePath()));
    }

    String filename = prop.getProperty(FIELD_P12FILENAME);
    if (filename == null || filename.trim().length() == 0) {
      // "No s'ha definit la propietat " " en el fitxer "
      throw new Exception(getTraduccio("error.nopropietatdefinida", locale,
          FIELD_P12FILENAME, propsFile.getAbsolutePath()));
    }

    File p12File = new File(userPath, filename);

    if (!p12File.exists()) {
      throw new Exception(getTraduccio("error.fitxer.noexisteix", locale, 
          p12File.getAbsolutePath()));
    }

    return new InfoCertificate(p12File, p12Password);

  }

  @Override
  public String getResourceBundleName() {
    return "serverminiappletinserver";
  }

  /**
   * 
   * @author anadal
   *
   */
  private static class InfoCertificate {
    public final File p12File;
    public final String p12Password;

    /**
     * @param p12File
     * @param p12Password
     */
    public InfoCertificate(File p12File, String p12Password) {
      super();
      this.p12File = p12File;
      this.p12Password = p12Password;
    }

  }

}