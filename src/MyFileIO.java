import java.io.*;
import java.util.ArrayList;

/**
 * A class responsible for reading and writing binary files.
 *
 * @author Allan Henriksen
 * @version 1.0
 */
public class MyFileIO
{
  /**
   * Writes the given object to a file with the given file name.
   *
   * @param fileName the name and path of the file to write to
   * @param obj      the object to write to the file
   * @throws FileNotFoundException if the file with fileName is not found
   * @throws IOException           if there is an error writing to the file
   */
  public void writeToFile(String fileName, Object obj)
      throws FileNotFoundException, IOException
  {
    ObjectOutputStream writeToFile = null;

    try
    {
      FileOutputStream fileOutStream = new FileOutputStream(fileName);
      writeToFile = new ObjectOutputStream(fileOutStream);
      System.out.println("C");
      writeToFile.writeObject(obj);
      System.out.println("D");
    }
    finally
    {
      if (writeToFile != null)
      {
        try
        {
          writeToFile.close();
        }
        catch (IOException e)
        {
          System.out.println("IO Error closing file " + fileName);
        }
      }
    }
  }

  public void PacierzSaver(String fileName, Object obj)
  {
    try
    {

      FileOutputStream FOS = new FileOutputStream(fileName);
      ObjectOutputStream OOS = new ObjectOutputStream(FOS);
      OOS.writeObject(obj);
      OOS.close();
      System.out.println("The Object  was succesfully written to a file");

    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }



  /**
   * Writes the objects in the given array to a file with the given file name.
   *
   * @param fileName the name and path of the file to write to
   * @param objs     the Object array to write to the file
   * @throws FileNotFoundException if the file with fileName is not found
   * @throws IOException           if there is an error writing to the file
   */
  public void writeToFile(String fileName, Object[] objs)
      throws FileNotFoundException, IOException
  {
    ObjectOutputStream writeToFile = null;

    try
    {
      FileOutputStream fileOutStream = new FileOutputStream(fileName);
      writeToFile = new ObjectOutputStream(fileOutStream);

      for (int i = 0; i < objs.length; i++)
      {
        writeToFile.writeObject(objs[i]);
      }
    }
    finally
    {
      if (writeToFile != null)
      {
        try
        {
          writeToFile.close();
        }
        catch (IOException e)
        {
          System.out.println("IO Error closing file " + fileName);
        }
      }
    }
  }

  public Object PacierzReader(String fileName)
  {
    Object obj = null;
    try
    {

      FileInputStream FIS = new FileInputStream(fileName);
      ObjectInputStream OIS = new ObjectInputStream(FIS);

      obj = OIS.readObject();

      System.out.println("The Object has been read from the file");
      OIS.close();

    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return obj;
  }

  /**
   * Reads the first object from the file with the given file name and returns it.
   * Whoever calls the method will need to cast it from type Object to its real type
   *
   * @param fileName the name and path of the file that is read
   * @return the Object read from the file
   * @throws FileNotFoundException  if the file with fileName is not found
   * @throws IOException            if there is an error reading the file
   * @throws ClassNotFoundException if the class of the serialized object cannot be found
   */
  public Object readObjectFromFile(String fileName)
      throws FileNotFoundException, IOException, ClassNotFoundException
  {
    Object obj = null;
    ObjectInputStream readFromFile = null;
    try
    {
      FileInputStream fileInStream = new FileInputStream(fileName);
      readFromFile = new ObjectInputStream(fileInStream);
      try
      {
        obj = readFromFile.readObject();
      }
      catch (EOFException eof)
      {
        //Done reading
      }
    }
    finally
    {
      if (readFromFile != null)
      {
        try
        {
          readFromFile.close();
        }
        catch (IOException e)
        {
          System.out.println("IO Error closing file " + fileName);
        }
      }
    }

    return obj;
  }

  /**
   * Reads all objects from the file with the given file name and returns it as an Object array.
   * Whoever calls the method will need to cast the Objects to their real type.
   *
   * @param fileName the name and path of the file that is read
   * @return and Object array with all the objects read from the file
   * @throws FileNotFoundException  if the file with fileName is not found
   * @throws IOException            if there is an error reading the file
   * @throws ClassNotFoundException if the class of the serialized object cannot be found
   */
  public Object[] readArrayFromFile(String fileName)
      throws FileNotFoundException, IOException, ClassNotFoundException
  {
    ArrayList<Object> objs = new ArrayList<Object>();

    ObjectInputStream readFromFile = null;
    try
    {
      FileInputStream fileInStream = new FileInputStream(fileName);
      readFromFile = new ObjectInputStream(fileInStream);
      while (true)
      {
        try
        {
          objs.add(readFromFile.readObject());
        }
        catch (EOFException eof)
        {
          //Done reading
          break;
        }
      }
    }
    finally
    {
      if (readFromFile != null)
      {
        try
        {
          readFromFile.close();
        }
        catch (IOException e)
        {
          System.out.println("IO Error closing file " + fileName);
        }
      }
    }

    return objs.toArray();
  }
}
