  <c:if test="${empty tipusFirmaItems}">
     <%@include file="tipusFirmaListEmpty.jsp" %>

  </c:if>
  
  <c:if test="${not empty tipusFirmaItems}">

  <table class="table table-condensed table-bordered table-striped" style="width:auto;"> 
    <thead>
      <tr>

          <%@include file="tipusFirmaListCoreHeaderMultipleSelect.jsp" %>

          <%@include file="tipusFirmaListCoreHeader.jsp" %>

          <%-- ADD HERE NEW COLUMNS HEADER  --%>

          <%@include file="tipusFirmaListButtonsHeader.jsp" %>

      </tr>
    </thead>
    <tbody>

      <c:forEach var="tipusFirma" items="${tipusFirmaItems}">

        <tr>
          <%@include file="tipusFirmaListCoreMultipleSelect.jsp" %>

          <%@include file="tipusFirmaListCoreContent.jsp" %>

          <%--  ADD HERE NEW COLUMNS CONTENT --%>


          <%@include file="tipusFirmaListButtons.jsp" %>

            
        </tr>

      </c:forEach>

    </tbody>
  </table>
  </c:if>
  