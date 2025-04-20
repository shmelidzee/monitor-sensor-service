# Monitor sensor API
Implementation of a basic REST API for a monitoring sensors

# How to run application
1. Clone repository, go to root directory
2. Run sh run.sh in root directory
   sh run.sh will build the application and run it in a docker container
3. Open http://localhost:8085/swagger-ui/index.html#/ in browser
4. Click to button Authorize and input authorize data
   Default values:
   admin:321
   user:123
5. Select the interesting request and execute it 
6. Check results requests
![image](https://github.com/user-attachments/assets/b64bb0fa-9edb-4097-ab0b-e13fbdf43623)


# How to run main tests
1. Clone repository, go to root directory
2. Run ./gradlew clean build test in root directory
3. You will see the test results in the console
![image](https://github.com/user-attachments/assets/ab5d935c-bce8-4b59-9e81-1300769e81e9)
