
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * #ApplicationPath defines the URL of the requests to be processed by RESTEasy classes.
 * In our example, all requests which are prefixed by /rest/* are processed by our service.
 */
@ApplicationPath("rest")
public class HelloWorldApplication extends Application {

    public HelloWorldApplication() {
    }

    /**
     * this method returns a set of resources to be loaded on the startup of the application,
     * if you want your application to load all RESTEasy resources then return an empty set as above.
     *
     * @return a set of Objects to be loaded at startup.
     */
    @Override
    public Set<Object> getSingletons() {
        HashSet<Object> set = new HashSet<Object>();
        return set;
    }
}