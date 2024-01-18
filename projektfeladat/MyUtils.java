import java.util.*;
import java.io.*;

public class MyUtils
{
    private MyUtils() {
        // üres konstruktor, nem példányosítható
    }

    public static String imageHtmlName(String f) {
        return f.replace(".jpg", ".html").replace(".png", ".html").replace(".jpeg", ".html").replace(".JPG", ".html").replace(".PNG", ".html").replace(".JPEG", ".html");
    }

    public static File imageHtmlLink(String f) {
        return new File((new File(imageHtmlName(f))).getName());
    }


    public static File link(String previous, String current) {
        int repeat =  current.split("/").length - previous.split("/").length;
        return new File("../".repeat(repeat) + "index.html");
    }

    public static String indexHtmlName(String f) {
        return (f.substring(0, f.lastIndexOf('/')) + "/index.html");
    }

    public static List<File> selectDirectories(List<String> files)
    {
        List<File> directories = new ArrayList<>();

        for (String s : files)
        {
            if (new File(s).isDirectory()) {
                directories.add(new File(s));
            }
        }
        return directories;
    }

    public static List<File> selectImages(List<String> files)
    {
        List<File> images = new ArrayList<>();

        for (String s : files) {
            if (new File(s).isFile()) {
                images.add(new File(s));
            }
        }
        return images;
    }

    public static String imageHtmlTitle(File image) {
        return image.getName().replace(".jpg", "").replace(".png", "").replace(".jpeg", "").replace(".JPG", "").replace(".PNG", "").replace(".JPEG", "");
    }

    public static boolean isNotHome(File home, List<File> images, List<File> directories) {
        return (images.size() != 0 && !images.get(0).isFile()) || (directories.size() != 0 && !directories.get(0).toString().substring(0, directories.get(0).toString().lastIndexOf('/')).equals(home.toString())) || (directories.size() == 0);
    }

    public static boolean isNotEmpty(List<File> files) {
        return files.size() != 0 && !files.get(0).toString().substring(0, files.get(0).toString().lastIndexOf('/')).equals(files.toString());
    }

    public static String indexHTMLTitle(List<File> files, String type)
    {
        if (type.equals("image")) {
            return (new File(MyUtils.indexHtmlName(files.get(0).toString()).substring(0, MyUtils.indexHtmlName(files.get(0).toString()).lastIndexOf('/')))).getName();
        } else {
            return (new File(files.get(0).toString().substring(0, files.get(0).toString().lastIndexOf('/')))).getName();
        }
    }

    public static String indexHtmlName(List<File> files) {
        return files.get(0).toString().substring(0, files.get(0).toString().lastIndexOf('/')) + "/index.html";
    }

    public static String indexHtmlNameEmpty(List<String> files, String type) {
        if (type.equals("image")) {
            return files.get(0).substring(0, files.get(0).lastIndexOf('/')).substring(0, files.get(0).substring(0, files.get(0).lastIndexOf('/')).lastIndexOf('/')) + "/empty/index.html";
        } else {
            return files.get(0).substring(0, files.get(0).lastIndexOf('/')) + "/empty/index.html";
        }
    }

    public static boolean isImage(File f, String extension) {
        return f.getAbsolutePath().toLowerCase().endsWith(extension);
    }
}