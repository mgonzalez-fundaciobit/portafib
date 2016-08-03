package org.fundaciobit.plugin.signatureweb.afirmatriphaseserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.fundaciobit.plugins.signatureweb.api.FileInfoSignature;
import org.fundaciobit.plugins.signatureweb.api.PolicyInfoSignature;
import org.fundaciobit.plugins.signatureweb.api.SignaturesSet;
import org.fundaciobit.plugins.signatureweb.api.StatusSignature;
import org.fundaciobit.plugins.signatureweb.api.StatusSignaturesSet;
import org.fundaciobit.plugins.signatureweb.miniappletutils.AbstractMiniAppletSignaturePlugin;
import org.fundaciobit.plugins.signatureweb.miniappletutils.MiniAppletUtils;
import org.fundaciobit.plugins.utils.FileUtils;

import es.gob.afirma.core.misc.AOUtil;
import es.gob.afirma.core.misc.Base64;
import es.gob.afirma.signfolder.server.proxy.RetrieveService;
import es.gob.afirma.signfolder.server.proxy.StorageService;
import es.gob.afirma.triphase.server.SignatureService;
import es.gob.afirma.triphase.server.document.DocumentManager;

/**
 * 
 * @author anadal
 *
 */
public class AfirmaTriphaseSignatureWebPlugin extends AbstractMiniAppletSignaturePlugin
    implements DocumentManager {

  /**
   *
   */
  public AfirmaTriphaseSignatureWebPlugin() {
    super();
  }

  /**
   * @param propertyKeyBase
   * @param properties
   */
  public AfirmaTriphaseSignatureWebPlugin(String propertyKeyBase, Properties properties) {
    super(propertyKeyBase, properties);
  }

  /**
   * @param propertyKeyBase
   */
  public AfirmaTriphaseSignatureWebPlugin(String propertyKeyBase) {
    super(propertyKeyBase);
  }

  @Override
  public String[] getSupportedSignatureTypes() {
    return new String[] {
        FileInfoSignature.SIGN_TYPE_PADES,
        FileInfoSignature.SIGN_TYPE_XADES,
        FileInfoSignature.SIGN_TYPE_CADES };
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
    return null;
  }

  @Override
  public String signDocuments(HttpServletRequest request, String absolutePluginRequestPath,
      String relativePluginRequestPath, SignaturesSet signaturesSet) throws Exception {
    addSignaturesSet(signaturesSet);

    // Mostrar Index
    return relativePluginRequestPath + "/" + INDEX_HTML;
  }

  @Override
  public List<String> getSupportedBarCodeTypes() {
    // Aquests Plugins No suporten estampació de CSV per si mateixos
    return null;
  }

  @Override
  public boolean acceptExternalTimeStampGenerator(String signType) {
    return true;
  }

  @Override
  public boolean providesTimeStampGenerator(String signType) {
    return false;
  }

  @Override
  public boolean acceptExternalRubricGenerator() {
    return false;
  }

  @Override
  public boolean providesRubricGenerator() {
    return false;
  }

  @Override
  public boolean acceptExternalSecureVerificationCodeStamper() {
    return true;
  }

  @Override
  public boolean providesSecureVerificationCodeStamper() {
    return false;
  }

  @Override
  protected String getSimpleName() {
    return "AfirmaTrifase";
  }

  @Override
  public String getResourceBundleName() {
    return "afirmatrifase";
  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------ GESTIO DE PETICIONS -----------------------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  @Override
  public void requestGET(String absolutePluginRequestPath, String relativePluginRequestPath,
      String query, SignaturesSet signaturesSet, int signatureIndex,
      HttpServletRequest request,
      HttpServletResponse response, Locale locale) {

    final SignIDAndIndex sai = new SignIDAndIndex(signaturesSet, signatureIndex);

    
    if (log.isDebugEnabled()) {
      logAllRequestInfo(request, "GET " + getSimpleName(), absolutePluginRequestPath,
          relativePluginRequestPath, query, sai);
    }


    if (query.startsWith(ISFINISHED_PAGE)) {
      isFinishedRequest(signaturesSet, signatureIndex, response);

    } else if (query.startsWith(INDEX_HTML)) {
      // Servicio de firma electronica en 3 fases v2.2
      indexPage(absolutePluginRequestPath, relativePluginRequestPath, request, response,
          signaturesSet, signatureIndex, locale);

    } else if (query.startsWith(JS_MINIAPPLET)) {
      // Servicio para la recuperacion de firmas v1.2
      javascriptMiniApplet(absolutePluginRequestPath, relativePluginRequestPath, request,
          response, signaturesSet, locale);

    } else if (commonRequestGETPOST(absolutePluginRequestPath, relativePluginRequestPath,
        query, signaturesSet, signatureIndex, request, response, locale)) {
      // OK
    } else {
      super.requestGET(absolutePluginRequestPath, relativePluginRequestPath, query,
          signaturesSet, signatureIndex, request, response, locale);
    }

  }

  @Override
  public void requestPOST(String absolutePluginRequestPath, String relativePluginRequestPath,
      String query, SignaturesSet signaturesSet, int signatureIndex,
      HttpServletRequest request, HttpServletResponse response, Locale locale) {

    final SignIDAndIndex sai = new SignIDAndIndex(signaturesSet, signatureIndex);
        
    if (log.isDebugEnabled()) {
      logAllRequestInfo(request, "POST " + getSimpleName(), absolutePluginRequestPath,
          relativePluginRequestPath, query, sai);
    }

    if (commonRequestGETPOST(absolutePluginRequestPath, relativePluginRequestPath, query,
        signaturesSet, signatureIndex, request, response, locale)) {
      // OK
    } else {

      super.requestPOST(absolutePluginRequestPath, relativePluginRequestPath, query,
          signaturesSet, signatureIndex, request, response, locale);
    }

  }

  public boolean commonRequestGETPOST(String absolutePluginRequestPath,
      String relativePluginRequestPath, String query, SignaturesSet signaturesSet,
      int signatureIndex, HttpServletRequest request,
      HttpServletResponse response, Locale locale) {
    final boolean resultat;

    /*
     * TODO <servlet> <description>Realiza la primera fase de un proceso de
     * firma por lote v1.1</description>
     * <servlet-name>BatchPresigner</servlet-name>
     * <servlet-class>es.gob.afirma.signers
     * .batch.server.BatchPresigner</servlet-class> </servlet>
     * 
     * <servlet> <description>Realiza la tercera (y ultima) fase de un proceso
     * de firma por lote v1.1</description>
     * <servlet-name>BatchPostsigner</servlet-name>
     * <servlet-class>es.gob.afirma.
     * signers.batch.server.BatchPostsigner</servlet-class> </servlet>
     */

    if (query.startsWith(RETRIEVESERVICE)) {
      // Servicio para la recuperacion de firmas v1.2
      retrieveService(absolutePluginRequestPath, relativePluginRequestPath, request, response,
          signaturesSet, locale);
      resultat = true;

    } else if (query.startsWith(SIGNATURESERVICE)) {
      // Servicio de firma electronica en 3 fases v2.2
      signatureService(absolutePluginRequestPath, relativePluginRequestPath, request,
          response, signaturesSet, locale);
      resultat = true;

    } else if (query.startsWith(STORAGESERVICE)) {
      // Servicio de almacenamiento temporal de firmas v1.4
      storageService(absolutePluginRequestPath, relativePluginRequestPath, request, response,
          signaturesSet, locale);
      resultat = true;

    } else if (query.startsWith(CLIENT_ERROR_PAGE)) {
      clientErrorPage(query, signaturesSet, signatureIndex, request, response);
      resultat = true;
    } else {
      resultat = false;
    }

    return resultat;
  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------- IS_FINISHED ----------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  protected static final String ISFINISHED_PAGE = "isfinished";

  protected void isFinishedRequest(SignaturesSet ss, int signatureIndex,
      HttpServletResponse response) {

    if (signatureIndex == -1) {

      for (FileInfoSignature fis : ss.getFileInfoSignatureArray()) {
        StatusSignature status = fis.getStatusSignature();
        int code = status.getStatus();
        if (code != StatusSignature.STATUS_FINAL_OK
            && code != StatusSignature.STATUS_FINAL_ERROR) {
          try {
            response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
          } catch (IOException e) {
            log.error(e.getMessage(), e);
          }
          return;
        }
      }
      response.setStatus(HttpServletResponse.SC_OK);

    } else {
      StatusSignature status = ss.getFileInfoSignatureArray()[signatureIndex]
          .getStatusSignature();
      if (status == null) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      } else {
        int code = status.getStatus();
        if (code == StatusSignature.STATUS_FINAL_OK
            || code == StatusSignature.STATUS_FINAL_ERROR) {
          response.setStatus(HttpServletResponse.SC_OK);
        } else {
          try {
            response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
          } catch (IOException e) {
            log.error(e.getMessage(), e);
          }
        }
      }
    }

  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------ PAGINA INICIAL -----------------------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  public static final String INDEX_HTML = "index";

  private void indexPage(String absolutePluginRequestPath, String relativePluginRequestPath,
      HttpServletRequest request, HttpServletResponse response, SignaturesSet signaturesSet,
      int signatureIndex, Locale locale) {

    final String finalURL;
    finalURL = signaturesSet.getCommonInfoSignature().getUrlFinal();

    final String signaturesSetID = signaturesSet.getSignaturesSetID();
    // TODO XYZ Només podem amb un fitxer firmat: revisar sistema Batch
    final int index = 0;

    FileInfoSignature fis = signaturesSet.getFileInfoSignatureArray()[index];

    Properties configProperties = new Properties();

    response.setCharacterEncoding("utf-8");
    response.setContentType("text/html");
    PrintWriter out;
    try {
      out = response.getWriter();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return;
    }

    URL url;
    try {
      url = new URL(absolutePluginRequestPath);
    } catch (MalformedURLException e) {
      String errorMsg = "La ruta Absoluta [" + absolutePluginRequestPath
          + "] té un format incorrecte.";
      finishWithError(response, signaturesSet, errorMsg, e);
      return;
    }
    final String HOST = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort();
    final String PATH = relativePluginRequestPath;
    
    
    // CODI CONVERSIO COMU
    MiniAppletUtils.convertCommon(fis, configProperties);


    // POLITICA DE FIRMA
    PolicyInfoSignature policy;
    policy = MiniAppletUtils.convertPolicy(signaturesSet.getCommonInfoSignature(),
        configProperties);

    final String signType = fis.getSignType();
    String format;
    if (FileInfoSignature.SIGN_TYPE_PADES.equals(signType)) {
      format = "PAdEStri";

      MiniAppletUtils.convertPAdES(fis, configProperties, policy);

    } else if (FileInfoSignature.SIGN_TYPE_XADES.equals(signType)) {
      format = "XAdEStri";

      MiniAppletUtils.convertXAdES(fis, configProperties);

    } else if (FileInfoSignature.SIGN_TYPE_CADES.equals(signType)) {
      format = "CAdEStri";
        
      MiniAppletUtils.convertCAdES(fis, configProperties);
      
    } else {

      // format = "FacturaE
      // format = "FacturaEtri
      // format = "ODF"
      // format = "OOXML"
      final String errorMsg = getSimpleName() + "::Tipus de Firma descogut o no suportat: "
          + signType;

      super.finishWithError(response, signaturesSet, errorMsg, null);
      return;
    }

    // ALGORISME DE FIRMA
    String algorithm;
    try {
      algorithm = MiniAppletUtils.convertAlgorithm(fis);
    } catch (Exception e) {
      String errorMsg = getSimpleName() + "::Tipus d'algorisme desconegut o no suportat "
          + fis.getSignAlgorithm();
      super.finishWithError(response, signaturesSet, errorMsg, null);
      return;
    }

    // SEGELL DE TEMPS
    if (fis.getTimeStampGenerator() != null) {
      int pos = relativePluginRequestPath.lastIndexOf(String.valueOf(signatureIndex));
      String baseSignaturesSet = relativePluginRequestPath.substring(0, pos - 1);
      String callbackhost = getHostAndContextPath(absolutePluginRequestPath,
          relativePluginRequestPath);
      String timeStampUrl = null;

      timeStampUrl = callbackhost + baseSignaturesSet + "/" + index + "/" + TIMESTAMP_PAGE;
      final boolean isLocalSignature = false;
      MiniAppletUtils.convertTimeStamp(fis, timeStampUrl, isLocalSignature, configProperties);
    }

    
    // ESPECIFIC DE @firma AutoFirma i Client @firma Mòbil
    configProperties.setProperty("serverUrl", HOST + PATH + "/" + SIGNATURESERVICE);

    // Convertir Properties a String
    StringBuffer configPropertiesStr = new StringBuffer();

    for (Object key : configProperties.keySet()) {
      configPropertiesStr
          .append(key + "=" + configProperties.getProperty((String) key) + "\n");
    }

    out.print("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
        + "\n" + "<html>" + "\n" + "  <head>" + "\n" + "    <title>"
        + getSimpleName()
        + "</title>"
        + "\n"
        + "    <meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" >"
        + "\n"
        + " <script type=\"text/javascript\" src=\"miniapplet.js\"></script>"
        + "\n"
        + " <script type=\"text/javascript\">"
        + "\n"
        + ""
        // +"  // IMPORTANTE: PARA PRUEBAS, USAR SIEMPRE UNA IP O NOMBRE DE DOMINIO, NUNCA 'LOCALHOST' O '127.0.0.1'"
        // +"  // SI NO SE HACE ASI, AUTOFIRMA BLOQUEARA LA FIRMA POR SEGURIDAD"
        + "  var HOST = \""
        + HOST
        + "\";"
        + "\n"
        + ""
        + "\n"
        + "  function showResultCallback(signatureB64, certificateB64) {"
        + "\n"
        // Enviar a finalitzar !!!
        + "    gotoFinal(); "
        + "  }"
        + "\n"
        + ""
        + "\n"
        // Enviar error a una pàgina concreta d'aquest plugin
        + "  function showErrorCallback(errorType, errorMessage) {"
        + "\n"
        + "    var msg;\n"
        + "    msg = \"Type: \" + errorType + \"Message: \" + errorMessage;\n"
        + "    var request;"
        + "\n"
        + "    if(window.XMLHttpRequest) {"
        + "\n"
        + "        request = new XMLHttpRequest();"
        + "\n"
        + "    } else {"
        + "\n"
        + "        request = new ActiveXObject(\"Microsoft.XMLHTTP\");"
        + "\n"
        + "    }"
        + "\n"
        + "    request.open('GET', '"
        + absolutePluginRequestPath
        + "/"
        + CLIENT_ERROR_PAGE
        + "?error=' + encodeURIComponent(msg), false);"
        + "\n"
        + "    request.send();"
        + "\n"
        + "    gotoFinal();\n"
        + "  }"
        + "\n"
        + "\n"
        + "  function doSign() {"
        + "\n"
        + "   try {"
        + "\n"
        + "     MiniApplet.sign('"
        + encodeSignatureItemID(signaturesSetID, index)
        + "',"
        + "\n"
        + "     '"
        + algorithm
        + "',"
        + "\n"
        + "     '"
        + format
        + "',"
        + "\n"
        + "     '"
        + StringEscapeUtils.escapeJavaScript(configPropertiesStr.toString())
        + "',"
        + "\n"
        + "     showResultCallback,"
        + "\n"
        + "     showErrorCallback);"
        + "\n"
        + "   } catch(e) {"
        + "\n"
        + "    try {"
        + "\n"
        + "       showErrorCallback(MiniApplet.getErrorType(), MiniApplet.getErrorMessage());\n"
        + "    } catch(ex) {"
        + "\n"
        + "       alert(\"Error: \" + ex);"
        + "\n"
        + "    }"
        + "\n"
        + "   }"
        + "\n"
        + "  }"
        + "\n"
        + ""
        + "\n"
        /*
         * +"  function doSignBatch() {" +"   try {"
         * +"    var batch = createBatchConfiguration();" +""
         * +"    MiniApplet.signBatch("
         * +"     MiniApplet.getBase64FromText(batch)," +
         * "     HOST + 'PATH/BatchPresigner',    '/afirma-server-triphase-signer/BatchPresigner'"
         * +
         * "     HOST + 'PATH/BatchPostsigner',  '/afirma-server-triphase-signer/BatchPostsigner'"
         * +"     document.getElementById(\"params\").value,"
         * +"     showResultCallback," +"     showErrorCallback);" +""
         * +"   } catch(e) {" +"    try {" +
         * "     showLog(\"Type: \" + MiniApplet.getErrorType() + \"\"Message: \" + MiniApplet.getErrorMessage());"
         * +"    } catch(ex) {" +"     showLog(\"Error: \" + e);" +"    }"
         * +"   }" +"  }"
         * 
         * +"  " +"  function doCoSign() {" +"   try {"
         * +"    var signature = document.getElementById(\"signature\").value;"
         * +"    var data = document.getElementById(\"data\").value;" +""
         * +"    MiniApplet.coSign(" +
         * "     (signature != undefined && signature != null && signature != \"\") ? signature : null,"
         * +
         * "     (data != undefined && data != null && data != \"\") ? data : null,"
         * +"     document.getElementById(\"algorithm\").value,"
         * +"     document.getElementById(\"format\").value,"
         * +"     document.getElementById(\"params\").value,"
         * +"     showResultCallback," +"     showErrorCallback);" +""
         * +"   } catch(e) {" +
         * "    showLog(\"Type: \" + MiniApplet.getErrorType() + \"Message: \" + MiniApplet.getErrorMessage());"
         * +"   }" +"  }" +"" +"  function doCounterSign() {" +"   try {"
         * +"    var signature = document.getElementById(\"signature\").value;"
         * +"" +"    MiniApplet.counterSign(" +
         * "     (signature != undefined && signature != null && signature != \"\") ? signature : null,"
         * +"     document.getElementById(\"algorithm\").value,"
         * +"     document.getElementById(\"format\").value,"
         * +"     document.getElementById(\"params\").value,"
         * +"     showResultCallback," +"     showErrorCallback);"
         * +"   } catch(e) {" +
         * "    showLog(\"Type: \" + MiniApplet.getErrorType() + \"Message: \" + MiniApplet.getErrorMessage());"
         * +"   }" +"  }" +""
         */

        + " </script>"
        + "\n"
        + "  </head>"
        + "\n"
        + " <body>"
        + "\n"
        + "<div id=\"ajaxloader\" style=\"width:100%;height:100%;\">"
        + "\n"
        + "  <table style=\"min-height:200px;width:100%;height:100%;\">"
        + "\n"
        + "  <tr valign=\"middle\"><td align=\"center\">"
        + "\n"
        + "  <h2>"
        + getTraduccio("espera", locale)
        + "</h2><br/>"
        + "\n"
        + "  <h2>"
        + getTraduccio("espera2", locale)
        + "</h2><br/>"
        + "\n"
        + "  <img alt=\"Esperi\" style=\"z-index:200\" src=\""
        + relativePluginRequestPath + "/" + WEBRESOURCE + "/img/ajax-loader2.gif"
        + "\"><br/>\n<br/>\n"
        + "  <input type=\"button\" class=\"btn btn-primary\" onclick=\"gotoCancel()\" value=\""
        + getTraduccio("cancel", locale)
        + "\">"
        + "  </td></tr>"
        + "\n"
        + " </table>"
        + "\n"
        + "</div>"
        + "\n"
        + "  <script type=\"text/javascript\">"
        + "\n"
        + "    MiniApplet.setForceWSMode(true);"
        + "\n"
        + "    MiniApplet.cargarAppAfirma(HOST + \""
        + request.getContextPath()
        + "\"); "
        + "\n"
        + "    MiniApplet.setServlets(HOST + \""
        + PATH
        + "/"
        + STORAGESERVICE
        + "\", HOST +  \""
        + PATH
        + "/"
        + RETRIEVESERVICE
        + "\");"
        + "\n"
        + "  </script>"
        + "\n"
        + "<script type=\"text/javascript\">"
        + "\n"
        + "  var myTimer;"
        + "\n"
        + "  myTimer = setInterval(function () {closeWhenSign()}, 5000);"
        + "\n"
        + "  function closeWhenSign() {"
        + "\n"
        + "    var request;"
        + "\n"
        + "    if(window.XMLHttpRequest) {"
        + "\n"
        + "        request = new XMLHttpRequest();"
        + "\n"
        + "    } else {"
        + "\n"
        + "        request = new ActiveXObject(\"Microsoft.XMLHTTP\");"
        + "\n"
        + "    }"
        + "\n"
        + "    request.open('GET', '"
        + absolutePluginRequestPath
        + "/"
        + ISFINISHED_PAGE
        + "', false);"
        + "\n"
        + "    request.send();"
        + "\n"
        + "    if ((request.status + '') == '"
        + HttpServletResponse.SC_NOT_MODIFIED
        + "') {"
        + "\n"
        + "      clearTimeout(myTimer);"
        + "\n"
        + "      myTimer = setInterval(function () {closeWhenSign()}, 4000);"
        + "\n"
        + "    } else {"
        + "\n"
        + "      clearTimeout(myTimer);"
        + "\n"
        // esperarem que es faci neteja de missatges abans de reenviar al
        // servidor
        + "      setInterval(function () {gotoFinal()}, 3000);"
        + "\n"
        + "    }"
        + "\n"
        + "  }"
        + "\n"
        + "  function gotoCancel() {"
        + "\n"
        + "    window.location.href='"
        + absolutePluginRequestPath
        + "/"
        + CANCEL_PAGE
        + "';"
        + "\n"
        + "  }"
        + "\n"
        + "  function gotoFinal() {"
        + "\n"
        + "    window.location.href='"
        + finalURL
        + "';"
        + "\n" + "  }" + "\n"
        // Inicia el proces de firma de forma automàtica
        + "   doSign();\n" + " </script>" + "\n" + " </body>" + "\n" + "</html>" + "\n");
    out.flush();

  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------ JAVASCRIPT -----------------------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  public static final String CLIENT_ERROR_PAGE = "clienterror";

  /**
   * En el client (Applet, javaWebStart, ...) s'ha produït un error i l'està
   * enviant al servidor
   * 
   * @param query
   * @param signaturesSet
   * @param signatureIndex
   * @param request
   * @param response
   * @param uploadedFiles
   * @throws Exception
   */
  private void clientErrorPage(String query, SignaturesSet signaturesSet, int signatureIndex,
      HttpServletRequest request, HttpServletResponse response) {

    StatusSignaturesSet status;
    if (signatureIndex == -1) {
      status = signaturesSet.getStatusSignaturesSet();
    } else {
      status = getStatusSignature(signaturesSet.getSignaturesSetID(), signatureIndex);
    }

    String errorMsg = request.getParameter("error");
    if (errorMsg == null) {
      String msg = "S'ha cridat a " + CLIENT_ERROR_PAGE
          + " però aquest no conté el parametre 'error'";
      log.error(msg, new Exception());
      try {
        response.sendError(404, msg);
      } catch (IOException e) {
        e.printStackTrace();
      }

      // TODO Traduir emprant langUI
      status.setErrorMsg("S'ha rebut un error però aquest no conté detalls del tipus"
          + " d'error que s'ha produït");
      status.setStatus(StatusSignature.STATUS_FINAL_ERROR);

    } else {
      log.warn("@firma AUTOFIRMA: S'ha rebut un error: " + errorMsg);
      status.setErrorMsg(errorMsg);
      status.setStatus(StatusSignature.STATUS_FINAL_ERROR);
    }

  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------ JAVASCRIPT -----------------------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  public static final String JS_MINIAPPLET = "miniapplet.js";

  private void javascriptMiniApplet(String absolutePluginRequestPath,
      String relativePluginRequestPath, HttpServletRequest request,
      HttpServletResponse response, SignaturesSet signaturesSet, Locale locale) {

    readResource(response, JS_MINIAPPLET);
  }

  private void readResource(HttpServletResponse response, String relativePath) {
    InputStream fis = FileUtils.readResource(this.getClass(), relativePath);
    if (fis != null) {
      responseResource(response, relativePath, fis);
    } else {
      log.error("No s'ha pogut llegir el recurs: " + relativePath);
    }
  }

  private void responseResource(HttpServletResponse response, String relativePath,
      InputStream fis) {
    try {
      FileUtils.copy(fis, response.getOutputStream());
      fis.close();
    } catch (IOException e) {

      if (e.getCause() != null && e.getCause().getClass().equals(SocketException.class)) {
        // Ok El client ha abortat
      } else {
        log.error("Error intentant retornar recurs " + relativePath + " (" + getSimpleName()
            + "): " + e.getMessage(), e);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      }
    }
  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // --------- SERVEIS @FIRMA TRIFASE :: StorageService ----------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  public static final String STORAGESERVICE = "StorageService";

  /**
   * Servicio de almacenamiento temporal de firmas v1.4
   * 
   * @param absolutePluginRequestPath
   * @param relativePluginRequestPath
   * @param request
   * @param response
   * @param signaturesSet
   * @param locale
   */
  private void storageService(String absolutePluginRequestPath,
      String relativePluginRequestPath, HttpServletRequest request,
      HttpServletResponse response, SignaturesSet signaturesSet, Locale locale) {

    // Init SignatureService
    getSignatureServiceInstance();

    StorageService storageService = new StorageService();

    try {
      storageService.service(request, response);
    } catch (Exception e) {

      final String errorMsg = "Error desconegut processant storageService(): "
          + e.getMessage();

      finishWithError(response, signaturesSet, errorMsg, e);
    }

  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // --------- SERVEIS @FIRMA TRIFASE :: RetrieveService ----------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  public static final String RETRIEVESERVICE = "RetrieveService";

  /**
   * Servicio para la recuperacion de firmas v1.2
   * 
   * @param absolutePluginRequestPath
   * @param relativePluginRequestPath
   * @param request
   * @param response
   * @param signaturesSet
   * @param locale
   */
  private void retrieveService(String absolutePluginRequestPath,
      String relativePluginRequestPath, HttpServletRequest request,
      HttpServletResponse response, SignaturesSet signaturesSet, Locale locale) {

    // Init Signature Service
    getSignatureServiceInstance();

    Map<?, ?> mapp = request.getParameterMap();
    if (mapp.size() == 0 || (mapp.size() == 1 && mapp.containsKey("restOfTheUrl"))) {

      try {
        Scanner s = new Scanner(request.getInputStream()).useDelimiter("\\A");
        String query = s.hasNext() ? s.next() : "";
        Map<String, String> query_pairs = new HashMap<String, String>();

        String[] pairs = query.split("&");
        for (String pair : pairs) {
          int idx = pair.indexOf("=");
          query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
              URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }

        request = new ParametersServletRequest(request, query_pairs);

      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }


    // TODO Cache ???
    RetrieveService retrieveService = new RetrieveService();

    try {
      retrieveService.service(request, response);
    } catch (Exception e) {

      final String errorMsg = "Error desconegut processant retrieveService(): "
          + e.getMessage();

      finishWithError(response, signaturesSet, errorMsg, e);
    }

  }

  public static class ParametersServletRequest extends
      javax.servlet.http.HttpServletRequestWrapper {

    final Map<String, String> query_pairs;

    /**
     * @param request
     * @param query_pairs
     */
    public ParametersServletRequest(HttpServletRequest request, Map<String, String> query_pairs) {
      super(request);
      this.query_pairs = query_pairs;
    }

    @Override
    public String getParameter(String name) {
      return query_pairs.get(name);
    }

  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // --------- SERVEIS @FIRMA TRIFASE :: SignatureService ----------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  public static final String SIGNATURESERVICE = "SignatureService";

  /**
   * Servicio de firma electronica en 3 fases v2.2
   * 
   * @param absolutePluginRequestPath
   * @param relativePluginRequestPath
   * @param request
   * @param response
   * @param signaturesSet
   * @param locale
   */
  private void signatureService(String absolutePluginRequestPath,
      String relativePluginRequestPath, HttpServletRequest request,
      HttpServletResponse response, SignaturesSet signaturesSet, Locale locale) {

    // Cache
    SignatureService signatureService = getSignatureServiceInstance();

    try {
      signatureService.service(request, response);
    } catch (Exception e) {

      final String errorMsg = "Error desconegut processant signatureService(): "
          + e.getMessage();

      finishWithError(response, signaturesSet, errorMsg, e);
    }

  }

  private SignatureService signatureService = null;

  public SignatureService getSignatureServiceInstance() {
    if (signatureService == null) {
      signatureService = new SignatureService();
      init();
    }
    return signatureService;
  }

  private static final String CONFIG_PARAM_DOCUMENT_MANAGER_CLASS = "document.manager";

  private void init() {

    Field configField;
    try {
      configField = SignatureService.class.getDeclaredField("config");
      configField.setAccessible(true);

      
      // Valors NO REALS, nomes per inicialitzar el sistema !!!!
      Properties config = (Properties) configField.get(null);

      config.put("overwrite", "true");
      config
          .put(
              "outdir",
              "D:/dades/dades/CarpetesPersonals/Programacio/portafib-1.1-jboss-5.1.0.GA/server/default/deployportafib/triphaseout");
      config
          .put(
              "indir",
              "D:/dades/dades/CarpetesPersonals/Programacio/portafib-1.1-jboss-5.1.0.GA/server/default/deployportafib/triphasein");
      config.put("alternative.xmldsig", "false");
      config.put("Access-Control-Allow-Origin", "*");
      config.put(CONFIG_PARAM_DOCUMENT_MANAGER_CLASS,
          "es.gob.afirma.triphase.server.document.FileSystemDocumentManager");

      DocumentManager DOC_MANAGER = this;

      Field privateField = SignatureService.class.getDeclaredField("DOC_MANAGER");
      privateField.setAccessible(true);
      privateField.set(null, DOC_MANAGER);

    } catch (Exception e) {
      log.error("Error inicialitzant DocumentManager: " + e.getMessage(), e);
    }

  }

  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  // ------------------------- DOCUMENT MANAGER INTERFACE ----------------------
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------

  protected String encodeSignatureItemID(String signaturesSetID, int index) {

    // log.error(" ENCODE:: signaturesSetID = " + signaturesSetID);
    // log.error(" ENCODE:: index = ]" + index + "[");

    return Base64.encode((signaturesSetID + "|" + index).getBytes());
  }

  protected Item decodeSignatureItemID(String id) throws IOException {
    String id_and_index = new String(Base64.decode(id));

    final boolean debug = log.isDebugEnabled();

    if (debug) {
      log.debug(" DECODE:: id = " + id);
      log.debug(" DECODE:: id_and_index = ]" + id_and_index + "[");
    }

    String[] parts = id_and_index.split("\\|");

    Item item = new Item();

    item.signaturesSetID = parts[0];
    item.index = Integer.parseInt(parts[1]);

    if (debug) {
      log.debug(" DECODE:: parts = ]" + item.signaturesSetID + "[");
      log.debug(" DECODE:: parts = ]" + item.index + "[");
    }

    return item;
  }

  protected class Item {
    String signaturesSetID;
    int index;
  }

  /**
   * Obtiene un documento en base a su identificador. Si no es posible recuperar
   * el fichero se debe lanzar una excepci&oacute;n. El mensaje se
   * recibir&aacute; como parte del mensaje de error en el cliente de firma.
   * 
   * @param id
   *          Identificador del documento
   * @param certChain
   *          Cadena de certificados que se usar&aacute; para realizar la firma
   * @param config
   *          Par&aacute;metros para la configuraci&oacute;n de la
   *          recuperaci&oacute;n del documento.
   * @return Documento (en binario)
   * @throws IOException
   *           Cuando ocurre alg&uacute;n problema con la recuperaci&oacute;n
   */
  @Override
  public byte[] getDocument(String id, X509Certificate[] certChain, Properties config)
      throws IOException {

    InputStream fis = null;
    Item item = null;
    File file = null;

    try {

      item = decodeSignatureItemID(id);

      final String signatureSetID = item.signaturesSetID;
      final int index = item.index;

      // TODO CHECK si ss == null
      SignaturesSet ss = getSignaturesSet(signatureSetID);

      // TODO Check Null

      FileInfoSignature fisig = ss.getFileInfoSignatureArray()[index];

      file = fisig.getFileToSign();

      if (log.isDebugEnabled()) {
        log.debug(" getDocument():: FileToSign => " + file.getAbsolutePath());
      }

      fis = new FileInputStream(file);
      final byte[] data = AOUtil.getDataFromInputStream(fis);
      fis.close();
      return data;

    } catch (final Exception e) {

      if (item == null) {
        log.warn("Error desconegut recuperant fitxer codificat " + id, e);
      } else {
        log.warn("Error desconegut recuperant fitxer:  codificat " + id, e);
        log.warn("   - getDocument()::signatureSetID = " + item.signaturesSetID);
        log.warn("   - getDocument()::index = " + item.index);
      }

      if (fis != null) {
        try {
          fis.close();
        } catch (final IOException e2) {
          log.warn("El fitxer ha quedat sense tancar " + file.getAbsolutePath(), e2);
        }
      }
      throw new IOException(e.getMessage(), e);
    }

  }

  /**
   * Almacena un documento firmado. Si no es posible almacenar el fichero se
   * debe lanzar una excepci&oacute;n. El mensaje se recibir&aacute; como parte
   * del mensaje de error en el cliente de firma.
   * 
   * @param id
   *          Identificador del documento original no firmado.
   * @param certChain
   *          Cadena de certificados de firma.
   * @param data
   *          Datos firmados.
   * @param config
   *          Par&aacute;metros para la configuraci&oacute;n del guardado del
   *          documento.
   * @return Identificador del nuevo documento codificado en base 64.
   * @throws IOException
   *           Cuando ocurre alg&uacute;n problema con la recuperaci&oacute;n
   */
  @Override
  public String storeDocument(String id, final X509Certificate[] certChain, byte[] data,
      Properties config) throws IOException {

    Item item = null;

    item = decodeSignatureItemID(id);

    final String signaturesSetID = item.signaturesSetID;
    final int signatureIndex = item.index;

    // TODO CHECK si ss == null ==> CADUCAT !!!
    SignaturesSet ss = getSignaturesSet(signaturesSetID);

    StatusSignature status = getStatusSignature(signaturesSetID, signatureIndex);

    File firmat = null;
    FileOutputStream fos = null;
    try {
      firmat = File.createTempFile("TriphaseSigWebPlugin", "signedfile");

      fos = new FileOutputStream(firmat);
      fos.write(data);
      fos.flush();
      fos.close();

      status.setSignedData(firmat);
      

      // Estat d'aquest document en particular
      status.setStatus(StatusSignature.STATUS_FINAL_OK);
      // Estat d'aquest document en concret
      ss.getStatusSignaturesSet().setStatus(StatusSignature.STATUS_FINAL_OK);

      if (log.isDebugEnabled()) {
        log.debug(" Traduir  Escribiendo el fichero: " + firmat.getAbsolutePath());
      }
      return Base64.encode(firmat.getAbsolutePath().getBytes());

    } catch (final IOException e) {
      log.error("Error al guardar les dades en el fitxer '" + firmat.getAbsolutePath() + "': " + e); 
      if (fos != null) {
        try {
          fos.close();
        } catch (final IOException e2) {
          log.warn("Error al guardar les dades en el fitxer: " + firmat.getAbsolutePath());
        }
      }
      throw e;
    }

  }

}