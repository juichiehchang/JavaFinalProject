echo "Compiling hotel/user files..."
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/Check.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/Cancel.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/CheckResult.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/Date_diff.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/ModifyDate.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/ModifyDateResult.java 
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/ModifyRoom.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/ModifyRoomResult.java 
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/Order.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/OrderResult.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/Query.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/user/QueryResult.java

echo "Compiling hotel/exceptions files..."
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/exceptions/CheckException.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/exceptions/ModifyException.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/exceptions/OrderException.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/exceptions/Pair.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/exceptions/RoomType.java

echo "Compiling hotel/create files..."
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/create/CreateTable.java

echo "Compiling hotel/parse files..."
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/parse/Insert.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/parse/ParseHotel.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar:json-simple-1.1.1.jar" hotel/parse/ParseJson.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/parse/ParseOrder.java
javac -cp ".:sqlite-jdbc-3.27.2.1.jar" hotel/parse/ParseResv.java


