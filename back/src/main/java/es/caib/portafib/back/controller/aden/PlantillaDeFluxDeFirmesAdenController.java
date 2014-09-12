package es.caib.portafib.back.controller.aden;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.caib.portafib.back.controller.soli.PlantillaDeFluxDeFirmesController;
import es.caib.portafib.back.form.PlantillaDeFluxDeFirmesFilterForm;
import es.caib.portafib.back.form.PlantillaDeFluxDeFirmesForm;
import es.caib.portafib.back.form.webdb.FluxDeFirmesFilterForm;
import es.caib.portafib.back.form.webdb.FluxDeFirmesForm;

/**
 * 
 * @author anadal
 *
 */
@Controller
@RequestMapping(value = "/aden/plantilla")
@SessionAttributes(types = { PlantillaDeFluxDeFirmesFilterForm.class,
    PlantillaDeFluxDeFirmesForm.class, FluxDeFirmesForm.class,
    FluxDeFirmesFilterForm.class })
public class PlantillaDeFluxDeFirmesAdenController 
     extends PlantillaDeFluxDeFirmesController {

  @Override
  public String getTileForm() {
    return "PlantillaDeFluxDeFirmesFormAden";
  }

  @Override
  public String getTileList() {
    return "PlantillaDeFluxDeFirmesListAden";
  }

  @Override
  public String getSessionAttributeFilterForm() {
    return "PlantillaFluxDeFirmes_aden";
  }
  
  @Override
  public boolean isUsuariEntitat() {
    return false;
  }

  
}