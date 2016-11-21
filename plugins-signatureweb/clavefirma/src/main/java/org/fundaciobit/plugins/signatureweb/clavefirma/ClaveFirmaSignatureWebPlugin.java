package org.fundaciobit.plugins.signatureweb.clavefirma;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fundaciobit.plugins.signature.api.CommonInfoSignature;
import org.fundaciobit.plugins.signature.api.FileInfoSignature;
import org.fundaciobit.plugins.signature.api.StatusSignature;
import org.fundaciobit.plugins.signature.api.StatusSignaturesSet;
import org.fundaciobit.plugins.signatureserver.miniappletutils.MiniAppletSignInfo;
import org.fundaciobit.plugins.signatureserver.miniappletutils.MiniAppletUtils;
import org.fundaciobit.plugins.signatureweb.api.SignaturesSetWeb;
import org.fundaciobit.plugins.signatureweb.miniappletutils.AbstractMiniAppletSignaturePlugin;
import org.fundaciobit.plugins.utils.CertificateUtils;

import es.gob.afirma.core.misc.AOUtil;
import es.gob.clavefirma.client.HttpCertificateBlockedException;
import es.gob.clavefirma.client.HttpForbiddenException;
import es.gob.clavefirma.client.HttpNetworkException;
import es.gob.clavefirma.client.HttpNoUserException;
import es.gob.clavefirma.client.certificatelist.HttpCertificateList;
import es.gob.clavefirma.client.generatecert.GenerateCertificateResult;
import es.gob.clavefirma.client.generatecert.HttpCertificateAvailableException;
import es.gob.clavefirma.client.generatecert.HttpGenerateCertificate;
import es.gob.clavefirma.client.generatecert.HttpUserWeakRegistryException;
import es.gob.clavefirma.client.signprocess.HttpLoadProcess;
import es.gob.clavefirma.client.signprocess.HttpSignProcess;
import es.gob.clavefirma.client.signprocess.HttpSignProcessConstants;
import es.gob.clavefirma.client.signprocess.HttpSignProcessConstants.SignatureAlgorithm;
import es.gob.clavefirma.client.signprocess.HttpSignProcessConstants.SignatureFormat;
import es.gob.clavefirma.client.signprocess.HttpSignProcessConstants.SignatureOperation;
import es.gob.clavefirma.client.signprocess.HttpSignProcessConstants.SignatureUpgrade;
import es.gob.clavefirma.client.signprocess.LoadResult;

/**
 * XYZ ZZZ Revisar cas de no te certificats i solicitar-ne un => On guardar ID
 * 
 * @author anadal
 *
 */
public class ClaveFirmaSignatureWebPlugin extends AbstractMiniAppletSignaturePlugin {
  // AbstractSignatureWebPlugin {

  public static final String CLAVEFIRMA_BASE_PROPERTIES = SIGNATUREWEB_BASE_PROPERTY
      + "clavefirma.";

  public static final String PROPERTY_APPID = CLAVEFIRMA_BASE_PROPERTIES + "appid";

  public static final String PROPERTY_PROCEDURE = CLAVEFIRMA_BASE_PROPERTIES + "procedure";
  
  public static final String PROPERTY_CLIENT_CONFIG_PROPERTIES_PATH = CLAVEFIRMA_BASE_PROPERTIES
      + "client_properties_config_path";

  private static final String PROPERTY_MAPPING_USERS_PATH = CLAVEFIRMA_BASE_PROPERTIES
      + "mappingusers";

  private static final String PROPERTY_USERS_PATTERN = CLAVEFIRMA_BASE_PROPERTIES
      + "userspattern";

  // XYZ ??????
  private static final String PROPERTY_CALLBACK_HOST = CLAVEFIRMA_BASE_PROPERTIES
      + "callbackhost";

  public static final String IGNORE_CERTIFICATE_FILTER = CLAVEFIRMA_BASE_PROPERTIES
      + "ignore_certificate_filter";
  
  public static final String PROPERTY_ALLOW_CERTIFICATE_GENERATION =
      CLAVEFIRMA_BASE_PROPERTIES + "allowcertificategeneration";
  
  public static final String PROPERTY_SHOW_INFO_WHEN_NON_REGISTERED_USER =
      CLAVEFIRMA_BASE_PROPERTIES + "showinfowhennonregistereduser";
  
  

  protected Map<String, ClaveFirmaSignInformation[]> transactions = new HashMap<String, ClaveFirmaSignInformation[]>();

  /**
   *
   */
  public ClaveFirmaSignatureWebPlugin() {
    super();
  }

  /**
   * @param propertyKeyBase
   * @param properties
   */
  public ClaveFirmaSignatureWebPlugin(String propertyKeyBase, Properties properties) {
    super(propertyKeyBase, properties);
  }

  /**
   * @param propertyKeyBase
   */
  public ClaveFirmaSignatureWebPlugin(String propertyKeyBase) {
    super(propertyKeyBase);
  }
  
  
  protected boolean permetreGeneracioDeCertificat() {
    return "true".equalsIgnoreCase(getProperty(PROPERTY_ALLOW_CERTIFICATE_GENERATION));
  }
  

  protected boolean mostrarInformacioAUsuariNoRegistrat() {
    return "true".equalsIgnoreCase(getProperty(PROPERTY_SHOW_INFO_WHEN_NON_REGISTERED_USER));
  }


  @Override
  public String getName(Locale locale) {
    return getTraduccio("pluginname", locale);
  }

  @Override
  public boolean filter(HttpServletRequest request, SignaturesSetWeb signaturesSet) {

    // Revisar si l'usuari està registrar a ClaveFirma i si té certificats
    // de firma en aquest entorn.
    CommonInfoSignature common = signaturesSet.getCommonInfoSignature();

    String username = common.getUsername();
    String administrationID = common.getAdministrationID();
    String filter = common.getFiltreCertificats();


    if (checkCertificates(username, administrationID, filter)) {
      return super.filter(request, signaturesSet);
    } else {
      return false;
    }
  }

