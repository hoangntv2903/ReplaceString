import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class main {
    private  static String FILE_NAME_JSP_LST = "/home/nguyen.t.v.hoang/develop/Docs/update_UTF8.txt";
    private  static String FOLDER_PATH__JSP_REPLACE = "/home/nguyen.t.v.hoang/develop/EXCH_PJ/Debug/Live/backyard/src/main/webapp/jsp";
    private static List<String> fileName_Updated_UTF_8 = new ArrayList<String>();
    private static int countFileUpdate = 0;
    private static List<String> fileName_Updated_UTF_82 = new ArrayList<String>();
    private static int countFileUpdate2 = 0;
    public static void main(String[] args) {
        System.out.println("start replace");

        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME_JSP_LST))) {

            stream.forEach(main::replace);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end replace");

        Stream<String> streamFileName = fileName_Updated_UTF_8.stream();
        System.out.println("File Updated UTF8: START");
        System.out.println("COUNT: " + fileName_Updated_UTF_8.size());
        streamFileName.forEach(System.out::println);
        System.out.println("File Updated UTF8: END");
        System.out.println("Count file updated: " + countFileUpdate);

        System.out.println("COUNT22: " + fileName_Updated_UTF_82.size());
        System.out.println("Count file updated2: " + countFileUpdate2);
    }

    private static void replace(String fileName) {
        if (fileName.contains(".jsp")) {
            replace2(fileName);
        }
    }

    private static void replace2(String fileName) {
        File root = new File(FOLDER_PATH__JSP_REPLACE);
        boolean recursive = true;

        try {
            String[] extensions = {"jsp"};
            Collection files = FileUtils.listFiles(root, extensions, recursive);

            for (Iterator iterator = files.iterator(); iterator.hasNext();) {
                File file = (File) iterator.next();
                if (file.getAbsolutePath().contains(fileName)) {
                    try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()), Charset.forName("EUC-JP"))) {

                       List<String> replaced = stream
                                .map(line-> line.replaceAll("EUC-JP", "UTF-8"))
                                .collect(Collectors.toList());
                        Files.write(Paths.get(file.getAbsolutePath()), replaced);
                        countFileUpdate++;

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        fileName_Updated_UTF_8.add(file.getAbsolutePath());
                    }
                    try (Stream<String> stream2 = Files.lines(Paths.get(file.getAbsolutePath()), Charset.forName("UTF-8"))) {

                        List<String> replaced = stream2
                                .map(line-> line.replaceAll("EUC-JP", "UTF-8"))
                                .collect(Collectors.toList());
                        Files.write(Paths.get(file.getAbsolutePath()), replaced);
                        countFileUpdate2++;

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        fileName_Updated_UTF_82.add(file.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
