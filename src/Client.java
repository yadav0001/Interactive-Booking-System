public class Client implements Comparable<Client>{
    
    private String firstName;
    private String lastName;
    SortedArrayList<Event> event;

    public Client(String firstName, String lastName) {
        this.firstName=firstName;
        this.lastName=lastName;
        event=new SortedArrayList<>();
    }

    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }

    @Override
    public int compareTo(Client c) {
        int lcomp=lastName.compareTo(c.lastName);
        int fcomp=firstName.compareTo(c.firstName);

        if(lcomp!=0) return lcomp;
        else if(fcomp!=0) return fcomp;
        else return 0;
    }

}
