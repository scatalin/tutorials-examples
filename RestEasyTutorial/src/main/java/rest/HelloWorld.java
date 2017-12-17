package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * #Path: defines the relative path of the resource, clients access the resource through concatenating
 * the value of the @Path annotation with the REST mapping URI, so this resource would process requests
 * like: /rest/helloWorld
 */
@Path("helloWorld")
public class HelloWorld {

    /**
     * #GET: defines a method which processes GET requests.
     * #Produces(MediaType.TEXT_PLAIN): the meta type of the returned result, in this case it’s plain text.
     *
     * @return result of this method call
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from programmer gate!";
    }
}