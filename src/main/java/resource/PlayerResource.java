/*
 * Copyright 2016 tantk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package resource;

import Manager.PlayerManager;
import com.google.gson.Gson;
import entity.Player;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author tantk
 */
@Path("player")
@RequestScoped
public class PlayerResource {

    @Inject 
    private PlayerManager playerMgr;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Hello, Heroku!";
    }

    @GET
    @Path("create/{makePlayer}")
    @Produces("application/json")
    public Response get(@Context HttpServletRequest request, @PathParam("makePlayer") String makePlayer) {
        //  PlayerManager playerMgr = new 
        Player g = playerMgr.createPlayer(makePlayer);
        HttpSession session = request.getSession();
        session.setAttribute("UserName", makePlayer);
        session.setAttribute("userID", g.getId());
        Gson gson = new Gson();
        String jsonInString = gson.toJson(session.getAttribute("userID"));

        // Player player= playerMgr.createPlayer(makePlayer);
        return (Response.ok(jsonInString).build());

    }

    @GET
    @Path("/viewall")
    @Produces(MediaType.APPLICATION_JSON)
    public Response seeAllGame() {
        //TODO return proper representation object
        return Response.ok(playerMgr.getAllPlayersJSon()).build();

    }

}
