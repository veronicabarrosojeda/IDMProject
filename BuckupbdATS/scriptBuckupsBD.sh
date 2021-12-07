#!/bin/bash                                          

myuser=cliente
mypass=atuservicio.IDM..
DB=atuserviciobd
dia=`date +"%d:%m:%Y"`
hora=`date +"%H:%M"`

args="-u"$myuser" -p"$mypass" --add-drop-database --add-locks --create-options --complete-insert --comments --disable-keys --dump-date --extended-insert --quick --routines --triggers"                                                     

destino="BuckupsBD/"$DB"Bkup"$hora"-"$dia".sql"
mysqldump ${args} $DB > $destino

