package deques;
import java.util.ArrayList;

public class ArrayListDeque<T> implements Deque<T> {

    private final ArrayList<T> list;

    public ArrayListDeque() {
        list = new ArrayList<T>();
    }

    @Override
    public void addFirst(T item) {
        //insert at 0 position
        list.add(0, item);//Inserts the specified element at the specified position in this list.
    }

    @Override
    public void addLast(T item) {
        //if index isn't specified then add at end of list
        list.add(item); //Appends the specified element to the end of this list.
    }

    @Override
    public T get(int index) {
        if ((index >= list.size()) || (index < 0)) {
            return null;
        }
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T removeFirst() {
        if (list.size() == 0) {
            return null;
        }
        T result = list.get(0);
        list.remove(0);
        return result;
    }

    @Override
    public T removeLast() {
        if (list.size() == 0) {
            return null;
        }
        T result = list.get(list.size()-1); //last element will be at index length-1
        list.remove(list.size()-1);
        return result;
    }
}
