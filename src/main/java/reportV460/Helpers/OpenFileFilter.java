package reportV460.Helpers;

import java.io.File;
import javax.swing.filechooser.*;

public class OpenFileFilter extends FileFilter {

    String description = "";
    String fileExt = "";

    public OpenFileFilter(String extension) {
        super();
        fileExt = extension;
    }

    public OpenFileFilter(String extension, String typeDescription) {
        super();
        fileExt = extension;
        this.description = typeDescription;
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        return (f.getName().toLowerCase().endsWith(fileExt));
    }

    @Override
    public String getDescription() {
        return description;
    }

}
