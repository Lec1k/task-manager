package app.Model;
import app.Controller.Main;

import java.io.*;
import java.text.ParseException;
import java.util.Arrays;

/**
 * TaskIO created for byte and stream IO.
 */
@SuppressWarnings("deprecation")
public class TaskIO {
    /**
     * Writes all fields of the tasks in  a byte stream.
     * @param tasks tasks to write.
     * @param out output stream
     * @throws IOException
     */
    public static void write(TaskList tasks, OutputStream out) throws IOException{
        DataOutputStream outputStream = new DataOutputStream(out);
    try {

        outputStream.writeByte(tasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            outputStream.writeByte(tasks.getTask(i).getTitle().length());
            outputStream.writeUTF(tasks.getTask(i).getTitle());
            outputStream.writeBoolean(tasks.getTask(i).isActive());

            if (tasks.getTask(i).isRepeatable()) {
                outputStream.writeBoolean(true);            //repeat
                outputStream.writeInt(tasks.getTask(i).getRepeatInterval());
                outputStream.writeByte(tasks.getTask(i).getTime().getDate());
                outputStream.writeByte(tasks.getTask(i).getTime().getMonth());
                outputStream.writeByte(tasks.getTask(i).getTime().getYear());
                outputStream.writeByte(tasks.getTask(i).getTime().getHours());
                outputStream.writeByte(tasks.getTask(i).getTime().getMinutes());
                outputStream.writeByte(tasks.getTask(i).getEndTime().getDate());
                outputStream.writeByte(tasks.getTask(i).getEndTime().getMonth());
                outputStream.writeByte(tasks.getTask(i).getEndTime().getYear());
                outputStream.writeByte(tasks.getTask(i).getEndTime().getHours());
                outputStream.writeByte(tasks.getTask(i).getEndTime().getMinutes());


            } else {
                outputStream.writeBoolean(false);
                outputStream.writeByte(tasks.getTask(i).getTime().getDay());
                outputStream.writeByte(tasks.getTask(i).getTime().getMonth());
                outputStream.writeByte(tasks.getTask(i).getTime().getYear());
                outputStream.writeByte(tasks.getTask(i).getTime().getHours());
                outputStream.writeByte(tasks.getTask(i).getTime().getMinutes());
            }

        }
    }
    catch (IOException e){
        System.out.println("Write process interrupted");
    }
    finally {
        outputStream.flush();
        outputStream.close();
    }
    }

    /**
     * Reads byte stream of tasks and save it to array or list of tasks.
     * @param tasks Destination of stream to save tasks.
     * @param in input stream.
     * @throws IOException
     * @throws ParseException
     */
    public static void read(TaskList tasks, InputStream in)throws IOException,ParseException{
        DataInputStream dataInputStream = new DataInputStream(in);
        try {


            int quantity = dataInputStream.readByte();
            for (int i = 0; i < quantity; i++) {
                int lengthoftitle = dataInputStream.readByte();
                Task temp = new Task();
                temp.setTitle(dataInputStream.readUTF());
                temp.setActive(dataInputStream.readBoolean());
                if (dataInputStream.readBoolean()) {
                    temp.setIntervalTime(dataInputStream.readInt());
                    temp.setTime(dataInputStream.readByte() + " " + (dataInputStream.readByte() + 1) + " " + (dataInputStream.readByte() + 1900)
                            + " " + dataInputStream.readByte() + ":" + dataInputStream.readByte());
//                temp.getTime().setDate(dataInputStream.readByte());
//                temp.getTime().setMonth(dataInputStream.readByte());
//                temp.getTime().setYear(dataInputStream.readByte());
//                temp.getTime().setHours(dataInputStream.readByte());
//                temp.getTime().setMinutes(dataInputStream.readByte());
                    temp.setNewEndTime(dataInputStream.readByte() + " " + (dataInputStream.readByte() + 1) + " " + (dataInputStream.readByte() + 1900)
                            + " " + dataInputStream.readByte() + ":" + dataInputStream.readByte());
//                temp.getEndTime().setDate(dataInputStream.readByte());
//                temp.getEndTime().setMonth(dataInputStream.readByte());
//                temp.getEndTime().setYear(dataInputStream.readByte());
//                temp.getEndTime().setHours(dataInputStream.readByte());
//                temp.getEndTime().setMinutes(dataInputStream.readByte());
                    temp.setRepeatable(true);
                } else {
                    temp.setTime(dataInputStream.readByte() + " " + (dataInputStream.readByte() + 1) + " " + (dataInputStream.readByte() + 1900)
                            + " " + dataInputStream.readByte() + ":" + dataInputStream.readByte());
//                temp.getTime().setDate(dataInputStream.readByte());
//                temp.getTime().setMonth(dataInputStream.readByte());
//                temp.getTime().setYear(dataInputStream.readByte());
//                temp.getTime().setHours(dataInputStream.readByte());
//                temp.getTime().setMinutes(dataInputStream.readByte());
                }
                tasks.add(temp);
            }
        }
        finally {
            dataInputStream.close();
        }
    }

