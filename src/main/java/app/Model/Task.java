package app.Model;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Main object
 */

public class Task implements Cloneable,Serializable{

    private String newTitle;
    private Date newTime;
    private Date currentTime;
    private Date newEndTime;
    private int intervalTime=0;
    private boolean newActive;
    private boolean repeatable;
    public static Date currDate = new Date();
    public final static SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy HH:mm");

    /**
     * Sets flag that task is repeatable.
     * @param repeatable
     */
    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    /**
     * Sets interval of repeat for repeatable tasks.
     * @param intervalTime
     */
    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    /**
     * Default constructor.
     */
    public Task(){


    }

    /**
     * Constructor for a basic Task.
     * @param newTitle Title of the task.
     * @param date Date when task should be completed.
     * @throws ParseException
     * @throws IOException
     */
    public Task(String newTitle, String date) throws ParseException,IOException{

        this.newTitle = newTitle;
        this.newTime = format.parse(date);
        this.repeatable = false;
        this.newActive = true;
        if(newTime.before(currDate))throw new IOException("Wrong date!");

    }

    /**
     * Constructor for a repeatable Task.
     * @param newTitle Title of the task.
     * @param startDate Starting date of the task.
     * @param endDate Finishing date of the task.
     * @param intervalTime Interval of repeat represented as milliseconds.
     * @throws ParseException
     * @throws IOException
     */
    public Task(String newTitle,String startDate,String endDate,int intervalTime) throws ParseException,IOException{
        this.newTitle = newTitle;
        this.newTime = format.parse(startDate);
        this.newEndTime = format.parse(endDate);
        this.intervalTime = intervalTime;
        this.repeatable = true;
        this.newActive = true;
        if (newTime.before(currDate)||newEndTime.before(currDate)) throw new IOException("Wrong date!");
        if (intervalTime<=0)throw new IOException("Wrong interval, can't be less or equal 0!");
    }


    /**
     * Getter for the title field.
     * @return Title of the task.
     */

    public String getTitle(){
        return newTitle;
    }

    /**
     * Sets new title for the task.
     * @param title
     */
    public void setTitle(String title){
        this.newTitle = title;
    }

    /**
     * Checking status of the task.
     * @return boolean value
     */
    public boolean isActive(){
        return newActive;
    }

    /**
     *  Sets parameter active for the task.
     * @param active
     */
    public void setActive(boolean active){
        this.newActive = active;
    }

    /**
     * Getter for the starting date field or date field if task is not repeatable.
     * @return Date of the task.
     */
    public Date getTime(){
        return newTime;
    }

    /**
     * Sets ending time for repeatable task.
     * @param newEndTime
     * @throws ParseException
     */
    public void setNewEndTime(String newEndTime) throws ParseException{
        this.newEndTime = format.parse(newEndTime);
    }

    /**
     * Sets date or starting date if task is repeatable.
     * @param newDate
     * @throws ParseException
     * @throws IOException
     */
    public void setTime(String newDate) throws ParseException,IOException{
        this.newTime = format.parse(newDate);
//        if(newTime.before(currDate)) throw new IOException("Wrong date!");
        this.repeatable = false;

    }

    /**
     * Look getTime() method.
     * @return Starting date.
     */
    public Date getStartTime(){
        return newTime;
    }

    /**
     * Getter for finishing date of the repeatable task.
     * @return Finishing date.
     */
    public Date getEndTime(){
        if(repeatable){
            return newEndTime;
        }
        else{
            return newTime;
        }
    }

    /**
     * Getter for repeat interval value.(Hours)
     * @return repeat interval represented as hours.
     */
    public int getRepeatInterval(){
        if(repeatable){
            return intervalTime;
        }
        else{
            return 0;
        }
    }

    /**
     * Sets starting date and finishing date for repeated task.
     * @param start Starting date
     * @param end Finishing date
     * @param interval Interval between task being completed.
     * @throws ParseException
     * @throws IOException
     */
    void setTime(String start, String end, int interval)throws ParseException,IOException{
        if(interval<=0)throw new IOException("Interval can't be less or equal 0");
        this.newTime = format.parse(start);
        this.newEndTime = format.parse(end);
        if(newTime.before(currDate)||newEndTime.before(currDate)) throw new IOException("Wrong date!");
        this.intervalTime = interval;
        this.repeatable = true;
    }

