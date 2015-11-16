
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
@Table(name = "pfi_modulfirmapertipusdoc"  , uniqueConstraints = {
            @UniqueConstraint( columnNames={"tipusdocumentid","pluginid"}) } )
@SequenceGenerator(name="PORTAFIB_SEQ", sequenceName="pfi_portafib_seq", allocationSize=1)
@javax.xml.bind.annotation.XmlRootElement
public class ModulDeFirmaPerTipusDeDocumentJPA implements ModulDeFirmaPerTipusDeDocument {



private static final long serialVersionUID = 2145428058L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PORTAFIB_SEQ")
	@Index(name="pfi_modulfirmapertipusdoc_pk_i")
	@Column(name="id",nullable = false,length = 19)
	long ID;

	@Index(name="pfi_mofitido_tipusdoc_fk_i")
	@Column(name="tipusdocumentid",nullable = false,length = 19)
	long tipusDocumentID;

	@Index(name="pfi_mofitido_modfirma_fk_i")
	@Column(name="pluginid",nullable = false,length = 19)
	long pluginID;

	@Column(name="nom",nullable = false,length = 100)
	java.lang.String nom;



  /** Constructor Buit */
  public ModulDeFirmaPerTipusDeDocumentJPA() {
  }

  /** Constructor amb tots els camps  */
  public ModulDeFirmaPerTipusDeDocumentJPA(long ID , long tipusDocumentID , long pluginID , java.lang.String nom) {
    this.ID=ID;
    this.tipusDocumentID=tipusDocumentID;
    this.pluginID=pluginID;
    this.nom=nom;
}
  /** Constructor sense valors autoincrementals */
  public ModulDeFirmaPerTipusDeDocumentJPA(long tipusDocumentID , long pluginID , java.lang.String nom) {
    this.tipusDocumentID=tipusDocumentID;
    this.pluginID=pluginID;
    this.nom=nom;
}
  public ModulDeFirmaPerTipusDeDocumentJPA(ModulDeFirmaPerTipusDeDocument __bean) {
    this.setID(__bean.getID());
    this.setTipusDocumentID(__bean.getTipusDocumentID());
    this.setPluginID(__bean.getPluginID());
    this.setNom(__bean.getNom());
	}

	public long getID() {
		return(ID);
	};
	public void setID(long _ID_) {
		this.ID = _ID_;
	};

	public long getTipusDocumentID() {
		return(tipusDocumentID);
	};
	public void setTipusDocumentID(long _tipusDocumentID_) {
		this.tipusDocumentID = _tipusDocumentID_;
	};

	public long getPluginID() {
		return(pluginID);
	};
	public void setPluginID(long _pluginID_) {
		this.pluginID = _pluginID_;
	};

	public java.lang.String getNom() {
		return(nom);
	};
	public void setNom(java.lang.String _nom_) {
		this.nom = _nom_;
	};



  @Override
  public boolean equals(Object __obj) {
  boolean __result;
    if (__obj != null && __obj instanceof ModulDeFirmaPerTipusDeDocument) {
      ModulDeFirmaPerTipusDeDocument __instance = (ModulDeFirmaPerTipusDeDocument)__obj;
      __result = true;
      __result = __result && (this.getID() == __instance.getID()) ;
    } else {
      __result = false;
    }
    return __result;
  }

// IMP Field:tipusdocumentid | Table: pfi_tipusdocument | Type: 1  

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name="pfi_mofitido_tipusdoc_fk")
	@JoinColumn(name = "tipusdocumentid", referencedColumnName ="tipusDocumentID", nullable = false, insertable=false, updatable=false)
	private TipusDocumentJPA tipusDocument;

	public TipusDocumentJPA getTipusDocument() {
    return this.tipusDocument;
  }

	public  void setTipusDocument(TipusDocumentJPA tipusDocument) {
    this.tipusDocument = tipusDocument;
  }

// IMP Field:pluginid | Table: pfi_plugin | Type: 1  

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name="pfi_mofitido_plugin_fk")
	@JoinColumn(name = "pluginid", referencedColumnName ="pluginID", nullable = false, insertable=false, updatable=false)
	private PluginJPA plugin;

	public PluginJPA getPlugin() {
    return this.plugin;
  }

	public  void setPlugin(PluginJPA plugin) {
    this.plugin = plugin;
  }


 // ---------------  STATIC METHODS ------------------
  public static ModulDeFirmaPerTipusDeDocumentJPA toJPA(ModulDeFirmaPerTipusDeDocument __bean) {
    if (__bean == null) { return null;}
    ModulDeFirmaPerTipusDeDocumentJPA __tmp = new ModulDeFirmaPerTipusDeDocumentJPA();
    __tmp.setID(__bean.getID());
    __tmp.setTipusDocumentID(__bean.getTipusDocumentID());
    __tmp.setPluginID(__bean.getPluginID());
    __tmp.setNom(__bean.getNom());
		return __tmp;
	}


  public static ModulDeFirmaPerTipusDeDocumentJPA copyJPA(ModulDeFirmaPerTipusDeDocumentJPA __jpa) {
    return copyJPA(__jpa,new java.util.HashMap<Object,Object>(), null);
  }

  static java.util.Set<ModulDeFirmaPerTipusDeDocumentJPA> copyJPA(java.util.Set<ModulDeFirmaPerTipusDeDocumentJPA> __jpaSet,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpaSet == null) { return null; }
    java.util.Set<ModulDeFirmaPerTipusDeDocumentJPA> __tmpSet = (java.util.Set<ModulDeFirmaPerTipusDeDocumentJPA>) __alreadyCopied.get(__jpaSet);
    if (__tmpSet != null) { return __tmpSet; };
    __tmpSet = new java.util.HashSet<ModulDeFirmaPerTipusDeDocumentJPA>(__jpaSet.size());
    __alreadyCopied.put(__jpaSet, __tmpSet);
    for (ModulDeFirmaPerTipusDeDocumentJPA __jpa : __jpaSet) {
      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));
    }
    return __tmpSet;
  }

  static ModulDeFirmaPerTipusDeDocumentJPA copyJPA(ModulDeFirmaPerTipusDeDocumentJPA __jpa,
    java.util.Map<Object,Object> __alreadyCopied, String origenJPA) {
    if (__jpa == null) { return null; }
    ModulDeFirmaPerTipusDeDocumentJPA __tmp = (ModulDeFirmaPerTipusDeDocumentJPA) __alreadyCopied.get(__jpa);
    if (__tmp != null) { return __tmp; };
    __tmp = toJPA(__jpa);
    __alreadyCopied.put(__jpa, __tmp);
    // Copia de beans complexes (EXP)
    // Copia de beans complexes (IMP)
    if(!"TipusDocumentJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.tipusDocument) || org.hibernate.Hibernate.isInitialized(__jpa.getTipusDocument()) ) ) {
      __tmp.setTipusDocument(TipusDocumentJPA.copyJPA(__jpa.getTipusDocument(), __alreadyCopied,"ModulDeFirmaPerTipusDeDocumentJPA"));
    }
    if(!"PluginJPA".equals(origenJPA) && 
       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa.plugin) || org.hibernate.Hibernate.isInitialized(__jpa.getPlugin()) ) ) {
      __tmp.setPlugin(PluginJPA.copyJPA(__jpa.getPlugin(), __alreadyCopied,"ModulDeFirmaPerTipusDeDocumentJPA"));
    }

    return __tmp;
  }




}
