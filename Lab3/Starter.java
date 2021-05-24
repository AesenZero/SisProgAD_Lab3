import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

class Starter
{
    public static void main(String[] args) throws IOException
    {
        
        String textToCheck;
        TextChecker TC = new TextChecker();  
        textToCheck = new String(Files.readAllBytes(Paths.get("input.txt")));
        TC.check(textToCheck);
    }
}