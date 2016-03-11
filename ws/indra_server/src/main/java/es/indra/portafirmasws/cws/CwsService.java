package es.indra.portafirmasws.cws;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2013-10-15T09:09:33.898+02:00
 * Generated source version: 2.7.4
 * 
 */
@WebServiceClient(name = "CwsService", 
                  wsdlLocation = "indra-portafirmes.wsdl",
                  targetNamespace = "http://www.indra.es/portafirmasws/cws") 
public class CwsService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.indra.es/portafirmasws/cws", "CwsService");
    public final static QName CWS = new QName("http://www.indra.es/portafirmasws/cws", "CWS");
    static {
        URL url = CwsService.class.getResource("indra-portafirmes.wsdl");
        if (url == null) {
            url = CwsService.class.getClassLoader().getResource("indra-portafirmes.wsdl");
        } 
        if (url == null) {
            java.util.logging.Logger.getLogger(CwsService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "indra-portafirmes.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public CwsService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public CwsService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CwsService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns Cws
     */
    @WebEndpoint(name = "CWS")
    public Cws getCWS() {
        return super.getPort(CWS, Cws.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Cws
     */
    @WebEndpoint(name = "CWS")
    public Cws getCWS(WebServiceFeature... features) {
        return super.getPort(CWS, Cws.class, features);
    }

}