<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="FluxDeFirmesFields" className="es.caib.portafib.model.fields.FluxDeFirmesFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,FluxDeFirmesFields.NOM)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[FluxDeFirmesFields.NOM])?'fluxDeFirmes.nom':__theForm.labels[FluxDeFirmesFields.NOM]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[FluxDeFirmesFields.NOM]}">
              <i class="icon-info-sign" title="${__theForm.help[FluxDeFirmesFields.NOM]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="fluxDeFirmes.nom" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,FluxDeFirmesFields.NOM)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,FluxDeFirmesFields.NOM)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="50" path="fluxDeFirmes.nom" />
           </td>
        </tr>
        </c:if>
        