                                   Battleship
              
    What is it?
    -----------      
     This is a console version of Battleship game, where you can play with bot. 
     Bot chooses random square for shot. If it hits a ship it will sunk it 
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
    To play the game you need download battleship.jar and run it by command:
        java -jar <path-to-jar>/battleship.jar 
     
    How to build the project?
    -----------
    To build project you can use Maven. 
    Game is written on JDK 1.8.0_11 so you need to compile it with Java 1.8.
    Download project, navigate to root folder of the project, where pom.xml is located
    and run this command:
        mvn clean install 
    
    
    
    
     
               