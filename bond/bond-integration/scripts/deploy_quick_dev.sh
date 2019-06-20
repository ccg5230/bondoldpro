#!/bin/bash

echo "remove all old source code ..."
cd /home/bond_dev/src/
rm -fr ./dm-ada
echo "done"

echo "checkout lastest source from svn .."
svn checkout https://192.168.8.189:18080/svn/DM-VM-JAVA/trunk/dm-ada
if [ $? -eq 0 ]
then
    echo "done"
else
    echo "failed"
    exit 1;
fi

echo "compile dm-model ..."
cd /home/bond_dev/src/dm-ada/dm-model
mvn clean install
if [ $? -eq 0 ]
then
    echo "done"
else
    echo "failed"
    exit 1;
fi

echo "compile dm-core ..."
cd /home/bond_dev/src/dm-ada/dm-core
mvn clean install
if [ $? -eq 0 ]
then
    echo "done"
else
    echo "failed"
    exit 1;
fi

echo "compile bond-service ..."
cd /home/bond_dev/src/dm-ada/bond/bond-service
mvn clean install
if [ $? -eq 0 ]
then
    echo "done"
else
    echo "failed"
    exit 1;
fi

echo "compile bond-web ..."
cd /home/bond_dev/src/dm-ada/bond/bond-web
mvn clean install
if [ $? -eq 0 ]
then
    echo "done"
else
    echo "failed"
    exit 1;
fi


echo "compile bond-integration ..."
cd /home/bond_dev/src/dm-ada/bond/bond-integration
mvn clean install
if [ $? -eq 0 ]
then
    echo "done"
else
    echo "failed"
    exit 1;
fi

echo "re-deploy war file to tomcat..."
cp -fr /home/bond_dev/src/dm-ada/bond/bond-web/target/bond-web.war /home/tomcat-dm-refactor/webapps/
if [ $? -eq 0 ]
then
    echo "done"
else
    echo "failed"
    exit 1;
fi

sleep 1

#echo "shutdown tomcat ..."
#/home/tomcat-dm-refactor/bin/shutdown.sh
#sleep 2

#/home/tomcat-dm-refactor/bin/startup.sh

