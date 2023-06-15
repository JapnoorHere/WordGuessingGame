# WordGuessingGame
 
This repository conssis of word guessing game.

Instructions : 

1. You have to answer 15 questions .
2. Every question should be solved 30 seconds.
3. Every question consists of 1 point.
4. Result will displayed after completion of 15 questiones either wrong or correct.

<b>Some Images and Videos Of game</b>

<div class="horizontal-image">
  <img src="https://github.com/Japnoor2104/WordGuessingGame/assets/109523523/8592862c-120d-4279-b5fe-7a68c31bac10" width="150" height="300" alt="Login Page" />
  <img src="https://github.com/Japnoor2104/WordGuessingGame/assets/109523523/bce57421-5c54-4c8c-9068-3d4cec771005" width="150" height="300" alt="Login Page" 
       />
 <img src="https://github.com/Japnoor2104/WordGuessingGame/assets/109523523/5db78f67-966c-442b-a3bc-541b4e7dc3de" width="150" height="300" alt="Login Page" 
       />
</div>

https://github.com/Japnoor2104/WordGuessingGame/assets/109523523/dd78debc-34c1-428e-86a5-779823110686

All questions are store in Firebase manually : 

        var hashMap = HashMap<String, String>()
        hashMap["A rhythmic movement to music"] = "Dance"
        hashMap["An expression of happiness or joy"] = "Smile"
        hashMap["A sandy shore by the sea or a lake"] = "Beach"
        hashMap["The state of resting or slumbering"] = "sleep"
        hashMap["A written or printed work of literature or reference"] = "Book"
        hashMap["It's a fruit with either red or green color"] = "Apple"
        hashMap["It's a small, fast mammal often associated with cheese"] = "Mouse"
        hashMap["It's a type of flower associated with love and romance"] = "Rose"
        hashMap["It's a musical instrument with black and white keys"] = "piano"
        hashMap["It's a board game with black and white pieces"] = "chess"
        hashMap["A season characterized by falling leaves"] = "Autumn"
        hashMap["It's a large body of water with salt content"] = "Ocean"
        hashMap["A mode of transportation with two wheels"] = "Bike"
        hashMap["A condiment often used to add flavor to food."] = "Salt"
        hashMap["A large building where books are kept"] = "Moon"
        hashMap["A garment worn to protect the body from rain"] = "coat"
        hashMap["A vehicle that travels on tracks and carries people."] = "Train"
        hashMap["A country known for its pyramids and sphinx"] = "Egypt"
        hashMap["A fruit that is yellow and has a curved shape"] = "Banana"
        hashMap["A sport that involves hitting a ball with a racket"] = "Tennis"
        hashMap["A device used to capture and store images."] = "Camera"
        hashMap["A celestial body that shines at night in sky."] = "Star"
        hashMap["It's a season characterized by warm weather, longer days"] = "Summer"
        hashMap["A device used to help people communicate"] = "Phone"
        hashMap["A beverage made from crushed grapes."] = "Juice"
        hashMap["It's a fruit with a yellow peel and a sour taste"] = "Lemon"
        hashMap["A device used to measure time"] = "Clock"
        hashMap["A mode of transportation that travels on water."] = "Boat"
        hashMap["A mode of transportation that flies in the sky"] = "plane"
        hashMap["A small, sweet treat often enjoyed on birthdays"] = "cake"
        hashMap["It's a large building where artworks are displayed"] = "museum"
        hashMap["A type of clothing worn on the upper part of the body"] = "Shirt"
        hashMap["A device used for writing and drawing, often with graphite."] = "Pencil"
        hashMap["A mode of transportation that carries people and goods over water"] = "Ship"
        hashMap["A common farm animal that provides milk."] = "cow"
        hashMap["A planet known for its beautiful rings"] = "Saturn"
        hashMap["A small, round bread often used for sandwiches"] = "bun"
        hashMap["A cleansing product used for washing hands or body"] = "Soap"
        hashMap["It's a fluffy, white mass floating in the sky."] = "Cloud"
        hashMap["A transparent liquid that quenches your thirst"] = "water"
        hashMap["It's a best friend, reflecting back your image."] = "Mirror"
        hashMap["A portable ink dispenser that brings words to Paper"] = "Pen"
        hashMap["It's a rubbery savior that corrects your mistakes."] = "Eraser"
        hashMap["A best friend used to save your thoughts"] = "Diary"
        hashMap["A modern marvel that sits on your lap, bringing technology at your fingertips"] =
            "Laptop"
        hashMap["It's a four-legged animal often kept as a pet and says meow"] = "cat"
        hashMap["It's a small, winged insect that makes a buzzing sound and can sting."] = "Bee"
        hashMap["A small, round confectionery with a hard outer shell and a soft center"] = "Candy"
        hashMap["It's a small, handheld device used for capturing audio recordings"] = "mic"
        hashMap["A musical instrument with strings and a hollow body."] = "guitar"
        hashMap["A popular outdoor activity that involves walking in mountains."] = "hiking"
        hashMap["A popular fruit known for its yellow peel and sweet flesh available in summers"] =
            "Mango"
        hashMap["A popular sport played on a grassy field with a round ball and goal posts."] =
            "Soccer"
        hashMap["A popular social media platform known for its short-form videos."] = "Tiktok"
        hashMap["A popular beverage made from fermented grapes."] = "Wine"
        hashMap["It's a common household object that serves as an entryway into a room or building."] =
            "Door"
        hashMap["A object that soars high in the sky, propelled by wind and controlled with string."] =
            "Kite"
        hashMap["A device used to secure something valuable,which requires a key to open"] = "lock"
        hashMap["An assessment usually conducted to evaluate a person's understanding of a subject"] =
            "exam"
        hashMap["A field of study that deals with numbers, quantities, shapes, and patterns,"] =
            "Maths"
        hashMap["A three-dimensional representation of a person or object often made of stone or metal"] =
            "Statue"
        hashMap["A series of steps designed to allow people to move between different levels in a building"] =
            "Stairs"
        hashMap["It's an outdoor space or area adorned with plants, flowers used for relaxation"] =
            "Garden"
        hashMap["A route designed for vehicles, pedestrians to travel from one place to another,"] =
            "Road"
        hashMap["A series of thoughts, images or sensations that occur during sleep"] = "Dream"
        hashMap["A musical composition typically consisting of lyrics and melodies"] = "Song"
        hashMap["A transparent opening in a wall that provide a view to the outside."] = "window"
        hashMap["A living organism rooted in the ground, with stems and leaves,"] = "Plant"
        hashMap["A third planet in our solar system, known for its presence of life."] = "earth"
        hashMap["A soft fabric typically made of cotton, used for drying or wiping the body"] =
            "towel"
        hashMap["A beverage typically made by mixing fruits vigorously together, often with ice"] =
            "shake"
        hashMap["A common food item produced by female animals, including birds and reptiles"] =
            "egg"

        for (each in hashMap) {
            questionRef.child(questionRef.push().key.toString()).setValue(each)

        }
        
Hope you like it!
