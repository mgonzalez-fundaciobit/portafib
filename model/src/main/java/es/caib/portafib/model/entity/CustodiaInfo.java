package es.caib.portafib.model.entity;

public interface CustodiaInfo extends org.fundaciobit.genapp.common.IGenAppEntity {

	public long getCustodiaInfoID();
	public void setCustodiaInfoID(long _custodiaInfoID_);

	public java.lang.String getNomPlantilla();
	public void setNomPlantilla(java.lang.String _nomPlantilla_);

	public java.lang.String getCustodiaDocumentID();
	public void setCustodiaDocumentID(java.lang.String _custodiaDocumentID_);

	public long getPluginID();
	public void setPluginID(long _pluginID_);

	public java.lang.String getCustodiaPluginParameters();
	public void setCustodiaPluginParameters(java.lang.String _custodiaPluginParameters_);

	public boolean isCustodiar();
	public void setCustodiar(boolean _custodiar_);

	public java.lang.String getUrlFitxerCustodiat();
	public void setUrlFitxerCustodiat(java.lang.String _urlFitxerCustodiat_);

	public java.lang.String getPagines();
	public void setPagines(java.lang.String _pagines_);

	public java.lang.String getMissatge();
	public void setMissatge(java.lang.String _missatge_);

	public long getMissatgePosicioPaginaID();
	public void setMissatgePosicioPaginaID(long _missatgePosicioPaginaID_);

	public java.lang.String getCodiBarresID();
	public void setCodiBarresID(java.lang.String _codiBarresID_);

	public long getCodiBarresPosicioPaginaID();
	public void setCodiBarresPosicioPaginaID(long _codiBarresPosicioPaginaID_);

	public java.lang.String getCodiBarresText();
	public void setCodiBarresText(java.lang.String _codiBarresText_);

	public java.lang.String getUsuariEntitatID();
	public void setUsuariEntitatID(java.lang.String _usuariEntitatID_);

	public java.lang.String getUsuariAplicacioID();
	public void setUsuariAplicacioID(java.lang.String _usuariAplicacioID_);

	public java.lang.String getEntitatID();
	public void setEntitatID(java.lang.String _entitatID_);

	public java.lang.String getTitolPeticio();
	public void setTitolPeticio(java.lang.String _titolPeticio_);

	public java.sql.Timestamp getDataCustodia();
	public void setDataCustodia(java.sql.Timestamp _dataCustodia_);

	public boolean isEditable();
	public void setEditable(boolean _editable_);



  // ======================================

}
