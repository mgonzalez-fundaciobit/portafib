package es.caib.portafib.back.controller.soli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.fundaciobit.genapp.common.KeyValue.KeyValueComparator;
import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.web.form.AdditionalButton;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NValidationException;
import org.fundaciobit.genapp.common.query.BooleanField;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.GroupByItem;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.StringField;
import org.fundaciobit.genapp.common.query.SubQuery;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.genapp.common.web.HtmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.web.servlet.ModelAndView;

import es.caib.portafib.back.controller.webdb.FluxDeFirmesController;
import es.caib.portafib.back.form.PlantillaDeFluxDeFirmesFilterForm;
import es.caib.portafib.back.form.PlantillaDeFluxDeFirmesForm;
import es.caib.portafib.back.form.webdb.FluxDeFirmesFilterForm;
import es.caib.portafib.back.form.webdb.FluxDeFirmesForm;
import es.caib.portafib.back.form.webdb.UsuariAplicacioRefList;
import es.caib.portafib.back.security.LoginInfo;
import es.caib.portafib.back.utils.MappingOrder;
import es.caib.portafib.back.utils.Utils;
import es.caib.portafib.back.validator.webdb.PlantillaFluxDeFirmesWebValidator;
import es.caib.portafib.ejb.FirmaLocal;
import es.caib.portafib.ejb.PlantillaFluxDeFirmesLocal;
import es.caib.portafib.ejb.UsuariAplicacioLocal;
import es.caib.portafib.jpa.BlocDeFirmesJPA;
import es.caib.portafib.jpa.EstatDeFirmaJPA;
import es.caib.portafib.jpa.FirmaJPA;
import es.caib.portafib.jpa.FluxDeFirmesJPA;
import es.caib.portafib.jpa.PeticioDeFirmaJPA;
import es.caib.portafib.jpa.PlantillaFluxDeFirmesJPA;
import es.caib.portafib.jpa.UsuariEntitatJPA;
import es.caib.portafib.logic.BlocDeFirmesLogicaLocal;
import es.caib.portafib.logic.FluxDeFirmesLogicaLocal;
import es.caib.portafib.logic.UsuariEntitatLogicaLocal;
import es.caib.portafib.model.entity.FluxDeFirmes;
import es.caib.portafib.model.entity.PlantillaFluxDeFirmes;
import es.caib.portafib.model.entity.RoleUsuariEntitat;
import es.caib.portafib.model.entity.UsuariAplicacio;
import es.caib.portafib.model.entity.UsuariPersona;
import es.caib.portafib.model.fields.FluxDeFirmesQueryPath;
import es.caib.portafib.model.fields.PlantillaFluxDeFirmesFields;
import es.caib.portafib.model.fields.RoleUsuariEntitatFields;
import es.caib.portafib.model.fields.UsuariAplicacioFields;
import es.caib.portafib.model.fields.UsuariEntitatFields;
import es.caib.portafib.utils.Configuracio;
import es.caib.portafib.utils.Constants;

/**
 * 
 * @author anadal
 * 
 */
@Controller
@RequestMapping(value = "/soli/plantilla")
@SessionAttributes(types = { 
    PlantillaDeFluxDeFirmesFilterForm.class, PlantillaDeFluxDeFirmesForm.class,
    FluxDeFirmesForm.class, FluxDeFirmesFilterForm.class })
