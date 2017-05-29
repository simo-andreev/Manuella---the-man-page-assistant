
import java.util.Scanner;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ManuellaRegexR{

    private static boolean isVerbose = false;
    private static final String OUTPUT_FILE = "mauellaTitles";
    private static final String REGEX = "^[\\w\\.\\:]+";

    public static void main(String ... args){

        Scanner sc = new Scanner(System.in);
        Pattern regPat = Pattern.compile(REGEX);

	//TODO - add logic verfing and extracting params from cli(args)
        File allMan = new File(args.length > 1 ? args[1] : args[0]);
        
	//TODO - check that the file is valid.

        Scanner reader;
        try {
            reader = new Scanner(allMan);
        } catch ( FileNotFoundException e ) { //TODO - on FnF request new file or maybe even start the file-creating script?
            System.out.println("FILE NOT FOUND! EXITING...!");
            return;
        }

        
        File manTitles = new File(OUTPUT_FILE);

        if(!manTitles.canWrite()){
            System.out.println("I don't have permission to to do 'mah job! I'm leaving!");
            System.out.println("exiting...");
            return;
        }

	if(manTitles.exists()){
            System.out.println("The output file already exixts! Do you want to delete it? [Y/n]");

            if(sc.next().charAt(0) == 'n' || sc.next().charAt(0) == 'N'){
                System.out.println("'mkay then. Leavving. Please restart me when you are ready to commit to the goal.");
                System.out.println("exiting...");  //TODO - again.. take user input instead of bitiching out and exiting. 
                return;
            }
            
            manTitles.delete();
        }

        FileWriter writer;
        try{
            manTitles.createNewFile();
            writer = new FileWriter(manTitles);
        }catch(IOException e){
            System.out.println("File creation failed. Aborting...");
            return;
        }

        
        String old = "";
        while(reader.hasNext()){
            String line = reader.nextLine();
            Matcher m = regPat.matcher(line);
            if(m.matches()){
                String title = m.group().trim();
                if(title.length() < 2 || title.equals(old)) continue;

                old = title;
                try{
                    writer.write(title);
                }catch(IOException e){
                    continue; //TODO - handle properly or just declare;
                }
            }
        }
    }
}
