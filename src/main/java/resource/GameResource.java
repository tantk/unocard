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

import Manager.GameManager;
import com.google.gson.Gson;
import entity.UNOGame;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author tantk
 */
@Path("/game")
@RequestScoped
public class GameResource {

    @Inject
    private GameManager gameMgr;
    public GameResource() {
    }

    @POST
    @Path("create/{gamename}")
    @Produces("application/json")
    public Response createGame(@PathParam("gamename") String gamename)
    {
        Gson gson = new Gson();
        UNOGame g = gameMgr.createGame(gamename);
        String jsonInString = gson.toJson(g);
        System.out.print("create game post");
        return Response.ok(jsonInString).build();
    }

    /**
     * Retrieves representation of an instance of resource.GameResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/viewall")
    @Produces(MediaType.APPLICATION_JSON)
    public Response seeAllGame() {
        //TODO return proper representation object
        return Response.ok(gameMgr.getAllGamesJSon()).build();

    }

    /**
     * PUT method for updating or creating an instance of GameResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
