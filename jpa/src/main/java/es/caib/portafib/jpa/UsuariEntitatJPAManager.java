
package es.caib.portafib.jpa;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import es.caib.portafib.model.entity.*;
import es.caib.portafib.model.fields.*;
import es.caib.portafib.model.dao.*;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.events.ModificationManager;


public class UsuariEntitatJPAManager
		 extends AbstractPortaFIBJPAManager<UsuariEntitat, String >
		 implements IUsuariEntitatManager, UsuariEntitatFields {




  private static final long serialVersionUID = 795835066L;

	 public static final TableName<UsuariEntitat> _TABLENAME =  new TableName<UsuariEntitat>("UsuariEntitatJPA");



  static final ModificationManager<UsuariEntitat> __eventManager = new ModificationManager<UsuariEntitat>();


  @PersistenceContext
  protected EntityManager __em;
  public UsuariEntitatJPAManager() {
  }
  protected UsuariEntitatJPAManager(EntityManager __em) {
    this.__em = __em;
  }


  protected EntityManager getEntityManager() {
    return this.__em;
  }
	public Class<?> getJPAClass() {
		return UsuariEntitatJPA. class;
	}



	public ModificationManager<UsuariEntitat> getEventManager() {
	return __eventManager;
	}


	public TableName<UsuariEntitat> getTableName() {
		return _TABLENAME;
	}


	@Override
	protected String getTableNameVariable() {
		return _TABLE_MODEL;
	}


	public UsuariEntitat[] listToArray(List<UsuariEntitat> list)  {
		if(list == null) { return null; };
		return list.toArray(new UsuariEntitat[list.size()]);
	};

	public synchronized UsuariEntitat create( java.lang.String _usuariEntitatID_, java.lang.String _carrec_, java.lang.String _usuariPersonaID_, java.lang.String _entitatID_, boolean _actiu_, java.lang.String _email_, java.lang.Long _logoSegellID_, boolean _predeterminat_, boolean _rebreTotsElsAvisos_, java.lang.Boolean _potCustodiar_) throws I18NException {
		UsuariEntitatJPA __bean =  new UsuariEntitatJPA(_usuariEntitatID_,_carrec_,_usuariPersonaID_,_entitatID_,_actiu_,_email_,_logoSegellID_,_predeterminat_,_rebreTotsElsAvisos_,_potCustodiar_);
		return create(__bean);
	}



 public void delete(java.lang.String _usuariEntitatID_) {
   delete(findByPrimaryKey(_usuariEntitatID_));
 }




	public UsuariEntitat findByPrimaryKey(java.lang.String _usuariEntitatID_) {
	  return __em.find(UsuariEntitatJPA.class, _usuariEntitatID_);  
	}
	@Override
	protected UsuariEntitat getJPAInstance(UsuariEntitat __bean) {
		return convertToJPA(__bean);
	}


	public static UsuariEntitatJPA convertToJPA(UsuariEntitat __bean) {
	  if (__bean == null) {
	    return null;
	  }
	  if(__bean instanceof UsuariEntitatJPA) {
	    return (UsuariEntitatJPA)__bean;
	  }
	  
	  return UsuariEntitatJPA.toJPA(__bean);
	}


}