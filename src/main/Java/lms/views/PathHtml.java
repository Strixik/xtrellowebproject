package lms.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Singleton. Holds path object to html folder
 */
public class PathHtml {
    private static Logger log = Logger.getLogger(PathHtml.class.getName());
    private String path = "";
    private static PathHtml ourInstance = new PathHtml();

    public static PathHtml getInstance() {
        return ourInstance;
    }

    private PathHtml() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPartialHtml(String filename) {
        StringBuilder strb = new StringBuilder();
        Path file = Paths.get(this.path + filename);
        Charset charset = Charset.forName("UTF-8");

        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                strb.append(line).append("\n");
            }
        } catch (IOException e) {
            log.warning("Can't find html file: " + e.toString());
        }

        return strb.toString();
    }
}
