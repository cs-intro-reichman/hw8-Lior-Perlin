/** Represents a user in a social network. A user is characterized by a name,
 *  a list of user names that s/he follows, and the list's size. */
 public class User {

    // Maximum number of users that a user can follow
    static int maxfCount = 10;

    private String name;       // name of this user
    private String[] follows;  // array of user names that this user follows
    private int fCount;        // actual number of followees (must be <= maxfCount)

    /** Creates a user with an empty list of followees. */
    public User(String name) {
        this.name = name;
        follows = new String[maxfCount]; // fixed-size array for storing followees
        fCount = 0;                      // initial number of followees
    }

    /** Creates a user with some followees. The only purpose of this constructor is 
     *  to allow testing the toString and follows methods, before implementing other methods. */
    public User(String name, boolean gettingStarted) {
        this(name);
        follows[0] = "Foo";
        follows[1] = "Bar";
        follows[2] = "Baz";
        fCount = 3;
    }

    /** Returns the name of this user. */
    public String getName() {
        return name;
    }

    /** Returns the follows array. */
    public String[] getfFollows() {
        return follows;
    }

    /** Returns the number of users that this user follows. */
    public int getfCount() {
        return fCount;
    }

    /** If this user follows the given name, returns true; otherwise returns false. */
    public boolean follows(String name) {
        // Goes though the loop the amount of follow count there are, and checks if the name is there
        for(int i = 0; i < fCount; i++) {
            if(follows[i] == name) {
                return true;
            }
        }
        // If we went through the entire counted followees and didn't return true,  that means the user does not follow the given name so give back false
        return false;
    }
    /** Makes this user follow the given name. If successful, returns true. 
     *  If this user already follows the given name, or if the follows list is full, does nothing and returns false; */
    public boolean addFollowee(String name) {
        // Check if the follow list is full or the user already follows the person or we try to make a person follow himself
        if(this.fCount == 10 || this.follows(name) || this.name == name) {
            return false;
        }
        // If the follow list is not full
        else {
            // Add the name the the fCount position (which is always one higher then the last person added) and make sure the count is higher
            this.follows[this.fCount] = name;
            this.fCount++;
            return true;
        }
    }

    /** Removes the given name from the follows list of this user. If successful, returns true.
     *  If the name is not in the list, does nothing and returns false. */
    public boolean removeFollowee(String name) {
        for(int i = 0; i < this.fCount; i++) {
            // Checks if the name exists
            if(this.follows[i] == name) {
                // Let's bring every person a step back on the array
                while(i < this.fCount - 1) {
                    // Make this current follows the one after
                    this.follows[i] = this.follows[i + 1];
                    // Make the next follows null
                    this.follows[i + 1] = null;
                    i++;
                }
                // After moving all followees in the array one step back let's get the counter one back and return false
                this.fCount--;
                return true;
            }
        }
        // If we ran through the entire array and didn't return true, that means we did not find the followee on the array so we'll return false
        return false;
    }

    /** Counts the number of users that both this user and the other user follow.
    /*  Notice: This is the size of the intersection of the two follows lists. */
    public int countMutual(User other) {
        // Make sure the inputted user is not null
        if(other == null) {
            return 0;
        }
        int counter = 0;
        for(int i = 0; i < this.fCount; i++) {
            // At every index of our user's follows array, lets check if he exist's in the others users array using "follows" function.
            if(other.follows(this.follows[i])) {
                counter++;
            }
        }
        return counter;
    }

    /** Checks is this user is a friend of the other user.
     *  (if two users follow each other, they are said to be "friends.") */
    public boolean isFriendOf(User other) {
        // Checks usingn the follows function of the current user's name exist on the other users followees name.
        return other.follows(this.name) && this.follows(other.getName());
    }

    /** Returns this user's name, and the names that s/he follows. */
    public String toString() {
        String ans = name + " -> ";
        for (int i = 0; i < fCount; i++) {
            ans = ans + follows[i] + " ";
        }
        return ans;
    }
}
