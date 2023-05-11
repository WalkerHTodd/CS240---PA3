import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Doubly-linked-list implementation of the java.util.Deque interface. This implementation is more
 * space-efficient than Java's LinkedList class for large collections because each node contains a
 * block of elements instead of only one. This reduces the overhead required for next and previous
 * node references. This implementation does not allow null's to be added to the collection. Adding
 * a null will result in a NullPointerException.
 * 
 * @author Walker Todd
 * @version 4/2/2023
 * 
 *          My work complies with the JMU Honor Code and if any help was recieved it was from a TA
 *          and was listed where the help was recieved
 *
 */
public class HybridDeque<E> extends AbstractDeque<E> {

  /*
   * IMPLEMENTATION NOTES ----------------------------------
   *
   * The list of blocks is never empty, so leftBlock and rightBlock are never equal to null. The
   * list is not circular.
   *
   * A deque's first element is at leftBlock.elements[leftIndex]
   * 
   * and its last element is at rightBlock.elements[rightIndex].
   * 
   * The indices, leftIndex and rightIndex are always in the range:
   * 
   * 0 <= index < BLOCK_SIZE
   *
   * And their exact relationship is:
   * 
   * (leftIndex + size - 1) % BLOCK_SIZE == rightIndex
   *
   * Whenever leftBlock == rightBlock, then:
   * 
   * leftIndex + size - 1 == rightIndex
   *
   * However, when leftBlock != rightBlock, the leftIndex and rightIndex become indices into
   * distinct blocks and either may be larger than the other.
   *
   * Empty deques have:
   * 
   * size == 0
   * 
   * leftBlock == rightBlock
   * 
   * leftIndex == CENTER + 1
   * 
   * rightIndex == CENTER
   * 
   * Checking for size == 0 is the intended way to see whether the Deque is empty.
   * 
   * 
   * (Comments above are a lightly modified version of comments in Python's deque implementation:
   * https://github.com/python/cpython/blob/v3.11.2/Modules/_collectionsmodule.c
   * https://docs.python.org/3.11/license.html)
   * 
   */

  private static int BLOCK_SIZE = 8;
  private static int CENTER = (BLOCK_SIZE - 1) / 2;


  private Cursor leftCursor;
  private Cursor rightCursor;
  private int size;



  /**
   * DO NOT MODIFY THIS METHOD. This will be used in grading/testing to modify the default block
   * size..
   *
   * @param blockSize The new block size
   */
  protected static void setBlockSize(int blockSize) {
    HybridDeque.BLOCK_SIZE = blockSize;
    HybridDeque.CENTER = (blockSize - 1) / 2;
  }


  /**
   * Doubly linked list node (or block) containing an array with space for multiple elements.
   */
  private class Block {
    private E[] elements;
    private Block next;
    private Block prev;

    /**
     * Block Constructor.
     *
     * @param prev Reference to previous block, or null if this is the first
     * @param next Reference to next block, or null if this is the last
     */
    @SuppressWarnings("unchecked")
    public Block(Block prev, Block next) {
      this.elements = (E[]) (new Object[BLOCK_SIZE]);
      this.next = next;
      this.prev = prev;
    }
  }

  /**
   * Many of the complications of implementing this Deque class are related to the fact that there
   * are two pieces of information that need to be maintained to track a position in the deque: a
   * block reference and the index within that block. This class combines those two pieces of
   * information and provides the logic for moving forward and backward through the deque structure.
   * NOTE: The provided cursor class is *immutable*: once a Cursor object is created, it cannot be
   * modified. Incrementing forward or backward involves creating new Cursor objects at the required
   * location. Immutable objects can be cumbersome to work with, but they prevent coding errors
   * caused by accidentally aliasing mutable objects.
   */
  private class Cursor {
    private final Block block;
    private final int index;

    public Cursor(HybridDeque<E>.Block block, int index) {
      this.block = block;
      this.index = index;
    }

