###################################################################################################################
#Programme permettant de décommenter le bouton francais-anglais et insérer le bouton print par un fichier (*.txt) #
#dans tous les fichiers *.html                                                                                    #
#Ce programme se fait actuellement pour le dossier courant                                                        #
#Excecution :  ./modif_sed-print.sh ou bash modif_sed-print.sh                                                    #
#Date de création: 12/02/2013                                                                                     #
#Date de la dernière de la modification : 12/02/2013                                                              #
###################################################################################################################


#!/bin/sh

echo "Bonjour!"

echo "Décommenter bouton"
sed -i 's/<!--<span class="fontsize-en">/<span class="fontsize-en">/g' *.html
sed -i 's/<\/span>-->/<\/span>/g' *.html

echo "insertion balises"
sed -i 's/<span class="fontsize">/totototo \n tatatata \n <span class="fontsize">/g' *.html
sed -i 's/<span class="fontsize-en">/tutututu \n titititi \n <span class="fontsize-en">/g' *.html

echo "suppression texte entre balises"
sed -i '/tatatata/,/tutututu/ d' *.html

echo "insertion du nouveau texte"
for f in *.html; do
  echo $f
  sed -i '/<div id="droite">/ r insertion-print.txt' $f
done;

echo "suppression des balises"
sed -i 's/totototo//g' *.html
sed -i 's/titititi//g' *.html

echo "Bravo! Fin de la procédure!"

