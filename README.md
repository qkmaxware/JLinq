# JLinq
Flat file database system with basic constraints and data-types and collection querying similar to c# Linq

# Examples
Basic JLinq query
```java
//Some collection of integers
Collection<Integer> integers;

//Query - Get a new collection of integers greater than 5 put in ascending order of value
Select.From(integers).Where(x -> { return x > 5; }).OrderByAscending(x -> { return x; }).All();
```

Basic flat file table
```java
//Create a column model to define our table
//We assign datatype-validators and column constraints here
ColumnModel model = new ColumnModel(
    new ColumnDefinition("uid", new SequenceType(), new PrimaryKey()), //uid is an ascending numberic sequence that is unique and not null
    new ColumnDefinition("name", new VarcharType()), //name is a string
    new ColumnDefinition("age", new IntegerType()) //age is an integer
);

//Create the table
Table table = new Table(model);

//Set the backing file
File file = new File("person.tbl");
table.setFile(file);

//If file already exists, we can call
if(file.exists()){
    try{
        table.revert();
    catch(Exception e){
        System.out.println("Failed to load table data because " + e);
    }
}

//Add some data 
Row user = Row.FromData("1", "kim", "24");
Row user2 = Row.FromData("2", "jane", "22");
Row user3 = Row.FromData("3", "john", "28");
Row user4 = Row.FromData("4", "adam", "20");

table.add(user);
table.add(user2);
table.add(user3);
table.add(user4);

//Query table
System.out.println(
    Select.From(table).Where((x) -> {
        //x is the row instance, we want to only get entries where age > 20
        return ((BigInteger)x.getValue(2)).compareTo(new BigInteger("20")) == 1; 
    }).All()
);
```

