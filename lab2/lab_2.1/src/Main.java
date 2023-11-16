import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final String defaultDisk = "D:";
    public static final String separator = "\\";
    public static final String resultFile = "result.txt";
    public static final String resultZip = "result.zip";

    public static void main(String[] args) throws Exception {
        diskOutput();
        System.out.println("Enter name of catalog:");
        Scanner in = new Scanner(System.in);
        String fileCatalog = in.nextLine();
        in.close();
        dirOutput(fileCatalog);
        zipFile(fileCatalog);
    }

    public static void diskOutput() {
        File disk = new File(defaultDisk + separator);

        if (disk.exists() && disk.isDirectory()) {
            System.out.println("Catalogs on your disk:");
            File[] dirs = disk.listFiles();
            for (int i = 0; i < Objects.requireNonNull(dirs).length; i++) {
                System.out.println(dirs[i].getPath());
            }
        }
    }

    public static void dirOutput(String fileCatalog) throws Exception {
        File dir = new File(defaultDisk + separator + fileCatalog);

        if (dir.exists() && dir.isDirectory()) {
            System.out.println("Catalog " + dir.getName() + ":");

            File[] files = dir.listFiles();
            String filePath = defaultDisk + separator + fileCatalog + separator + resultFile;

            PrintWriter writer = new PrintWriter(filePath);

            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                System.out.println(files[i].getPath());
                Scanner reader = new Scanner(files[i]);
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    writer.print(data);
                    writer.println();
                }
            }
            System.out.println("Clone file created: " + filePath);
            writer.close();
        }
    }

    public static void zipFile(String fileCatalog) throws Exception {
        String zipPath = defaultDisk + separator + fileCatalog + separator + resultZip;
        String filePath = defaultDisk + separator + fileCatalog + separator + resultFile;

        FileInputStream fis = new FileInputStream(filePath);
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath));
        ZipEntry ze = new ZipEntry(filePath);
        zos.putNextEntry(ze);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) != -1) {
            zos.write(buffer, 0, length);
        }
        System.out.println("Zip created: " + zipPath);
        fis.close();
        zos.close();

    }
}
