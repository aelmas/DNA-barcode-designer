#!/bin/sh
#$ -j y
#$ -cwd
#$ -S /bin/bash
#$ -t 1-30  ## number of nodes (segments)
export ITER=$1
export PATH=$PATH:/share/apps/bin/ ## path to UNAFold installation (hybrid2.pl)
cd /tmp/ ## to store intermediate files as temporary 
/usr/java/jdk1.6.0_16/bin/java -Xmx2000M -DSGE_TASK_LAST=$SGE_TASK_LAST -DSGE_TASK_ID=$SGE_TASK_ID -DJOB_ID=$JOB_ID -cp /home/OrthogonalDNATagDesigner  TestS $ITER
