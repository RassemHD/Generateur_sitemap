###################################################################################################################
#Programme permettant de remplacer plusieurs lignes comprises entre 2 balises(motifs) par un fichier (*.txt) dans # 
#tous les fichiers *.html                                                                                         #
#Ce programme se fait actuellement pour le dossier courant                                                        #
#Excecution :  ./modif_sed.sh ou bash modif_sed.sh                                                                #
#Date de création: 09/12/2013                                                                                     #
#Date de la dernière de la modification : 09/12/2013                                                              #
###################################################################################################################


#!/bin/sh

echo "Bonjour!"

echo "insertion balises"
sed -i 's/<ul id="menu">/totototo \n tatatata \n <ul id="menu">/g' *.html
sed -i 's/<\/header>/tutututu \n titititi \n <\/header>/g' *.html

echo "suppression texte entre balises"
sed -i '/tatatata/,/tutututu/ d' *.html

echo "insertion du fichier insertion1.txt"
for f in *.html; do
  sed -i '/totototo/ r insertion1.txt' $f
done;

echo "suppression des balises"
sed -i 's/totototo//g' *.html
sed -i 's/titititi//g' *.html

echo "Bravo! Fin de la procédure!"

