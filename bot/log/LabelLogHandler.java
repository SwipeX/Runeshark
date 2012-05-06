package bot.log;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LabelLogHandler extends Handler {
	public final JLabel label = new JLabel();
	private final Color defaultColor;

	public LabelLogHandler() {
		super();
		defaultColor = label.getForeground();
	}

	@Override
	public void close() throws SecurityException {
	}

	@Override
	public void flush() {
	}

	@Override
	public void publish(final LogRecord record) {
		String msg = record.getMessage();
		if (record.getLevel().intValue() > Level.WARNING.intValue()) {
			label.setForeground(new Color(0xcc0000));
		} else {
			label.setForeground(defaultColor);
			msg += " ...";
		}
		label.setText(msg);
	}
}
