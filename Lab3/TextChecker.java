import java.util.HashSet;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Vector;
public class TextChecker 
{

    public TextChecker()
    {
        Initiate();
    }
    textCase curCase;
    String[] escSecAr = {"'\\a'", "'\\b'","'\\f'","'\\n'", "'\\r'", "'\\t'", "'\\v'", "'\\\\'", "'\\''", "'\\\"'","'\\?'","'\\0'" };
    Character[] escAr = {'a','b','f','n','r','r','v','\\','\'','"','?','0'};
    Character[] digAr = {'0', '1', '2', '3', '4', '5', '6','7','8','9'};
    Character[] alpAr = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','_'};
    Character[] lettersForHex = {'A','B','C','D','E','F'};
    String[] derectAr = {"#define", "#undef", "#include", "#if", "#ifdef", "#ifndef", "#else", "#elif","#endif","#line","#error","#pragma","#warning"};
    String[] reserAr = {"auto","asm","break","case","char","const","continue","cdecl","default","do","double","else","enum","extern","float","for","far","goto","if","huge","inline","int","interrupt","long","near","pascal","register","restrict","return","short","signed","sizeof","static","struct","switch","typedef","union","unsigned","void","volatile","while","_Alignas","-Alignof","_Atomic","_Bool","_Complex","_Generic","_Imaginary","_Noreturn","_Static_assert","_Thread_local"};
    Character[] delim = {' ', '\t', '\n'};


    ArrayList<String> intNumRes = new ArrayList<String>();
    ArrayList<String> floatNumRes = new ArrayList<String>();
    ArrayList<String> hexNumRes = new ArrayList<String>();
    ArrayList<String> charRes = new ArrayList<String>();
    ArrayList<String> stringRes = new ArrayList<String>();
    ArrayList<String> operRes = new ArrayList<String>();
    ArrayList<String> reserveRes = new ArrayList<String>();
    ArrayList<String> derevRes = new ArrayList<String>();
    ArrayList<String> puncRes = new ArrayList<String>();
    ArrayList<String> commRes = new ArrayList<String>();
    ArrayList<String> indRes = new ArrayList<String>();
    ArrayList<String> errRes = new ArrayList<String>();
    JTable Jt;
    JFrame Jf;
    String[] collumnNames = {"int","float","hex","char","string","operators","reserved","derev","comm","ind","punc","errors"};
    HashSet<String> escSec = new HashSet<String>();
    HashSet<Character> escLSet=new HashSet<Character>();
    HashSet<Character> digiSet = new HashSet<Character>();
    HashSet<Character> digiForOctoSet = new HashSet<Character>();
    HashSet<Character> alphaSet = new HashSet<Character>();
    HashSet<Character> hexLetSet = new HashSet<Character>();
    HashSet<String> derSet = new HashSet<String>();
    HashSet<String> reservSet = new HashSet<String>();
    HashSet<Character> delimSet = new HashSet<Character>();
    String curString;

    public Object resCheck(int i, ArrayList<String> t)
    {   
        if(i<t.size()) return t.get(i);
        return "";
    }
    public void Initiate()
    {
        for (int i = 0; i<escSecAr.length; i++ )
        {
            escSec.add(escSecAr[i]);
        }

        for (int i = 0; i<escAr.length; i++ )
        {
            escLSet.add(escAr[i]);
        }

        for (int i = 0; i<derectAr.length; i++ )
        {
            derSet.add(derectAr[i]);
        }

        for (int i = 0; i<reserAr.length; i++ )
        {
            reservSet.add(reserAr[i]);
        }

        for (int i = 0; i<digAr.length; i++ )
        {
            digiSet.add(digAr[i]);
        }

        for (int i = 0; i<lettersForHex.length; i++ )
        {
            hexLetSet.add(lettersForHex[i]);
        }

        for (int i = 0; i<alpAr.length; i++ )
        {
            alphaSet.add(alpAr[i]);
        }

        for (int i = 0; i<delim.length; i++ )
        {
            delimSet.add(delim[i]);
        }

        digiForOctoSet = digiSet;
        digiForOctoSet.remove('8');
        digiForOctoSet.remove('9');


    }
    public String getRes()
    {
        return curString;
    }

