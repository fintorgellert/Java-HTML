import java.io.*;
import java.util.*;
import java.nio.file.*;

class DirLister
{
    private List<List<String>> tree = new ArrayList<>();

    public DirLister(String dir) {
		try {
            Files.walk(Paths.get(dir)).filter(Files::isDirectory).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void walk(String path)
    {
        List<String> names = new ArrayList<>();
        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath());
                names.add(f.getAbsoluteFile().toString());
            }
            else {
                if (MyUtils.isImage(f, ".jpg") || MyUtils.isImage(f, ".jpeg") || MyUtils.isImage(f, ".png")) {
                    names.add(f.getAbsoluteFile().toString());
                }
            }
        }

        tree.add(names);
    }

    public List<List<String>> getTree() {
        return this.tree;
    }
}