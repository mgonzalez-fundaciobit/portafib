package es.caib.portafib.back.controller.common.destinatariextern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.caib.portafib.back.controller.soli.PlantillaDeFluxDeFirmesController;
import es.caib.portafib.back.form.PlantillaDeFluxDeFirmesForm;
import es.caib.portafib.back.form.SeleccioUsuariForm;
import es.caib.portafib.back.form.webdb.FluxDeFirmesFilterForm;
import es.caib.portafib.back.form.webdb.FluxDeFirmesForm;

/**
 * 
 * @author anadal
 *
 */
@Controller
@RequestMapping(value = "/common/plantilla")
@SessionAttributes(types = {  SeleccioUsuariForm.class, PlantillaDeFluxDeFirmesForm.class,
    FluxDeFirmesForm.class,  FluxDeFirmesFilterForm.class })
public class DestinatariExternPlantillaDeFluxDeFirmesDestController extends PlantillaDeFluxDeFirmesController {

  @Override
  public boolean isActiveList() {
    return true;
  }

  @Override
  public boolean isActiveFormNew() {
    return false;
  }
  
  @Override
  public boolean isActiveFormView() {
    return true;
  }

  @Override
  public boolean isActiveFormEdit() {
    return false;
  }

  @Override
  public boolean isActiveDelete() {
    return false;
  }
  
  
  @Override
  public String getSessionAttributeFilterForm() {
    return this.getClass().getName();
  }
  
  /** No té importància ja que està està en readonly */
  @Override
  public boolean isEditingPlantilla() {
    return false;
  }
  
}
