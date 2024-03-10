import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class FileInfo {
    static void checkExistence(Path path) {
        if (!Files.exists(path)) throw new RuntimeException(path + " doesn't exist!");
        if (!Files.isRegularFile(path)) throw new RuntimeException(path + " is not a file!");
    }

    static void checkFileInfo(Path path, Path resultsPath) {
        try {
            int[] cyrillicResults = new int[256];
            int[] latinResults = new int[256];

            int count = 0;

            File file = new File(String.valueOf(path));
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                char[] line = scanner.nextLine().toCharArray();
                for (int symbol : line) {
                    count += 1;

                    if (symbol > 257) cyrillicResults[symbol - 1021] += 1;
                    else latinResults[symbol] += 1;
                }
            }
            scanner.close();

            Files.writeString(resultsPath, "Analyzed file: " + path + "\n\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            Files.writeString(resultsPath, count + " symbols at all\n\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            for (int i = 0; i < 256; i++) {
                if (latinResults[i] > 0) {
                    if (i == 32)
                        Files.writeString(resultsPath, "space" + " - " + latinResults[i] + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                    else if (i == 9)
                        Files.writeString(resultsPath, "tab" + " - " + latinResults[i] + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                    else
                        Files.writeString(resultsPath, (char) i + " - " + latinResults[i] + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                }
            }
            Files.writeString(resultsPath, "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);

            for (int i = 0; i < 256; i++) {
                if (cyrillicResults[i] > 0)
                    Files.writeString(resultsPath, (char) (i + 1021) + " - " + cyrillicResults[i] + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading from " + path + ": " + e);
        }
    }
}