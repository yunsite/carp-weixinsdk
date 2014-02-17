/**
  	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.voa.weixin.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author zhaiyuxin
 *
 */
public class LogUtil {
	private final static Log LOG = LogFactory.getLog(LogUtil.class);

	private static Method TRACE = null;
	private static Method DEBUG = null;
	private static Method INFO = null;
	private static Method WARN = null;
	private static Method ERROR = null;
	private static Method FATAL = null;

	static {
		try {
			TRACE = Log.class.getMethod("trace", new Class[] { Object.class });
			DEBUG = Log.class.getMethod("debug", new Class[] { Object.class });
			INFO = Log.class.getMethod("info", new Class[] { Object.class });
			WARN = Log.class.getMethod("warn", new Class[] { Object.class });
			ERROR = Log.class.getMethod("error", new Class[] { Object.class });
			FATAL = Log.class.getMethod("fatal", new Class[] { Object.class });
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Cannot init log methods", e);
			}
		}
	}

	public static PrintStream getTraceStream(final Log logger) {
		return getLogStream(logger, TRACE);
	}

	public static PrintStream getDebugStream(final Log logger) {
		return getLogStream(logger, DEBUG);
	}

	public static PrintStream getInfoStream(final Log logger) {
		return getLogStream(logger, INFO);
	}

	public static PrintStream getWarnStream(final Log logger) {
		return getLogStream(logger, WARN);
	}

	public static PrintStream getErrorStream(final Log logger) {
		return getLogStream(logger, ERROR);
	}

	public static PrintStream getFatalStream(final Log logger) {
		return getLogStream(logger, FATAL);
	}

	/** Returns a stream that, when written to, adds log lines. */
	private static PrintStream getLogStream(final Log logger,
			final Method method) {
		return new PrintStream(new ByteArrayOutputStream() {
			private int scan = 0;

			private boolean hasNewline() {
				for (; scan < count; scan++) {
					if (buf[scan] == '\n')
						return true;
				}
				return false;
			}

			@Override
			public void flush() throws IOException {
				if (!hasNewline())
					return;
				try {
					method.invoke(logger, new Object[] { toString().trim() });
				} catch (Exception e) {
					if (LOG.isFatalEnabled()) {
						LOG.fatal("Cannot log with method [" + method + "]", e);
					}
				}
				reset();
				scan = 0;
			}
		}, true);
	}
}
