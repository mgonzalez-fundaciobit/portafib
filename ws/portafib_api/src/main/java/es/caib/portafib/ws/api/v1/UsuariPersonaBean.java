
package es.caib.portafib.ws.api.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for usuariPersonaBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="usuariPersonaBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idiomaID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="llinatges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nif" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rubrica" type="{http://impl.v1.ws.portafib.caib.es/}fitxerBean" minOccurs="0"/>
 *         &lt;element name="rubricaID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="usuariPersonaID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "usuariPersonaBean", propOrder = {
    "email",
    "idiomaID",
    "llinatges",
    "nif",
    "nom",
    "rubrica",
    "rubricaID",
    "usuariPersonaID"
})
public class UsuariPersonaBean {

    protected String email;
    protected String idiomaID;
    protected String llinatges;
    protected String nif;
    protected String nom;
    protected FitxerBean rubrica;
    protected Long rubricaID;
    protected String usuariPersonaID;

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the idiomaID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdiomaID() {
        return idiomaID;
    }

    /**
     * Sets the value of the idiomaID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdiomaID(String value) {
        this.idiomaID = value;
    }

    /**
     * Gets the value of the llinatges property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLlinatges() {
        return llinatges;
    }

    /**
     * Sets the value of the llinatges property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLlinatges(String value) {
        this.llinatges = value;
    }

    /**
     * Gets the value of the nif property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNif() {
        return nif;
    }

    /**
     * Sets the value of the nif property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNif(String value) {
        this.nif = value;
    }

    /**
     * Gets the value of the nom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets the value of the nom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNom(String value) {
        this.nom = value;
    }

    /**
     * Gets the value of the rubrica property.
     * 
     * @return
     *     possible object is
     *     {@link FitxerBean }
     *     
     */
    public FitxerBean getRubrica() {
        return rubrica;
    }

    /**
     * Sets the value of the rubrica property.
     * 
     * @param value
     *     allowed object is
     *     {@link FitxerBean }
     *     
     */
    public void setRubrica(FitxerBean value) {
        this.rubrica = value;
    }

    /**
     * Gets the value of the rubricaID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRubricaID() {
        return rubricaID;
    }

    /**
     * Sets the value of the rubricaID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRubricaID(Long value) {
        this.rubricaID = value;
    }

    /**
     * Gets the value of the usuariPersonaID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuariPersonaID() {
        return usuariPersonaID;
    }

    /**
     * Sets the value of the usuariPersonaID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuariPersonaID(String value) {
        this.usuariPersonaID = value;
    }

}