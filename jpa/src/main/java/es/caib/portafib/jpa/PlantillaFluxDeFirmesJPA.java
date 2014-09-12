
package es.caib.portafib.jpa;
import es.caib.portafib.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.HashSet;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import java.util.Set;
import org.hibernate.annotations.Index;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import org.hibernate.annotations.ForeignKey;


@Entity
@Table(name = "pfi_plantillafluxdefirmes" )
@SequenceGenerator(name="PORTAFIB_SEQ", sequenceName="pfi_portafib_seq")
public class PlantillaFluxDeFirmesJPA implements PlantillaFluxDeFirmes {



private static final long serialVersionUID = 139304578L;

	@Id
	@Index(name="pfi_plantillafluxdefirmes_pk_i")
	@Column(name="fluxdefirmesid",nullable = false,length = 19)
	long fluxDeFirmesID;

	@Column(name="descripcio",nullable = false,length = 1000)
	java.lang.String descripcio;

	@Index(name="pfi_plantiflfi_usrentiid_fk_i")
	@Column(name="usuarientitatid",length = 101)
	java.lang.String usuariEntitatID;

	@Index(name="pfi_plantiflfi_usrappid_fk_i")
	@Column(name="usuariaplicacioid",length = 101)
	java.lang.String usuariAplicacioID;

  /** true: compartir amb entitat, false:no compartir amb ningú: NULL: revisar permisos grup i usuaris */
	@Column(name="compartir",length = 1)
	java.lang.Boolean compartir;



  /** Constructor Buit */
  public PlantillaFluxDeFirmesJPA() {
  }

  /** Constructor amb tots els camps  */
  public PlantillaFluxDeFirmesJPA(long fluxDeFirmesID , java.lang.String descripcio , java.lang.String usuariEntitatID , java.lang.String usuariAplicacioID , java.lang.Boolean compartir) {
    this.fluxDeFirmesID=fluxDeFirmesID;
    this.descripcio=descripcio;
    this.usuariEntitatID=usuariEntitatID;
    this.usuariAplicacioID=usuariAplicacioID;
    this.compartir=compartir;
}
  /** Constructor dels valors Not Null */
  public PlantillaFluxDeFirmesJPA(long fluxDeFirmesID , java.lang.String descripcio) {
    this.fluxDeFirmesID=fluxDeFirmesID;
    this.descripcio=descripcio;
}
  public PlantillaFluxDeFirmesJPA(PlantillaFluxDeFirmes __bean) {
    this.setFluxDeFirmesID(__bean.getFluxDeFirmesID());
    this.setDescripcio(__bean.getDescripcio());
    this.setUsuariEntitatID(__bean.getUsuariEntitatID());
    this.setUsuariAplicacioID(__bean.getUsuariAplicacioID());
    this.setCompartir(__bean.getCompartir());
	}

  public static PlantillaFluxDeFirmesJPA toJPA(PlantillaFluxDeFirmes __bean) {
    if (__bean == null) { return null;}
    PlantillaFluxDeFirmesJPA __tmp = new PlantillaFluxDeFirmesJPA();
    __tmp.setFluxDeFirmesID(__bean.getFluxDeFirmesID());
    __tmp.setDescripcio(__bean.getDescripcio());
    __tmp.setUsuariEntitatID(__bean.getUsuariEntitatID());
    __tmp.setUsuariAplicacioID(__bean.getUsuariAplicacioID());
    __tmp.setCompartir(__bean.getCompartir());
		return __tmp;
	}

	public long getFluxDeFirmesID() {
		return(fluxDeFirmesID);
	};
	public void setFluxDeFirmesID(long _fluxDeFirmesID_) {
		this.fluxDeFirmesID = _fluxDeFirmesID_;
	};

	public java.lang.String getDescripcio() {
		return(descripcio);
	};
	public void setDescripcio(java.lang.String _descripcio_) {
		this.descripcio = _descripcio_;
	};

