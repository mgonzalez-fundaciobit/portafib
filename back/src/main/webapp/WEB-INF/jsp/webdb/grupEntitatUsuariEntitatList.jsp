<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

<form:form name="grupEntitatUsuariEntitat" cssClass="form-search"  modelAttribute="grupEntitatUsuariEntitatFilterForm" 
        method="${method}"  enctype="multipart/form-data">

  <%@include file="grupEntitatUsuariEntitatListCommon.jsp" %>
  <div class="filterLine lead" style="margin-bootom:10px">
    <%@include file="grupEntitatUsuariEntitatListHeaderButtons.jsp" %>
    <%-- ADD HERE NEW HEADER BUTTONS (Multiple Select or similar to add item)  --%>

  </div>
  <%@include file="grupEntitatUsuariEntitatListSubtitle.jsp" %>
  <%@include file="grupEntitatUsuariEntitatListFilterBy.jsp" %>
  <%-- Inici de div d'AGRUPACIO i TAULA CONTINGUTS --%>  
  <div>
  <%@include file="grupEntitatUsuariEntitatListGroupBy.jsp" %>
  <%-- Inici de div de TAULA CONTINGUTS --%>
  <div style="width: 100%;">
  <%@include file="grupEntitatUsuariEntitatListCore.jsp" %>
  <c:if test="${not empty grupEntitatUsuariEntitatItems}">
          <%@include file="webdbPagination.jsp" %>

  </c:if>

  </div> <%-- Final de div de TAULA CONTINGUTS --%>
  <%--  ADD HERE OTHER CONTENT --%>

  <c:if test="${__theFilterForm.attachedAdditionalJspCode}">
          <%@include file="../webdbmodificable/grupEntitatUsuariEntitatListModificable.jsp" %>
  </c:if>
  
  </div> <%-- Final de div d'AGRUPACIO i TAULA CONTINGUTS --%>

</form:form> 
    
