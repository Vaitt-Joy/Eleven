package com.eleven.http.gdo.helper;

/**
 * @author vic Zhou
 * @time 2017-12-31 2:06
 * @des 日志的管理
 */

public class LogHelper {
    private boolean isDebugMode;
    private LogPrinter mLogPrinter;
    private StringBuilder mLogAppender;

    public static LogHelper get(boolean debug, LogPrinter logPrinter) {
        LogHelper helper = new LogHelper(logPrinter);
        helper.isDebugMode(debug);
        return helper;
    }

    private LogHelper(LogPrinter logPrinter) {
        mLogPrinter = logPrinter;
    }

    public void isDebugMode(boolean debug) {
        isDebugMode = debug;
    }

    public boolean isDebugMode() {
        return isDebugMode;
    }

    public StringBuilder getLogAppender() {
        if (mLogAppender == null) {
            mLogAppender = new StringBuilder();
        }
        return mLogAppender;
    }

    public void print() {
        if (!isDebugMode || mLogAppender == null || mLogAppender.length() == 0) {
            return;
        }
        print(mLogAppender.toString());
    }

    public void print(String content) {
        if (!isDebugMode) {
            return;
        }

        mLogPrinter.print("Gdo", "*******************--BEGIN--*******************");
        mLogPrinter.print("Gdo", content);
        mLogPrinter.print("Gdo", "********************--END--********************");
    }

    public interface LogPrinter {
        void print(String tag, String content);
    }
}