    /**
     * Check if task is repeatable.
     * @return true if task is repeated.
     */
    public boolean isRepeatable(){
        return repeatable;
    }

    /**
     * Finds the date when task should be completed after specified date.
     * @param date Starting point for the search.
     * @return date when this task should be completed again.
     * @throws ParseException
     * @throws IOException
     */
        @SuppressWarnings("deprecation")
    public  Date nextTimeAfter(String date) throws ParseException,IOException{

        if(this.newActive) {
            this.currentTime = format.parse(date);
            if ((currentTime.before(newTime) || currentTime.equals(newTime)) && (!repeatable)) {
                return newTime;
            } else {
                if(currentTime.before(newTime) || currentTime.equals(newTime)){
                    return newTime;
                }
                else{

                    for(Date Time=this.newTime;Time.before(newEndTime)||Time.equals(newEndTime);Time.setTime(Time
                    .getTime() + this.intervalTime)){
                        if(currentTime.before(Time)||currentTime.equals(Time)){
                            return Time;
                        }
                    }

                }


            }

        }
            return null;
    }

    /**
     * Overriden standard method for object comparison
     * @param o object for comparison
     * @return true if objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (intervalTime != task.intervalTime) return false;
        if (newActive != task.newActive) return false;
        if (repeatable != task.repeatable) return false;
        if (newTitle != null ? !newTitle.equals(task.newTitle) : task.newTitle != null) return false;
        if (newTime != null ? !newTime.equals(task.newTime) : task.newTime != null) return false;
        if (currentTime != null ? !currentTime.equals(task.currentTime) : task.currentTime != null) return false;
        return !(newEndTime != null ? !newEndTime.equals(task.newEndTime) : task.newEndTime != null);

    }

    /**
     * Standard overriden method which generate int code using fields of the object.
     * @return unique int code for the object.
     */
    @Override
    public int hashCode() {
        int result = newTitle != null ? newTitle.hashCode() : 0;
        result = 31 * result + (newTime != null ? newTime.hashCode() : 0);
        result = 31 * result + (currentTime != null ? currentTime.hashCode() : 0);
        result = 31 * result + (newEndTime != null ? newEndTime.hashCode() : 0);
        result = 31 * result + intervalTime;
        result = 31 * result + (newActive ? 1 : 0);
        result = 31 * result + (repeatable ? 1 : 0);
        return result;
    }

    /**
     * Standard overriden method which represents fields of the object as a String.
     * @return String fields of the object.
     */
    @Override
    public String toString() {
        String active;
        if (!(this.isActive())) {

            active = "innactive";
        } else {
            active = "active";
        }
        if (this.isRepeatable()) {
            return ("\"" + this.getTitle() + "\"" +" from " + (Task.format.format(this.getTime())) +
                     " to " + (Task.format.format(this.getEndTime()))  + " Repeated in "
                    + Task.calculateIntervalTime(this.getRepeatInterval()) +
                    " Task is " + active +  "\n");
        } else {
            return ("\"" + this.getTitle() + "\"" + " at " + (Task.format.format(this.getTime())) + " Task is " +
                    active +  "\n");
        }

    }

    public static String calculateIntervalTime(int seconds) {
        String d="Day";
        String h="Hour";
        String m="Minute";
        String s="Second";
        long day = (int) TimeUnit.MILLISECONDS.toDays(seconds);
        long hours = TimeUnit.MILLISECONDS.toHours(seconds) -
                TimeUnit.DAYS.toHours(day);
        long minute = TimeUnit.MILLISECONDS.toMinutes(seconds) -
                TimeUnit.DAYS.toMinutes(day) -
                TimeUnit.HOURS.toMinutes(hours);
        long second = TimeUnit.MILLISECONDS.toSeconds(seconds) -
                TimeUnit.DAYS.toSeconds(day) -
                TimeUnit.HOURS.toSeconds(hours) -
                TimeUnit.MINUTES.toSeconds(minute);
        if(day>1 || day==0 ) d ="Days";
        if(hours>1|| hours==0) h="Hours";
        if(minute>1||minute==0) m="Minutes";
        if(second>1|| second==0) s ="Seconds";
        String output=( day + " " + d +" " + hours + " " + h+" " + minute +" " + m +" " + second + " " + s);
        return output;
    }

    /**
     * Clones an object.
     * @return clone task.
     * @throws CloneNotSupportedException
     */
    public Task clone() throws CloneNotSupportedException{

        return (Task) super.clone();
    }
}