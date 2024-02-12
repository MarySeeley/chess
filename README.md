# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
### Phase 2 Chess Server Design
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpZnbvoccKRdYSgM714FElgYAEWBhgAQRAgu3ACajgAI2CcUMWYroBzKBACu2GAGI0wKgE8YAJRRaknMFFFIIaYwHcAFkjApMiVFIAWgA+ckoaKAAuGABtAAUAeTIAFQBdGAB6PVUoAB00AG8cylMAWxQAGhhcTk53aFlqlDLgJAQAX0w2SJhQ8PZpThioW3tfKAAKEqhyqpqVesbm1vaASm6Ijk4+sJExSSHhmC0UMABVXOncuY39iSkZXfVFGLIAUQAZd7gUmBm5jAAGa6Mr-XKYe6HJ5Bfo9bYxNB6BAITaDLjPKGPOoxECjUQoS6Ua6lYAVaq1JZQWR3BTQurPDQxACSADkPlY-gCyfNKQ1qSs2ggYGyUolwbR4UNMXTscc8SgCeI9GBPCTZjzaQc5YzXiL2e9ORKNeSYMAVZ4UhAANbofVis0WyGyo59OFbIYxc2qq22tBoqjbN1hHqUL0W33oAOUYPwZDoMAxABMAAYUwVijcedVvZabeguuhZNpdAZjNAWKcYJ8IHY3EYvD4-AEE89Q9E4klUhlMqo6i40Bnuaa+Y0uu22x6uDEELWkGh1XMKYt+TSA0HQlijjFThcrsOUFqHq7+kzyF8flzcjB3D5PMbASxZAs6qvndqjrCQlLp8b19LNxdGRcXxXxlVVRdNXfY8ZFCM82Q5K9SVNXNIzcUVxVzaD6U4L8f2OVD839fC8IiMNHR9IjoygL8WwwZM0yHLMUIjIjCzQYtMB0fRDCMHQUDtGstH0QQG28Xx-HjYIQzIjtYiEC8UneHs+04AcCkIv1qNCfCZ1rETJk09ANnw3Yt2A9QUAQM4UHAtUjLQI8cLgvUFO+JSKLzP1gVBTzsJ1Uj0WOJEUX-DEvwGSJEWRVEJ1oqTExgVMU0wIsSx44xRmfT5xhgABxHkdjEptJMCQR3WocjYjy950iyLQeQ01itLi78p2OXcCoqThDOa4ywp2QCPwskBPBQEBrTs3rKL9Jy5Rct4L1+Ty0J8iAwSw8y6kCwNPRgRw9D8UyhpgnEYGQBwuq4SY5s-EIzw+b5loa7r-JPHT2pncZEiBK7uBIj7KuiC6wB+v7tNCOjEuSjMXunWIQbBwq0nYzjuLLIxsD0KBsGs+BQLUK6PHE5sEsnIGYgSZI6syOGmpm9BYZ5VkeXHWTAd238FQJK7pq8-qSJCLb5TGiapoc27YPuvVHsvFaiLWjanWFnaEQOo72pOnCom53xecl7bpZZA0jTh+1xTh7gVY5hETh5ZkhAGnawzhh2IZCKGGPTIpXaEVH0ox8wrNndwYAAKQged8p5YxFAQUBrVK1sKqiztzhpunTD6wciihuAIFnKBql9tmgZtvaACtI7QXmQAS-PC+L+2hBMzWhaAs7RvGyaLT5tCDdwo3zyev4HMVvzrbaoKonVp32+GnEu4mhvoEmOuypXou7YqB2B4W2XlvXhNN4fGOQXW7eMVvVVL7dyfdJgI+MBPg85+FmIDHkPWeUmX3qifsAm8iQmkPHQFWQ9zjxCEOIDyZtr73l9uPS2b1YLl2nCFWK7VYztmiqFOK-RPZJTTKlDiAdeI6GAJYRAipYDAGwLjQgzhXDExKlDcmqd5KKWUlkDQ2kp6c3lNZPAQgABCN034d0ETQ8R4CzxuVqu8GAwD7jCC4dHCoKj5EeTsvcFB200HBRik7DmYYMHu09slEhxYgA
