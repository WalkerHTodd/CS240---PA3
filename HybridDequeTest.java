import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 * Hybrid Deque Test Class - Testing Edge Cases and getting Line Coverage
 *
 * 
 * @author Walker Todd
 * @version 4/2/2023
 * 
 * 
 *          My work complies with the JMU Honor Code and if any help was recieved it was from a TA
 *          and was listed where the help was recieved
 *
 */
class HybridDequeTest {

  @Test
  void testSize() {
    HybridDeque<Integer> deque = new HybridDeque<Integer>();

    assertEquals(0, deque.size());

    deque.offerFirst(1);

    assertEquals(1, deque.size());

  }

  @Test
  void testBlockSet() {

    HybridDeque.setBlockSize(64);
  }


  @Test
  void testClear() {
    HybridDeque<String> deque = new HybridDeque<String>();

    deque.offerFirst("What");
    deque.offerFirst("Hello");
    deque.offerFirst("How");
    deque.offerFirst("Are");

    deque.clear();

    assertEquals(null, deque.peekFirst());
    assertEquals(0, deque.size());

  }

  @Test
  void testHybridDeque() {
    HybridDeque<String> deque = new HybridDeque<String>();

    assertEquals(0, deque.size());

    assertEquals(null, deque.peekFirst());

    deque.equals(deque);

  }

  @Test
  void testEquals() {
    HybridDeque<String> emptydeque = new HybridDeque<String>();

    HybridDeque<String> emptydeque1 = new HybridDeque<String>();

    assertEquals(true, emptydeque.equals(emptydeque1));

    emptydeque1.offerFirst("What");
    assertEquals(false, emptydeque.equals(emptydeque1));

    emptydeque1.pollFirst();
    emptydeque.offerFirst("What");
    assertEquals(false, emptydeque.equals(emptydeque1));


    HybridDeque<String> deque = new HybridDeque<String>();

    HybridDeque<String> deque2 = new HybridDeque<String>();


    deque.offerFirst("What");
    deque.offerFirst("The");
    deque.offerFirst("Heck");

    deque2.offerFirst("What");
    deque2.offerFirst("The");
    deque2.offerFirst("Heck");

    // Null Check
    assertEquals(false, deque.equals(null));

    assertEquals(true, deque.equals(deque2));

    assertEquals(false, deque.equals(1));

    HybridDeque<String> deque3 = new HybridDeque<String>();

    HybridDeque<String> deque4 = new HybridDeque<String>();

    deque3.offerFirst("What");
    deque3.offerFirst("The");
    deque3.offerFirst("Heck");

    deque4.offerFirst("What");
    deque4.offerFirst("The");
    deque4.offerFirst("Tree");

    assertEquals(false, deque3.equals(deque4));

    assertEquals(false, deque3.equals(deque4));

    // HybridDeque<String> empty = new HybridDeque<String>();
    //
    // HybridDeque<String> empty1 = new HybridDeque<String>();

    deque3.offerFirst("What");
    deque3.offerFirst("The");
    deque3.offerFirst("Heck");

    deque4.offerFirst("What");
    deque4.offerFirst("The");

    assertEquals(false, deque3.equals(deque4));

  }

  @Test
  void testOfferFirst() {
    HybridDeque<String> deque = new HybridDeque<String>();
    // Null Check
    assertThrows(NullPointerException.class, () -> {
      deque.offerFirst(null);
    });

    deque.offerFirst("What");

    assertEquals("What", deque.peekFirst());

    deque.offerFirst("The");

    assertEquals("The", deque.peekFirst());

    deque.offerFirst("Is");


    assertEquals("Is", deque.peekFirst());
    deque.offerFirst("Yo");

    assertEquals("Yo", deque.peekFirst());

    deque.offerFirst("mama");

    assertEquals("mama", deque.peekFirst());

    deque.offerFirst("hi");

    assertEquals("hi", deque.peekFirst());


  }

  @Test
  void testOfferLast() {
    HybridDeque<String> deque = new HybridDeque<String>();

    // Null Check
    assertThrows(NullPointerException.class, () -> {
      deque.offerLast(null);
    });

    deque.offerLast("What");

    assertEquals("What", deque.peekLast());

    deque.offerLast("The");

    assertEquals("The", deque.peekLast());

    deque.offerLast("Is");

    assertEquals("Is", deque.peekLast());

    deque.offerLast("What");
    deque.offerLast("Is");
    deque.offerLast("Love");
    deque.offerLast("Dont");
    // New block

    assertEquals("Dont", deque.peekLast());

  }

