public class Main {
    public static void main(String[] args) {

        String inputFile = "bookdatas.txt";
        String outputFile = "li_elements.txt";
        String outputFileSlug = "li_elements_slug.txt";

        String[] items =  FileReading.readFile(inputFile);
        String[] itemsSlug =  FileReadingWithSlug.readFile(inputFile);
        FileReading.writeToFile(items, outputFile);
        FileReadingWithSlug.writeToFile(itemsSlug, outputFileSlug);

    }
}