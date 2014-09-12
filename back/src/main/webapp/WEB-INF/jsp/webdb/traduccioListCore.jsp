  <c:if test="${empty traduccioItems}">
     <%@include file="traduccioListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty traduccioItems}">

  <table class="table table-condensed table-bordered table-striped" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="traduccioListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="traduccioListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="traduccioListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="traduccio" items="${traduccioItems}">

        <tr>
          <%@include file="traduccioListCoreMultipleSelect.jsp" %>

          <%@include file="traduccioListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="traduccioListButtons.jsp" %>

            
        </tr>

      </c:forEach>

    </tbody>
  </table>
  </c:if>
  