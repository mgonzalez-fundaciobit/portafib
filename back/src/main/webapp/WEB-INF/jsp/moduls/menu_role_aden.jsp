<%@page import="es.caib.portafib.utils.Configuracio"%>
<%@page import="es.caib.portafib.back.security.LoginInfo"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
%><%@ include  file="/WEB-INF/jsp/moduls/includes.jsp"%>
<sec:authorize access="hasRole('ROLE_ADEN')">
<div>
  <h5><fmt:message key="ROLE_ADEN.menu" /></h5>
<%!

final String[] menu = {
    "entitat.modificar", // Modificació de les dades d'una Entitat"}
    "",
    "usuaripersona.gestio",    
    "usuarientitat.gestio",
    "carrec.gestio",
    /*
    "altausuariaplicacio",
    "modificaciodadesusuariaplicacio", // Modificació de les dades d'un usuari-aplicació"}
    */
    
    "",
    "solicitant.gestio",
    
    "",
    
    "grups.gestio",
    
    "",
    
    "tipusdocument.gestio", // Gestió Tipus de Documents"
    
    /*
    "carrec.alta",                 // Crear usuaris entitat càrrecs
    "carrec.llistat",                 // LListat usuaris entitat càrrecs
    */
   
    
    "",
    //"associarrolssoliadendeusuariaplicacio", // Associar els rols Sol·licitant i/o Administrador d'Entitat a un usuari-aplicació d'una entitat 
    //"eliminarrolssoliadendeusuariaplicacio", // Eliminar rols Sol·licitant i/o Administrador d'Entitat a un usuari-aplicació d'una entitat 
    //"associarrolssolidestdeusuaripersona", // Associar els rols Sol·licitant i/o Destinatari a un usuari-persona d'una entitat 
    //"eliminarrolssolidestdeusuaripersona", // Eliminar rols Delegat, Sol·licitant, Destinatari i Col·laborador a un usuari d'una entitat
    //"",
    "peticionscaducades.llistat", // Llistar peticions de firma caducades
    "aturarpeticionsdefirma",
    "",    
    "usuariaplicacio.gestio", // Alta d'Usuari-Aplicació"}    
    "plantillaFluxDeFirmes.plantillaFluxDeFirmes.plural",
    "peticiodefirma.llistar", // Llistar Peticions de Firma de usauris Aplicacio
    "custodiaInfo.custodiaInfo.plural",
    "notificaciows.llistat"
    //"estatcoesnotificacio", // Estats de les Coes de Notificacions 
    //"bloquejarnotificacio", // Bloquejar Notificació   
    //"desbloquejarnotificacio", // Desbloquejar Notificació 
    //"eliminarnotificacio", // Eliminar Notificació


};

public static final Map<String, String> mapping;


static {
  //Mapping to existent path
  mapping = new HashMap<String, String>();
  
  mapping.put("entitat.modificar", "/aden/entitat/" + LoginInfo.getInstance().getEntitatID() + "/edit" );

  mapping.put("usuaripersona.gestio", "/aden/usuariPersona/nif");
  mapping.put("usuarientitat.gestio", "/aden/usuariEntitat/nif");
  mapping.put("carrec.gestio", "/aden/carrec/list");
  mapping.put("tipusdocument.gestio", "/aden/gestiotipusdoc/list");

  mapping.put("grups.gestio", "/aden/grup/list");

  /*
  mapping.put("crearplantillafluxfirmes", "/aden/plantilla/new?usuariEntitat=false");
  mapping.put("modificarplantillafluxfirmes", "/aden/plantilla/list/1?usuariEntitat=true");
  mapping.put("eliminarplantillafluxfirmes", "/aden/plantilla/list/1?usuariEntitat=true");
  */
  
  mapping.put("solicitant.gestio", "/aden/solicitant/nif");
  
  
  mapping.put("peticionscaducades.llistat", "/aden/peticionscaducades/list");
  mapping.put("aturarpeticionsdefirma", "/aden/aturarpeticions/nif");
  
  
  mapping.put("usuariaplicacio.gestio", "/aden/usuariAplicacio/list");
  /*
  mapping.put("altausuariaplicacio", "/aden/usuariAplicacio/new");
  mapping.put("modificaciodadesusuariaplicacio", "/aden/usuariAplicacio/list");
  */

  mapping.put("plantillaFluxDeFirmes.plantillaFluxDeFirmes.plural", "/aden/plantilla/list/1");

  mapping.put("notificaciows.llistat", "/aden/notificaciows/list");

  mapping.put("peticiodefirma.llistar", "/aden/peticiofirmaaplicacio/list");
  
  mapping.put("custodiaInfo.custodiaInfo.plural", "/aden/peticio/custodiainfo/list");
  
}

%><%

session.setAttribute("menu", menu);

session.setAttribute("mapping", mapping);

if (Configuracio.isCAIB()) {
%>

   <li style="list-style-type: disc; list-style-position: inside;">
    <a href="<c:url value="/aden/generarfiltrecaib/new"/>">
      <span style="${(fn:contains(urlActual, '/aden/generarfiltrecaib/new'))? 'font-weight: bold;' : ''} ">Filtre CAIB</span>
    </a>
   </li>
   <hr  style="margin-top: 6px;  margin-bottom: 6px;" />   
<%
}
%>
  
  <ul class="tree" style="margin: 3px; padding: 0px;">
    <c:forEach var="item" items="${menu}" >

    <c:if test="${empty item }">
    <hr  style="margin-top: 6px;  margin-bottom: 6px;" />
    </c:if>
    <c:if test="${not empty item }">
      <fmt:message var="traduccio" key="${item}" />
      
      <c:if test="${empty mapping[item]}">
        <c:set var="theurl" value="/aden/${item}"/>
      </c:if>
      <c:if test="${not (empty mapping[item])}">
        <c:set var="theurl" value="${mapping[item]}"/>
      </c:if>
      
      <li style="list-style-type: disc; list-style-position: inside;">
        <a href="<c:url value="${theurl}"/>">
          <span style="${(fn:contains(urlActual, theurl))? "font-weight: bold;" : ""} ${(fn:endsWith(traduccio, '(*)'))? "color: red;" : ""}">${traduccio}</span>
        </a>
      </li>
    </c:if>
    </c:forEach>

  </ul>
</div>
</sec:authorize>