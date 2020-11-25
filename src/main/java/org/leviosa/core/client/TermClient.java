/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leviosa.core.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.hedwig.cloud.response.HedwigResponseCode;
import org.hedwig.cms.constants.CMSServicePaths;
import org.hedwig.cms.dto.TermDTO;

/**
 *
 * @author bhaduri
 */
public class TermClient {

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
     */

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = CMSServicePaths.LEVIOSA_BASE_URL+"/"+CMSServicePaths.TERM_BASE;;

    private TermDTO callTermService(TermDTO termDTO) {
        WebTarget resource = webTarget;
        ObjectMapper objectMapper = new ObjectMapper();
        String termDTOJSON = null;
        String respTermDTOJSON = null;
        try {
            termDTOJSON = objectMapper.writeValueAsString(termDTO);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(TermClient.class.getName()).log(Level.SEVERE, null, ex);
            termDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return termDTO;
        }
        Invocation.Builder ib = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        Response response = ib.post(javax.ws.rs.client.Entity.entity(termDTOJSON, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            Logger.getLogger(TermClient.class.getName()).log(Level.SEVERE, "Service connection response" + Integer.toString(response.getStatus()));
            termDTO.setResponseCode(HedwigResponseCode.SERVICE_CONNECTION_FAILURE);
            return termDTO;
        } else {
            respTermDTOJSON = response.readEntity(String.class);
        }
        try {
            termDTO = objectMapper.readValue(respTermDTOJSON, TermDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(TermInstanceClient.class.getName()).log(Level.SEVERE, null, ex);
            termDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return termDTO;
        }
        return termDTO;
    }

    public TermDTO getTermDetails(TermDTO termDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.GET_TERM_DETAILS);
        return callTermService(termDTO);
    }

    public TermDTO getRootTermList(TermDTO termDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.GET_ROOT_TERM_LIST);
        return callTermService(termDTO);
    }
    public TermDTO getChildTermList(TermDTO termDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.GET_CHILD_TERM_LIST);
        return callTermService(termDTO);
    }
}
