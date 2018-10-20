#Pay Api example

* Run as standart java application. NO need for container
* Api swagger documentaion http://localhost:8080/broker/api-doc
In this application of api realisation is parsed in to documentation. So we always will have documentation up to date


## Testing
Run application in one process(you may use ide or standart java run jar syntax) then run 'pay/IntegrationTest' from ide.

Application will apply transfeer request to accounts in database and you will get error response when balance is not enough.
And we will not throw exception if customer sends money to incorrect recipient id ;) Just a joke, but here it works this way, just like in blockchain and may be fixed.