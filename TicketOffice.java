import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class TicketOffice {

     SortedArrayList<Event> eventList;
     SortedArrayList<Client> clientList;
     boolean runProgram;
     PrintWriter outFile; 

     // Constructor
     TicketOffice(){
        eventList=new SortedArrayList<>();
        clientList=new SortedArrayList<>();
        runProgram=true;
        try {
            outFile = new PrintWriter("letters.txt");
            
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
     }

     // File Reader Function
     void readFile() throws FileNotFoundException{
         
        Scanner inFile = new Scanner(new FileReader("input.txt"));
        int n= Integer.parseInt(inFile.nextLine());

        while(n>0){
            String s=inFile.nextLine();
            int x=Integer.parseInt(inFile.nextLine());
            Event e =new Event(s,x);
            eventList.insert(e);
            n--;
        }

        n=Integer.parseInt(inFile.nextLine());
        while(n>0){
            String[] s=inFile.nextLine().split(" ");
            Client c=new Client(s[0],s[1]);
            clientList.insert(c);
            n--;
        }
       
     }

     // Application Start Function
     void start(){
        while(runProgram)menu();
        outFile.close();
     }

     // Menu Printer Function
     void menu(){
        System.out.println("f - to finish running the program");
        System.out.println("e - to display on the screen information about all the events");
        System.out.println("c - to display on the screen information about all the clients");
        System.out.println("b - to update the stored data when tickets are bought by one of the registered clients");
        System.out.println("r - to update the stored data when a registered client cancels/returns tickets");

        Scanner s = new Scanner(System.in);
        char option=s.next().charAt(0);

        switch(option){
            case 'f':  System.out.println("Exiting the Program");runProgram=false;break;
            case 'e':  showEventInfo(); break;
            case 'c':  showClientInfo(); break;
            case 'b':  buyTickets(); break;
            case 'r':  cancelTickets(); break;
            default: System.out.println("Invalid option, Try again");System.out.println("");
        }    
     }

     // Show Event Info on Terminal
     void showEventInfo(){
        for(Event e:eventList){
            System.out.println("Event Name: "+ e.getEventName());
            System.out.println("Tickets Left: "+ e.getnumberOfTickets());
        }
     }

     // Show Client Info on Terminal 
     void showClientInfo() {
        for(Client c: clientList){
            System.out.println("Client Name: "+ c.getFirstName()+" " + c.getLastName());
            if(c.event.size()!=0){
                for(Event e: c.event){
                    System.out.println("Events: " +e.getEventName() + " Tickets Bought: "+ e.getnumberOfTickets());
                }
            }
        }
    }


/************************* Buy Tickets and Update Operations **********************************************/

    void buyTickets() {

        System.out.println("Enter the Client First name and Second name separated with space");
        Scanner in = new Scanner(System.in);
        String[] s=in.nextLine().split(" ");

        // Check if Client is valid one or not
        try{
            Client client=new Client(s[0], s[1]);
            int index=0;
            boolean isclient=false;
            for(Client c: clientList){
                if(c.getFirstName().equals(client.getFirstName()) && c.getLastName().equals(client.getLastName())){
                    isclient=true;
                    break;
                }
                else index++;
            }

            if(isclient){
                eventRegistrationInput(client,index);
                // Check if Tickets are available
            }
            else{
            System.out.println( "You are not a valid Client");
            }
        } catch(Exception e){
                System.out.println("Invalid Input, Try again");
                buyTickets();
            }
    }

    void eventRegistrationInput(Client client, int index) {

        System.out.println("Enter Event name :");
        Scanner in=new Scanner(System.in);
        String s=in.nextLine();

        if(checkEventName(s)){
            System.out.println("Number of Tickets to be Ordered:");
            int x=in.nextInt();

            checkTicketsAndUpdate(s,x,index);            // Check if tickets are available     
        }
        else {
            System.out.println("This Event don't exist");
        }
    }

    void checkTicketsAndUpdate(String s, int x, int index) {

        for(Event e:eventList){
                if(s.equals(e.getEventName())){
                    if(x<=e.getnumberOfTickets()){

                        //Adding Tickets to Clients
                        if(addTickets(s,x,index))

                        //Updating Tickets in Event List
                        {int rx=e.getnumberOfTickets()-x;
                        e.setnumberOfTickets(rx);
                        System.out.println(x+" tickets bought for event-"+s);}

     
                    }
                    else{
                        outFile.println("Dear "+clientList.get(index).getFirstName()+" "+clientList.get(index).getLastName()+", Not Enough Tickets left for the Event-"+ s);
                        System.out.println("Not Enough Tickets left for the Event-"+ s);
                    }
                }
            }
        }


        boolean checkEventName(String s) {
            for(Event e:eventList){
                if(s.equals(e.getEventName())){return true;}
            }
            return false;
        }

        boolean addTickets(String s, int x, int clientIndex) {

            Event eventbought=new Event(s,x);
            Client c=clientList.get(clientIndex);
            SortedArrayList<Event> event=c.event;
            boolean flag=false; 
            int eventIndex=0;
            for(int i=0;i<event.size();i++){
                if(s.equals(event.get(i).getEventName())){
                    flag=true;
                    eventIndex=i;
                    break;
                }
            }
            if(flag){
                int newCountOfTickets=x+event.get(eventIndex).getnumberOfTickets();
                event.get(eventIndex).setnumberOfTickets(newCountOfTickets);
                return true;
            }
            else if(flag==false && event.size()<3)
                {c.event.insert(eventbought);return true;}
            else{
                System.out.println("You can't buy tickets for more than 3 events");
                return false;
            }
        }


/************************* Cancel Tickets and Update Operations **********************************************/
    
        void cancelTickets() {
            System.out.println("Enter the Client First name and Second name separated with space");
            Scanner in = new Scanner(System.in);
            String[] s=in.nextLine().split(" ");
    
            // Check if Client is valid one or not
            try{
                Client client=new Client(s[0], s[1]);
                int index=0;
                boolean isclient=false;
                for(Client c: clientList){
                    if(c.getFirstName().equals(client.getFirstName()) && c.getLastName().equals(client.getLastName())){
                        isclient=true;
                        break;
                    }
                    else index++;
                }
    
                if(isclient){
                    eventRegistrationCheck(client,index);
                    // Check if Tickets are bought
                }
                else{
                System.out.println( "You are not a valid Client");
                }
            } catch(Exception e){
                    System.out.println("Invalid Input, Try again");
                    cancelTickets();
            }
        }

        void eventRegistrationCheck(Client client, int index) {

            System.out.println("Enter Event name :");
            Scanner in=new Scanner(System.in);
            String s=in.nextLine();
            Client c=clientList.get(index);
            if(checkEventName1(s,c)){
                System.out.println("Number of Tickets to be Cancelled:");
                int x=in.nextInt();
    
                checkTicketsAndUpdate1(s,x,index,c.event);            // Check if tickets are available     
            }
            else {
                System.out.println("You don't have tickets for this Event");
            }
        }

        boolean checkEventName1(String s, Client c) {
            for(Event e:c.event){
                if(s.equals(e.getEventName())){return true;}
            }
            return false;
        }
        void checkTicketsAndUpdate1(String s, int x, int index,SortedArrayList<Event> events) {

            for(Event e:events){
                    if(s.equals(e.getEventName())){
                        if(x<=e.getnumberOfTickets()){
    
                            //Removing Tickets from Clients
                            int rx=e.getnumberOfTickets()-x;
                            e.setnumberOfTickets(rx);
                            System.out.println(x+" tickets Cancelled/returned for event - "+s);
                            if(rx==0){
                                events.remove(e);
                            }
                            
                            //Updating Tickets in Event List
                            for(Event el:eventList){
                                if(s.equals(el.getEventName())){
                                    el.setnumberOfTickets(el.getnumberOfTickets()+x);
                                }
                            }
                        }
                        else{
                            System.out.println("Not Enough Tickets left for Cancelation for the event - "+ s);
                        }
                        break;
                    }
                }
        }
}

