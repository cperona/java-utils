package n1.act1.order_directory_alphabetically;

public class Main {
    public static void main() {
        FileUtils fileUtils = new FileUtils();
        //fileUtils.listDirectoryAlphabetically("/");

        fileUtils.listDirectoryTreeWithDetails("src");
        fileUtils.listDirectoryTreeWithDetailsToFile("src", "directory_tree.txt");
        fileUtils.readTextFile("example_text_file.txt");
    }
}
