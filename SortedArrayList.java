import java.util.ArrayList;

public class SortedArrayList<E extends Comparable<E>> extends ArrayList<E> {

    public SortedArrayList(){
        super();
    }

    public void insert(E element){
        Integer i=0;

        for(E e:this){
            if(e.compareTo(element)>0){
                i=this.indexOf(e);
                break;
            }
            else {i++;}
        }
        this.add(i,element);
    }

}
