package app.Model;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Array type list for tasks.
 */
public class ArrayTaskList extends TaskList implements Cloneable{

    private  Task[] taskList = new Task[1]; //If smth happens look here(made 1 to get rid of Nullpointer)
    private int counter;

    /**
     * Add task to the list
     * @param task task to add.
     */
    public void add(Task task){
        if(task.equals(null)){
            throw new NullPointerException("Can't add a null pointer task!");
        }
        if (counter==taskList.length){
            int index = taskList.length + 1;
            taskList = Arrays.copyOf(taskList,index);
        }
        taskList[counter] = task;
        counter++;
    }
    /*
    public int getCounter(){
        return counter;
    }*/

    /**
     * Size of array
     * @return length of specified array.
     */
    public int size(){
        return taskList.length;
    }

    public Task[] getTaskList() {
        return taskList;
    }

    /**
     * Returns task from the array using specified index.
     * @param index number which represent position of the task in array.
     * @return task from the specified position.
     */
    public Task getTask(int index){
        if (index<0||index>size()){throw new IndexOutOfBoundsException("Wrong range!");}
        return taskList[index];

    }

    /**
     * Removes task from array list.
     * @param task task to remove.
     * @return true if task was removed.
     */
    public boolean remove(Task task) {
        if(task == null){throw new NullPointerException("Can't add null pointer task!");}
        for (int i = 0; i < taskList.length; i++) {
            if (taskList[i].equals(task)) {
                taskList[i] = taskList[counter-1];
                taskList[counter-1] = null;
                counter--;
                return true;
            }
        }
        return false;
}

    /**
     * Standard overriden method to compare to array lists of tasks.
     * @param o array list to compare
     * @return true if array lists are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayTaskList that = (ArrayTaskList) o;
        return Arrays.equals(taskList, that.taskList);

    }

    /**
     * Clones array list.
     * @return clone array list.
     * @throws CloneNotSupportedException
     */
    public ArrayTaskList clone() throws CloneNotSupportedException{
        return (ArrayTaskList) super.clone();
    }

    /**
     * Standard overriden method to represent array list as a String.
     * @return array list as a String.
     */
    @Override
    public String toString() {
        String output="";
        for(Task t : taskList){ output = output + t.toString();}

       return output;

    }

    /**
     * Standard overriden method to generate unique code using fields.
     * @return int code for the selected object.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(taskList);
    }

    /**
     * Iterator to go through array list.
     * @return Array iterator .
     */

    public Iterator iterator() {
        return new ArrayIterator();
    }

    /**
     * Array iterator for array task list.
     */
    public class ArrayIterator implements Iterator<Task>{
        Task curTask = taskList[0];
        int lengthHas;
        int lengthNext;
        boolean cycle=false;

        /**
         * Checking if the next element exist in specified collection.
         * @return true if collection has next element.
         */

        public boolean hasNext() {
            lengthHas++;
           return  lengthHas<taskList.length && taskList[lengthHas]!=null;



        }

        /**
         *
         * @return titles of the tasks in the array list using iterator.
         */
        @Override
        public String toString() {
            return "ArrayIterator{" +
                    "curTask=" + curTask.getTitle() +
                    '}';
        }

        /**
         * Goes through collection.
         * @return next task in the collection.
         */

        public Task next() {
            lengthNext++;

            if(lengthNext<taskList.length){
                curTask = taskList[lengthNext];

            }
            else {
                curTask = taskList[0];
                lengthNext=0;
            }
            return curTask;
        }

        /**
         * Removes current task from the collection.
         */


        public void remove() {
            for(int i=0;i<counter;i++){
                int s=lengthNext;
                taskList[s] = taskList[s+1];
                if(taskList[s]==null){
                    curTask = taskList[lengthNext];
                    break;
                }
                s++;
            }
        }

        /**
         *
         * @param action
         */

        public void forEachRemaining(Consumer<? super Task> action) {

        }
    }
}