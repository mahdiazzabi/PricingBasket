# PricingBasket  
Program and associated unit tests that can price a basket of goods taking into account some special offers.  

The goods that can be purchased, together with their normal prices are:  
• Soup – 65p per tin  
• Bread – 80p per loaf  
• Milk – £1.30 per bottle  
• Apples – £1.00 per bag  
Current special offers:  
• Apples have a 10% discount off their normal price this week  
• Buy 2 tins of soup and get a loaf of bread for half price  

Input should be via the command line in the form PriceBasket item1 item2 item3 …  
For example:  
PriceBasket Apples Milk Bread  
Output should be to the console, for example:  
Subtotal: £3.10  
Apples 10% off: -10p  
Total: £3.00  
If no special offers are applicable the code should output :  
Subtotal: £1.30  
(No offers available)  
Total price: £1.30  

Feature 1 : Basket without discount – Simple price sum calculation  

As a user,  
When I enter my shopping list containing item1, item2, and item3...  
Then the program calculates the total of the item prices and displays the result as:  

Subtotal: £1.30  
(No offers available)  
Total price: £1.30  

Feature 2 : Basket with discount – Apply offer to total price  

As a user,  
When I enter my shopping list that includes discounted items (e.g., apples),  
Then the program calculates the subtotal, applies the available discount(s), and displays the result as:  

Subtotal: £3.10  
Apples 10% off: -10p  
Total: £3.00  

Feature 3 : Currency configuration via environment variable  

As a developer,
When I set the currency symbol using an environment variable (e.g., CURRENCY_SYMBOL=€),  
Then the program displays all prices using the configured currency symbol, for example:  

Subtotal: €3.10  
Apples 10% off: -10c  
Total: €3.00  
