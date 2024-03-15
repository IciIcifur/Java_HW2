import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("""
                \nThis program will count all latin and cyrillic symbols met in a file.
                \u001B[36mYou do not need to specify result file's extension.\u001B[0m\s""");

        Scanner in = new Scanner(System.in);

        String resultsPath = "";
        try {
            // SELECTING A DIRECTORY FOR RESULTS
            System.out.println("\n\nWhere do you want to store results?");
            String resultsDirectory = in.nextLine();
            resultsPath = resultsDirectory + "/results";

            if (Files.exists(Path.of(resultsDirectory)) && !Files.exists(Path.of(resultsPath))) {
                Files.createDirectory(Path.of(resultsPath));
            } else
                throw new RuntimeException("Directory doesn't exist or already contains directory 'results'.");
        } catch (IOException | RuntimeException e) {
            System.out.println("\n\u001B[31m" + e.getMessage() + "\u001B[0m");
            System.exit(1);
        }

        while (true) {
            try {
                // OPENING A FILE
                System.out.println("\n\nEnter filepath for analysis: ");
                Path filePath = Path.of(in.nextLine());
                FileInfo.checkExistence(filePath);

                System.out.print("\nEnter filename for results: ");
                Path resultFile = Path.of(resultsPath + "/" + in.nextLine() + ".txt");
                if (Files.exists(resultFile)) throw new RuntimeException(resultFile + " already exists!");
                Files.createFile(resultFile);

                // PERFORM CHECKS
                FileInfo.checkFileInfo(filePath, resultFile);

                // EXIT
                System.out.print("\nDo you want to exit? (1/0) ");
                int flag = in.nextInt();
                if (flag == 1) break;
                else in.nextLine();
            } catch (RuntimeException | IOException e) {
                System.out.println("\n\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        }
    }
}