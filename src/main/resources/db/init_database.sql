CREATE TABLE `jareds_user_login` (
`ID` int NOT NULL,
`Email` varchar(45) NOT NULL,
`Username` varchar(45) NOT NULL,
`Password` varchar(45) NOT NULL,
PRIMARY KEY (`ID`,`Email`,`Username`,`Password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci