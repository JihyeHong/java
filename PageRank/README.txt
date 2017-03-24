Question 4.7>
Basically, it is impossible to compute page ranks for all web pages in the web. Because, it cannot even afford to store such a big transition matrix in memory of a PC. To solve this problem intuitively, we can divide the huge web pages and links data to several distributed machines. Next step is computing page ranks of small data set in a each machine. Then combine them again and calculate the page ranks of total set.

p.s 
The code to test the implementations of matchServer() and the two-parameter version of scanAndPrint() is commented out in UrlExtractor.java, to explicitly test for each program. Simply you can remove the comment symbols in the file and test those functions. Thank you. 
