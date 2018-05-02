**Architecture**

*Structure*

Structure of Ekonomista is capsulated in the following picture;

![viikko4](https://user-images.githubusercontent.com/32302869/38872195-59bd19ca-425b-11e8-83de-37ddf4471853.JPG)

There is the GameUi (which gets some other Scene creating classes, but they are irrevelant), and it has GameService.
This GameService supplies GUI with all the methods it needs to run the game and keep track of everything it need to keep track of.

**GUI consists of four different views, and they are in no particular order;**

*Main menu* in here, player can start the game, go into highscores or go into company creator. This is also the scene in which the player inserts his or hers name and starting money.

*Company creator* in here, player can delete companies from the game files, or add new ones with custom parameters.

*Highscores* in here, player can bask in the glory of his or hers past achiements or loathe their betters.

*Game view* in here you play the game, silly. (You also can retire which closes the game and adds your money into highscores, just be sure to sell any stocks you might have as they are not counted as points!) 

Changing of these scenes is done in an extremely stupid way which causes exponentional work when new scenes are added, so... yeah.

**Basic components**
Two most fundemental components are company, and user. Company keeps track of companys index, and can calculate new indexes based on outside given random numbers.

User, on the other hand, keeps track of players money, and handles buying and selling stocks adjusting money accordingly and passing on boolean values on whether or not selling or buying succeeded to GameService.

Following picture shows how GameService asks User to buy stocks from a company;

![sekvenssivko5](https://user-images.githubusercontent.com/32302869/39156554-53ce9fa8-475f-11e8-8c5b-691a6a50b626.png)

**Saving of data**

Companies are saved in files. Currently Ekonomista tries to find a file called companies.data, and if it cannot find it, it simply creates one.

Highscores on the other hand, are saved in database because i thought that it would be more proper than to save them too in a game file. (Also because using multiple different ways to store data might bring more points?)

**Weaknesses**

GUI of Ekonomista is absolutely horrendous. It needs to be rewritten ASAP, but i think it has gotten to the point i can just say "im done" and move on, since it works.

Also, there is not much challenge in the game currently. You can actually just create a company that cannot fail and put all your money in it. This could be fixed by adding some kind of general index that all companies follow, but it is not a priority at the moment.

