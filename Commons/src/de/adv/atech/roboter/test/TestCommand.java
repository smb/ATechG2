package de.adv.atech.roboter.test;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map.Entry;

import de.adv.atech.roboter.commons.commands.DefaultMove;
import de.adv.atech.roboter.commons.exceptions.CommandException;

/**
 * 
 * @author sbu
 * 
 */
public class TestCommand {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DefaultMove moveCommand = new DefaultMove();

		System.out.println("Parameterliste von Move: ");

		for (Iterator<Entry<Enum<?>, Field>> it = moveCommand.getParameters()
				.entrySet().iterator(); it.hasNext();) {
			Entry<Enum<?>, Field> entry = it.next();

			System.out.println(" - " + entry.getKey() + " --> "
					+ entry.getValue());
		}

		try {
			moveCommand
					.setParameter(DefaultMove.Parameter.X, new Double("123"));
			moveCommand
					.setParameter(DefaultMove.Parameter.Y, new String("123"));

			Object param = moveCommand.getParameter(DefaultMove.Parameter.X);

			System.out.println(param + " - " + param.getClass());
		} catch (CommandException e) {
			e.dumpException();
		}
	}
}
