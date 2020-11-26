import java.util.ArrayList;

public class RequirementList
{

    private ArrayList<Requirement> requirements;

    public RequirementList(){
        this.requirements = new ArrayList<>();
    }

    public int size(){
        return requirements.size();
    }

    public void addRequirement(Requirement requirement){
        requirements.add(requirement);
    }

    public void removeRequirement(Requirement requirement){
        requirements.remove(requirement);
    }

    public Requirement getRequirement(int index){
        return requirements.get(index);
    }

    public int getIndexFromName(String name){
        for(int i = 0 ; i < requirements.size() ; i++){
            if(requirements.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    public boolean equals(Object obj){

        if(obj instanceof ArrayList){
            if(getRequirements().equals((ArrayList)obj)){
                return true;
            }
        }
        return false;
    }

}