    /**
     * writes tasklist binary to file.
     * @param tasks tasks to write.
     * @param file destination.
     * @throws IOException
     */
    public static void writeBinary(TaskList tasks, File file) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        try {
            objectOutputStream.writeObject(tasks);
        }
        catch (IOException e){
            System.out.println("Write binary interrupted");
        }
        finally {
            objectOutputStream.flush();
            objectOutputStream.close();
        }

    }

    /**
     * Reads binary from file. Use returned value to save task list.
     * @param tasks any empty task list.
     * @param file file to read.
     * @return task list read from file.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static TaskList readBinary(TaskList tasks, File file) throws IOException,ClassNotFoundException{
        if(Main.f.length()==0){return tasks;}
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));

        try {

            tasks = (TaskList) objectInputStream.readObject();
            return tasks;
        }
        finally {
            objectInputStream.close();
        }
    }

    /**
     * Writes task list as text to file.
     * @param tasks task list to write.
     * @param file destination path.
     * @throws IOException
     */
    public static void writeText(TaskList tasks, File file)throws IOException{
        FileWriter fileWriter = new FileWriter(file);
        String active="";
        String end ="";
    try {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.getTask(i).isActive()) {
                active = "active";
            } else {
                active = "inactive";
            }
            if (i == (tasks.size() - 1)) {
                end = ".";
            } else {
                end = ";";
            }
            if (tasks.getTask(i).isRepeatable()) {

                fileWriter.write("\"" + tasks.getTask(i).getTitle() + "\"" + " from " + (Task.format.format(tasks.getTask(i).getTime())) +
                        " to " + (Task.format.format(tasks.getTask(i).getEndTime())) + " every " + tasks.getTask(i).getRepeatInterval()
                        + "(" + Task.calculateIntervalTime(tasks.getTask(i).getRepeatInterval()) + ")" +
                        " " + active + end + "\n");
            } else {
                fileWriter.write("\"" + tasks.getTask(i).getTitle() + "\"" + " from " + (Task.format.format(tasks.getTask(i).getTime())) + " " +
                        active + end + "\n");
            }

        }
    }
    finally {
        fileWriter.flush();
        fileWriter.close();
            }
    }

    /**
     * Reads task list as text from file and save it to the new task list.
     * @param tasks destination for saving tasks
     * @param file file to read
     * @throws IOException
     * @throws ParseException
     */
    public static void readText(TaskList tasks, File file)throws IOException,ParseException{
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Task temp = new Task();
        char[] cbuf = new char[100];
        char[] cbufTitle = new char[25];
        char[] cbufTime= new char[25];
        char[] cbufEndTime = new char[25];
        for(int i =0;i<tasks.size();i++) {
            bufferedReader.read(cbuf);
            cbufTitle = Arrays.copyOfRange(cbuf, 1, 5);
            cbufTime = Arrays.copyOfRange(cbuf, 12, 29);
            cbufEndTime = Arrays.copyOfRange(cbuf, 32, 49);
            String s = new String(cbufEndTime);
//            System.out.println(s);
            temp.setTitle(s);
            int interval = Integer.parseInt(new String(Arrays.copyOfRange(cbuf, 55, 59)));
//            System.out.println(interval);
//        s = new String(cbufTime);
            temp.setTitle(new String(cbufTitle));
        temp.setTime(new String(cbufTime),new String(cbufEndTime),interval);
        tasks.add(temp);
        }
    }

    /**
     * writes task list as text to output stream
     * @param tasks tasks to write.
     * @param out output stream.
     * @throws IOException
     */
    public static void write(TaskList tasks, Writer out) throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(out);
        String active ="";
        String end = "";
        try {


            for (int i = 0; i < tasks.size(); i++) {
                if (!(tasks.getTask(i).isActive())) {

                    active = "false";
                } else {
                    active = "true";
                }
                if (i == (tasks.size() - 1)) {
                    end = ".";
                } else {
                    end = ";";
                }
                if (tasks.getTask(i).isRepeatable()) {
                    bufferedWriter.write("\"" + tasks.getTask(i).getTitle() + "\"" + '\n' + (Task.format.format(tasks.getTask(i).getTime())) +
                            (Task.format.format(tasks.getTask(i).getEndTime())) + '\n' + tasks.getTask(i).getRepeatInterval()
                            + Task.calculateIntervalTime(tasks.getTask(i).getRepeatInterval()) +
                            " " + active + end + "\n");
                } else {
                    bufferedWriter.write("\"" + tasks.getTask(i).getTitle() + "\"" + " at " + (Task.format.format(tasks.getTask(i).getTime())) + " " +
                            active + end + "\n");
                }

            }
        }
        finally {
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }

    /**
     * Reads tasks as text from stream.
     * @param tasks
     * @param in
     * @throws IOException
     * @throws ParseException
     */
    public static void read(TaskList tasks, Reader in)throws IOException,ParseException{
        BufferedReader bufferedReader = new BufferedReader(in);
        Task temp = new Task();
        try {
            for (int i = 0; i < tasks.size(); i++) {
                temp.setTitle(bufferedReader.readLine());
                temp.setTime(bufferedReader.readLine(), bufferedReader.readLine(), Integer.valueOf(bufferedReader.readLine()));
            }
            tasks.add(temp);
        }
        finally {
            bufferedReader.close();
        }
    }
}
