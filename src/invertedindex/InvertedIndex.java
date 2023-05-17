package invertedindex;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class InvertedIndex
{
    static Map<String, DictEntry> hash = new HashMap<>();

    public  static void  Build() throws IOException
    {
        ArrayList<String> fileNames = new ArrayList<String>();
        fileNames.add("file1.txt");
        fileNames.add("file2.txt");
        fileNames.add("file3.txt");
        fileNames.add("file4.txt");
        fileNames.add("file5.txt");
        fileNames.add("file6.txt");
        fileNames.add("file7.txt");
        fileNames.add("file8.txt");
        fileNames.add("file9.txt");
        fileNames.add("file10.txt");


        int docId = 0;
        for(String filename:fileNames)
        {
            docId++;
            BufferedReader br=new BufferedReader(new FileReader (new File(filename)));
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] ArrayWords = line.split(" ");

                for (String w : ArrayWords) {
                    w = w.toLowerCase();
                    if (!hash.containsKey(w)) {
                        hash.put(w, new DictEntry());
                    }

                    DictEntry term = hash.get(w);
                    term.term_freq++;
                    Posting posting = term.pList;
                    while (posting != null && posting.docId != docId) //while continue run if current docid not fount in  posting docid  
                    {
                        posting = posting.next;
                    }
                    if (posting != null)
                    {
                        posting.dtf++;  //freq of doc ++ if docid in posting 
                    } 
                    else 
                    {
                        Posting newP = new Posting(docId);
                        newP.dtf = 1;
                        newP.next = term.pList;
                        term.pList = newP;
                        term.doc_freq++;
                    }
                    term.sort();
                }

            }


            br.close();


        }

    }

    public  static void  Search(String text)
    {
        if (hash.containsKey(text))
        {
            DictEntry entry = hash.get(text);
            Posting p = entry.pList;
            System.out.println("Word :"+" "+text+" appear in "+entry.doc_freq +"  DOC");
            System.out.println("Word :"+" "+text+" appear "+entry.term_freq +" Times in all DOC");

            while (p != null)
            {
                System.out.println("File " + p.docId + " "+ "appear " + p.dtf + " times.");
                p = p.next;
            }
        }
        else //word not fount
        {
            System.out.println("The word " + text + " Not Found at any File");
        }
    }




    public static void main(String[] args) throws IOException
    {


        Scanner input=new Scanner(System.in);
        System.out.println("Enter a word you want to Search for : ");
        String text=input.nextLine();
        Build();
        Search(text);
    }


}


