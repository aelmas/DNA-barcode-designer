//import java.util.*;
import java.io.*;

class StreamGobbler extends Thread
{
    InputStream is;
    String type;
    
    StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }
    
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
//            String line = br.readLine();
//            String line = null;
//            while ( (line = br.readLine()) != null){
            while ( br.readLine() != null){

            	//                System.out.println(type + ">" + line);
            }    
        } 
        catch (IOException ioe){
                ioe.printStackTrace();  
        }
    }
}