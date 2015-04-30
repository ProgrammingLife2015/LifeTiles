package nl.tudelft.lifetiles.tree;

import java.util.StringTokenizer;

public class NewickParser {
	private PhylogeneticTreeItem root;
	private String treeFile;
	private int nodeID = 0;
	
	public NewickParser(String tree){
		treeFile = tree;
		root = new PhylogeneticTreeItem(nodeID);
		nodeID++;
		parse();
		
	}
	
	private void parse(){
		StringTokenizer st = new StringTokenizer(treeFile,"(:,);",true);
		PhylogeneticTreeItem currentNode = root;
		while(st.hasMoreTokens()){
			String currentToken = st.nextToken();
			
			
			// list of children
			if(currentToken.equals("(")){
				PhylogeneticTreeItem newChild = new PhylogeneticTreeItem(nodeID);
				newChild.setParent(currentNode);
				currentNode = newChild;
				nodeID++;
			}
			// next token is a distance
			else if(currentToken.equals(":")){
				String distanceToken = st.nextToken();
				double distance = Double.parseDouble(distanceToken);
				currentNode.setDistance(distance);
			}
			// next token is another child
			else if(currentToken.equals(",")){
				currentNode = currentNode.getParent();
				PhylogeneticTreeItem newChild = new PhylogeneticTreeItem(nodeID);
				newChild.setParent(currentNode);
				currentNode = newChild;
				nodeID++;
				
				
				
			}// list of children ends
			else if(currentToken.equals(")")){
				currentNode = currentNode.getParent();
			}
			// tree is done
			else if(currentToken.equals(";")){
				break;
			}
			// final case current token is a name
			else{
				currentNode.setName(currentToken);
			}
		}
		
		
	}
	
	public PhylogeneticTreeItem getRoot(){
		return root;
	}

}
