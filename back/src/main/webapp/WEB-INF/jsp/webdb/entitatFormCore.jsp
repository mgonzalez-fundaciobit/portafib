<%-- ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! --%>
<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>
<un:useConstants var="EntitatFields" className="es.caib.portafib.model.fields.EntitatFields"/>
  
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.ENTITATID)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.ENTITATID])?'entitat.entitatID':__theForm.labels[EntitatFields.ENTITATID]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[EntitatFields.ENTITATID]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.ENTITATID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.entitatID" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.ENTITATID)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.ENTITATID)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="50" path="entitat.entitatID" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.NOM)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.NOM])?'entitat.nom':__theForm.labels[EntitatFields.NOM]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[EntitatFields.NOM]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.NOM]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.nom" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.NOM)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.NOM)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="50" path="entitat.nom" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.DESCRIPCIO)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.DESCRIPCIO])?'entitat.descripcio':__theForm.labels[EntitatFields.DESCRIPCIO]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.DESCRIPCIO]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.DESCRIPCIO]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
              <form:errors path="entitat.descripcio" cssClass="errorField alert alert-error" />
              <form:textarea rows="3" readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.DESCRIPCIO)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.DESCRIPCIO)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"   path="entitat.descripcio"  />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.ACTIVA)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.ACTIVA])?'entitat.activa':__theForm.labels[EntitatFields.ACTIVA]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.ACTIVA]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.ACTIVA]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,EntitatFields.ACTIVA)}" >
              <form:errors path="entitat.activa" cssClass="errorField alert alert-error" />
              <form:checkbox onclick="javascript:return ${ gen:contains(__theForm.readOnlyFields ,EntitatFields.ACTIVA)? 'false' : 'true'}" path="entitat.activa" />
          </c:if>
          <c:if test="${gen:contains(__theForm.readOnlyFields ,EntitatFields.ACTIVA)}" >
                <fmt:message key="genapp.checkbox.${__theForm.entitat.activa}" />
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.WEB)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.WEB])?'entitat.web':__theForm.labels[EntitatFields.WEB]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[EntitatFields.WEB]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.WEB]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.web" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.WEB)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.WEB)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="250" path="entitat.web" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.FAVICONID)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.FAVICONID])?'entitat.faviconID':__theForm.labels[EntitatFields.FAVICONID]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[EntitatFields.FAVICONID]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.FAVICONID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
              <form:errors path="entitat.faviconID" cssClass="errorField alert alert-error" />
              <div class="fileupload fileupload-new" data-provides="fileupload" style="margin-bottom: 0px">
                <div class="input-append">
                <c:if test="${!gen:contains(__theForm.readOnlyFields ,EntitatFields.FAVICONID)}" >
                    <div class="uneditable-input span3">
                      <i class="icon-file fileupload-exists"></i>
                      <span class="fileupload-preview"></span>
                    </div>
                    <span class="btn btn-file">
                      <span class="fileupload-new"><fmt:message key="genapp.form.file.select"/></span>
                      <span class="fileupload-exists"><fmt:message key="genapp.form.file.change"/></span>
                      <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.FAVICONID)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.FAVICONID)? 'input uneditable-input' : 'input'}"  path="faviconID" type="file" />
                    </span>
                    <a href="#" class="btn fileupload-exists" data-dismiss="fileupload"><fmt:message key="genapp.form.file.unselect"/></a>
                    <span class="add-on">&nbsp;</span>
                </c:if>
                <c:if test="${not empty __theForm.entitat.favicon}">
                    <span class="add-on">
                        <a target="_blank" href="<c:url value="${pfi:fileUrl(__theForm.entitat.favicon)}"/>">${__theForm.entitat.favicon.nom}</a>
                    </span>
                </c:if>
                </div>
              </div>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.LOGOWEBID)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.LOGOWEBID])?'entitat.logoWebID':__theForm.labels[EntitatFields.LOGOWEBID]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[EntitatFields.LOGOWEBID]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.LOGOWEBID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
              <form:errors path="entitat.logoWebID" cssClass="errorField alert alert-error" />
              <div class="fileupload fileupload-new" data-provides="fileupload" style="margin-bottom: 0px">
                <div class="input-append">
                <c:if test="${!gen:contains(__theForm.readOnlyFields ,EntitatFields.LOGOWEBID)}" >
                    <div class="uneditable-input span3">
                      <i class="icon-file fileupload-exists"></i>
                      <span class="fileupload-preview"></span>
                    </div>
                    <span class="btn btn-file">
                      <span class="fileupload-new"><fmt:message key="genapp.form.file.select"/></span>
                      <span class="fileupload-exists"><fmt:message key="genapp.form.file.change"/></span>
                      <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.LOGOWEBID)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.LOGOWEBID)? 'input uneditable-input' : 'input'}"  path="logoWebID" type="file" />
                    </span>
                    <a href="#" class="btn fileupload-exists" data-dismiss="fileupload"><fmt:message key="genapp.form.file.unselect"/></a>
                    <span class="add-on">&nbsp;</span>
                </c:if>
                <c:if test="${not empty __theForm.entitat.logoWeb}">
                    <span class="add-on">
                        <a target="_blank" href="<c:url value="${pfi:fileUrl(__theForm.entitat.logoWeb)}"/>">${__theForm.entitat.logoWeb.nom}</a>
                    </span>
                </c:if>
                </div>
              </div>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.LOGOWEBPEUID)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.LOGOWEBPEUID])?'entitat.logoWebPeuID':__theForm.labels[EntitatFields.LOGOWEBPEUID]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[EntitatFields.LOGOWEBPEUID]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.LOGOWEBPEUID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
              <form:errors path="entitat.logoWebPeuID" cssClass="errorField alert alert-error" />
              <div class="fileupload fileupload-new" data-provides="fileupload" style="margin-bottom: 0px">
                <div class="input-append">
                <c:if test="${!gen:contains(__theForm.readOnlyFields ,EntitatFields.LOGOWEBPEUID)}" >
                    <div class="uneditable-input span3">
                      <i class="icon-file fileupload-exists"></i>
                      <span class="fileupload-preview"></span>
                    </div>
                    <span class="btn btn-file">
                      <span class="fileupload-new"><fmt:message key="genapp.form.file.select"/></span>
                      <span class="fileupload-exists"><fmt:message key="genapp.form.file.change"/></span>
                      <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.LOGOWEBPEUID)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.LOGOWEBPEUID)? 'input uneditable-input' : 'input'}"  path="logoWebPeuID" type="file" />
                    </span>
                    <a href="#" class="btn fileupload-exists" data-dismiss="fileupload"><fmt:message key="genapp.form.file.unselect"/></a>
                    <span class="add-on">&nbsp;</span>
                </c:if>
                <c:if test="${not empty __theForm.entitat.logoWebPeu}">
                    <span class="add-on">
                        <a target="_blank" href="<c:url value="${pfi:fileUrl(__theForm.entitat.logoWebPeu)}"/>">${__theForm.entitat.logoWebPeu.nom}</a>
                    </span>
                </c:if>
                </div>
              </div>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.LOGOSEGELLID)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.LOGOSEGELLID])?'entitat.logoSegellID':__theForm.labels[EntitatFields.LOGOSEGELLID]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[EntitatFields.LOGOSEGELLID]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.LOGOSEGELLID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
              <form:errors path="entitat.logoSegellID" cssClass="errorField alert alert-error" />
              <div class="fileupload fileupload-new" data-provides="fileupload" style="margin-bottom: 0px">
                <div class="input-append">
                <c:if test="${!gen:contains(__theForm.readOnlyFields ,EntitatFields.LOGOSEGELLID)}" >
                    <div class="uneditable-input span3">
                      <i class="icon-file fileupload-exists"></i>
                      <span class="fileupload-preview"></span>
                    </div>
                    <span class="btn btn-file">
                      <span class="fileupload-new"><fmt:message key="genapp.form.file.select"/></span>
                      <span class="fileupload-exists"><fmt:message key="genapp.form.file.change"/></span>
                      <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.LOGOSEGELLID)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.LOGOSEGELLID)? 'input uneditable-input' : 'input'}"  path="logoSegellID" type="file" />
                    </span>
                    <a href="#" class="btn fileupload-exists" data-dismiss="fileupload"><fmt:message key="genapp.form.file.unselect"/></a>
                    <span class="add-on">&nbsp;</span>
                </c:if>
                <c:if test="${not empty __theForm.entitat.logoSegell}">
                    <span class="add-on">
                        <a target="_blank" href="<c:url value="${pfi:fileUrl(__theForm.entitat.logoSegell)}"/>">${__theForm.entitat.logoSegell.nom}</a>
                    </span>
                </c:if>
                </div>
              </div>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.ADREZAHTML)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.ADREZAHTML])?'entitat.adrezaHtml':__theForm.labels[EntitatFields.ADREZAHTML]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[EntitatFields.ADREZAHTML]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.ADREZAHTML]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
              <form:errors path="entitat.adrezaHtml" cssClass="errorField alert alert-error" />
              <form:textarea cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.ADREZAHTML)? 'mceEditorReadOnly':'mceEditor'}" path="entitat.adrezaHtml"  />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.FILTRECERTIFICATS)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.FILTRECERTIFICATS])?'entitat.filtreCertificats':__theForm.labels[EntitatFields.FILTRECERTIFICATS]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[EntitatFields.FILTRECERTIFICATS]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.FILTRECERTIFICATS]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
              <form:errors path="entitat.filtreCertificats" cssClass="errorField alert alert-error" />
              <form:textarea rows="3" readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.FILTRECERTIFICATS)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.FILTRECERTIFICATS)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"   path="entitat.filtreCertificats"  />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.PDFAUTORITZACIODELEGACIOID)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.PDFAUTORITZACIODELEGACIOID])?'entitat.pdfAutoritzacioDelegacioID':__theForm.labels[EntitatFields.PDFAUTORITZACIODELEGACIOID]}" /> &nbsp;(*)
              <c:if test="${not empty __theForm.help[EntitatFields.PDFAUTORITZACIODELEGACIOID]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.PDFAUTORITZACIODELEGACIOID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
              <form:errors path="entitat.pdfAutoritzacioDelegacioID" cssClass="errorField alert alert-error" />
              <div class="fileupload fileupload-new" data-provides="fileupload" style="margin-bottom: 0px">
                <div class="input-append">
                <c:if test="${!gen:contains(__theForm.readOnlyFields ,EntitatFields.PDFAUTORITZACIODELEGACIOID)}" >
                    <div class="uneditable-input span3">
                      <i class="icon-file fileupload-exists"></i>
                      <span class="fileupload-preview"></span>
                    </div>
                    <span class="btn btn-file">
                      <span class="fileupload-new"><fmt:message key="genapp.form.file.select"/></span>
                      <span class="fileupload-exists"><fmt:message key="genapp.form.file.change"/></span>
                      <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.PDFAUTORITZACIODELEGACIOID)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.PDFAUTORITZACIODELEGACIOID)? 'input uneditable-input' : 'input'}"  path="pdfAutoritzacioDelegacioID" type="file" />
                    </span>
                    <a href="#" class="btn fileupload-exists" data-dismiss="fileupload"><fmt:message key="genapp.form.file.unselect"/></a>
                    <span class="add-on">&nbsp;</span>
                </c:if>
                <c:if test="${not empty __theForm.entitat.pdfAutoritzacioDelegacio}">
                    <span class="add-on">
                        <a target="_blank" href="<c:url value="${pfi:fileUrl(__theForm.entitat.pdfAutoritzacioDelegacio)}"/>">${__theForm.entitat.pdfAutoritzacioDelegacio.nom}</a>
                    </span>
                </c:if>
                </div>
              </div>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.SUPORTTELEFON)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.SUPORTTELEFON])?'entitat.suportTelefon':__theForm.labels[EntitatFields.SUPORTTELEFON]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.SUPORTTELEFON]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.SUPORTTELEFON]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.suportTelefon" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.SUPORTTELEFON)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.SUPORTTELEFON)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="50" path="entitat.suportTelefon" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.SUPORTWEB)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.SUPORTWEB])?'entitat.suportWeb':__theForm.labels[EntitatFields.SUPORTWEB]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.SUPORTWEB]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.SUPORTWEB]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.suportWeb" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.SUPORTWEB)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.SUPORTWEB)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="250" path="entitat.suportWeb" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.SUPORTEMAIL)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.SUPORTEMAIL])?'entitat.suportEmail':__theForm.labels[EntitatFields.SUPORTEMAIL]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.SUPORTEMAIL]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.SUPORTEMAIL]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.suportEmail" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.SUPORTEMAIL)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.SUPORTEMAIL)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="100" path="entitat.suportEmail" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.USUARIAPLICACIOID)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.USUARIAPLICACIOID])?'entitat.usuariAplicacioID':__theForm.labels[EntitatFields.USUARIAPLICACIOID]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.USUARIAPLICACIOID]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.USUARIAPLICACIOID]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
          <form:errors path="entitat.usuariAplicacioID" cssClass="errorField alert alert-error" />
          <c:if test="${gen:contains(__theForm.readOnlyFields ,EntitatFields.USUARIAPLICACIOID)}" >
          <form:hidden path="entitat.usuariAplicacioID"/>
          <input type="text" readonly="true" class="input-xxlarge uneditable-input" value="${gen:findValue(__theForm.entitat.usuariAplicacioID,__theForm.listOfUsuariAplicacioForUsuariAplicacioID)}"  />
          </c:if>
          <c:if test="${!gen:contains(__theForm.readOnlyFields ,EntitatFields.USUARIAPLICACIOID)}" >
          <form:select id="entitat_usuariAplicacioID"  onchange="if(typeof onChangeUsuariAplicacioID == 'function') {  onChangeUsuariAplicacioID(this); };"  cssClass="input-xxlarge" path="entitat.usuariAplicacioID">
          <%-- El camp pot ser null, per la qual cosa afegim una entrada buida --%>
          <form:option value="" ></form:option>
            <c:forEach items="${__theForm.listOfUsuariAplicacioForUsuariAplicacioID}" var="tmp">
            <form:option value="${tmp.key}" >${tmp.value}</form:option>
            </c:forEach>
          </form:select>
          </c:if>
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.MAXUPLOADSIZE)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.MAXUPLOADSIZE])?'entitat.maxUploadSize':__theForm.labels[EntitatFields.MAXUPLOADSIZE]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.MAXUPLOADSIZE]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.MAXUPLOADSIZE]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.maxUploadSize" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.MAXUPLOADSIZE)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.MAXUPLOADSIZE)? 'input-mini uneditable-input' : 'input-mini'}"   path="entitat.maxUploadSize" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.MAXSIZEFITXERADAPTAT)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.MAXSIZEFITXERADAPTAT])?'entitat.maxSizeFitxerAdaptat':__theForm.labels[EntitatFields.MAXSIZEFITXERADAPTAT]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.MAXSIZEFITXERADAPTAT]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.MAXSIZEFITXERADAPTAT]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.maxSizeFitxerAdaptat" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.MAXSIZEFITXERADAPTAT)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.MAXSIZEFITXERADAPTAT)? 'input-mini uneditable-input' : 'input-mini'}"   path="entitat.maxSizeFitxerAdaptat" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.MAXFILESTOSIGNATSAMETIME)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.MAXFILESTOSIGNATSAMETIME])?'entitat.maxFilesToSignAtSameTime':__theForm.labels[EntitatFields.MAXFILESTOSIGNATSAMETIME]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.MAXFILESTOSIGNATSAMETIME]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.MAXFILESTOSIGNATSAMETIME]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.maxFilesToSignAtSameTime" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.MAXFILESTOSIGNATSAMETIME)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.MAXFILESTOSIGNATSAMETIME)? 'input-mini uneditable-input' : 'input-mini'}"   path="entitat.maxFilesToSignAtSameTime" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.POLICYIDENTIFIER)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.POLICYIDENTIFIER])?'entitat.policyIdentifier':__theForm.labels[EntitatFields.POLICYIDENTIFIER]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.POLICYIDENTIFIER]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.POLICYIDENTIFIER]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.policyIdentifier" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.POLICYIDENTIFIER)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.POLICYIDENTIFIER)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="100" path="entitat.policyIdentifier" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.POLICYIDENTIFIERHASH)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.POLICYIDENTIFIERHASH])?'entitat.policyIdentifierHash':__theForm.labels[EntitatFields.POLICYIDENTIFIERHASH]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.POLICYIDENTIFIERHASH]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.POLICYIDENTIFIERHASH]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
              <form:errors path="entitat.policyIdentifierHash" cssClass="errorField alert alert-error" />
              <form:textarea rows="3" readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.POLICYIDENTIFIERHASH)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.POLICYIDENTIFIERHASH)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"   path="entitat.policyIdentifierHash"  />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.POLICYIDENTIFIERHASHALGORITHM)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.POLICYIDENTIFIERHASHALGORITHM])?'entitat.policyIdentifierHashAlgorithm':__theForm.labels[EntitatFields.POLICYIDENTIFIERHASHALGORITHM]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.POLICYIDENTIFIERHASHALGORITHM]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.POLICYIDENTIFIERHASHALGORITHM]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.policyIdentifierHashAlgorithm" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.POLICYIDENTIFIERHASHALGORITHM)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.POLICYIDENTIFIERHASHALGORITHM)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="50" path="entitat.policyIdentifierHashAlgorithm" />
           </td>
        </tr>
        </c:if>
        
        <c:if test="${!gen:contains(__theForm.hiddenFields,EntitatFields.POLICYURLDOCUMENT)}">
        <tr>
          <td>
            <label>
              <fmt:message key="${(empty __theForm.labels[EntitatFields.POLICYURLDOCUMENT])?'entitat.policyUrlDocument':__theForm.labels[EntitatFields.POLICYURLDOCUMENT]}" />
              <c:if test="${not empty __theForm.help[EntitatFields.POLICYURLDOCUMENT]}">
              <i class="icon-info-sign" title="${__theForm.help[EntitatFields.POLICYURLDOCUMENT]}" ></i>
              </c:if>
             </label>
            </td>
            <td>
            <form:errors path="entitat.policyUrlDocument" cssClass="errorField alert alert-error" />
            <form:input readonly="${ gen:contains(__theForm.readOnlyFields ,EntitatFields.POLICYURLDOCUMENT)? 'true' : 'false'}" cssClass="${gen:contains(__theForm.readOnlyFields ,EntitatFields.POLICYURLDOCUMENT)? 'input-xxlarge uneditable-input' : 'input-xxlarge'}"  maxlength="255" path="entitat.policyUrlDocument" />
           </td>
        </tr>
        </c:if>
        