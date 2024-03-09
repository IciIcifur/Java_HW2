import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInfo {
    static void checkExistence(Path path) {
        if (!Files.exists(path)) throw new RuntimeException(path + " doesn't exist!");
        if (!Files.isRegularFile(path)) throw new RuntimeException(path + " is not a file!");
    }

    static void checkFileInfo(Path path, Path results) {
        try {
            String content = Files.readString(path);
            System.out.println(content);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading from " + path);
        }
    }
}
