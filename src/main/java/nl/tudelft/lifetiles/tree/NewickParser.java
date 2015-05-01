package nl.tudelft.lifetiles.tree;

import java.util.StringTokenizer;

public class NewickParser {
	private PhylogeneticTreeItem root;
	private String treeFile;
		
	/**
	 * Creates a new NewickParser that parses the String
	 * containing a single tree and creates a tree of PhylogeneticTreeItems 
	 * @param tree a String specifying a single tree in newick format.
	 */
	public NewickParser(String tree){
		treeFile = tree;
		root = new PhylogeneticTreeItem();
		
		parse();
		
	}
	
	/**
	 * parses the String the parser has been given.
	 * the String is first tokenized, after which the method iterates 
	 * over all tokens
	 */
	private void parse(){
	    // split the string ino tokens, and keep the delimiters in the list so we have
	    // information about the next token.
		StringTokenizer st = new StringTokenizer(treeFile,"(:,);",true);
		
		PhylogeneticTreeItem currentNode = root;
		// loop over all tokens
		while(st.hasMoreTokens()){
			String currentToken = st.nextToken();
			// this starts a list of child nodes, add a child node and make it our current node
			if(currentToken.equals("(")){
				PhylogeneticTreeItem newChild = new PhylogeneticTreeItem();
				newChild.setParent(currentNode);
				currentNode = newChild;
			}
			// next token is a distance, add it to the current node
			else if(currentToken.equals(":")){
				String distanceToken = st.nextToken();
				double distance = Double.parseDouble(distanceToken);
				currentNode.setDistance(distance);
			}
			// next token is another child, add a new child to the list and make it the current node
			else if(currentToken.equals(",")){
				currentNode = currentNode.getParent();
				PhylogeneticTreeItem newChild = new PhylogeneticTreeItem();
				newChild.setParent(currentNode);
				currentNode = newChild;
					
				
				
			}// list of children ends, go up 1 level so we can add a name and distance to this node
			// if they are specified
			else if(currentToken.equals(")")){
				currentNode = currentNode.getParent();
			}
			// tree is done, nothing special needs to be done
			else if(currentToken.equals(";")){
				break;
			}
			// final case, the current token is text, so the current token is a name
			else{
				currentNode.setName(currentToken);
			}
		}
		
		
	}
	/**
	 * Returns the parsed tree.
	 * @return the root node of the tree.
	 */
	public PhylogeneticTreeItem getRoot(){
		return root;
	}

}
