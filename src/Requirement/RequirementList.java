package Requirement;

import Task.Task;

import java.io.Serializable;
import java.util.ArrayList;

public class RequirementList implements Serializable
{

    private ArrayList<Requirement> requirements;

    public RequirementList(){
        this.requirements = new ArrayList<>();
    }

    public void addRequirement(Requirement requirement){
        requirements.add(requirement);
    }

    public void removeRequirement(Requirement requirement){
        requirements.remove(requirement);
    }

    public int size(){
        return requirements.size();
    }

    public void add(Requirement requirement){
        requirements.add(requirement);
    }

    public Requirement getRequirement(int index)
    {
        if (index < requirements.size())
        {
            return requirements.get(index);
        }
        else
        {
            return null;
        }
    }

    public ArrayList<Requirement> getRequirements() {
        return requirements;
    }

    public boolean equals(Object obj){

        if(obj instanceof ArrayList){
            return getRequirements().equals((ArrayList) obj);
        }
        return false;
    }


    public Requirement getRequirementsByName(String requirementName){
        int k=0 ;
        for (int i = 0; i < requirements.size(); i++)
        {
            if (requirements.get(i).getName().equals(requirementName))
            {
                k=i;
                break;
            }
        };
        return requirements.get(k);
    }
}
