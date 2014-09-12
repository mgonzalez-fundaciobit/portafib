<%@ include file="/WEB-INF/jsp/moduls/includes.jsp"%>

<c:set var="contexte" value="${seleccioFluxDeFirmesForm.contexte}" />

<c:if test="${fn:startsWith(contexte,'/aden/')}" >
    <c:set var="context_role" value="aden" />
</c:if>

<c:if test="${fn:startsWith(contexte,'/soli/')}" >
    <c:set var="context_role" value="soli" />
</c:if>

<h4><fmt:message key="selectflux.title" /></h4>
<c:url var="theAction" value="${contexte}/selectflux" />
<form:form modelAttribute="seleccioFluxDeFirmesForm" method="${method}" action="${theAction}" name="seleccioFluxDeFirmesForm">

<table class="table table-condensed table-bordered table-striped" style="width:auto;"> 
<tr>
  <td><label><fmt:message key="nom" />:</label></td>
  <td>
    <form:errors path="nom" cssClass="errorField alert alert-error" />
     <form:input cssClass="input-xxlarge" path="nom"/>
  </td>
</tr>

<c:if test="${seleccioFluxDeFirmesForm.solicitantUsuariEntitat == false}">
<tr>
  <td><label><fmt:message key="peticioDeFirma.usuariAplicacioID" />:</label></td>
  <td>
    <form:errors path="usuariAplicacioID"  cssClass="errorField alert alert-error" />
    <form:input cssClass="input-xlarge" readonly="true" path="usuariAplicacioID"/>
  </td>
</tr>
</c:if>

</table>

<c:if test="${seleccioFluxDeFirmesForm.tipus != -1}">
<div class="alert">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <strong><fmt:message key="selectflux.editarfluxposteriorment" /></strong>
</div>
</c:if>

<c:if test="${not empty seleccioFluxDeFirmesForm.listOfUsuariFavorit}">
<table class="table table-condensed table-bordered table-striped" style="width:auto;"> 
<tbody>
  <tr>
    <td>
       <label class="radio">
       <form:radiobutton path="tipus"  value="3" onChange="radioChanged();" />
       <fmt:message key="selectflux.crearnou" />
       </label>
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="selectflux.selectusers" />
      <form:errors path="usuarisFlux" cssClass="errorField alert alert-error" />
      <table border="0">
        <tr>
          <td>
              <form:select path="usuarisFlux" cssClass="input-xlarge" multiple="true" size="4">
              </form:select>
          </td>
          <td>
            <button style="btn btn-primary" type="button" id="botoafegir" onclick="afegir()">&lt;&lt; </button>    
          </td>
          <td>
              <select name="allUsuarisFavorits" id="allUsuarisFavorits" class="input-xlarge" multiple size="4">          
                <c:forEach items="${seleccioFluxDeFirmesForm.listOfUsuariFavorit}" var="usuariEntitat">
                
                <option value="${usuariEntitat.key}">${usuariEntitat.value}</option>
                </c:forEach>
              </select>
          </td>
        </tr>
      </table>

    </td>
  </tr>
</tbody>
</table>
</c:if>

<c:if test="${not empty seleccioFluxDeFirmesForm.listOfFluxPlantillaUsuari}">
<table class="table table-condensed table-bordered table-striped" style="width:auto;"> 
<tbody>
  <tr>
    <td style="width:200px">
       <label class="radio">
       <form:radiobutton path="tipus" value="0" onChange="radioChanged();" disabled="${empty seleccioFluxDeFirmesForm.listOfFluxPlantillaUsuari}"/>
       
       <c:if test="${seleccioFluxDeFirmesForm.solicitantUsuariEntitat == true}">
       <fmt:message  key="selectflux.plantillesmeves"/>
       </c:if>
       <c:if test="${seleccioFluxDeFirmesForm.solicitantUsuariEntitat == false}">
       <fmt:message  key="selectflux.plantillesde">
           <fmt:param>${seleccioFluxDeFirmesForm.usuariAplicacioID}</fmt:param>
       </fmt:message>
       </c:if>

       </label>
    </td>
    <td>
        <form:select id="fluxPlantillaUsuari" path="fluxPlantillaUsuari"  onchange="canviNom(this)" cssClass="input-xxlarge">          
          <c:forEach items="${seleccioFluxDeFirmesForm.listOfFluxPlantillaUsuari}" var="tmp">
          <form:option value="${tmp.fluxDeFirmesID}"  >${tmp.nom} </form:option>
          </c:forEach>
        </form:select>
    </td>
    <td>
      <button id="fluxUsuariView" onclick="mostrarFlux('fluxPlantillaUsuari')" type="button" class="btn"><i class="icon-eye-open"></i></button>
    </td>
  </tr>
