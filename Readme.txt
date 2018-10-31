DNA barcode designer

----------------------------------------------------
Instructions:
----------------------------------------------------
1. The program has dependency, please install UNAFold from "http://mfold.rna.albany.edu/“. 

	a) for Unix systems, choose the default installation location: '/usr/local/bin'). 

	b) for Windows systems, open Util.java and set "bin_path" for the path where UNAFold bin files (i.e., hybrid2.pl file) are installed. 

2. To set new parameters, please edit the "Parameters" sections in TestS.java, ChangeS.java and Util.java, then compile and run the simulation:

	$ javac TestS.java
	$ java TestS

----------------------------------------------------
Notes:
----------------------------------------------------
Improvements will be posted as "*bestsofar_X*.txt" files during optimization.
The final result will be generated as "*Dataset_X*.txt" file.
Parallel jobs can be sent to clusters by modifying the script "sendparallel.sh":
	$ qsub sendparallel.sh  

----------------------------------------------------
Citation:
----------------------------------------------------
Abdulkadir Elmas, Guido H. Jajamovich, Xiaodong Wang, and Michael S. Samoilov. 
Designing DNA Barcodes Orthogonal in Melting Temperature by Simulated Annealing Optimization.
Nucleic Acid Therapeutics. Apr 2013. http://doi.org/10.1089/nat.2012.0394
