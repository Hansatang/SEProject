import java.io.Serializable;

public class Member implements Serializable
{
  private String name;

  public Member(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
