<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bootom:10px">
  <c:if test="${empty roleUsuariAplicacioForm.entityNameCode}">
    <fmt:message var="entityname" key="roleUsuariAplicacio.roleUsuariAplicacio"/>
  </c:if>
  <c:if test="${not empty roleUsuariAplicacioForm.entityNameCode}">
    <fmt:message var="entityname" key="${roleUsuariAplicacioForm.entityNameCode}"/>
  </c:if>
  <c:if test="${not empty roleUsuariAplicacioForm.titleCode}">
    <fmt:message key="${roleUsuariAplicacioForm.titleCode}" >
      <fmt:param value="${roleUsuariAplicacioForm.titleParam}" />
    </fmt:message>
  </c:if>
  <c:if test="${empty roleUsuariAplicacioForm.titleCode}">
    <c:set var="keytitle" value="${roleUsuariAplicacioForm.nou?'genapp.createtitle':(roleUsuariAplicacioForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
  </c:if>
  
  <c:if test="${not empty roleUsuariAplicacioForm.subTitleCode}">
      <br/><h5 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;"><fmt:message key="${roleUsuariAplicacioForm.subTitleCode}" /></h5>
  </c:if>
</div>