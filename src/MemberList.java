import java.io.Serializable;
import java.util.ArrayList;

public class MemberList implements Serializable
{
  private ArrayList<Member> members;

  public MemberList()
  {
    this.members = new ArrayList<>();
  }

  /**
   * Gets how many Members objects are in the list.
   *
   * @return the number of Members objects in the list
   */
  public int size()
  {
    return members.size();
  }

  /**
   * Gets a Member object from position index from the list.
   *
   * @param index the position in the list of the Member object
   * @return the Member object at position index if one exists, else null
   */
  public Member get(int index)
  {
    return members.get(index);
  }

  public void addMember(Member member)
  {
    members.add(member);
  }


  public void removeMember(Member member)
  {
    members.remove(member);
  }

  public void deleteMember(String name)
  {
    members.remove(getIndexFromName(name));
  }

  public void replaceMember(String name,String newName)
  {
    members.get(getIndexFromName(name)).setName(newName);
  }

  public int getIndexFromName(String name)
  {
    for (int i = 0; i < members.size(); i++)
    {

      if (members.get(i).getName().equals(name))
      {
        return i;
      }

    }
    return -1;
  }

  public ArrayList<Member> getMembers()
  {
    return members;
  }

  public boolean equals(Object obj)
  {
    if (obj instanceof MemberList)
    {
      if (this.getMembers().equals(((MemberList) obj).getMembers()))
      {
        return true;
      }
    }
    return false;
  }

  public String toString()
  {
    String str = "";
    if (members.size()!=0)
    {
      for (int i = 0; i < members.size(); i++)
      {
        str += members.get(i).getName() + " , ";
      }
      str = str.substring(0, str.length() - 2);
    }
    return str;
  }
}
