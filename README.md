# Covid-19-API Java Client

Simple command line Java program to get information from [Covid-19-API](https://github.com/M-Media-Group/Covid-19-API).
1. When run the application would ask user to input a country name ([ISO 3166-1 alpha-2](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2)).
2. It will display following information for the given country
   1. Current moment 
      1. Confirmed
      2. Recovered
      3. Deaths
      4. Vaccinated level in % of total population
   2. Historical data
      1. New confirmed cases since last data available 

**Disclaimer: [Covid-19-API](https://github.com/M-Media-Group/Covid-19-API) deprecated on the 31st of October 2022.**

## How to run
This project uses Maven as the build automation tool.
### Prerequisites
* Java 17
* Maven

#### Buildg the application
```mvn package```

#### Run the Application 
```java -cp .\target\Covid-19-API-Java-Client-1.0-SNAPSHOT.jar org.mithwick.covid19.client.Main```


## License Information
<pre>
        Copyright(c) 2022 M.S. Wickramarathne

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

            https://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
</pre>