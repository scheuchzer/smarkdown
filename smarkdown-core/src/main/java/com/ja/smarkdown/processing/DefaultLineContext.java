package com.ja.smarkdown.processing;

import java.util.ArrayList;
import java.util.List;

import com.ja.smarkdown.util.ToString;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class DefaultLineContext<T> implements LineContext {

	private final String line;
	private final MetaData metaData;
	@Setter
	private T currentOwner;
	private final List<Action> actions = new ArrayList<>();

	String applyActions() {
		logActions();
		String newline = line;
		for (final Action action : actions) {
			newline = action.apply(newline, metaData);
		}
		return newline;
	}

	private void logActions() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Actions to apply to line\n");
		sb.append("Line=");
		sb.append(line);
		sb.append("\n");
		sb.append("Actions:\n");
		for (final Action action : actions) {
			sb.append("- ").append(action).append("\n");
		}
		log.info("{}", sb);

	}

	@Override
	public void remove() {
		actions.add(new Action(currentOwner, ActionType.remove, null) {

			@Override
			String apply(final String result, final MetaData md) {
				return null;

			}
		});
	}

	@Override
	public void dontCare() {
		actions.add(new Action(currentOwner, ActionType.dontCare, null) {

			@Override
			String apply(final String result, final MetaData md) {
				return result;
			}
		});
	}

	@Override
	public void addMetaData(final String key, final Object value) {
		actions.add(new Action(currentOwner, ActionType.addMetaData,
				new ToString("%s=%s", key, value)) {
			@Override
			String apply(final String result, final MetaData md) {
				md.add(key, value);
				return result;
			}
		});
	}

	private enum ActionType {
		remove, dontCare, addMetaData;
	};

	@Data
	@AllArgsConstructor
	@RequiredArgsConstructor
	abstract class Action {
		private final T owner;
		private final ActionType type;
		private Object comment;

		abstract String apply(final String line, final MetaData md);
	}

}
