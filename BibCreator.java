
// Assignment 3
// Written by: Zhang Bo, ID:40108654
// -----------------------------------------------------

package assignment3;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 
 * @author maybe
 * @ver
 */
// This program could change a BIB file into other three format files: ACM, IEEE,and NJ.
public class BibCreator {
	
/**
 * 
 * @
 *
 */
    static class FileInvalidException extends Exception {

        public FileInvalidException() {
            super("Error: Input file cannot be parsed due to missing information (i.e. month={}, title={}, etc.) ");
        }
           
        public FileInvalidException(String s,String emptyFile) {
           
            System.out.println("Error: Detected Empty Field!");
            System.out.println("============================");
            System.out.println();
            System.out.println("Problem detected with input file: "+s);
            System.out.println("File is Invalid: Field '"+emptyFile+"' is Empty. Processing stoped at this point.Other EMPTYfield may be present as well!");
        }

    }
   //use transfered scanner to open giving file.
    public static void openFile(Scanner sc,String file) {
        try {
           
            sc=new Scanner(new FileInputStream((file)));
           
       
       }catch(FileNotFoundException e){
        System.out.println("Could not open input file "+file+
                "  for reading. Please check if file exists! Program will terminate after closing any opened files.");
           sc.close();
        System.exit(0);
       }
       
    }
    //create three other format files by giving file
    public static void createFile(PrintWriter pw1,PrintWriter pw2,PrintWriter pw3,int i) {
        try {
            pw1=new PrintWriter(new FileOutputStream("IEEE"+i+".jason"));
            pw2=new PrintWriter(new FileOutputStream("ACM"+i+".jason"));
            pw3=new PrintWriter(new FileOutputStream("NJ"+i+".jason"));
            pw1.close();
            pw2.close();
            pw3.close();
        }catch(IOException e) {
            System.out.println("File Latex"+i+".BIB can't be opened/created! Program will terminate after closing any opened files.");
            pw1.close();
            pw2.close();
            pw3.close();
            System.exit(0);           
        }
    }
    //check if the giving file is valid.
    
