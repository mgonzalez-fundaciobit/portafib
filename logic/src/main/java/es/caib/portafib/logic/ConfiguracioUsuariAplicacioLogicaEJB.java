package es.caib.portafib.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.caib.portafib.ejb.UsuariAplicacioConfiguracioEJB;
import es.caib.portafib.model.entity.PerfilsPerUsuariAplicacio;
import es.caib.portafib.model.entity.UsuariAplicacioConfiguracio;
import es.caib.portafib.model.entity.PerfilDeFirma;
import es.caib.portafib.model.fields.PerfilsPerUsuariAplicacioFields;
import es.caib.portafib.model.fields.PerfilsPerUsuariAplicacioQueryPath;
import es.caib.portafib.model.fields.PerfilDeFirmaFields;
import es.caib.portafib.utils.ConstantsV2;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleSignDocumentRequest;
import org.fundaciobit.apisib.apifirmasimple.v1.beans.FirmaSimpleUpgradeRequest;
import org.fundaciobit.genapp.common.i18n.I18NArgumentString;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.pluginsib.utils.templateengine.TemplateEngine;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 * 
 * @author anadal
 *
 */
@Stateless(name = "ConfiguracioUsuariAplicacioLogicaEJB")
@SecurityDomain("seycon")
public class ConfiguracioUsuariAplicacioLogicaEJB extends UsuariAplicacioConfiguracioEJB
    implements ConfiguracioUsuariAplicacioLogicaLocal {

  @EJB(mappedName = es.caib.portafib.ejb.PerfilsPerUsuariAplicacioLocal.JNDI_NAME)
  protected es.caib.portafib.ejb.PerfilsPerUsuariAplicacioLocal perfilsPerUsuariAplicacioEjb;

  @EJB(mappedName = es.caib.portafib.ejb.PerfilDeFirmaLocal.JNDI_NAME)
  protected es.caib.portafib.ejb.PerfilDeFirmaLocal perfilDeFirmaEjb;

  /*
  @Override
  @RolesAllowed({ "PFI_ADMIN", "PFI_USER" })
  public UsuariAplicacioConfiguracio getConfiguracioUsuariAplicacio(
      final String usuariAplicacioID, String codiPerfil, final int usFirma)
      throws I18NException {

    PerfilDeFirma usuariApicacioPerfil = getPerfilDeFirma(usuariAplicacioID, codiPerfil,
        usFirma);

    // XYZ ZZZ Falta Fer !!!!
    // Aqui s'ha de processar la condició del Perfil i veure quin valor retorna
    // A partir del valor sencer obtingut retornar UsrAppConfiguracio1ID
    // o UsrAppConfiguracio2ID o UsrAppConfiguracio3ID

    final Long idConf = usuariApicacioPerfil.getConfiguracioDeFirma1ID();

    final UsuariAplicacioConfiguracio config = findByPrimaryKey(idConf);

    checkTePermisPerUsDeFirma(usuariAplicacioID, codiPerfil, usFirma, config);

    return config;
  }
  */

  protected void checkTePermisPerUsDeFirma(final String usuariAplicacioID, String codiPerfil,
      final int usFirma, final UsuariAplicacioConfiguracio config) throws I18NException {
    boolean tePermis = false;
    String nom;
    switch (usFirma) {
      case ConstantsV2.US_FIRMA_CONF_APP_APIFIRMASIMPLESERVIDOR:
        tePermis = config.isUsEnFirmaApiSimpleServidor();
        nom = "US_FIRMA_CONF_APP_APIFIRMASIMPLESERVIDOR";
      break;
      case ConstantsV2.US_FIRMA_CONF_APP_APIFIRMASIMPLEWEB:
        tePermis = config.isUsEnFirmaApiSimpleServidor();
        nom = "US_FIRMA_CONF_APP_APIFIRMASIMPLEWEB";
      break;
      case ConstantsV2.US_FIRMA_CONF_APP_FIRMAWEB:
        tePermis = config.isUsEnFirmaApiSimpleServidor();
        nom = "US_FIRMA_CONF_APP_FIRMAWEB";
      break;
      case ConstantsV2.US_FIRMA_CONF_APP_FIRMAWS2:
        tePermis = config.isUsEnFirmaApiSimpleServidor();
        nom = "US_FIRMA_CONF_APP_FIRMAWS2";
      break;
      case ConstantsV2.US_FIRMA_CONF_APP_PASSARELAFIRMASERVIDOR:
        tePermis = config.isUsEnFirmaApiSimpleServidor();
        nom = "US_FIRMA_CONF_APP_PASSARELAFIRMASERVIDOR";
      break;
      case ConstantsV2.US_FIRMA_CONF_APP_PASSARELAFIRMAWEB:
        tePermis = config.isUsEnFirmaApiSimpleServidor();
        nom = "US_FIRMA_CONF_APP_PASSARELAFIRMAWEB";
      break;
      default:
        // XYZ ZZZ TRA Traduir
        throw new I18NException("genapp.comodi", "Ús de Firma amb codi  " + usFirma
            + " desconegut." + "Consulti amb l'Administrador.");

    }

    if (!tePermis) {

      // XYZ ZZZ TRA Traduir
      throw new I18NException("genapp.comodi", "La configuració de firma " + config.getNom()
          + " enllaçada amb el perfil de firma amb codi " + codiPerfil
          + " i associat a l'usuari aplicació " + usuariAplicacioID
          + " no té permis per ser usat en firmes de tipus " + nom
          + ". Consulti amb l'Administrador.");
    }
    
    
     // Check si es firma en servidor que tengui definit el Plugin
    if ((usFirma == ConstantsV2.US_FIRMA_CONF_APP_APIFIRMASIMPLESERVIDOR)
        || (usFirma == ConstantsV2.US_FIRMA_CONF_APP_PASSARELAFIRMASERVIDOR)) {

      Long pluginId = config.getPluginFirmaServidorID();

      if (pluginId == null) {
        // XYZ ZZZ Traduir
        throw new I18NException("genapp.comodi", "La configuració de firma "
            + config.getNom() + " enllaçada amb el perfil de firma amb codi " + codiPerfil
            + " i associat a l'usuari aplicació " + usuariAplicacioID
            + " no té definit el plugin de firma en servidor. "
            + "Consulti amb l'Administrador.");
      }
    }
    
    
  }

  @Override
  @RolesAllowed({ "PFI_ADMIN", "PFI_USER" })
  public PerfilDeFirma getPerfilDeFirma(final String usuariAplicacioID, String codiPerfil,
      final int usFirma) throws I18NException {
    
    if (codiPerfil == null || codiPerfil.trim().length() == 0) {
      // XYZ ZZZ TRA Traduir
      throw new I18NException("genapp.comodi", "S'ha fet una cridada REST amb l´usuari aplicació "
          + usuariAplicacioID + " però s'ha indicat un perfil de firma null o buit."
          + ". Consulti amb l'Administrador.");
    }
    
    // Check si codiPerfil existeix
    PerfilDeFirma perfilDeFirma = getPerfilDeFirmaByCodi(codiPerfil);

    // Check si usuariAplicacio té assignat aquest codi perfil
    // PerfilsPerUsuariAplicacio perfil;
    {

      Where w = Where.AND(PerfilsPerUsuariAplicacioFields.PERFILDEFIRMAID.equal(perfilDeFirma
          .getUsuariAplicacioPerfilID()), PerfilsPerUsuariAplicacioFields.USUARIAPLICACIOID
          .equal(usuariAplicacioID));

      List<PerfilsPerUsuariAplicacio> perfils = perfilsPerUsuariAplicacioEjb.select(w);

      if (perfils == null || perfils.size() == 0) {
        // XYZ ZZZ TRA Traduir
        throw new I18NException("genapp.comodi", "El codi de perfil " + codiPerfil
            + " no està associat a l'usuari aplicació " + usuariAplicacioID
            + ". Consulti amb l'Administrador.");
      }

      // perfil = perfils.get(0);
    }

    // Cercar si tenim permis per aquell ús o si alguna de les configuracions
    // és firma en servidor que s'hagi definit plugin de firma en servidor

    Long[] configuracions = { perfilDeFirma.getConfiguracioDeFirma1ID(),
        perfilDeFirma.getConfiguracioDeFirma2ID(), perfilDeFirma.getConfiguracioDeFirma3ID() };

    List<Long> configuracionsList = new ArrayList<Long>();
    for (Long cfg : configuracions) {
      if (cfg != null) {
        configuracionsList.add(cfg);
      }
    }
/*
    List<UsuariAplicacioConfiguracio> configs;
    configs = select(UsuariAplicacioConfiguracioFields.USUARIAPLICACIOCONFIGID
        .in(configuracionsList));

    for (UsuariAplicacioConfiguracio config : configs) {

      // Check si es té permis
      checkTePermisPerUsDeFirma(usuariAplicacioID, codiPerfil, usFirma, config);

      // Check si es firma en servidor que tengui definit el Plugin
      if ((usFirma == ConstantsV2.US_FIRMA_CONF_APP_APIFIRMASIMPLESERVIDOR)
          || (usFirma == ConstantsV2.US_FIRMA_CONF_APP_PASSARELAFIRMASERVIDOR)) {

        Long pluginId = config.getPluginFirmaServidorID();

        if (pluginId == null) {
          // XYZ ZZZ Traduir
          throw new I18NException("genapp.comodi", "La configuració de firma "
              + config.getNom() + " enllaçada amb el perfil de firma amb codi " + codiPerfil
              + " i associat a l'usuari aplicació " + usuariAplicacioID
              + " no té definit el plugin de firma en servidor. "
              + "Consulti amb l'Administrador.");
        }
      }
    }
*/
    return perfilDeFirma;
  }

  protected PerfilDeFirma getPerfilDeFirmaByCodi(String codiPerfil) throws I18NException {
    PerfilDeFirma perfilDeFirma;
    {
      List<PerfilDeFirma> list = perfilDeFirmaEjb.select(PerfilDeFirmaFields.CODI
          .equal(codiPerfil));

      if (list == null || list.size() == 0) {
        // XYZ ZZZ TRA Traduir
        throw new I18NException("genapp.comodi", "El codi de perfil " + codiPerfil
            + " no existeix. Consulti amb l'Administrador.");
      }

      perfilDeFirma = list.get(0);
    }
    return perfilDeFirma;
  }

  /**
   * Els usuaris aplicacio de passarela NOMES poden tenir UN SOL PERFIL
   * 
   * @param usuariAplicacioID
   * @return
   * @throws I18NException
   */
  @Override
  @RolesAllowed({ "PFI_ADMIN", "PFI_USER" })
  public UsuariAplicacioConfiguracio getConfiguracioUsuariAplicacioPerPassarela(
      final String usuariAplicacioID, final boolean esFirmaEnServidor) throws I18NException {

    final Field<Boolean> usFirmaPassarela;

    final int usFirma;
    if (esFirmaEnServidor) {
      usFirma = ConstantsV2.US_FIRMA_CONF_APP_PASSARELAFIRMASERVIDOR;
      usFirmaPassarela = new PerfilsPerUsuariAplicacioQueryPath().PERFILDEFIRMA()
          .CONFIGURACIODEFIRMA1().USENFIRMAPASSARELASERVIDOR();
    } else {
      usFirma = ConstantsV2.US_FIRMA_CONF_APP_PASSARELAFIRMAWEB;
      usFirmaPassarela = new PerfilsPerUsuariAplicacioQueryPath().PERFILDEFIRMA()
          .CONFIGURACIODEFIRMA1().USENFIRMAPASSARELAWEB();
    }

    // Hauria de retornar 1
    List<String> codisPerfil = perfilsPerUsuariAplicacioEjb.executeQuery(
        new PerfilsPerUsuariAplicacioQueryPath().PERFILDEFIRMA().CODI(), Where.AND(
            PerfilsPerUsuariAplicacioFields.USUARIAPLICACIOID.equal(usuariAplicacioID),
            usFirmaPassarela.equal(true)));

    if (codisPerfil == null || codisPerfil.size() != 1) {
      // XYZ ZZZ TRA
      throw new I18NException("genapp.comodi", "Els usuaris aplicació, com "
          + usuariAplicacioID + ", que es fan servir per passarel·la han de tenir un"
          + " i solament un perfil de firma assignat, i aquest o no en te cap o en té múltiples.");
    }
    
    String codiPerfil = codisPerfil.get(0);
    
    PerfilDeFirma perfilDeFirma = getPerfilDeFirmaByCodi(codiPerfil);
    
    if (perfilDeFirma.getCondicio() != null || perfilDeFirma.getConfiguracioDeFirma2ID() != null 
        || perfilDeFirma.getConfiguracioDeFirma3ID() != null) {
      // XYZ ZZZ TRA
      throw new I18NException("genapp.comodi", "El perfil amb codi " + codiPerfil 
          + " que esta assignat a l´usuari aplicació " + usuariAplicacioID 
          + ", que es fa servir per passarel·la, no pot tenir condició, ni configuració de firma 2"
          + " ni configuracio de firma 3");
    }

    UsuariAplicacioConfiguracio config = findByPrimaryKey(perfilDeFirma.getConfiguracioDeFirma1ID());
    
    checkTePermisPerUsDeFirma(usuariAplicacioID, codiPerfil, usFirma, config);
    
    return config;
  }
  
  
  @Override
  @RolesAllowed({ "PFI_ADMIN", "PFI_USER" })
  public UsuariAplicacioConfiguracio getConfiguracioUsuariAplicacioPerUpgrade(
      String usuariAplicacioID, PerfilDeFirma perfilDeFirma, FirmaSimpleUpgradeRequest 
      firmaSimpleUpgradeRequest) throws I18NException {

    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("firmaSimpleUpgradeRequest", firmaSimpleUpgradeRequest);

    UsuariAplicacioConfiguracio config = avaluarCondicio(usuariAplicacioID, 
        perfilDeFirma, ConstantsV2.US_FIRMA_CONF_APP_APIFIRMASIMPLESERVIDOR,
        firmaSimpleUpgradeRequest.getLanguageUI(), parameters);

   
    Integer upgradeID = config.getUpgradeSignFormat();

    if (upgradeID == null) {
      // XYZ ZZZ Traduir
      throw new I18NException("genapp.codi", "L´usuari aplicació "
          + usuariAplicacioID
          + " no té definida configuració d´Extensió de Firma (Perfil de Firma: " 
          + perfilDeFirma.getCodi() + ", Configuració de Firma: " + config.getNom() + ")");
    }

    return config;

  }
  
  
  
  @Override
  @RolesAllowed({ "PFI_ADMIN", "PFI_USER" })
  public UsuariAplicacioConfiguracio getConfiguracioFirmaPerApiFirmaSimpleEnServidor(
      String usuariAplicacioID, PerfilDeFirma perfilDeFirma, 
      FirmaSimpleSignDocumentRequest firmaSimpleSignDocumentRequest) throws I18NException {

      
      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("firmaSimpleSignDocumentRequest", firmaSimpleSignDocumentRequest);
      
      UsuariAplicacioConfiguracio config = avaluarCondicio(usuariAplicacioID, perfilDeFirma,
          ConstantsV2.US_FIRMA_CONF_APP_APIFIRMASIMPLESERVIDOR,
          firmaSimpleSignDocumentRequest.getCommonInfo().getLanguageUI(), parameters);

      return config;
  
  }

  
  
  public UsuariAplicacioConfiguracio getConfiguracioFirmaPerApiFirmaSimpleWeb(
      String usuariAplicacioID, PerfilDeFirma perfilDeFirma,
      FirmaSimpleSignDocumentRequest firmaSimpleSignDocumentRequest) throws I18NException {

      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("firmaSimpleSignDocumentRequest", firmaSimpleSignDocumentRequest);
      
      UsuariAplicacioConfiguracio config = avaluarCondicio(usuariAplicacioID, perfilDeFirma,            
          ConstantsV2.US_FIRMA_CONF_APP_APIFIRMASIMPLEWEB,
          firmaSimpleSignDocumentRequest.getCommonInfo().getLanguageUI(),
          parameters);

      return config;

  }

  
  /**
   * 
   * @param perfilDeFirma
   * @param parameters
   * @return
   * @throws I18NException
   */
  protected UsuariAplicacioConfiguracio avaluarCondicio(String usuariAplicacioID,
      PerfilDeFirma perfilDeFirma, int usFirma, String lang, Map<String, Object> parameters)
      throws I18NException {

    parameters.put("usFirma", usFirma);
    parameters.put("lang", lang);

    String condicio = perfilDeFirma.getCondicio();
    Long configID;

    if (condicio == null || condicio.trim().length() == 0) {
      configID = perfilDeFirma.getConfiguracioDeFirma1ID();
    } else {
      String errorOrConfig;
      try {
        errorOrConfig = TemplateEngine.processExpressionLanguage(condicio, parameters);
      } catch (IOException e) {
        // XYZ ZZZ TRAD
        throw new I18NException(e, "genapp.comodi", new I18NArgumentString(
            "Error processant condició del perfil " + perfilDeFirma.getCodi() + ": "
                + e.getMessage()));
      }

      long configPos;
      try {
        configPos = Long.parseLong(errorOrConfig.trim());
      } catch (NumberFormatException e) {
        // Signnifica que la condicio ha retornat un error
        // XYZ ZZZ TRAD
        throw new I18NException("genapp.comodi", new I18NArgumentString(
            "El processat de la condició del perfil " + perfilDeFirma.getCodi()
                + " ha retornat un missatge d´error: " + errorOrConfig));
      }

      if (configPos < 1 || configPos > 5) {
        // XYZ ZZZ TRAD
        throw new I18NException("genapp.comodi", new I18NArgumentString(
            "El processat de la condició del perfil " + perfilDeFirma.getCodi()
                + " ha retornat id de Configuració major que 3 o menor que 1: " + configPos));
      }

      switch ((int) configPos) {

        case 1:
          configID = perfilDeFirma.getConfiguracioDeFirma1ID();
        break;

        case 2:
          configID = perfilDeFirma.getConfiguracioDeFirma2ID();
        break;

        case 3:
          configID = perfilDeFirma.getConfiguracioDeFirma3ID();
        break;
        
        case 4:
          configID = perfilDeFirma.getConfiguracioDeFirma4ID();
        break;

        case 5:
          configID = perfilDeFirma.getConfiguracioDeFirma5ID();
        break;

        default:
          // XYZ ZZZ TRAD
          throw new I18NException("genapp.comodi", "El processat de la condició del perfil "
              + perfilDeFirma.getCodi()
              + " ha retornat id de Configuració major que 5 o menor que 1: " + configPos);
      }

      if (configID == null) {
        // XYZ ZZZ TRAD
        throw new I18NException("genapp.comodi", "La configuració " + configPos
            + " del perfil " + perfilDeFirma.getCodi() + " no està definida.");
      }
    }

    UsuariAplicacioConfiguracio config = findByPrimaryKey(configID);

    checkTePermisPerUsDeFirma(usuariAplicacioID, perfilDeFirma.getCodi(), usFirma, config);

    return config;
  }

}
