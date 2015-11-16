<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
  
<div class="lead" style="margin-bottom:10px">
  <c:if test="${empty pluginForm.entityNameCode}">
    <fmt:message var="entityname" key="plugin.plugin"/>
  </c:if>
  <c:if test="${not empty pluginForm.entityNameCode}">
    <fmt:message var="entityname" key="${pluginForm.entityNameCode}"/>
  </c:if>
  <c:if test="${not empty pluginForm.titleCode}">
    <fmt:message key="${pluginForm.titleCode}" >
      <fmt:param value="${pluginForm.titleParam}" />
    </fmt:message>
  </c:if>
  <c:if test="${empty pluginForm.titleCode}">
    <c:set var="keytitle" value="${pluginForm.nou?'genapp.createtitle':(pluginForm.view?'genapp.viewtitle':'genapp.edittitle')}"/>
    <fmt:message key="${keytitle}">
      <fmt:param value="${entityname}"/>
    </fmt:message>
  </c:if>
  
  <c:if test="${not empty pluginForm.subTitleCode}">
  <br/><h5 style="line-height: 10px; margin-top: 0px; margin-bottom: 0px;">
<c:set var="subtitleTranslated" value="${fn:startsWith(pluginForm.subTitleCode,'=')}" />
<c:if test="${subtitleTranslated}">
   <c:out value="${fn:substringAfter(pluginForm.subTitleCode, '=')}" escapeXml="false"/>
</c:if>
<c:if test="${not subtitleTranslated}">
  <fmt:message key="${pluginForm.subTitleCode}" />
</c:if>
</h5>
  </c:if>
</div>