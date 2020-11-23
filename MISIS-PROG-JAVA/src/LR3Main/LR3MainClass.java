package LR3Main;

import java.io.*;
import java.util.Scanner;
import java.util.zip.*;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LR3MainClass
{
    public static void main(String[] args) throws Exception
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Укажите путь к папке/файлу:");
        String filePath = scanner.nextLine();
        System.out.println("Укажите путь к архиву:");
        String zipPath = scanner.nextLine();

        FileOutputStream fileStream = new FileOutputStream(zipPath);
        ZipOutputStream zipStream = new ZipOutputStream(fileStream);
        archive(zipStream, new File(filePath), null);

        zipStream.flush();
        fileStream.flush();
        zipStream.close();
        fileStream.close();

        System.out.println("Данные успешно заархивированы");
    }
    public static void archive(ZipOutputStream zipStream, File fileToArchive, String fileParentDir) throws Exception
    {
        if (fileToArchive == null || !fileToArchive.exists()) return;

        String zipEntryName = fileToArchive.getName();

        if (fileParentDir!=null && !fileParentDir.isEmpty()) zipEntryName = fileParentDir + "/" + fileToArchive.getName();

        if (fileToArchive.isDirectory()) for (File file : fileToArchive.listFiles()) archive(zipStream, file, zipEntryName);
        else
            {
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(fileToArchive);
                zipStream.putNextEntry(new ZipEntry(zipEntryName));

                int length;
                while ((length = fis.read(buffer)) > 0) zipStream.write(buffer, 0, length);
                zipStream.closeEntry();
                fis.close();
            }
    }
}