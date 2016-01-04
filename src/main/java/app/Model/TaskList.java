package app.Model;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LeC1K on 30.10.2015.
 */
abstract public class TaskList implements Iterable, Serializable{
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract int size();
    public abstract Task getTask(int index);
    public TaskList incoming(String fromm, String too) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy HH:mm");
        int size = size();
        Date from;
        Date to;
        from = format.parse(fromm);
        to = format.parse(too);
        Date temp,temp1;
        if (this instanceof ArrayTaskList){
            ArrayTaskList incTasks = new ArrayTaskList();
        for(int i=0;i<size;i++) {
            if (!this.getTask(i).isRepeatable()) {
                temp = this.getTask(i).getTime();
                if (temp.before(to) && temp.after(from)) {
                    incTasks.add(this.getTask(i));
                }
            } else {
                temp = this.getTask(i).getTime();
                temp1 = this.getTask(i).getEndTime();
                if (temp.before(to) && temp.after(from) || temp1.before(to) && temp1.after(from)) {
                    incTasks.add(this.getTask(i));
                }
            }
        }
            return incTasks;
        }


        else{
            LinkedTaskList newTaskList = new LinkedTaskList();
            for (int i = 0; i < size(); i++) {
                if (!this.getTask(i).isRepeatable()) {
                    temp = this.getTask(i).getTime();
                    if (temp.before(to) && temp.after(from)) {
                        newTaskList.add(this.getTask(i));
                    }
                }
                else {
                    temp = this.getTask(i).getStartTime();
                    temp1 = this.getTask(i).getEndTime();
                    if (temp.before(to) && temp.after(from) || temp1.before(to) && temp1.after(from)) {
                        newTaskList.add(this.getTask(i));
                    }
                }
            }
            return newTaskList;
        }
    }
}
