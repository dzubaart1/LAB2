package openHash;

public class Item
{
    public char[] Name;
    public Item Next;

    public Item(char[] name, Item next)
    {
        Name = name;
        Next = next;
    }
}
