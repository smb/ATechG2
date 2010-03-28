package de.adv.atech.roboter.commons.commands.rvm1;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Rvm1CommandInternals;

public class MoveToCoordinates extends AbstractCommand implements
		Rvm1CommandInternals {

	public Double X;

	public Double Y;

	public Double Z;

	public enum Parameter {
		X, Y, Z
	}

	public MoveToCoordinates() {
		super();
		init(this, Parameter.class);
		this.commandName = "MoveTo";
	}

	@Override
	public String getCode(Object informationRef) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCommandCodeList(Object informationRef) {
        List<String> result = new ArrayList<String>();
        NumberFormat nf = NumberFormat.getInstance(new Locale("en"));
        nf.setMaximumFractionDigits(1);
        String strX = nf.format(X);
        String strY = nf.format(Y);
        String strZ = nf.format(Z);
        result.add("mp " + strX + "," + strY + "," + strZ);
        return result;
	}
}
