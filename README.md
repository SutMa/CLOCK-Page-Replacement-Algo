# CLOCK-Page-Replacement-Algo
Page Replacement Algorithm simulator using the CLOCK algorithm.
The CLOCK (i.e., Second- Chance) algorithm is an approximation of the well-known Least Recently Used (LRU) algorithm, which evicts the least recently accessed (referenced) pages. The CLOCK algorithm uses a reference bit (RB) associated with each page to estimate the recency of page references and to identify the victim pages for eviction. A modify bit (MB) is associated with each page to indicate whether the page has been modified and needs to be swapped out if being evicted.

This programs simulates a CLOCK algortithm. 
The number of available page frames should be set according to an input of the program. 
We assume that demand paging is used. All the memory page frames are unallocated initially, and all the referenced pages are initially in the swap space

Instructions to Run:
To compile: javac Clock.java
To run with small example:  java Clock 3 10 20 pageref-small.txt output.txt
To run with large example: java Clock 3 10 20 pageref.txt output.txt
To see results: cat output.txt
