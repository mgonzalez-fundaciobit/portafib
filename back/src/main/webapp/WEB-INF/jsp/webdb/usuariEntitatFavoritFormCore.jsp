<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="UsuariEntitatFavoritFields" className="es.caib.portafib.model.fields.UsuariEntitatFavoritFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,UsuariEntitatFavoritFields.ORIGENID)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[UsuariEntitatFavoritFields.ORIGENID])?'usuariEntitatFavorit.origenID':__theForm.labels[UsuariEntitatFavoritFields.ORIGENID]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[UsuariEntitatFavoritFields.ORIGENID]}">
              <i class="icon-info-sign" title="${__theForm.help[UsuariEntitatFavoritFields.ORIGENID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
          <form:errors path="usuariEntitatFavorit.origenID" cssClass="errorField alert alert-error" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,UsuariEntitatFavoritFields.ORIGENID)}" >
          <form:hidden path="usuariEntitatFavorit.origenID"/>
          <input type="text" readonly="true" class="input-xxlarge uneditable-input" value="${gen:findValue(__theForm.usuariEntitatFavorit.origenID,__theForm.listOfUsuariEntitatForOrigenID)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,UsuariEntitatFavoritFields.ORIGENID)}" >
          <form:select id="usuariEntitatFavorit_origenID"  onchange="if(typeof onChangeOrigenID == 'function') {  onChangeOrigenID(this); };"  cssClass="input-xxlarge" path="usuariEntitatFavorit.origenID">
            <c:forEach items="${__theForm.listOfUsuariEntitatForOrigenID}" var="tmp">
            <form:option value="${tmp.key}" >${tmp.value}</form:option>
            </c:forEach>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,UsuariEntitatFavoritFields.FAVORITID)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[UsuariEntitatFavoritFields.FAVORITID])?'usuariEntitatFavorit.favoritID':__theForm.labels[UsuariEntitatFavoritFields.FAVORITID]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[UsuariEntitatFavoritFields.FAVORITID]}">
              <i class="icon-info-sign" title="${__theForm.help[UsuariEntitatFavoritFields.FAVORITID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
          <form:errors path="usuariEntitatFavorit.favoritID" cssClass="errorField alert alert-error" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,UsuariEntitatFavoritFields.FAVORITID)}" >
          <form:hidden path="usuariEntitatFavorit.favoritID"/>
          <input type="text" readonly="true" class="input-xxlarge uneditable-input" value="${gen:findValue(__theForm.usuariEntitatFavorit.favoritID,__theForm.listOfUsuariEntitatForFavoritID)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,UsuariEntitatFavoritFields.FAVORITID)}" >
          <form:select id="usuariEntitatFavorit_favoritID"  onchange="if(typeof onChangeFavoritID == 'function') {  onChangeFavoritID(this); };"  cssClass="input-xxlarge" path="usuariEntitatFavorit.favoritID">
            <c:forEach items="${__theForm.listOfUsuariEntitatForFavoritID}" var="tmp">
            <form:option value="${tmp.key}" >${tmp.value}</form:option>
            </c:forEach>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        