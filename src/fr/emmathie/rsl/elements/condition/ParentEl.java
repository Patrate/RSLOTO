package fr.emmathie.rsl.elements.condition;

import java.util.List;

import fr.emmathie.rsl.Main;
import fr.emmathie.rsl.Main.PRINT_LEVEL;
import fr.emmathie.rsl.elements.Element;

public interface ParentEl {

	public List<Element> getSubEl();

	public default boolean subExecute() {
		int i = 0;
		List<Element> elements = getSubEl();
		Main.print("START SUB SCRIPT", PRINT_LEVEL.TRACE);
		while (i < elements.size()) {
			Main.print("\tEXECUTE " + elements.get(i), PRINT_LEVEL.TRACE);
			if (elements.get(i).execute()) {
				i++;
				Main.print("\tGOT TRUE", PRINT_LEVEL.TRACE);
			} else {
				Main.print("\tGOT FALSE, EXITING", PRINT_LEVEL.TRACE);
				return false;
			}
		}
		Main.print("END SUB SCRIPT", PRINT_LEVEL.TRACE);
		return true;
	}
	
	public abstract void deleteme();
}
