package ads1ss11.pa1;


/**
 * Klasse zur Repr&auml;sentation einer doppelt verketteten Liste.
 *
 * <p>
 * <b>WICHTIG:</b> Nehmen Sie keine &Auml;nderungen in dieser Klasse vor. Bei
 * der Abgabe werden diese &Auml;nderungen verworfen und es k&ouml;nnte dadurch
 * passieren, dass Ihr Programm somit nicht mehr korrekt funktioniert.
 * </p>
 */
public class DoublyLinkedList {
	
	private static int counter=0;
	
	/** Zeiger auf das erste Element in der Liste */
	public ListElement first;

	/**
	 * Erzeugt eine doppelt verkettete Liste mit <code>start</code> als 
	 * Startelement.
	 * 
	 * @param start	das erste Element der Liste.
	 */
	protected DoublyLinkedList(ListElement start) {
		first = start;
		first.next = first;
		first.prev = first;
		if(counter == Integer.MAX_VALUE) {
			System.out.println("Zu viele DoublyLinkedLists angelegt!");
			System.exit(1);
		}
		counter++;
	}

	/**
	 * @return counter
	 */
	public static int getCounter() {
		return counter;
	}
	
	
	
}
