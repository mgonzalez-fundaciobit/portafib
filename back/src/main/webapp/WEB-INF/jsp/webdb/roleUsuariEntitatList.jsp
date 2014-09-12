<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

<form:form name="roleUsuariEntitat" cssClass="form-search"  modelAttribute="roleUsuariEntitatFilterForm" 
        method="${method}"  enctype="multipart/form-data">

  <%@include file="roleUsuariEntitatListCommon.jsp" %>
  <div class="filterLine lead" style="margin-bootom:10px">
    <%@include file="roleUsuariEntitatListHeaderButtons.jsp" %>
    <%-- ADD HERE NEW HEADER BUTTONS (Multiple Select or similar to add item)  --%>

  </div>
  <%@include file="roleUsuariEntitatListSubtitle.jsp" %>
  <%@include file="roleUsuariEntitatListFilterBy.jsp" %>
  <%-- Inici de div d'AGRUPACIO i TAULA CONTINGUTS --%>  
  <div>
  <%@include file="roleUsuariEntitatListGroupBy.jsp" %>
  <%-- Inici de div de TAULA CONTINGUTS --%>
  <div style="width: 100%;">
  <%@include file="roleUsuariEntitatListCore.jsp" %>
  <c:if test="${not empty roleUsuariEntitatItems}">
          <%@include file="webdbPagination.jsp" %>

  </c:if>

  </div> <%-- Final de div de TAULA CONTINGUTS --%>
  <%--  ADD HERE OTHER CONTENT --%>

  <c:if test="${__theFilterForm.attachedAdditionalJspCode}">
          <%@include file="../webdbmodificable/roleUsuariEntitatListModificable.jsp" %>
  </c:if>
  
  </div> <%-- Final de div d'AGRUPACIO i TAULA CONTINGUTS --%>

</form:form> 
    