	public java.lang.String getUsuariEntitatID() {
		return(usuariEntitatID);
	};
	public void setUsuariEntitatID(java.lang.String _usuariEntitatID_) {
		this.usuariEntitatID = _usuariEntitatID_;
	};

	public java.lang.String getUsuariAplicacioID() {
		return(usuariAplicacioID);
	};
	public void setUsuariAplicacioID(java.lang.String _usuariAplicacioID_) {
		this.usuariAplicacioID = _usuariAplicacioID_;
	};

	public java.lang.Boolean getCompartir() {
		return(compartir);
	};
	public void setCompartir(java.lang.Boolean _compartir_) {
		this.compartir = _compartir_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof PlantillaFluxDeFirmes) {
      PlantillaFluxDeFirmes __instance = (PlantillaFluxDeFirmes)__obj;
      __result = true;
      __result = __result && (this.getFluxDeFirmesID() == __instance.getFluxDeFirmesID()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// EXP  Field:fluxdefirmesid | Table: pfi_permisgrupplantilla | Type: 0  

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "plantillaFluxDeFirmes")
	private Set<PermisGrupPlantillaJPA> permisGrupPlantillas = new HashSet<PermisGrupPlantillaJPA>(0);
	public  Set<PermisGrupPlantillaJPA> getPermisGrupPlantillas() {
    return this.permisGrupPlantillas;
  }

	public void setPermisGrupPlantillas(Set<PermisGrupPlantillaJPA> permisGrupPlantillas) {
	  this.permisGrupPlantillas = permisGrupPlantillas;
	}


// EXP  Field:fluxdefirmesid | Table: pfi_permisusuariplantilla | Type: 0  

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "plantillaFluxDeFirmes")
	private Set<PermisUsuariPlantillaJPA> permisUsuariPlantillas = new HashSet<PermisUsuariPlantillaJPA>(0);
	public  Set<PermisUsuariPlantillaJPA> getPermisUsuariPlantillas() {
    return this.permisUsuariPlantillas;
  }

	public void setPermisUsuariPlantillas(Set<PermisUsuariPlantillaJPA> permisUsuariPlantillas) {
	  this.permisUsuariPlantillas = permisUsuariPlantillas;
	}


// IMP Field:fluxdefirmesid | Table: pfi_fluxdefirmes | Type: 1  

	@OneToOne(fetch = FetchType.LAZY)
	@ForeignKey(name="pfi_plantiflfi_fluxfirmes_fk")
	@JoinColumn(name = "fluxdefirmesid", nullable = false, insertable=false, updatable=false)
	private FluxDeFirmesJPA fluxDeFirmes;

	public FluxDeFirmesJPA getFluxDeFirmes() {
    return this.fluxDeFirmes;
  }

	public  void setFluxDeFirmes(FluxDeFirmesJPA fluxDeFirmes) {
    this.fluxDeFirmes = fluxDeFirmes;
  }

// IMP Field:usuarientitatid | Table: pfi_usuarientitat | Type: 1  

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name="pfi_plantiflfi_usrentitat_fk")
	@JoinColumn(name = "usuarientitatid", referencedColumnName ="usuariEntitatID", nullable = true, insertable=false, updatable=false)
	private UsuariEntitatJPA usuariEntitat;

	public UsuariEntitatJPA getUsuariEntitat() {
    return this.usuariEntitat;
  }

	public  void setUsuariEntitat(UsuariEntitatJPA usuariEntitat) {
    this.usuariEntitat = usuariEntitat;
  }

// IMP Field:usuariaplicacioid | Table: pfi_usuariaplicacio | Type: 1  

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name="pfi_plantiflfi_usrapp_fk")
	@JoinColumn(name = "usuariaplicacioid", referencedColumnName ="usuariAplicacioID", nullable = true, insertable=false, updatable=false)
	private UsuariAplicacioJPA usuariAplicacio;

	public UsuariAplicacioJPA getUsuariAplicacio() {
    return this.usuariAplicacio;
  }

	public  void setUsuariAplicacio(UsuariAplicacioJPA usuariAplicacio) {
    this.usuariAplicacio = usuariAplicacio;
  }



}