    //return true if file is valid.
    public static boolean fileValidation(Scanner sc,String file) {
        boolean valid=true;
        String emptyFile=null;    
        String str=null;
        try {
            sc=new Scanner(new FileInputStream((file)));
           
            while(sc.hasNextLine()) {
                str=sc.nextLine();
                 if(str.contains("{}")) {
                     emptyFile=str.substring(0,str.indexOf("{")-1);
                       valid=false;
                    
                     throw new FileInvalidException(file,emptyFile);
                 }
            }                       
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileInvalidException e) {
           System.out.println();
        }
        sc.close();
        return valid;
    }
    //use valid giving file to get the useful information for other files according the format.
    public static void processFilesForValidation(Scanner sc,int i) {
        String author=null;String journal=null;String title=null;String year=null;
        String volume=null;String number=null;String pages=null;String DOI=null;String month=null;
               
        try {
            sc = new Scanner(new FileInputStream("Latex"+i+".BIB"));
            while(sc.hasNextLine()) {      
               String str;
               str=sc.nextLine();
               if(str.contains("author")) {
                     author=str.substring(str.indexOf("{")+1,str.indexOf("}"));}
               else if (str.contains("journal")) {
                     journal=str.substring(str.indexOf("{")+1,str.indexOf("}"));}
               else if (str.contains("title")) {
                     title=str.substring(str.indexOf("{")+1,str.indexOf("}"));}
               else if (str.contains("year")) {
                     year=str.substring(str.indexOf("{")+1,str.indexOf("}"));}
               else if (str.contains("volume")) {
                     volume=str.substring(str.indexOf("{")+1,str.indexOf("}"));}
               else if (str.contains("number")) {
                     number=str.substring(str.indexOf("{")+1,str.indexOf("}"));}
               else if (str.contains("pages")) {
                     pages=str.substring(str.indexOf("{")+1,str.indexOf("}"));}
               else if (str.contains("doi")) {
                     DOI=str.substring(str.indexOf("{")+1,str.indexOf("}"));}
               else if (str.contains("month")) {
                     month=str.substring(str.indexOf("{")+1,str.indexOf("}"));}
               else if (str.equals("}")) {
               writeFiles(sc,i,author,title,journal,volume,pages,year,month,DOI,number);}
                   
         }
        }catch(Exception e){}
    }
    /**
     * 
     *@param
     */
    //write in the created files.
    public static void writeFiles(Scanner sc,int i,String author,String title,String journal,String volume,String pages,String year,String month,String DOI,String number) {
        PrintWriter pw1=null;
        PrintWriter pw2=null;
        PrintWriter pw3=null;
        try {

            int count=1;
               
                      pw1=new PrintWriter(new FileOutputStream("IEEE"+i+".jason",true));
                      pw2=new PrintWriter(new FileOutputStream("ACM"+i+".jason",true));
                       pw3=new PrintWriter(new FileOutputStream("NJ"+i+".jason",true));
                    
                     pw1.println(author+". "+"\""+ title+"\", "+journal+", "+"vol."+volume+", "+"no."+number+", "+"p."+pages+", "+month+year+". "+"\n");
                     if(author.contains("and")) {
                          pw2.println("["+count+"]"+author.substring(0, author.indexOf(" and"))+" et al. "+year+". "+ title+". "+journal+". "+volume+", "+"("+year+"), "+pages+", "+"p."+pages+". DOI:https://doi.org/"+DOI+". "+"\n");
                          count++;}
                     else {pw2.println("["+count+"]"+author+year+". "+ title+". "+journal+". "+volume+", "+"("+year+"), "+pages+", "+"p."+pages+". DOI:https://doi.org/"+DOI+". "+"\n");
                          count++;}
                  
             
              pw3.println(author+". "+ title+". "+journal+". "+volume+", "+pages+"("+year+"). "+"\n");
              pw1.flush();
              pw2.flush();
              pw3.flush();

           
        }catch(FileNotFoundException e) {
            System.out.println("Problem opening files. Cannot proceed to copy.");System.exit(0);}
    }
    //display the file user choosed.exist the program after.
    public static void readFiles(BufferedReader br, String filename) throws IOException  {
        
        int x = 0;     
        
          br=new BufferedReader(new FileReader(filename));
          
          System.out.println("Here are the contents of the successfully created Jason fill: "+filename);
          x = br.read();
            while(x != -1)
             {
              System.out.print((char)x);        // MUST CAST; otherwise all what is read will be shown as integers
              x = br.read();        
             }

              br.close();
               System.out.println("Goodbye! Hope you have enjoyed creating the needed files using BibCreator.");
               System.exit(0);
          }
               
    
    public static void main(String[] args) {
        
     System.out.println("Welcome to Bibcreator! ");
     System.out.println("Created by Zhang Bo \n");
        Scanner sc=null;
        int i=1;
        int validfile=0;
        for(;i<11;i++) {
           String file="Latex"+i+".BIB";
           BibCreator.openFile(sc,file );     
        }
       
        PrintWriter pw1=null;
        PrintWriter pw2=null;
        PrintWriter pw3=null;
        for(i=1;i<11;i++) {
           BibCreator.createFile(pw1, pw2, pw3, i);
        }
        for(i=1;i<11;i++) {
               String file="Latex"+i+".BIB";
               // boolean x=
                if(fileValidation(sc,file)) {
                    processFilesForValidation(sc,i);
                 validfile++;
                }
                else {
                   
                    File file1=new File("IEEE"+i+".jason");
                  File file2=new File("ACM"+i+".jason");
                  File file3=new File("NJ"+i+".jason");
                   file1.delete();
                  file2.delete();
                  file3.delete();
                }           
           
        }
        int invalidfile=10-validfile;
        System.out.println("A total of "+invalidfile+" files were invalid, and couldn't be processed. All other "+validfile+" \"Valid\" files have been created!\n");
       
       int trytime=1;
       do{
        System.out.println("Pls enter the name of one of the files that you need to review:");
        Scanner input=new Scanner(System.in);
          String filename=input.nextLine();
          BufferedReader br = null;
          try {
        
            readFiles(br,filename);
    
        }catch (FileNotFoundException e) {
            if(trytime<2) {System.out.println("Can not open input file. File does not exist; possibly it could not be created");
            System.out.println("However, you will allowed another chance to enter another file name.\n");    
            trytime++;}
            else {
            System.out.println("Could not open input file gain! Either file does not exist or could not be created.\n");
            System.out.println("Sorry ! Iam unable to display your desired files! Program will exit!");
            System.exit(0);
                
            }
                }
      
           catch (IOException e) {            
                System.out.println("Error: An error has occurred while reading from the file. ");
           System.out.println("Program will terminate.");
           System.exit(0);}
       }while(true);
    }
   
    
}

