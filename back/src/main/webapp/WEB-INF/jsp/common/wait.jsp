<%@ page language="java" 
%><%@ include file="/WEB-INF/jsp/moduls/includes.jsp" 
%>
<table border="0" width="100%">
<tr valign="middle"><td>
   <div style="width: 200px;height: 200px">&nbsp;</div>
   </td>
   <td align="center">
   <img src="<c:url value="/img/spinner_40.gif"/>" loop="infinite" alt="Espera ..." >
   </td>
   <td>
   <iframe width="200px" height="200px"  scrolling="no" frameborder="0" src="" id="imgLoad" srcdoc="<img src='<c:url value="/img/spinner_40.gif"/>' loop='infinite' alt='Espera XXX'" >
    </iframe>     
</td></tr>
<tr><td align="center" colspan="3">
   <i><fmt:message key="processantfirmes" /></i>
</td></tr>
</table>
<form id="reloadform" action="<c:url value="${finalURL}"/>">
</form>

<script>
window.onload = function(e) {
  document.getElementById("reloadform").submit();
}
</script>
