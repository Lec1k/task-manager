package taskmanager.model;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by LeC1K on 30.10.2015.
 */

/**
 * LinkedTask element of the linked task list.
 */
class LinkedTask implements Serializable {
    Task currentTask;
    LinkedTask nextTask;
    LinkedTask previousTask;

    /**
     * Constructor for the linked task.
     * @param currentTask
     */
    public LinkedTask(Task currentTask) {
        this.currentTask = currentTask;
    }
}

/**
 * Linked task list for saving tasks as a collection.
 */
public class LinkedTaskList extends TaskList implements Cloneable{



    private LinkedTask firstTask;
    private LinkedTask lastTask;
    private int listLength;

    /**
     * Clones linked task list.
     * @return clone linked list
     * @throws CloneNotSupportedException
     */

    public LinkedTaskList clone() throws CloneNotSupportedException{
        return (LinkedTaskList) super.clone();
    }

    /**
     * Standard overriden method, represents linked task list as a String.
     * @return all the tasks in the linked task list as a String.
     */
    @Override
    public String toString() {
        LinkedTask task = firstTask;
        String s = "";
        for(int i=0;i<listLength;i++) {

             s = s  + task.currentTask.toString();
            task = task.nextTask;

        }
        return  s;

    }

    /**
     * Standard overriden method to compare linked lists.
     * @param o linked list to compare.
     * @return true if linked lists are equals
     */
    @Override
    public boolean equals(Object o) {
        int count=0;
        if (this == o) return true;
        if (!(o instanceof LinkedTaskList)) return false;

        LinkedTaskList that = (LinkedTaskList) o;

        if (listLength != that.listLength) return false;
        LinkedTask o1 = ((LinkedTaskList) o).firstTask;
        LinkedTask o2 = that.firstTask;
        for(int i=0;i<listLength;i++){

                if(o1.equals(o2)){
                    count++;
                }
            o1 = o1.nextTask;
            o2 = o2.nextTask;
        }
        return count==listLength;

    }
    /**
     * Standard overriden method to generate unique code using fields.
     * @return int code for the selected object.
     */

    @Override
    public int hashCode() {
       int result = 1;
        LinkedTask tmp = this.firstTask;
        for(int i=0;i<listLength;i++){
            result = 31 * result + (tmp.currentTask != null ? tmp.currentTask.hashCode() : 0);
            tmp = tmp.nextTask;
        }
        result = 31 * result + listLength;
        return result;
    }

    /**
     * Adds task to the linked task list.
     * @param task task to add.
     */
    public void add(Task task){
        if(task == null){throw new NullPointerException("Can't add null pointer task!");}
        LinkedTask newLinkedTask = new LinkedTask(task);
        if(listLength==0){firstTask = newLinkedTask;}
        if(lastTask!=null){lastTask.nextTask=newLinkedTask;}
        newLinkedTask.previousTask = lastTask;
        lastTask = newLinkedTask;
        listLength++;


    }

    /**
     * Size of the list represented as an int value.
     * @return length of the list.
     */
    public int size(){
        return listLength;
    }

    /**
     * Get task from the list.
     * @param index index of the task to return.
     * @return task with specified index in the list.
     */
    public Task getTask(int index){
        if (index<0||index>size()){throw new IndexOutOfBoundsException("Wrong range!");}
        LinkedTask temp = firstTask;
        for(int i=0;i<index;i++){
            temp = temp.nextTask;
        }
        return temp.currentTask;
    }

    /**
     * Removes task from the linked list.
     * @param task task to remove.
     * @return true if task was deleted.
     */

    public boolean remove(Task task){
        if(task == null){throw new NullPointerException("Can't add null task!");}
        LinkedTask temp = firstTask;
        boolean res = false;
            for(int i=0;i<listLength;i++){
                if(temp.currentTask.equals(task)){
                    if(temp.previousTask==null){
                        firstTask = temp.nextTask;
                    }
                    else {
                        temp.previousTask.nextTask = temp.nextTask;
                    }
                    if(temp.nextTask==null){
                        lastTask = temp.previousTask;
                    }
                    else{
                        temp.nextTask.previousTask = temp.previousTask;
                    }
                    res = true;
                    break;
                }
                temp = temp.nextTask;
            }
        listLength--;
        return res;

    }

    /**
     * Removes linked task from the list.
     * @param linkedTask linked task to remove.
     */
    public void  removeLinkedTask(LinkedTask linkedTask){
        if(linkedTask==null){throw new NullPointerException("Element can't be null");}
        if(linkedTask.previousTask==null){
            firstTask = linkedTask.nextTask;
        }
        else{
            linkedTask.previousTask.nextTask = linkedTask.nextTask;
        }
        if(linkedTask.nextTask==null){
            lastTask = linkedTask.previousTask;
        }
        else{
            linkedTask.nextTask.previousTask = linkedTask.previousTask;
        }
        listLength--;
    }

    /**
     * Iterator to go through linked task list.
     * @return linked task list iterator.
     */

    public Iterator<Task> iterator() {
        return new LinkedIterator();
    }

    /**
     * Iterator for the linked task list.
     */
    public class LinkedIterator implements Iterator<Task>{
        LinkedTask curTask = firstTask;
        boolean wasNext=false;


        public boolean hasNext() {
            if(curTask.nextTask!=null){
                return true;
            }
            else {return false;}
        }


        public Task next() {

            Task task = curTask.currentTask;
            curTask = curTask.nextTask;
            wasNext = true;
            return task;
        }


        public void remove() {
            if(!wasNext){throw new IllegalStateException("Use next method before remove");}
            if(curTask==null){              //Out of bound
                curTask = lastTask;
            }
            else{
                curTask = curTask.previousTask; //Look at next method
            }
            removeLinkedTask(curTask);
            wasNext = false;
        }

    }

}
