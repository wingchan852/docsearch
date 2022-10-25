import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class FileHelpers {
    static List<File> getFiles(Path start) throws IOException {
        File f = start.toFile();
        List<File> result = new ArrayList<>();
        if (f.isDirectory()) {
            System.out.println("It's a folder");
            File[] paths = f.listFiles();
            for (File subFile : paths) {
                result.addAll(getFiles(subFile.toPath()));
            }
        } else {
            result.add(start.toFile());
        }
        return result;
    }

    static String readFile(File f) throws IOException {
        System.out.println(f.toString());
        return new String(Files.readAllBytes(f.toPath()));
    }
}

class Handler implements URLHandler {
    List<File> files;

    Handler(String directory) throws IOException {
        this.files = FileHelpers.getFiles(Paths.get(directory));
    }

    public String handleRequest(URI url) throws IOException {
        List<File> paths = FileHelpers.getFiles(Paths.get("./technical"));
        if (url.getPath().equals("/")) {
            return String.format("There are %d files to searach", paths.size());
        } else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("q")) {
                List<String> foundPaths = new ArrayList<>();
                for (int i = 0; i < paths.size(); i++) {
                    if (FileHelpers.readFile(paths.get(i)).contains(parameters[1])) {
                        foundPaths.add(paths.get(i).toString());
                    }
                }
                return String.format("There are %d files found:%s", foundPaths.size(), foundPaths);
            }
        }
        return "Don't know how to handle that path!";
    }
}

class DocSearchServer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler("./technical/"));
    }
}
