package fr.emmathie.rsl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.emmathie.rsl.Main.PRINT_LEVEL;
import fr.emmathie.rsl.elements.Element;
import fr.emmathie.rsl.elements.Jump;

public class Script extends Thread implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3229816344910848077L;
	private String name;
	private List<Element> elements;

	public Script(String name) {
		this.name = name;
		this.elements = new ArrayList<Element>();
	}

	@Override
	public void run() {
		execute();
	}

	private Boolean execute() {
		int i = 0;
		Main.print("START SCRIPT", PRINT_LEVEL.TRACE);
		while (i < elements.size()) {
			Main.print("EXECUTE " + elements.get(i), PRINT_LEVEL.TRACE);
			Element current = elements.get(i);
			if (current.execute()) {
				Main.print("GOT TRUE", PRINT_LEVEL.TRACE);
				if (current instanceof Jump) {
					i = ((Jump) current).getJumpId();
					Main.print("JUMPING TO " + i, PRINT_LEVEL.TRACE);
				} else {
					i++;
				}

			} else {
				Main.print("GOT FALSE, EXITING", PRINT_LEVEL.TRACE);
				return false;
			}
		}
		Main.print("END SCRIPT");
		return true;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void addElement(Element e) {
		elements.add(e);
	}

	public void removeElement(Element e) {
		elements.remove(e);
	}

	public void moveElement(Element e, int newIndex) {
		if (elements.contains(e)) {
			elements.remove(e);
		}
		elements.add(newIndex, e);
	}

	public void setScriptName(String name) {
		this.name = name;
	}

	public String getScriptName() {
		return name;
	}

	@Override
	public String toString() {
		return "Script [name=" + name + ", elements=" + elements + "]";
	}
}
