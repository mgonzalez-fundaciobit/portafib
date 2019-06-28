
package es.caib.portafib.model.fields;
import org.fundaciobit.genapp.common.query.*;

public class CustodiaInfoQueryPath extends org.fundaciobit.genapp.common.query.QueryPath {

  public CustodiaInfoQueryPath() {
  }

  protected CustodiaInfoQueryPath(QueryPath parentQueryPath) {
    super(parentQueryPath);
  }

  public LongField CUSTODIAINFOID() {
    return new LongField(getQueryPath(), CustodiaInfoFields.CUSTODIAINFOID);
  }

  public StringField NOMPLANTILLA() {
    return new StringField(getQueryPath(), CustodiaInfoFields.NOMPLANTILLA);
  }

  public StringField CUSTODIADOCUMENTID() {
    return new StringField(getQueryPath(), CustodiaInfoFields.CUSTODIADOCUMENTID);
  }

  public LongField PLUGINID() {
    return new LongField(getQueryPath(), CustodiaInfoFields.PLUGINID);
  }

  public StringField CUSTODIAPLUGINPARAMETERS() {
    return new StringField(getQueryPath(), CustodiaInfoFields.CUSTODIAPLUGINPARAMETERS);
  }

  public BooleanField CUSTODIAR() {
    return new BooleanField(getQueryPath(), CustodiaInfoFields.CUSTODIAR);
  }

  public StringField PAGINES() {
    return new StringField(getQueryPath(), CustodiaInfoFields.PAGINES);
  }

  public StringField MISSATGE() {
    return new StringField(getQueryPath(), CustodiaInfoFields.MISSATGE);
  }

  public LongField MISSATGEPOSICIOPAGINAID() {
    return new LongField(getQueryPath(), CustodiaInfoFields.MISSATGEPOSICIOPAGINAID);
  }

  public StringField CODIBARRESID() {
    return new StringField(getQueryPath(), CustodiaInfoFields.CODIBARRESID);
  }

  public LongField CODIBARRESPOSICIOPAGINAID() {
    return new LongField(getQueryPath(), CustodiaInfoFields.CODIBARRESPOSICIOPAGINAID);
  }

  public StringField CODIBARRESTEXT() {
    return new StringField(getQueryPath(), CustodiaInfoFields.CODIBARRESTEXT);
  }

  public StringField USUARIENTITATID() {
    return new StringField(getQueryPath(), CustodiaInfoFields.USUARIENTITATID);
  }

  public StringField USUARIAPLICACIOID() {
    return new StringField(getQueryPath(), CustodiaInfoFields.USUARIAPLICACIOID);
  }

  public StringField ENTITATID() {
    return new StringField(getQueryPath(), CustodiaInfoFields.ENTITATID);
  }

  public StringField TITOLPETICIO() {
    return new StringField(getQueryPath(), CustodiaInfoFields.TITOLPETICIO);
  }

  public TimestampField DATACUSTODIA() {
    return new TimestampField(getQueryPath(), CustodiaInfoFields.DATACUSTODIA);
  }

  public BooleanField EDITABLE() {
    return new BooleanField(getQueryPath(), CustodiaInfoFields.EDITABLE);
  }

  public StringField CSV() {
    return new StringField(getQueryPath(), CustodiaInfoFields.CSV);
  }

  public StringField CSVVALIDATIONWEB() {
    return new StringField(getQueryPath(), CustodiaInfoFields.CSVVALIDATIONWEB);
  }

  public StringField CSVGENERATIONDEFINITION() {
    return new StringField(getQueryPath(), CustodiaInfoFields.CSVGENERATIONDEFINITION);
  }

  public StringField URLFITXERCUSTODIAT() {
    return new StringField(getQueryPath(), CustodiaInfoFields.URLFITXERCUSTODIAT);
  }

  public StringField ORIGINALFILEDIRECTURL() {
    return new StringField(getQueryPath(), CustodiaInfoFields.ORIGINALFILEDIRECTURL);
  }

  public StringField PRINTABLEFILEDIRECTURL() {
    return new StringField(getQueryPath(), CustodiaInfoFields.PRINTABLEFILEDIRECTURL);
  }

  public StringField ENIFILEDIRECTURL() {
    return new StringField(getQueryPath(), CustodiaInfoFields.ENIFILEDIRECTURL);
  }

  public StringField EXPEDIENTARXIUID() {
    return new StringField(getQueryPath(), CustodiaInfoFields.EXPEDIENTARXIUID);
  }

  public StringField DOCUMENTARXIUID() {
    return new StringField(getQueryPath(), CustodiaInfoFields.DOCUMENTARXIUID);
  }



  @Override
  public String getQueryPath() {
    return ((this.parentQueryPath == null) ? (CustodiaInfoFields._TABLE_MODEL + ".")
        : this.parentQueryPath.getQueryPath());
  }


/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public EntitatQueryPath ENTITATS() {
    return new EntitatQueryPath(new QueryPath() {
      public String getQueryPath() {
          return CustodiaInfoQueryPath.this.getQueryPath() + "entitats" + ".";
      }
    });
  }
*/

/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public PeticioDeFirmaQueryPath PETICIODEFIRMAS() {
    return new PeticioDeFirmaQueryPath(new QueryPath() {
      public String getQueryPath() {
          return CustodiaInfoQueryPath.this.getQueryPath() + "peticioDeFirmas" + ".";
      }
    });
  }
*/

/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public UsuariAplicacioQueryPath USUARIAPLICACIOS() {
    return new UsuariAplicacioQueryPath(new QueryPath() {
      public String getQueryPath() {
          return CustodiaInfoQueryPath.this.getQueryPath() + "usuariAplicacios" + ".";
      }
    });
  }
*/

/* L'ús d'aquest camp (OneToMany) llança una exception:
 [Illegal attempt to dereference a collection]

 // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA 

  public UsuariEntitatQueryPath USUARIENTITATS() {
    return new UsuariEntitatQueryPath(new QueryPath() {
      public String getQueryPath() {
          return CustodiaInfoQueryPath.this.getQueryPath() + "usuariEntitats" + ".";
      }
    });
  }
*/

  public PluginQueryPath PLUGIN() {
    return new PluginQueryPath(new QueryPath() {
      public String getQueryPath() {
          return CustodiaInfoQueryPath.this.getQueryPath() + "plugin" + ".";
      }
    });
  }

  public CodiBarresQueryPath CODIBARRES() {
    return new CodiBarresQueryPath(new QueryPath() {
      public String getQueryPath() {
          return CustodiaInfoQueryPath.this.getQueryPath() + "codiBarres" + ".";
      }
    });
  }

  public UsuariEntitatQueryPath USUARIENTITAT() {
    return new UsuariEntitatQueryPath(new QueryPath() {
      public String getQueryPath() {
          return CustodiaInfoQueryPath.this.getQueryPath() + "usuariEntitat" + ".";
      }
    });
  }

  public UsuariAplicacioQueryPath USUARIAPLICACIO() {
    return new UsuariAplicacioQueryPath(new QueryPath() {
      public String getQueryPath() {
          return CustodiaInfoQueryPath.this.getQueryPath() + "usuariAplicacio" + ".";
      }
    });
  }

  public EntitatQueryPath ENTITAT() {
    return new EntitatQueryPath(new QueryPath() {
      public String getQueryPath() {
          return CustodiaInfoQueryPath.this.getQueryPath() + "entitat" + ".";
      }
    });
  }

}
