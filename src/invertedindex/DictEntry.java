package invertedindex;


public class DictEntry
{
    public int doc_freq = 0; // number of documents that contain the term
    public int term_freq = 0; // number of times the term is mentioned in the collection
    public Posting pList = null; // linked list of postings

    public void sort()
    {
        if (pList == null || pList.next == null)
        {
            return; // List is already sorted or empty
        }

        Posting sortedList = pList; //sorted list has first term in plist
        pList = pList.next;
        sortedList.next = null;

        while (pList != null) 
        {
            Posting current = pList; //current is first term in remaining plist 
            pList = pList.next;

            if (current.docId < sortedList.docId)
            {
                current.next = sortedList; //current term  become before sortedlist term
                sortedList = current;
            } 
            else //current >soertedlist term
            {
                Posting currentsorted = sortedList;
                while (currentsorted.next != null && currentsorted.next.docId < current.docId) //loop on sortedlist to put current term in right position
                {
                    currentsorted = currentsorted.next;
                }
                current.next = currentsorted.next; // if current docid < currentsorted docid
                currentsorted.next = current;
            }
        }

        pList = sortedList;
    }
}
