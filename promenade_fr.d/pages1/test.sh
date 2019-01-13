###################################################################################################################
#Programme permettant de sauter une ligne puis de rajouter une ligne supplémentaire dans tous les fichiers *.html #
#Ce programme se fait actuellement pour le dossier courant                                                        #
#Excecution :   bash test.sh                                                                                      #
#Date de création: 27/02/2013                                                                                     #
#Date de la dernière de la modification : 01/03/2013                                                              #
###################################################################################################################



#####################################################################################################
# Explications et mode de fonctionnement                                                            #
#                                                                                                   #
# Pour RAJOUTER dans tous les fichiers html la ligne suivante:                                      #
# <li><a href="..\/pages1\/131.html">Soleil<\/a><\/li>                                              #
# après la chaine de caractère suivante :                                                           #
# /Terre<\/a><\/li>                                                                                 #
# On écrit la ligne suivante dans le script:                                                        #
# sed -i 's/Terre<\/a><\/li>/&  \n <li><a href="..\/pages1\/131.html">Soleil<\/a><\/li>/g' *.html   #
#                                                                                                   #
# Pour REMPLACER dans tous les fichiers html la ligne suivante:                                     #
# <li><a href="..\/pages1\/131.html">Soleil<\/a><\/li>                                              #
# après la chaine de caractère suivante :                                                           #
# /Terre<\/a><\/li>                                                                                 #
# On écrit la ligne suivante dans le script:                                                        #
# sed -i 's/Terre<\/a><\/li>/  \n <li><a href="..\/pages1\/131.html">Soleil<\/a><\/li>/g' *.html    #
#                                                                                                   #
# ATTENTION à la casse!!!                                                                           #
#                                                                                                   #
# Pour choisir tous les fichiers, dans le répertoire courant, composés de 3 caractères commençant   # 
# par 1 et qui ne possède pas de lettre après le 1 et se terminant par l'extension.html, on écrit   #
# 1[!a-zA-Z][!a-zA-Z].html                                                                          #
# au lieu de *.html qui permet d'appliquer la fonction sed sur tous les fichiers avec l'extension   #
# .html dans le dossier courant                                                                     #
# 1[!7-9][!a-zA-Z].html permet de choisir les fichiers, dans le répertoire coiurant, composé de 3   #
# caractères commençant par 1 et- ne possèdant pas les chiffres 7, 8 et 9 pour les décimales et ne  #
# soit pas composé de lettres pour les unités et se teminant avec l'extension .html                 #
#                                                                                                   #
#Pour remplacer une chaine de caractere par une autre chaine de caratète on écrit la ligne suivante:#
#sed -i 's/&agrave;/à/g' *.html                                                                     #
#                                                                                                   #                                                          
#Si une chaine de caractère possède un caractère spécial, il faudra l'échapper à l'aide d'un antislash#
#Si une chaine de caractère possède une apostrophe il faut inverser les quotes (simple et double) tel#
#que dans la ligne en exemple ci après:                                                              #
# sed -i "s/<li><a href=\"..\/pages2\/258.html\">Qu'est-ce que l'astronomie?<\/a>/<li><a href=\"..\/pages2\/258.html\">Comprendre<\/a>/g" *.html#
######################################################################################################



echo "bonjour"
# set -xv

#sed -i -e '/Terre/ s/<\/li>/& \/d10 TOTO/g' *.html



# sed -i.bak 's/<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
# "http://www.w3.org/TR/html4/loose.dtd">/ <!DOCTYPE html> \n /g' *.html


#sed -i '/Author/ s/THOUVENIN""/THOUVENIN"\//g' *.html

#sed -i 's/&agrave;/à/g' *.html

# sed -i -e '/Author/ s/\/>/& \n<\!-- pour la comptabilité pour internet explorer 9-->\n<\!--\[if lt IE 9\]>\n<script src="http:\/\/html5shiv.googlecode.com\/svn\/trunk\/html5.js"><\/script>\n<\!\[endif\]-->\n/g' *.html

# sed -i 's/<a href="..\/pages4\/443.html"><div id="glossaire">Glossaire<\/div><\/a>/<a href="..\/pages2\/221.html"><div id="glossaire">Glossaire<\/div><\/a>/g' *.html

#sed -i 's/&agrave;/à/g' *.html

# sed -i 's/Zélenchouk/Zélenchuk/g' *.html
# sed -i 's/ZELENCHOUK/ZELENCHUK/g' *.html
# sed -i 's/<li><a href="..\/pages1\/165.html">Zélenchuk<\/a><\/li>/&  \n <li><a href="..\/pages6\/830.html">Shanghai<\/a><\/li> \n <li><a href="..\/pages6\/831.html">Itajuba<\/a><\/li>/g' *.html

# sed -i 's/<li><a href="..\/pages2\/247.html">Greenwich<\/a><\/li>/&  \n <li><a href="..\/pages6\/822.html">Bruxelles<\/a><\/li> \n <li><a href="..\/pages6\/823.html">Turin<\/a><\/li> \n <li><a href="..\/pages6\/824.html">Castel Gandolfo<\/a><\/li> \n <li><a href="..\/pages6\/825.html">Catane<\/a><\/li> \n <li><a href="..\/pages6\/826.html">San Fernando<\/a><\/li> \n <li><a href="..\/pages6\/827.html">Bucarest<\/a><\/li> \n <li><a href="..\/pages6\/828.html">Poznan<\/a><\/li> \n <li><a href="..\/pages6\/823.html">Nikolaev<\/a><\/li>/g' *.html

