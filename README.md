# Final Project For Database Class 
### **School Project**
this is a repurposed java project (<a href="https://github.com/vitrobiani/final_java_project_OOD" target="_blank">original</a>).<br>
<p>the project focuses on implementing database principles, and innterfacing with the Postgresql DBMS through the JDBM and writing queries and updates to the Database</p>

## Loading The Database
<p>The db is managed by postgresql, so ofcource you need to install it in order to host this db. <br>
This program was written in java and so you wil need to add the JDBM driver JAR file to your project. <br>
And you can use the DDOFinalProj.backup file, use  the postgres restore option to load the db. <br>
The tables are loaded automatically each time the program starts and everytime the first option is chosen.<br>
The tables are initilized with the <a href="https://github.com/vitrobiani/Final_Database_Java_Project/blob/master/src/TN.java">TN.java file</a> as a reference to the table and column names.</p>

## General Explanation
<p>When running the code you will first be greeted with this screen: </p>

```
 ~ Welcome to the store management system ~
1. add Products automatically (for testing, only when there are no products)
2. Add a new product
3. remove a product
4. update product inventory
5. add an order
6. remove an order
7. add a shipping company
8. remove a shipping company
9. print Product details
10. print all products and store profit
11. print all orders of a product and the profit
12. drop all the database tables
13. Exit
Please Enter Your Choice:  
```

<p>asking for input.</p>
<details>
  <summary>Option 1</summary>

  ### adding products automatically
  <p>this option is purely for testing. it will add 10 porducts, 3 shipping companies, 5 countries (countries are hard coded anyways), 3 customers and 7 orders and invoices. <br>
  Keep in mind you can only use it if there are no products in the db, and of cource you can change the hard coded products, shipping companies and what not in the <a href="https://github.com/vitrobiani/Final_Database_Java_Project/blob/master/src/autoAddProductCommand.java"> autoAddProductCommand class <a></a> 
  <br>If there are no tebles in the db it will initialize it. </p>
</details>
    <br>
<details>
  <summary>Option 2</summary>
  
  ### add a new product
  <p>option to add a new product to the db, keep in mind that the product code is the main identifier (primary key) so you can't create 2 with the same one.</p>
</details>
<br>
<details>
  <summary>Option 3</summary>

  ### remove a product
  <p>option to remove a product from the db, based on the product code. keep in mind you can't remove a product if there are pending orders for it.</p>
</details>
<br>
<details>
  <summary>Option 4</summary>

  ### update product stock
  <p>option to update a product stock in the db, based on the product code.</p>
</details>
<br>
<details>
  <summary>Option 5</summary>

  ### adding an order
  <p>the option to add an order to a product, adding an order will require the store to have products in the db.</p>
</details>
<br>
<details>
  <summary>Option 6</summary>

  ### remove an order
  <p>the option to remove an order of a product, keep in mind that the invoice will be deleted as well.</p>
</details>
<br>
<details>
  <summary>Option 7</summary>

  ### add a shipping company
  <p>option to add a shipping company to the db.</p>
</details>
<br>
<details>
  <summary>Option 8</summary>

  ### remove a shipping company
  <p>the option to remove a shipping company from the db. keep in mind that you can't remove a shipping company if it still has pending orders.</p>
</details>
<br>
<details>
  <summary>Option 9</summary>

  ### print product details
  <p>opening an option menu to choose from all available products in the store, and printing their details. also alows you to print any of their orders invoices (if they have any)</p>
</details>
<br>
<details>
  <summary>Option 10</summary>

  ### printing all products and store profit
  <p>printing all products in the store and their details and in the end the store profit</p>
</details>
<br>
<details>
  <summary>Option 11</summary>

  ### print product orders
  <p>opening an option menu to choose from all available products in the store, and printing their orders (if they have any).</p>
</details>
<br>
<details>
  <summary>Option 12</summary>

  ### drop the database
  <p>JOKE OPTION!. this will actually drop all the tables in the db.</p>
</details>
