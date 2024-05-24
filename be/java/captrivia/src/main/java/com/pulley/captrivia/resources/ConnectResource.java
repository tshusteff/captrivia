package com.pulley.captrivia.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/connect")
@Produces(MediaType.TEXT_PLAIN)
public class ConnectResource {

    // TODO: Fix this so that the data is not hardcoded and is in the right
    // shape that the frontend expects, or maybe not worry about leaderboards at all!
    @GET
    public String getConnect(@QueryParam("name") String name) {
        return "Hello "+name;
    }

}