  /**
   * 
   * @param username
   * @param administrationID
   * @param filter
   * @return
   */
  private boolean checkCertificates(String username, String administrationID, String filter) {

    try {

      List<X509Certificate> map = listCertificates(username, administrationID);
      if (map == null || map.size() == 0) {
        if (permetreGeneracioDeCertificat()) {
          return true;
        } else {
          return false;
        }
        
      }

      
      int certificatsDisponibles;
      if ("true".equals(getProperty(IGNORE_CERTIFICATE_FILTER))) {
        // Ignoram el filtre de certificats
        certificatsDisponibles = map.size();
      } else {
        boolean passFilter;
        certificatsDisponibles = 0;
        for (X509Certificate ci : map) {
          try {
            X509Certificate cert = ci;
            passFilter = MiniAppletUtils.matchFilter(cert, filter);
          } catch (Exception e) {
            log.error(
                " Error comprovant filtre Certificat " + ci.getIssuerDN() + ": "
                    + e.getMessage(), e);
            passFilter = false;
          }
          if (passFilter) {
            certificatsDisponibles++;
          }
        }
      }
      return certificatsDisponibles != 0;
    
      
    } catch (HttpCertificateBlockedException se) {
      log.warn("filter:: L'usuari té el certificat bloquejat: " + se.getMessage() , se);
      return false;
    } catch (HttpNoUserException se) {
      
      if (mostrarInformacioAUsuariNoRegistrat()) {
        return true;
      } else {
        log.warn("filter:: L'usuari no està donat d'alta en el sistema ClaveFirma", se);
        return false;
      }
    } catch (Throwable e) {
      log.error("filter:: Unknown Error " + e.getMessage(), e);
      return false;
    }
  }

  @Override
  public void closeSignaturesSet(HttpServletRequest request, String id) {
    transactions.remove(id);
    super.closeSignaturesSet(request, id);
  }

  @Override
  public void requestGET(String absolutePluginRequestPath, String relativePluginRequestPath,
      String relativePath, SignaturesSetWeb signaturesSet, int signatureIndex,
      HttpServletRequest request, HttpServletResponse response, Locale locale) {

    if (relativePath.startsWith(SELECT_CERTIFICATE_PAGE)) {
      selectCertificateGET(absolutePluginRequestPath, relativePluginRequestPath, relativePath,
          request, response, signaturesSet, signatureIndex, locale);
    } else if (relativePath.startsWith(FIRMAR_POST_PAGE)) {
        firmarPostOk(request, response, signaturesSet, signatureIndex, locale);
    } else if (relativePath.startsWith(NO_REGISTRAT_PAGE)) {
        // L'usuari no està donat d'alta a Cl@ve Firma
        noRegistratPage(absolutePluginRequestPath, relativePluginRequestPath, request,
            response, signaturesSet, signatureIndex, locale);
    } else if (relativePath.startsWith(GENERAR_NOU_CERTIFICAT_PAGE)) {
        generarNouCertificat(absolutePluginRequestPath, relativePluginRequestPath,
            request, response, signaturesSet, signatureIndex, locale);    
    } else if (relativePath.startsWith(SENSE_CERTIFICATS_PAGE)) {
      // S'ha de provar si funciona
      senseCertificats(absolutePluginRequestPath, relativePluginRequestPath, request,
          response, signaturesSet, signatureIndex, locale);
    } else if (relativePath.startsWith(SIGN_ERROR_PAGE)) {
      signErrorPage(request, response, signaturesSet, signatureIndex, relativePath, locale);
    }  else if (relativePath.startsWith(CLOSE_CLAVEFIRMA_PAGE)) {

      closeClaveFirmaPage(response, locale);

    } else {

      super.requestGET(absolutePluginRequestPath, relativePluginRequestPath, relativePath,
          signaturesSet, signatureIndex, request, response, locale);
    }

  }

  @Override
  public void requestPOST(String absolutePluginRequestPath, String relativePluginRequestPath,
      String relativePath, SignaturesSetWeb signaturesSet, int signatureIndex,
      HttpServletRequest request, HttpServletResponse response, Locale locale) {

    if (relativePath.startsWith(FIRMAR_PRE_PAGE)) {

      firmarPre(absolutePluginRequestPath, relativePluginRequestPath, request, response,
          signaturesSet, locale);

    } else if (relativePath.startsWith(SIGN_ERROR_PAGE)) {
      signErrorPage(request, response, signaturesSet, signatureIndex, relativePath, locale);
    } else {
      super.requestPOST(absolutePluginRequestPath, relativePluginRequestPath, relativePath,
          signaturesSet, signatureIndex, request, response, locale);

    }

  }

