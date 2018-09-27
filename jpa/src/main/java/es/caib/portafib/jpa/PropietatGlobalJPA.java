
package es.caib.portafib.jpa;
import es.caib.portafib.model.entity.*;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import org.hibernate.annotations.Index;
import javax.persistence.UniqueConstraint;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.ForeignKey;
import javax.persistence.GeneratedValue;


@Entity
@Table(name = "pfi_propietatglobal"  , uniqueConstraints = {
            @UniqueConstraint( columnNames={"clau","entitatid"}) } )
@SequenceGenerator(name="PORTAFIB_SEQ", sequenceName="pfi_portafib_seq", allocationSize=1)
@javax.xml.bind.annotation.XmlRootElement
public class PropietatGlobalJPA implements PropietatGlobal {



private static final long serialVersionUID = 1545722544L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PORTAFIB_SEQ")
	@Index(name="pfi_propietatglobal_pk_i")
	@Column(name="propietatglobalid",nullable = false,length = 19)
	long propietatGlobalID;

	@Column(name="clau",nullable = false,length = 255)
	java.lang.String clau;

	@Column(name="valor",length = 255)
	java.lang.String valor;

	@Index(name="pfi_propietat_entitatid_fk_i")
	@Column(name="entitatid",length = 50)
	java.lang.String entitatID;

	@Column(name="descripcio",length = 1000)
	java.lang.String descripcio;



  /** Constructor Buit */
  public PropietatGlobalJPA() {
  }

  /** Constructor amb tots els camps  */
  public PropietatGlobalJPA(long propietatGlobalID , java.lang.String clau , java.lang.String valor , java.lang.String entitatID , java.lang.String descripcio) {
    this.propietatGlobalID=propietatGlobalID;
    this.clau=clau;
    this.valor=valor;
    this.entitatID=entitatID;
    this.descripcio=descripcio;
}
  /** Constructor sense valors autoincrementals */
  public PropietatGlobalJPA(java.lang.String clau , java.lang.String valor , java.lang.String entitatID , java.lang.String descripcio) {
    this.clau=clau;
    this.valor=valor;
    this.entitatID=entitatID;
    this.descripcio=descripcio;
}
  /** Constructor dels valors Not Null */
  public PropietatGlobalJPA(long propietatGlobalID , java.lang.String clau) {
    this.propietatGlobalID=propietatGlobalID;
    this.clau=clau;
}
  public PropietatGlobalJPA(PropietatGlobal __bean) {
    this.setPropietatGlobalID(__bean.getPropietatGlobalID());
    this.setClau(__bean.getClau());
    this.setValor(__bean.getValor());
    this.setEntitatID(__bean.getEntitatID());
    this.setDescripcio(__bean.getDescripcio());
	}

	public long getPropietatGlobalID() {
		return(propietatGlobalID);
	};
	public void setPropietatGlobalID(long _propietatGlobalID_) {
		this.propietatGlobalID = _propietatGlobalID_;
	};

	public java.lang.String getClau() {
		return(clau);
	};
	public void setClau(java.lang.String _clau_) {
		this.clau = _clau_;
	};

	public java.lang.String getValor() {
		return(valor);
	};
	public void setValor(java.lang.String _valor_) {
		this.valor = _valor_;
	};

	public java.lang.String getEntitatID() {
		return(entitatID);
	};
	public void setEntitatID(java.lang.String _entitatID_) {
		this.entitatID = _entitatID_;
	};

	public java.lang.String getDescripcio() {
		return(descripcio);
	};
	public void setDescripcio(java.lang.String _descripcio_) {
		this.descripcio = _descripcio_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof PropietatGlobal) {
      PropietatGlobal __instance = (PropietatGlobal)__obj;
      __result = true;
      __result = __result && (this.getPropietatGlobalID() == __instance.getPropietatGlobalID()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// IMP Field:entitatid | Table: pfi_entitat | Type: 1  

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name="pfi_propietat_entitat_fk")
	@JoinColumn(name = "entitatid", referencedColumnName ="entitatID", nullable = true, insertable=false, updatable=false)
	private EntitatJPA entitat;

	public EntitatJPA getEntitat() {
    return this.entitat;
  }

	public  void setEntitat(EntitatJPA entitat) {
    this.entitat = entitat;
  }


 // ---------------  STATIC METHODS ------------------
  public static PropietatGlobalJPA toJPA(PropietatGlobal __bean) {
    if (__bean == null) { return null;}
    PropietatGlobalJPA __tmp = new PropietatGlobalJPA();
    __tmp.setPropietatGlobalID(__bean.getPropietatGlobalID());
    __tmp.setClau(__bean.getClau());
    __tmp.setValor(__bean.getValor());
    __tmp.setEntitatID(__bean.getEntitatID());
    __tmp.setDescripcio(__bean.getDescripcio());
		return __tmp;
	}


  public static PropietatGlobalJPA copyJPA(PropietatGlobalJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<PropietatGlobalJPA> copyJPA(java.util.Set<PropietatGlobalJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<PropietatGlobalJPA> __tmpSet = (java.util.Set<PropietatGlobalJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<PropietatGlobalJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (PropietatGlobalJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static PropietatGlobalJPA copyJPA(PropietatGlobalJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    PropietatGlobalJPA __tmp = (PropietatGlobalJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    // Copia de beans complexes (IMP)
    if(!"EntitatJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.entitat) || org.hibernate.Hibernate.isInitialized(__jpa.getEntitat()) ) ) {
      __tmp.setEntitat(EntitatJPA.copyJPA(__jpa.getEntitat(), __alreadyCopied,"PropietatGlobalJPA"));
    }

    return __tmp;
  }




}