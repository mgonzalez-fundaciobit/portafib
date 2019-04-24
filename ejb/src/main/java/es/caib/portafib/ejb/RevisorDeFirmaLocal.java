
package es.caib.portafib.ejb;

import javax.ejb.Local;

import es.caib.portafib.jpa.RevisorDeFirmaJPA;
import es.caib.portafib.model.dao.IRevisorDeFirmaManager;

@Local
public interface RevisorDeFirmaLocal extends IRevisorDeFirmaManager {

 public static final String JNDI_NAME = "portafib/RevisorDeFirmaEJB/local";
  public RevisorDeFirmaJPA findByPrimaryKey(Long _ID_);
}