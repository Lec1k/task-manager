package taskmanager.model;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class Tasks has improved method incoming to work with collections.
 *
 * Created by LeC1K on 21.11.2015.
 */
public class Tasks {
    static SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy HH:mm");

    /**
     * Shows which tasks will be active during specified dates.
     * @param tasks array list or linked list of tasks
     * @param start starting date
     * @param end finishing date
     * @return linked list of found task.
     * @throws ParseException
     */

    public static Iterable<Task> incoming(Iterable<Task> tasks, String start, String end)throws ParseException{
        Iterable<Task> incomingTaskList = new LinkedTaskList();
        Date from = format.parse(start);
        Date to = format.parse(end);

        for( Task t : tasks) {
            if(!t.isRepeatable()){
                if(t.getTime().before(to) || t.getTime().equals(to) && t.getTime().after(from)|| t.getTime().equals(from)){
                    ((LinkedTaskList)incomingTaskList).add(t);
                }
            }
            else {
                if(t.getTime().before(to) && t.getTime().after(from) || t.getEndTime().before(to) && t.getEndTime().after(from)){
                    ((LinkedTaskList)incomingTaskList).add(t);
                }
            }
        }
        return incomingTaskList;
    }

    /**
     * Creates a Sorted map of tasks, sorted by date and reachable by date key value.
     * @param tasks array list or linked list of tasks
     * @param start starting date
     * @param end finishing date
     * @return Sorted map of tasks
     * @throws ParseException
     * @throws IOException
     */

    public static SortedMap<Date,Set<Task>> calendar(Iterable<Task> tasks, String start, String end) throws ParseException,IOException{
                SortedMap<Date,Set<Task>> TaskMap = new TreeMap<Date,Set<Task>>();
                Set<Task> taskSet = new HashSet<Task>();
                Date from = format.parse(start);
                Date to = format.parse(end);

            for(Task t: tasks){
                if(!t.isRepeatable()){
                    if(t.getTime().before(to) || t.getTime().equals(to) && t.getTime().after(from)|| t.getTime().equals(from)){
                        taskSet.add(t);
                    }
                }
                else {
                    if(t.getTime().before(to) && t.getTime().after(from) || t.getEndTime().before(to) && t.getEndTime().after(from)){
                        long time=((t.getEndTime().getTime()-t.getTime().getTime())/(t.getRepeatInterval()));

                        for(long i = 0;i<time;i++){

                            Task temp = new Task(t.getTitle(), Task.FORMAT.format(t.getTime().getTime()+t.getRepeatInterval()*i+1),
                                                 Task.FORMAT.format(t.getEndTime()),t.getRepeatInterval());

//                            temp.getTime().setTime(temp.getTime().getTime()+temp.getRepeatInterval()*counter);
                            taskSet.add(temp);

                    }
                }
            }
            }
        for (Task taskHashSet: taskSet){
            TaskMap.put(taskHashSet.getTime(),taskSet);
        }



        return TaskMap;
    }


}