  /**
   * Inici del proces de firma
   * 
   * @param request
   * @param absolutePluginRequestPath
   * @param relativePluginRequestPath
   * @param signaturesSet
   * @return
   * @throws Exception
   */
  @Override
  public String signDocuments(HttpServletRequest request, String absolutePluginRequestPath,
      String relativePluginRequestPath, SignaturesSetWeb signaturesSet) throws Exception {
 
    List<X509Certificate> certificates;
    // En principi això ja no ha de llançar errors a no ser de usuari no registrat
    try {
      certificates = listCertificates(signaturesSet);
      
      if (certificates == null || certificates.size() == 0) { 
        // Si s'ha arribat aquí, es que es permet la creació de Certificats Online
        // Afegim 5 minuts pel tema de donar d'alta el certificat
        signaturesSet.setExpiryDate(new Date(signaturesSet.getExpiryDate().getTime() + 300000));
        addSignaturesSet(signaturesSet);
        return  relativePluginRequestPath + "/" + SENSE_CERTIFICATS_PAGE;
      } else {
        // Mostrar llistat de certificats per a  seleccionar-ne un 
        addSignaturesSet(signaturesSet);
        return relativePluginRequestPath + "/" +   SELECT_CERTIFICATE_PAGE;        
      }
      
    } catch (HttpNoUserException se) {

      //  L'usuari no està donat d'alta en el sistema ClaveFirma
      addSignaturesSet(signaturesSet);
      return relativePluginRequestPath + "/" +   NO_REGISTRAT_PAGE; 
    }

  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ---------------- TANCAR FINESTRA DE LA WEB DE CL@VEFIRMA -------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  private static final String CLOSE_CLAVEFIRMA_PAGE = "closeclavefirmapage";

  private void closeClaveFirmaPage(HttpServletResponse response, Locale locale) {
    PrintWriter out;
    try {

      response.setCharacterEncoding("utf-8");
      response.setContentType("text/html");

      out = response.getWriter();

      out.println("<html><head>" + "<script type=\"text/javascript\">" + "    window.close();"
          + "</script>" + "</head><body>" + "</body></html>");

      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
  
  
  
 //----------------------------------------------------------------------------
 // ---------------------------------------------------------------------------
 // -------------- USUARI NO REGISTRAT A CL@VE FIRMA --------------------------
 // ---------------------------------------------------------------------------
 // ---------------------------------------------------------------------------

 private static final String NO_REGISTRAT_PAGE = "noregistrat";

 private void noRegistratPage(String absolutePluginRequestPath,
     String relativePluginRequestPath, HttpServletRequest request,
     HttpServletResponse response, SignaturesSetWeb signaturesSet, int signatureIndex,
     Locale locale) {

   SignIDAndIndex sai = new SignIDAndIndex(signaturesSet, signatureIndex);

   PrintWriter out = generateHeader(request, response, absolutePluginRequestPath,
       relativePluginRequestPath, locale.getLanguage(), sai, signaturesSet);

   out.println("<br/><br/>");

   out.println("<center>");
   out.println("<div>");
   out.println("<p style=\"text-align:center\">");
   // EL USUARIO NO EST&Aacute; DADO DE ALTA EN EL SISTEMA
   out.println("    <h4>"  +  getTraduccio("error.noregistrat.titol", locale) +  "</h4>");
   out.println("</p>");
   out.println("</div>");
   out.println("<div>");
   out.println("<p>");
   out.println(getTraduccio("error.noregistrat.missatge", locale));
   out.println("</p>");
   out.println("</div>");
   out.println("<button class=\"btn\" type=\"button\"  onclick=\"location.href='"
       + relativePluginRequestPath + "/" + CANCEL_PAGE + "'\" >"
       + getTraduccio("cancel", locale) + "</button>");

   out.println("</center>");

   generateFooter(out, sai, signaturesSet);
 }
  
 
 
 // ----------------------------------------------------------------------------
 // ----------------------------------------------------------------------------
 // --------------------- SENSE CERTIFICAT - GENERAR UN DE NOU -----------------
 // ----------------------------------------------------------------------------
 // ----------------------------------------------------------------------------

 private static final String GENERAR_NOU_CERTIFICAT_PAGE = "generarnoucertificat";

 protected void generarNouCertificat(String absolutePluginRequestPath,
     String relativePluginRequestPath, HttpServletRequest request,
     HttpServletResponse response, SignaturesSetWeb signaturesSet, int signatureIndex,
     Locale locale) {
   

   String callbackhost = getProperty(PROPERTY_CALLBACK_HOST);

   String callBackURLOK;
   String callBackURLError;
   String tancarFinestraURL;
   if (callbackhost == null) {
     callBackURLOK = absolutePluginRequestPath + "/" + SELECT_CERTIFICATE_PAGE;
     callBackURLError = absolutePluginRequestPath + "/" + SIGN_ERROR_PAGE + "/ERROR_GENERANT_CERTIFICAT";
     tancarFinestraURL = absolutePluginRequestPath + "/" + CLOSE_CLAVEFIRMA_PAGE;
   } else {
     callBackURLOK = callbackhost + request.getServletPath() + "/" + SELECT_CERTIFICATE_PAGE;
     callBackURLError = callbackhost + request.getServletPath() + "/" + SIGN_ERROR_PAGE + "/ERROR_GENERANT_CERTIFICAT";
     tancarFinestraURL = callbackhost + request.getServletPath() + "/" + CLOSE_CLAVEFIRMA_PAGE;
   }

   // XYZ ZZZhauria de posar l'index nO????
   log.info("XYZ ZZZ generarNouCertificat::callBackURLOK = " + callBackURLOK);
   log.info("XYZ ZZZ generarNouCertificat::callBackURLError = " + callBackURLError);
   log.info("XYZ ZZZ generarNouCertificat::tancarFinestraURL = " + tancarFinestraURL);
   
   
   Properties config = new Properties();
   config.setProperty("redirectOkUrl", callBackURLOK);
   config.setProperty("redirectErrorUrl", callBackURLError);
   GenerateCertificateResult result;
   try {
     final CommonInfoSignature commonInfoSignature = signaturesSet.getCommonInfoSignature();

     final String appId = getPropertyRequired(PROPERTY_APPID);
     final String subjectId = getClaveFirmaUser(commonInfoSignature.getUsername(),
         commonInfoSignature.getAdministrationID());

     initApiClientLib();
     String confB64 = AOUtil.properties2Base64(config);
     result = HttpGenerateCertificate.generateCertificate(appId, subjectId, confB64);
     
     String transactionId = result.getTransactionId();
     
     log.info("XYZ ZZZ generarNouCertificat::Recibido el ID de transaccion: " + transactionId);
     
     
     String redirectUrl = result.getRedirectUrl();
     
     log.info("XYZ ZZZ generarNouCertificat::Recibida la URL de redireccion: " + redirectUrl);
     

     // XYZ ZZZ
     final boolean debug = true; // log.isDebugEnabled()

     showFullScreenPage(relativePluginRequestPath, response, locale, debug, callBackURLOK,
         callBackURLError, tancarFinestraURL, redirectUrl);
     
    } catch (HttpForbiddenException e) {
      // XYZ ZZZ Traduir
      String errorMsg = ("Error de permisos en la solicitud de certificados: " + e
          .getMessage());
      finishWithError(response, signaturesSet, errorMsg, e);
    } catch (HttpNetworkException e) {
      // XYZ ZZZ Traduir
      String errorMsg = ("Error de red en la solicitud de certificados: " + e.getMessage());
      finishWithError(response, signaturesSet, errorMsg, e);
    } catch (HttpCertificateAvailableException e) {
      // XYZ ZZZ Traduir
      String errorMsg = ("El usuario ya tiene un certificado de ese tipo: " + e.getMessage());
      finishWithError(response, signaturesSet, errorMsg, e);
    } catch (HttpUserWeakRegistryException e) {
      // XYZ ZZZ Traduir
      String errorMsg = ("El usuario no tiene acceso a esta operacion por "
          + " registrarse mediante autenticacion debil: " + e.getMessage());
      finishWithError(response, signaturesSet, errorMsg, e);

    } catch (Exception e) {
      // XYZ ZZZ Traduir
      String errorMsg = ("Error en la solicitud de certificado: " + e.getMessage());
      finishWithError(response, signaturesSet, errorMsg, e);
    }
   
   
   
 }
 
 

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------------------ SENSE CERTIFICATS ---------------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  private static final String SENSE_CERTIFICATS_PAGE = "sensecertificats";

  private void senseCertificats(String absolutePluginRequestPath,
      String relativePluginRequestPath, HttpServletRequest request,
      HttpServletResponse response, SignaturesSetWeb signaturesSet, int signatureIndex,
      Locale locale) {

    SignIDAndIndex sai = new SignIDAndIndex(signaturesSet, signatureIndex);

    PrintWriter out = generateHeader(request, response, absolutePluginRequestPath,
        relativePluginRequestPath, locale.getLanguage(), sai, signaturesSet);

    out.println("<br/><br/>");

    out.println("<center>");
    

    out.println("<h4>" + getTraduccio("generarnoucertificat.titol", locale) + "" + "</h4>");
    
    
    
    out.println("<div style=\"margin-top:30px;text-align: center; \">");
    out.println("  <label>");
    out.println("   <i>" + getTraduccio("generarnoucertificat.info", locale) + "</i>");
    out.println("  </label><br><br>");
    
    out.println("<button class=\"btn\" type=\"button\"  onclick=\"location.href='"
        + relativePluginRequestPath + "/" + CANCEL_PAGE + "'\" >"
        + getTraduccio("cancel", locale) + "</button> &nbsp;&nbsp;");
    
    out.println("<button class=\"btn btn-primary\" type=\"button\"  onclick=\"location.href='"
        + relativePluginRequestPath + "/" + GENERAR_NOU_CERTIFICAT_PAGE + "'\" >"
        + getTraduccio("generarnoucertificat.boto", locale) + "</button>");
    
    
    out.println("</div>");
    
    
    out.println("<br/>");



    out.println("</center>");

    generateFooter(out, sai, signaturesSet);
  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------ FIRMAR PRE
  // ----------------------------------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  private static final String FIRMAR_PRE_PAGE = "firmarpre";

  protected void firmarPre(String absolutePluginRequestPath, String relativePluginRequestPath,
      HttpServletRequest request, HttpServletResponse response,
      SignaturesSetWeb signaturesSet, Locale locale) {

    log.info("XYZ ZZZ firmarPre::absolutePluginRequestPath=" + absolutePluginRequestPath);
    log.info("XYZ ZZZ firmarPre::relativePluginRequestPath=" + relativePluginRequestPath);

    final String signaturesSetID = signaturesSet.getSignaturesSetID();
    final CommonInfoSignature commonInfoSignature = signaturesSet.getCommonInfoSignature();

    final boolean debug = log.isDebugEnabled();
    try {

      // Conté el SerialNumber del Cert
      String cert = request.getParameter("cert");
      if (debug) {
        log.debug("firmarPre:: PARAMETRE[cert] = " + cert);
      }

      List<X509Certificate> mapCert = listCertificates(signaturesSet);

      if (debug) {
        log.debug("firmarPre:: mapCert.size() = " + mapCert.size());
      }

      X509Certificate certificate = null;
      for (X509Certificate key : mapCert) {
        if (debug) {
          log.debug("firmarPre:: KEY MAP => " + key.getIssuerDN());
        }

        if (cert.equals(String.valueOf(key.getSerialNumber()))) {
          certificate = key;
          break;
        }

      }

      int pos = relativePluginRequestPath.lastIndexOf("-1");

      String baseSignaturesSet = relativePluginRequestPath.substring(0, pos - 1);

      final String appId = getPropertyRequired(PROPERTY_APPID);
      final String subjectId = getClaveFirmaUser(commonInfoSignature.getUsername(),
          commonInfoSignature.getAdministrationID());

      // TODO Check que tots els fitxers firmen amb el mateix tipus d'algorisme
      FileInfoSignature[] fileInfoArray = signaturesSet.getFileInfoSignatureArray();

      ClaveFirmaSignInformation[] claveFirmaSignInfoArray = new ClaveFirmaSignInformation[fileInfoArray.length];

      // TODO Només de la primera firma
      String callBackURLOK;
      String callBackURLError;
      String tancarFinestraURL;
      String redirectUrl = null;

      // NOMES SUPORTAM UNA FIRMA !!!!!
      for (int i = 1; i < fileInfoArray.length; i++) {
        // NOMES SUPORTAM UNA FIRMA !!!!!
        // ClaveFirma no suporta multiples firmes
        String msg =  getTraduccio("warn.nosuportamultiplesfirmes", locale, this.getName(locale));
        FileInfoSignature fileInfo = fileInfoArray[i];
        StatusSignature ss = fileInfo.getStatusSignature();
        ss.setErrorMsg(msg);
        ss.setErrorException(null);
        ss.setStatus(StatusSignature.STATUS_CANCELLED);
      }

      // TODO AQUI HI HAURIA D?AVER UN FOR for (int i = 0; i <
      // fileInfoArray.length; i++)
      int i = 0;
      {

        FileInfoSignature fileInfo = fileInfoArray[i];
        StatusSignature ss = fileInfo.getStatusSignature();

        final HttpSignProcessConstants.SignatureFormat signType;
        signType = convertSignType(fileInfo.getSignType());

        final HttpSignProcessConstants.SignatureAlgorithm signAlgorithm;
        signAlgorithm = convertSignAlgorithm(fileInfo.getSignAlgorithm());

        String timeStampUrl = null;
        if (fileInfo.getTimeStampGenerator() != null) {
          String callbackhost = getHostAndContextPath(absolutePluginRequestPath,
              relativePluginRequestPath);

          timeStampUrl = callbackhost + baseSignaturesSet + "/" + i + "/" + TIMESTAMP_PAGE;
        }
        MiniAppletSignInfo info;
        info = MiniAppletUtils.convertLocalSignature(commonInfoSignature, fileInfo,
            timeStampUrl, certificate);
        final Properties signProperties = info.getProperties();

        final byte[] dataToSign = info.getDataToSign();

        final Properties remoteConfProperties = new Properties();
        String callbackhost = getProperty(PROPERTY_CALLBACK_HOST);

        if (callbackhost == null) {
          callBackURLOK = absolutePluginRequestPath + "/" + FIRMAR_POST_PAGE;
          callBackURLError = absolutePluginRequestPath + "/" + SIGN_ERROR_PAGE;
          tancarFinestraURL = absolutePluginRequestPath + "/" + CLOSE_CLAVEFIRMA_PAGE;
        } else {
          callBackURLOK = callbackhost + request.getServletPath() + "/" + FIRMAR_POST_PAGE;
          callBackURLError = callbackhost + request.getServletPath() + "/" + SIGN_ERROR_PAGE;
          tancarFinestraURL = callbackhost + request.getServletPath() + "/" + CLOSE_CLAVEFIRMA_PAGE;
        }

        // XYZ ZZZhauria de posar l'index nO????
        log.info("XYZ ZZZ firmarPre::callBackURLOK = " + callBackURLOK);
        log.info("XYZ ZZZ firmarPre::callBackURLError = " + callBackURLError);
        log.info("XYZ ZZZ firmarPre::tancarFinestraURL = " + tancarFinestraURL);

        // redirectOkUrl: URL a la que redirigir al usuario en caso de terminar
        // correcta-mente la operación.
        remoteConfProperties.setProperty("redirectOkUrl", callBackURLOK);
        // redirectErrorUrl: URL a la que redirigir al usuario en caso de error.
        remoteConfProperties.setProperty("redirectErrorUrl", callBackURLError);
        // procedureName: Nombre del procedimiento que se ejecuta (previamente
        // dado de alta en la GISS).
        remoteConfProperties.put("procedureName", getPropertyRequired(PROPERTY_PROCEDURE));

        // Muntar Objecte

        ClaveFirmaSignInformation cfsi = new ClaveFirmaSignInformation(appId, subjectId,
            signType, signAlgorithm, signProperties, certificate, dataToSign,
            remoteConfProperties);

        LoadResult loadResult;
        try {
          log.info("XYZ ZZZ Cridam a INIT");
          initApiClientLib();

          log.info("XYZ ZZZ Cridam a HttpLoadProcess.loadData()");
          loadResult = HttpLoadProcess.loadData(cfsi.getAppId(), cfsi.getSubjectId(),
              cfsi.getSignOperation(), cfsi.getSignType(), cfsi.getSignAlgorithm(),
              cfsi.getSignProperties(), cfsi.getCertificate(), cfsi.getDataToSign(),
              cfsi.getRemoteConfProperties());

          cfsi.setLoadResult(loadResult);

          // XYZ ZZZ Print loadResult.getTriphaseData()

          ss.setStatus(StatusSignature.STATUS_IN_PROGRESS);

          claveFirmaSignInfoArray[i] = cfsi;

          String transactionId = loadResult.getTransactionId();

          log.info("XYZ ZZZ Recibido el ID de transaccion: " + transactionId);

          redirectUrl = loadResult.getRedirectUrl();

          log.info("XYZ ZZZ Recibida LA URL de redireccion: " + redirectUrl);

        } catch (Throwable th) {
          String msg;
          if (th instanceof HttpForbiddenException) {
            msg = "Error de permisos en la carga de datos: " + th.getMessage();
          } else if (th instanceof HttpNetworkException) {
            msg = "Error de red en la carga de datos: " + th.getMessage();
          } else {
            msg = "Error en la carga de datos a firmar: " + th.getMessage();
          }

          finishWithError(response, signaturesSet, msg, th);
        }

      }

      this.transactions.put(signaturesSetID, claveFirmaSignInfoArray);
      
      signaturesSet.getStatusSignaturesSet().setStatus(StatusSignaturesSet.STATUS_IN_PROGRESS);
      
      

      showFullScreenPage(relativePluginRequestPath, response, locale, debug, callBackURLOK,
          callBackURLError, tancarFinestraURL, redirectUrl);
    } catch (Exception e) {
      // TODO XYZ FILTRAR ERRORS . Veure documentacio

      // TODO Traduir
      String msg = " Error desconegut preparant l'enviament dels documents al servidor de ClaveFirma: "
          + e.getMessage();

      finishWithError(response, signaturesSet, msg, e);

    }
  }

  protected void showFullScreenPage(String relativePluginRequestPath,
      HttpServletResponse response, Locale locale, final boolean debug, String callBackURLOK,
      String callBackURLError, String tancarFinestraURL, String redirectUrl)
      throws IOException {
    if (debug) {
      log.info("callBackURLOK = " + callBackURLOK);
      log.info("callBackURLOK = " + callBackURLError);
      log.info("tancarFinestraURL = " + tancarFinestraURL);
      log.info("redireccionURL = " + redirectUrl);
    }
    

    final boolean showInNewWindow = false;

    if (showInNewWindow) {
      // OK
    } else {
      tancarFinestraURL = callBackURLOK;
    }


    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    out.println("<html>" + "\n" + "<head>" + "\n" + "<script type=\"text/javascript\">"
        + "\n");

    if (showInNewWindow) {
      out.println(" var win;" + "\n" + "    win = window.open('" + redirectUrl
          + "', '_blank', '');" + "\n" + "    var timer = setInterval(function() {" + "\n"
          + "        if (win.closed) {" + "\n" + "          clearInterval(timer);" + "\n"
          + "          document.location.href = '" + callBackURLOK + "';" + "\n"
          + "        }" + "\n" + "      }, 500);" + "\n" + " };" + "\n");
    } else {
      out.println("    var insideIframe = window.top !== window.self;" + "\n"
          + "    if(insideIframe){" + "\n" + "       window.top.location.href='"
          + redirectUrl + "';\n" + "    } else {" + "\n"
          + "       document.location.href = '" + redirectUrl + "';" + "\n" + "    };"
          + "\n");
    }

    out.println("</script>"
        + "\n"
        + "</head><body>"
        + "\n"
        + "<br/><center>"
        + "\n"
        + "<h1>"
        + getTraduccio("introduircontrasenya", locale)
        + "</h1><br/>"
        + "\n"
        + "<img src=\""
        + relativePluginRequestPath
        + "/"
        + WEBRESOURCE
        + "/img/ajax-loader2.gif\" />"
        + "\n"
        + "<br/><input id=\"clickMe\" type=\"button\" value=\"clickme\" onclick=\"xyz();\" />"
        + "\n" + "</center>" + "\n" + "</body>" + "\n" + "</html>");

    out.flush();
  }

  // ----------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  // ------------------ FIRMAR POST ERROR (SIGNERRORPAGE) ---------------------
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  private static final String SIGN_ERROR_PAGE = "signErrorPage";

  private void signErrorPage(HttpServletRequest request, HttpServletResponse response,
      SignaturesSetWeb signaturesSet, int signatureIndex, String query, Locale locale) {
    // XYZ ZZZZ
    // TODO Traduir
    log.info("XYZ ZZZ S'ha rebut una cridada a la URL d'ERROR: signaturesSetID = "
        + signaturesSet.getSignaturesSetID());
    
    log.info("signErrorPage:: QUERY = |" + query + "|");
    
    // XYZ ZZZZ
    Map<Object, Object> params = request.getParameterMap();
    for (Object key : params.keySet()) {
      log.info("signErrorPage():: param[" + key + "] = " + ((String[])params.get(key))[0]);
    }
    
    
    String msg;
    if (query.endsWith("ERROR_GENERANT_CERTIFICAT"))  {
      msg = getTraduccio("error.generantcerificat", locale);
    } else {
      msg = getTraduccio("error.autenticacio", locale);
    }
    

    finishWithError(response, signaturesSet, msg, null);
  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------ FIRMAR POST OK
  // ------------------------------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  private static final String FIRMAR_POST_PAGE = "firmarpost";

  protected void firmarPostOk(HttpServletRequest request, HttpServletResponse response,
      SignaturesSetWeb signaturesSet, int signatureIndex, Locale locale) {

    String signaturesSetID = signaturesSet.getSignaturesSetID();
    ClaveFirmaSignInformation[] firmaSignInformationArray = transactions.get(signaturesSetID);

    log.info("XYZ ZZZ");
    log.info("XYZ ZZZ firmarPostOk:: signatureIndex = " + signatureIndex);
    log.info("XYZ ZZZ");
    final int i = 0; // XYZ hauria d'assignar signatureIndex

    if (firmaSignInformationArray == null || firmaSignInformationArray[i] == null) {
      // TODO traduir
      String errorMsg = "No es pot trobar la transacció pel procés de "
          + "firma amb ID igual a " + signaturesSetID + "[" + i + "]";

      finishWithError(response, signaturesSet, errorMsg, null);
      return;
    }

    ClaveFirmaSignInformation fsi = firmaSignInformationArray[i];

    FileInfoSignature[] fileInfoArray = signaturesSet.getFileInfoSignatureArray();
    FileInfoSignature fileInfo = fileInfoArray[i];

    try {

      // Veure manual del Integrador de ClaveFirma
      final SignatureUpgrade upgrade = null;
      initApiClientLib();
      byte[] signedData = HttpSignProcess.sign(fsi.getAppId(), fsi.getLoadResult()
          .getTransactionId(), fsi.getSignOperation(), fsi.getSignType(), fsi
          .getSignAlgorithm(), fsi.getSignProperties(), fsi.getCertificate(), fsi
          .getDataToSign(), fsi.getLoadResult().getTriphaseData(), upgrade);

      // ========= CAS OK

     

      File firmat = File.createTempFile("ClaveFirmaWebPlugin", "signedfile");

      FileOutputStream fos = new FileOutputStream(firmat);
      fos.write(signedData);
      fos.flush();
      fos.close();
      // Buidar memòria
      signedData = null;

      StatusSignature ss = fileInfo.getStatusSignature();
      ss.setSignedData(firmat);
      ss.setStatus(StatusSignature.STATUS_FINAL_OK);

      signaturesSet.getStatusSignaturesSet().setStatus(StatusSignaturesSet.STATUS_FINAL_OK);

      final String url = signaturesSet.getUrlFinal();

      sendRedirect(response, url);

    } catch (Exception e) {
      // TODO XYZ FILTRAR ERRORS. Veure documentacio

      // TODO Traduir
      String msg = " Error desconegut enviant/realitzant les firmes : " + e.getMessage();

      StatusSignature ss = fileInfo.getStatusSignature();

      ss.setStatus(StatusSignature.STATUS_FINAL_ERROR);

      ss.setErrorException(e);

      ss.setErrorMsg(getTraduccio("error.firmantdocument", locale) + fileInfo.getName() + " ["
          + e.getClass().getName() + "]:" + e.getMessage());

      finishWithError(response, signaturesSet, msg, e);

    }

  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------ S E L E C T C E R T I F I C A T E -------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  private static final String SELECT_CERTIFICATE_PAGE = "selectCertificate";

  private void selectCertificateGET(String absolutePluginRequestPath,
      String relativePluginRequestPath, String relativePath, HttpServletRequest request,
      HttpServletResponse response, SignaturesSetWeb signaturesSet, int signatureIndex,
      Locale locale) {
    
    

    StringWriter sw = new StringWriter();
    try {
      
      // En principi això ja no ha de llançar errors a no ser de comunicació
      List<X509Certificate> map = listCertificates(signaturesSet);
   

      PrintWriter out = new PrintWriter(sw);

      out.println("<h3>" + getTraduccio("selectcertificat.titol", locale) + "</h3><br/>");

      

      // EL CONTROL DE QUE HI HAGI CERTIFICATS ES FA EN FILTER

      out.println("<form action=\"" + relativePluginRequestPath + "/" + FIRMAR_PRE_PAGE
          + "\" method=\"post\" >"); // enctype=\"multipart/form-data\"

      out.println("<table border=0>");
      out.println("<tr>");
      out.println("<td colspan>" + getTraduccio("certificatfirmar", locale) + ":<br/></td>");
      out.println("<td>");

      int count = 0;
      for (X509Certificate certificate : map) {

        String PROPERTY_SUBJECT = CertificateUtils
            .getCN(certificate.getSubjectDN().toString());
        String PROPERTY_ISSUER = CertificateUtils.getCN(certificate.getIssuerDN().toString());
        String PROPERTY_VALID_FROM = String.valueOf(certificate.getNotBefore().getTime());
        String PROPERTY_VALID_TO = String.valueOf(certificate.getNotAfter().getTime());

        DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);

        String from = sdf.format(new Date(Long.parseLong(PROPERTY_VALID_FROM)));
        String to = sdf.format(new Date(Long.parseLong(PROPERTY_VALID_TO)));

        out.println("<table>");
        out.println("<tr>");
        out.println("<td align=\"center\" width=\"50px\">");
        out.println("<input type=\"radio\" name=\"cert\" id=\"optionsRadios_"
            + certificate.getSerialNumber() + "\" value=\"" + certificate.getSerialNumber()
            + "\" " + ((count == 0) ? "checked" : "") + " >");

        out.println("</td>");
        out.println("<td style=\"border: 1px solid gray; padding-top:1px;\">");

        out.println("<label class=\"radio\">");

        out.println("<b>" + PROPERTY_SUBJECT + "</b><br/>");
        out.println("<i>" + PROPERTY_ISSUER + " </i><br/>");
        // Afegir dates
        String valid = getTraduccio("valid", locale);

        out.println("<small>" + MessageFormat.format(valid, from, to) + "</small>");

        out.println("</label>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        count++;

      }

      out.println("</td>");
      out.println("</tr>");

      out.println("</table>");

      out.println("<br/><br/>");

      out.println("<button class=\"btn\" type=\"button\"  onclick=\"location.href='"
          + relativePluginRequestPath + "/" + CANCEL_PAGE + "'\" >"
          + getTraduccio("cancel", locale) + "</button>");

      out.println("&nbsp;&nbsp;");
      int numFitxers = signaturesSet.getFileInfoSignatureArray().length;
      out.println("<button class=\"btn btn-primary\" type=\"submit\">"
          + getTraduccio("firmardocument" + (numFitxers == 0 ? "" : ".plural"), locale)
          + "</button>");
      out.println("</form>");
      out.flush();
      out.close();

    } catch (Exception e) {
      // XYZ Errors especifics

      finishWithError(response, signaturesSet, e.getMessage(), e);

      return;
    }

    SignIDAndIndex sai = new SignIDAndIndex(signaturesSet, signatureIndex);

    PrintWriter outS = generateHeader(request, response, absolutePluginRequestPath,
        relativePluginRequestPath, locale.getLanguage(), sai, signaturesSet);

    outS.println(sw.toString());

    generateFooter(outS, sai, signaturesSet);

    outS.flush();

  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // -------------------------- OPERACIONS API CLAVEFIRMA -------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  public List<X509Certificate> listCertificates(SignaturesSetWeb signaturesSet)
      throws Exception,  HttpCertificateBlockedException, HttpNoUserException {

    String username = signaturesSet.getCommonInfoSignature().getUsername();
    String administrationID = signaturesSet.getCommonInfoSignature().getAdministrationID();

    return listCertificates(username, administrationID);

  }

  // Cache de certificats
  private Map<String, List<X509Certificate>> cacheCertificates = new HashMap<String, List<X509Certificate>>();

  private Set<String> cacheUserWithoutClaveFirma = new HashSet<String>();

  private long lastCacheUpdate = 0;

  public List<X509Certificate> listCertificates(String username, String administrationID)
      throws Exception,  HttpCertificateBlockedException, HttpNoUserException {

    long now = System.currentTimeMillis();
    if ((lastCacheUpdate + 3600000) < now) {
      // Fer net la cache cada Hora
      cacheCertificates.clear();
      cacheUserWithoutClaveFirma.clear();
    }

    String userClaveFirma = getClaveFirmaUser(username, administrationID);
    if (cacheUserWithoutClaveFirma.contains(userClaveFirma)) {
      // L'usuari no està donat d'alta al sistema ClaveFirma
      return null;
    }

    List<X509Certificate> certificates = cacheCertificates.get(userClaveFirma);

    final String appId = getPropertyRequired(PROPERTY_APPID);

    if (certificates == null) {

      // Configura Properties de ConfigManager !!!!!
      initApiClientLib();
      certificates = listCertificatesStatic(userClaveFirma, appId);


      final boolean debug = log.isDebugEnabled();
      if (debug) {
        log.debug(" CERTIFICATS == " + certificates.size());
      }

      if (certificates != null) {
        cacheCertificates.put(userClaveFirma, certificates);
      }

    }

    return certificates;
  }

  public static List<X509Certificate> listCertificatesStatic(String userClaveFirma,
      final String appId) throws Exception, HttpCertificateBlockedException, HttpNoUserException  {
    // Listamos los certificados del usuario
    List<X509Certificate> certificates = null;
    try {

      certificates = HttpCertificateList.getList(appId, userClaveFirma);
      if (certificates == null || certificates.isEmpty()) {
        return null; // error = "0;URL='ChooseCertificateNoCerts.jsp'";
      }
      return certificates;
    } catch (final HttpNetworkException e) {
      // XYZ ZZZ Traduir
      throw new Exception("Error contactant amb el Component Central pel Llistat de Certificats: " + e.getMessage(), e);
    } 
    /*
    catch (final HttpCertificateBlockedException e) {
      // XYZ ZZZ
      throw new Exception("0;URL='ChooseCertificateCertBlocked.jsp'", e);
    } catch (final HttpNoUserException e) {
      throw new Exception("0;URL='ChooseCertificateNoUser.jsp'", e);
    }
    */
    catch (final Exception e) {
      
      if (e instanceof HttpCertificateBlockedException
          || e instanceof HttpNoUserException) {
        throw e;
      } else {
        // XYZ ZZZ TRADUIR
        System.out.println(e);
        throw new Exception("Error general llistant els certificats de l'usuari: " + e.getMessage(), e);
      }
    }
    
  }

  // ------------------------------------------------------------------
  // -------------------------------------------------------------------
  // -------------------------- CONFIGURACIO PLUGIN --------------------
  // -------------------------------------------------------------------
  // -------------------------------------------------------------------

  @Override
  public String getResourceBundleName() {
    return "clavefirma";
  }

  @Override
  protected String getSimpleName() {
    return "ClaveFirmaPlugin";
  }

  @Override
  public List<String> getSupportedBarCodeTypes() {
    return null;
  }

  @Override
  public boolean acceptExternalTimeStampGenerator(String signType) {
    return true; // A traves de propietats de miniapplet
  }

  @Override
  public boolean providesTimeStampGenerator(String signType) {
    return false;
  }

  @Override
  public boolean acceptExternalRubricGenerator() {
    return true;
  }

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
        FileInfoSignature.SIGN_TYPE_XADES, FileInfoSignature.SIGN_TYPE_CADES
    // TODO SMIME XYZ ZZZ ???
    };
  }

  @Override
  public String[] getSupportedSignatureAlgorithms(String signType) {

    if (FileInfoSignature.SIGN_TYPE_PADES.equals(signType)
        || FileInfoSignature.SIGN_TYPE_XADES.equals(signType)
        || FileInfoSignature.SIGN_TYPE_CADES.equals(signType)
    // TODO SMIME XYZ ZZZ ???
    ) {

      return new String[] { FileInfoSignature.SIGN_ALGORITHM_SHA1 };
    }
    return null;
  }

  // ------------------------------------------------------------------
  // -------------------------------------------------------------------
  // ---------- U T I L I T A T S C L A V E - F I R M A ----------------
  // -------------------------------------------------------------------
  // -------------------------------------------------------------------

  protected boolean inicialitzat = false;

  /**
   * 
   */
  protected synchronized void initApiClientLib() throws Exception {
    if (!inicialitzat) {

      String propertiesPath = getPropertyRequired(PROPERTY_CLIENT_CONFIG_PROPERTIES_PATH);

      log.info("XYZ ZZZ propertiesPath = " + propertiesPath);

      File f = new File(propertiesPath);

      log.info("XYZ ZZZ new File(propertiesPath).exists() = " + f.exists());

      staticInit(f);

      inicialitzat = true;
    }
  }

  private static void staticInit(File propertiesFile) throws Exception {
    
    String javaVersion = System.getProperty("java.version"); // => 1.6.0_33"
    
    if (javaVersion.startsWith("1.6")) {
      System.setProperty("https.protocols", "TLSv1");
    }
    

    Properties config = readPropertiesFromFile(propertiesFile);

    // private static Properties config = null;
    // es.gob.clavefirma.client.ConfigManager.class

    Field field = es.gob.clavefirma.client.ConfigManager.class.getDeclaredField("config");
    field.setAccessible(true);

    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    field.set(null, config);

    // XYZ ZZZ prova

    field = es.gob.clavefirma.client.ConfigManager.class.getDeclaredField("config");
    field.setAccessible(true);

    Properties configget = (Properties) field.get(null);

    for (Object key : configget.keySet()) {
      // XYZ ZZZ log.info
      System.out.println("XYZ ZZZ PropertiesGET[" + key + "] => " + configget.get(key));
    }

  }

  // XYZ ZZZ ELIMINAR o PASSAR A TESTS !!!!
  public static void main(String[] args) {

    try {
      
      //XTrustProvider.install();
      //System.setProperty("https.protocols", "TLSv1.1");
      
      String javaVersion = System.getProperty("java.version"); // => 1.6.0_33"
      
      if (javaVersion.startsWith("1.6")) {
        System.setProperty("https.protocols", "TLSv1");
      }
      
      for(Object key : System.getProperties().keySet()) {
        System.out.println(key + " => " + System.getProperty((String)key));
      }
      
      
      
      File propertiesFile = new File(
          "D:\\dades\\dades\\CarpetesPersonals\\Programacio\\clavefirma_apache-tomcat-8.0.20_7000\\webapps\\clavefirma_config\\client_config.properties");
      staticInit(propertiesFile);
      
      String userClaveFirma = "00001";
      String appId = "930CD4CDF298";
      

      
      List<X509Certificate> list = listCertificatesStatic(userClaveFirma, appId);
      
      for (X509Certificate x509Certificate : list) {
        System.out.println(" - " + x509Certificate.toString());
      }

    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();

    }

  }

  protected HttpSignProcessConstants.SignatureFormat convertSignType(String signType) {
    if (FileInfoSignature.SIGN_TYPE_PADES.equals(signType)) {
      return HttpSignProcessConstants.SignatureFormat.PADES;
    } else if (FileInfoSignature.SIGN_TYPE_XADES.equals(signType)) {
      return HttpSignProcessConstants.SignatureFormat.XADES;
    } else if (FileInfoSignature.SIGN_TYPE_PADES.equals(signType)) {
      return HttpSignProcessConstants.SignatureFormat.CADES;
    } else {
      // XYZ ZZZ SMIME
      log.error("Tipus de Firma no suportada fent conversió a ClaveFirma: " + signType,
          new Exception());
      return null;
    }
  }

  protected HttpSignProcessConstants.SignatureAlgorithm convertSignAlgorithm(
      String signAlgorithm) {

    if (FileInfoSignature.SIGN_ALGORITHM_SHA1.equals(signAlgorithm)) {
      return HttpSignProcessConstants.SignatureAlgorithm.SHA1WITHRSA;
    } else if (FileInfoSignature.SIGN_ALGORITHM_SHA256.equals(signAlgorithm)) {
      return HttpSignProcessConstants.SignatureAlgorithm.SHA256WITHRSA;
    } else if (FileInfoSignature.SIGN_ALGORITHM_SHA384.equals(signAlgorithm)) {
      return HttpSignProcessConstants.SignatureAlgorithm.SHA384WITHRSA;
    } else if (FileInfoSignature.SIGN_ALGORITHM_SHA512.equals(signAlgorithm)) {
      return HttpSignProcessConstants.SignatureAlgorithm.SHA512WITHRSA;
    } else {
      // XYZ ZZZ
      log.error(
          "Algorisme de Firma no suportat fent conversió a ClaveFirma: " + signAlgorithm,
          new Exception());
      return null;
    }
  };

  /**
   * Obté l'usuari ClaveFirma a partir de l'username o NIF (administrationID)
   * 
   * @param username
   *          (opcional)
   * @param administrationID
   *          És el NIF (obligatori)
   * @return
   * @throws Exception
   */
  public String getClaveFirmaUser(String username, String administrationID) throws Exception {

    boolean debug = log.isDebugEnabled();

    if (debug) {
      log.debug("getClaveFirmaUser => U: " + username + " | NIF: " + administrationID);
    }

    // Primer provam el mapping
    String mappingPath = getProperty(PROPERTY_MAPPING_USERS_PATH);

    if (mappingPath != null) {
      Properties props = readPropertiesFromFile(new File(mappingPath));
      if (props != null && username != null) {
        String newUser = props.getProperty(username);
        if (newUser != null) {
          return newUser;
        }
      }
    }

    // Si el mapping no funciona llavors provam el PATTERN
    // {0} == username || {1} == administrationID (NIF)

    String usersPattern = getProperty(PROPERTY_USERS_PATTERN);

    String newUser = null;

    if (usersPattern != null) {
      newUser = MessageFormat.format(usersPattern, username, administrationID);
    }

    if (newUser == null) {
      if (username == null) {
        newUser = administrationID;
      } else {
        newUser = username;
      }
    }

    if (debug) {
      log.debug("getClaveFirmaUser:: RETURN " + newUser);
    }

    return newUser;

  }

  public class ClaveFirmaSignInformation {

    protected final String appId;
    protected final String subjectId;
    protected final HttpSignProcessConstants.SignatureOperation signOperation;
    protected final HttpSignProcessConstants.SignatureFormat signType;
    protected final HttpSignProcessConstants.SignatureAlgorithm signAlgorithm;
    protected final Properties signProperties;
    protected final X509Certificate certificate;
    protected final byte[] dataToSign;
    protected final Properties remoteConfProperties;

    protected LoadResult loadResult;

    /**
     * @param appId
     * @param subjectId
     * @param signOperation
     * @param signType
     * @param signAlgorithm
     * @param signProperties
     * @param certificate
     * @param dataToSign
     * @param remoteConfProperties
     */
    public ClaveFirmaSignInformation(String appId, String subjectId, SignatureFormat signType,
        SignatureAlgorithm signAlgorithm, Properties signProperties,
        X509Certificate certificate, byte[] dataToSign, Properties remoteConfProperties) {
      super();
      this.appId = appId;
      this.subjectId = subjectId;
      this.signOperation = HttpSignProcessConstants.SignatureOperation.SIGN;
      this.signType = signType;
      this.signAlgorithm = signAlgorithm;
      this.signProperties = signProperties;
      this.certificate = certificate;
      this.dataToSign = dataToSign;
      this.remoteConfProperties = remoteConfProperties;
    }

    /**
     * @param appId
     * @param subjectId
     * @param signOperation
     * @param signType
     * @param signAlgorithm
     * @param signProperties
     * @param certificate
     * @param dataToSign
     * @param remoteConfProperties
     */
    public ClaveFirmaSignInformation(String appId, String subjectId,
        SignatureOperation signOperation, SignatureFormat signType,
        SignatureAlgorithm signAlgorithm, Properties signProperties,
        X509Certificate certificate, byte[] dataToSign, Properties remoteConfProperties) {
      super();
      this.appId = appId;
      this.subjectId = subjectId;
      this.signOperation = signOperation;
      this.signType = signType;
      this.signAlgorithm = signAlgorithm;
      this.signProperties = signProperties;
      this.certificate = certificate;
      this.dataToSign = dataToSign;
      this.remoteConfProperties = remoteConfProperties;
    }

    public String getAppId() {
      return appId;
    }

    public String getSubjectId() {
      return subjectId;
    }

    public HttpSignProcessConstants.SignatureOperation getSignOperation() {
      return signOperation;
    }

    public HttpSignProcessConstants.SignatureFormat getSignType() {
      return signType;
    }

    public HttpSignProcessConstants.SignatureAlgorithm getSignAlgorithm() {
      return signAlgorithm;
    }

    public Properties getSignProperties() {
      return signProperties;
    }

    public X509Certificate getCertificate() {
      return certificate;
    }

    public byte[] getDataToSign() {
      return dataToSign;
    }

    public Properties getRemoteConfProperties() {
      return remoteConfProperties;
    }

    public LoadResult getLoadResult() {
      return loadResult;
    }

    public void setLoadResult(LoadResult loadResult) {
      this.loadResult = loadResult;
    }

  }

}