</tbody>
</table>
</c:if>

<c:if test="${not empty seleccioFluxDeFirmesForm.listOfFluxPlantillaPersonaCompartit}">  
<table class="table table-condensed table-bordered table-striped" style="width:auto;"> 
<tbody>
  <tr>
    <td style="width:200px"> 
       <label class="radio">     
       <form:radiobutton path="tipus"  value="1" onChange="radioChanged();" disabled="${empty seleccioFluxDeFirmesForm.listOfFluxPlantillaPersonaCompartit}"/>
       <fmt:message key="selectflux.plantillescompartides" />
       </label>
    </td>
    <td>
        <form:select id="fluxPlantillaPersonaCompartit" path="fluxPlantillaPersonaCompartit" onchange="canviNom(this)" cssClass="input-xxlarge">          
          <c:forEach items="${seleccioFluxDeFirmesForm.listOfFluxPlantillaPersonaCompartit}" var="tmp">
          <form:option value="${tmp.fluxDeFirmesID}"  >${tmp.nom}</form:option>
          </c:forEach>
        </form:select>
    </td>
    <td>
      <button id="fluxSharedView" onclick="mostrarFlux('fluxPlantillaPersonaCompartit')" type="button" class="btn"><i class="icon-eye-open"></i></button>
    </td>
  </tr>
</tbody>
</table>
</c:if>

<c:if test="${not empty seleccioFluxDeFirmesForm.listOfFluxPlantillaAplicacioCompartit}">
<table class="table table-condensed table-bordered table-striped" style="width:auto;"> 
<tbody>
  <tr>
    <td style="width:200px">
       <label class="radio">
       <form:radiobutton path="tipus"  value="2" onChange="radioChanged();" disabled="${empty seleccioFluxDeFirmesForm.listOfFluxPlantillaAplicacioCompartit}"/>
       <fmt:message key="selectflux.plantillesaplicacions" />
       </label>
    </td>
    <td>
        <form:select id="fluxPlantillaAplicacioCompartit" path="fluxPlantillaAplicacioCompartit" onchange="canviNom(this)" cssClass="input-xxlarge">          
          <c:forEach items="${seleccioFluxDeFirmesForm.listOfFluxPlantillaAplicacioCompartit}" var="tmp">
          <form:option value="${tmp.fluxDeFirmesID}">${tmp.nom}  </form:option>
          </c:forEach>
        </form:select>
    </td>
    <td>
      <button id="fluxAplicacioView" onclick="mostrarFlux('fluxPlantillaAplicacioCompartit')" type="button" class="btn"><i class="icon-eye-open"></i></button>
    </td>
  </tr>
</tbody>
</table>
</c:if>