    /**
     * Increment the cursor, crossing a block boundary if necessary.
     *
     * @return A new cursor at the next position, or null if there are no more valid positions.
     */
    private Cursor next() {

      if (index == BLOCK_SIZE - 1) { // We need to cross a block boundary
        if (block.next == null) {
          return null;
        } else {
          return new Cursor(block.next, 0);
        }
      } else { // Just move one spot forward in the current block
        return new Cursor(block, index + 1);
      }
    }

    /**
     * Decrement the cursor, crossing a block boundary if necessary.
     *
     * @return A new cursor at the previous position, or null if there is no previous position.
     */
    private Cursor prev() {
      if (index == 0) { // We need to cross a block boundary
        if (block.prev == null) {
          return null;
        } else {
          return new Cursor(block.prev, BLOCK_SIZE - 1);
        }
      } else { // Just move one spot back in the current block.
        return new Cursor(block, index - 1);
      }
    }



    /**
     * Return the element stored at this cursor.
     */
    public E get() {
      return block.elements[index];
    }

    /**
     * Set the element at this cursor.
     */
    public void set(E item) {
      block.elements[index] = item;
    }
  }

  /**
   * Default Constructor.
   */
  public HybridDeque() {
    Block blck = new Block(null, null);
    leftCursor = new Cursor(blck, CENTER + 1);
    rightCursor = new Cursor(blck, CENTER);

    this.size = 0;
  }


  @Override
  public boolean offerFirst(E e) {
    if (e == null) {
      throw new NullPointerException();
    }

    if (leftCursor.prev() == null) {
      // Make new Cursor
      Block block = new Block(null, leftCursor.block);
      Cursor newCursor = new Cursor(block, BLOCK_SIZE - 1);
      leftCursor.block.prev = newCursor.block;
      leftCursor = newCursor;
      leftCursor.set(e);
      size++;

      return true;
    }
    leftCursor = leftCursor.prev();
    leftCursor.set(e);

    size++;

    return true;
  }

  @Override
  public boolean offerLast(E e) {
    if (e == null) {
      throw new NullPointerException();
    }
    if (rightCursor.next() == null) {
      Block block = new Block(rightCursor.block, null);
      Cursor newCursor = new Cursor(block, 0);
      rightCursor.block.next = newCursor.block;
      rightCursor = newCursor;
      rightCursor.set(e);
      size++;

      return true;
    }

    rightCursor = rightCursor.next();
    rightCursor.set(e);
    size++;
    return true;
  }

  @Override
  public E pollFirst() {
    if (size == 0) {
      return null;
    }

    E temp = leftCursor.block.elements[leftCursor.index];
    leftCursor.set(null);
    leftCursor = leftCursor.next();
    size--;
    return temp;
  }

  @Override
  public E pollLast() {
    if (size == 0) {
      return null;
    }

    E temp = rightCursor.block.elements[rightCursor.index];
    rightCursor.set(null);
    rightCursor = rightCursor.prev();
    size--;
    return temp;
  }

  @Override
  public E peekFirst() {
    if (size == 0) {
      return null;
    }
    return leftCursor.get();
  }

  @Override
  public E peekLast() {
    if (size == 0) {
      return null;
    }
    return rightCursor.get();
  }

