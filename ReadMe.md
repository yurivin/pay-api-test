#Pay Api example

* Run as standart java application. NO need for container. Main class is `Application.java`
* Api swagger documentaion http://localhost:8080/broker/api-doc
In this application api realisation is parsed in to documentation. So we always have documentation up to date



## Testing
Run application in one process(you may use ide or standard java run jar syntax) then run 'pay/IntegrationTest' from ide.
When application lunched this test will work only ones, becuse account balances will be changed to not expected values

Application will apply transfeer request to accounts in database and you will get error response when balance is not enough.

Also Versioning of account changes implemented to avoid race conditions and disable "double spent" problem.

## Restrictions
We will not throw exception if customer sends money to incorrect recipient id. Here it works this way, just like in blockchain and may be adjusted.

Also Validation of parameters does not happen. Just because "keep it simple" requirement. So one of the way to implement validation is using standart validation api realisation.

Double spend condition tries to happen sender wil not receive any error. Just one of money transfer transactions will be executed.

## Known problems
- Low test coverage
- Only integration tests
- Unhappy cases not tested
- It is allowed to make payment with negative amount

