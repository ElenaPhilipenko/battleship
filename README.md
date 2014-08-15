                                   Battleship
              
    What is it?
    -----------      
     This is a  Battleship game, where you can play with a bot. 
     the bot chooses random square for his shot. If it hits a ship, the bot will sunk it 
     by searching vertical or horizontal ends of the ship. 
     Bot doesn't shoot into squares around sank ships.
     Rules for the game specified in Game class. Default rules are:
     - size of game grid is 10;
     - amount of ships is 10;
     - sizes for ships are: 4, 3,3, 2,2,2, 1,1,1,1;
     - 1 or more empty squares must be between squares of different ships.
    
    How to install?
    -----------
    For playing this game you need installed Java 8 on your computer.
    You can play by console or by UI.
    To play the game by console you need to download battleship.jar and run it by command:
        java -jar <path-to-jar>/battleship.jar 
    To play the game by UI you need to download uibattleship.jar and run it by command:
            java -jar <path-to-jar>/uibattleship.jar  
     
    How to build the project?
    -----------
    To build project you can use Maven. 
    Game is written on JDK 1.8.0_11 so you need to compile it with Java 1.8.
    Download project, navigate to root folder of the project, where pom.xml is located. 
    To build Ui version of the game run command:
        mvn clean install -P ui
    To build console version of the game run command:
        mvn clean install -P console
        
    
    
    
    
     
               