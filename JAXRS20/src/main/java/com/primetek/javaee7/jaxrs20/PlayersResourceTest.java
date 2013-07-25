package com.primetek.javaee7.jaxrs20;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientFactory;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author mertcaliskan
 */
public class PlayersResourceTest {
    
    public static void main(String args[]) { 
        Client client = ClientFactory.newClient();
        WebTarget target = client.target("http://localhost:8080/JAXRS20/webresources");
        Response response = target.path("players").request().get();
        assert response.getStatus() == 200;

        String result = response.readEntity(String.class);        
        System.out.println(result);
    }
}