package knowledge_base;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Class for read text file to data for DataSave2Dmatrix.java.
 * @author Ladislav Martinek
 */
public class ReadDataFile {
    String file_name;

    public ReadDataFile(String file_name) {
        this.file_name = file_name;
    }
   
    private BufferedReader get_file_buffer(){
        
        try {
            BufferedReader br;
            br = new BufferedReader(new FileReader(file_name));
            return br;
        } catch (FileNotFoundException ex) {
            System.err.println("Chyba při čtení souboru nenasel");
        }
         return null;
    }
    
    public List<NumberLineText> get_questions(){
        return getText("Q:", "C:");
    }
    
    private List<NumberLineText> getText(String firs_delimiter, String second_delimiter){
        try{
            BufferedReader br = get_file_buffer();
            List<NumberLineText> list = new LinkedList<>();
            String read = "";
            read = br.readLine();  
            while (true){
                if (read.equals(firs_delimiter)){
                    break;
                }
                read = br.readLine();
            }
            while (true){
                read = br.readLine();
                read = read.replaceAll("^\\s+","");
                if (read.equals("")){
                    continue;
                }
                if (read.equals(second_delimiter)){
                    break;
                }
                int res = new Scanner(read).useDelimiter("\\D+").nextInt();
                if (read.lastIndexOf("]")>0){
                    String text = read.substring(read.indexOf(" ") , read.indexOf("["));
                    Double probability = Double.parseDouble(read.substring(read.indexOf("[")+1 , read.indexOf("]")));
                    list.add(new NumberLineText(res, text, probability));
                }else{
                    String text = read.substring(read.indexOf(" ") + 1);
                    list.add(new NumberLineText(res, text));
                }
            }
            return list;
        }catch(IOException e){
            System.err.println("Chyba při čtení souboru");
        }
        return null;
    }
    
    public List<NumberLineText> get_conclusions(){
        return getText("C:", "R:");
    }
    public List<List<Integer>> get_relations(){
        try{
            BufferedReader br = get_file_buffer();
            List<List<Integer>> list = new LinkedList<>();
            String read = "";
            read = br.readLine();  
            while (true){
                if (read.equals("R:")){
                    break;
                }
                read = br.readLine();
            }
            while (true){
                List<Integer> list_integer= new LinkedList<>();
                
                
                read = br.readLine();
                if(read == null){
                    break;
                }
                read = read.replaceAll("^\\s+","");
                if (read.equals("")){
                    continue;
                }
                read = read.replaceAll("^\\s+","");
                String text = read.substring(0, read.indexOf(">"));
                text = text.replaceAll("\\s+","");
                read = read.substring(read.indexOf(">")+1);
                int res = Integer.parseInt(text);
                list_integer.add(res);
                while (read.length()!= 0){
                    read = read.replaceAll("^\\s+","");
                    String q_text = "";
                    try{
                        q_text = read.substring(0, read.indexOf(","));
                    }catch(StringIndexOutOfBoundsException e){
                        read = read.replaceAll("\\s+","");
                        res = Integer.parseInt(read); 
                        list_integer.add(res);
                        break;
                    }
                    q_text = q_text.replaceAll("\\s+","");
                    read = read.substring(read.indexOf(",")+1);
                    res = Integer.parseInt(q_text); 
                    list_integer.add(res);
                }                
                list.add(list_integer);
            }
            return list;
        }catch(IOException e){
            System.err.println("Chyba při čtení souboru");
        }
        return null;     
    }
    
    
}
