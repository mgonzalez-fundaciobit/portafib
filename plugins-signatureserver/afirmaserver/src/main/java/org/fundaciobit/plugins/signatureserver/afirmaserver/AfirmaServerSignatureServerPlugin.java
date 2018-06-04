package org.fundaciobit.plugins.signatureserver.afirmaserver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.BindingProvider;

import net.java.xades.security.xml.XMLSignatureElement;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.fundaciobit.plugins.signature.api.CommonInfoSignature;
import org.fundaciobit.plugins.signature.api.FileInfoSignature;
import org.fundaciobit.plugins.signature.api.PolicyInfoSignature;
import org.fundaciobit.plugins.signature.api.StatusSignature;
import org.fundaciobit.plugins.signature.api.StatusSignaturesSet;
import org.fundaciobit.plugins.signatureserver.api.AbstractSignatureServerPlugin;
import org.fundaciobit.plugins.signatureserver.miniappletutils.SMIMEInputStream;
import org.fundaciobit.plugins.signatureserver.miniappletutils.MIMEInputStream;
import org.fundaciobit.plugins.signature.api.SignaturesSet;
import org.fundaciobit.plugins.utils.Base64;
import org.fundaciobit.plugins.utils.FileUtils;
import org.fundaciobit.plugins.utils.XTrustProvider;
import org.fundaciobit.plugins.utils.cxf.CXFUtils;
import org.fundaciobit.plugins.utils.cxf.ClientHandler;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import es.gob.afirma.afirma5ServiceInvoker.Afirma5ServiceInvokerException;
import es.gob.afirma.i18n.Language;
import es.gob.afirma.integraFacade.GenerateMessageResponse;
import es.gob.afirma.integraFacade.ValidateRequest;
import es.gob.afirma.integraFacade.pojo.ServerSignerResponse;
import es.gob.afirma.integraFacade.pojo.UpgradeSignatureRequest;
import es.gob.afirma.transformers.TransformersConstants;
import es.gob.afirma.transformers.TransformersFacade;
import es.gob.afirma.utils.Base64Coder;
import es.gob.afirma.utils.GeneralConstants;
import es.gob.afirma.utils.DSSConstants.AlgorithmTypes;
import es.gob.afirma.utils.DSSConstants.DSSTagsRequest;
import es.gob.afirma.utils.DSSConstants.SignTypesURIs;
import es.gob.afirma.utils.DSSConstants.SignatureForm;
import es.gob.afirma.utils.DSSConstants.XmlSignatureMode;

/**
 *
 * @author anadal
 *
 */
