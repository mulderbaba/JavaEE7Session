/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.primetek.javaee7.jaxrs20;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;

/**
 * REST Web Service
 *
 * @author mertcaliskan
 */
@Path("players")
@RequestScoped
public class PlayersResource {

    @Context
    private UriInfo context;
    
    List<String> players;

    public PlayersResource() {
        players = new ArrayList<String>();
        players.add("Iniesta");
        players.add("Messi");
        players.add("Xavi");
    }

    @GET
    @Produces("application/json")
    public List getJson() {
        return players;
    }
}
