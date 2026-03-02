package n1.act1.order_directory_alphabetically;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class FileUtils {
    public FileUtils() {
    }

    public void listDirectoryAlphabetically(String route) {
        Path path = Path.of(route);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            StreamSupport.stream(stream.spliterator(), false)
                    .sorted(new FileNameComparator())
                    .forEach(p -> {
                        System.out.println(p.getFileName());
                    });
        } catch (IOException | DirectoryIteratorException e) {
            System.out.println("Directory listing error.");
        }
    }

    public void listDirectoryTreeWithDetails(String route) {
        Path path = Path.of(route);
        try {
            System.out.println(path.toAbsolutePath());
            listDirectoryTreeRecursive(path, "");
        } catch (IOException e) {
            System.out.println("Directory tree listing error: " + e.getMessage());
        }
    }

    public void listDirectoryTreeWithDetailsToFile(String route, String outputFile) {
        Path path = Path.of(route);
        try (BufferedWriter writer = Files.newBufferedWriter(
                Path.of(outputFile),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            
            writer.write(path.toAbsolutePath().toString());
            writer.newLine();
            listDirectoryTreeRecursiveToFile(path, "", writer);
            System.out.println("Directory tree saved to: " + Path.of(outputFile).toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving directory tree to file: " + e.getMessage());
        }
    }


    private void listDirectoryTreeRecursive(Path directory, String indent) throws IOException {
        List<Path> entries = new ArrayList<>();
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            StreamSupport.stream(stream.spliterator(), false)
                    .forEach(entries::add);
        }
        
        // Order alphabetically
        entries.sort(new FileNameComparator());
        
        for (Path entry : entries) {
            BasicFileAttributes attributes = Files.readAttributes(entry, BasicFileAttributes.class);

            String type;
            if (attributes.isDirectory()) {
                type = "D";
            } else {
                type = "F";
            }

            String lastModified = formatLastModifiedTime(attributes.lastModifiedTime());
            
            System.out.println(indent + type + " - " + entry.getFileName() + " (" + lastModified + ")");
            
            if (attributes.isDirectory()) {
                //Call the method recursively and add a space to indent
                listDirectoryTreeRecursive(entry, indent + "  ");
            }
        }
    }

    private void listDirectoryTreeRecursiveToFile(Path directory, String indent, BufferedWriter writer) throws IOException {
        List<Path> entries = new ArrayList<>();
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            StreamSupport.stream(stream.spliterator(), false)
                    .forEach(entries::add);
        }
        
        // Order alphabetically
        entries.sort(new FileNameComparator());
        
        for (Path entry : entries) {
            BasicFileAttributes attributes = Files.readAttributes(entry, BasicFileAttributes.class);

            String type;
            if (attributes.isDirectory()) {
                type = "D";
            } else {
                type = "F";
            }

            String lastModified = formatLastModifiedTime(attributes.lastModifiedTime());
            
            writer.write(indent + type + " - " + entry.getFileName() + " (" + lastModified + ")");
            writer.newLine();
            
            if (attributes.isDirectory()) {
                //Call the method recursively and add a space to indent
                listDirectoryTreeRecursiveToFile(entry, indent + "  ", writer);
            }
        }
    }

    private String formatLastModifiedTime(FileTime fileTime) {
        Instant instant = fileTime.toInstant();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }
}
