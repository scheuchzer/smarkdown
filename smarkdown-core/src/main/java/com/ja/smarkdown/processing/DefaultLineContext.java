package com.ja.smarkdown.processing;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.ja.smarkdown.util.ToString;

@RequiredArgsConstructor
@Slf4j
public class DefaultLineContext implements LineContext {

	private final String line;
	private final MetaData metaData;
	@Setter
	private Object currentOwner;
	private final List<Action> actions = new ArrayList<>();

	public String applyActions() {
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
			public String apply(final String result, final MetaData md) {
				String tmp = StringUtils.remove(result, line + "\n");
				if (tmp.equals(result)) {
					return StringUtils.trimToNull(StringUtils.remove(result, line));
				}
				return tmp;
			}
		});
	}

	@Override
	public void dontCare() {
		actions.add(new Action(currentOwner, ActionType.dontCare, null) {

			@Override
			public String apply(final String result, final MetaData md) {
				return result;
			}
		});
	}

	@Override
	public void addMetaData(final String key, final Object value) {
		actions.add(new Action(currentOwner, ActionType.addMetaData,
				new ToString("%s=%s", key, value)) {
			@Override
			public String apply(final String result, final MetaData md) {
				md.add(key, value);
				return result;
			}
		});
	}

	@Override
	public void insertBefore(final String content) {
		actions.add(new Action(currentOwner, ActionType.insertBefore, null) {

			@Override
			public String apply(final String result, final MetaData md) {
				return String.format("%s\n%s", content, result);
			}
		});
	}

	@Override
	public void insertAfter(final String content) {
		actions.add(new Action(currentOwner, ActionType.insertAfter, null) {

			@Override
			public String apply(final String result, final MetaData md) {
				return String.format("%s\n%s", result, content);
			}
		});
	}

	@Override
	public void custom(Action action) {
		actions.add(action);

	}

}