  @Test
  void testPollFirst() {

    HybridDeque<String> deque = new HybridDeque<String>();

    // Empty
    assertEquals(null, deque.pollFirst());

    deque.offerFirst("What");

    assertEquals("What", deque.pollFirst());

    deque.offerFirst("What");
    deque.offerFirst("The");
    // System.out.println("First: " + deque.toString());

    assertEquals("The", deque.pollFirst());
    // System.out.println("Sec: " + deque.toString());

    deque.offerFirst("What");
    deque.offerFirst("The");
    deque.offerFirst("What");
    deque.offerFirst("The");
    deque.offerFirst("What");
    deque.offerFirst("The");
    deque.offerFirst("What");
    deque.offerFirst("The");

    assertEquals("The", deque.pollFirst());

  }

  @Test
  void testPollLast() {

    HybridDeque<String> deque = new HybridDeque<String>();

    // Empty
    assertEquals(null, deque.pollLast());

    deque.offerLast("What");

    assertEquals("What", deque.pollLast());
    deque.offerLast("What");
    deque.offerLast("The");

    assertEquals("The", deque.pollLast());



  }

  @Test
  void testPeekFirst() {

    HybridDeque<String> deque = new HybridDeque<String>();

    // Empty
    assertEquals(null, deque.peekFirst());
    deque.offerFirst("What");

    assertEquals("What", deque.peekFirst());

    deque.offerFirst("The");

    assertEquals("The", deque.peekFirst());

  }

  @Test
  void testPeekLast() {
    HybridDeque<String> deque = new HybridDeque<String>();

    assertEquals(null, deque.peekLast());

    deque.offerLast("What");

    assertEquals("What", deque.peekLast());

    deque.offerLast("New");

    assertEquals("New", deque.peekLast());
  }

  @Test
  void testRemoveFirstOccurrence() {
    HybridDeque<String> edge = new HybridDeque<String>();
    edge.offerFirst("Six");
    edge.offerFirst("Six");
    edge.offerFirst("Six");

    assertFalse(edge.removeFirstOccurrence("Five"));

    assertTrue(edge.removeFirstOccurrence("Six"));

    HybridDeque<String> deque = new HybridDeque<String>();
    // Empty
    assertFalse(deque.removeFirstOccurrence("E"));

    deque.offerFirst("Nine");
    deque.offerFirst("Durf");
    deque.offerFirst("Four");
    deque.offerFirst("Five");
    // System.out.println("First" + deque.toString());

    deque.removeFirstOccurrence("Nine");
    // System.out.println("Second" + deque.toString());

    assertEquals("Five", deque.peekFirst());

    assertEquals("Durf", deque.peekLast());

    deque.offerFirst("Nine");
    deque.offerFirst("Two");
    deque.offerFirst("Three");
    deque.offerFirst("Four");
    deque.offerFirst("Five");
    deque.offerFirst("Six");
    deque.offerLast("Nine");
    deque.offerLast("Two");
    deque.offerLast("Three");
    deque.offerLast("Four");
    deque.offerLast("Five");
    deque.offerLast("Six");

    deque.removeFirstOccurrence("Six");
    deque.removeFirstOccurrence("Three");


    assertEquals("Five", deque.peekFirst());
    // System.out.println("First" + deque.toString());


    assertEquals("Six", deque.peekLast());

    deque.offerFirst("Nine");
    deque.offerFirst("Two");
    deque.offerFirst("Three");
    deque.offerFirst("Four");
    deque.offerFirst("Five");
    deque.offerFirst("Six");
    deque.offerFirst("Nine");
    deque.offerFirst("Two");
    deque.offerFirst("Three");
    deque.offerFirst("Four");
    deque.offerFirst("Five");
    deque.offerFirst("Six");

    assertEquals("Six", deque.peekLast());


  }

