import java.io.*;
import java.util.*;

class HTMLBuilder
{
    private List<String> files;
    private List<String> previous;
    private List<File> directories = new ArrayList<>();
    private List<File> images = new ArrayList<>();
    private File home;

    public HTMLBuilder(File root, List<String> file, List<String> previous)
    {
        this.files = file;
        this.previous = previous;
        this.home = root;

        Collections.sort(this.files);

        directories.addAll(MyUtils.selectDirectories(this.files));
        images.addAll(MyUtils.selectImages(this.files));
    }

    public void indexHTML()
    {
        String file = new String();
        String title = new String();

        if (MyUtils.isNotEmpty(this.images))
        {
            file = MyUtils.indexHtmlName(this.images.get(0).toString());
            title = MyUtils.indexHTMLTitle(images, "image");
        }
        else if (MyUtils.isNotEmpty(this.directories)) {
            file = MyUtils.indexHtmlName(directories);
            title = MyUtils.indexHTMLTitle(directories, "directory");
        }
        else if (this.directories.size() == 0) {
            title = "empty";
            if ((new File(this.previous.get(0))).isFile()) {
                file = MyUtils.indexHtmlNameEmpty(previous, "image");
            } else {
                file = MyUtils.indexHtmlNameEmpty(previous, "directory");
            }
        } else {
            file = this.home.toString() + "/index.html";
            title = "Home";
        }

        File backToHome = MyUtils.link(this.home.toString(), file.substring(0, file.lastIndexOf('/')));

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<title>" + title + "</title>\n" +
                    "\t</head>\n" +
                    "\t<body>\n" +
                    "\t\t<a href=\"" + backToHome + "\">\n" +
                    "\t\t\t<h1>Start Page </h1>\n" +
                    "\t\t</a>\n" +
                    "\t\t<hr size=2 width=100%>\n" +
                    "\t\t<h1> Dictectory: </h1>\n" +
                    "\t\t<ul>\n");

        if (MyUtils.isNotHome(home, images, directories)) {
            File dirBackLink = new File("../index.html");
                html.append("\t\t\t<a href=\"" + dirBackLink + "\">\n" +
                            "\t\t\t\t<li> << </li>\n" +
                            "\t\t\t</a>\n");
        }

        for (int i = 0; i < this.directories.size(); i++) {
            String dirName = this.directories.get(i).getName();
            File dirLink = new File(this.directories.get(i).getName() + "/index.html");
            html.append("\t\t\t<li>" + "<a href=\"" + dirLink + "\">" + dirName + "</a>" + "</li>\n");
        }

        html.append("\t\t</ul>\n" +
                    "\t\t<hr size=2 width=100%>\n" +
                    "\t\t<h1> Images: </h1>\n" +
                    "\t\t<ul>\n");

        for (int i = 0; i < this.images.size(); i++) {
            String imageName = this.images.get(i).getName();
            File imageLink = MyUtils.imageHtmlLink(this.images.get(i).toString());
            html.append("\t\t\t<li>" + "<a href=\"" + imageLink + "\">" + imageName + "</a>" + "</li>\n");
        }

        html.append("\t\t</ul>\n" +
                    "\t</body>\n" +
                    "</html>\n");

        try {
            FileWriter out = new FileWriter(file);
            out.write(html.toString());
            out.close();
            System.out.printf("Successfully wrote to the directory. \t(%s)\n",file);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void imageHTML()
    {
        for (int i = 0; i < this.images.size(); i++) {
            File image = new File(this.images.get(i).toString().split("/")[this.images.get(i).toString().split("/").length -1]);
            String file = MyUtils.imageHtmlName(this.images.get(i).toString());
            String title = MyUtils.imageHtmlTitle(image);
            File dirBackLink = new File("index.html");
            File linkBack = MyUtils.imageHtmlLink(((i != 0) ? this.images.get(i - 1).toString() : this.images.get(i)).toString());
            File linkNext = MyUtils.imageHtmlLink(((i != this.images.size() - 1) ? this.images.get(i + 1) : this.images.get(i)).toString());
            File backToHome = MyUtils.link(this.home.toString(), this.images.get(i).toString().substring(0, this.images.get(i).toString().lastIndexOf('/')));

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "\t<head>\n" +
                        "\t\t<title> " + title + " </title>\n" +
                        "\t</head>\n" +
                        "\t<body>\n" +
                        "\t\t<a href=\"" + backToHome + "\">\n" +
                        "\t\t\t<h1> Start Page </h1>\n" +
                        "\t\t</a>\n" +
                        "\t\t<hr size=2 width=100%>\n" +
                        "\t\t<a href=\""+ dirBackLink + "\">\n" +
                        "\t\t\t<p> ^^ </p>\n" +
                        "\t\t</a>\n" +
                        "\t\t<h2> <a href=\"" + linkBack + "\"> << </a>" + image.getName() + "<a href=\"" + linkNext + "\"> >> </a> </h2>\n" +
                        "\t\t<a href=\"" + linkNext + "\">\n" +
                        "\t\t\t<img src=\"" + image + "\" width=\"30%\">\n" +
                        "\t\t</a>\n" +
                        "\t</body>\n" +
                        "</html>\n");
            try {
                FileWriter out = new FileWriter(file);
                out.write(html.toString());
                out.close();
                System.out.printf("Successfully wrote to the file. \t(%s)\n", file);
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}