package com.jumbo.tools.input.console;

import com.jumbo.components.interfaces.ParametrizedAction;

public class JumboCommand {
	private final String input, desc, usage;
	private final ParametrizedAction<String[]> action;

	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}

	/**
	 * @return the action
	 */
	public ParametrizedAction<String[]> getAction() {
		return action;
	}

	public JumboCommand(String command, String help, String usage, ParametrizedAction<String[]> action) {
		this.desc = help;
		this.usage = usage;
		this.input = command;
		this.action = action;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @return the usage
	 */
	public String getUsage() {
		return usage;
	}
}