  @Test
  void testRemoveLastOccurrence() {
    HybridDeque<String> edge = new HybridDeque<String>();
    edge.offerLast("Six");

    assertFalse(edge.removeLastOccurrence("Five"));

    assertTrue(edge.removeLastOccurrence("Six"));


    HybridDeque<String> deque = new HybridDeque<String>();
    // Empty
    assertFalse(deque.removeLastOccurrence("E"));
    deque.offerLast("What");
    deque.offerLast("The");
    deque.offerLast("Beck");
    deque.offerLast("Do");
    deque.offerLast("You");
    deque.offerLast("What");
    // System.out.println("First: " + deque.toString());

    deque.removeLastOccurrence("What");
    // System.out.println("Second" + deque.toString());

    assertEquals("You", deque.peekLast());
    assertEquals("What", deque.peekFirst());


    deque.offerLast("Six");
    deque.offerLast("Two");
    deque.offerLast("Three");
    deque.offerLast("Four");
    deque.offerLast("Five");
    deque.offerLast("Six");

    deque.offerFirst("New First?");

    deque.removeLastOccurrence("Six");

    assertEquals("Five", deque.peekLast());

    assertEquals("New First?", deque.peekFirst());
  }

  @Test
  void testHasNext() {
    HybridDeque<String> deque = new HybridDeque<String>();

    deque.offerFirst("What");
    deque.offerFirst("The");
    deque.offerFirst("Heck");
    deque.offerFirst("Do");
    deque.offerFirst("You");
    deque.offerFirst("Want");
    deque.offerFirst("What");
    deque.offerFirst("The");
    deque.offerFirst("Heck");
    deque.offerFirst("Do");
    deque.offerFirst("You");
    deque.offerFirst("Want");

    Iterator<String> it = deque.iterator();
    Object[] array = deque.toArray();

    assertTrue(it.hasNext());
    int i = 0;

    while (it.hasNext()) {
      // System.out.println("Deque:" + array[i]);
      // System.out.println("\n");

      assertEquals(it.next(), array[i]);

      i++;
    }
    // REMOVE TEST

    HybridDeque<String> empty1 = new HybridDeque<String>();

    Iterator<String> empty = empty1.iterator();

    assertFalse(empty.hasNext());

    assertThrows(IllegalStateException.class, () -> {
      empty.remove();
    });
    assertThrows(NoSuchElementException.class, () -> {
      empty.next();
    });
    HybridDeque<String> removeDeque = new HybridDeque<String>();


    removeDeque.offerFirst("One");
    removeDeque.offerFirst("Two");
    removeDeque.offerFirst("Three");
    removeDeque.offerFirst("Four");

    Iterator<String> itDescending = removeDeque.iterator();

    itDescending.next();
    itDescending.remove();
    assertEquals("Three", itDescending.next());

    itDescending.next();
    itDescending.remove();
    assertEquals("One", itDescending.next());
    itDescending.remove();
    assertFalse(it.hasNext());

    assertThrows(IllegalStateException.class, () -> {
      itDescending.remove();
    });
    assertFalse(itDescending.hasNext());
    assertThrows(NoSuchElementException.class, () -> {
      itDescending.next();
    });

  }

  @Test
  void testDescendingIterator() {


    HybridDeque<String> deque = new HybridDeque<String>();

    deque.offerFirst("What");
    deque.offerFirst("The");
    deque.offerFirst("Heck");
    deque.offerFirst("Do");
    deque.offerFirst("You");
    deque.offerFirst("Want");
    deque.offerFirst("What");
    deque.offerFirst("The");
    deque.offerFirst("Heck");
    deque.offerFirst("Do");
    deque.offerFirst("You");
    deque.offerFirst("Want");

    Iterator<String> it = deque.descendingIterator();
    Object[] array = deque.toArray();

    int i = deque.size() - 1;

    while (it.hasNext()) {
      // System.out.println("Deque:" + array[i]);
      // System.out.println("\n");

      assertEquals(it.next(), array[i]);

      i--;
    }
    // REMOVE TEST



    HybridDeque<String> removeDeque = new HybridDeque<String>();

    removeDeque.offerFirst("One");
    removeDeque.offerFirst("Two");
    removeDeque.offerFirst("Three");
    removeDeque.offerFirst("Four");

    Iterator<String> itDescending = removeDeque.descendingIterator();

    itDescending.next();
    itDescending.remove();
    assertEquals("Two", itDescending.next());

    itDescending.next();
    itDescending.remove();
    assertEquals("Four", itDescending.next());
    itDescending.remove();

    assertThrows(IllegalStateException.class, () -> {
      itDescending.remove();
    });
    assertThrows(NoSuchElementException.class, () -> {
      itDescending.next();
    });
  }

