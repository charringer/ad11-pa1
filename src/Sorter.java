package ads1ss11.pa1;

/**
 * Sorter Klasse in der die Methode {@link #mergesort(DoublyLinkedList, int)}
 * implementiert werden soll.
 * 
 * <p>
 * In dieser Klasse m&uuml;ssen Sie Ihren Code einf&uuml;gen und die Method
 * {@link #mergesort(DoublyLinkedList, int)} implementieren.
 * </p>
 * 
 * <p>
 * Sie k&ouml;nnen beliebige neue Variablen und Methoden in dieser Klasse
 * hinzuf&uuml;gen. 
 * </p>
 */

public class Sorter {

	/**
	 * MergeSort Implementierung
	 * 
	 * @param in Unsortierte Eingabefolge
	 * @param numOfElements Gr&ouml;&szlig;e der Eingabefolge 
	 * @return Sortierte Eingabefolge
	 */
	public DoublyLinkedList mergesort(DoublyLinkedList in, int numOfElements) {
		ListElement first = cyclicSinglyLinked(in);

		first = mergesort_intern(first, numOfElements);

		return cyclicDoublyLinked(in, first);
	}

	/**
	 * Wandelt in eine zyklische einfach verkettete Liste um.
	 *
	 * @param in zyklische doppelt verkettete Liste
	 * @return erstes Element der Liste
	 */
	protected ListElement cyclicSinglyLinked(DoublyLinkedList in) {
		//System.out.println("make cyclicSinglyLinked");

		ListElement current = in.first;
		while (current.next != in.first) {
			//System.out.println("original "+current);
			current.prev = null;
			current = current.next;
		}
		//System.out.println("original "+current);
		current.prev = null;
		current.next = in.first;
		return in.first;
	}

	/**
	 * Wandelt in eine zyklische doppelt verkettete Liste um.
	 *
	 * @param list Listen-Datenstruktur, in die geschrieben werden soll
	 * @param first erstes Element der zyklischen einfach verketteten Liste
	 * @return list
	 */
	protected DoublyLinkedList cyclicDoublyLinked(DoublyLinkedList list, ListElement first) {
		//System.out.println("make cyclicDoublyLinked");

		ListElement current = first;
		while (current.next != first) {
			//System.out.println("final "+current);
			current.next.prev = current;
			current = current.next;
		}
		//System.out.println("final "+current);
		current.next = first;
		first.prev = current;

		list.first = first;
		return list;
	}

	/**
	 * Mergesort.
	 *
	 * @param list zyklische einfach verkettete Liste
	 * @param numOfElements Anzahl der Elemente
	 * @return erstes Element der sortierten Liste
	 */
	protected ListElement mergesort_intern(ListElement list, int numOfElements) {
		//System.out.println("mergesort("+list2string(list)+")");
		if (numOfElements < 2)
			return list;

		ListElement l1, l2;
		int len1 = numOfElements/2;
		int len2 = numOfElements - len1;
		//System.out.println("splitting in "+len1+" and "+len2);

		l2 = split(list, len1);
		l1 = list;

		l1 = mergesort_intern(l1, len1);
		l2 = mergesort_intern(l2, len2);

		return merge(l1, l2);
	}

	/**
	 * Teile (zyklische einfach verkettete Liste) in zwei Listen.
	 *
	 * @param first erstes Element der ursprünglichen Liste
	 * @param skip &ge; 1
	 * @return erstes Element des abgespaltenen hinteren Teils der Liste
	 */
	protected ListElement split(ListElement first, int skip) {
		ListElement endOfFirst = first;
		for (int i = 0; i < skip-1; i++) {
			endOfFirst = endOfFirst.next;
		}
		ListElement endOfSecond = endOfFirst.next;
		while (endOfSecond.next != first) {
			endOfSecond = endOfSecond.next;
		}
		ListElement second = endOfFirst.next;
		endOfFirst.next = first;
		endOfSecond.next = second;

		return second;
	}

	/**
	 * Führt zwei sortierte (zyklische einfach verkettete) Listen zusammen.
	 *
	 * @param firsta erste Liste, &ne; null
	 * @param firstb zweite Liste, &ne; null
	 * @return erstes Element der zusammengeführten Liste
	 */
	protected ListElement merge(ListElement firsta, ListElement firstb) {
		//System.out.println("merge("+list2string(firsta)+","+list2string(firstb)+")");

		ListElement a = firsta;
		ListElement b = firstb;
		ListElement current = null;
		if (a.getKey() <= b.getKey()) {
			current = a;
			a = (a.next==firsta) ? null : a.next;
		} else {
			current = b;
			b = (b.next==firstb) ? null : b.next;
		}
		//System.out.println("merge "+current);
		ListElement first = current;

		while (a != null || b != null) {
			if (b == null || (a != null && a.getKey() <= b.getKey())) {
				current.next = a;
				a = (a.next==firsta) ? null : a.next;
			} else {
				current.next = b;
				b = (b.next==firstb) ? null : b.next;
			}
			current = current.next;
			//System.out.println("merge "+current);
		}
		current.next = first;

		//System.out.println("result of merge: "+list2string(first));
		return first;
	}

	/**
	 * Liste anzeigen.
	 *
	 * @param first erstes Element einer zyklischen oder zyklischen Liste, &ne; null
	 */
	protected String list2string(ListElement first) {
		if (first == null) {
			return "<>";
		} else  {
			String output = "<"+first;
			ListElement current = first.next;
			while (current != null && current != first) {
				output += " "+current;
				current = current.next;
			}
			output+=">";
			return output;
		}
	}
}
