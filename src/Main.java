import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("""
                \nThis program will count all latin and cyrillic symbols met in a file.
                \u001B[36mYou do not need to specify result file's extension, results will be found in /results/.\u001B[0m\s""");

        Scanner in = new Scanner(System.in);
        try {
            Path resultsDirectory = Path.of("results");
            if (!Files.exists(resultsDirectory)) Files.createDirectory(resultsDirectory);
        } catch (IOException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        while (true) {
            try {
                // OPENING A FILE
                System.out.println("\n\nEnter filepath for analysis: ");
                Path filePath = Path.of(in.nextLine());
                FileInfo.checkExistence(filePath);

                System.out.print("\nEnter filename for results: ");
                Path resultsPath = Path.of("results\\" + in.nextLine() + ".txt");
                if (Files.exists(resultsPath)) throw new RuntimeException(resultsPath + " already exists!");
                Files.createFile(resultsPath);

                // PERFORM CHECKS
                FileInfo.checkFileInfo(filePath, resultsPath);

                // EXIT
                System.out.print("\nDo you want to exit? (1/0) ");
                int flag = in.nextInt();
                if (flag == 1) break;
                else in.nextLine();
            } catch (RuntimeException | IOException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        }
    }
}