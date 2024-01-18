import java.io.*;
import java.util.*;

public class Start
{
    public static void main(String[] args)
	{
        if (args.length != 1 || !((new File(args[0]))).isDirectory()) {
            System.out.println("Error: provid the part of directory");
            System.exit(1);
        }

        String root = args[0];

		DirLister d = new DirLister(root);

//        DirLister d = new DirLister("/home/fintorgellert/images");

        d.walk(root);
//        d.walk("/home/fintorgellert/images");

        System.out.println("\n" + "-".repeat(40) + "\n");

        List<List<String>> t = d.getTree();

        for (int i = 0; i < t.size(); i++) {
            HTMLBuilder h = new HTMLBuilder(new File(root), t.get(i), ((i == 0) ? t.get(0) : t.get(i - 1)));
//            HTMLBuilder h = new HTMLBuilder(new File("/home/fintorgellert/images"), t.get(i), ((i == 0) ? t.get(0) : t.get(i - 1)));
            h.indexHTML();
            h.imageHTML();
        }
	}
}