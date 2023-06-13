package model;

import utils.DateConverter;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The treatment class.
 */
public class Treatment {
    private long tid;
    private long pid;
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String description;
    private String remarks;

    /**
     * Creates a new treatment.
     * @param pid The primary key of the patient.
     * @param date The date of the treatment.
     * @param begin The beginning time.
     * @param end The ending time.
     * @param description The short description.
     * @param remarks The long description.
     */
    public Treatment(long pid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks) {
        this.pid = pid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
    }

    /**
     * * Creates a new treatment with primary key.
     * @param tid The primary key of the treatment.
     * @param pid The primary key of the patient.
     * @param date The date of the treatment.
     * @param begin The beginning time.
     * @param end The ending time.
     * @param description The short description.
     * @param remarks The long description.
     */
    public Treatment(long tid, long pid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks) {
        this.tid = tid;
        this.pid = pid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
    }

    /**
     * Gets the primary key of the treatment.
     * @return The primary key of the treatment.
     */
    public long getTid() {
        return tid;
    }

    /**
     * Gets the primary key of the patient.
     * @return The primary key of the patient.
     */
    public long getPid() {
        return this.pid;
    }

    /**
     * Gets the date.
     * @return The date.
     */
    public String getDate() {
        return date.toString();
    }

    /**
     * Gets the beginning.
     * @return The beginning.
     */
    public String getBegin() {
        return begin.toString();
    }

    /**
     * Gets the end.
     * @return The end.
     */
    public String getEnd() {
        return end.toString();
    }

    /**
     * Sets the date.
     * @param s_date The new date as string.
     */
    public void setDate(String s_date) {
        LocalDate date = DateConverter.convertStringToLocalDate(s_date);
        this.date = date;
    }

    /**
     * Sets the beginning.
     * @param begin The new beginning as string.
     */
    public void setBegin(String begin) {
        LocalTime time = DateConverter.convertStringToLocalTime(begin);
        this.begin = time;
    }

    /**
     * Sets the end.
     * @param end The new end as string.
     */
    public void setEnd(String end) {
        LocalTime time = DateConverter.convertStringToLocalTime(end);
        this.end = time;
    }

    /**
     * Gets the short description.
     * @return The short description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the short description.
     * @param description The new short description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the long description.
     * @return The long description.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the long description.
     * @param remarks The new long description.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Override for the toString method.
     * @return The treatment described in a string.
     */
    public String toString() {
        return "\nBehandlung" + "\nTID: " + this.tid +
                "\nPID: " + this.pid +
                "\nDate: " + this.date +
                "\nBegin: " + this.begin +
                "\nEnd: " + this.end +
                "\nDescription: " + this.description +
                "\nRemarks: " + this.remarks + "\n";
    }
}