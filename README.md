# GlobalNews
Global News Client side android app for showing news from different rss feeds

This is only the client side part, we have server side part also.

Functionalities:
1. Server will parse the RSS feeds from different NEWS Feed urls,(URLS can be configured for any number of URLS).
2. It will store the parsed details to the MySQL Database.
3. Its protected with a Access_token through this only you can update the URLS feeds and store the details in DB.
4. Android app will send a request to the server to get the latest RSS feeds.
5. Server will respond with the top 30 new details, and the details will be stored to SQLlite db in mobile side, to acces the app even with out internet connection.
6. Once you connected to the internet referesh the app by Pull refresh. this will update the latest news.

Server Side:
PHP and MYSQL - WampServer.

ClientSide:
Android,SQLLite.

To Change this project required details are given in the project itself, download and change it.

Thanks....!

