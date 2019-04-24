package es.caib.portafib.model.entity;

public interface UsuariAplicacioConfiguracio extends org.fundaciobit.genapp.common.IGenAppEntity {

	public long getUsuariAplicacioConfigID();
	public void setUsuariAplicacioConfigID(long _usuariAplicacioConfigID_);

	public java.lang.String getNom();
	public void setNom(java.lang.String _nom_);

	public java.lang.String getEntitatID();
	public void setEntitatID(java.lang.String _entitatID_);

	public boolean isUsEnFirmaApiSimpleServidor();
	public void setUsEnFirmaApiSimpleServidor(boolean _usEnFirmaApiSimpleServidor_);

	public boolean isUsEnFirmaApiSimpleWeb();
	public void setUsEnFirmaApiSimpleWeb(boolean _usEnFirmaApiSimpleWeb_);

	public boolean isUsEnFirmaWeb();
	public void setUsEnFirmaWeb(boolean _usEnFirmaWeb_);

	public boolean isUsEnFirmaWS1();
	public void setUsEnFirmaWS1(boolean _usEnFirmaWS1_);

	public boolean isUsEnFirmaWS2();
	public void setUsEnFirmaWS2(boolean _usEnFirmaWS2_);

	public boolean isUsEnFirmaPassarelaServidor();
	public void setUsEnFirmaPassarelaServidor(boolean _usEnFirmaPassarelaServidor_);

	public boolean isUsEnFirmaPassarelaWeb();
	public void setUsEnFirmaPassarelaWeb(boolean _usEnFirmaPassarelaWeb_);

	public int getUsPoliticaDeFirma();
	public void setUsPoliticaDeFirma(int _usPoliticaDeFirma_);

	public java.lang.String getPolicyIdentifier();
	public void setPolicyIdentifier(java.lang.String _policyIdentifier_);

	public java.lang.String getPolicyIdentifierHash();
	public void setPolicyIdentifierHash(java.lang.String _policyIdentifierHash_);

	public java.lang.String getPolicyIdentifierHashAlgorithm();
	public void setPolicyIdentifierHashAlgorithm(java.lang.String _policyIdentifierHashAlgorithm_);

	public java.lang.String getPolicyUrlDocument();
	public void setPolicyUrlDocument(java.lang.String _policyUrlDocument_);

	public java.lang.String getFiltreCertificats();
	public void setFiltreCertificats(java.lang.String _filtreCertificats_);

	public int getTipusOperacioFirma();
	public void setTipusOperacioFirma(int _tipusOperacioFirma_);

	public int getTipusFirmaID();
	public void setTipusFirmaID(int _tipusFirmaID_);

	public java.lang.Integer getAlgorismeDeFirmaID();
	public void setAlgorismeDeFirmaID(java.lang.Integer _algorismeDeFirmaID_);

	public boolean isModeDeFirma();
	public void setModeDeFirma(boolean _modeDeFirma_);

	public int getPoliticaTaulaFirmes();
	public void setPoliticaTaulaFirmes(int _politicaTaulaFirmes_);

	public int getPosicioTaulaFirmesID();
	public void setPosicioTaulaFirmesID(int _posicioTaulaFirmesID_);

	public java.lang.Long getFirmatPerFormatID();
	public void setFirmatPerFormatID(java.lang.Long _firmatPerFormatID_);

	public java.lang.String getPropietatsTaulaFirmes();
	public void setPropietatsTaulaFirmes(java.lang.String _propietatsTaulaFirmes_);

	public java.lang.Long getMotiuDelegacioID();
	public void setMotiuDelegacioID(java.lang.Long _motiuDelegacioID_);

	public int getPoliticaSegellatDeTemps();
	public void setPoliticaSegellatDeTemps(int _politicaSegellatDeTemps_);

	public java.lang.Long getPluginSegellatID();
	public void setPluginSegellatID(java.lang.Long _pluginSegellatID_);

	public java.lang.String getHtmlPerLlistarPluginsFirmaWeb();
	public void setHtmlPerLlistarPluginsFirmaWeb(java.lang.String _htmlPerLlistarPluginsFirmaWeb_);

	public java.lang.Long getPluginFirmaServidorID();
	public void setPluginFirmaServidorID(java.lang.Long _pluginFirmaServidorID_);

	public java.lang.Integer getUpgradeSignFormat();
	public void setUpgradeSignFormat(java.lang.Integer _upgradeSignFormat_);

	public java.lang.Long getLoginCertificateID();
	public void setLoginCertificateID(java.lang.Long _loginCertificateID_);

	public java.lang.Boolean getComprovarNifFirma();
	public void setComprovarNifFirma(java.lang.Boolean _comprovarNifFirma_);

	public java.lang.Boolean getCheckCanviatDocFirmat();
	public void setCheckCanviatDocFirmat(java.lang.Boolean _checkCanviatDocFirmat_);

	public java.lang.Boolean getValidarFirma();
	public void setValidarFirma(java.lang.Boolean _validarFirma_);

	public java.lang.Boolean getValidarCertificat();
	public void setValidarCertificat(java.lang.Boolean _validarCertificat_);

  // Fitxer
  public <F extends Fitxer> F getLoginCertificate();


  // ======================================

}