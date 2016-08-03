
package es.caib.portafib.ejb;

import javax.ejb.Stateless;
import org.jboss.ejb3.annotation.SecurityDomain;
import javax.annotation.security.RolesAllowed;
import org.fundaciobit.genapp.common.i18n.I18NException;
import es.caib.portafib.model.entity.EstatDeFirma;
import es.caib.portafib.jpa.EstatDeFirmaJPA;
import es.caib.portafib.jpa.EstatDeFirmaJPAManager;

@Stateless(name = "EstatDeFirmaEJB")
@SecurityDomain("seycon")
public class EstatDeFirmaEJB extends EstatDeFirmaJPAManager implements EstatDeFirmaLocal {

  @Override
	@RolesAllowed({"PFI_ADMIN","PFI_USER"})
	public void delete(EstatDeFirma instance) {
		super.delete(instance);
	}

  @Override
	@RolesAllowed({"PFI_ADMIN","PFI_USER"})
	public EstatDeFirma create(EstatDeFirma instance) throws I18NException {
		return super.create(instance);
	}

  @Override
	@RolesAllowed({"PFI_ADMIN","PFI_USER"})
	public EstatDeFirma update(EstatDeFirma instance) throws I18NException {
		 return super.update(instance);
	}

  @Override
	@RolesAllowed({"PFI_ADMIN","PFI_USER"})
  public EstatDeFirmaJPA findByPrimaryKey(Long _ID_) {
    return (EstatDeFirmaJPA)super.findByPrimaryKey(_ID_);
  }

}