###

# sed -i 's/<a href="#">Europe<\/a>/<a href="..\/pages6\/833.html">Europe<\/a>/g' *.html
# 
# sed -i 's/<a href="#">Autres<\/a>/<a href="..\/pages2\/246.html">Autres<\/a>/g' *.html
# 
# sed -i 's/<li><a href="..\/pages6\/61.html">ESO<\/a><\/li>//g' *.html
# 
# sed -i 's/<li><a href="..\/pages6\/679.html">GAIA<\/a><\/li>//g' *.html
# 
# sed -i 's/<li><a href="..\/pages6\/78.html">CFHT Hawai<\/a><\/li>/& \n <li><a href="..\/pages6\/61.html">ESO<\/a><\/li>/g' *.html
# 
# sed -i 's/<li><a href="..\/pages4\/48.html">HST<\/a><\/li>/& \n <li><a href="..\/pages6\/679.html">GAIA<\/a><\/li>/g' *.html

############
#  sed -i 's/<a href="..\/pages3\/37.html">Encyclopédie<\/a>/<a href="..\/pages3\/37.html"><span class="police_chap">Encyclopédie<\/span> <\/a>/g' *.html
#  sed -i 's/<a href="..\/pages3\/32.html">Découvertes<\/a>/<a href="..\/pages3\/32.html"><span class="police_chap">Découvertes<\/span> <\/a>/g' *.html
#  sed -i 's/<a href="..\/pages1\/164.html">Espace<\/a>/<a href="..\/pages1\/164.html"><span class="police_chap">Espace<\/span> <\/a>/g' *.html
#  sed -i 's/<a href="..\/pages1\/114.html">Observatoires<\/a>/<a href="..\/pages1\/114.html"><span class="police_chap">Observatoires<\/span> <\/a>/g' *.html
#  sed -i 's/<a href="..\/pages2\/258.html">Comprendre<\/a>/<a href="..\/pages2\/258.html"><span class="police_chap">Comprendre<\/span> <\/a>/g' *.html
#  sed -i 's///g' *.html
###########
# # sed -i 's/<a href="#">Nançay??????<\/a>/<a href="..\/pages2\/281.html">Nançay<\/a>/g' *.html
# # 
# # sed -i 's/<li><a href="#">ESO Chili E-ELT?????<\/a><\/li>/& \n <li><a href="..\/pages5\/580.html">Les réflecteurs<\/a><\/li>  \n <li><a href="..\/pages5\/581.html">Les réfracteurs<\/a><\/li> /g' *.html
# # 
# # 
# # sed -i 's/<li><a href="..\/pages6\/78.html">CFHT Hawai<\/a><\/li>/& \n <li><a href="..\/pages6\/61.html">ESO<\/a><\/li>/g' *.html
# #  
# # sed -i 's/<li><a href="..\/pages6\/61.html">ESO Chili VLT<\/a><\/li>//g' *.html
# #  
# # sed -i 's/<li><a href="#">ESO Chili E-ELT?????<\/a><\/li>/ /g' *.html
# #  
# # sed -i 's/<li><a href="..\/pages4\/48.html">HST<\/a><\/li>/& \n <li><a href="..\/pages6\/679.html">GAIA<\/a><\/li>/g' *.html
# # ###
# # 
# # 
# # sed -i 's/<li><a href="..\/pages6\/727.html">vénus Express<\/a><\/li>/<li><a href="..\/pages6\/727.html">Vénus Express<\/a><\/li>/g' *.html
# # 
# # sed -i 's/<li><a href=..\/pages1\/107.html"">Pic du Midi<\/a><\/li>/<li><a href="..\/pages1\/107.html">Pic du Midi<\/a><\/li>/g' *.html








#  sed -i "s/<li><a href=\"..\/pages2\/258.html\">Qu'est-ce que l'astronomie?<\/a>/<li><a href=\"..\/pages2\/258.html\">Comprendre<\/a>/g" *.html





# 
# sed -i 's/<a href="..\/pages1\/164.html">Missions spatiales<\/a>/<a href="..\/pages1\/164.html">Espace<\/a>/g' *.html
# 
#  sed -i 's/<a href="..\/pages3\/37.html">Encyclopédie astronomique<\/a>/<a href="..\/pages3\/37.html">Encyclopédie <\/a>/g' *.html

# sed -i 's/<a href="..\/pages1\/119.html"><div id="transversal">Navigation <br \/>transverse<\/div><\/a>/ /g' *.html


#  sed -i ' s/Haut de page<\/div><\/a>/& \n 	     <a href="..\/pages4\/443.html"><div id="glossaire">Glossaire<\/div><\/a>/g' *.html
#  sed -i ' s/Haut de page<\/div><\/a>/& \n 	     <a href="..\/pages2\/201.html"><div id="index">Index<\/div><\/a>/g' *.html
# sed -i 's/<a href="..\/pages4\/443.html"><div id="glossaire">Glossaire<\/div><\/a>/ /g' *.html
#  sed -i 's/<a href="..\/pages2\/201.html"><div id="index">Index<\/div><\/a>/ /g' *.html

# sed -i 's/<a href="..\/pages5\/51.html"><img class="image" src="..\/images\/new_image\/bandeau.png" alt="" \/><\/a>/<a href="..\/pages5\/51.html"><img class="image-bandeau" src="..\/images\/new_image\/bandeau.png" alt="" \/><\/a>/g' *.html



echo "Fin de la procédure"

