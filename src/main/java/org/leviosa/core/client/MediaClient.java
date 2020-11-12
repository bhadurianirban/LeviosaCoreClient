/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.leviosa.core.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.hedwig.cloud.response.HedwigResponseCode;
import org.hedwig.cms.constants.CMSServicePaths;
import org.hedwig.cms.dto.MediaDTO;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

/**
 *
 * @author dgrfiv
 */
public class MediaClient {

    private  WebTarget webTarget;
    private  Client client;
    private static final String BASE_URI = CMSServicePaths.DGRFCMS_BASE_URL+"/"+CMSServicePaths.MEDIA_BASE;

    public MediaClient() {
        
    }

    public MediaDTO uploadMedia(MediaDTO mediaDTO) {
        client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.UPLOAD_MEDIA);
        String mediaFilePath = mediaDTO.getMediaFilePath();
        WebTarget resource = webTarget;
        ObjectMapper objectMapper = new ObjectMapper();
        String mediaDTOJSON = null;
        try {
            mediaDTOJSON = objectMapper.writeValueAsString(mediaDTO);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MediaClient.class.getName()).log(Level.SEVERE, null, ex);
            mediaDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return mediaDTO;
        }
//        FileDataBodyPart filePart = new FileDataBodyPart("file", new File("/home/dgrfiv/Downloads/2018-07-09/V1/ch201.csv"));
        FileDataBodyPart filePart = new FileDataBodyPart("uploadfile", new File(mediaFilePath));
        MultiPart multipartEntity = new FormDataMultiPart()
                .field("mediaDTO", mediaDTOJSON)
                .bodyPart(filePart);
        Response response = resource.request().post(Entity.entity(multipartEntity, multipartEntity.getMediaType()));
         if (response.getStatus() != 200) {
            Logger.getLogger(MediaClient.class.getName()).log(Level.SEVERE, "Service connection response"+Integer.toString(response.getStatus()));
            mediaDTO.setResponseCode(HedwigResponseCode.SERVICE_CONNECTION_FAILURE);
            return mediaDTO;
        }
        String respMapJSON = response.readEntity(String.class);
        try {
            mediaDTO = objectMapper.readValue(respMapJSON, MediaDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(MediaClient.class.getName()).log(Level.SEVERE, null, ex);
            mediaDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return mediaDTO;
        }
        response.close();
        return mediaDTO;
    }
    public MediaDTO deleteMedia(MediaDTO mediaDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(CMSServicePaths.DELETE_MEDIA);
        WebTarget resource = webTarget;
        ObjectMapper objectMapper = new ObjectMapper();
        String mediaDTOJSON = null;
        String respMediaDTOJSON = null;
        try {
            mediaDTOJSON = objectMapper.writeValueAsString(mediaDTO);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(TermInstanceClient.class.getName()).log(Level.SEVERE, null, ex);
            mediaDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return mediaDTO;
        }
        Invocation.Builder ib = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        Response response = ib.post(javax.ws.rs.client.Entity.entity(mediaDTOJSON, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            Logger.getLogger(MediaClient.class.getName()).log(Level.SEVERE, "Service connection response"+Integer.toString(response.getStatus()));
            mediaDTO.setResponseCode(HedwigResponseCode.SERVICE_CONNECTION_FAILURE);
            return mediaDTO;
        } else {
            respMediaDTOJSON = response.readEntity(String.class);
        }
        try {
            mediaDTO = objectMapper.readValue(respMediaDTOJSON, MediaDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(TermInstanceClient.class.getName()).log(Level.SEVERE, null, ex);
            mediaDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return mediaDTO;
        }
        return mediaDTO;
    }
}
