/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leviosa.core.driver;

import org.hedwig.cloud.dto.HedwigConstants;
import org.hedwig.leviosa.constants.CMSServicePaths;
import org.leviosa.core.client.MediaClient;
import org.leviosa.core.client.MenuClient;
import org.leviosa.core.client.TermClient;
import org.leviosa.core.client.TermInstanceClient;
import org.leviosa.core.client.TermMetaClient;
import org.hedwig.cms.dto.MediaDTO;
import org.hedwig.cms.dto.MenuDTO;
import org.hedwig.cms.dto.TermDTO;
import org.hedwig.cms.dto.TermInstanceDTO;
import org.hedwig.cms.dto.TermMetaDTO;

/**
 *
 * @author bhaduri
 */
public class LeviosaClientService {

//    public LeviosaClientService() {
//        CMSServicePaths.CONN_URL = HedwigConstants.createConnectionUrl(CMSServicePaths.LEVIOSA_SERVER,CMSServicePaths.LEVIOSA_PORT,CMSServicePaths.LEVIOSA_BASE_URL);
//    }
    public LeviosaClientService(String server,String serverPort) {
        CMSServicePaths.CONN_URL = HedwigConstants.createConnectionUrl(server,serverPort,CMSServicePaths.LEVIOSA_BASE_URL);
    }

    


    public TermMetaDTO getTermMetaList(TermMetaDTO termMetaDTO) {

        TermMetaClient termMetaClient = new TermMetaClient();
        termMetaDTO = termMetaClient.getTermMeta(termMetaDTO);
        //List<Map<String, Object>> termMetaFields = termMetaDTO.getTermMetaFields();
        
        return termMetaDTO;

    }
    public TermMetaDTO getChildTermMetaList(TermMetaDTO termMetaDTO) {

        TermMetaClient termMetaClient = new TermMetaClient();
        termMetaDTO = termMetaClient.getChildTermMetaList(termMetaDTO);
        //List<Map<String, Object>> termMetaFields = termMetaDTO.getTermMetaFields();
        
        return termMetaDTO;

    }

    public TermInstanceDTO getTermInstance(TermInstanceDTO termInstanceDTO) {

        TermInstanceClient termInstanceClient = new TermInstanceClient();
        termInstanceDTO = termInstanceClient.getTermInstance(termInstanceDTO);

        return termInstanceDTO;

    }

    public TermInstanceDTO getTermInstanceList(TermInstanceDTO termInstanceDTO) {

        TermInstanceClient termInstanceClient = new TermInstanceClient();
        termInstanceDTO = termInstanceClient.getTermInstanceList(termInstanceDTO);

        return termInstanceDTO;

    }
    public TermInstanceDTO getChildTermInstanceList(TermInstanceDTO termInstanceDTO) {

        TermInstanceClient termInstanceClient = new TermInstanceClient();
        termInstanceDTO = termInstanceClient.getChildTermInstanceList(termInstanceDTO);

        return termInstanceDTO;

    }
    public TermInstanceDTO deleteTermInstance(TermInstanceDTO termInstanceDTO) {

        TermInstanceClient termInstanceClient = new TermInstanceClient();
        termInstanceDTO = termInstanceClient.deleteTermInstance(termInstanceDTO);

        return termInstanceDTO;

    }

    public TermInstanceDTO saveTermInstance(TermInstanceDTO termInstanceDTO) {

        TermInstanceClient termInstanceClient = new TermInstanceClient();
        termInstanceDTO = termInstanceClient.saveTermInstance(termInstanceDTO);

        return termInstanceDTO;
    }

    public TermDTO getTermDetails(TermDTO termDTO) {

        TermClient termClient = new TermClient();
        termDTO = termClient.getTermDetails(termDTO);

        return termDTO;
    }

    public MenuDTO getMenuTree(MenuDTO menuDTO) {

        MenuClient mc = new MenuClient();
        menuDTO = mc.getMenuTree(menuDTO);

        return menuDTO;
    }

    public TermInstanceDTO isExistsTermInstanceSlug(TermInstanceDTO termInstanceDTO) {
        
        TermInstanceClient termInstanceClient = new TermInstanceClient();
        termInstanceDTO = termInstanceClient.termInstanceSlugExists(termInstanceDTO);
        return termInstanceDTO;
    }
    public MediaDTO deleteMediaTermInstance (MediaDTO mediaDTO) {
        
        MediaClient mediaClient = new MediaClient();
        mediaDTO = mediaClient.deleteMedia(mediaDTO);
        return mediaDTO;
    }
}
