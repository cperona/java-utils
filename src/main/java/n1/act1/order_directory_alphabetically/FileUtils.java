package n1.act1.order_directory_alphabetically;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FileUtils {
    public FileUtils() {
    }

    public void listDirectoryAlphabetically(String route) {
        Path dir = Path.of(route);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            StreamSupport.stream(stream.spliterator(), false)
                    .sorted(new FileNameComparator())
                    .forEach(path -> {
                        System.out.println(path.getFileName());
                    });
        } catch (IOException | DirectoryIteratorException e) {
            System.out.println("Directory listing error.");
        }
    }
}
