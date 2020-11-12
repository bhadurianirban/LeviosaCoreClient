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
import org.hedwig.cms.dto.MenuDTO;


/**
 *
 * @author bhaduri
 */
public class MenuClient {
    
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = CMSServicePaths.DGRFCMS_BASE_URL+"/"+CMSServicePaths.MENU_BASE;

    public MenuDTO getMenuTree(MenuDTO menuDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.GET_MENU_TREE);
        WebTarget resource = webTarget;
        ObjectMapper objectMapper = new ObjectMapper();
        String menuDTOJSON = null;
        String respMenuDTOJSON = null;
        try {
            menuDTOJSON = objectMapper.writeValueAsString(menuDTO);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MenuClient.class.getName()).log(Level.SEVERE, null, ex);
            menuDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return menuDTO;
        }
        Invocation.Builder ib = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        Response response = ib.post(javax.ws.rs.client.Entity.entity(menuDTOJSON, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            Logger.getLogger(MenuClient.class.getName()).log(Level.SEVERE, "Service connection response"+Integer.toString(response.getStatus()));
            menuDTO.setResponseCode(HedwigResponseCode.SERVICE_CONNECTION_FAILURE);
            return menuDTO;
        } else {
            respMenuDTOJSON = response.readEntity(String.class);
        }
        try {
            menuDTO = objectMapper.readValue(respMenuDTOJSON, MenuDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(MenuClient.class.getName()).log(Level.SEVERE, null, ex);
            menuDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return menuDTO;
        }
        return menuDTO;
    }
}
