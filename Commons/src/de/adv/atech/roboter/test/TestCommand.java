package de.adv.atech.roboter.test;

import java.util.Iterator;

import de.adv.atech.roboter.commons.commands.DefaultMove;

public class TestCommand {

	public static void main(String[] args) {
		DefaultMove moveCommand = new DefaultMove();

		System.out.println("Parameterliste von Move: ");

		for (Iterator<String> it = moveCommand.getParameters().keySet()
				.iterator(); it.hasNext();) {
			System.out.println(" - " + it.next());
		}

		moveCommand.setParameter("X", new Double(123));

		Object param = moveCommand.getParameter("X");

		param = moveCommand.getParameter("X");

		System.out.println(param + " - " + param.getClass());
	}
}
