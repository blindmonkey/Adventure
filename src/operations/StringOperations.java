package operations;
/**
 * @author Sergey Grabkovsky
 */

import java.util.*;


public class StringOperations {
    public static String between(String inString, String between1, String between2) {
        return between(inString,between1,between2,0);
    }
    
    public static String between(String inString, String between1, String between2, int startAt) {
        int i1, i2;
        i1 = inStr(inString,between1,startAt);
        if (i1 < 0) return "";
        i2 = inStr(inString,between2,i1 + between1.length());
        if (i2 < 0) return "";
        return inString.substring(i1 + between1.length(), i2);
    }
    
    public static int inStr(String inString, String findString, int startAt) {
        for (int x=startAt; x<inString.length()-findString.length()+1; x++) {
            try {
                if (inString.substring(x, findString.length() + x).equals(findString))
                    return x;
            } catch (Exception e) {
                System.out.println ("error on byte " + x + ", searching for \"" + findString + "\" in \"" + inString + "\".  Starting at " + startAt);
                e.notifyAll();
            }
        }
        return -1;
    }
    public static int inStr(String inString, String findString) {
        return inStr(inString,findString,0);
    }
    
    public static String formatFileSize(int size, boolean shortenName) {
        String[] sizeTiers;
        String[] sizeTiersS = {"b","Kb","Mb","Gb","Tb"};
        String[] sizeTiersE = {" Bytes"," Kilobytes"," Megabytes"," Gigabytes"," Terabytes"};
        
        if (shortenName) sizeTiers=sizeTiersS; else sizeTiers=sizeTiersE;
        int currentTier = 0;
        double s = size;
        
        while (s >= 1024) {
            s = s / 1024;
            currentTier++;
        }
        return MathOperations.round(s,2) + sizeTiers[currentTier];
    }
    
    public static int numOfChars(String str, char c) {
        int n=0;
        for (int i=0 ; i<str.length() ; i++) {
            if (str.charAt(i) == c)
                n++;
        }
        return n;
    }
    
    public static ArrayList<String> splitString(String str, String delimeter) {
        ArrayList<String> al = new ArrayList<String>();
        
        if (inStr(str,delimeter) < 0 || delimeter.equals(""))
            if (!str.equals("") && !delimeter.equals("")) {
                al.add(str);
                return al;
            } else return al;
        
        
        int last = 0;
        int i = inStr(str,delimeter);
        al.add(str.substring(last,i));
        last = i;
        i = inStr(str,delimeter,last + delimeter.length());
        
        while (i >= 0) {
            al.add(str.substring(last+delimeter.length(),i));
            last = i;
            i = inStr(str,delimeter,i+delimeter.length());
        }
        al.add(between(str+"\0",delimeter,"\0",last));
        return al;
    }
    
    public static String formatFileSize(int size) {
        return formatFileSize(size,true);
    }
    
    public static String trimString(String str, String validCharacters) {
        String out ="";
        boolean broken = false;
        for (int x=0 ; x<str.length() ; x++) {
            broken = false;
            for (int y=0 ; y<validCharacters.length() ; y++) {
                if (str.charAt(x) == validCharacters.charAt(y)) {
                    out = out + Character.toString(str.charAt(x));
                    broken = true;
                    break;
                }
            }
            if (!broken) out = out + " ";
            broken = false;
        }
        while (inStr(out,"  ") >= 0)
            out = out.replace("  ", " ");
        return out;
        /*
        int start=-1, end=-1;
        for (int x=0 ; x<str.length() ; x++) {
            for (int y=0 ; y<validCharacters.length() ; y++) {
                if (str.charAt(x) == validCharacters.charAt(y)) {
                    start = x;
                    break;
                }
            }
            if (start > -1) break;
        }
        for (int x=str.length()-1 ; x>=0 ; x--) {
            for (int y=0 ; y<validCharacters.length() ; y++) {
                if (str.charAt(x) == validCharacters.charAt(y)) {
                    end = x+1;
                    break;
                }
            }
            if (end > -1) break;
        }
        return str.substring(start,end);
        */
    }
    
    public static String trimString(String str) {
        return trimString(str," -=_+`~!@#$%^&*(),./<>?;':\"[]\\{}|abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890").trim();
    }
    
    public static boolean fitsWildcard(String str, String wildcard) {
        while (inStr(wildcard,"**") >= 0)
            wildcard = wildcard.replace("**", "*");
        ArrayList<String> parsed = splitString(wildcard,"*");
        
        //System.out.println("Comparing String \"" + str + "\" to wildcard \"" + wildcard + "\".  Parsed wildcard into " + parsed.size() + " pieces");
        
        if (parsed.size() == 0)
            return str.equals("");
        else if (parsed.size() == 1)
            return str.equals(parsed.get(0));
        else {
            int lastStopped = 0;
            int startIndex = 1;
            if (parsed.get(0).equals("")) {
                lastStopped = inStr(str,parsed.get(1));
                if (lastStopped < 0)
                    return false;
                lastStopped+=parsed.get(1).length();
                startIndex = 2;
            } else {
                if (parsed.get(0).length() > str.length())
                    return false;
                else if (parsed.get(0).equals(str.substring(0,parsed.get(0).length())))
                    lastStopped = parsed.get(0).length();
                else
                    return false;
                startIndex = 1;
            }
            
            for (int x=startIndex ; x<parsed.size()-1 ; x++) {
                lastStopped = inStr(str,parsed.get(x), lastStopped);
                if (lastStopped < 0)
                    return false;
                lastStopped+=parsed.get(x).length();
            }
            
            if (parsed.get(parsed.size()-1).equals(""))
                return true;
            else {
                lastStopped = inStr(str,parsed.get(parsed.size()-1),lastStopped);
                //lastStopped+=parsed.get(parsed.size()-1).length();
                return lastStopped>=0;//lastStopped==str.length()-1;
            }
        }
    }
    
    public static double getPercentage(String str, String wildcard) {
        return wildcard.replace("*", "").length() / str.length();
    }
    
    public static ArrayList<String> getWildcardParts(String str, String wildcard) {
        while (inStr(wildcard,"**") >= 0)
            wildcard = wildcard.replace("**", "*");
        ArrayList<String> out = new ArrayList<String>();
        ArrayList<String> parsed = splitString(wildcard,"*");
        
        if (!fitsWildcard(str,wildcard)) throw new RuntimeException("Wildcard \"" + wildcard + "\" does not match string \"" + str + "\".");
        if (wildcard.equals(str)) {
            out.add(str);
            return out;
        }
        else if (parsed.size() == 0 || parsed.size() == 1)
            return new ArrayList<String>();
        else {
            int lastFound = 0;
            int startIndex = 1;
            if (parsed.get(0).equals("")) {
                lastFound = inStr(str,parsed.get(1));
                startIndex = 2;
            }
            
            for (int x=startIndex ; x<parsed.size()-1 ; x++) {
                out.add(between(str,parsed.get(x-1),parsed.get(x),lastFound));
                lastFound = inStr(str,parsed.get(x),lastFound+parsed.get(x-1).length());
            }
            if (lastFound < 0)
                throw new RuntimeException("Failure to match " + str + " to " + wildcard);
            
            if (parsed.get(parsed.size()-1).equals(""))
                out.add(between(str+"\2",parsed.get(parsed.size()-2),"\2",lastFound));
            else {
                out.add(between(str+"\2",parsed.get(parsed.size()-2),parsed.get(parsed.size()-1),lastFound));
            }
            return out;
        }
    }
    
    public static void print(String s) {
        System.out.println(s);
    }
    
}
