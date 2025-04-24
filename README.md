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


__How It Works__  
JSON Configuration:   
The application uses JSON files to define discount rules. These files are loaded at runtime and contain the following information:  
Description: A textual description of the discount.  
Discount: The percentage of the discount.  
Target Tag: The product tag to which the discount applies.  
Condition Eligibility: The conditions that must be met for the discount to apply.  
Example JSON (discount-eligibility.json)  

{
  "discountEligibility": [  
    {  
      "description": "Apples 10% off",  
      "discount": 10,  
      "targetTag": "Apples",  
      "conditionEligibility": {  
        "minQuantity": 1,  
        "ofTag": "Apples",  
        "startDate": "2025-04-01",  
        "endDate": "2025-12-31"  
      }  
    },  
    {  
      "description": "Bread 50% off",  
      "discount": 50,  
      "targetTag": "Bread",  
      "conditionEligibility": {  
        "minQuantity": 2,  
        "ofTag": "Soup",  
        "startDate": "2025-04-01",  
        "endDate": "2025-12-31"  
      }  
    }  
  ]  
}    
  
__Key Components__  
ProductService: Manages product retrieval and mapping.  
DiscountEligibilityRepository: Loads discount rules from the JSON file.  
BasketService: Applies discounts to the basket based on the rules.  
BasketPrinter: Handles user input and displays the final basket.  
Adding or Modifying Discounts  
To add or modify discounts:  
Open the discount-eligibility.json file.  
Add or update entries in the discountEligibility array.  
Ensure the JSON structure is valid.  
Error Handling  
If the JSON file is missing or invalid, the application will log an error and continue with an empty discount list.  
Unknown items entered by the user will be ignored, and a warning will be displayed.  
__Requirements__  
Java 17+  
Maven for dependency management  
Spring Boot for application framework  
__Running the Application__  
Build the project using Maven:  
mvn clean install  
Run the application:  
java -jar target/pricing-basket-app.jar  
Enter items when prompted, and view the calculated discounts and total price.  


__Feature 1 : Basket without discount – Simple price sum calculation__  

As a user,  
When I enter my shopping list containing item1, item2, and item3...  
Then the program calculates the total of the item prices and displays the result as:  

Subtotal: £1.30  
(No offers available)  
Total price: £1.30  

__Feature 2 : Basket with discount – Apply offer to total price__  

As a user,  
When I enter my shopping list that includes discounted items (e.g., apples),  
Then the program calculates the subtotal, applies the available discount(s), and displays the result as:  

Subtotal: £3.10  
Apples 10% off: -10p  
Total: £3.00  

__Feature 3 : Currency configuration via environment variable__  

As a developer,
When I set the currency symbol using an environment variable (e.g., CURRENCY_SYMBOL=€),  
Then the program displays all prices using the configured currency symbol, for example:  

Subtotal: €3.10  
Apples 10% off: -10c  
Total: €3.00  
