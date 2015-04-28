import javax.swing.JFileChooser;
import java.io.File; 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import javax.swing.JFrame;

public class CodeMagnetParser {
   String title="";
   String description="";
   String problemType="";
   String className="";
   String functions="";
   String statements="";
   JFrame myFrame;

   private File openStandardFormatFile(String path){
      /*		JFileChooser fc = new JFileChooser(".");
            int result = fc.showOpenDialog(myFrame);
            if(result == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
            }
            System.out.println("unable to open file");
            return null;
            */
      try{
         return new File(path);
      } catch (Exception e){
         e.printStackTrace();
      }
      return null;
   }

   private String getKeyword(String statement){
      String[] splitString = statement.split("[^A-Za-z]",2);
      return splitString[0];
   }

   private void processStatement(String statement){
      if(statement.equals("") || statement.equals("}")) return;
      String keyword = getKeyword(statement);
      switch (keyword) {
         case "if":
            case "while":
            case "for":
            case "else": 
            statements = statements + statement + "<br /><!-- panel --><br />}.:|:.";
         break;

         default: statements = statements + statement + ".:|:.";

      }

   }

   private String trimComment(String statement)
   {
      if(statement.contains("//")) {
         statement = statement.substring(0,statement.indexOf("//"));
      }
      return statement;
   }

   private void parseFile(File standardFormatFile){
      try {
         BufferedReader r = new BufferedReader(new FileReader(standardFormatFile));
         String statement = trimComment(r.readLine()).trim();;
         if(statement.contains("/*")) { //process header block
            title = trimComment(r.readLine()).trim();;
            statement = trimComment(r.readLine()).trim();;
            while (!statement.contains("*/")) {
               description = statement + " ";
               statement = trimComment(r.readLine()).trim();;
            } 
         }

         do { // throw away blank lines
            statement = trimComment(r.readLine()).trim();;
         } while (statement.equals(""));

         // assumes next non-empty line is the method header
         functions = functions + statement + "<br /><!-- panel --><br />}.:|:.";
         do {
            statement = trimComment(r.readLine()).trim();;
            processStatement(statement);
         } while (r.ready());
         r.close();		
      } catch (Exception e){System.out.println("Exception is "+e);};
   }

   private void createCodeMagnets(String path) {
      File standardFormatFile = openStandardFormatFile(path);
      problemType = "basic_problem";
      className = "public class Student {<br /><!-- panel --><br />}";
      parseFile(standardFormatFile);
      if (title.equals("") ){
         title = "Parser did not recognize title";
      }
      System.out.println(title);
      if (description.equals("") ){
         description = "Parser did not recognize description";
      }
      System.out.println(description);
      if (problemType.equals("") ){
         problemType = "Parser did not recognize problemType";
      }
      System.out.println(problemType);
      if (className.equals("") ){
         className = "Parser did not recognize className";
      }
      System.out.println(className);
      if (functions.equals("") ){
         functions = "Parser did not recognize functions";
      }
      System.out.println(functions);
      if (statements.equals("") ){
         statements = "Parser did not recognize statements";
      }
      System.out.println(statements);
   }

   // Pull in the filename as a parameter
   public static void main(String[] args) {
      CodeMagnetParser myParser = new CodeMagnetParser();
      myParser.createCodeMagnets(args[0]);

   }

}
