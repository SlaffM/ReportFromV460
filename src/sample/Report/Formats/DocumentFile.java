package sample.Report.Formats;

public class DocumentFile {
    private String name;
    private String extension;

    public DocumentFile(String name) {
        this.name = name;
        this.extension = name.substring(name.indexOf(".") + 1);
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }
}
