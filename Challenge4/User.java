public class User 
{
    private int id;
    private String name;

    public User(int _id, String _name)
    {
        id = _id;
        name = _name;
    }

    public int GetID() { return id; }
    public String GetName() { return name; }
}
