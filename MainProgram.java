public class MainProgram {

    public static void main(String[] args) throws Exception {

        TicketOffice ticketOffice=new TicketOffice();
        ticketOffice.readFile();
        ticketOffice.start();

    }       

}
