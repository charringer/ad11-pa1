package ads1ss11.pa1;

/**
 * Klasse zur Kapselung eines Listenelements.
 * 
 * <p>
 * <b>WICHTIG:</b> Nehmen Sie keine &Auml;nderungen in dieser Klasse vor. Bei
 * der Abgabe werden diese &Auml;nderungen verworfen und es k&ouml;nnte dadurch
 * passieren, dass Ihr Programm somit nicht mehr korrekt funktioniert.
 * </p>
 */
public class ListElement {
	
	private static int counter = 0;
	
	private int id;
	
	@Override
	public String toString() {
		return ""+key+"";
	}

	/** Schl&uuml;ssel dieses Listenelements */
	private int key;
	
	/** Zeiger auf das vorhergehende Listenelement */
	public ListElement prev;
	
	/** Zeiger auf das nachfolgende Listenelement */
	public ListElement next;
	
	/**
	 * Erzeugt ein neues Listenelement mit dem Schl&uuml;ssel <code>_key</code>.
	 * 
	 * @param _key	der Schl&uuml;ssel f&uuml;r das neue Listenelement.
	 */
	protected ListElement(int _key) {
		key = _key;
		prev = null;
		next = null;
		if(counter == Integer.MAX_VALUE) {
			System.out.println("Zu viele ListElements angelegt!");
			System.exit(1);
		}
		counter++;
		id = counter;
	}

	/**
	 * Liefert den Schl&uuml;ssel dieses Listenelements zur&uuml;ck.
	 * 
	 * @return	den Schl&uuml;ssel dieses Listenelements.
	 */
	public int getKey() {
		return key;
	}
	
	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return counter
	 */
	public static int getCounter() {
		return counter;
	}
	
	
}
