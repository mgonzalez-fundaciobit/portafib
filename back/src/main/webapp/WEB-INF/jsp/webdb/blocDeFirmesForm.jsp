
<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
 
  <%@include file="blocDeFirmesFormTitle.jsp" %>


<form:form modelAttribute="blocDeFirmesForm" method="${method}"
  enctype="multipart/form-data">
  
  <c:set var="contexte" value="${blocDeFirmesForm.contexte}"/>
  <form:hidden path="nou" />
  
  <%@include file="blocDeFirmesFormCorePre.jsp" %>
  <%@include file="blocDeFirmesFormCore.jsp" %>

  <%@include file="blocDeFirmesFormCorePost.jsp" %>

  <%@include file="blocDeFirmesFormButtons.jsp" %>

  <c:if test="${blocDeFirmesForm.attachedAdditionalJspCode}">
     <%@include file="../webdbmodificable/blocDeFirmesFormModificable.jsp" %>
  </c:if>

</form:form>

