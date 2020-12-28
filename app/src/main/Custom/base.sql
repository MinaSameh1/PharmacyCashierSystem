CREATE TABLE IF NOT EXISTS Admin(
    AdminID INT PRIMARY KEY,
    AdminName VARCHAR(100) NOT NULL,
    AdminPassword VARCHAR(100) NOT NULL,
    AdminAddress VARCHAR(400),
    AdminTelephone VARCHAR(15)
);
                        
CREATE TABLE IF NOT EXISTS Cashier( 
    CashierID INT PRIMARY KEY,
    CashierName     VARCHAR(100) NOT NULL,
    CashierPassword VARCHAR(100) NOT NULL,
    CashierAddress VARCHAR(400),
    CashierTelephone VARCHAR(16),
    CashierNumberOfOrders INT NOT NULL
);


CREATE TABLE IF NOT EXISTS Items(
    ItemID INT PRIMARY KEY,
    ItemName VARCHAR(100) NOT NULL,
    ItemCost DOUBLE NOT NULL,
    InStock INT,
    Sold INT
);


CREATE TABLE IF NOT EXISTS Warehouse(
    WarehouseID INT PRIMARY KEY,
    WarehouseName VARCHAR(100) NOT NULL,
    WarehousePass VARCHAR(100) NOT NULL,
    WarehouseAddress VARCHAR(400),
    WarehouseItemIDSupplied INT
);

CREATE TABLE IF NOT EXISTS Bills(
    BillID INT PRIMARY KEY,
    CashierID INT,
    ItemsSold VARCHAR(500), 
    NumberOfItems INT,
    Total DOUBLE,
    AmountPaid DOUBLE,
    Change DOUBLE
);

INSERT INTO Admin VALUES(1, 'admin', 'admin', 'Ain Shams 123 El Zahraa','0113024124');

INSERT INTO Cashier VALUES( 1, 'cashier', 'cashier', 'Zein El 3abdeen Shobra' );

INSERT INTO Items( 1, 'Medicine', 100.00, 10, 20 );

SELECT * FROM Admin;

SELECT AdminID,AdminName,AdminPassword FROM Admin WHERE AdminName='admin';
