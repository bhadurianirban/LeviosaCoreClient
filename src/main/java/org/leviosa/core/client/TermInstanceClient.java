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
import org.hedwig.leviosa.constants.CMSServicePaths;
import org.hedwig.cms.dto.TermInstanceDTO;


/**
 *
 * @author bhaduri
 */
public class TermInstanceClient {

    private WebTarget webTarget;
    private Client client;
    private String BASE_URI;

    public TermInstanceClient() {
        BASE_URI = CMSServicePaths.CONN_URL+"/"+CMSServicePaths.TERM_INSTANCE_BASE;
    }

    
    
    private TermInstanceDTO callTermInstanceService(TermInstanceDTO termInstanceDTO) {
        WebTarget resource = webTarget;
        ObjectMapper objectMapper = new ObjectMapper();
        String termInstanceGetDTOJSON = null;
        String respTermInstanceGetDTOJSON = null;
        try {
            termInstanceGetDTOJSON = objectMapper.writeValueAsString(termInstanceDTO);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(TermInstanceClient.class.getName()).log(Level.SEVERE, null, ex);
            termInstanceDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return termInstanceDTO;
        }
        Invocation.Builder ib = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        Response response = ib.post(javax.ws.rs.client.Entity.entity(termInstanceGetDTOJSON, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            Logger.getLogger(TermInstanceClient.class.getName()).log(Level.SEVERE, "Service connection response"+Integer.toString(response.getStatus()));
            termInstanceDTO.setResponseCode(HedwigResponseCode.SERVICE_CONNECTION_FAILURE);
            return termInstanceDTO;
        } else {
            respTermInstanceGetDTOJSON = response.readEntity(String.class);
        }
        try {
            termInstanceDTO = objectMapper.readValue(respTermInstanceGetDTOJSON, TermInstanceDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(TermInstanceClient.class.getName()).log(Level.SEVERE, null, ex);
            termInstanceDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return termInstanceDTO;
        }
        return termInstanceDTO;
    }
    public TermInstanceDTO getTermInstance(TermInstanceDTO termInstanceDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.GET_TERM_INSTANCE);
        return callTermInstanceService(termInstanceDTO);
    }
    public TermInstanceDTO getTermInstanceList(TermInstanceDTO termInstanceDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.GET_TERM_INSTANCE_LIST);
        return callTermInstanceService(termInstanceDTO);
    }    
    public TermInstanceDTO saveTermInstance(TermInstanceDTO termInstanceDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.SAVE_TERM_INSTANCE);
        return callTermInstanceService(termInstanceDTO);

    }
    public TermInstanceDTO deleteTermInstance(TermInstanceDTO termInstanceDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.DELETE_TERM_INSTANCE);
        return callTermInstanceService(termInstanceDTO);

    }
    public TermInstanceDTO termInstanceSlugExists(TermInstanceDTO termInstanceDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.IS_TERM_INSTANCE_EXISTS);
        return callTermInstanceService(termInstanceDTO);
    }
    public TermInstanceDTO createMediaTermInstance(TermInstanceDTO termInstanceDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        
        webTarget = client.target(BASE_URI).path(CMSServicePaths.INIT_MEDIA_TERM_INSTANCE);
        return callTermInstanceService(termInstanceDTO);
    }
    public TermInstanceDTO getChildTermInstanceList(TermInstanceDTO termInstanceDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.GET_CHILD_TERM_INSTANCE_LIST);
        return callTermInstanceService(termInstanceDTO);
    }

}
