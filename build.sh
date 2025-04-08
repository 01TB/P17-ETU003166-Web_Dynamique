
# Définir les variables de répertoire
APP_NAME="ETU003166"
SRC_DIR="src/main/java"
WEB_DIR="src/main/webapp"
CONF_DIR="$SRC_DIR/etc"
BUILD_DIR="build"
LIB_DIR="lib"
TOMCAT_WEBAPPS="/home/berthin/Bureau/L1 ITU/Installation/Apache Tomcat/tomcat-10.1.28/apache-tomcat-10.1.28/webapps"
SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"
MYSQL_API_JAR="$LIB_DIR/mysql-connector-j-9.2.0.jar"

# Nettoyage et création du répertoire temporaire
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR/WEB-INF/classes

# Compilation des fichiers Java avec le JAR des Servlets
find $SRC_DIR -name "*.java" > sources.txt
javac -cp "$SERVLET_API_JAR:$MYSQL_API_JAR" -d $BUILD_DIR/WEB-INF/classes @sources.txt
rm sources.txt

# Copier les fichiers web (web.xml, JSP, etc...)
cp -r $WEB_DIR/* $BUILD_DIR/

# Copier les fichiers de configuration
cp -r $WEB_DIR "build/WEB-INF/classesww"

# Générer le fichier .war dans le dossier build
cd $BUILD_DIR || exit
jar -cvf $APP_NAME.war *
cd ..


# Déploiement dans Tomcat
cp -f $BUILD_DIR/$APP_NAME.war "$TOMCAT_WEBAPPS"/

echo ""

echo "Déploiment terminé. Redémarrez Tomcat si nécessaire."

echo ""

