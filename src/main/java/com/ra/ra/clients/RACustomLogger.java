package com.ra.ra.clients;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;

/**

 * A wrapper class which takes a logger as constructor argument and offers a

 * PrintStream whose flush method writes the written content to the supplied

 * logger (debug level).

 * <p>

 * Usage:<br>

 * initializing in @BeforeClass of the unit test:

 * <p>

 * <pre>

 * ToLoggerPrintStream loggerPrintStream = new ToLoggerPrintStream(myLog);

 * RestAssured.config = RestAssured.config().logConfig(new LogConfig(loggerPrintStream.getPrintStream(), true));

 * </pre>

 * <p>

 * will redirect all log outputs of a ValidatableResponse to the supplied

 * logger:

 * <p>

 * <pre>

 * resp.then().log().all(true);

 * </pre>

 *

 * @author Heri Bender

 * @version 1.0 (28.10.2015)

 */
public class RACustomLogger {

    /**
     * Logger for this class
     */

    private Logger myLog;
    private PrintStream myPrintStream;

    /**
     * @return printStream
     * @throws UnsupportedEncodingException
     */

    public PrintStream getPrintStream() {
        if (myPrintStream == null) {
            OutputStream output = new OutputStream() {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                @Override
                public void write(int b) {
                    baos.write(b);
                }
                /**
                 * @see java.io.OutputStream#flush()
                 */
                @Override
                public void flush() throws UnsupportedEncodingException {
//                    String log = this.baos.toString().trim();
//                    System.out.println(Charset.defaultCharset().toString());
                    String log = this.baos.toString("UTF-8").trim();
                    if (!StringUtils.isBlank(log)) {
                        myLog.info(log);
                        baos = new ByteArrayOutputStream();
                    }
                }
            };
            //true: autoflush
            //must be set!
            myPrintStream = new PrintStream(output, true);
        }
        return myPrintStream;
    }
    /**
     * Constructor
     *
     * @param logger
     */
    public RACustomLogger(Logger logger) {
        super();
        myLog = logger;
    }
}