  @Override
  public boolean removeFirstOccurrence(Object o) {
    if (size == 0) {
      return false;
    }

    Iterator<E> it = this.iterator();

    while (it.hasNext()) {
      if (o.equals(it.next())) {
        it.remove();
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean removeLastOccurrence(Object o) {
    if (size == 0) {
      return false;
    }
    Iterator<E> it = this.descendingIterator();

    while (it.hasNext()) {
      if (o.equals(it.next())) {
        it.remove();
        return true;
      }
    }

    return false;
  }

  @Override
  public Iterator<E> descendingIterator() {
    return new CustomIteratorDescending();
  }

  @Override
  public Iterator<E> iterator() {
    return new CustomIterator();
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof HybridDeque)) {
      return false;
    }
    @SuppressWarnings("unchecked")
    HybridDeque<E> valDeque = (HybridDeque<E>) other;
    if (this.size == 0 && valDeque.size == 0) {
      return true;
    }
    if (this.size != valDeque.size) {
      return false;
    }

    Iterator<E> otherdeque = valDeque.iterator();
    Iterator<E> deque = this.iterator();

    while (otherdeque.hasNext()) {
      if (!otherdeque.next().equals(deque.next())) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void clear() {
    Block clearBlock = new Block(null, null);
    leftCursor = new Cursor(clearBlock, CENTER + 1);
    rightCursor = new Cursor(clearBlock, CENTER);
    size = 0;
  }


  private class CustomIterator implements Iterator<E> {
    private Cursor removeCursorIndex;
    private Cursor cursor = leftCursor;

    private boolean removeAllowed = false;

    @Override
    public boolean hasNext() {
      return cursor != null && cursor.get() != null;
      // cursor != null && cursor.get() != null
    }

    @Override
    public E next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      removeAllowed = true;
      E temp = cursor.get();
      removeCursorIndex = cursor;
      cursor = cursor.next();

      return temp;
    }

    @Override
    public void remove() {
      if (!removeAllowed) {
        throw new IllegalStateException();
      }
      removeCursorIndex.set(null);
      removeCursorIndex = null;
      size--;
      removeHelper(leftCursor);
      removeAllowed = false;
      rightCursor = rightCursor.prev();
      // TA help right here with an Edge Case
      // TA Riley White I think helped here
      if (cursor != null) {
        cursor = cursor.prev();
      }
    }
  }

  private class CustomIteratorDescending implements Iterator<E> {
    private Cursor cursor = rightCursor;
    private Cursor removeCursorIndex;
    private boolean removeAllowed = false;

    @Override
    public boolean hasNext() {
      return cursor != null && cursor.get() != null;

    }

    @Override
    public E next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      removeCursorIndex = cursor;
      removeAllowed = true;
      E temp = cursor.get();
      cursor = cursor.prev();
      // New Cursor set that as remove Cursor as holding place
      return temp;
    }

    @Override
    public void remove() {
      if (!removeAllowed) {
        throw new IllegalStateException();
      }
      removeCursorIndex.set(null);
      removeCursorIndex = null;
      size--;
      removeHelper(leftCursor);
      removeAllowed = false;
      rightCursor = rightCursor.prev();

    }
  }

  private void removeHelper(Cursor list) {
    boolean remover = false;
    int itemsDone = 0;
    while (itemsDone < size) {
      if (list.get() == null) {
        remover = true;
      }

      if (remover) {
        // System.out.println(list.block.elements[list.index]);
        if (list.index + 1 >= BLOCK_SIZE) {
          list.set(list.next().block.elements[list.next().index]);

        } else {
          list.set(list.block.elements[list.index + 1]);
        }
      }
      list = list.next();
      itemsDone++;
    }
    list.set(null);

  }

  // ----------------------------------------------------
  // ADD UNIMPLEMENTED DEQUE METHODS HERE.
  // (You Don't need to provide JavaDoc comments for inherited methods. They
  // will inherit appropriate comments from Deque.)

  // -------------------------------------------------
  // METHODS THAT NEED TO BE IMPLEMENTED FOR PART 1:
  //
  // constructor
  // clear
  // offerLast
  // offerFirst
  // peekFirst
  // peekLast
  // pollFirst
  // pollLast
  // equals
  // iterator (without removal)
  // descendingIterator (without removal)

  // -------------------------------------------------
  // METHODS THAT NEED TO BE IMPLEMENTED FOR PART 2:
  //
  // removeFirstOccurrence
  // removeLastOccurrence
  // remove methods for iterators


}
