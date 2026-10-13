/**
 * Stack API that students must implement in two ways: with a linked list
 * and with a resizing array.
 *
 * @param <T> the type of items stored in the stack
 */
public interface Stack<T> {
    void push(T item);

    T pop();

    boolean isEmpty();

    int size();
}