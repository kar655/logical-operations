## Logical Operations Simplifier

Ony of my projects, fully written in Java with some Unit Tests in JUnit 4.
I used here 
[Shunting yard algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm)
for expression parsing.

Just type in command line expression (separated by whitespaces) 
you want to test or simplify.
By expression, I mean variables connected with operations.

#### This project offers things like:
- Constant folding
- Basic logical operations
- Refer to last expression parsed with _
- Choose what you want to know about parsed expression, from tautology testing to 
printing all possible values

#### It is also very customizable:
- Change how operations symbols look
- Tune operations priority
