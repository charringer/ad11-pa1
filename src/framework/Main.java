package ads1ss11.pa1;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeSet;


/**
 * Diese Klasse enth&auml;lt nur die {@link #main main()}-Methode zum starten
 * des Programms, sowie {@link #printDebug(String)} zum Ausgeben von Debug
 * Meldungen.
 * 
 * <p>
 * <b>WICHTIG:</b> Nehmen Sie keine &Auml;nderungen in dieser Klasse vor. Bei
 * der Abgabe werden diese &Auml;nderungen verworfen und es k&ouml;nnte dadurch
 * passieren, dass Ihr Programm somit nicht mehr korrekt funktioniert.
 * </p>
 */
public class Main {

	/**
	 * Der Name der Datei, aus der die Testinstanz auszulesen ist. Ist <code>
	 * null</code>, wenn von {@link System#in} eingelesen wird.
	 */
	private static String fileName = null;

	/** Der abgeschnittene Pfad */
	private static String choppedFileName;

	/** Test flag f&uuml;r Laufzeit Ausgabe */
	private static boolean test = false;

	/** Debug flag f&uuml;r zus&auml;tzliche Debug Ausgaben */
	private static boolean debug = false;
	
	private static TreeSet<Integer> input;

	/**
	 * Gibt die Meldung <code>msg</code> aus und beendet das Programm.
	 * 
	 * @param msg
	 *            Die Meldung die ausgegeben werden soll.
	 */
	private static void bailOut(String msg) {
		System.out.println();
		System.err.println((test ? choppedFileName + ": "
				: "") + "ERR " + msg);
		System.exit(1);
	}

	/**
	 * Generates a chopped String representation of the filename.
	 */
	private static void chopFileName() {
		if (fileName == null) {
			choppedFileName = "System.in";
			return;
		}

		int i = fileName.lastIndexOf(File.separatorChar);

		if (i > 0)
			i = fileName.lastIndexOf(File.separatorChar, i - 1);
		if (i == -1)
			i = 0;

		choppedFileName = ((i > 0) ? "..." : "") + fileName.substring(i);
	}

	/**
	 * Gibt eine debugging Meldung aus. Wenn das Programm mit <code>-d</code>
	 * gestartet wurde, wird <code>msg</code> zusammen mit dem Dateinamen der
	 * Inputinstanz ausgegeben, ansonsten macht diese Methode nichts.
	 * 
	 * @param msg
	 *            Text der ausgegeben werden soll.
	 */
	public static void printDebug(String msg) {
		if (!debug)
			return;

		System.out.println(choppedFileName + ": DBG " + msg);
	}

