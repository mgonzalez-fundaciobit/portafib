package es.caib.portafib.ws.api.v1;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.4
 * 2015-11-30T11:08:59.713+01:00
 * Generated source version: 2.6.4
 * 
 */
@WebService(targetNamespace = "http://impl.v1.ws.portafib.caib.es/", name = "PortaFIBUsuariEntitatWs")
@XmlSeeAlso({ObjectFactory.class})
public interface PortaFIBUsuariEntitatWs {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getEntitatID", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetEntitatID")
    @WebMethod
    @ResponseWrapper(localName = "getEntitatIDResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetEntitatIDResponse")
    public java.lang.String getEntitatID();

    @RequestWrapper(localName = "removeRoleSolicitant", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.RemoveRoleSolicitant")
    @WebMethod
    @ResponseWrapper(localName = "removeRoleSolicitantResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.RemoveRoleSolicitantResponse")
    public void removeRoleSolicitant(
        @WebParam(name = "usuariEntitatID", targetNamespace = "")
        java.lang.String usuariEntitatID
    ) throws WsI18NException;

    @RequestWrapper(localName = "activateCarrec", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.ActivateCarrec")
    @WebMethod
    @ResponseWrapper(localName = "activateCarrecResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.ActivateCarrecResponse")
    public void activateCarrec(
        @WebParam(name = "carrecID", targetNamespace = "")
        java.lang.String carrecID
    ) throws WsI18NException;

    @RequestWrapper(localName = "deactivateUsuariEntitat", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DeactivateUsuariEntitat")
    @WebMethod
    @ResponseWrapper(localName = "deactivateUsuariEntitatResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DeactivateUsuariEntitatResponse")
    public void deactivateUsuariEntitat(
        @WebParam(name = "usuariEntitatID", targetNamespace = "")
        java.lang.String usuariEntitatID
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUsuariEntitat", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariEntitat")
    @WebMethod
    @ResponseWrapper(localName = "getUsuariEntitatResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariEntitatResponse")
    public es.caib.portafib.ws.api.v1.UsuariEntitatBean getUsuariEntitat(
        @WebParam(name = "usuariEntitatID", targetNamespace = "")
        java.lang.String usuariEntitatID
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getInfoFromPluginUserInfoByUsername", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetInfoFromPluginUserInfoByUsername")
    @WebMethod
    @ResponseWrapper(localName = "getInfoFromPluginUserInfoByUsernameResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetInfoFromPluginUserInfoByUsernameResponse")
    public es.caib.portafib.ws.api.v1.UsuariPersonaBean getInfoFromPluginUserInfoByUsername(
        @WebParam(name = "administrationID", targetNamespace = "")
        java.lang.String administrationID
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCarrecsOfMyEntitat", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetCarrecsOfMyEntitat")
    @WebMethod
    @ResponseWrapper(localName = "getCarrecsOfMyEntitatResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetCarrecsOfMyEntitatResponse")
    public java.util.List<es.caib.portafib.ws.api.v1.CarrecWs> getCarrecsOfMyEntitat();

    @RequestWrapper(localName = "updateAdministrationIDOfCarrec", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.UpdateAdministrationIDOfCarrec")
    @WebMethod
    @ResponseWrapper(localName = "updateAdministrationIDOfCarrecResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.UpdateAdministrationIDOfCarrecResponse")
    public void updateAdministrationIDOfCarrec(
        @WebParam(name = "carrecID", targetNamespace = "")
        java.lang.String carrecID,
        @WebParam(name = "administrationID", targetNamespace = "")
        java.lang.String administrationID
    ) throws WsI18NException, WsValidationException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "createCarrecSimple", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.CreateCarrecSimple")
    @WebMethod
    @ResponseWrapper(localName = "createCarrecSimpleResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.CreateCarrecSimpleResponse")
    public es.caib.portafib.ws.api.v1.CarrecWs createCarrecSimple(
        @WebParam(name = "administrationID", targetNamespace = "")
        java.lang.String administrationID,
        @WebParam(name = "entitatID", targetNamespace = "")
        java.lang.String entitatID,
        @WebParam(name = "carrecUsername", targetNamespace = "")
        java.lang.String carrecUsername,
        @WebParam(name = "carrecName", targetNamespace = "")
        java.lang.String carrecName
    ) throws WsI18NException, WsValidationException;

    @RequestWrapper(localName = "createCarrec", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.CreateCarrec")
    @WebMethod
    @ResponseWrapper(localName = "createCarrecResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.CreateCarrecResponse")
    public void createCarrec(
        @WebParam(name = "carrec", targetNamespace = "")
        es.caib.portafib.ws.api.v1.CarrecWs carrec
    ) throws WsI18NException, WsValidationException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUsuariEntitatIDInMyEntitatByUsuariPersonaID", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariEntitatIDInMyEntitatByUsuariPersonaID")
    @WebMethod
    @ResponseWrapper(localName = "getUsuariEntitatIDInMyEntitatByUsuariPersonaIDResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariEntitatIDInMyEntitatByUsuariPersonaIDResponse")
    public java.lang.String getUsuariEntitatIDInMyEntitatByUsuariPersonaID(
        @WebParam(name = "usuariPersonaID", targetNamespace = "")
        java.lang.String usuariPersonaID
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUsuariPersonaIDByAdministrationID", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariPersonaIDByAdministrationID")
    @WebMethod
    @ResponseWrapper(localName = "getUsuariPersonaIDByAdministrationIDResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariPersonaIDByAdministrationIDResponse")
    public java.lang.String getUsuariPersonaIDByAdministrationID(
        @WebParam(name = "administrationID", targetNamespace = "")
        java.lang.String administrationID
    ) throws WsI18NException, WsValidationException;

    @RequestWrapper(localName = "deleteUsuariPersona", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DeleteUsuariPersona")
    @WebMethod
    @ResponseWrapper(localName = "deleteUsuariPersonaResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DeleteUsuariPersonaResponse")
    public void deleteUsuariPersona(
        @WebParam(name = "usuariPersonaID", targetNamespace = "")
        java.lang.String usuariPersonaID
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUsuariEntitatIDByUsuariPersonaID", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariEntitatIDByUsuariPersonaID")
    @WebMethod
    @ResponseWrapper(localName = "getUsuariEntitatIDByUsuariPersonaIDResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariEntitatIDByUsuariPersonaIDResponse")
    public java.lang.String getUsuariEntitatIDByUsuariPersonaID(
        @WebParam(name = "usuariPersonaID", targetNamespace = "")
        java.lang.String usuariPersonaID,
        @WebParam(name = "entitatID", targetNamespace = "")
        java.lang.String entitatID
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "createUsuariEntitatSimple", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.CreateUsuariEntitatSimple")
    @WebMethod
    @ResponseWrapper(localName = "createUsuariEntitatSimpleResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.CreateUsuariEntitatSimpleResponse")
    public es.caib.portafib.ws.api.v1.UsuariEntitatBean createUsuariEntitatSimple(
        @WebParam(name = "administrationID", targetNamespace = "")
        java.lang.String administrationID,
        @WebParam(name = "entitatID", targetNamespace = "")
        java.lang.String entitatID
    ) throws WsI18NException, WsValidationException;

    @RequestWrapper(localName = "deactivateCarrec", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DeactivateCarrec")
    @WebMethod
    @ResponseWrapper(localName = "deactivateCarrecResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DeactivateCarrecResponse")
    public void deactivateCarrec(
        @WebParam(name = "carrecID", targetNamespace = "")
        java.lang.String carrecID
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUsuariEntitatIDInMyEntitatByAdministrationID", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariEntitatIDInMyEntitatByAdministrationID")
    @WebMethod
    @ResponseWrapper(localName = "getUsuariEntitatIDInMyEntitatByAdministrationIDResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariEntitatIDInMyEntitatByAdministrationIDResponse")
    public java.lang.String getUsuariEntitatIDInMyEntitatByAdministrationID(
        @WebParam(name = "administrationID", targetNamespace = "")
        java.lang.String administrationID
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getVersionWs", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetVersionWs")
    @WebMethod
    @ResponseWrapper(localName = "getVersionWsResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetVersionWsResponse")
    public int getVersionWs();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "createUsuariEntitat", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.CreateUsuariEntitat")
    @WebMethod
    @ResponseWrapper(localName = "createUsuariEntitatResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.CreateUsuariEntitatResponse")
    public es.caib.portafib.ws.api.v1.UsuariEntitatBean createUsuariEntitat(
        @WebParam(name = "usuariEntitatBean", targetNamespace = "")
        es.caib.portafib.ws.api.v1.UsuariEntitatBean usuariEntitatBean
    ) throws WsI18NException, WsValidationException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getInfoFromPluginUserInfoByAdministrationID", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetInfoFromPluginUserInfoByAdministrationID")
    @WebMethod
    @ResponseWrapper(localName = "getInfoFromPluginUserInfoByAdministrationIDResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetInfoFromPluginUserInfoByAdministrationIDResponse")
    public es.caib.portafib.ws.api.v1.UsuariPersonaBean getInfoFromPluginUserInfoByAdministrationID(
        @WebParam(name = "administrationID", targetNamespace = "")
        java.lang.String administrationID
    ) throws WsI18NException;

    @RequestWrapper(localName = "deleteUsuariEntitat", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DeleteUsuariEntitat")
    @WebMethod
    @ResponseWrapper(localName = "deleteUsuariEntitatResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DeleteUsuariEntitatResponse")
    public void deleteUsuariEntitat(
        @WebParam(name = "usuariEntitatID", targetNamespace = "")
        java.lang.String usuariEntitatID
    ) throws WsI18NException;

    @RequestWrapper(localName = "addRoleSolicitant", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.AddRoleSolicitant")
    @WebMethod
    @ResponseWrapper(localName = "addRoleSolicitantResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.AddRoleSolicitantResponse")
    public void addRoleSolicitant(
        @WebParam(name = "usuariEntitatID", targetNamespace = "")
        java.lang.String usuariEntitatID
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getVersion", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetVersion")
    @WebMethod
    @ResponseWrapper(localName = "getVersionResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetVersionResponse")
    public java.lang.String getVersion();

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUsuariEntitatIDByAdministrationID", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariEntitatIDByAdministrationID")
    @WebMethod
    @ResponseWrapper(localName = "getUsuariEntitatIDByAdministrationIDResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariEntitatIDByAdministrationIDResponse")
    public java.lang.String getUsuariEntitatIDByAdministrationID(
        @WebParam(name = "administrationID", targetNamespace = "")
        java.lang.String administrationID,
        @WebParam(name = "entitatID", targetNamespace = "")
        java.lang.String entitatID
    ) throws WsI18NException;

    @RequestWrapper(localName = "activateUsuariEntitat", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.ActivateUsuariEntitat")
    @WebMethod
    @ResponseWrapper(localName = "activateUsuariEntitatResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.ActivateUsuariEntitatResponse")
    public void activateUsuariEntitat(
        @WebParam(name = "usuariEntitatID", targetNamespace = "")
        java.lang.String usuariEntitatID
    ) throws WsI18NException;

    @RequestWrapper(localName = "deleteCarrec", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DeleteCarrec")
    @WebMethod
    @ResponseWrapper(localName = "deleteCarrecResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DeleteCarrecResponse")
    public void deleteCarrec(
        @WebParam(name = "carrecID", targetNamespace = "")
        java.lang.String carrecID
    ) throws WsI18NException;

    @RequestWrapper(localName = "removeRoleAdministradorDeEntitat", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.RemoveRoleAdministradorDeEntitat")
    @WebMethod
    @ResponseWrapper(localName = "removeRoleAdministradorDeEntitatResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.RemoveRoleAdministradorDeEntitatResponse")
    public void removeRoleAdministradorDeEntitat(
        @WebParam(name = "usuariEntitatID", targetNamespace = "")
        java.lang.String usuariEntitatID
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getSupportedLanguages", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetSupportedLanguages")
    @WebMethod
    @ResponseWrapper(localName = "getSupportedLanguagesResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetSupportedLanguagesResponse")
    public java.util.List<java.lang.String> getSupportedLanguages() throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCarrecsByEntitatID", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetCarrecsByEntitatID")
    @WebMethod
    @ResponseWrapper(localName = "getCarrecsByEntitatIDResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetCarrecsByEntitatIDResponse")
    public java.util.List<es.caib.portafib.ws.api.v1.CarrecWs> getCarrecsByEntitatID(
        @WebParam(name = "entitatID", targetNamespace = "")
        java.lang.String entitatID
    ) throws WsI18NException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "downloadFileUsingEncryptedFileID", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DownloadFileUsingEncryptedFileID")
    @WebMethod
    @ResponseWrapper(localName = "downloadFileUsingEncryptedFileIDResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.DownloadFileUsingEncryptedFileIDResponse")
    public es.caib.portafib.ws.api.v1.FitxerBean downloadFileUsingEncryptedFileID(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    ) throws WsI18NException;

    @RequestWrapper(localName = "createUsuariPersona", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.CreateUsuariPersona")
    @WebMethod
    @ResponseWrapper(localName = "createUsuariPersonaResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.CreateUsuariPersonaResponse")
    public void createUsuariPersona(
        @WebParam(name = "usuariPersonaBean", targetNamespace = "")
        es.caib.portafib.ws.api.v1.UsuariPersonaBean usuariPersonaBean
    ) throws WsI18NException, WsValidationException;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCarrec", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetCarrec")
    @WebMethod
    @ResponseWrapper(localName = "getCarrecResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetCarrecResponse")
    public es.caib.portafib.ws.api.v1.CarrecWs getCarrec(
        @WebParam(name = "carrecID", targetNamespace = "")
        java.lang.String carrecID
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getUsuariPersona", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariPersona")
    @WebMethod
    @ResponseWrapper(localName = "getUsuariPersonaResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.GetUsuariPersonaResponse")
    public es.caib.portafib.ws.api.v1.UsuariPersonaBean getUsuariPersona(
        @WebParam(name = "usuariPersonaID", targetNamespace = "")
        java.lang.String usuariPersonaID
    ) throws WsI18NException, WsValidationException;

    @RequestWrapper(localName = "addRoleAdministradorDeEntitat", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.AddRoleAdministradorDeEntitat")
    @WebMethod
    @ResponseWrapper(localName = "addRoleAdministradorDeEntitatResponse", targetNamespace = "http://impl.v1.ws.portafib.caib.es/", className = "es.caib.portafib.ws.api.v1.AddRoleAdministradorDeEntitatResponse")
    public void addRoleAdministradorDeEntitat(
        @WebParam(name = "usuariEntitatID", targetNamespace = "")
        java.lang.String usuariEntitatID
    ) throws WsI18NException;
}
