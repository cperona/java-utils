package n1;

import java.util.Comparator;
import java.nio.file.Path;

public class FileNameComparator implements Comparator<Path> {
    @Override
    public int compare(Path o1, Path o2) {
        return o1.getFileName().compareTo(o2.getFileName());
    }
}