public class AfirmaServerSignatureServerPlugin extends AbstractSignatureServerPlugin implements
    ValidateSignatureConstants {

  protected static final Map<String, String> hashAlgorithmMap = new HashMap<String, String>();

  static {
    hashAlgorithmMap.put("SHA1", "http://www.w3.org/2000/09/xmldsig#sha1");
    hashAlgorithmMap.put("SHA-256", "http://www.w3.org/2001/04/xmlenc#sha256");
    hashAlgorithmMap.put("SHA-384", "http://www.w3.org/2001/04/xmldsig-more#sha384");
    hashAlgorithmMap.put("SHA-512", "http://www.w3.org/2001/04/xmlenc#sha512");
  }

  public static final String AFIRMASERVER_BASE_PROPERTIES = SIGNATURESERVER_BASE_PROPERTY
      + "afirmaserver.";

  public static final String APPLICATIONID_SENSE_SEGELLLAT_DE_TEMPS_PROPERTY = AFIRMASERVER_BASE_PROPERTIES
      + "applicationID";

  public static final String APPLICATIONID_AMB_SEGELLLAT_DE_TEMPS_PROPERTY = AFIRMASERVER_BASE_PROPERTIES
      + "applicationID_TimeStamp";

  public static final String DEFAULT_ALIAS_CERTIFICATE_PROPERTY = AFIRMASERVER_BASE_PROPERTIES
      + "defaultAliasCertificate";

  public static final String TRANSFORMERSTEMPLATESPATH_PROPERTY = AFIRMASERVER_BASE_PROPERTIES
      + "TransformersTemplatesPath";

  public static final String ENDPOINT_SIGN = AFIRMASERVER_BASE_PROPERTIES + "endpoint";

  public static final String ENDPOINT_UPGRADE = AFIRMASERVER_BASE_PROPERTIES
      + "endpoint_upgrade";

  public static final String IGNORE_SERVER_CERTIFICATES = AFIRMASERVER_BASE_PROPERTIES
      + "ignoreservercertificates";

  /**
   * 
   */
  public AfirmaServerSignatureServerPlugin() {
    super();
  }

  /**
   * @param propertyKeyBase
   * @param properties
   */
  public AfirmaServerSignatureServerPlugin(String propertyKeyBase, Properties properties) {
    super(propertyKeyBase, properties);
  }

  /**
   * @param propertyKeyBase
   */
  public AfirmaServerSignatureServerPlugin(String propertyKeyBase) {
    super(propertyKeyBase);
  }

  protected boolean isIgnoreServerCertificates() {
    String val = getProperty(IGNORE_SERVER_CERTIFICATES);
    if ("true".equalsIgnoreCase(val)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 
   * @return true true indica que el plugin accepta generadors de Segell de Temps definits dins
   *         FileInfoSignature.timeStampGenerator
   */
  @Override
  public boolean acceptExternalTimeStampGenerator(String signType) {

    return false;
    /*
     * if (FileInfoSignature.SIGN_TYPE_CADES.equals(signType) ||
     * FileInfoSignature.SIGN_TYPE_SMIME.equals(signType)) { return true; } else { return
     * false; }
     */
    /*
     * if (FileInfoSignature.SIGN_TYPE_PADES.equals(signType)) { return true; } else if
     * (FileInfoSignature.SIGN_TYPE_XADES.equals(signType)) { // Per ara MiniApplet no suporta
     * firma de XadesT return false; } else { log.warn("S'ha cridat a " +
     * this.getClass().getName() +
     * "::acceptExternalTimeStampGenerator amb un tipus de firma no controlat:" + signType);
     * return false; }
     */
  }

  /**
   * 
   * @return true, indica que el plugin internament ofereix un generador de segellat de temps.
   */
  @Override
  public boolean providesTimeStampGenerator(String signType) {
    // TODO AQUI HEM DE VEURE SI l'applicationID de @FIRMA permet estampador
    // de segell de temps

    log.info("AfirmaServerSignatureServerPlugin::providesTimeStampGenerator() => ENTRA");

    String applicationID_TS = getProperty(APPLICATIONID_AMB_SEGELLLAT_DE_TEMPS_PROPERTY);
    if (applicationID_TS == null || applicationID_TS.trim().length() == 0) {
      log.warn("La propietat " + APPLICATIONID_AMB_SEGELLLAT_DE_TEMPS_PROPERTY
          + " està buida: No suportam Segellat de temps");
    } else {

      // NOTA: Amb aplication amb suport de firma només he aconseguit
      // que es generin firmes Xades amb segell de temps
      if (FileInfoSignature.SIGN_TYPE_XADES.equals(signType)
          || FileInfoSignature.SIGN_TYPE_CADES.equals(signType)
          || FileInfoSignature.SIGN_TYPE_SMIME.equals(signType)
      // || FileInfoSignature.SIGN_TYPE_PADES.equals(signType)
      ) {
        return true;
      } else {
        if (FileInfoSignature.SIGN_TYPE_PADES.equals(signType)) {
          log.warn("No sabem per quina raó el plugin [" + getName(new Locale("ca"))
              + "] no afegeix Segell de Temps en les firmes PAdES");
        }
      }
    }
    return false;
  }

  /**
   * 
   * @return true indica que el plugin accepta generadors del imatges de la Firma Visible PDF
   *         definits dins FileInfoSignature.pdfInfoSignature.rubricGenerator.
   */
  @Override
  public boolean acceptExternalRubricGenerator() {
    return false;
  }

  /**
   * 
   * @return true, indica que el plugin internament ofereix un generador de imatges de la Firma
   *         Visible PDF.
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
    return new String[] { FileInfoSignature.SIGN_TYPE_PADES,
        FileInfoSignature.SIGN_TYPE_XADES, FileInfoSignature.SIGN_TYPE_CADES,
        FileInfoSignature.SIGN_TYPE_SMIME };
  }

  @Override
  public String[] getSupportedSignatureAlgorithms(String signType) {

    if (FileInfoSignature.SIGN_TYPE_PADES.equals(signType)
        || FileInfoSignature.SIGN_TYPE_XADES.equals(signType)
        || FileInfoSignature.SIGN_TYPE_CADES.equals(signType)) {

      return new String[] { FileInfoSignature.SIGN_ALGORITHM_SHA1,
          FileInfoSignature.SIGN_ALGORITHM_SHA256, FileInfoSignature.SIGN_ALGORITHM_SHA384,
          FileInfoSignature.SIGN_ALGORITHM_SHA512 };
    }
    if (FileInfoSignature.SIGN_TYPE_SMIME.equals(signType)) {
      return new String[] { FileInfoSignature.SIGN_ALGORITHM_SHA1 };
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
    return "AfirmaServerSignatureServerPlugin";
  }

  @Override
  public boolean filter(SignaturesSet signaturesSet) {

    final boolean suportXAdES_T = true;

    final boolean isc = isIgnoreServerCertificates();
    log.info("+ IgnoreServerCertificates = " + isc);
    if (/* endpoint.toLowerCase().startsWith("https") && */isc) {
      XTrustProvider.install();
    }

    if (checkFilter(this, signaturesSet, suportXAdES_T, this.log)) {
      return checkConnection();
    }

    return false;
  }

  public String getAliasCertificate(SignaturesSet signaturesSet) throws Exception {
    String usr = signaturesSet.getCommonInfoSignature().getUsername();
    if (usr != null) {
      return usr;
    } else {
      return getPropertyRequired(DEFAULT_ALIAS_CERTIFICATE_PROPERTY);
    }
  }

  protected boolean isDebug() {
    return "true".equals(getProperty(AFIRMASERVER_BASE_PROPERTIES + "debug"));
  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------------------ FIRMAR
  // --------------------------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  /**
   * 
   */
  @Override
  public SignaturesSet signDocuments(SignaturesSet signaturesSet, String timestampUrlBase) {

    final CommonInfoSignature commonInfoSignature = signaturesSet.getCommonInfoSignature();

    FileInfoSignature[] fileInfoArray = signaturesSet.getFileInfoSignatureArray();

    signaturesSet.getStatusSignaturesSet().setStatus(StatusSignaturesSet.STATUS_IN_PROGRESS);

    long start;

    Locale locale = new Locale(commonInfoSignature.getLanguageUI());

    for (int i = 0; i < fileInfoArray.length; i++) {

      start = System.currentTimeMillis();
      FileInfoSignature fileInfo = fileInfoArray[i];

      try {
        final boolean debug = isDebug();

        byte[] bytesToSign = FileUtils.readFromFile(fileInfo.getFileToSign());

        // FIRMA PADES o Xades
        StatusSignature ss = fileInfo.getStatusSignature();

        ss.setStatus(StatusSignature.STATUS_IN_PROGRESS);

        String algorisme;
        {
          final String algorithm = fileInfo.getSignAlgorithm();
          if (debug) {
            log.info(" XYZ ZZZ @FIRMA SERVER: SIGN_ALGO PRE [algorithm] = " + algorithm);
          }
          if (FileInfoSignature.SIGN_ALGORITHM_SHA1.equals(algorithm)) {
            algorisme = AlgorithmTypes.SHA1;
          } else if (FileInfoSignature.SIGN_ALGORITHM_SHA256.equals(algorithm)) {
            algorisme = AlgorithmTypes.SHA256;
          } else if (FileInfoSignature.SIGN_ALGORITHM_SHA384.equals(algorithm)) {
            algorisme = AlgorithmTypes.SHA384;
          } else if (FileInfoSignature.SIGN_ALGORITHM_SHA512.equals(algorithm)) {
            algorisme = AlgorithmTypes.SHA512;
          } else {
            throw new Exception("Algorisme no suportat: " + algorithm);
          }
          log.info(" XYZ ZZZ @FIRMA SERVER: SIGN_ALGO POST [algorisme] = " + algorisme);
        }

        // Instanciamos un mapa con los parámetros de entrada
        Map<String, Object> inParams = new HashMap<String, Object>();
        
        final String tipusFirma = fileInfo.getSignType();
        String signType;
        String signMode = null;
        if (FileInfoSignature.SIGN_TYPE_PADES.equals(tipusFirma)) {
          signType = SignTypesURIs.PADES;          
        } else if (FileInfoSignature.SIGN_TYPE_XADES.equals(fileInfo.getSignType())) {
          signType = SignTypesURIs.XADES_V_1_3_2;
        } else if (FileInfoSignature.SIGN_TYPE_CADES.equals(fileInfo.getSignType())
            || FileInfoSignature.SIGN_TYPE_SMIME.equals(fileInfo.getSignType())) {
          signType = SignTypesURIs.CADES;
        } else {
          // TODO Falta CADes,

          // "Tipus de Firma amb ID {0} no està suportat pel plugin
          // `{1}`
          String msg = getTraduccio("tipusfirma.desconegut", locale, fileInfo.getSignType(),
              this.getName(locale));

          ss.setErrorMsg(msg);
          ss.setErrorException(null);
          ss.setStatus(StatusSignature.STATUS_FINAL_ERROR);
          continue;
        }

        // MODE PER XADES I CADES
        if (!FileInfoSignature.SIGN_TYPE_PADES.equals(tipusFirma)) {
          if (fileInfo.getSignMode() == FileInfoSignature.SIGN_MODE_EXPLICIT) {
            signMode = XmlSignatureMode.DETACHED;
          } else if (fileInfo.getSignMode() == FileInfoSignature.SIGN_MODE_IMPLICIT) {
            signMode = XmlSignatureMode.ENVELOPING;
          } else {
            String msg = getTraduccio("modefirma.desconegut", locale, fileInfo.getSignType(),
                this.getName(locale));

            ss.setErrorMsg(msg);
            ss.setErrorException(null);
            ss.setStatus(StatusSignature.STATUS_FINAL_ERROR);
            continue;
          }
        }



        // Indicamos los datos a firmar

        if (FileInfoSignature.SIGN_TYPE_SMIME.equals(fileInfo.getSignType())) {
          // SMIME
          String mimeType = fileInfo.getMimeType();
          if (mimeType == null || mimeType.trim().length() == 0) {
            mimeType = "application/octet-stream";
          }

          MIMEInputStream mis = new MIMEInputStream(new ByteArrayInputStream(bytesToSign),
              mimeType);

          bytesToSign = FileUtils.toByteArray(mis); // IOUtils.toByteArray(mis);
          mis.close();
        }

        final String applicationID;
        if (fileInfo.isUserRequiresTimeStamp()) {
          applicationID = getPropertyRequired(APPLICATIONID_AMB_SEGELLLAT_DE_TEMPS_PROPERTY);
        } else {
          applicationID = getPropertyRequired(APPLICATIONID_SENSE_SEGELLLAT_DE_TEMPS_PROPERTY);
        }

        boolean isXML = CXFUtils.isXMLFormat(bytesToSign);
        if (debug) {
          log.info("Doc ES XML: " + isXML);
          log.info("applicationID = " + applicationID);
        }

        if (!FileInfoSignature.SIGN_TYPE_PADES.equals(tipusFirma) && isXML) {
          inParams.put(DSSTagsRequest.BASE64XML, new String(Base64.encode(bytesToSign)));
        } else {
          inParams.put(DSSTagsRequest.BASE64DATA, new String(Base64.encode(bytesToSign)));
        }

        // Indicamos el nombre del documento a firmar
        inParams.put(DSSTagsRequest.ADDITIONAL_DOCUMENT_NAME, fileInfo.getName());

        // Indicamos el tipo del documento a firmar
        inParams.put(DSSTagsRequest.ADDITIONAL_DOCUMENT_TYPE, fileInfo.getMimeType());

        // Indicamos el alias del certificado que usar para generar la
        // firma
        inParams.put(DSSTagsRequest.KEY_SELECTOR, getAliasCertificate(signaturesSet));

        // Indicamos el identificador de aplicación cliente definida en
        // @firma que usar para generar la firma
        inParams.put(DSSTagsRequest.CLAIMED_IDENTITY, applicationID);

        // Indicamos el tipo de firma a generar
        inParams.put(DSSTagsRequest.SIGNATURE_TYPE, signType);

        if (signMode != null) {
          inParams.put(DSSTagsRequest.XML_SIGNATURE_MODE, signMode);
        }

        // Indicamos el algoritmo de hash que usar para generar la firma
        inParams.put(DSSTagsRequest.HASH_ALGORITHM, algorisme);

        // Indicamos el formato de la firma a generar
        PolicyInfoSignature pis = commonInfoSignature.getPolicyInfoSignature();
        if (FileInfoSignature.SIGN_TYPE_PADES.equals(tipusFirma)) {
          
          if (fileInfo.isUserRequiresTimeStamp()) {
            // https://ssweb.seap.minhap.es/ayuda/seguimiento
            // identificador de consulta: 426066
            // número de seguimiento: 553439
            //inParams.put(DSSTagsRequest.SIGNATURE_FORM, "urn:afirma:dss:1.0:profile:XSS:PAdES:1.1.2:forms:LTV");
            inParams.put(DSSTagsRequest.SIGNATURE_FORM, "urn:afirma:dss:1.0:profile:XSS:AdES:forms:T-Level");
          } else {

            if (pis != null) {
              inParams.put(DSSTagsRequest.SIGNATURE_FORM, SignatureForm.PADES_EPES);
            } else {
              inParams.put(DSSTagsRequest.SIGNATURE_FORM, SignatureForm.PADES_BES);
  
              // inParams.put(DSSTagsRequest.SIGNATURE_FORM,
              // SignatureForm.PADES_EPES);
              // inParams.put(DSSTagsRequest.SIGPOL_SIGNATURE_POLICY_IDENTIFIER,"2.16.724.1.3.1.1.2.1.9");
            }
          }
        } else {

          // TODO XYZ ZZZ Que passa amb CADES I XADES !!!!!
          if (pis != null) {
            inParams.put(DSSTagsRequest.SIGNATURE_FORM, SignatureForm.EPES);
          }

        }

        if (pis != null) {

          inParams.put(DSSTagsRequest.SIGPOL_SIGNATURE_POLICY_IDENTIFIER,
              pis.getPolicyIdentifier());

          String algHash = hashAlgorithmMap.get(pis.getPolicyIdentifierHashAlgorithm());
          if (algHash == null) {
            throw new Exception("No es troba l'algorisme en que està firmat el Hash: "
                + pis.getPolicyIdentifierHashAlgorithm());
          }

          // AQUEST ALGORISME ES DE LA FIRMA NO DE LA POLITICA
          // !!!!!!!!
          // inParams.put(DSSTagsRequest.HASH_ALGORITHM, algHash);

          // TODO XYZ XYZ FALTA ALGO URL i HASH
          // inParams.put(DSSTagsRequest., );
        }

        TransformersFacade transformersFacade = getTransformersFacade();

        if (debug) {

          StringBuffer inputProperties = new StringBuffer();

          for (String key : inParams.keySet()) {

            inputProperties.append(key + " => ");
            Object obj = inParams.get(key);
            if (obj instanceof String) {
              inputProperties.append((String) obj);
            } else {
              inputProperties.append(" [ BINARY VALUE]");
            }
            inputProperties.append("\n");

          }

          log.info(" ============ INPUT PROPERTIES ==============\n" + inputProperties + "\n");

        }

        // Construimos el XML que constituye la petici�n
        String xmlInput = transformersFacade.generateXml(inParams,
            GeneralConstants.DSS_AFIRMA_SIGN_REQUEST, GeneralConstants.DSS_AFIRMA_SIGN_METHOD,
            TransformersConstants.VERSION_10);

        if (debug) {
          log.info(" XML_INPUT\n" + xmlInput);
        }

        String xmlOutput = cridadaWsSign(xmlInput);

        if (debug) {
          log.info(" XML_OUTPUT  =\n" + xmlOutput);
        }

        // Parseamos la respuesta en un mapa
        Map<String, Object> propertiesResult = transformersFacade.parseResponse(xmlOutput,
            GeneralConstants.DSS_AFIRMA_SIGN_REQUEST, GeneralConstants.DSS_AFIRMA_SIGN_METHOD,
            TransformersConstants.VERSION_10);

        if (propertiesResult != null) {
          ServerSignerResponse response = new ServerSignerResponse();
          GenerateMessageResponse.generateServerSignerResponse(propertiesResult, response);

          String result = response.getResult().getResultMajor();

          if (debug) {
            log.info("RESULT = " + result);
          }

          if (result != null && result.endsWith("Success")) {

            byte[] signedData = response.getSignature();

            if (signedData == null) {
              // BUG del generateServerSignerResponse()

              // SIGNATURE_DS =
              // "dss:SignatureObject/ds:Signature";
              signedData = propertiesResult.get(DSSTagsRequest.SIGNATURE_DS).toString()
                  .getBytes();
            }

            File firmat = File.createTempFile("AfirmaServerPlugin", "signedfile");

            if (FileInfoSignature.SIGN_TYPE_SMIME.equals(fileInfo.getSignType())) {
              // SMIME

              String mimeType = fileInfo.getMimeType();
              if (mimeType == null || mimeType.trim().length() == 0) {
                mimeType = "application/octet-stream";
              }

              FileInputStream fis = new FileInputStream(fileInfo.getFileToSign());

              SMIMEInputStream smis = new SMIMEInputStream(signedData, fis, mimeType);

              FileOutputStream fos = new FileOutputStream(firmat);
              org.fundaciobit.plugins.utils.FileUtils.copy(smis, fos);

              smis.close();
              fis.close();
              fos.flush();
              fos.close();

            } else {

              FileOutputStream fos = new FileOutputStream(firmat);
              fos.write(signedData);
              fos.flush();
              fos.close();
            }

            // Buidar memòria
            signedData = null;

            ss.setSignedData(firmat);
            ss.setStatus(StatusSignature.STATUS_FINAL_OK);

          } else {
            // String msg = (String)
            // propertiesResult.get(transformersFacade.getParserParameterValue("ResultMessage"));
            String msg = response.getResult().getResultMessage();

            if (msg == null || msg.trim().length() == 0) {
              // msg = (String)
              // propertiesResult.get(transformersFacade.getParserParameterValue("ResultMinor"));
              msg = response.getResult().getResultMinor();
            }

            if (msg == null || msg.trim().length() == 0) {
              // TODO XYZ ZZZ Traduir
              throw new Exception("No es pot determinar l'estat final de la petició de firma");
            } else {
              // TODO XYZ ZZZ Traduir
              throw new Exception("Error durant la firma: " + msg);
            }
          }

        } else {
          // TODO XYZ ZZZ Traduir
          throw new Exception(
              "No es pot determinar l'estat final de la petició de firma (XML de retorn està buit)");
        }

        if (log.isDebugEnabled()) {
          log.debug("Firma amb ID " + fileInfo.getSignID() + " finalitzada en "
              + (System.currentTimeMillis() - start) + "ms ");
        }

      } catch (Throwable th) {
        // TODO Mirar certs tipus d'excepció

        String param = fileInfo.getName() + " (" + i + ")[" + th.getClass().getName() + "]:"
            + th.getMessage();
        String msg = getTraduccio("error.firmantdocument", locale, param);

        log.error(msg, th);

        StatusSignature ss = getStatusSignature(signaturesSet, i);

        ss.setStatus(StatusSignature.STATUS_FINAL_ERROR);

        ss.setErrorException(th);

        ss.setErrorMsg(msg);
      }
    }

    signaturesSet.getStatusSignaturesSet().setStatus(StatusSignaturesSet.STATUS_FINAL_OK);

    return signaturesSet;

  }

  /**
   * Tiquet (a.10) Afegir mètodes d'extensió de firma: upgradeSignature() i
   * supportUpgradeSignature() #167 https://github.com/GovernIB/portafib/issues/167
   * 
   * @param upgSigReq
   * @return
   * @throws Exception
   */
  public ServerSignerResponse upgradeSignature(UpgradeSignatureRequest upgSigReq)
      throws Exception {
    ServerSignerResponse serSigRes = new ServerSignerResponse();
    String resultValidate = ValidateRequest.validateUpgradeSignatureRequest(upgSigReq);

    TransformersFacade transformersFacade = getTransformersFacade();

    // try {
    if (resultValidate != null) {
      throw new Afirma5ServiceInvokerException(Language.getFormatResIntegra("IFWS020",
          new Object[] { resultValidate }));
    }
    Map<String, Object> inputParameters = generateUpgradeSignatureRequest(upgSigReq);
    if (inputParameters != null) {
      String xmlInput = transformersFacade.generateXml(inputParameters, "DSSAfirmaVerify",
          "verify", "1_0");

      // String xmlOutput =
      // Afirma5ServiceInvokerFacade.getInstance().invokeService(xmlInput,
      // "DSSAfirmaVerify", "verify", upgSigReq.getApplicationId());

      String xmlOutput = cridadaWsUpgrade(xmlInput);

      Map<String, Object> propertiesResult = transformersFacade.parseResponse(xmlOutput,
          "DSSAfirmaVerify", "verify", "1_0");
      if (propertiesResult != null) {
        GenerateMessageResponse.generateServerSignerResponse(propertiesResult, serSigRes);
      }
    }
    /*
     * } catch (Exception e) { log.error((Object)Language.getFormatResIntegra("IFWS031", new
     * Object[]{e.getMessage()})); }
     */
    return serSigRes;
  }

  public static Map<String, Object> generateUpgradeSignatureRequest(
      UpgradeSignatureRequest upgSigReq) {
    HashMap<String, Object> inputParameters = new HashMap<String, Object>();
    if (upgSigReq.getSignature() != null) {
      incorporateSignatureImplicit(inputParameters, upgSigReq.getSignature());
    } else {
      inputParameters.put("dss:SignatureObject/dss:Other/afxp:SignatureArchiveId",
          upgSigReq.getTransactionId());
      inputParameters.put(
          "dss:SignatureObject/dss:Other/cmism:getContentStream/cmism:repositoryId", upgSigReq
              .getSignatureRepository().getId());
      inputParameters.put(
          "dss:SignatureObject/dss:Other/cmism:getContentStream/cmism:objectId", upgSigReq
              .getSignatureRepository().getObject());
    }
    inputParameters.put("dss:OptionalInputs/dss:ClaimedIdentity/dss:Name",
        upgSigReq.getApplicationId());
    if (upgSigReq.getSignatureFormat() != null) {
      inputParameters.put("dss:OptionalInputs/dss:ReturnUpdatedSignature@Type", upgSigReq
          .getSignatureFormat().getUriFormat());
    }
    if (upgSigReq.getTargetSigner() != null) {
      String encodedTargetSigner = null;
      try {
        encodedTargetSigner = new String(Base64Coder.encodeBase64(upgSigReq.getTargetSigner()));
      } catch (Exception e) {
        // XYZ ZZZ
        e.printStackTrace();
      }
      inputParameters.put("dss:OptionalInputs/afxp:TargetSigner", encodedTargetSigner);
    }
    if (upgSigReq.isIgnoreGracePeriod()) {
      inputParameters.put("dss:OptionalInputs/afxp:IgnoreGracePeriod", "");
    }
    return inputParameters;
  }

  private static void incorporateSignatureImplicit(Map<String, Object> inputParameters,
      byte[] signature) {
    try {
      if (!CXFUtils.isXMLFormat(signature)) {
        inputParameters.put("dss:SignatureObject/dss:Base64Signature",
            new String(Base64Coder.encodeBase64(signature)));
      } else {

        String typeOfESignature = getXAdESFormat(signature);
        if (SIGNFORMAT_IMPLICIT_ENVELOPING_ATTACHED.equals(typeOfESignature)) {
          inputParameters.put("dss:SignatureObject", new String(signature));
        } else if ("XAdES Enveloped".equals(typeOfESignature)
            || "XAdES Detached".equals(typeOfESignature)) {
          String idSignaturePtr = String.valueOf(Math.random() * 9999.0);
          inputParameters.put("dss:SignatureObject/dss:SignaturePtr@WhichDocument",
              idSignaturePtr);
          inputParameters.put("dss:InputDocuments/dss:Document@ID", idSignaturePtr);
          inputParameters.put("dss:InputDocuments/dss:Document/dss:Base64XML", new String(
              Base64.encode((byte[]) signature)));
        }
      }
    } catch (Exception e) {
      // XYZ ZZZ
      e.printStackTrace();
    }
  }

  /**
   * AQUEST MÈTODE ESTA DUPLICAT AL PLUGIN-INTEGR@
   * 
   * @param eSignature
   * @return
   * @throws SigningException
   */
  public static String getXAdESFormat(byte[] signature) throws Exception {

    DocumentBuilderFactory dBFactory = DocumentBuilderFactory.newInstance();
    dBFactory.setNamespaceAware(true);

    org.w3c.dom.Document eSignature = dBFactory.newDocumentBuilder().parse(
        new ByteArrayInputStream(signature));

    XMLSignature xmlSignature;
    String rootName = eSignature.getDocumentElement().getNodeName();
    if (rootName.equalsIgnoreCase("ds:Signature") || rootName.equals("ROOT_COSIGNATURES")) {
      // "XAdES Enveloping"
      return SIGNFORMAT_IMPLICIT_ENVELOPING_ATTACHED;
    }
    NodeList signatureNodeLs = eSignature.getElementsByTagName("ds:Manifest");
    if (signatureNodeLs.getLength() > 0) {
      // "XAdES Externally Detached
      return SIGNFORMAT_EXPLICIT_EXTERNALLY_DETACHED;
    }
    NodeList signsList = eSignature.getElementsByTagNameNS(
        "http://www.w3.org/2000/09/xmldsig#", "Signature");
    if (signsList.getLength() == 0) {
      throw new Exception(Language.getResIntegra("XS003"));
    }
    org.w3c.dom.Node signatureNode = signsList.item(0);
    try {
      xmlSignature = new XMLSignatureElement((Element) signatureNode).getXMLSignature();
    } catch (MarshalException e) {
      throw new Exception(Language.getResIntegra("XS005"), e);
    }
    List<?> references = xmlSignature.getSignedInfo().getReferences();
    for (int i = 0; i < references.size(); ++i) {
      if (!"".equals(((Reference) references.get(i)).getURI()))
        continue;
      // "XAdES Enveloped"
      return SIGNFORMAT_IMPLICIT_ENVELOPED_ATTACHED;
    }
    // "XAdES Detached"
    return SIGNFORMAT_EXPLICIT_DETACHED;
  }

  /*
   * Al utilitzar import net.java.xades.util.XMLUtils.isXMLFormat(bytesToSign); tal i com fa la
   * capa de FACADE llavors llança aquest error.
   * 
   * 
   * java.lang.VerifyError: (class: es/gob/afirma/utils/UtilsSignature, method:
   * validatePAdESEnhancedOptionalAttributes signature: (Les/gob/afirma/signature
   * /pades/PDFSignatureDictionary;Lorg/bouncycastle/asn1
   * /cms/AttributeTable;Lorg/bouncycastle/asn1/cms/AttributeTable;Z)V) Incompatible argument
   * to function at net.java.xades.util.XMLUtils.isXMLFormat(XMLUtils.java:535) at
   * org.fundaciobit .plugins.signatureserver.afirmaserver.AfirmaServerSignatureServerPlugin
   * .signDocuments(AfirmaServerSignatureServerPlugin.java:376) at org.fundaciobit
   * .plugins.signatureserver.afirmaserver.test.AfirmaServerTest.signFile
   * (AfirmaServerTest.java:199) at org.fundaciobit.plugins.signatureserver.afirmaserver
   * .test.AfirmaServerTest.main(AfirmaServerTest.java:105) Error Firma 1. MSG = Error firmant
   * document hola.pdf (0)[java.lang.VerifyError]:(class: es/gob/afirma/utils/UtilsSignature,
   * method: validatePAdESEnhancedOptionalAttributes signature: (Les/gob/afirma/signature
   * /pades/PDFSignatureDictionary;Lorg/bouncycastle/asn1
   * /cms/AttributeTable;Lorg/bouncycastle/asn1/cms/AttributeTable;Z)V) Incompatible argument
   * to function java.lang.Exception: Error firmant document hola.pdf
   * (0)[java.lang.VerifyError]:(class: es/gob/afirma/utils/UtilsSignature, method:
   * validatePAdESEnhancedOptionalAttributes signature: (Les/gob/afirma/signature
   * /pades/PDFSignatureDictionary;Lorg/bouncycastle/asn1
   * /cms/AttributeTable;Lorg/bouncycastle/asn1/cms/AttributeTable;Z)V) Incompatible argument
   * to function at org.fundaciobit.plugins.signatureserver
   * .afirmaserver.test.AfirmaServerTest.signFile(AfirmaServerTest.java:216) at org
   * .fundaciobit.plugins.signatureserver.afirmaserver.test.AfirmaServerTest .main
   * (AfirmaServerTest.java:105)
   */

  @Override
  public byte[] generateTimeStamp(String signaturesSetID, int signatureIndex,
      byte[] inputRequest) throws Exception {

    // No Acceptam EXTERNAL TIMESTAMPER ?????
    throw new Exception("NO SUPORTAT");

  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------------ U T I L I T A T S
  // ---------------------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  @Override
  public String getResourceBundleName() {
    return "afirmaserver";
  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------- METODES COMUNICACIO AFIRMA FEDERAT
  // ---------------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  private static void addSoftwareLibrary(File file) throws Exception {
    Method method = URLClassLoader.class
        .getDeclaredMethod("addURL", new Class[] { URL.class });
    method.setAccessible(true);
    method.invoke(ClassLoader.getSystemClassLoader(), new Object[] { file.toURI().toURL() });
  }

  protected TransformersFacade getTransformersFacade() throws Exception {

    String newClassPath = getPropertyRequired(TRANSFORMERSTEMPLATESPATH_PROPERTY);

    if (isDebug()) {
      log.info("getTransformersFacade()::newClassPath=" + newClassPath);
    }

    addSoftwareLibrary(new File(newClassPath));

    TransformersFacade transformersFacade = TransformersFacade.getInstance();

    Properties transfProp = (Properties) FieldUtils.readField(transformersFacade,
        "transformersProperties", true);

    // S'obte d'una propietat
    transfProp.put("TransformersTemplatesPath",
        getPropertyRequired(TRANSFORMERSTEMPLATESPATH_PROPERTY));

    return transformersFacade;
  }

  // Cache

  protected org.fundaciobit.plugins.signatureserver.afirmaserver.apiws.DSSSignature apiSign = null;

  protected long lastConnectionSign = 0;

  public synchronized String cridadaWsSign(String inputXml) throws Exception {

    // Cada 10 minuts refem la comunicació
    long now = System.currentTimeMillis();
    if (lastConnectionSign + 10 * 60 * 1000L < now) {
      lastConnectionSign = now;
      apiSign = null;
    }

    if (apiSign == null) {

      String endPoint = getPropertyRequired(ENDPOINT_SIGN);
      boolean debug = isDebug();
      if (debug) {
        log.info("ENDPOINT = " + endPoint);
      }

      if (isIgnoreServerCertificates()) {
        XTrustProvider.install();
      }

      final ClientHandler clientHandler;
      clientHandler = CXFUtils.getClientHandler(this, AFIRMASERVER_BASE_PROPERTIES);

      if (debug) {
        log.info("ClientHandler Class = " + clientHandler.getClass());
      }

      org.fundaciobit.plugins.signatureserver.afirmaserver.apiws.DSSSignatureService service;
      service = new org.fundaciobit.plugins.signatureserver.afirmaserver.apiws.DSSSignatureService(
          new java.net.URL(endPoint + "?wsdl"));
      apiSign = service.getDSSAfirmaSign();

      Map<String, Object> reqContext = ((BindingProvider) apiSign).getRequestContext();
      reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);

      clientHandler.addSecureHeader(apiSign);

    }

    String xmlResposta = apiSign.sign(inputXml);

    return xmlResposta;

  }

  protected org.fundaciobit.plugins.signatureserver.afirmaserver.validarfirmaapi.DSSSignature apiUpgrade = null;

  protected long lastConnectionUpgrade = 0;

  public synchronized String cridadaWsUpgrade(String inputXml) throws Exception {

    // Cada 10 minuts refem la comunicació
    long now = System.currentTimeMillis();
    if (lastConnectionUpgrade + 10 * 60 * 1000L < now) {
      lastConnectionUpgrade = now;
      apiUpgrade = null;
    }

    if (apiUpgrade == null) {

      String endPoint = getPropertyRequired(ENDPOINT_UPGRADE);
      boolean debug = isDebug();
      if (debug) {
        log.info("ENDPOINT = " + endPoint);
      }

      final ClientHandler clientHandler;
      clientHandler = CXFUtils.getClientHandler(this, AFIRMASERVER_BASE_PROPERTIES);

      if (debug) {
        log.info("ClientHandler Class = " + clientHandler.getClass());
      }

      org.fundaciobit.plugins.signatureserver.afirmaserver.validarfirmaapi.DSSSignatureService service;
      service = new org.fundaciobit.plugins.signatureserver.afirmaserver.validarfirmaapi.DSSSignatureService(
          new java.net.URL(endPoint + "?wsdl"));
      apiUpgrade = service.getDSSAfirmaVerify();

      Map<String, Object> reqContext = ((BindingProvider) apiUpgrade).getRequestContext();
      reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);

      clientHandler.addSecureHeader(apiUpgrade);

    }

    String xmlResposta = apiUpgrade.verify(inputXml);

    return xmlResposta;

  }

  /**
   * Aquets mètode servirà per validar la comunicació amb el Servidor
   * 
   * @return
   */
  protected boolean checkConnection() {

    try {

      String endpoint = getPropertyRequired(ENDPOINT_SIGN);
      try {
        log.info(" EndPoint = " + endpoint);

        URL url = new URL(endpoint + "?wsdl");
        URLConnection conn = url.openConnection();
        conn.connect();
        return true;
      } catch (Exception e) {
        log.warn(" Error connectant amb " + endpoint + ":" + e.getMessage(), e);
        return false;
      }

    } catch (Throwable e) {
      log.error("Error provant comunicació amb servidor: " + e.getMessage(), e);
      return false;
    }

    /**
     * VerifyCertificateRequest verCerReq = new VerifyCertificateRequest();
     * 
     * verCerReq.setApplicationId(applicationID);
     * 
     * InputStream is = org.fundaciobit.plugins.utils.FileUtils.readResource(
     * AfirmaServerSignatureServerPlugin.class, "check/usedtocheckconnection.cer");
     * 
     * if (is == null) { log.error("No puc llegir recurs 'check/usedtocheckconnection.cer'");
     * return false; }
     * 
     * // Indicamos el certificado que validar
     * verCerReq.setCertificate(FileUtils.toByteArray(is));
     * 
     * // Indicamos que queremos recuperar informaci�n del certificado en // lo que a campos
     * mapeados se refiere verCerReq.setReturnReadableCertificateInfo(false);
     * 
     * // Instanciamos el objeto que se encarga de a�adir opciones de // verificaci�n a la
     * petici�n VerificationReport verRep = new VerificationReport();
     * 
     * // Indicamos que queremos incluir la cadena de certificaci�n en la // respuesta
     * verRep.setIncludeCertificateValues(false);
     * 
     * // Indicamos que queremos verificar el estado de cada uno de los // certificados de la
     * cadena de certificaci�n verRep.setCheckCertificateStatus(false);
     * 
     * // Indicamos que queremos incluir informaci�n del estado de // revocaci�n de la cadena
     * de certificaci�n en la // respuesta verRep.setIncludeRevocationValues(false);
     * 
     * verRep.setReportDetailLevel(DetailLevelEnum.NO_DETAILS);
     * 
     * verCerReq.setReturnVerificationReport(verRep);
     * 
     * String resultValidate = ValidateRequest.validateVerifyCertificateRequest(verCerReq);
     * 
     * if (resultValidate != null) { throw new Afirma5ServiceInvokerException
     * (Language.getFormatResIntegra("IFWS020", new Object[] { resultValidate })); }
     * 
     * Map<String, Object> inputParameters = GenerateMessageRequest
     * .generateVerifyCertificateRequest(verCerReq);
     * 
     * if (inputParameters == null) { log.error(" inputParameters val null"); return false; }
     * 
     * TransformersFacade transformersFacade = getTransformersFacade(); String xmlInput =
     * transformersFacade.generateXml(inputParameters, "DSSAfirmaVerifyCertificate", "verify",
     * "1_0");
     * 
     * String xmlOutput = Afirma5ServiceInvokerFacade.getInstance().invokeService(xmlInput,
     * "DSSAfirmaVerifyCertificate", "verify", connectionProperties); //
     * verCerReq.getApplicationId());
     * 
     * Map<String, Object> propertiesResult = transformersFacade.parseResponse(xmlOutput,
     * "DSSAfirmaVerifyCertificate", "verify", "1_0");
     * 
     * if (propertiesResult == null) { }
     * 
     * VerifyCertificateResponse verCerRes = new VerifyCertificateResponse();
     * GenerateMessageResponse .generateVerifyCertificateResponse(propertiesResult, verCerRes);
     * 
     * if (verCerRes.getResult() != null && verCerRes.getResult().getResultMajor() != null) {
     * return true; } else { return false; }
     */

  }

}
