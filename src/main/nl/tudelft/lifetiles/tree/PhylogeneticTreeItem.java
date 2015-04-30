package nl.tudelft.lifetiles.tree;

import java.util.ArrayList;


public class PhylogeneticTreeItem {
	
	private PhylogeneticTreeItem parent;
	private ArrayList<PhylogeneticTreeItem> children;
	
	private String name;
	private double distance;
	private int id;
	
	public PhylogeneticTreeItem(int id){
		children = new ArrayList<PhylogeneticTreeItem>();
		this.id = id;
	}
	
	public String treeToString(){
		String result = this.toString() + "\n";
		for(int i = 0; i < children.size(); i++){
			result += children.get(i).treeToString();
		}
		return result;
	}
	
	public String toString(){
		String result ="<Node: " + id + ", name: " + name + ", Distance: " + distance ;
		if(parent != null){
			result += ", parent: "+ parent.getId();
		}
		else{
			result += ", ROOT ";
		}
		return  result + ">";
	}
	
	public void addChild(PhylogeneticTreeItem child){
		children.add(child);
	}
	
	public ArrayList<PhylogeneticTreeItem> getChildren(){
		return children;
	}
	
	public void setParent(PhylogeneticTreeItem parent){
		this.parent = parent;
		this.parent.addChild(this);
	}
	
	public PhylogeneticTreeItem getParent(){
		return parent;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getId() {
		return id;
	}



}
