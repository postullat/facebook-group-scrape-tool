# Verba FGPS - Facebook Group Posts Scraper

## !!! This tool is not working anymore since facebook changed layout !!!

This tool was responsible as synchronization mechanism between FB group and site storage
Written in Java and `Selenide Web Driver`. Simple `jar` file scrapes facebook group posts and push them to database

### Important info
The tool has been developed to store data into Firebase.
Currently, export data into `.xsl` file and `postgres` is not supported 

#### What does it scrape
- post text
- post images
- post link
- post date
- author name
- author link
- author avatar

#### How it scrapes
1. tool reads last `N` posts from specified facebook group
2. store them into `database` (needs to be intalled on computer)
3. wait `Z` minutes 
4. go to step 1

`N`, `Z` and `database` parameters are configurable

#### Requirements:  
- java 8;   
- maven 3.6.0
- FireFox 60.3.0esr (64-bit);  

#### Build project: 
 1. Adjust project settings in `PostDataToFirebase.properties` and `application.properties` files
 2. Navigate to project dir from cmd
 2. Run `mvn clean install` command
 
#### Run project: 

 1. Run JAR: `java -jar fbreaper-MILESTONE-2.1.jar --scheduling.enabled=false`
 2. Run JAR with scheduling: `java -jar target\fbreaper-MILESTONE-2.1.jar --scheduling.enabled=true --cron.expression="* */5 * * * *"`
 
### Availabel parameters:  
  
**PostDataToFirebase.properties:**  
 
    fb.login fb.pass   
    fb.group.url
    posts.to.fetch 
     
**application.properties:**  
 
    firebase.jsonfile.path
    firebase.storage.bucket
    cron.expression
    fb.big.images.limit
    fb.big.images.load.timeout
    selenide.timeout

