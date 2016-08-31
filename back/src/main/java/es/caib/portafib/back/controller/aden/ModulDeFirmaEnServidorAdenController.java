package es.caib.portafib.back.controller.aden;


import javax.ejb.EJB;

import org.fundaciobit.plugins.signatureserver.api.ISignatureServerPlugin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.caib.portafib.back.controller.AbstractPluginAdenController;
import es.caib.portafib.back.form.webdb.PluginFilterForm;
import es.caib.portafib.back.form.webdb.PluginForm;
import es.caib.portafib.logic.AbstractPluginLogicaLocal;
import es.caib.portafib.logic.ModulDeFirmaServidorLogicaLocal;
import es.caib.portafib.utils.Constants;

/**
 * 
 * @author anadal
 *
 */
@Controller
@RequestMapping(value = "/aden/moduldefirmaenservidor")
@SessionAttributes(types = { PluginForm.class, PluginFilterForm.class })
public class ModulDeFirmaEnServidorAdenController extends AbstractPluginAdenController<ISignatureServerPlugin> {
  
  @EJB(mappedName = ModulDeFirmaServidorLogicaLocal.JNDI_NAME)
  protected ModulDeFirmaServidorLogicaLocal modulDeFirmaEnServidorEjb;
  
  
  @Override
  public String getTileForm() {
    return "modulDeFirmaFormAden";
  }

  @Override
  public String getTileList() {
    return "modulDeFirmaListAden";
  }

  @Override
  public AbstractPluginLogicaLocal<ISignatureServerPlugin> getPluginEjb() {
    return modulDeFirmaEnServidorEjb;
  }

  @Override
  public String getCrearTranslationCode() {
    return "moduldefirmaenservidor.crear"; 
  }

  @Override
  public int getTipusDePlugin() {
    return Constants.TIPUS_PLUGIN_MODULDEFIRMASERVIDOR;
  }

  @Override
  public String getCodeName() {
    return "moduldefirmaenservidor";
  }

  @Override
  public String getTitolModalCode() {
    return "moduldefirmaenservidor.titolmodal";
  }

  
}