    public Boolean checkForHexPart(String s)
    {
        for (int i = 0; i<s.length(); i++)
        {
            if(!(digiSet.contains( s.charAt(i))||hexLetSet.contains( s.charAt(i))))return false;
        }
        return true;
    }

    public Boolean checkForOctoPart(String s)
    {
        for (int i = 0; i<s.length(); i++)
        {
            if(!(digiForOctoSet.contains( s.charAt(i))))return false;
        }
        return true;
    }
    public Boolean CharCase(String s)
    {
        
        if((s.length()==3 && s.charAt(1)!= '\\' && s.charAt(1)!= '\'')||escSec.contains(s))
        {
            return true;
        }
        String t1 = s.substring(1,3);
        String t2 = s.substring(1,4);
        String t3 = "\\0";
        String t4 = "\\0x";
        if(((t2.charAt(0)==t4.charAt(0) && t2.charAt(1)==t4.charAt(1)&& t2.charAt(2)==t4.charAt(2))))
        {
            String t = s.substring(4, s.length()-1);
            if(checkForHexPart(t)) return true;
        }  
        if((t1.charAt(0)==t3.charAt(0) && t1.charAt(1)==t3.charAt(1)))
        {
            String t = s.substring(3, s.length()-1);
            if(checkForOctoPart(t)) return true;
        }
             
        return false;
        
    }
    public int checkForChar(String s, int p, char c)
    {
        int temp = p;
        for(;temp<s.length();temp++)
        {
            if(s.charAt(temp) == c) return temp;
        }
        return -1;
    }
    public void check(String s)
    {
        int pointer = 0;
        String temp = "";
        curCase = textCase.startCheck;
        while (pointer < s.length())
        {
            switch (curCase) 
            {

                case startCheck:
                {   
                    switch(s.charAt(pointer))
                    {

                        case ' ':
                        case '\t':
                        case '\n':
                        case '\r':
                        default:
                        {
                            pointer++;
                        }break;
                        case '"':
                        {
                            if(pointer<s.length()-1)
                            {
                            temp+=s.charAt(pointer);
                            pointer++;
                            curCase = textCase.str;
                            break;
                            }
                            curCase= textCase.err;
                        }
                        
                        case '#':
                        {
                            if(pointer<s.length()-2)
                            {
                            temp+=s.charAt(pointer);
                            pointer++;
                            curCase = textCase.der;
                            break;
                            }else if(pointer<s.length()-1 && s.charAt(pointer+1)==' ')
                            {
                                temp+=s.charAt(pointer);
                                curCase = textCase.punc;
                            }
                        }

                        case '/':
                        {
                            if(pointer!=s.length()-1 && s.charAt(pointer+1)=='/')
                            {
                                temp+=s.charAt(pointer);
                                pointer++;
                                temp+=s.charAt(pointer);
                                pointer++;
                                curCase = textCase.comm;
                                break;
                            }
                            if(pointer<=s.length()-4 && s.charAt(pointer+1)=='*')
                            {
                                temp+=s.charAt(pointer);
                                pointer++;
                                temp+=s.charAt(pointer);
                                pointer++;
                                curCase = textCase.commBig;
                                break;
                            }
                            if(pointer<=s.length()-1|| (pointer!=s.length()-1&&(s.charAt(pointer+1)==' '||s.charAt(pointer+1)=='=')))
                            {
                                temp+=s.charAt(pointer);
                                if(pointer!=s.length()-1&&s.charAt(pointer+1)=='=') temp+=s.charAt(pointer+1);
                                pointer++;
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.punc;
                                break;
                            }
                            
                            curCase = textCase.err;
                        }

                        case '\'':
                        {
                            if(pointer<s.length()-1)
                            {
                            temp+=s.charAt(pointer);
                            pointer++;
                            curCase = textCase.chr;
                            break;
                            }
                            curCase= textCase.err;
                        }break;

                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        {
                            temp+=s.charAt(pointer);
                            pointer++;
                            curCase = textCase.numeric;
                        }break;
                        
                        case '(':
                        case ')':
                        case '[':
                        case ']':
                        case '{':
                        case '}':
                        case ',':
                        case ':':
                        case ';':
                        case '.':
                        {
                            temp+=s.charAt(pointer);
                            curCase = textCase.punc;
                        }break;

                        case '+':
                        {
                            if(pointer<=s.length()-1|| (pointer!=s.length()-1&&(s.charAt(pointer+1)==' '||s.charAt(pointer+1)=='='||s.charAt(pointer+1)=='+')))
                            {
                                temp+=s.charAt(pointer);
                                if(pointer!=s.length()-1&&(s.charAt(pointer+1)=='=' ||s.charAt(pointer+1)=='+')) {temp+=s.charAt(pointer+1);
                                pointer++;}
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                                break;
                            }
                        }
                        case '-':
                        {
                            if(pointer<=s.length()-1|| (pointer!=s.length()-1&&(s.charAt(pointer+1)==' '||s.charAt(pointer+1)=='='||s.charAt(pointer+1)=='-')))
                            {
                                temp+=s.charAt(pointer);
                                if(pointer!=s.length()-1&&(s.charAt(pointer+1)=='=' ||s.charAt(pointer+1)=='-')) {temp+=s.charAt(pointer+1);
                                pointer++;}
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                                break;
                            }
                        }
                        break;
                        case '*':
                        {
                            if(pointer<=s.length()-1|| (pointer!=s.length()-1&&(s.charAt(pointer+1)==' '||s.charAt(pointer+1)=='=')))
                            {
                                temp+=s.charAt(pointer);
                                if(pointer!=s.length()-1&&s.charAt(pointer+1)=='=') {temp+=s.charAt(pointer+1);
                                pointer++;}
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                                break;
                            }
                        }break;
                        case '%':
                        {
                            if(pointer<=s.length()-1|| (pointer!=s.length()-1&&(s.charAt(pointer+1)==' '||s.charAt(pointer+1)=='=')))
                            {
                                temp+=s.charAt(pointer);
                                if(pointer!=s.length()-1&&s.charAt(pointer+1)=='=') {temp+=s.charAt(pointer+1);
                                pointer++;}
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                                break;
                            }
                        }break;
                        case '!':
                        {
                            if(pointer<=s.length()-1|| (pointer!=s.length()-1&&(s.charAt(pointer+1)==' '||s.charAt(pointer+1)=='=')))
                            {
                                temp+=s.charAt(pointer);
                                if(pointer!=s.length()-1&&s.charAt(pointer+1)=='=') {temp+=s.charAt(pointer+1);
                                pointer++;}
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                                break;
                            }
                        }break;
                        
                        case '=':
                        {
                            if(pointer<=s.length()-1|| (pointer!=s.length()-1&&(s.charAt(pointer+1)==' '||s.charAt(pointer+1)=='=')))
                            {
                                temp+=s.charAt(pointer);
                                if(pointer!=s.length()-1&&s.charAt(pointer+1)=='=') {temp+=s.charAt(pointer+1);
                                pointer++;}
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                                break;
                            }
                        }break;
                        case '<':
                        {
                            if(pointer<=s.length()-1|| (pointer!=s.length()-1&&(s.charAt(pointer+1)==' '||s.charAt(pointer+1)=='='||s.charAt(pointer+1)=='<')))
                            {
                                temp+=s.charAt(pointer);
                                if(pointer!=s.length()-1&&(s.charAt(pointer+1)=='=' ||s.charAt(pointer+1)=='<')) {temp+=s.charAt(pointer+1);
                                pointer++;}
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                                break;
                            }
                        }break;
                        case '>':
                        {
                            if(pointer<=s.length()-1|| (pointer!=s.length()-1&&(s.charAt(pointer+1)==' '||s.charAt(pointer+1)=='='||s.charAt(pointer+1)=='>')))
                            {
                                temp+=s.charAt(pointer);
                                if(pointer!=s.length()-1&&(s.charAt(pointer+1)=='=' ||s.charAt(pointer+1)=='>')) {temp+=s.charAt(pointer+1);
                                pointer++;}
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                                break;
                            }
                        }break;
                        case '&':
                        {
                            if(pointer<=s.length()-1|| (pointer!=s.length()-1&&(s.charAt(pointer+1)==' '||s.charAt(pointer+1)=='&')))
                            {
                                temp+=s.charAt(pointer);
                                if(pointer!=s.length()-1&&s.charAt(pointer+1)=='&') {temp+=s.charAt(pointer+1);
                                pointer++;}
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                                break;
                            }
                        }break;
                        case '|':
                        {
                            if(pointer<=s.length()-1||(pointer!=s.length()-1&& (s.charAt(pointer+1)==' '||s.charAt(pointer+1)=='|')))
                            {
                                temp+=s.charAt(pointer);
                                if(pointer!=s.length()-1&&s.charAt(pointer+1)=='|') {temp+=s.charAt(pointer+1);
                                pointer++;}
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                                break;
                            }
                        }break;
                        case '^':
                        {
                            temp+=s.charAt(pointer);
                            pointer++;
                            if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                                

                        }break;
                        case '`':
                        {
                            temp+=s.charAt(pointer);
                            pointer++;
                            if(pointer== s.length()) pointer--;
                                curCase = textCase.op;
                        }
                        break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                        case 'g':
                        case 'h':
                        case 'i':
                        case 'j':
                        case 'k':                    
                        case 'l':
                        case 'm':
                        case 'n':
                        case 'o':
                        case 'p':
                        case 'q':
                        case 'r':
                        case 's':
                        case 't':
                        case 'u':
                        case 'v':
                        case 'w':
                        case 'x':
                        case 'y':
                        case 'z':
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                        case 'G':
                        case 'H':
                        case 'I':
                        case 'J':
                        case 'K':                    
                        case 'L':
                        case 'M':
                        case 'N':
                        case 'O':
                        case 'P':
                        case 'Q':
                        case 'R':
                        case 'S':
                        case 'T':
                        case 'U':
                        case 'V':
                        case 'W':
                        case 'X':
                        case 'Y':
                        case 'Z':
                        case '_':
                        {                   
                            temp+=s.charAt(pointer);         
                            pointer++;
                            while(pointer < s.length()&&(alphaSet.contains(s.charAt(pointer))||digiSet.contains(s.charAt(pointer))))
                            {
                                
                                if(pointer != s.length() && !delimSet.contains(s.charAt(pointer))) temp+=s.charAt(pointer);       
                                pointer++;                      
                            }
                            if(reservSet.contains(temp))
                            {
                                if(pointer== s.length()) pointer--;
                                 curCase = textCase.reserved;
                                 break;
                            }                       
                            if(temp.length()<=32)
                            {
                                if(pointer== s.length()) pointer--;
                                 curCase = textCase.ind;
                                 break;
                            }else 
                            {
                                if(pointer== s.length()) pointer--;
                                 curCase = textCase.err;
                                 break;
                            }
                        }
                        
                            
                    }
                }break;
                
                case chr:
                {
                    if(pointer != s.length()-2) 
                    {
                        while(pointer < s.length() && ((s.charAt(pointer)!= ' ')&& s.charAt(pointer) != '\''))
                        {
                            
                            temp+=s.charAt(pointer);
                            pointer++;
                        }
                        temp+=s.charAt(pointer);
                        if(CharCase(temp))
                        {
                            if(pointer== s.length()) pointer--;
                            charRes.add(temp);
                            curCase = textCase.endCheck;
                        }
                        else 
                        {
                            curCase=textCase.err;
                            if(pointer== s.length()) pointer--;
                        }
                    }
                    else 
                    {
                        curCase=textCase.err;
                        break;
                    }

                }
                break;
                case numeric:
                {
                    if(pointer < s.length()-2)
                    {
                        if((s.charAt(pointer)== '.'))
                        {
                            temp+=s.charAt(pointer);
                            curCase = textCase.floatNum;
                            break;
                        }
                        if((s.charAt(pointer)=='x'))
                        {
                            temp+=s.charAt(pointer);
                            curCase = textCase.hexNum;
                            break;
                        }
                    }
                    curCase = textCase.intNum;

                    
                   
                }
                break;
                case comm:
                {
                    while(pointer < s.length()-1 && (s.charAt(pointer+1)!= '\n'))
                    {                        
                            temp+=s.charAt(pointer);
                            pointer++;
                    }
                    temp+=s.charAt(pointer);
                    if(pointer== s.length()) pointer--;
                    commRes.add(temp);
                    curCase = textCase.endCheck;

                }
                    break;
                case commBig:
                {
                    while((s.charAt(pointer)!= '*')&&(s.charAt(pointer+1)!= '/'))
                    {                        
                        
                        temp+=s.charAt(pointer);
                        pointer++;        
                            if(pointer == s.length())
                            {
                                if(pointer== s.length()) pointer--;
                                curCase = textCase.err;
                                break;
                            }
                    }
                    temp+=s.charAt(pointer);
                    temp+=s.charAt(pointer+1);
                    pointer++;
                    if(pointer== s.length()) pointer--;
                    commRes.add(temp);
                    curCase = textCase.endCheck;

                }
                    break;
                case der:
                {
                    while(pointer < s.length()-1 && ((s.charAt(pointer+1)!= '\n')&&(s.charAt(pointer+1)!= ' ')))
                    {                        
                            temp+=s.charAt(pointer);
                            pointer++;
                    }
                    if(((s.charAt(pointer)!= '\n')&&(s.charAt(pointer)!= ' ')&&(s.charAt(pointer)!= '\r')))
                    temp+=s.charAt(pointer);
                    if(derSet.contains((temp)))
                    {
                        derevRes.add(temp);
                        curCase = textCase.endCheck;
                    }else  curCase = textCase.err;
                    if(pointer== s.length()) pointer--;
                }
                    break;
                case err:
                {
                    errRes.add(temp);
                    temp = "";
                    pointer++;
                    curCase = textCase.startCheck;
                }break;
                    
                case floatNum:
                {
                    pointer++;
                    while((pointer < s.length() && s.charAt(pointer)!= ' ' && digiSet.contains(s.charAt(pointer))))
                    {
                        
                        temp+=s.charAt(pointer);
                        pointer++;
                        
                    }
                    pointer--;
                    if(pointer== s.length()) pointer--;
                    floatNumRes.add(temp);
                    curCase = textCase.endCheck;
                }
                    break;
                case hexNum:
                {
                    pointer++;
                    while((pointer < s.length() && s.charAt(pointer)!= ' ' && digiSet.contains(s.charAt(pointer))))
                    {
                        
                        temp+=s.charAt(pointer);
                        pointer++;
                        
                    }
                    pointer--;
                    String t = temp.substring(2, temp.length());
                    if(pointer== s.length()) pointer--;
                    if(checkForHexPart(t))
                    {
                        hexNumRes.add(temp);
                        
                        curCase = textCase.endCheck;
                    }
                    else  curCase=textCase.err;
                    

                }
                    break;
                case ind:
                {
                    indRes.add(temp);
                    curCase = textCase.endCheck;
                }
                    break;
                case intNum:
                {

                    while((pointer < s.length() && s.charAt(pointer)!= ' ' && digiSet.contains(s.charAt(pointer))))
                    {
                        
                        temp+=s.charAt(pointer);
                        pointer++;
                        
                    }
                    pointer--;
                    if(pointer== s.length()) pointer--;
                    intNumRes.add(temp);
                    curCase = textCase.endCheck;
                }
                    break;
                case op:
                {
                    if(pointer== s.length()) pointer--;
                    operRes.add(temp);
                    curCase = textCase.endCheck;
                }
                    break;
                case punc:
                {
                    if(pointer== s.length()) pointer--;
                    puncRes.add(temp);
                    curCase = textCase.endCheck;
                }
                    break;
                case reserved:
                {
                    if(pointer== s.length()) pointer--;
                    reserveRes.add(temp);
                    curCase = textCase.endCheck;
                }
                    break;
                
                case str:
                {
                    Boolean tb = false;
                    while((pointer < s.length() && s.charAt(pointer)!= '"' ))
                    {
                        
                        temp+=s.charAt(pointer);
                        pointer++;
                        if(pointer<s.length()-2 && s.charAt(pointer)=='\\' &&  !escLSet.contains(s.charAt(pointer+1)))
                        {
                            tb=true;
                            temp+=s.charAt(pointer);
                            temp+=s.charAt(pointer+1);
                            pointer+=2;
                        }
                        else if(pointer<s.length()-2 && s.charAt(pointer)=='\\' &&  escLSet.contains(s.charAt(pointer+1)))
                        {
                            
                            temp+=s.charAt(pointer);
                            temp+=s.charAt(pointer+1);
                            pointer+=2;
                        }
                                          
                    }
                    temp+=s.charAt(pointer);
                    if(pointer== s.length()) pointer--;

                    if(tb||(temp.charAt(temp.length()-1)=='"'&&temp.charAt(temp.length()-2)=='\\')||temp.charAt(temp.length()-1)!='"')
                    {
                        
                    curCase = textCase.err;
                    }
                    else
                    {
                        stringRes.add(temp);
                        curCase = textCase.endCheck;
                    }
                    
                }
                    break;
                case endCheck:
                {
                    temp = "";
                    pointer++;
                    curCase = textCase.startCheck;

                }
                default:
                    break;

                
            }
        }
        Object[][] data ={intNumRes.toArray(),floatNumRes.toArray(),hexNumRes.toArray(),charRes.toArray(),stringRes.toArray(),operRes.toArray(),reserveRes.toArray(),derevRes.toArray(),commRes.toArray(),indRes.toArray(),puncRes.toArray(),errRes.toArray()};
        int maxresize = 0;
        for (Object[] objects : data) {
            if(objects.length>maxresize) maxresize=objects.length;
        }
        Vector<Vector<Object>> results = new Vector<Vector<Object>>();
        for(int i=0; i<maxresize; i++)
        {
            Vector<Object> tempRes = new Vector<Object>();
            tempRes.add(resCheck(i, intNumRes));
            tempRes.add(resCheck(i, floatNumRes));
            tempRes.add(resCheck(i, hexNumRes));
            tempRes.add(resCheck(i, charRes));
            tempRes.add(resCheck(i, stringRes));
            tempRes.add(resCheck(i, operRes));
            tempRes.add(resCheck(i, reserveRes));
            tempRes.add(resCheck(i, derevRes));
            tempRes.add(resCheck(i, commRes));
            tempRes.add(resCheck(i, indRes));
            tempRes.add(resCheck(i, puncRes));
            tempRes.add(resCheck(i, errRes));
            results.add(tempRes);
        }
        Vector<String> resColl = new Vector<String>();
        for(int i=0; i<collumnNames.length; i++)
        {
            resColl.add(collumnNames[i]);
        }
        Jf = new JFrame();
        Jf.getContentPane().setLayout(new FlowLayout());
        Jf.setSize(1200,800);
        Jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Jt = new JTable(results,resColl);
        Jf.setTitle("Lexem");
        JScrollPane sp = new JScrollPane(Jt);
        Jt.setPreferredScrollableViewportSize(new Dimension(1000, 500));
        Jf.getContentPane().add(sp);
        Jf.setVisible(true);
    }
}
