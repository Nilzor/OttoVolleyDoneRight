#A pattern/example on how to do Android HTTP communication with Volley the right way

Volley does that for you, you say? Wrong. Volley solves these problems:

  - Thread allocation for the web requests
  - Local caching of requests
  - Correct implementation of parallell HTTP calls asynchronously
  
Volley does *not* solve the follwing:
  1. Response handling when activities are killed after request is sent
  2. Response processing in the UI-layer when activities are paused and recreated after 
      request is sent (case: device orientation change)
  3. JSON-to POJO object mapping 
  
OttoVolleyDoneRight solves 1 and 2 through the use of the Otto Event Bus and a pattern outlined by
[Thomas Moerman in this SO-answer](http://stackoverflow.com/a/18057498) and partly implemented by
[tslamic in this example](https://github.com/tslamic/AndroidExamples/tree/master/HttpBinVolley). I add to that solution 
handling of HTTP responses that arrive between the `onPause` and `onResume`-events through the `OttoGsonResponseBuffer`-class. 

I solve #3 simply by adding the use of Google Gson and [GsonRequest](https://gist.github.com/ficusk/5474673). 
So thanks to all the guys who created the building blocks for this solution.

PS: If you don't need #3, it's easy to take out the Gson-dependency (but really... it's super neat!)

##Testing
The application is compiled with IntelliJ Ultimate 13. I think it will load in both IntelliJ community edition and Android Studio, though.
When the application is laucnhed, click the "HTTP GET"-button, and rotate the device before the response arrives. 
Or you can manually disable listening for responses temporarily to allow for the buffer to store the responses by toggling 
the switch button. 

##Using it is a library
To use this example as a library:
 
 - Copy all clases in the `nilzor.ottovolley.core` and `nilzor.ottovolley.messages` to your project
 - Add the same dependencies to your project (copy-paste from `pom.xml` if you're using Maven)
 - Create a way to retrieve the singletons as I've done in `ServiceLocator.java`
