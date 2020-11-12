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
import org.hedwig.cms.dto.TermMetaDTO;

/**
 *
 * @author bhaduri
 */
public class TermMetaClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = CMSServicePaths.DGRFCMS_BASE_URL+"/"+CMSServicePaths.TERM_META_BASE;

    private TermMetaDTO callTermMetaService(TermMetaDTO termMetaDTO) {
        WebTarget resource = webTarget;
        ObjectMapper objectMapper = new ObjectMapper();
        String termMetaDTOJSON = null;
        String respTermMetaDTOJSON = null;
        try {
            termMetaDTOJSON = objectMapper.writeValueAsString(termMetaDTO);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(TermClient.class.getName()).log(Level.SEVERE, null, ex);
            termMetaDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return termMetaDTO;
        }
        Invocation.Builder ib = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        Response response = ib.post(javax.ws.rs.client.Entity.entity(termMetaDTOJSON, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            Logger.getLogger(TermInstanceClient.class.getName()).log(Level.SEVERE, "Service connection response" + Integer.toString(response.getStatus()));
            termMetaDTO.setResponseCode(HedwigResponseCode.SERVICE_CONNECTION_FAILURE);
            return termMetaDTO;
        } else {
            respTermMetaDTOJSON = response.readEntity(String.class);
        }
        try {
            termMetaDTO = objectMapper.readValue(respTermMetaDTOJSON, TermMetaDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(TermInstanceClient.class.getName()).log(Level.SEVERE, null, ex);
            termMetaDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return termMetaDTO;
        }
        return termMetaDTO;
    }

    public TermMetaDTO getTermMeta(TermMetaDTO termMetaDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.GET_TERM_META_LIST);
        return callTermMetaService(termMetaDTO);
    }

    public TermMetaDTO getChildTermMetaList(TermMetaDTO termMetaDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.GET_CHILD_TERM_META_LIST);
        return callTermMetaService(termMetaDTO);
    }
}