public class PlantillaDeFluxDeFirmesController extends FluxDeFirmesController
   implements Constants {
  
  
  public static final StringField USUARIAPLICACIOID;
  
  public static final BooleanField COMPARTIR_PLANTILLA;
  
  static {
    USUARIAPLICACIOID = new FluxDeFirmesQueryPath().PLANTILLAFLUXDEFIRMES().USUARIAPLICACIOID();
    
    COMPARTIR_PLANTILLA = new FluxDeFirmesQueryPath().PLANTILLAFLUXDEFIRMES().COMPARTIR();
  }
  

  @EJB(mappedName = PlantillaFluxDeFirmesLocal.JNDI_NAME)
  private PlantillaFluxDeFirmesLocal plantillaFluxDeFirmesEjb;

  @EJB(mappedName = "portafib/UsuariEntitatLogicaEJB/local")
  protected UsuariEntitatLogicaLocal usuariEntitatLogicaEjb;

  @EJB(mappedName = "portafib/UsuariAplicacioEJB/local")
  protected UsuariAplicacioLocal usuariAplicacioEjb;

  @EJB(mappedName = "portafib/FirmaEJB/local")
  protected FirmaLocal firmaEjb;

  @EJB(mappedName = "portafib/FluxDeFirmesLogicaEJB/local")
  protected FluxDeFirmesLogicaLocal fluxDeFirmesLogicaEjb;

  @EJB(mappedName = "portafib/BlocDeFirmesLogicaEJB/local")
  protected BlocDeFirmesLogicaLocal blocDeFirmesLogicaEjb;
  
  @EJB(mappedName = "portafib/RoleUsuariEntitatEJB/local")
  protected es.caib.portafib.ejb.RoleUsuariEntitatLocal roleUsuariEntitatEjb;

  @EJB(mappedName = "portafib/PeticioDeFirmaEJB/local")
  protected es.caib.portafib.ejb.PeticioDeFirmaLocal peticioDeFirmaEjb;

  @Autowired
  protected PlantillaFluxDeFirmesWebValidator plantillaFluxDeFirmesValidator;

  // References
  @Autowired
  protected UsuariAplicacioRefList usuariAplicacioRefList;

  protected boolean isViewMode = false;
  
  protected boolean tipusPlantillaInViewMode = false; // true = usuariEntitat | false = usuariAplicacio

  protected MappingOrder mappingOrder = new MappingOrder();
  
  /**
   * 
   */
  public PlantillaDeFluxDeFirmesController() {
    super();
    mappingOrder.addMapping(PlantillaFluxDeFirmesFields.USUARIAPLICACIOID, USUARIAPLICACIOID);
    
    mappingOrder.addMapping(PlantillaFluxDeFirmesFields.COMPARTIR, COMPARTIR_PLANTILLA);
    
  }

  @Override
  public String getTileForm() {
    return "PlantillaDeFluxDeFirmesFormSoli";
  }

  @Override
  public String getTileList() {
    return "PlantillaDeFluxDeFirmesListSoli";
  }

  @Override
  public String getSessionAttributeFilterForm() {
    return "PlantillaFluxDeFirmes_soli";
  }
  
  @PostConstruct
  public void init() {

    usuariAplicacioRefList = new UsuariAplicacioRefList(usuariAplicacioRefList);

    usuariAplicacioRefList.setSelects(new Select<?>[] {
        UsuariAplicacioFields.USUARIAPLICACIOID.select        
    });
    usuariAplicacioRefList.setSeparator("");
  }
  
  
  @RequestMapping(value = "/viewflux/{fluxDeFirmesID}", method = RequestMethod.GET)
  public ModelAndView viewFluxDeFirmesGet(@PathVariable("fluxDeFirmesID") java.lang.Long fluxDeFirmesID,
      HttpServletRequest request, HttpServletResponse response) throws I18NException {
        
    ModelAndView mav = veureFluxDeFirmesGet(fluxDeFirmesID, request, response);
    
    mav.setViewName("PlantillaDeFluxDeFirmesFormSoliPopup");
    
    FluxDeFirmesForm fff = (FluxDeFirmesForm)mav.getModel().get("fluxDeFirmesForm");
    
    // Oculta informacio del propietari de la plantilla (si en té)
    fff.addHiddenField(PlantillaFluxDeFirmesFields.USUARIENTITATID);
    fff.addHiddenField(PlantillaFluxDeFirmesFields.COMPARTIR);

    // Oculta el boto de tornar
    //fff.getHiddenFields().add("tornar");
    fff.setAdditionalButtons(new ArrayList<AdditionalButton>());

    return mav;

  }
  

  @RequestMapping(value = "/viewonlyflux/{fluxDeFirmesID}", method = RequestMethod.GET)
  public ModelAndView viewOnlyFluxDeFirmesGet(@PathVariable("fluxDeFirmesID") java.lang.Long fluxDeFirmesID,
      HttpServletRequest request, HttpServletResponse response) throws I18NException {

    ModelAndView mav = viewFluxDeFirmesGet(fluxDeFirmesID, request, response);
    mav.addObject("onlyFlux", true);
    return mav;
  }
  
  
  @RequestMapping(value = "/viewonlyfluxofpeticio/{peticioDeFirmaID}", method = RequestMethod.GET)
  public ModelAndView viewOnlyFluxOfPeticioGet(@PathVariable("peticioDeFirmaID") java.lang.Long peticioDeFirmaID,
      HttpServletRequest request, HttpServletResponse response) throws I18NException {

    PeticioDeFirmaJPA peticio = peticioDeFirmaEjb.findByPrimaryKey(peticioDeFirmaID);
    
    ModelAndView mav = viewFluxDeFirmesGet(peticio.getFluxDeFirmesID(), request, response);
    mav.addObject("onlyFlux", true);
    return mav;
  }
  
  
  
  @Override
  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {
    
    
    SubQuery<PlantillaFluxDeFirmes, Long> subquery;
    if (isUsuariEntitat()) {
      // Flux de Firmes Usuari-Entitat
      String currentUser = LoginInfo.getInstance().getUsuariEntitatID();
      
      subquery = plantillaFluxDeFirmesEjb.getSubQuery(
          PlantillaFluxDeFirmesFields.FLUXDEFIRMESID,
          PlantillaFluxDeFirmesFields.USUARIENTITATID.equal(currentUser));
      
    } else {
      // Només mostrar els Usuaris Aplicacio de la meva entitat !!!!
      SubQuery<UsuariAplicacio, String> subqueryUA;
      subqueryUA =usuariAplicacioEjb.getSubQuery(UsuariAplicacioFields.USUARIAPLICACIOID,
          UsuariAplicacioFields.ENTITATID.equal(LoginInfo.getInstance().getEntitatID()));
      // Flux de Firmes Usuari-Aplicacio
      
      subquery = plantillaFluxDeFirmesEjb.getSubQuery(
          PlantillaFluxDeFirmesFields.FLUXDEFIRMESID,
          PlantillaFluxDeFirmesFields.USUARIAPLICACIOID.in(subqueryUA));
      
    }

    return FLUXDEFIRMESID.in(subquery);
  }
  
  /**
   * 
   * @param request
   * @return
   */
  public boolean isUsuariEntitat() {
    // L'usuari solicitant no pot gestionar Plantilles de Flux de Firmes 
    // de usuaris-aplicacio
    return true;
  }
  
  @Override
  public void preList(HttpServletRequest request, ModelAndView mav, FluxDeFirmesFilterForm filterForm)  throws I18NException {
    // TODO FICAR DINS FILTER FORM
    if (isUsuariEntitat()) {      
      filterForm.addHiddenField(PlantillaFluxDeFirmesFields.USUARIENTITATID);
    }
  }
  
  
  @Override
  public FluxDeFirmesFilterForm getFluxDeFirmesFilterForm(Integer pagina, ModelAndView mav,
      HttpServletRequest request) throws I18NException {

      FluxDeFirmesFilterForm fluxDeFirmesFilterForm;
      fluxDeFirmesFilterForm = super.getFluxDeFirmesFilterForm(pagina, mav, request);
      
      if (fluxDeFirmesFilterForm.isNou()) {

        fluxDeFirmesFilterForm = new PlantillaDeFluxDeFirmesFilterForm(fluxDeFirmesFilterForm);
 
        //  codi comú
        if (!Configuracio.isDesenvolupament()) {
          fluxDeFirmesFilterForm.addHiddenField(FLUXDEFIRMESID);
        }

        fluxDeFirmesFilterForm.setAddButtonVisible(false);

        
        fluxDeFirmesFilterForm.addHiddenField(PlantillaFluxDeFirmesFields.USUARIENTITATID);
        if (isUsuariEntitat()) {
          fluxDeFirmesFilterForm.addHiddenField(PlantillaFluxDeFirmesFields.USUARIAPLICACIOID);
        }

        fluxDeFirmesFilterForm.addHiddenField(PlantillaFluxDeFirmesFields.FLUXDEFIRMESID);
        
        if (LoginInfo.getInstance().hasRole(Constants.ROLE_ADMIN) ||
            LoginInfo.getInstance().hasRole(Constants.ROLE_ADEN)) {
          fluxDeFirmesFilterForm.addGroupByField(COMPARTIR_PLANTILLA);
        } else {
          fluxDeFirmesFilterForm.addHiddenField(PlantillaFluxDeFirmesFields.COMPARTIR);          
        }

        fluxDeFirmesFilterForm.setVisibleMultipleSelection(true);
    
        if (!isUsuariEntitat()) {
          fluxDeFirmesFilterForm.addGroupByField(USUARIAPLICACIOID);
          
        }
        
      } else {
        // MAPEIG ORDRE
        mappingOrder.processDirectOrderMapping(fluxDeFirmesFilterForm);
      }

      log.info("usuariPersona = " + isUsuariEntitat());
      mav.addObject("usuariPersona", isUsuariEntitat());
            
      return fluxDeFirmesFilterForm;
  }
  
 
  
  @Override
  public void postList(HttpServletRequest request, ModelAndView mav, 
      FluxDeFirmesFilterForm filterForm,  List<FluxDeFirmes> list) throws I18NException {
    
    // DE-MAPEIG ORDRE
    mappingOrder.processIndirectOrderMapping(filterForm);

    String parentContext = isUsuariEntitat()?"soli":"aden";
    
    // Mostrar boto per editar usuaris que poden veure les meves plantilles
    if (isUsuariEntitat() && (LoginInfo.getInstance().hasRole(Constants.ROLE_ADMIN) ||
        !LoginInfo.getInstance().hasRole(Constants.ROLE_ADEN) )) {
      
      
      filterForm.getAdditionalButtonsByPK().clear();

       for (FluxDeFirmes flux : list) {
         if (((FluxDeFirmesJPA)flux).getPlantillaFluxDeFirmes().getCompartir() == null)
         filterForm.addAdditionalButtonByPK(flux.getFluxDeFirmesID(), new AdditionalButton(
           "icon-lock", "fluxDeFirmes.permisos", "/" + parentContext + "/permisosplantilla/view/{0}",
           "btn-warning"));
       }
    } 
    
  }
  
  
  
  @Override
  public Map<Field<?>, GroupByItem> fillReferencesForList(FluxDeFirmesFilterForm filterForm,
      HttpServletRequest request, ModelAndView mav, List<FluxDeFirmes> list,
      List<GroupByItem> groupItems) throws I18NException {

    Map<Field<?>, GroupByItem> groupByItemsMap = super.fillReferencesForList(filterForm,
        request, mav, list, groupItems);

    

    // Field usuariAplicacioID
    if (!isUsuariEntitat()) {
      Map<String, String> _tmp;
      List<StringKeyValue> _listSKV;
      
      
      _listSKV = usuariAplicacioRefList.getReferenceList(
          UsuariAplicacioFields.USUARIAPLICACIOID, null);
      _tmp = org.fundaciobit.genapp.common.utils.Utils.listToMap(_listSKV);
      ((PlantillaDeFluxDeFirmesFilterForm) filterForm) .setMapOfUsuariAplicacioForUsuariAplicacioID(_tmp);      
      
      if (filterForm.getGroupByFields().contains(USUARIAPLICACIOID)) {
        fillValuesToGroupByItems(_tmp, groupByItemsMap, USUARIAPLICACIOID, false);
        groupByItemsMap.get(USUARIAPLICACIOID).setCodeLabel(      
          PlantillaFluxDeFirmesFields.USUARIAPLICACIOID.fullName);
      }

      
      
      
      if (filterForm.getGroupByFields().contains(COMPARTIR_PLANTILLA)) {
        fillValuesToGroupByItemsBoolean("genapp.checkbox", groupByItemsMap, COMPARTIR_PLANTILLA);
        groupByItemsMap.get(COMPARTIR_PLANTILLA).setCodeLabel(
          PlantillaFluxDeFirmesFields.COMPARTIR.fullName);
      }

      
    }
    
    /*
     * _listSKV = this.entitatRefList.getReferenceList(EntitatFields.NOM, null);
      _tmp = org.fundaciobit.genapp.common.utils.Utils.listToMap(_listSKV);
      groupByItemsMap.get(ENTITAT_NOM).setCodeLabel(EntitatFields._TABLE_MODEL + "." + EntitatFields._TABLE_MODEL);
      fillValuesToGroupByItems(_tmp, groupByItemsMap, ENTITAT_NOM, false);

     * 
     */
    
    
    return groupByItemsMap;

  }

  
  
  
  

  @Override
  public FluxDeFirmesForm getFluxDeFirmesForm(FluxDeFirmesJPA _jpa, boolean __isView,
      HttpServletRequest request, ModelAndView mav) throws I18NException {

    FluxDeFirmesForm formBase = super.getFluxDeFirmesForm(_jpa, __isView, request, mav);

    PlantillaDeFluxDeFirmesForm form;
    form = new PlantillaDeFluxDeFirmesForm(formBase);

    // Per paràmetre ens han de passar si estam amb tipus usuari-aplicacio o
    // usuari-entitat
    

    if (form.isNou()) {
      
      // TODO llevar !!!!
      form.setRedirectOnModify(getContextWeb() + "/list/1");
     
      PlantillaFluxDeFirmesJPA ua = new PlantillaFluxDeFirmesJPA();
      ua.setCompartir(false);
      
      if (isUsuariEntitat()) {
        ua.setUsuariEntitatID(LoginInfo.getInstance().getUsuariEntitatID());
      } else {
        // Aquest where ha de filtrar només els usuaris aplicacio de la
        // meva entitat !!!!!
        Where where = UsuariAplicacioFields.ENTITATID.equal(LoginInfo.getInstance().getEntitatID());

        List<StringKeyValue> usernames = usuariAplicacioRefList.getReferenceList(UsuariAplicacioFields.USUARIAPLICACIOID, where);

        Collections.sort(usernames, new KeyValueComparator<String>());
        
        form.setListOfUsuariAplicacioForUsuariAplicacioID(usernames);

        ua.setUsuariEntitatID(null);
      }

      form.setPlantillaFluxDeFirmes(ua);
      
      FluxDeFirmesJPA ff = form.getFluxDeFirmes();
      Set<BlocDeFirmesJPA> blocDeFirmess = new HashSet<BlocDeFirmesJPA>();
      ff.setBlocDeFirmess(blocDeFirmess);
    } else {
      // -- MODE EDICIO
      FluxDeFirmesJPA ff = form.getFluxDeFirmes();
      long fluxID = ff.getFluxDeFirmesID();
      
      // Miram READ ONLY
      String ro = request.getParameter("readOnly");
      form.setReadOnly("true".equals(ro));
      

      form.addHiddenField(PlantillaFluxDeFirmesFields.USUARIENTITATID);
      if (isUsuariEntitat()) {
        form.addHiddenField(PlantillaFluxDeFirmesFields.USUARIAPLICACIOID);
      } else {
        form.addReadOnlyField(PlantillaFluxDeFirmesFields.USUARIAPLICACIOID);
      }
      
      if (form.isReadOnly()) {
        form.addReadOnlyField(NOM);
        
        form.addReadOnlyField(PlantillaFluxDeFirmesFields.DESCRIPCIO);

        form.setDeleteButtonVisible(false);
        form.setSaveButtonVisible(false);
        form.setCancelButtonVisible(false);
      }
      

      // Colors de les caixes de Bloc i Firmes
      /*
      PeticioDeFirmaJPA peticio = ff.getPeticioDeFirma();
      
      log.info(" Entra dins readONLY: " + peticio);

      if (peticio != null) {
          
        // && peticio.getTipusEstatPeticioDeFirmaID() != TIPUSESTATPETICIODEFIRMA_NOINICIAT
        */
        log.info(" Entra dins processarBackgroundColors");
        procesaBackgroundColors(ff, form, ff.getPeticioDeFirma() != null );
      // }
      
      // Carregar plantilla de flux
      PlantillaFluxDeFirmesJPA pff;
      pff = (PlantillaFluxDeFirmesJPA) plantillaFluxDeFirmesEjb.findByPrimaryKey(fluxID);
      if (pff != null) {
        form.setPlantillaFluxDeFirmes(pff);
        if (pff.getUsuariAplicacioID() != null) {
          // No permetem modificar l'usuari seleccionat
          List<StringKeyValue> list = new ArrayList<StringKeyValue>();
          list.add(new StringKeyValue(pff.getUsuariAplicacioID(),pff.getUsuariAplicacioID()));
          // mav.addObject("listOfUsuariAplicacioForUsuariAplicacioID", list);
          form.setListOfUsuariAplicacioForUsuariAplicacioID(list);
        }
      } else {
        form.setPlantillaFluxDeFirmes(pff);
      }
    

    }; // Final mode edició
    
    if (!LoginInfo.getInstance().hasRole(Constants.ROLE_ADMIN) &&
        !LoginInfo.getInstance().hasRole(Constants.ROLE_ADEN)) {
      form.addHiddenField(PlantillaFluxDeFirmesFields.COMPARTIR);
    }

    String redirectOnModify = request.getParameter("redirectOnModify");
    if (redirectOnModify == null) {
      form.setRedirectOnModify(getContextWeb() + "/list/1");
    } else {
      form.setRedirectOnModify(redirectOnModify);
    }
    
    List<UsuariEntitatJPA> users = null;

     // Plantilla de Fluxos d'usuari i per fluxos de la peticio de firma
    PlantillaFluxDeFirmesJPA pff = form.getPlantillaFluxDeFirmes();
    if ((pff == null) || (pff != null && pff.getUsuariAplicacioID() == null)) {
      // Seleccionem l'usuari actual
      LoginInfo loginInfo = LoginInfo.getInstance();
      String currentUser = loginInfo.getUsuariEntitatID();
      
      if (!form.isReadOnly() && (pff!= null && pff.getUsuariEntitatID() != null)) {
        UsuariPersona up = loginInfo.getUsuariPersona();
        String nom = up.getNom() + " " + up.getLlinatges();
        Set<StringKeyValue> list = new HashSet<StringKeyValue>();
        list.add(new StringKeyValue(currentUser, nom));
        form.setListOfUsuariEntitatForUsuariEntitatID(list);

        if (pff!= null && pff.getUsuariEntitatID() != null) {
          pff.setUsuariEntitatID(currentUser);
        }
     }
      

      // Llista de posibles firmants

      // Seleccionem tots els usuaris favorits (ja contenen usuari-persona)
      final boolean incloureCarrecs = true;
      users = usuariEntitatLogicaEjb.selectFavorits(
            currentUser, null /*Constants.ROLE_DEST*/, incloureCarrecs);

    } else {
      
      String entitatID = LoginInfo.getInstance().getEntitatID();

      // La gent que pot estar dins un flux d'usuari aplicació son:
      //  (1) Tots els usuaris-entitat actius amb role ROLE_DEST de la meva entitat i
      //  (2) Tots els càrrecs actius de la meva entitat 
      SubQuery<RoleUsuariEntitat, String> sq;
      sq = roleUsuariEntitatEjb.getSubQuery(RoleUsuariEntitatFields.USUARIENTITATID,
          RoleUsuariEntitatFields.ROLEID.equal(Constants.ROLE_DEST));
      
      Where w = Where.AND(
          UsuariEntitatFields.ENTITATID.equal(entitatID), // de la meva entitat
          UsuariEntitatFields.ACTIU.equal(true), // actius
          Where.OR(
               UsuariEntitatFields.USUARIENTITATID.in(sq), // Role_Dest o
               UsuariEntitatFields.CARREC.isNotNull()  // Es càrrec
               )
          );

      users = usuariEntitatLogicaEjb.selectFull(w);
    }
    
    if (form.isNou() && users.size() == 0) {
      HtmlUtils.saveMessageWarning(request, I18NUtils.tradueix("selectflux.avisnofavorits"));
    }
    
    
    // Llevar de la llista d'usuaris els usuaris que ja estan al flux
    //List<UsuariEntitatJPA> usuarisDelFlux = new ArrayList<UsuariEntitatJPA>();
    Set<BlocDeFirmesJPA> blocs = form.getFluxDeFirmes().getBlocDeFirmess();
    for (BlocDeFirmesJPA blocDeFirmesJPA : blocs) {      
      Set<FirmaJPA> firmes = blocDeFirmesJPA.getFirmas();
      for (FirmaJPA firmaJPA : firmes) {
        users.remove(firmaJPA.getUsuariEntitat());
        //usuarisDelFlux.add(firmaJPA.getUsuariEntitat());
      }
    }

    String urlCancel;
    if (form.getRedirectOnModify() == null) {
      urlCancel = getContextWeb() + "/list/1";
    } else {
      urlCancel = form.getRedirectOnModify();
    }    
    form.addAdditionalButton(new AdditionalButton("", "tornar", urlCancel, ""));
    
    form.setCancelButtonVisible(false);
    
    form.addHiddenField(PlantillaFluxDeFirmesFields.FLUXDEFIRMESID);
    form.addHiddenField(PlantillaFluxDeFirmesFields.USUARIENTITATID);
    if (isUsuariEntitat()) {
      form.addHiddenField(PlantillaFluxDeFirmesFields.USUARIAPLICACIOID);
    }

 
    if (users != null) {
      List<StringKeyValue> listNovaFirma = Utils.usuariEntitatList2StringKeyValue(users);
      form.setListOfUsuariEntitatFavorit(listNovaFirma);
    }

    mav.addObject("fluxDeFirmesForm", form);
    request.getSession().setAttribute("fluxDeFirmesForm", form);
   
    return form;
  }

  

  protected void procesaBackgroundColors(FluxDeFirmesJPA ff,
      PlantillaDeFluxDeFirmesForm form, boolean iniciat) {
    //final String GROC = "FFFFAA";
    final String BLAU = "C0F7FE"; //"BBBBFF";
    final String VERD = "BBFFBB";
    final String ROIG_B = "FAE7EC"; //"E37795"; //"FFA4A4"; // "FF6666";
    final String ROIG_F = "FF7575";    
    final String GRIS = "D0D0D0";
    final String VERMELL = "FF0000";
    
    Map<Long,String> backgroundColorsOfBloc = new HashMap<Long,String>();
    
    Map<Long,String> backgroundColorsOfFirma = new HashMap<Long,String>();
    
    final boolean isDebug = log.isDebugEnabled();
    
    for (BlocDeFirmesJPA bloc : ff.getBlocDeFirmess()) {
      Set<FirmaJPA> firmes = bloc.getFirmas();
      if (iniciat) {
        
        if (isDebug) { log.info("===== COLORS: Bloc[" + bloc.getBlocDeFirmesID() + "]"); }
        
        if (bloc.getDataFinalitzacio() == null) {
            // No iniciat o en progress
          
            // Esta en progres si les firmes tenen estats de firmes
            Set<EstatDeFirmaJPA> estats = null;
            for (FirmaJPA firmaJPA : firmes) {
              estats = firmaJPA.getEstatDeFirmas();
              break;
            }
            
            if (estats == null || estats.size() == 0) {
              // No iniciat
              break;
            } else {
              // En progres
              backgroundColorsOfBloc.put(bloc.getBlocDeFirmesID(), BLAU);
            }          
         
        } else {
          // Finalitzat o rebutjat (si es rebutjat ho processarem dins les firmes)
          backgroundColorsOfBloc.put(bloc.getBlocDeFirmesID(), VERD);
        }
      }

      for (FirmaJPA firma : firmes) {
        if (isDebug) {  log.info("===== COLORS: Firma[" + firma.getFirmaID() + "]"); }
        if (!firma.getUsuariEntitat().isActiu()) {
          backgroundColorsOfFirma.put(firma.getFirmaID(), VERMELL);
        } else {
          if (iniciat) {
            if (firma.getFitxerFirmatID() == null) {
              if (bloc.getDataFinalitzacio() == null) {
                // En progres
                backgroundColorsOfFirma.put(firma.getFirmaID(), BLAU);
              } else {
                Set<EstatDeFirmaJPA> estats = firma.getEstatDeFirmas();
                boolean rebutjat = false;
                for (EstatDeFirmaJPA estat : estats) {
                  if (estat.getTipusEstatDeFirmaFinalID() == TIPUSESTATDEFIRMAFINAL_REBUTJAT) {
                    rebutjat = true;
                    break;
                  }
                }
                if (rebutjat) {
                  backgroundColorsOfFirma.put(firma.getFirmaID(), ROIG_F);
                  backgroundColorsOfBloc.put(bloc.getBlocDeFirmesID(), ROIG_B);
                } else {
                  // Descartat
                  backgroundColorsOfFirma.put(firma.getFirmaID(), GRIS);
                }
              }
            } else {
              // Firmat
              backgroundColorsOfFirma.put(firma.getFirmaID(), VERD);
            }
          }
        }
      }
    }

    form.setBackgroundColorsOfBloc(backgroundColorsOfBloc);
    form.setBackgroundColorsOfFirma(backgroundColorsOfFirma);

  }



  @RequestMapping(value = "/afegirFirma", method = RequestMethod.POST)
  public String afegirFirma(@ModelAttribute @Valid FluxDeFirmesForm fluxDeFirmesForm,
      @RequestParam("usuariEntitatID") String usuariEntitatID,
      @RequestParam("blocID") long blocID, HttpServletRequest request) {

    try {
      // TODO Moure tot aquest mètode a EJB
  
      //log.error("afegirFirma: usuariEntitatID: " + usuariEntitatID + " \\  blocID: " + blocID);
  
      UsuariEntitatJPA usuariEntitat = usuariEntitatLogicaEjb
          .findByPrimaryKeyFull(usuariEntitatID);
      if (usuariEntitat == null) {
        HtmlUtils.saveMessageError(request, I18NUtils.tradueix("error.notfound",
            new String[] {
            I18NUtils.tradueix("usuariEntitat.usuariEntitat"),
            I18NUtils.tradueix("usuariEntitat.usuariEntitatID"),
            String .valueOf(usuariEntitatID)
             }));
        return getTileForm();
      }
  
      BlocDeFirmesJPA bloc = searchBloc(fluxDeFirmesForm, blocID);
  
      if (bloc == null) {
        HtmlUtils.saveMessageError(request, I18NUtils.tradueix("error.notfound",
            new String[] {
            I18NUtils.tradueix("blocDeFirmes.blocDeFirmes"),
            I18NUtils.tradueix("blocDeFirmes.blocDeFirmesID"),
            String .valueOf(blocID)
             }));
        return getTileForm();
      }
  
      FirmaJPA firma = new FirmaJPA();
  
      firma.setBlocDeFirmaID(blocID);
      firma.setDestinatariID(usuariEntitat.getUsuariEntitatID());
      firma.setUsuariEntitat(usuariEntitat);
      firma.setObligatori(true);
  
      firma = (FirmaJPA) firmaEjb.create(firma);
  
      if (bloc.getMinimDeFirmes() == bloc.getFirmas().size()) {
        bloc.getFirmas().add(firma);
        bloc.setMinimDeFirmes(bloc.getFirmas().size());
        blocDeFirmesLogicaEjb.update(bloc);
      } else {
        bloc.getFirmas().add(firma);
      }
      
      
      //long  fluxDeFirmesID = fluxDeFirmesForm.getFluxDeFirmes().getFluxDeFirmesID();    
      afegitUsuariAlFlux(fluxDeFirmesForm, usuariEntitatID);
    } catch(I18NException e) {
      HtmlUtils.saveMessageError(request, I18NUtils.getMessage(e));
    }
    
    
    return getTileForm();
  }

  private void afegitUsuariAlFlux(FluxDeFirmesForm fluxDeFirmesForm,
    String usuariEntitatID) {

    List<StringKeyValue> list = ((PlantillaDeFluxDeFirmesForm)fluxDeFirmesForm).getListOfUsuariEntitatFavorit();
    StringKeyValue toDelete = null;
    for (StringKeyValue stringKeyValue : list) {
      if (stringKeyValue.getKey().equals(usuariEntitatID)) {
        toDelete = stringKeyValue;
        break;
      }
    }
    
    list.remove(toDelete);
    
  }

  @RequestMapping(value = "/afegirBloc", method = RequestMethod.POST)
  public String afegirBloc(@ModelAttribute @Valid FluxDeFirmesForm fluxDeFirmesForm,
      @RequestParam("usuariEntitatID") String usuariEntitatID,
      @RequestParam("blocOrdre") int blocOrdre, HttpServletRequest request) throws Exception {

    try {
      FluxDeFirmesJPA flux = fluxDeFirmesForm.getFluxDeFirmes();
      long fluxID = flux.getFluxDeFirmesID();
      BlocDeFirmesJPA bloc = fluxDeFirmesLogicaEjb.afegirBlocDeFirmesAFlux(fluxID,
          usuariEntitatID, blocOrdre);
  
      Set<BlocDeFirmesJPA> blocs = flux.getBlocDeFirmess();
      blocs.add(bloc);
  
      fluxDeFirmesLogicaEjb.regeneraOrdres(blocs);
      
      afegitUsuariAlFlux(fluxDeFirmesForm, usuariEntitatID);
    } catch(I18NException e) {
      HtmlUtils.saveMessageError(request, I18NUtils.getMessage(e));
    }

    return getTileForm();

  }

  @RequestMapping(value = "/canviMinimDeFirmes", method = RequestMethod.POST)
  public String canviMinimDeFirmes(@ModelAttribute @Valid FluxDeFirmesForm fluxDeFirmesForm,
      @RequestParam("minimDeFirmes") int minimDeFirmes, @RequestParam("blocID") long blocID,
      HttpServletRequest request) throws I18NException {

    BlocDeFirmesJPA bloc = searchBloc(fluxDeFirmesForm, blocID);

    if (bloc == null) {
      HtmlUtils.saveMessageError(request, I18NUtils.tradueix("error.notfound",
          new String[] {
          I18NUtils.tradueix("blocDeFirmes.blocDeFirmes"),
          I18NUtils.tradueix("blocDeFirmes.blocDeFirmesID"),
          String .valueOf(blocID)
           }));
      return getTileForm();
    }
    
    int countObligatori = 0;
    int countNoObligatori = 0;
    for (FirmaJPA f : bloc.getFirmas()) {
      if (f.isObligatori()) {
        countObligatori ++;
      } else {
        countNoObligatori ++;
      }
    }
    
    int afegit = (countNoObligatori == 0)? 0 : 1;
    if (minimDeFirmes >= (countObligatori + afegit)) {
      // OK
      bloc.setMinimDeFirmes(minimDeFirmes);
      blocDeFirmesLogicaEjb.update(bloc);
    } else {
      // TODO Traduir
      HtmlUtils.saveMessageWarning(request,
          "No es posible canviar el mínim de firmes a " 
          + minimDeFirmes +  " en aquest bloc");
    }

    return getTileForm();

  }

  @RequestMapping(value = "/ferFirmaObligatoria", method = RequestMethod.POST)
  public String ferFirmaObligatoria(@ModelAttribute @Valid FluxDeFirmesForm fluxDeFirmesForm,
      @RequestParam("blocID") long blocID, @RequestParam("firmaID") long firmaID,
      HttpServletRequest request) throws I18NException {

    BlocDeFirmesJPA bloc = searchBloc(fluxDeFirmesForm, blocID);

    if (bloc == null) {
      // error.notfound=No s´ha trobat cap {0} amb {1} igual a {2}
      HtmlUtils.saveMessageError(request, I18NUtils.tradueix("error.notfound",
          new String[] {
          I18NUtils.tradueix("blocDeFirmes.blocDeFirmes"),
          I18NUtils.tradueix("blocDeFirmes.blocDeFirmesID"),
          String .valueOf(blocID)
           }));
      return getTileForm();
    }

    FirmaJPA firma = null;
    int countObligatori = 0;
    int countNoObligatori = 0;
    for (FirmaJPA f : bloc.getFirmas()) {
      if (f.getFirmaID() == firmaID) {
        firma = f;        
      }
      if (f.isObligatori()) {
        countObligatori++;
      } else {
        countNoObligatori++;
      }
    }

    if (firma == null) {
      // error.notfound=No s´ha trobat cap {0} amb {1} igual a {2}
      HtmlUtils.saveMessageError(request, I18NUtils.tradueix("error.notfound",
          new String[] {
          I18NUtils.tradueix("firma.firma"),
          I18NUtils.tradueix("firma.firmaID"),
          String .valueOf(firmaID)
           }));
      return getTileForm();
    }

    boolean nouObligatori = !firma.isObligatori();
    
    // TODO Moure a EJB
    int menorValorPerMinimDeFirmes;
    if (nouObligatori == true) {
      // Era una firma no obligatoria i ara volem que sigui obligatoria
      // Revisar Minim del bloc
      menorValorPerMinimDeFirmes = countObligatori + 1 + ((countNoObligatori -1) == 0? 0 : 1);
      
      
    } else {
      // Era una firma  obligatoria i ara volem que sigui no obligatoria
      menorValorPerMinimDeFirmes = countObligatori - 1 + ( (countNoObligatori + 1 )== 0? 0 : 1);
      
      // Podem llevar obligatori si Minim_Firmes > Firmes_obligatories + (1 si existeixen firmes no_obligatories)
      if (menorValorPerMinimDeFirmes == 0) {
        // TODO
        HtmlUtils.saveMessageWarning(request, "No es posible eliminar el flag Obligatori d'aquesta firma.");
        return getTileForm();
      }
    }
    
    if (bloc.getMinimDeFirmes() < menorValorPerMinimDeFirmes) {
      bloc.setMinimDeFirmes(menorValorPerMinimDeFirmes);
      blocDeFirmesLogicaEjb.update(bloc);
    } else {
      if (bloc.getMinimDeFirmes() > bloc.getFirmas().size()) {
        bloc.setMinimDeFirmes(bloc.getFirmas().size());
        blocDeFirmesLogicaEjb.update(bloc);
      }
    }
    
    firma.setObligatori(nouObligatori);
    firmaEjb.update(firma);


    return getTileForm();

  }

  @RequestMapping(value = "/eliminarFirma", method = RequestMethod.POST)
  public String eliminarFirma(@ModelAttribute @Valid FluxDeFirmesForm fluxDeFirmesForm,
      @RequestParam("blocID") long blocID, @RequestParam("firmaID") long firmaID,
      HttpServletRequest request) throws I18NException {

    BlocDeFirmesJPA bloc = searchBloc(fluxDeFirmesForm, blocID);

    if (bloc == null) {
      HtmlUtils.saveMessageError(request, I18NUtils.tradueix("error.notfound",
          new String[] {
          I18NUtils.tradueix("blocDeFirmes.blocDeFirmes"),
          I18NUtils.tradueix("blocDeFirmes.blocDeFirmesID"),
          String .valueOf(blocID)
           }));
      return getTileForm();
    }

    FirmaJPA firma = null;
    int countObligatori = 0;
    for (FirmaJPA f : bloc.getFirmas()) {
      if (f.getFirmaID() == firmaID) {
        firma = f;        
      } else {
        if (f.isObligatori()) {
          countObligatori ++;
        }
      }
    }
    
    
    

    if (firma == null) {
      // error.notfound=No s´ha trobat cap {0} amb {1} igual a {2}
      HtmlUtils.saveMessageError(request, I18NUtils.tradueix("error.notfound",
          new String[] {
          I18NUtils.tradueix("firma.firma"),
          I18NUtils.tradueix("firma.firmaID"),
          String .valueOf(firmaID)
           }));
      return getTileForm();
    }

    // TODO s'ha de passar a EJB

    firmaEjb.delete(firma);
    bloc.getFirmas().remove(firma);
    
    boolean update =false;
    if (countObligatori > bloc.getMinimDeFirmes())  {
      bloc.setMinimDeFirmes(countObligatori);
      update = true;
    }
    
    if (bloc.getMinimDeFirmes() > bloc.getFirmas().size()) {
      bloc.setMinimDeFirmes(bloc.getFirmas().size());
      update = true;
    }
      
    if (update) {
      blocDeFirmesLogicaEjb.update(bloc);
    }
    
    
    UsuariEntitatJPA ue = firma.getUsuariEntitat();
    
    eliminatUsuariDelFlux(fluxDeFirmesForm, ue);
    
    return getTileForm();

    
  }

  private void eliminatUsuariDelFlux(FluxDeFirmesForm fluxDeFirmesForm, UsuariEntitatJPA ue) {
    PlantillaDeFluxDeFirmesForm pfff = (PlantillaDeFluxDeFirmesForm)fluxDeFirmesForm;
    
    List<StringKeyValue> list = pfff.getListOfUsuariEntitatFavorit();
    
    Utils.addUserToList(list, ue);
    Utils.sortStringKeyValueList(list);
  }

  @RequestMapping(value = "/eliminarBloc", method = RequestMethod.POST)
  public String eliminarBloc(@ModelAttribute @Valid FluxDeFirmesForm fluxDeFirmesForm,
      @RequestParam("blocID") long blocID, HttpServletRequest request) throws I18NException {

    BlocDeFirmesJPA bloc = searchBloc(fluxDeFirmesForm, blocID);

    if (bloc == null) {
      HtmlUtils.saveMessageError(request, I18NUtils.tradueix("error.notfound",
          new String[] {
          I18NUtils.tradueix("blocDeFirmes.blocDeFirmes"),
          I18NUtils.tradueix("blocDeFirmes.blocDeFirmesID"),
          String .valueOf(blocID)
           }));
      return getTileForm();
    }

    blocDeFirmesLogicaEjb.deleteFull(blocID);

    fluxDeFirmesForm.getFluxDeFirmes().getBlocDeFirmess().remove(bloc);
    
    for (FirmaJPA firma : bloc.getFirmas()) {
      eliminatUsuariDelFlux(fluxDeFirmesForm, firma.getUsuariEntitat());
    }

    return getTileForm();
  }

  protected BlocDeFirmesJPA searchBloc(FluxDeFirmesForm fluxDeFirmesForm, long blocID) {
    Set<BlocDeFirmesJPA> blocs = fluxDeFirmesForm.getFluxDeFirmes().getBlocDeFirmess();

    BlocDeFirmesJPA bloc = null;
    for (BlocDeFirmesJPA blocDeFirmesJPA : blocs) {
      if (blocID == blocDeFirmesJPA.getBlocDeFirmesID()) {
        bloc = blocDeFirmesJPA;
        break;
      }
    }
    return bloc;
  }
/*
  @Override
  public void postValidate(FluxDeFirmesForm fluxDeFirmesForm, BindingResult result)
     throws Exception {

  }
  */

  @Override
  public void preValidate(FluxDeFirmesForm fluxDeFirmesForm2, BindingResult result)
      throws I18NException {

    final boolean isDebug = log.isDebugEnabled();
    if (isDebug) { log.info("Entra a PreVALIDATE()" + result.getClass()); }
    
    PlantillaDeFluxDeFirmesForm form = (PlantillaDeFluxDeFirmesForm) fluxDeFirmesForm2;

    //FluxDeFirmesJPA flux = fluxDeFirmesForm.getFluxDeFirmes();

    if (form.isNou()) {
      // Utilitzarem el prevalidate per carregar la primera firma dins el
      // fluxDeFirmes

      if (isDebug) { log.info(" PreValidate():: Entra a NOU: " + result.hasErrors()); }

      String usuariEntitat = form.getUsuariEntitatPrimeraFirma();
      
      if (usuariEntitat == null || usuariEntitat.trim().length() == 0) {
        final String name = "usuariEntitatPrimeraFirma";
        result.addError(new FieldError(name, name, null, false, 
            new String[] {"genapp.validation.required" },
            new Object[] { I18NUtils.tradueix("usuarientitatprimerafirma") }, null));
        
        List<StringKeyValue> usersFavorits = form.getListOfUsuariEntitatFavorit(); 
        if (usersFavorits == null || usersFavorits.size() == 0) {
          result.addError(new FieldError(name, name, null, false, 
            new String[] {"selectflux.avisnofavorits"}, null, null));
        }
      } else {

        if (isDebug) {
          log.info(" PreValidate:: NOU --> usuari Entitat es " + usuariEntitat);
        }

        BlocDeFirmesJPA bloc = new BlocDeFirmesJPA();
        bloc.setMinimDeFirmes(1);
  
        FirmaJPA firma = new FirmaJPA();
        firma.setDestinatariID(usuariEntitat);
        firma.setObligatori(true);
  
        bloc.getFirmas().add(firma);
  
        form.getFluxDeFirmes().getBlocDeFirmess().clear();
        form.getFluxDeFirmes().getBlocDeFirmess().add(bloc);
      }
    }
    
    // TODO fullName
    final String modelPlantilla = PlantillaFluxDeFirmesFields._TABLE_MODEL;
    result.recordSuppressedField(modelPlantilla 
        + "." + PlantillaFluxDeFirmesFields.FLUXDEFIRMESID.javaName);

    PlantillaFluxDeFirmesJPA ffup = form.getPlantillaFluxDeFirmes();
    if (ffup != null) {
      BindingResult errors = new BeanPropertyBindingResult(form, "");
      plantillaFluxDeFirmesValidator.validate(form, errors);
      
      if (!isUsuariEntitat()) {
        if (ffup.getUsuariAplicacioID() == null) {
          final String modelApp =  modelPlantilla 
             + "." + PlantillaFluxDeFirmesFields.USUARIAPLICACIOID.javaName;
          ValidationUtils.rejectIfEmptyOrWhitespace(errors, modelApp,
              "genapp.validation.required", new Object[] { I18NUtils.tradueix(modelApp) });
        }
      }
      
      processErrors(result, errors);
    } 

    log.debug("Surt de PreVALIDATE()");
    
   
  }
  
  @Override  
  public void postValidate(FluxDeFirmesForm fluxDeFirmesForm, BindingResult result)  throws I18NException {
    
    if (result.hasErrors()) {
      
      List<ObjectError> list = result.getAllErrors();
      
      log.error(" postValidate HAS ERRORS : " + result.hasErrors());
      
      
      for (ObjectError oe1 : list) {
        
        FieldError oe = (FieldError) oe1;
        
        log.error("Error " + oe.getObjectName() + ": Camp " + oe.getField());
      }
      
    } else {      
      PlantillaDeFluxDeFirmesForm form = (PlantillaDeFluxDeFirmesForm) fluxDeFirmesForm;
      
      FluxDeFirmesJPA flux = form.getFluxDeFirmes();
      
      
      PlantillaFluxDeFirmesJPA ffup = form.getPlantillaFluxDeFirmes();
      if (ffup == null) {
        flux.setPlantillaFluxDeFirmes(null);
      } else {
        flux.setPlantillaFluxDeFirmes(ffup);
      }
      
    }
  }

  

  
  protected void processErrors(BindingResult result, BindingResult errors) {
    List<ObjectError> list = errors.getAllErrors();
    
    log.error(" processErrors 111 : " + errors.hasErrors());
    
    
    for (ObjectError oe1 : list) {
      FieldError oe = (FieldError) oe1;
      
      log.error("Error " + oe.getObjectName() + ": Camp " + oe.getField());
      
      // Ignorar fluxDeFirmesID
      if (oe.getField().endsWith("fluxDeFirmesID")) {
        continue;
      }

      result.addError(oe);
    }
    
    
    log.error(" processErrors 222: " + errors.hasErrors());
  }

  @Override
  public String getRedirectWhenCreated(FluxDeFirmesForm fluxDeFirmesForm) {
    return "redirect:" + getContextWeb() + "/"
        + fluxDeFirmesForm.getFluxDeFirmes().getFluxDeFirmesID() + "/edit";
  }

  @Override
  public String getRedirectWhenModified(FluxDeFirmesForm fluxDeFirmesForm, Throwable __e) {
    
    PlantillaDeFluxDeFirmesForm form = (PlantillaDeFluxDeFirmesForm)fluxDeFirmesForm;
    
    String redirectOnModify = form.getRedirectOnModify();
    
    if (log.isDebugEnabled()) {
      log.debug("getRedirectWhenModified => " + redirectOnModify);
    }
    
    if (redirectOnModify == null) {
      return super.getRedirectWhenModified(fluxDeFirmesForm, __e);
    } else {
      return "redirect:" + redirectOnModify;
    }
  }
  

  @Override
  public FluxDeFirmesJPA create(FluxDeFirmesJPA fluxDeFirmes) throws Exception, I18NException, I18NValidationException {
    // El create ha de crear:
    // (1) El flux
    // (2) Plantilla de Usuari Entitat o Plantilla de Usuari
    // (3) Primera Firma del Flux (Bloc + Firma)
    
    if (log.isDebugEnabled()) {
      PlantillaFluxDeFirmes pff = fluxDeFirmes.getPlantillaFluxDeFirmes();
      if (pff != null) {
        if (pff.getUsuariEntitatID() != null) {
          log.info("Entra dins create (Person): " + pff);        
          log.info("  User:  " + pff.getUsuariEntitatID());
          log.info("  Desc:  " + pff.getDescripcio());
        } else {
          log.info("Entra dins create (Aplic): " + pff);
          log.info("  UserApp:  " + pff.getUsuariAplicacioID());
          if (pff.getCompartir() == null) {
            pff.setCompartir(false);
          }
          
        }        
      }
    }
    return fluxDeFirmesLogicaEjb.createFull(fluxDeFirmes);
  }
  
  @Override
  public FluxDeFirmesJPA update(FluxDeFirmesJPA fluxDeFirmes) throws I18NException, I18NValidationException {
    
    PlantillaFluxDeFirmes pff = fluxDeFirmes.getPlantillaFluxDeFirmes();
    if (pff != null && pff.getUsuariAplicacioID() != null && pff.getCompartir() == null) {
        pff.setCompartir(false);
    }
    
    return fluxDeFirmesLogicaEjb.updateFullPlantillaFluxUsuari(fluxDeFirmes);
  }

  @Override
  public void delete(HttpServletRequest request, FluxDeFirmes fluxDeFirmes)
      throws I18NException {
    fluxDeFirmesLogicaEjb.deleteFull(fluxDeFirmes.getFluxDeFirmesID());
    //return true;
  }

  @Override
  public FluxDeFirmesJPA findByPrimaryKey(java.lang.Long fluxDeFirmesID) throws I18NException {

    FluxDeFirmesJPA fluxDeFirmes;
    fluxDeFirmes = fluxDeFirmesLogicaEjb.findByPrimaryKeyFullForPlantilla(fluxDeFirmesID);

    Set<BlocDeFirmesJPA> blocsUnordered = fluxDeFirmes.getBlocDeFirmess();

    if (blocsUnordered != null) {
      Set<BlocDeFirmesJPA> blocs = new TreeSet<BlocDeFirmesJPA>(
          new Comparator<BlocDeFirmesJPA>() {
            @Override
            public int compare(BlocDeFirmesJPA o1, BlocDeFirmesJPA o2) {
              return o1.getOrdre() - o2.getOrdre();
            }
          });

      blocs.addAll(blocsUnordered);
      fluxDeFirmesLogicaEjb.regeneraOrdres(blocs);

      fluxDeFirmes.setBlocDeFirmess(blocs);
    }

    return fluxDeFirmes;
  }


  
  public List<StringKeyValue> getReferenceListForUsuariAplicacioID(HttpServletRequest request,
      ModelAndView mav, Where where) throws I18NException {
    return usuariAplicacioRefList.getReferenceList(UsuariAplicacioFields.USUARIAPLICACIOID,
        where);
  }
  
  @Override
  public String getEntityNameCode() {
    final String model = PlantillaFluxDeFirmesFields._TABLE_MODEL; 
    return model + "." + model;
  }
  
  
  
  @Override
  public String getEntityNameCodePlural() {
      return getEntityNameCode() + ".plural";
  }
 

  @Override
  @InitBinder("fluxDeFirmesForm")
  public void initBinderForm(WebDataBinder binder) {
    super.initBinderForm(binder);

    binder.setDisallowedFields(FLUXDEFIRMESID.fullName,
    		PlantillaFluxDeFirmesFields.FLUXDEFIRMESID.fullName);
  }


}