package FINAL;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FinalMainClass
{
    public static void main(String[] args) throws IOException {
        //Обозначение путей к файлам для чтения/записи
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Введите путь к базе Телефон-ФИО (для чтения):");
//        String telNamePath = scanner.nextLine();
//        System.out.println("Введите путь к базе Телефон-Комментарий (для чтения):");
//        String telCom = scanner.nextLine();
//        System.out.println("Введите путь к базе Телефон-ФИО-Комментарий (для записи):");
//        String telNameCom = scanner.nextLine();

        //Открытие потоков для работы с файлами
        FileInputStream telcomFileStream = new FileInputStream("src/FINAL/tel-com.txt");
        BufferedReader telcomBufferedReader = new BufferedReader(new InputStreamReader(telcomFileStream));
        FileInputStream telnameFileStream = new FileInputStream("src/FINAL/tel-name.txt");
        BufferedReader telnameBufferedReader = new BufferedReader(new InputStreamReader(telnameFileStream));
        FileOutputStream telnamecomFileStream = new FileOutputStream("src/FINAL/tel-name-com.txt");
        DataOutputStream telnamecomOutStream = new DataOutputStream(new BufferedOutputStream(telnamecomFileStream));

        //Создание регулярного выражения для поиска телефона
        Pattern telephone = Pattern.compile(".+\\d");

        //Создание списков из объектов
        ArrayList<String> telnameList = new ArrayList<String>();
        ArrayList<String> telcomList = new ArrayList<String>();
        
        String telnameLine;
        String telcomLine;
        while ((telnameLine = telnameBufferedReader.readLine()) != null)
        {
            Matcher telnameMatcher = telephone.matcher(telnameLine);
            if (telnameMatcher.find()) telnameList.add(telnameLine);
        }
        while ((telcomLine = telcomBufferedReader.readLine()) != null)
        {
            Matcher telcomMatcher = telephone.matcher(telcomLine);
            if (telcomMatcher.find())telcomList.add(telcomLine);
        }

        //Поиск записей в списках с идентичными номерами и формирование итогового файла
        telnamecomOutStream.writeUTF("Телефон - ФИО - Комментарий \n ----------------------------- \n");
        for (String telname : telnameList)
        {
            for (String telcom : telcomList)
            {
                if (telname.split(" - ")[0].equals(telcom.split(" - ")[0]))
                    telnamecomOutStream.writeUTF((telname + " - " + telcom.split(" - ")[1]).trim() + "\n");

            }
        }

        //Закрытие потоков для работы с файлами
        telnamecomOutStream.close();
        telcomFileStream.close();
        telnameFileStream.close();
    }
}
