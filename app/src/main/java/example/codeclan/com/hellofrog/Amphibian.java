package example.codeclan.com.hellofrog;

/**
 * Created by sandy on 25/04/2016.
 */
public class Amphibian {
    public Amphibian(String name)
    {
        mName = name;
    }

    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String nName) {
        mName = nName;
    }
}
