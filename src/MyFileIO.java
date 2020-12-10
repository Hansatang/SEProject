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
      writeToFile.writeObject(obj);
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
}
