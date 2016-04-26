package example.codeclan.com.hellofrog;

import java.util.ArrayList;

/**
 * Created by user on 26/04/2016.
 */

public class AmphibianList {

    private ArrayList<Amphibian> mAmphibians;

    public AmphibianList()
    {
        mAmphibians = new ArrayList<Amphibian>();
    }

    public AmphibianList(ArrayList<Amphibian> amphibians) {
        mAmphibians = new ArrayList<Amphibian>(amphibians);
    }

    public ArrayList<Amphibian> getList() {
        //return mAmphibians;  THIS IS WRONG!!! This is a point by reference - allows our original collection to be altered by mistake if we do anything to it, eg sort it or add/delete things from it.
        return new ArrayList<Amphibian>(mAmphibians);
        // The line above clones the ArrayList mAmphibians so that if anyone made changes ( eg. getList().add()/.remove()/.sort() ), it wont change our original colection, it will only change the clone.
    }

    public int length()
    {
        return mAmphibians.size();
    }

    public void add(Amphibian amphibian){
    //// could add more things here if wanted, eg. if(amphibian is alive)
        mAmphibians.add(amphibian);
    }

    public Amphibian get(Amphibian amphibian)
    {
        if (mAmphibians.contains(amphibian))
        {
            int index = mAmphibians.indexOf(amphibian);
            return mAmphibians.get(index);
        }
        return null;
    }

    public Amphibian get(String name) {
        for (Amphibian amphibian : mAmphibians)
        {
            if (amphibian.getName().equals(name)) {
                return amphibian;
            }
        }
        return null;
    }

    public void delete(Amphibian amphibian)
    {
        mAmphibians.remove(amphibian);
    }

    public void delete(String name)
    {
        Amphibian amphibian = get(name);
        if (amphibian != null) {
            delete(amphibian);
        }
    }

}

