package n1.act1.order_directory_alphabetically;

public class Main {
    public static void main(String[] args) {
        FileUtils fileUtils = new FileUtils();
        
        // List directory
        fileUtils.listDirectoryAlphabetically("/");

        // List directory tree with details
        fileUtils.listDirectoryTreeWithDetails("src");

        // List directory tree with details and write the result into a file
        fileUtils.listDirectoryTreeWithDetailsToFile("src", "directory_tree.txt");
        
        // Read text file
        fileUtils.readTextFile("example_text_file.txt");
        
        // Serialize and deserialize an object

        System.out.println("\nSerialization Example: ");
        
        // 1 - Create a Person object
        Person person = new Person("John Doe", 30, "john@example.com");
        System.out.println("Original object: " + person);
        
        // 2 - Serialize the object
        fileUtils.serializeObject(person, "person.ser");
        
        // 3 - Deserialize the object
        Object deserializedObj = fileUtils.deserializeObject("person.ser");
        if (deserializedObj instanceof Person) {
            Person deserializedPerson = (Person) deserializedObj;
            System.out.println("Deserialized object: " + deserializedPerson);
        }
    }
}

