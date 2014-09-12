<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bootom:10px">
  <c:if test="${empty usuariEntitatForm.entityNameCode}">
    <fmt:message var="entityname" key="usuariEntitat.usuariEntitat"/>
  </c:if>
  <c:if test="${not empty usuariEntitatForm.entityNameCode}">
    <fmt:message var="entityname" key="${usuariEntitatForm.entityNameCode}"/>
  </c:if>
  <c:if test="${not empty usuariEntitatForm.titleCode}">
    <fmt:message key="${usuariEntitatForm.titleCode}" >
      <fmt:param value="${usuariEntitatForm.titleParam}" />
    </fmt:message>
  </c:if>
  <c:if test="${empty usuariEntitatForm.titleCode}">
    <c:set var="keytitle" value="${usuariEntitatForm.nou?'genapp.createtitle':(usuariEntitatForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
  </c:if>
  
  <c:if test="${not empty usuariEntitatForm.subTitleCode}">
      <br/><h5 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;"><fmt:message key="${usuariEntitatForm.subTitleCode}" /></h5>
  </c:if>
</div>