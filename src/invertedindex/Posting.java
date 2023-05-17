package invertedindex;
public  class Posting
{
    public Posting next;
    public int docId;
    public int dtf; // document term frequency
    public Posting(int docId)
    {
        this.docId = docId;
        this.dtf = 1;
        this.next = null;
    }

}