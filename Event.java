public class Event implements Comparable<Event>{
    private String eventName;
    private int numberOfTickets;

    
   public Event(String eventName, int numberOfTickets){
        this.eventName=eventName;
        this.numberOfTickets=numberOfTickets;
    }

    public String getEventName(){
        return eventName;
    }

    public int getnumberOfTickets(){
        return numberOfTickets;
    }

    public void setnumberOfTickets(int rx) {
        this.numberOfTickets=rx;
    }

    @Override
    public int compareTo(Event e) {
        int eventComp=eventName.compareTo(e.getEventName());
        if(eventComp!=0){
            return eventComp;
        }
        else return 0;
    }
}
