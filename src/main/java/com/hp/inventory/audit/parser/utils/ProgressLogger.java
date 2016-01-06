/*
 * Copyright (c) 2015 Topcoder Inc. All rights reserved.
 */

package com.hp.inventory.audit.parser.utils;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;

/**
 * In long running jobs, logs progress. By default, prints every 2s.
 *
 * @author TCDEVELOPER
 * @version 1.0.5
 */
public class ProgressLogger {

    private final Logger log;
    private final long startTime;
    private int count;
    private int total;

    private long frequency = 2000;

    private PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
            .appendHours()
            .appendSuffix("h")
            .appendMinutes()
            .appendSuffix("m")
            .minimumPrintedDigits(2)
            .printZeroAlways()
            .appendSeconds()
            .appendSuffix("s")
            .toFormatter();

    private ProgressPrinter progressPrinter;

    /**
     * Create a new progress logger. If total is set to zero, printing is halt until the #setTotal
     * @param log
     * @param total
     */
    public ProgressLogger(Logger log, int total) {
        this.log = log;
        this.total = total;
        this.startTime = System.currentTimeMillis();
    }

    private void startPrinting() {
        progressPrinter = new ProgressPrinter();
        new Thread(progressPrinter).start();
    }

    public void tick() {
        count++;

        if (total <= 0) return;

        if (progressPrinter == null) {
            startPrinting();
        }

        if (count >= total) {
            progressPrinter.running = false;
        }

    }

    public void finish() {
        progressPrinter.running = false;
    }

    class ProgressPrinter implements Runnable {
        boolean running = true;

        @Override
        public void run() {
            while(running) {
                try {
                    Thread.sleep(frequency);
                    if (!running) return;

                    double progressPercent = ((double) count / (double)total) * 100.0;

                    long ctime = System.currentTimeMillis();
                    long elapsed = (ctime - startTime);

                    double speed = (double) count / (double)elapsed;

                    long eta = (int)Math.round((total- count) / speed);
                    String etaString = periodFormatter.print(new Period(eta));

                    log.info(String.format("PROGRESS: %.2f%% [%s/%s]. %.2f per sec. ETA: %s",
                            progressPercent, count, total, speed * 1000d, etaString));

                } catch (InterruptedException ignored) { }
            }
        }
    }

    public void setTotal(int total) {
        this.total = total;
        if (progressPrinter == null)
            startPrinting();
    }

    public int getCount() {
        return count;
    }

    /**
     * Gets the print frequency in millis.
     */
    public long getFrequency() {
        return frequency;
    }

    /**
     * Sets the print frequency in millis.
     */
    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }
}
