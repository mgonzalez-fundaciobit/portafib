package es.caib.portafib.model.entity;

public interface PeticioDeFirma extends org.fundaciobit.genapp.common.IGenAppEntity {

	public long getPeticioDeFirmaID();
	public void setPeticioDeFirmaID(long _peticioDeFirmaID_);

	public java.lang.String getTitol();
	public void setTitol(java.lang.String _titol_);

	public java.lang.String getDescripcio();
	public void setDescripcio(java.lang.String _descripcio_);

	public java.lang.String getMotiu();
	public void setMotiu(java.lang.String _motiu_);

	public long getFitxerAFirmarID();
	public void setFitxerAFirmarID(long _fitxerAFirmarID_);

	public java.lang.Long getFitxerAdaptatID();
	public void setFitxerAdaptatID(java.lang.Long _fitxerAdaptatID_);

	public long getTipusDocumentID();
	public void setTipusDocumentID(long _tipusDocumentID_);

	public java.lang.String getDescripcioTipusDocument();
	public void setDescripcioTipusDocument(java.lang.String _descripcioTipusDocument_);

	public int getPosicioTaulaFirmesID();
	public void setPosicioTaulaFirmesID(int _posicioTaulaFirmesID_);

	public java.sql.Timestamp getDataSolicitud();
	public void setDataSolicitud(java.sql.Timestamp _dataSolicitud_);

	public java.sql.Timestamp getDataFinal();
	public void setDataFinal(java.sql.Timestamp _dataFinal_);

	public java.sql.Timestamp getDataCaducitat();
	public void setDataCaducitat(java.sql.Timestamp _dataCaducitat_);

	public int getTipusFirmaID();
	public void setTipusFirmaID(int _tipusFirmaID_);

	public long getAlgorismeDeFirmaID();
	public void setAlgorismeDeFirmaID(long _algorismeDeFirmaID_);

	public java.lang.Boolean getModeDeFirma();
	public void setModeDeFirma(java.lang.Boolean _modeDeFirma_);

	public int getTipusEstatPeticioDeFirmaID();
	public void setTipusEstatPeticioDeFirmaID(int _tipusEstatPeticioDeFirmaID_);

	public java.lang.String getMotiuDeRebuig();
	public void setMotiuDeRebuig(java.lang.String _motiuDeRebuig_);

	public java.lang.String getIdiomaID();
	public void setIdiomaID(java.lang.String _idiomaID_);

	public int getPrioritatID();
	public void setPrioritatID(int _prioritatID_);

	public long getFluxDeFirmesID();
	public void setFluxDeFirmesID(long _fluxDeFirmesID_);

	public java.lang.String getUsuariAplicacioID();
	public void setUsuariAplicacioID(java.lang.String _usuariAplicacioID_);

	public java.lang.String getRemitentNom();
	public void setRemitentNom(java.lang.String _remitentNom_);

	public java.lang.String getRemitentDescripcio();
	public void setRemitentDescripcio(java.lang.String _remitentDescripcio_);

	public java.lang.String getInformacioAdicional();
	public void setInformacioAdicional(java.lang.String _informacioAdicional_);

	public java.lang.Long getLogoSegellID();
	public void setLogoSegellID(java.lang.Long _logoSegellID_);

	public java.lang.Long getCustodiaInfoID();
	public void setCustodiaInfoID(java.lang.Long _custodiaInfoID_);

	public java.lang.String getUsuariEntitatID();
	public void setUsuariEntitatID(java.lang.String _usuariEntitatID_);

	public boolean isAvisWeb();
	public void setAvisWeb(boolean _avisWeb_);

  // Fitxer
  public <F extends Fitxer> F getFitxerAFirmar();
  // Fitxer
  public <F extends Fitxer> F getFitxerAdaptat();
  // Fitxer
  public <F extends Fitxer> F getLogoSegell();


  // ======================================

}