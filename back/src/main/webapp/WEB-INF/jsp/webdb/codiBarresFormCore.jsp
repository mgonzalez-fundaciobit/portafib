<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="CodiBarresFields" className="es.caib.portafib.model.fields.CodiBarresFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,CodiBarresFields.CODIBARRESID)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[CodiBarresFields.CODIBARRESID])?'codiBarres.codiBarresID':__theForm.labels[CodiBarresFields.CODIBARRESID]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[CodiBarresFields.CODIBARRESID]}">
              <i class="icon-info-sign" title="${__theForm.help[CodiBarresFields.CODIBARRESID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="codiBarres.codiBarresID" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,CodiBarresFields.CODIBARRESID)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,CodiBarresFields.CODIBARRESID)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="255" path="codiBarres.codiBarresID" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,CodiBarresFields.NOM)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[CodiBarresFields.NOM])?'codiBarres.nom':__theForm.labels[CodiBarresFields.NOM]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[CodiBarresFields.NOM]}">
              <i class="icon-info-sign" title="${__theForm.help[CodiBarresFields.NOM]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="codiBarres.nom" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,CodiBarresFields.NOM)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,CodiBarresFields.NOM)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="50" path="codiBarres.nom" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,CodiBarresFields.DESCRIPCIO)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[CodiBarresFields.DESCRIPCIO])?'codiBarres.descripcio':__theForm.labels[CodiBarresFields.DESCRIPCIO]}" />
              <c:if test="${not empty __theForm.help[CodiBarresFields.DESCRIPCIO]}">
              <i class="icon-info-sign" title="${__theForm.help[CodiBarresFields.DESCRIPCIO]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
              <form:errors path="codiBarres.descripcio" cssClass="errorField alert alert-error" />
              <form:textarea rows="3" readonly="${ gen:contains(__theForm.readOnlyFields ,CodiBarresFields.DESCRIPCIO)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,CodiBarresFields.DESCRIPCIO)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"   path="codiBarres.descripcio"  />
           </td>
        </tr>
        </c:if>
        