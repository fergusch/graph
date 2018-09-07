# graph-dijkstra

This is a basic implementation of a [graph](https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)) data structure which includes [Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm).

## Usage

### Creating a graph
Creating a graph is pretty simple:
```Java
// Create a new graph that is undirected and weighted
Graph g = new Graph(false, true);

// Add vertices to the graph
g.setVertices(new String[] {"A", "B", "C", "D", "E"});

// Create edges between vertices and set edge weights
g.setEdge("A", "B", 1);
g.setEdge("B", "C", 3);
g.setEdge("C", "D", 1);
g.setEdge("D", "E", 2);
g.setEdge("B", "E", 8);
```

This will create a graph that, when visually represented, would look something like this:
![](https://i.imgur.com/f9bL2bG.png)

### Using Dijkstra's algorithm

To run Dijkstra's algorithm on the entire graph from a single node:
```Java
DijkstraTable dt = g.dijkstra("B");
```
This will return a `DijkstraTable` object, which contains two `HashMap` objects containing the distances and previous nodes along the shortest path to each node. Put together, they create a table which looks like the following:

| Node | Distance to B | Previous |
| --- | --- | --- |
| A | 1 | B |
| C | 3 | B |
| D | 4 | C |
| E | 6 | D |

These values can be accessed like so:
```Java
dt.getDistanceTo("D");     // 4
dt.getPreviousVertex("D"); // "C"
```

To trace the shortest path between two nodes, use `getShortestPathBetween`:
```Java
Path p = g.getShortestPathBetween("B", "E");
System.out.println(p); // prints "B -> C -> D -> E"
```
This returns a `Path` object, which contains an ordered list of vertices representing the shortest path between the two nodes.
