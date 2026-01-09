/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        // Lets make a loop and go through the network trying to find our name
        for(int i = 0; i < this.userCount; i++) {
            // Checks if the user in the current index (using the get name function of User Class) is the same name as the entered name
            if(this.users[i].getName() == name) {
                return this.users[i];
            }
        }
        // If we did not return anything by now, that means we ran through the array and did not find the user, so let's return null as requested in such scenario
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        // First let's check if the user already exists or the network is full
        if(this.getUser(name) != null || userCount >= this.users.length) {
            return false;
        }
        else {
            User newUser = new User(name);
            // The current count points to the lowest null index so we can use that
            users[this.userCount] = newUser;
            this.userCount++;
            return true;
        }
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        // First let's check if both users exists
        if(this.getUser(name1) == null || this.getUser(name2) == null) {
            return false;
        }
        else {
            // Using the addFollowee function of User Class. if it failed than it means name1's follows array is full...
            return this.getUser(name1).addFollowee(name2);
        }
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        // Making our lives easy by making a specific user to point on the user's name
        User cur = this.getUser(name);
        // Let's make sure the user actually exist
        if(cur == null) {
            return null;
        }
        // If both instensence were false that means we are good to find a recommended new followee
        else {
            // Let's create veriables to remember the most recommended to follow and the amount of friends they have in common
            // we will see how many common friends they have using User Class countMutual function
            // Most mutual starts at -1 because if someone doesn't has friends at all or noone has a mutual friend with him we still want to recommend someone.
            String mostCommonName = null;
            int mostMutualAmount = -1;
            for(int i = 0; i < this.userCount; i++) {
                // Let's make sure our we skip our inputted user
                if(this.users[i] == cur) {
                    i++;
                }
                // Now check if this user has more mutual friends than the one we had most
                if(this.users[i].countMutual(cur) > mostMutualAmount) {
                    // If so, lets remember how many mutual friends the one with the most had and the person that our user had the most mutual friends with
                    mostMutualAmount = this.users[i].countMutual(cur);
                    mostCommonName = this.users[i].getName();
                }
            }
            return mostCommonName;
        }
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        // Make sure the network has users
        if(this.userCount <= 0) {
            return null;
        }
        int mostPopularAmount = -1;
        String mostPopularName = "";
        for(int i = 0; i < this.userCount; i++) { 
            if(followeeCount(this.users[i].getName()) > mostPopularAmount) {
                mostPopularAmount = followeeCount(this.users[i].getName());
                mostPopularName = this.users[i].getName();
            }
        }
        return mostPopularName;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int counter = 0;
        for(int i = 0; i < this.userCount; i++) { 
            if(this.users[i].follows(name)) {
                counter++;
            }
        }
        return counter;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
       String outPut = "";
        for(int i = 0; i < this.userCount; i++) { 
            outPut += this.users[i].toString() + "\n";
        }
       return outPut;
    }
}
