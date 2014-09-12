package es.caib.portafib.logic.utils;

import java.io.File;

/**
 * 
 * @author anadal
 * 
 */
public class StampTaulaDeFirmes {
  int numFirmes;
  int posicioTaulaDeFirmes;
  String signantLabel;
  String resumLabel;
  String descLabel;
  String desc;
  String titolLabel;
  String titol;
  File logoFile;

  /**
   * 
   */
  public StampTaulaDeFirmes() {
    super();
  }

  /**
   * @param numFirmes
   * @param posicioTaulaDeFirmes
   * @param signantLabel
   * @param resumLabel
   * @param descLabel
   * @param desc
   * @param titolLabel
   * @param titol
   * @param logoFile
   */
  public StampTaulaDeFirmes(int numFirmes, int posicioTaulaDeFirmes, String signantLabel,
      String resumLabel, String descLabel, String desc, String titolLabel, String titol,
      File logoFile) {
    super();
    this.numFirmes = numFirmes;
    this.posicioTaulaDeFirmes = posicioTaulaDeFirmes;
    this.signantLabel = signantLabel;
    this.resumLabel = resumLabel;
    this.descLabel = descLabel;
    this.desc = desc;
    this.titolLabel = titolLabel;
    this.titol = titol;
    this.logoFile = logoFile;
  }

  public int getNumFirmes() {
    return numFirmes;
  }

  public void setNumFirmes(int numFirmes) {
    this.numFirmes = numFirmes;
  }

  public int getPosicioTaulaDeFirmes() {
    return posicioTaulaDeFirmes;
  }

  public void setPosicioTaulaDeFirmes(int posicioTaulaDeFirmes) {
    this.posicioTaulaDeFirmes = posicioTaulaDeFirmes;
  }

  public String getSignantLabel() {
    return signantLabel;
  }

  public void setSignantLabel(String signantLabel) {
    this.signantLabel = signantLabel;
  }

  public String getResumLabel() {
    return resumLabel;
  }

  public void setResumLabel(String resumLabel) {
    this.resumLabel = resumLabel;
  }

  public String getDescLabel() {
    return descLabel;
  }

  public void setDescLabel(String descLabel) {
    this.descLabel = descLabel;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getTitolLabel() {
    return titolLabel;
  }

  public void setTitolLabel(String titolLabel) {
    this.titolLabel = titolLabel;
  }

  public String getTitol() {
    return titol;
  }

  public void setTitol(String titol) {
    this.titol = titol;
  }

  public File getLogoFile() {
    return logoFile;
  }

  public void setLogoFile(File logoFile) {
    this.logoFile = logoFile;
  }

}
