import static org.junit.Assert.*;
import org.junit.*;
import java.net.URI;
import java.net.URISyntaxException;
public class DocSearchTest {
    @Test
    public void testHandler() throws URISyntaxException {
        Handler h = new Handler();
        URI home = new URI("");
        assertEquals(h.handleRequest(home), new String ("Don't know how to handle that path!"));
    }
}
