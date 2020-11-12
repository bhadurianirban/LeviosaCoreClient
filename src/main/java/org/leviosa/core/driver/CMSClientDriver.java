/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leviosa.core.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.leviosa.core.client.MenuClient;
import org.leviosa.core.client.TermClient;
import org.leviosa.core.client.TermInstanceClient;
import org.leviosa.core.client.TermMetaClient;
import org.hedwig.cloud.dto.HedwigAuthCredentials;
import org.hedwig.cms.constants.CMSConstants;
import org.hedwig.cms.dto.MenuDTO;
import org.hedwig.cms.dto.MenuNode;
import org.hedwig.cms.dto.TermDTO;
import org.hedwig.cms.dto.TermInstanceDTO;
import org.hedwig.cms.dto.TermMetaDTO;

/**
 *
 * @author dgrfv
 */
public class CMSClientDriver {
    public static HedwigAuthCredentials AUTH_CREDENTIALS;
    public static void main(String args[]) {
        AUTH_CREDENTIALS = new HedwigAuthCredentials();
        AUTH_CREDENTIALS.setProductId(1);
        AUTH_CREDENTIALS.setTenantId(1);
        AUTH_CREDENTIALS.setUserId("bhaduri");
        AUTH_CREDENTIALS.setPassword("1234");
        AUTH_CREDENTIALS.setRoleId(1);
        //deleteTermInstance();
        //saveTermInstance();
        getMenuList();
    }
    public static void isExistsTermInstanceSlug() {
        TermInstanceDTO termInstanceDTO = new TermInstanceDTO();
        termInstanceDTO.setAuthCredentials(AUTH_CREDENTIALS);
        termInstanceDTO.setTermSlug("school");
        termInstanceDTO.setTermInstanceSlug("sp");
        TermInstanceClient termInstanceClient = new TermInstanceClient();
        termInstanceDTO = termInstanceClient.termInstanceSlugExists(termInstanceDTO);
        System.out.println(termInstanceDTO.getResponseCode());
    }
    public static void getTerm() {
        
        TermDTO termDTO = new TermDTO();
        termDTO.setAuthCredentials(AUTH_CREDENTIALS);
        termDTO.setTermSlug("school");
        
        TermClient termClient = new TermClient();
        termDTO = termClient.getTermDetails(termDTO);
        System.out.println(termDTO.getTermDetails().get("termName"));
    }
    public static void getTermMeta() {
        TermMetaDTO termMetaDTO = new TermMetaDTO();
        termMetaDTO.setAuthCredentials(AUTH_CREDENTIALS);
        termMetaDTO.setTermSlug("school");
        
        TermMetaClient termMetaClient = new TermMetaClient();
        termMetaDTO = termMetaClient.getTermMeta(termMetaDTO);
        System.out.println(termMetaDTO.getTermMetaFields().get(1).get(CMSConstants.META_KEY));
    }    

    public static TermInstanceDTO getTermInstance() {
        TermInstanceDTO termInstanceDTO = new TermInstanceDTO();
        termInstanceDTO.setAuthCredentials(AUTH_CREDENTIALS);
        termInstanceDTO.setTermSlug("school");
        termInstanceDTO.setTermInstanceSlug("sp");
        TermInstanceClient termInstanceGet = new TermInstanceClient();
        termInstanceDTO = termInstanceGet.getTermInstance(termInstanceDTO);
        return termInstanceDTO;
    }

    public static void saveTermInstance() {
        TermInstanceDTO termInstanceDTO = new TermInstanceDTO();
        termInstanceDTO.setAuthCredentials(AUTH_CREDENTIALS);
        Map<String, Object> termInstance = new HashMap<>();
        termInstance.put(CMSConstants.TERM_SLUG, "employee");
        termInstance.put(CMSConstants.TERM_INSTANCE_SLUG, "bhom");
        termInstance.put("name", "Bhombol");
        List<String> genderList = new ArrayList<>();
        genderList.add("hum");
        termInstance.put("gender", genderList);
        termInstanceDTO.setTermSlug("employee");
        termInstanceDTO.setTermInstanceSlug("bhom");
        termInstanceDTO.setTermInstance(termInstance);
        TermInstanceClient tc = new TermInstanceClient();
        termInstanceDTO = tc.saveTermInstance(termInstanceDTO);
        System.out.println(termInstanceDTO.getResponseCode());
    }
    

    public static void deleteTermInstance() {
        TermInstanceDTO termInstanceDTO = new TermInstanceDTO();
        termInstanceDTO.setAuthCredentials(AUTH_CREDENTIALS);
        termInstanceDTO.setTermSlug("employee");
        termInstanceDTO.setTermInstanceSlug("bhom");
        TermInstanceClient termInstanceGet = new TermInstanceClient();

        termInstanceGet.deleteTermInstance(termInstanceDTO);
    }

    public static void getTermInstanceList() {
        TermInstanceDTO termInstanceDTO = new TermInstanceDTO();
        termInstanceDTO.setTermSlug("school");

        TermInstanceClient termInstanceGet = new TermInstanceClient();
        termInstanceGet.getTermInstanceList(termInstanceDTO);
        System.out.println(termInstanceDTO.getResponseCode());
    }

    public static void getMenuList() {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setAuthCredentials(AUTH_CREDENTIALS);
        MenuClient menuListGet = new MenuClient();
        menuDTO = menuListGet.getMenuTree(menuDTO);
        MenuNode authorisedMenuRoot = menuDTO.getRootMenuNode();
        List<MenuNode>rootMenuForest = authorisedMenuRoot.getChildren();
        rootMenuForest.stream().forEach(m -> System.out.println(m.getName()));
        System.out.println(menuDTO.getResponseMessage());
    }
}
