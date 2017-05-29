import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManuellaRegexR {

    private static final String OUTPUT_FILE = "manuellaTitles";
    private static final String REGEX = "^[\\w\\.\\:]+";
    private static boolean isVerbose = true;

    public static void main(String... args) {

        Scanner fileReader;
        FileWriter fileWriter;
        String oldTitle = "";

        Scanner sc = new Scanner(System.in);
        Pattern pattern = Pattern.compile(REGEX);

        //TODO - add logic verfing and extracting params from cli(args)
        File allMan = new File(args[0]);  //(args.length > 1 ? args[1] : args[0]);

        //TODO - check that the file is valid.


        try {
            fileReader = new Scanner(allMan);
        } catch (FileNotFoundException e) { //TODO - on FnF request new file or maybe even start the file-creating script?
            System.out.println("FILE NOT FOUND! EXITING...!");
            return;
        }


        File manTitles = new File(args[1]);

        if (manTitles.exists()) {
            System.out.println("The output file already exixts! Do you want to delete it? [Y/n]");

            char ans = sc.next().charAt(0);
            if (ans == 'n' || ans == 'N') {
                System.out.println("'mkay then. Leavving. Please restart me when you are ready to commit to the goal.");
                System.out.println("exiting...");  //TODO - again.. take user input instead of bitiching out and exiting. 
                return;
            }

            manTitles.delete();
        }
        
        try {
            manTitles.createNewFile();
            fileWriter = new FileWriter(manTitles);
        } catch (IOException e) {
            System.out.println("File creation failed. Aborting...");
            return;
        }

        if(!manTitles.canWrite()){
            System.out.println("I don't have permission to to do 'mah job! I'm leaving!");
            System.out.println("exiting...");
            return;
        }


        while(fileReader.hasNext()){
            String line = fileReader.nextLine();

            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String match = matcher.group().trim();

                if (match.equals(oldTitle) || match.length() < 2) continue;

                oldTitle = match;

                if (isVerbose){
                    System.out.println(line);
                    System.out.println("----" + match);
                }
                try {
                    fileWriter.write(match + '\n');
                }catch (IOException e){
                    System.out.println("writing error");
                }
            }
        }
        System.out.println(manTitles);
    }
}
