<%@ page contentType="text/html;charset=UTF-8" language="java"%><%@ include
  file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="Constants" className="es.caib.portafib.utils.Constants" />
<!--  INICI MENU -->
<div class="mainMenu span5">
  
  <iframe scrolling="auto"
    src="<c:url value="/${rolecontext}/plantilla/viewonlyflux/${peticioDeFirma.fluxDeFirmesID}?readOnly=true"/>"
    style="width: 100%; height: 375px;"> </iframe>

  <br /> <br />
  <c:set var="peticioID" value="${peticioDeFirma.peticioDeFirmaID}" />
  <c:set var="estatID" value="${estatDeFirma.estatDeFirmaID}" />
  
  <c:if test="${empty estatDeFirma.tipusEstatDeFirmaFinalID }">
  
  <div style="text-align: center">
    <a class="btn btn-success" href="#"
      onclick="goTo('<c:url value="${contexte}/firmar/${estatID}/${peticioID}"/>')"> <i
      class="icon-edit"></i> <fmt:message key="firmar" /> </a> &nbsp;&nbsp; 
    <a class="btn btn-danger" href="#"
      onclick="rebutjar('<c:url value="${contexte}/rebutjar/${estatID}/${peticioID}"/>')">
      <i class="icon-remove"></i> <fmt:message key="rebutjar" /> </a>

  </div>
  </c:if>

  <div>
    <style>
        .bs-docs-example:after {
        	content: "<fmt:message key="peticioDeFirma.peticioDeFirma" />";
        }
    </style>
    <form class="bs-docs-example bs-docs-example form-inline" style="margin-bottom: 5px;">
      <small>
       <b> <fmt:message key="peticioDeFirma.titol" />:</b> 
           <c:out  value="${peticioDeFirma.titol}" /><br /> 

       <b> <fmt:message key="peticioDeFirma.motiu" />:</b>
           <c:out value="${peticioDeFirma.motiu}" /><br />

       <b> <fmt:message key="peticioDeFirma.descripcio" />:</b>
           <c:out  value="${peticioDeFirma.descripcio}" /><br />

       <b> <fmt:message key="peticioDeFirma.remitentNom" />:</b>
           <c:out  value="${peticioDeFirma.remitentNom}" /><br />

       <b> <fmt:message key="peticioDeFirma.remitentDescripcio" />:</b>
           <c:out  value="${peticioDeFirma.remitentDescripcio}" /><br />  
           
           
        <c:choose>
          <c:when test="${peticioDeFirma.prioritatID <= 2}">
             <c:set var="title_priority" value="prioritat.baixa"/>
          </c:when>
          <c:when test="${peticioDeFirma.prioritatID >= 7}">
              <c:set var="title_priority" value="prioritat.alta"/>
          </c:when>
          <c:otherwise>
              <c:set var="title_priority" value="prioritat.normal"/>
          </c:otherwise>
         </c:choose>
         <b><fmt:message key="peticioDeFirma.prioritatID" />:</b>
               <fmt:message key="${title_priority}" />

     </small>
    </form>
  </div>
    
  <div>
    <style>
        .bs-docs-example-dest:after {
            content: "<fmt:message key="ROLE_DEST" />";
        }
    </style>
    <form class="bs-docs-example-dest bs-docs-example form-inline" style="margin-bottom: 5px;">
      <ul style="list-style-type:square;">
      <li>
      <small>
         ${pfi:getNom(destinatari.usuariEntitat.usuariPersona)}
         &nbsp;<img src="<c:url value="/img/fletxa.gif" />"/>&nbsp;
         <b>
           <c:choose>
             <c:when test="${empty destinatari.tipusEstatDeFirmaFinalID}">
                <fmt:message key="pendent" />
             </c:when>
             
             <c:when test="${destinatari.tipusEstatDeFirmaFinalID == Constants.TIPUSESTATDEFIRMAFINAL_REBUTJAT }">
                <fmt:message key="${traduccions[destinatari.tipusEstatDeFirmaFinalID]}" /><br/>
                <small style="color: red;">${destinatari.descripcio}</small>
             </c:when>

             <c:otherwise>
                <fmt:message key="${traduccions[destinatari.tipusEstatDeFirmaFinalID]}" /> 
             </c:otherwise>

           </c:choose>
        </b>
      </small>
      </li>
      </ul>
    </form>
    

    <c:if test="${not empty delegats}">

      <style>
          .bs-docs-example-dele:after {
          	content: "<fmt:message key="ROLE_DELE.plural" />";
          }
      </style>

      <form class="bs-docs-example-dele bs-docs-example form-inline" style="margin-bottom: 5px;">
        
        <ul style="list-style-type:square;">
          <c:forEach var="estat" items="${delegats}">
          <li> 
          <small> 
             ${pfi:getNom(estat.usuariEntitat.usuariPersona)} 
             &nbsp;<img src="<c:url value="/img/fletxa.gif" />"/>&nbsp;
             <b>               
             <c:choose>
               <c:when test="${empty estat.tipusEstatDeFirmaFinalID}">
                  <fmt:message key="pendent" />
               </c:when>
               
               <c:when test="${estat.tipusEstatDeFirmaFinalID == Constants.TIPUSESTATDEFIRMAFINAL_REBUTJAT }">
                    <fmt:message key="${traduccions[estat.tipusEstatDeFirmaFinalID]}" />
                    <small style="color: red;">${estat.descripcio}</small>
               </c:when>
               
               <c:otherwise>
                  <fmt:message key="${traduccions[estat.tipusEstatDeFirmaFinalID]}" /> 
               </c:otherwise>
             </c:choose>
             </b>
            </small>
            </li>
          </c:forEach>
          </ul>
      </form>
    </c:if>

    <c:if test="${not empty colaboradors}">

      <style>
        .bs-docs-example-cola:after {
        	content: "<fmt:message key="ROLE_COLA.plural" />";
        }
      </style>

      
      <form class="bs-docs-example-cola bs-docs-example form-inline">
        
        <ul style="list-style-type:square;">
           <c:forEach var="estat" items="${colaboradors}"> 
              <li>              
              <small>
              ${pfi:getNom(estat.usuariEntitat.usuariPersona)}  
              &nbsp;<img src="<c:url value="/img/fletxa.gif" />"/>&nbsp;
               <b>
               <c:choose>
                 <c:when test="${empty estat.tipusEstatDeFirmaFinalID}">
                    <fmt:message key="pendent" />
                 </c:when>
                 <c:when test="${estat.tipusEstatDeFirmaFinalID == Constants.TIPUSESTATDEFIRMAFINAL_INVALIDAT }">
                    <fmt:message key="${traduccions[estat.tipusEstatDeFirmaFinalID]}" /><br/>
                    <small style="color: red;">${estat.descripcio}</small>
                    <c:set var="valorrebuig" value="${estat.descripcio}. ${valorrebuig}"/>
                 </c:when>
                 <c:otherwise>
                    <fmt:message key="${traduccions[estat.tipusEstatDeFirmaFinalID]}" /> 
                 </c:otherwise>
               </c:choose>
               </b> 
               </small>              
               </li>
          </c:forEach>
        </ul>
        
        
      </form>
      
    </c:if>

  </div>

</div>

<c:if test="${empty estatDeFirma.tipusEstatDeFirmaFinalID }">
<script>

  function rebutjar(url) {
    var x;
    
    var reason = prompt("<fmt:message key="motiurebutjar"/>:","${pfi:escapeJavaScript(valorrebuig)}");
    
    if (reason!=null) {
      document.location.href = url + "?motiu=" + encodeURIComponent(reason);
    }
  }
  
</script>
</c:if>

<!-- CONTINGUT -->
<div class="span7">

  <!--  Missatges  -->
  <jsp:include page="/WEB-INF/jsp/moduls/missatges.jsp" />
  
  <%--  Visor del Document a firmar i dels annexes --%>
  <jsp:include page="/WEB-INF/jsp/dest/estatFirmaDocuments.jsp" />

  <!-- FINAL DIV CONTINGUT -->
</div>

<div class="clearfix"></div>