
package es.caib.portafib.back.form.webdb;

import java.util.List;
import javax.ejb.EJB;
import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.Where;

import es.caib.portafib.ejb.UsuariEntitatFavoritLocal;
import org.fundaciobit.genapp.common.i18n.I18NException;
import es.caib.portafib.model.fields.UsuariEntitatFavoritFields;
import org.fundaciobit.genapp.common.web.controller.RefListBase;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class UsuariEntitatFavoritRefList extends RefListBase
    implements UsuariEntitatFavoritFields {

  @EJB(mappedName = UsuariEntitatFavoritLocal.JNDI_NAME)
  private UsuariEntitatFavoritLocal usuariEntitatFavoritEjb;

  public UsuariEntitatFavoritRefList(UsuariEntitatFavoritRefList __clone) {
    super(__clone);
    this.usuariEntitatFavoritEjb = __clone.usuariEntitatFavoritEjb;
  }
  public UsuariEntitatFavoritRefList() {
    setSelects(new Select<?>[] { ORIGENID.select, FAVORITID.select });
  }
  public List<StringKeyValue> getReferenceList(Field<?> keyField, Where where, OrderBy ... orderBy) throws I18NException {
    Select<StringKeyValue> select =  new org.fundaciobit.genapp.common.query.SelectMultipleStringKeyValue(keyField.select, getSeparator(), getSelects());
    List<StringKeyValue> list = usuariEntitatFavoritEjb.executeQuery(select, where, (orderBy==null || orderBy.length == 0) ? getOrderBy() : orderBy);
    return list;
  }
}