- # Design Patterns 

  Assignments for the course of Design Patterns by University of Alberta on Coursera

  # Course Notes

  ##### Table of Contents 

  [Week 1](#week-1) 

  [Week 2](#week-2) 

  [Week 3](#week-3) 

  [Week 4](#week-4) 


  # Week 1

  ## 1. Gang of Four's Pattern Catalogue

  Design Patterns: Elements of Reusable Object-Oriented Software – Gamma, Helm, Johnson, and Vlissides

  ## 2. Categories of Patterns

  ### 2.1 Creational Patterns

  Creational patterns deal with the creation or cloning new objects.

  #### 2.1.1 Singleton Pattern

  - Enforces one and only one object of a Singleton class.

  - Has the Singleton object globally accessible.

  - Example in code

    Not Singleton

    ```java
    public class notSingleton {
        //Public Constructor
        public notSingleton() {
            ...
        }
    }
    ```

    Singleton

    ```java
    public class ExampleSingleton { // lazy construction
        // the class variable is null if no instance is
        // instantiated
        private static ExampleSingleton uniqueInstance = null;
    
        private ExampleSingleton() { 
            ...
        }
      
        // lazy construction of the instance
        public static ExampleSingleton getInstance() {
            if (uniqueInstance == null) {
                uniqueInstance = new ExampleSingleton();
            }
            Return uniqueInstance;
        }
    			...
    }
    ```

  #### 2.1.2 Factory Method Pattern

  - Factory Objects

    - Example

      ```java
      public class KnifeFactory {
          public Knife createKnife(String knifeType) {
              Knife knife = null;
              // create Knife object
              If(knifeType.equals(“steak”)) {
                  knife = new SteakKnife();
              } else if (knifeType.equals(“chefs”)) {
                  knife = new ChefsKnife();
              }
              return knife;
          }
      }
      ```

      ```java
      public class KnifeStore {
          private KnifeFactory factory;
      
          // require a KnifeFactory object to be passed 
          // to this constructor:
          Public KnifeStore(KnifeFactory factory) {
              this.factory = factory;
          }
      }
      ```

      ```java
      public Knife orderKnife(String knifeType) {
          Knife knife;
          //use the create method in the factory 
          knife = factory.createKnife(knifeType);
          //prepare the Knife 
          knife.sharpen();
          knife.polish();
          knife.package();
          return knife;
      }
      ```

  - Benefits of Factory Objects

    If there are multiple clients that want to instantiate the same set of classes, then by using a Factory object, you have cut out redundant code and made the software easier to modify.

  - Factory Method

    The Factory Method design intent is to define an interface for creating objects, but let the subclasses decide which class to instantiate. 

    ```java
    public abstract class KnifeStore {
        public Knife orderKnife(String knifeType) {
            Knife knife;
            // now creating a knife is a method in the class
            knife = createKnife(knifeType);
            // now creating a knife is a method in the = createKnife(knifeType);
            knife.sharpen();
            knife.polish();
            knife.package();
            return knife;
        }
    
        abstract Knife createKnife(String type);
    }
    ```

    ```java
    public BudgetKnifeStore extends KnifeStore {
        // up to any subclass of KnifeStore to define this method
        Knife createKnife(String knifeTYpe) {
            if(knifeType.equals("steak")) {
                return new BudgetSteakKnife();
            }else if(knifeType.equals("chefs")) {
                return new BudgetChefsKnife();
            }
            //.. more types
            else {
                return null;
            }
        }
    }
    ```

  - UML Diagrams

    ![](https://i.imgur.com/LyY6zvN.png)

    ![](https://i.imgur.com/zFQ20Ce.png)

  ### 2.2 Structural Patterns

  Structural patterns describe how objects are connected to each other. 

  #### 2.2.1 Façade Pattern

  - What are the conditions required for you to use the facade design pattern?

    - You need to simplify the interaction with your subsystem for client classes.
    - You need a class to act as an interface between your subsystem and a client class.

  - Façade

    A façade is a wrapper class that encapsulates a subsystem in order to hide
    the subsystem’s complexity, and acts as a point of entry into a subsystem
    without adding more functionality in itself. 

  - Without the pattern

    ![](https://i.imgur.com/C3Fp1ke.png)

  - With the pattern

    ![](https://i.imgur.com/zqHStvH.png)

      - Step 1: Design the Interface

    ```java
    public interface IAccount {
        public void deposit(BigDecimal amount);
        public void withdraw(BigDecimal amount);
        public void transfer(BigDecimal amount);
        public int getAccountNumber();
    }
    ```

      - Step 2: Implement the Interface with one or more classes

    ```java
    public class Chequing implements IAccount {... }
    public class Saving implements IAccount { ... }
    public class Investment implements IAccount { ... }
    ```

      - Step 3: Create the façade class and wrap the classes that
        implement the interface

    ```java
    public class BankService {
        private Hashtable<int, IAccount> bankAccounts;
    
        public BankService() {
            this.bankAccounts = new Hashtable<int, IAccount>
        }
    
        public int createNewAccount(String type, BigDecimal initAmount) {
            IAccount newAccount = null;
            switch (type) {
                case "chequing":
                    newAccount = new Chequing(initAmount);
                    break;
                case "saving":
                    newAccount = new Saving(initAmount);
                    break;
                case "investment":
                    newAccount = new Investment(initAmount);
                    break;
                default:
                    System.out.println("Invalid account type");
                    break;
            }
            if (newAccount != null) {
                this.bankAccounts.put(newAccount.getAc countNumber(), newAccount);
                return newAccount.getAccountNumber();
            }
            return -1;
        }
    
        public void transferMoney(int to, int from, BigDecimal amount) {
            IAccount toAccount = this.bankAccounts.get(to);
            IAccount fromAccount = this.bankAccounts.get(from);
            fromAccount.transfer(toAccount, amount);
        }
    }
    ```

      - Step 4: Use the façade class to access the subsystem

    ```java
    public class Customer {
        public static void main(String args[]) {
            BankService myBankService = new BankService();
            int mySaving = myBankService.createNewAccount("saving", new BigDecimal(500.00));
            int myInvestment = myBankService.createNewAccount("investment", new BigDecimal(1000.00));
    
            myBankService.transferMoney(mySaving, myInvestment, new BigDecimal(300.00));
        }
    }
    ```

  - What are the key design principles are used to implement the facade design pattern?

    Encapsulation, information hiding, separation of concerns

  - In summary, the façade design pattern:

    - Is a means to hide the complexity of a subsystem by encapsulating
      it behind a unifying wrapper called a façade class.
    - Removes the need for client classes to manage a subsystem on
      their own, resulting in less coupling between the subsystem and the
      client classes.
    - Handles instantiation and redirection of tasks to the appropriate
      class within the subsystem.
    - Provides client classes with a simplified interface for the subsystem.
    - Acts simply as a point of entry to a subsystem and does not add
      more functional the subsystem.

  #### 2.2.2 Adapter Pattern

  ![](https://i.imgur.com/hAbK8ao.png)

  - Example

    ![](https://i.imgur.com/3L3WKtV.png)

    - Step 1: Design the target interface

      ```java
      public interface WebRequester {
          public int request(Object);
      }
      ```

    - Step 2: Implement the target interface with the adapter
      class

      ```java
      public class WebAdapter implements WebRequester {
          private WebService service;
      
          public void connect(WebService currentService) {
              this.service = currentService;
              /* Connect to the web service */
          }
      
          public int request(Object request) {
              Json result = this.toJson(request);
              Json response = service.request(result);
              if (response != null)
                  return 200; // OK status code
              return 500; // Server error status code
          }
          
          private Json toJson(Object input) { ... }
      }
      ```

    - Step 3: Send the request from the client to the adapter
      using the target interface

      ```java
      public class WebClient {
          private WebRequester webRequester;
      
          public WebClient(WebRequester webRequester) {
              this.webRequester = webRequester;
          }
          
          private Object makeObject() { ... } // Make an Object
      
          public void doWork() {
              Object object = makeObject();
              int status = webRequester.request(object);
              if (status == 200) {
                  System.out.println("OK");
              } else {
                  System.out.println("Not OK");
              }
              return;
          }
      }
      ```

      ```java
      public class Program {
          public static void main(String args[]) {
              String webHost = "Host: https://google.com\n\r";
              WebService service = new WebService(webHost);
              WebAdapter adapter = new WebAdapter();
              adapter.connect(service);
              WebClient client = new WebClient(adapter);
              client.doWork();
          }
      }
      ```

  - What are the characteristics of the adapter design pattern?

    - The client and adaptee classes have incompatible interfaces.
    - An adapter is a wrapper class that wraps the adaptee, hiding it from the client.
    - The client sends requests indirectly to the adaptee by using the adapter’s target interface.
    - The adapter translates the request sent by the client class into a request that the adaptee class is expecting.

  - In summary, an adapter is meant to:

    - Wrap the adaptee and exposes a target interface to the client.
    - Indirectly change the adaptee’s interface into one that the client is
      expecting by implementing a target interface.
    - Indirectly translate the client’s request into one that the adaptee is
      expecting.
    - Reuse an existing adaptee with an incompatible interface.

  #### Assignment: Adapter Pattern

  Use this UML class diagram to help modify the code.

  ![](https://i.imgur.com/Y9MhsXK.png)

  * Code

    CoffeeMachineInterface.java

    ``````java
    public interface CoffeeMachineInterface {
    	public void chooseFirstSelection();
    	public void chooseSecondSelection();
    }
    ``````

    OldCoffeeMachine.java

    ```java
    public class OldCoffeeMachine {
      
    	public void selectA() {
        	// Select A
    		System.out.println(“A - Selected”);
    	}
      
    	Public void selectB() {
        	// Select B
    		System.out.println(“B - Selected”);
    	}
    }
    ```

    CoffeeTouchscreenAdapter.java

    ```java
    public class CoffeeTouchscreenAdapter implements CoffeeMachineInterface {
      
    	private OldCoffeeMachine oldMachine;
    
    	public CoffeeTouchscreenAdapter(OldCoffeeMachine newMachine) {
    		oldMachine = newMachine;
    	}
    	
      	@Override
    	public void chooseFirstSelection() {
    		oldMachine.selectA();
    	}
    	
      	@Override
    	public void chooseSecondSelection() {
    		oldMachine.selectB();
    	}
    }
    ```

  #### 2.2.3 Composite Pattern

  - A composite design pattern is meant to achieve two goals:

    - To compose nested structures of objects, and
    - To deal with the classes for theses objects uniformly. 

  - The following basic design 

    ![](https://i.imgur.com/U9jVhSI.png)

  - Think of composite design patterns as trees:

    ![](https://i.imgur.com/D9w0P36.png)

  - The composite design pattern is used to address two issues.

    - How do we use individual types of objects to build a tree-like structure?
    - How can we treat the individual types of objects uniformly without checking their types?

  - How are these two issues addressed by the composite design pattern?

    - Each individual class is a subtype of an interface or superclass, and will be able to conform to a set of shared behaviors.
    - The composite class is capable of aggregating component classes, which will create a tree-like structure.

  - Example

    ![](https://i.imgur.com/dgHYAb1.png)

    - Step 1: Design the interface that defines the overall type

      This supports polymorphism for your component and leaf
      classes. 

      ```java
      public interface IStructure {
      		public void enter();
      		public void exit();
      		public void location();
      }
      ```

    - Step 2: Implement the composite class

      ![](https://i.imgur.com/Ix1cERh.png)

    - Step 3: Implement the leaf class

      ![](https://i.imgur.com/dFQbdvE.png)

    - How to use it

      ![](https://i.imgur.com/iwgj2NP.png)

  - In summary,  a composite design pattern allows you to build a tree-like structure of objects and treat individual types of those objects uniformly. This is achieved by:

    - Enforcing polymorphism across each class through implementing an interface (or inheriting from a superclass).
    - Using a technique called recursive composition which allows objects to be composed of other objects that are of a common type. 

  #### Practice Peer-graded Assignment: Composite Pattern

  Use the UML class diagram pictured below to help modify the provided code.

  ![](https://i.imgur.com/jRtjTR2.png)

  ```
  --------------------
  [Program.java]
  --------------------
  public class Program {
  
  	public static void main(String args[]) {
  	
  	// Make new empty "Study" playlist 
  	Playlist studyPlaylist = new Playlist("Study");		
  
  	// Make "Synth Pop" playlist and add 2 songs to it.
  	Playlist synthPopPlaylist = new Playlist("Synth Pop");
  	Song synthPopSong1 = new Song("Girl Like You", "Toro Y Moi" );
  	Song synthPopSong2 = new Song("Outside", "TOPS");
  	synthPopPlaylist.add(synthPopSong1);
  	synthPopPlaylist.add(synthPopSong2);
  
  	// Make "Experimental" playlist and add 3 songs to it, 
  	// then set playback speed of the playlist to 0.5x
  	Playlist experimentalPlaylist = new Playlist("Experimental");		
  	Song experimentalSong1 = new Song("About you", "XXYYXX");
  	Song experimentalSong2 = new Song("Motivation", "Clams Casino");	
  	Song experimentalSong3 = new Song("Computer Vision", "Oneohtrix Point Never");
  	experimentalPlaylist.add(experimentalSong1);
  	experimentalPlaylist.add(experimentalSong2);
  	experimentalPlaylist.add(experimentalSong3);
  	float slowSpeed = 0.5f;
  	experimentalPlaylist.setPlaybackSpeed(slowSpeed);
  	
  	// Add the "Synth Pop" playlist to the "Experimental" playlist
  	experimentalPlaylist.add(synthPopPlaylist);
  
  	// Add the "Experimental" playlist to the "Study" playlist
  	studyPlaylist.add(experimentalPlaylist);
  
  	// Create a new song and set its playback speed to 1.25x, play this song, 
  	// get the name of glitchSong → "Textuell", then get the artist of this song → "Oval"
  	Song glitchSong = new Song("Textuell", "Oval");
  	float fasterSpeed = 1.25f;
  	glitchSong.setPlaybackSpeed(fasterSpeed);
  	glitchSong.play(); 
  	String name = glitchSong.getName(); 
  	String artist = glitchSong.getArtist(); 
  	System.out.println ("The song name is " + name );
  	System.out.println ("The song artist is " + artist );
  
  	// Add glitchSong to the "Study" playlist
  	studyPlaylist.add(glitchSong);
  
  	// Play "Study" playlist.
  	studyPlaylist.play(); 
  
  	// Get the playlist name of studyPlaylist → "Study"
  	System.out.println ("The Playlist's name is " + studyPlaylist.getName() );
  	}
  }
  
  --------------------
  [IComponent.java]
  --------------------
  public interface IComponent {
  
  // Your code goes here!
  
  }
  
  --------------------
  [Playlist.java]
  --------------------
  public class Playlist implements IComponent {
  
  	public String playlistName;
  	public ArrayList<IComponent> playlist = new ArrayList();
  
  	public Playlist(String playlistName) {
  		this.playlistName = playlistName;
  	}
  
    // Your code goes here!
  
  }
  
  --------------------
  [Song.java]
  --------------------
  public class Song implements IComponent {
  	public String songName;
  	public String artist;
  	public float speed = 1; // Default playback speed
  
  	public Song(String songName, String artist ) {
  		this.songName = songName;
  		this.artist = artist; 
  	}
  	
  	// Your code goes here!
  	
  }
  ```

  * Solution

  ```
  --------------------
  [Program.java]
  --------------------
  public class Program {
  
  	public static void main(String args[]) {
  	
  	// Make new empty "Study" playlist 
  	Playlist studyPlaylist = new Playlist("Study");		
  
  	// Make "Synth Pop" playlist and add 2 songs to it.
  	Playlist synthPopPlaylist = new Playlist("Synth Pop");
  	Song synthPopSong1 = new Song("Girl Like You", "Toro Y Moi" );
  	Song synthPopSong2 = new Song("Outside", "TOPS");
  	synthPopPlaylist.add(synthPopSong1);
  	synthPopPlaylist.add(synthPopSong2);
  
  	// Make "Experimental" playlist and add 3 songs to it, 
  	// then set playback speed of the playlist to 0.5x
  	Playlist experimentalPlaylist = new Playlist("Experimental");		
  	Song experimentalSong1 = new Song("About you", "XXYYXX");
  	Song experimentalSong2 = new Song("Motivation", "Clams Casino");	
  	Song experimentalSong3 = new Song("Computer Vision", "Oneohtrix Point Never");
  	experimentalPlaylist.add(experimentalSong1);
  	experimentalPlaylist.add(experimentalSong2);
  	experimentalPlaylist.add(experimentalSong3);
  	float slowSpeed = 0.5f;
  	experimentalPlaylist.setPlaybackSpeed(slowSpeed);
  	
  	// Add the "Synth Pop" playlist to the "Experimental" playlist
  	experimentalPlaylist.add(synthPopPlaylist);
  
  	// Add the "Experimental" playlist to the "Study" playlist
  	studyPlaylist.add(experimentalPlaylist);
  
  	// Create a new song and set its playback speed to 1.25x, play this song, 
  	// get the name of glitchSong → "Textuell", then get the artist of this song → "Oval"
  	Song glitchSong = new Song("Textuell", "Oval");
  	float fasterSpeed = 1.25f;
  	glitchSong.setPlaybackSpeed(fasterSpeed);
  	glitchSong.play(); 
  	String name = glitchSong.getName(); 
  	String artist = glitchSong.getArtist(); 
  	System.out.println ("The song name is " + name );
  	System.out.println ("The song artist is " + artist );
  
  	// Add glitchSong to the "Study" playlist
  	studyPlaylist.add(glitchSong);
  
  	// Play "Study" playlist.
  	studyPlaylist.play(); 
  
  	// Get the playlist name of studyPlaylist → "Study"
  	System.out.println ("The Playlist's name is " + studyPlaylist.getName() );
  	}
  }
  
  --------------------
  [IComponent.java]
  --------------------
  public interface IComponent {
  	void play();
  	void setPlaybackSpeed(float speed);
  	String getName();
  }
  
  --------------------
  [Playlist.java]
  --------------------
  public class Playlist implements IComponent {
  
  	public String playlistName;
  	public ArrayList<IComponent> playlist = new ArrayList();
  
  	public Playlist(String playlistName) {
  		this.playlistName = playlistName;
  	}
  
  	public void add(IComponent component) {
     		 playlist.add(component);
  }
  
  	public void remove(IComponent component) {
  		playlist.remove(component);
  	}
  	
  	public void play(){
  		for(IComponent component : playlist) {
  			component.play();
  	    	}
  	}
  
  	public void setPlaybackSpeed(float speed) {
  	      for(IComponent component : this.playlist){
  		component.setPlaybackSpeed(speed);
  	    }
  	}
  
  	public String getName() {
  	    return this.playlistName;
  	}
  }
  
  --------------------
  [Song.java]
  --------------------
  public class Song implements IComponent {
  	public String songName;
  	public String artist;
  	public float speed = 1; // Default playback speed
  
  	public Song(String songName, String artist ) {
  		this.songName = songName;
  		this.artist = artist; 
  	}
  
  	public void play() {
  		// Play the song using this.speed
  	}
  
  	public void setPlaybackSpeed(float speed) {
  		this.speed = speed;
  	}
  
  	public String getName() {
  		return this.songName;
  	}
  
  	public String getArtist() {
  		return this.artist;
  	}
  }
  ```

  #### 2.2.4 Proxy Pattern

  - The three most common scenarios where proxy classes are used are: 

    - To act as a virtual proxy.
    - To act as a protection proxy.
    - To act as a remote proxy.

  - The UML diagram

    ![](https://i.imgur.com/ilzXxLY.png)	

    - Proxy and RealSubject are subtypes of Subject.
    - The proxy design pattern achieves polymorphism through implementing a Subject interface.

  - Example

    ![](https://i.imgur.com/pQ0LteA.png)

      - Step 1: Design the subject interface

     		![](https://i.imgur.com/0uHuTei.png)
          

      - Step 2: Implement the real subject class
         		
        ![](https://i.imgur.com/CfM1mzo.png)

      - Step 3: Implement the proxy class

        ![](https://i.imgur.com/GwoaYKo.png)

  - Which are key responsibilities of the proxy class?

    - It protects the real subject class by checking the client's request and controlling access to the real subject class.
    - It acts as a wrapper class for the real subject class.

  - The main features of a proxy design pattern are:

    - To use the proxy class to wrap the real subject class.
    - To have a polymorphic design so that the client class can expect the same interface for the proxy and real subject classes.
    - To use a lightweight proxy in place of a resource intensive object until it is actually needed.
    - To implement some form of intelligent verification of requests from client code in order to determine if, how, and to whom the requests should be forwarded to.
    - To present a local representation of a system that is not in the same physical or virtual space.

  #### 2.2.5 Decorator Pattern

  - Decorator aggregates other types of components which will allow us to stack components on top of each other, and decorator serves as the abstract superclass of concrete decorator classes that will provide an increment of behavior. 

    ![](https://i.imgur.com/8dFYQ4R.png)

  - What are the reasons for using the decorator design pattern?

    - To reduce the number of classes needed to offer a combination of behaviors.
    - The decorator design pattern allows objects to dynamically add behaviors to others.

  - Example

    ![](https://i.imgur.com/ETQokYe.png)

      - Step 1: Design the component interface

      	![](https://i.imgur.com/5WoOjF8.png)
        

      - Step 2: Implement the interface with your base concrete component class

        ![](https://i.imgur.com/FxJ7oCL.png)

    - Step 3: Implement the interface with your abstract decorator class

      ![](https://i.imgur.com/zQHaftg.png)

    - Step 4: Inherit from the abstract decorator and implement the component interface with concrete decorator classes

      ![](https://i.imgur.com/jqweOeP.png)
          

      - In action

      ![](https://i.imgur.com/pBpdXc9.png)
          

          ![](https://i.imgur.com/K8m75rR.png)

  - The main features of a decorate design pattern are:

    - We can add, in effect, any number of behaviors dynamically to an object at runtime by using aggregation as a substitute for pure inheritance
    - Polymorphism is achieved by implementing a single interface.
    - Aggregation lets us create a stack of objects.
    - Each decorator object in the stack is aggregated in a one-to-one relationship with the object below it in the stack. And,
    - By combining aggregation and polymorphism, we can recursively invoke the same behavior down the stack and have the behavior execute upwards from the concrete component object.

  ### 2.3 Behavioural patterns

  Behavioural patterns focus on how objects distribute work, and describe
  how each object does a single cohesive function. Behavioural patterns also
  focus on how independent objects work towards a common goal. 


  # Week 2

  ## 1. Behavioural patterns

  - These are patterns that focus on ways that individual objects collaborate to achieve a common goal.

  ### 1.1 Template Method Pattern

  - The template method defines the program skeleton of an algorithm in an operation, deferring some steps to subclasses.

  - The UML for a self-driving vehicle based template method.

    ![](https://i.imgur.com/QPNFq3z.png)

    ![](https://i.imgur.com/uGlYx0m.jpg)

    ![](https://i.imgur.com/Q13a4Fh.jpg)

  - Example

    ![](https://i.imgur.com/xkUT2BY.png)

    ![](https://i.imgur.com/5Xdza16.png)

      ![](https://i.imgur.com/OgMFf2B.png)

  ### 1.2 Chain of Responsibility Pattern

  - The chain of responsibility design pattern is a chain of objects that are responsible for handling requests.

   	![](https://i.imgur.com/quDBmsH.png)
      

  - This pattern is similar to exception handling in Java.

   	![](https://i.imgur.com/02AsKIj.png)
      

  - The UML diagram

    ![](https://i.imgur.com/O8RmEgX.png)

  - Each filter needs to go through the following steps:

    - 1. Check if rule matches

    - 2. If it matches, do something specific

    - 3. If it doesn’t match, call the next filter in the list

      It may be helpful to use the template pattern learned in a previous lesson to ensure that each class will handle a request in a similar way, following the above steps.

  ### 1.3 State Pattern

  - This pattern is primarily used when you need to change the behaviour of an object based upon changes to its internal state or the state it is in at run-time.

  - The UML diagram

    ![](https://i.imgur.com/C4A45xN.png)

      - For example, imagine you want to purchase a chocolate bar from the vending machine, that costs one dollar. Below is the UML state diagram:

      	![](https://i.imgur.com/nAbFXY6.png)
          

          - The State interface
          
          	- UML diagram
          	
          		![](https://i.imgur.com/SdDOrkS.png)       
              
              - Code
              
              	![](https://i.imgur.com/igS0v1q.png)
              
          - States
          
          	- Idle
          	
              	![](https://i.imgur.com/qPSJSPs.png)
              
              - HasOneDollar
               
              	![](https://i.imgur.com/wueT2Up.png)
              
              	![](https://i.imgur.com/j5OXM8y.png)
              
          - Vending Machine
          
          	![](https://i.imgur.com/HFkD3gB.png)
              
              ![](https://i.imgur.com/UaiAwTA.png)

  ### 1.4 Command Pattern

  - Purposes of the Command Pattern:
    - One purpose of using the command pattern is to store and schedule different requests. One is to store and schedule different requests. If requests are turned into
      command objects in your software, then 
      - Store them into lists
      - Manipulate them before they are completed
      - Put them onto a queue
    - Another purpose for the command pattern is to allow command to be
      undone or redone.
  - The UML diagram
    ![](https://i.imgur.com/FclD4Ny.png)
  - Example of "pasting text"
    - Concerte command: 
      ![](https://i.imgur.com/iiyhhiR.png)
    - Invoker:
      ![](https://i.imgur.com/YPmh5az.png)
  - Benefits of the Command Pattern:
    - Allows commands to be manipulated as objects
    - Decouples the objects
    - Allows logic to be pulled from user interfaces

  ### 1.5 Mediator Pattern

  In the Mediator pattern, you will add an object that will talk to all of these other objects and coordinate their activities

  - Implementation
    ![](https://i.imgur.com/mwOXl7m.png)
  - A common usage for the mediator pattern, identified by the Gang of Four, is for dialog boxes with many components such as a check box or a radio button 

  ### 1.6 Observer Pattern

  A pattern where a subject keeps a list of observers.

  - Imagine you have subscribed to a blog, and would like to receive notifications of any changes made to the blog.
    - The sequence diagram for this example:
      ![](https://i.imgur.com/xRTpgWM.png)
    - The UML diagram for this example:
      ![](https://i.imgur.com/4umqkUq.png)
    - The Java code for this example:
      ![](https://i.imgur.com/nGxSUgj.png)
      ![](https://i.imgur.com/gty1xZ9.png)
      ![](https://i.imgur.com/Y8EXrZA.png)
      ![](https://i.imgur.com/2Hkkjp6.png)

  #### Assignment: Observer Pattern

  Use the UML class diagram below to help modify the provided code
  ![](https://i.imgur.com/p2OIjWm.png)

  * Code

  ```
  --------------------
  [Subject.java]
  --------------------
  public interface Subject {
  
  }
  
  --------------------
  Channel.java
  --------------------
  public class Channel implements Subject {
  
  }
  
  --------------------
  Observer.java
  --------------------
  public interface Observer {
  	public void update(String status);
  }
  
  --------------------
  Follower.java
  --------------------
  public class Follower implements Observer {
  
  }
  ```

  * Solution

  ```
  --------------------
  [Subject.java]
  --------------------
  public interface Subject {
  	public void registerObserver(Observer observer);
  	public void removeObserver(Observer observer);
  	public void notifyObservers();
  }
  
  --------------------
  Channel.java
  --------------------
  	private ArrayList<Observer> observers = new ArrayList<Observer>();
  	private String channelName;
  	private String status;
  
  	public Channel(String channelName, String status) {
  		this.channelName = channelName;
  		this.status = status;
  	}
  	
  	public String getChannelName() {
  		return channelName;
  	}
  	
  	public void setChannelName(String channelName) {
  		this.channelName = channelName;
  	}
  	
  	public string getStatus() {
  		return status;
  	}
  
  	public void setStatus(String status) {
  		this.status = status;
  		notifyObservers();
  	}
  
  	public void notifyObservers() {
  		for (Observer obs : observers) {
  			obs.update(this.status);
  		}
  	}
  	
  	public void registerObserver(Observer observer) {
  		observers.add(observer);
  	}
  
  	public void removeObserver(Observer observer) {
  		observers.remove(observer);
  	}
  
  --------------------
  Observer.java
  --------------------
  public interface Observer {
  	public void update(String status);
  }
  
  --------------------
  Follower.java
  --------------------
  public class Follower implements Observer {
  
  	private String followerName;
  
  	public Follower(String followerName) {
  		this.followerName = followerName;
  	}
  
  	public String getFollowerName() {
  		return followerName;
  	}
  
  	public void setFollowerName(String followerName) {
  		this.followerName = followerName;
  	}
  
  	public void update(String status) {
  		//send message to followers that Channel is live.
  	}
  	
  	public void play() {
  		//play channel
  	}
  
  }
  ```

  # Week 3

  ## 1. Model-View-Controller Pattern

  A diagram of a simple MVC pattern
  ![](https://i.imgur.com/cGlvVpi.png)

  - Java code example
    Imagine you are creating an interface for a grocery store, where cashiers can enter orders, and they are displayed. Customers and cashiers should be able to see the list of items entered into the order with a barcode scanner, and see the total bill amount. Cashiers should also be able to make corrections if necessary.

    - Model
      ![](https://i.imgur.com/Vv98tLY.png)
      ![](https://i.imgur.com/no5bQlq.png)
    - View	![](https://i.imgur.com/H7GhWnG.png)
      ![](https://i.imgur.com/P2IKs9w.png)
    - Controller
      ![](https://i.imgur.com/qqWP8Ur.png)

  #### Assignment – MVC Pattern

  You have been asked by a local coffee shop to create a system that allows managers to see, edit and add employee information. You have decided to create them a web application. They have informed you that they may decide to grow and expand later on and would like the system to be flexible to expansion. 
  Create a UML class diagram that displays the basic MVC pattern for this web application. The system should keep track of an **employee’s name**, **ID number**, **job title** and **salary**. The controller should be able to get the employee model’s properties (getter methods) and change the properties (setter methods). The view should only display employee info.
  ![](https://i.imgur.com/oRM6OvQ.png)

  ## 2. Design Principles Underlying Design Patterns

  ### 2.1 Liskov Substitution Principle

  If inheritance is not used correctly, it can lead to a violation of the “Liskov Substitution Principle”. This principle uses substitution to determine whether or not inheritance has been properly used. The Liskov Substitution Principle states that: 

  **If a class, S, is a subtype of a class, B, then S can be used to replace all instances of B without changing the behaviors of a program.**

  #### 2.1.1 Inheritance Guidelines

      1. The condition used to determine if a base class should or should not invoke a method cannot be "strengthened" by a subclass. That is, a subclass cannot add more conditions to determine if a method should be called.
      2. The condition of the program after the execution of a method cannot be "weakened" by a subclass. This means that the subclass should cause the state of the program to be in the same state as the base class after a method call. Subclasses are allowed to "strengthen" the postcondition of a program. For example, if the base class sets an alarm for a specific date, the subclass must do the same, but the result can be more precise by setting the specific hour as well.
      3. Invariant conditions that exist in the base class, must also remain invariant in the subclass. Since invariant conditions are expected to be immutable, the subclass should not change them as it may cause a side effect in the behaviours of the base class or the program.
      4. Immutable characteristics of a base class must not be changed by the subclass. Since classes can modify their own characteristics, a subclass can modify all the characteristics that it inherits from the base. However, the base class may encapsulate attributes that should be fixed values. These values are identifiable by observing whether or not they are changed in the program, or by a method in the base class. If it is not changed, then these attributes are considered immutable. Subclasses can get around this problem by declaring and modifying their own attributes. The attributes of a subclass are not visible to the base class and therefore, do not affect the behaviour of the base class.

  ### 2.2 Open/Close Principle

  It states that classes should be open for extension but closed to change.

  You should considered a class as being “closed” to editing once it has been:

   - Tested to be functioning properly
   - All the attributes and behaviours are encapsulated
   - proven to be stable within your system

  If a system needs to be extended or have more features added, then the “open” side of the principle comes into play.

  - **inheritance** of a superclass
    ![enter image description here](https://i.imgur.com/zofGXrs.png)
    If you want to limit a class so that it is no longer extendable, it can be declared as “final” to prevent further inheritance.

  - A class can be open, is if the class is abstract and enforces the open/close principle through polymorphism.
    ![enter image description here](https://i.imgur.com/oEo8wIN.png)

  ####  The Open/Close Principle

  The open/close principle is used to keep stable parts of a system separate from varying parts. While you want to be able to add more features to your system, you don't want to do it at the expense of disrupting something that works. By using extension over change, you can work on the varying parts without introducing unwanted side effects into the stable parts.

  #### The goal of object-oriented design is to:

   - Help keep a system stable by “closing” classes to change.
   - Allow a system to open for extension through inheritance or Design Patterns interfaces.

  ### 2.3 Dependency Inversion Principle 

  #### The Dependency Inversion Principle is a means to:

   - Change the referencing of concrete classes from being direct to indirect
   - Generalize the behaviors of your concrete classes into abstract classes and interfaces
   - Have client classes interact with your sysrem through a generalization rather than directly with concrete resources
   - Put emphasis on high level dependency over low level concrete dependency

  ### 2.4 Composing Objects Principle

  This principle states that classes should achieve code reuse through aggregation rather than inheritance. Design patterns like the **composite design pattern** and **decorator design pattern** use this design principle.

  #### Advantages

  - Aggregation and delegation offer less coupling thant inheritance
  - "Arms length" relationship
    ![enter image description here](https://i.imgur.com/TkJWh9N.png)
  - Provides your system with more flexibility
  - Dynamically change the behaviod of objects at run time

  #### Disadvantages

  The biggest drawback of composition is that you must provide implementations for all behaviour, without the benefit of inheritance to share code. That means that you might have very similar implementation across classes.

  #### Summary

  The composing objects principle will:

   -   Provide a means of code reuse without the tight coupling of inheritance.
   -   Allow objects to dynamically add behaviours at run time. 
   -   Provide your system with more flexibility, so that less time needs to
       be spent on system updates.

  #### Did You Know?

  Some good tips and questions to help you decide whether the best solution for your system is composition or inheritance include:

   - You need to examine the needs of your system in order to determine whihc design principle is appropriate
   - Do you have a set of related classes or unrelated classes?
   - What is a common behaviour between them?
   - Do you need specialized classes to handle specific cases or do you need a different implementation of the same behaviour?

  ### 2.5 Interface Segregation Principle

  The interface segregation principle states that **a class should not be forced to depend on methods it does not use**. This means that any classes that implement an interface, should not have "dummy" implementation of any methods defined in the interface. Instead, you should **split large interfaces into smaller generalizations**.

  Bad example:
  ![enter image description here](https://i.imgur.com/gjxRkdA.png)

  Good example:
  ![enter image description here](https://i.imgur.com/CYA8fV8.png)

  Interfaces are an integral part of object oriented systems.

  #### Summary

  The interface segregation principle states that:

  -   A class should not be forced to depend on methods it does not use.
  -   Interfaces should be split up in such a way that it can properly    
      describe the separate functionalities of your system.

  Remember that interfaces are descriptions of what parts of your system can do.

  ### 2.6 Principle of Least Knowledge

  To manage complexity, one idea is that **a class should be designed so that it does not need to know about and depend upon almost every other class in the system**.

  #### Law of Demeter

  The underlying idea of this law is that **classes should know about and interact with as few other classes as possible**. This means that any class should only communicate with it "immediate friends". These "friends" would be other classes that one class should only know about.

  ##### First Rule

  A method, M, in an object, O, can call on any other method within O itself.
  ![enter image description here](https://i.imgur.com/Bu3ZJ5P.png)

  ##### Second Rule

  A method, M, can call the methods of any parameter P.
  ![enter image description here](https://i.imgur.com/a2pMoze.png)

  ##### Third Rule

  A method, M, can call a method, N, of an object, I, if I is instantiated within M.
  ![enter image description here](https://i.imgur.com/Bag4z3U.png)

  ##### Fourth Rule

  Any method, M, in object O, can invoke methods of any type of object that is a direct component of O.
  ![enter image description here](https://i.imgur.com/x0AR1fg.png)

  The Law of Demeter apperas to be a complicated and abstract concept, but all the rules come down to the principle that **your should not allow a method to acces another method by "reaching through" an object**. This means that **a method should not invoke methods of any object that is not local.**

  #### “Reach Through"

  **Returned objects must of the same type as:**

  - Those declared in the method parameter.
  - Those declared and instantiated locally in the method.   
  - Those declared in instance variable of the class that encapsulates
    the method.

  **According to this design principle, a method, M, of an object should only call other methods if they are:**

    1.  Encapsulated within the same object.
    2.  Encapsualted within an object that is in the parameters of M.
    3.  Encapsulated within an object that is instantiated inside the M.
    4.  Encapsulated within an object that is referenced in an instance
        variable of the class for M.
