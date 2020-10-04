# promotion-engine

A promotion-engine is designed which can calculate the total cost of items in a cart after applying promotions.

## Assumptions
The promotions will be mutually exclusive; if promotion is defined on an item then the item cannot be part of another promotion

## Technology used
Java 8

## Steps to build and run
1.	Once the codebase is downloaded from Github repository, go to the base directory where pom.xml is located and run the following command from terminal: mvn clean package.
2.	The above mentioned command executes the test cases as well as builds a fat-jar (maven-shade-plugin has been used) within "target" directory by the name promotion-engine-1.0.0-SNAPSHOT.jar.
3.	Run the application by executing the following command from terminal: java -jar target/promotion-engine-1.0.0-SNAPSHOT.jar
4. The base price of the items (A, B, C, D) has been setup in the Enum Item.java. The inputs for the promotions and cart details needs to be provided through the console. On, the basis of the inputs provided, the total value of the cart is calculated after applying appropriate promotions. Sample input from console is provided within the base directory in the file "sample-console-input.txt".
5. For test case execution, the promotions and cart details have been setup in the class PromotionComputeEngineTest.java



