# LifeTiles [![Build Status](https://travis-ci.org/ProgrammingLife5/LifeTiles.svg?branch=dev)](https://travis-ci.org/ProgrammingLife5/LifeTiles)


LifeTiles is a tool for interactive visualization of DNA sequence graphs to represent the genome architecture of organisms of interest. The application mainly consists of a Graph Viewer and a Tree Viewer but also a Sequence Viewer used for additional overview. 

- The **Graph Viewer** shows the contents of the graph in the structure of a tile lane diagram. It is able to visualize multiple DNA strands and the mutations between those DNA strands if a reference is selected. 
- The **Tree Viewer** shows the contents of the phylogenetic tree in the structure of a Sunburst diagram. This shows the relation between the different samples used in the program.
- The **Sequence Viewer** shows all sequences names so the user has an overview of the data which is currently viewed.


## Features


- View a single or multiple DNA strands as a graph to research the structure. In a unified form where every sequence segment has the same coordinate system and empty sequence segments are also visualized.
- Create a phylogenetic tree based on several DNA strands so that the user can compare them and observe the timeline and the common ancestors of several functionalities.
- Make interaction between the Tree Viewer and the Graph Viewer interactive by giving the user the ability to select subsets in the graph or in the tree which are synchronized.
- Indicate mutation types through colours based on a reference sequence.
- Filter certain types of mutations so that the user can identify specific types of mutations: SNP, insertion, deletion, inversion, translocation and duplication.
- Select the level of detail of the viewport of DNA so that the user can see the relevant parts of DNA using semantic zooming.




## Installation

The application can be executed by downloading or cloning the source. 

To start researching your DNA sequence graph you need your files in the graph format which consists of two files with `.node.graph` and `.edge.graph` as extension.
The phylogenetic tree files must be in the Newick file format. Newick files have the `.nwk` extension.