  @Test
  void testIterator() {
    HybridDeque<String> hasNextEmpty = new HybridDeque<String>();
    Iterator<String> hasnextEmpty1 = hasNextEmpty.iterator();
    assertEquals(false, hasnextEmpty1.hasNext());
    assertEquals(false, hasnextEmpty1.hasNext());

    HybridDeque<String> removeDeque1 = new HybridDeque<String>();
    removeDeque1.offerLast("One");
    removeDeque1.offerLast("Two");
    removeDeque1.offerLast("Three");
    removeDeque1.offerLast("Four");
    // Object[] array2 = removeDeque1.toArray();
    // for (int i = 0; i < array2.length; i++) {
    // System.out.print(" " + array2[i]);
    //
    // }

    HybridDeque<String> deque = new HybridDeque<String>();

    deque.offerFirst("What");
    deque.offerFirst("The");
    deque.offerFirst("Heck");
    deque.offerFirst("Do");
    deque.offerFirst("You");
    deque.offerFirst("Want");

    deque.remove();
    Iterator<String> nothingChange = deque.iterator();
    Object[] nothingChangeArray = deque.toArray();

    int k = 0;
    while (nothingChange.hasNext()) {
      // System.out.println("Deque:" + array[i]);
      // System.out.println("\n");

      assertEquals(nothingChange.next(), nothingChangeArray[k]);
      k++;
    }
    HybridDeque<String> removeDeque = new HybridDeque<String>();

    removeDeque.offerLast("One");
    removeDeque.offerLast("Two");
    removeDeque.offerLast("Three");
    removeDeque.offerLast("Four");
    removeDeque.offerLast("Five");
    removeDeque.offerLast("Six");

    Iterator<String> it = deque.iterator();
    Object[] array = deque.toArray();

    int i = 0;
    while (it.hasNext()) {
      // System.out.println("Deque:" + array[i]);
      // System.out.println("\n");

      assertEquals(it.next(), array[i]);
      i++;
    }
    Iterator<String> it2 = removeDeque.iterator();

    HybridDeque<String> removeDequeCorrect = new HybridDeque<String>();
    removeDequeCorrect.offerLast("One");
    removeDequeCorrect.offerLast("Two");
    removeDequeCorrect.offerLast("Three");
    removeDequeCorrect.offerLast("Four");
    removeDequeCorrect.offerLast("Five");
    removeDequeCorrect.offerLast("Six");

    Object[] array1 = removeDequeCorrect.toArray();

    int j = 0;
    while (it2.hasNext()) {

      assertEquals(it2.next(), array1[j]);
      j++;

    }


    HybridDeque<String> edgecases = new HybridDeque<String>();


    edgecases.offerLast("One");
    edgecases.offerLast("Two");
    edgecases.offerLast("Four");
    edgecases.offerLast("Five");
    edgecases.offerLast("Six");
    Iterator<String> it3 = edgecases.iterator();

    it3.next();
    it3.remove();
    assertThrows(IllegalStateException.class, () -> {
      it3.remove();
    });
    HybridDeque<String> empty = new HybridDeque<String>();

    Iterator<String> empty1 = empty.iterator();

    assertThrows(NoSuchElementException.class, () -> {
      empty1.next();
    });



  }

  @Test
  void testIteratorRemoveAll() {
    HybridDeque<String> edgecases = new HybridDeque<String>();

    edgecases.offerFirst("One");
    edgecases.offerFirst("One");
    edgecases.offerFirst("One");
    edgecases.offerFirst("One");

    
    edgecases.offerLast("One");
    edgecases.offerLast("Two");
    edgecases.offerLast("Three");
    edgecases.offerLast("Four");

    Iterator<String> it = edgecases.iterator();
    
    for(int i = 0; i < edgecases.size();i++) {
      it.next();
    }
    it.remove();
    // EdgeCase removing everything
    HybridDeque<String> removeAll = new HybridDeque<String>();
    removeAll.offerFirst("One");
    removeAll.offerFirst("Two");
    removeAll.offerFirst("Three");
    removeAll.offerFirst("Four");
    removeAll.offerFirst("One");
    removeAll.offerFirst("Two");
    removeAll.offerFirst("Three");
    removeAll.offerFirst("Four");
    removeAll.offerFirst("One");
    removeAll.offerFirst("Two");
    removeAll.offerFirst("Three");
    removeAll.offerFirst("Four");
    Iterator<String> empty1 = removeAll.iterator();

    while (empty1.hasNext()) {
      empty1.next();
      empty1.remove();

    }
  }

}