<c:if test="${seleccioFluxDeFirmesForm.tipus != -1}">
<button class="btn btn-primary" type="button" onclick="preSubmit()"  class="btn"><fmt:message key="acceptar"/></button>
</c:if>
<script language="JavaScript" type="text/javascript">

      function mostrarFlux(selectID) {
    	  var e = document.getElementById(selectID);
    	  var flux = e.options[e.selectedIndex].value;
          window.open('<c:url value="/${context_role}/plantilla/viewflux"/>/' + flux + '?readOnly=true' ,'popup','toolbar=no,directories=no,menubar=no,location=no,scrollbars=yes,width=560,height=650');
      }

      function canviNom(select) { 

    	  if (select.selectedIndex == -1) {
    	        return null;
    	  }
    	  nom = document.getElementById("nom").value;
 
          var copiade;
          copiade = '<fmt:message key="copiade" />';

          emptycopiade = copiade.replace('{0}', ''); 

          if (nom != "" && nom.indexOf(emptycopiade) == -1) {
            // Si el nom no és buit i el nom no ha estat generat, llavors no feim res
            return;            
          } else {
            nomPlantilla = select.options[select.selectedIndex].text;

            // TODO revisar escape
            nomPlantilla = copiade.replace('{0}', nomPlantilla); 

            document.getElementById("nom").value=nomPlantilla;
          }
      }

      function afegir(){

        var origen = document.getElementById("allUsuarisFavorits");
        var desti =    document.getElementById("usuarisFlux");

        if (origen.selectedIndex == -1) {
            return;
        }

        var selectValue = origen.options[origen.selectedIndex].value;
        var selectText = origen.options[origen.selectedIndex].text;

        //alert(selectValue + " " + selectText);

        $("#usuarisFlux").append(new Option(selectText, selectValue));
      }

      function seleccionarTotsUsuarisFavorits() {
    	  var desti = document.getElementById("usuarisFlux");
    	  for (var i = 0; i < desti.options.length; i++) { 
              desti.options[i].selected = true; 
          }
      }

      function preSubmit() {
          
    	  var tipus = getRadioButtonSelectedValue("tipus");
              //document.seleccioFluxDeFirmesForm.tipus);

          if (tipus == undefined) {
              return false;
          }
        
          //alert(tipus);
          // check nom
          /*
          var nom = document.getElementById("nom").value;
          if (nom == "") {
               alert('ha de posar un nom (traduir)');
               return;
          }
          */
          

          switch(tipus) {

            case '3':

                var len = document.getElementById("usuarisFlux").length;
                //alert("Items: " + len);
/*
                if (len == 0) {
                  // TODO traduir)
                  alert('Ha de seleccionar usuaris al flux de firma (traduir)');
                  return;
                }
*/
                // check com a minim una firma
                seleccionarTotsUsuarisFavorits();

            break;

            case '0':
            case '1':
            case '2':
                // OK
                break;

            
  
            default:
               alert("Tipus de flux no gestionat " + tipus);
               return;    
          
          }
          document.seleccioFluxDeFirmesForm.submit();
      }


      function getRadioButtonSelectedValue( groupName ) {
    	    var radios = document.getElementsByName( groupName );
    	    for( i = 0; i < radios.length; i++ ) {
    	        if( radios[i].checked ) {
    	            return radios[i].value;
    	        }
    	    }
    	    return null;
    	}

      

      function radioChanged() {
    	  disableAll();
    	  var tipus = getRadioButtonSelectedValue("tipus");
              //document.seleccioFluxDeFirmesForm.tipus);
    	  //alert("radioChanged() => " + tipus);
    	  switch(tipus)
    	  {
        	  case '0':
        		  enable("fluxPlantillaUsuari");
        		  enable("fluxUsuariView");
        		  canviNom(document.getElementById("fluxPlantillaUsuari"));
              break;
        	  case '1':
        		  enable("fluxPlantillaPersonaCompartit");
        		  enable("fluxSharedView");
        		  canviNom(document.getElementById("fluxPlantillaPersonaCompartit"));
        	  break;
        	  case '2':
        		  enable("fluxPlantillaAplicacioCompartit");
        		  enable("fluxAplicacioView");
        		  canviNom(document.getElementById("fluxPlantillaAplicacioCompartit"));
        	  break;
        	  case '3': // usuaris favorits
        	  default:
        		enable("botoafegir");
        	    enable("nom");
        	    enable("allUsuarisFavorits");
                enable("usuarisFlux");
    	  }
      }

      function enable(id) {
        if (document.getElementById(id)) {
    	  document.getElementById(id).disabled='';
        }
      }

      function disableAll() {
    	  var elements = ['fluxPlantillaUsuari', 'fluxUsuariView',
                      'fluxPlantillaPersonaCompartit','fluxSharedView',
                      'fluxPlantillaAplicacioCompartit', 'fluxAplicacioView',
                      'allUsuarisFavorits','usuarisFlux', 'botoafegir'];

    	  for (i=0;i<elements.length;i++){ 
            //document.getElementById(elements[i]).disabled=true;
            if (document.getElementById(elements[i])) {
    		  document.getElementById(elements[i]).disabled='disabled';
            }
    	  }

          if (document.getElementById('botoafegir')) {
    	    document.getElementById('botoafegir').disabled='disabled';
          }

      }

      radioChanged();

</script>



</form:form>