	/**
	 * Liest die Daten einer Testinstanz ein und f&uuml;gt sie in eine
	 * {@link DoublyLinkedList doppelt verkettete Liste} ein. Danach 
	 * wird {@link Sorter#mergesort(DoublyLinkedList, int)} aufgerufen,
	 * wo die Liste mittels Mergesort sortiert werden soll.
	 * 
	 * <p>
	 * Zum Schluss wird &uuml;berpr&uuml;ft, ob die Liste korrekt 
	 * sortiert wurde und eine entsprechende Meldung ausgegeben.
	 * </p>
	 * 
	 * <p>
	 * Wenn auf der Kommandozeile die Option <code>-d</code> angegeben wird,
	 * werden s&auml;mtliche Strings, die an {@link Main#printDebug(String)}
	 * &uuml;bergeben werden, ausgegeben.
	 * </p>
	 * 
	 * <p>
	 * Der erste String in <code>args</code>, der <em>nicht</em> mit <code>-d
	 * </code>, oder <code>-t</code> beginnt, wird als der Pfad zur Datei
	 * interpretiert, aus der die Testinstanz auszulesen ist. Alle nachfolgenden
	 * Parameter werden ignoriert. Wird kein Dateiname angegeben, wird die
	 * Testinstanz &uuml;ber {@link System#in} eingelesen.
	 * </p>
	 * 
	 * @param args
	 *            Die von der Kommandozeile &uuml;bergebene Argumente. Die
	 *            Option <code>-d</code> aktiviert debug-Ausgaben &uuml;ber
	 *            {@link #printDebug(String)}, <code>-t</code> gibt
	 *            zus&auml;tzlich Dateiname und Laufzeit aus. Der erste andere
	 *            String wird als Dateiname interpretiert.
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		long offs = end - start;

		Scanner s = processArgs(args);
		chopFileName();
		// if (debug) System.out.println("# " + fileName);

		try {
			// Setting security manager
			SecurityManager sm = new ADS1SecurityManager();
			System.setSecurityManager(sm);
		} catch (SecurityException e) {
			bailOut("Error: could not set security manager: " + e);
		}

		try {
			DoublyLinkedList list = null;
			int val;
			ListElement tmp;

			if (s.hasNextLine()) {
				val = Integer.valueOf(s.nextLine());
				list = new DoublyLinkedList(tmp = new ListElement(val));
				input = new TreeSet<Integer>();
				input.add(tmp.getId());
			} else {
				bailOut("Leere Inputdatei");
			}
			
			while (s.hasNextLine()) {
				val = Integer.valueOf(s.nextLine());
				list.first.prev.next = tmp = new ListElement(val); // nachfolger von last auf neu
				list.first.prev.next.prev = list.first.prev; // vorgaenger von neu auf last
				list.first.prev.next.next = list.first; // nachfolger von neu auf first
				list.first.prev = list.first.prev.next; // vorgaenger von first auf neu
				input.add(tmp.getId());
			}
			
			int dll_counter = DoublyLinkedList.getCounter();
			int le_counter = ListElement.getCounter();
			
			Sorter sort = new Sorter();

			start = System.currentTimeMillis();
			
			DoublyLinkedList result = sort.mergesort(list, input.size());

			end = System.currentTimeMillis();

			StringBuffer msg = new StringBuffer(test ? choppedFileName + ": "
					: "");

			long sum = end - start - offs;
			
			if (le_counter != ListElement.getCounter()){ 
				bailOut("Es wurden zusaetzliche ListElement Objekte erzeugt!");
			}
			
			if (dll_counter != DoublyLinkedList.getCounter()) {
				bailOut("Es wurden zusaetzliche DoublyLinkedList Objekte erzeugt!");
			}
			
			// Ergebnis checken und ausgeben
			if (result == null)
				bailOut("Rueckgabeliste ist null");
			
			StringBuffer sortedList = new StringBuffer();
			ListElement le = result.first;
			while (!le.equals(result.first.prev)) {
				if (debug)
					sortedList.append(le.getKey() + ", ");
				
				if (!input.remove((Integer)le.getId())) {
					if (debug)
						sortedList.append(le.next.getKey());
					printDebug(sortedList.toString());
					bailOut("Rueckgabeliste enthaelt nicht die Eingabewerte");
				}
				
				if (!le.next.prev.equals(le) || !le.prev.next.equals(le)) {
					if (debug)
						sortedList.append(le.next.getKey());
					printDebug(sortedList.toString());
					bailOut("Inkonsistente Liste");
				}
				
				if (le.next.getKey() < le.getKey()) {
					if (debug)
						sortedList.append(le.next.getKey());
					printDebug(sortedList.toString());
					bailOut("Liste ist nicht aufsteigend sortiert");
				}
				le = le.next;
			}
			
			if (debug)
				sortedList.append(le.getKey());
			printDebug(sortedList.toString());
			
			if (!le.next.prev.equals(le) || !le.prev.next.equals(le))
				bailOut("Inkonsistente Liste");
			
			if (!input.remove((Integer)le.getId()))
				bailOut("Rueckgabeliste enthaelt nicht die Eingabewerte");
			
			if (!input.isEmpty())
				bailOut("Rueckgabeliste enthaelt nicht alle Eingabewerte");
			
			msg.append("OK");

			if (test)
				msg.append(", Zeit: " + sum + " ms");

			System.out.println("");
			System.out.println(msg.toString());

		} catch (SecurityException se) {
			bailOut("Unerlaubter Funktionsaufruf: \"" + se.toString() + "\"");
		} catch (NumberFormatException e) {
			bailOut("Falsches Inputformat: \"" + e.toString() + "\"");
		} catch (Exception e) {
			e.printStackTrace();
			bailOut("caught exception \"" + e.toString() + "\"");
		}

	}

	/**
	 * &Ouml;ffnet die Eingabedatei und gibt einen {@link Scanner}
	 * zur&uuml;ck, der von ihr liest. Falls kein Dateiname angegeben wurde, wird eine Zeile von
	 * {@link System#in} gelesen.
	 * 
	 * @return Einen {@link Scanner} der von der Eingabedatei liest.
	 */
	private static Scanner openInputFile() {
		if (fileName != null)
			try {
				return new Scanner(new File(fileName));
			} catch (NoSuchElementException e) {
				bailOut("\"" + fileName + "\" is empty");
			} catch (Exception e) {
				bailOut("could not open \"" + fileName + "\" for reading");
			}

		return new Scanner(System.in);

	}

	/**
	 * Interpretiert die Parameter, die dem Programm &uuml;bergeben wurden und
	 * gibt einen {@link Scanner} zur&uuml;ck, der von der Testinstanz liest.
	 * 
	 * @param args
	 *            Die Eingabeparameter
	 * @return Einen {@link Scanner} der von der Eingabedatei liest.
	 */
	private static Scanner processArgs(String[] args) {
		for (String a : args) {
			if (a.equals("-t")) {
				test = true;
			} else if (a.equals("-d")) {
				debug = test = true;
			} else if (fileName == null) {
				fileName = a;

				break;
			}
		}

		return openInputFile();
	}

	/**
	 * The constructor is private to hide it from JavaDoc.
	 * 
	 */
	private Main() {
	}

}
