import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String DATA_FOLDER = "data/";

    public static List<String> readAll(String fileName) {
        List<String> lines = new ArrayList<>();

        try {
            File file = new File(DATA_FOLDER + fileName);

            if (!file.exists()) {
                file.createNewFile();
                return lines;
            }

            BufferedReader reader = new BufferedReader(
                    new FileReader(file)
            );

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return lines;
    }

    public static void appendLine(String fileName, String line) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(DATA_FOLDER + fileName, true)
            );

            writer.write(line);
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void writeAll(String fileName, List<String> lines) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(DATA_FOLDER + fileName)
            );

            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}