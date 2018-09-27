<%@page import="es.caib.portafib.utils.Configuracio"%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<%--  ======  AQUI COMEN�A MODAL DE SELECCI� D'USUARI ============ --%>

<style type="text/css">
.modal-body{overflow-y: inherit;}

.modal-open .dropdown-menu {
  z-index: 2050;
}
</style>

<c:if test="${ usuarimodalconfig == null }">
  <c:set var="usuarimodalconfig" value="" />
</c:if>


<div id="selectUser${usuarimodalconfig}Modal" class="modal hide fade" style="width:640px;">
  <form:form modelAttribute="seleccioUsuariForm" action="${theURL}" method="${method}" name="seleccioUsuari${usuarimodalconfig}Form" id="seleccioUsuari${usuarimodalconfig}Form">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
      <h3><fmt:message key="${seleccioUsuariForm.titol}"></fmt:message></h3>
    </div>
    <div class="modal-body">
      
       <%@ include file="/WEB-INF/jsp/common/seleccioUsuariField.jsp"%>
       <br/>
       <div align="center">
       <button id="afegirUsuari${usuarimodalconfig}Modal" type="button" class="btn btn-primary" onclick="selectedUser${usuarimodalconfig}FromModal()" title="<fmt:message key="afegir" />" >
          <i class="icon-plus-sign icon-white"></i>
          <fmt:message key="afegir"/>
      </button>
      </div>
      
    </div>
  </form:form>
</div>
<script type="text/javascript">

  function openSelectUser${usuarimodalconfig}Dialog() {
      $('#selectUser${usuarimodalconfig}Modal').modal('show');
  }
  
</script>


<script type="text/javascript">

function selectedUser${usuarimodalconfig}FromModal() {
    var id = $('#seleccioUsuariForm #id').val();
    <%-- alert(" seleccioUsuari${usuarimodalconfig}Form PRESUBMIT Valor de ID ]" + id + "["); --%>
    
    var param1 = $('#seleccioUsuariForm #param1').val();
    <%-- alert(" seleccioUsuari${usuarimodalconfig}Form PRESUBMIT Valor de ID ]" + param1 + "[");--%>
    
    $('#seleccioUsuari${usuarimodalconfig}Form #id').val(id);
    $('#seleccioUsuari${usuarimodalconfig}Form #param1').val(param1);
    
    if (id) {
      document.getElementById("seleccioUsuari${usuarimodalconfig}Form").submit();
    } else {
      alert("<fmt:message key="formselectionby.error.emptyid"/>");
    }

}

</script>

<%--  ===================================== --%>