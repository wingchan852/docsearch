import static org.junit.Assert.*;
import org.junit.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DocSearchTest {
    @Test
    public void testHandler() throws IOException, URISyntaxException {
        Handler handler = new Handler("./technical");
        URI uri = new URI("http://localhost/");
        // assertEquals()
    }
}
