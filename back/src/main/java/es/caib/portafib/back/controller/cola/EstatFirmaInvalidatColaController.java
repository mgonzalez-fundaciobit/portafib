package es.caib.portafib.back.controller.cola;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.caib.portafib.back.form.webdb.EstatDeFirmaFilterForm;
import es.caib.portafib.utils.ConstantsV2;

/**
 * 
 * @author anadal
 *
 */
@Controller
@RequestMapping(value = ConstantsV2.CONTEXT_COLA_ESTATFIRMA_INVALIDAT )
@SessionAttributes(types = { EstatDeFirmaFilterForm.class })
public class EstatFirmaInvalidatColaController extends EstatFirmaAbstractColaController {

  @Override
  public int getFilterType() {
    return FILTRAR_PER_NOACCEPTAT; // == Invalidat
  }


}
