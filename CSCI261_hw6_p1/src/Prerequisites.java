/**
 * Prerequisites.java
 * 
 * @author	Derek Brown
 *
 * Purpose:	To determine the size of the longest prerequisite chain for a series
 * 			of courses.
 */

import java.util.Scanner;

public class Prerequisites {
	
	// Constants
	public static final int MAX_COURSES = 100000;
	public static final int MAX_PREREQS = 112;

	// Hidden data members
	private int size;
	private int[][] adjList = new int[MAX_PREREQS+1][MAX_COURSES+1];
	private boolean[] seen;
	private int[] fin;
	private int time;
	private int[] S;
	private int[] order;
	
	
	// Constructor
	
	/**
	 * Constructor for creating an instance of a Prerequisites object,  Holds
	 * important info like the adjacency list, the 'time' global, and the
	 * solution array.
	 * 
	 * @param n			The number of vertices in the graph.
	 * @param prereqs	The array given by user showing the prereq dependencies.
	 */
	public Prerequisites( int n, int[][] prereqs ) {
		this.size = n;
		int[] outdegree = new int[n+1];
		for( int i = 1 ; i <= n ; i++ ) {
			outdegree[i] = 0;
		}//end for i
		for( int c = 1 ; c <= n ; c++ ) {
			int i = 0;
			int v;
			while( prereqs[i][c] != 0 ) {
				v = prereqs[i][c]; 
				adjList[outdegree[v]][v] = c;
				outdegree[v]++;
				i++;
			}//end while
		}//end for c
		this.seen = new boolean[n+1];
		for( int i = 1 ; i <= n ; i++ ) {
			seen[i] = false;
		}//end for
		this.fin = new int[n+1];
		for( int i = 1 ; i <= n ; i++ ) {
			fin[i] = Integer.MAX_VALUE;
		}//end for
		this.time = 0;
		this.S = new int[size+1];
		this.order = new int[size+1];
	}//end Prerequisites constructor
	
	// Methods
	
	/**
	 * Implementation of the topological sort algorithm.
	 */
	public void topOrder() {
		for( int s = 1 ; s <= size ; s++ ) {
			if( !seen[s] ) {
				DFS( s );
			}//end if
		}//end for
	}//end longestPrereq
	
	/**
	 * Depth-first-search helper function for topological sort
	 * 
	 * @param v	The vertex to be processed.
	 */
	public void DFS( int v ) {
		seen[v] = true;
		int index = 0;
		while( adjList[index][v] != 0 ) {
			if( !seen[adjList[index][v]] ) {
				DFS( adjList[index][v] );
			}//end if
			index++;
		}//end while
		time++;
		fin[v] = time;
	}//end DFS
	
	/**
	 * After topological sort has been run, re-orders the fin array so that the
	 * vertices are actually in order.
	 */
	public void addToOrder() {
		for( int i = 1 ; i <= size ; i++ ) {
			order[fin[i]] = i;
		}//end for i
	}//end addToOrder
	
	/**
	 * Finds the length of the longest chain of prereq courses.
	 * 
	 * @return	The length of the longest chain of prereq courses.
	 */
	public int longestPrereq() {
		for( int i = 1 ; i <= size ; i++ ) {
			int v = order[i];
			S[i] = 1;
			for( int j = 1 ; j < i ; j++ ) {
				int k = 0;
				while( adjList[k][v] != 0 ) {
					if( adjList[k][v] == order[j] && S[j]+1 > S[i] ) {
						S[i] = S[j]+1;
					}//end if
					k++;
				}//end while
			}//end for j
		}//end for i
		int max = 0;
		for( int i = 1 ; i <= size ; i++ ) {
			if( S[i] > max ) {
				max = S[i];
			}//end if
		}//end for
		return max;
	}//end longestPrereq

	/**
	 * The main logic for the program, Reads in input from the user and then
	 * executes: topological sort, orders the fin array, and finally finds and
	 * displays the length of the longest prereq chain.
	 * 
	 * @param args	Command line arguments, unused.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner( System.in );
		String input;
		input = sc.next();
		int numVertices = Integer.parseInt( input );
		int[][] prereqs = new int[MAX_PREREQS+1][numVertices+1];
		int value;
		for( int v = 1 ; v <= numVertices ; v++ ) {
			input = sc.next();
			value = Integer.parseInt( input );
			int prereq = 0;
			while( value != 0 ) {
				prereqs[prereq][v] = value;
				input = sc.next();
				value = Integer.parseInt( input );
				prereq++;
			}//end while
		}//end for
		sc.close();
		Prerequisites P = new Prerequisites( numVertices, prereqs );
		P.topOrder();
		P.addToOrder();
		System.out.println( P.longestPrereq() );
	}//end main
}//end Prerequisites class
