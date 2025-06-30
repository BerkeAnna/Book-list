public class Main {
    public static void main(String[] args) {

        String inputFile = "bookdatas.txt";
        String outputFile = "li_elements.txt";

        String[] items =  FileReading.readFile(inputFile);
        FileReading.writeToFile(items, outputFile);

    }
}