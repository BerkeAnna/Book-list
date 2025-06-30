
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReading {
    public static String[] readFile(String filePath) {

    StringBuilder content = new StringBuilder();

    try (Scanner scanner = new Scanner(new File(filePath))) {
        while (scanner.hasNextLine()) {
            content.append(scanner.nextLine());
        }
    } catch (IOException e) {
        System.err.println("Error: " + e.getMessage());
    }

    ArrayList<String> spans = new ArrayList<>();
    String text = content.toString();
    int index = 0;
    boolean firstSpanSkipped = false;

    while ((index = text.indexOf("<span", index)) != -1) {
        int startTagEnd = text.indexOf(">", index);
        int endTag = text.indexOf("</span>", startTagEnd);
        if (startTagEnd == -1 || endTag == -1) break;

        String value = text.substring(startTagEnd + 1, endTag)
                .replaceAll("\\s+", " ")
                .trim();

        // ❌ Skip the first <span>...</span>
        if (!firstSpanSkipped) {
            firstSpanSkipped = true;
            index = endTag + 7;
            continue;
        }

        // Tisztítás
        if (value.toLowerCase().endsWith("- 0 ft")) {
            value = value.substring(0, value.length() - 6).trim();
        }

        if (value.toLowerCase().endsWith(" (puhatáblás)")) {
            value = value.substring(0, value.length() - 13).trim();
        }

        if (value.toLowerCase().endsWith(" (keménytáblás)")) {
            value = value.substring(0, value.length() - 15).trim();
        }

        spans.add(value);
        index = endTag + 7;
    }

    return spans.toArray(new String[0]);
}


     public static void writeToFile(String[] items, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            for (String item : items) {
                writer.write(item + System.lineSeparator());
            }
            System.out.println("Sikeresen kiírva a fájlba: " + outputPath);
        } catch (IOException e) {
            System.err.println("Hiba a fájl írása közben: " + e.getMessage());
        }
    }

    public static String toSlug(String input) {
    // Ékezetek eltávolítása
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "");

    // Nem alfanumerikus karakterek → kötőjel
    String slug = normalized.toLowerCase()
            .replaceAll("[^a-z0-9]+", "-") // minden nem betű/szám helyett -
            .replaceAll("^-+", "")        // elejéről levágja a felesleges kötőjelet
            .replaceAll("-+$", "")        // végéről is
            .replaceAll("-{2,}", "-");    // többszörös kötőjelek → 1 db

    return slug;
}
}