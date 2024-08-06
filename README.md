Features
Smooth Animation: Utilizes Java Swing's Timer to update the animation every 20 milliseconds.
Colorful Effects: Each ball is assigned a unique color based on the rainbow spectrum.
Boundary Detection: Balls bounce off the circular boundary, reflecting their direction upon collision.
Dynamic Speed: Balls move at varying speeds for a more dynamic and interesting effect.
Timed Removal: Balls are removed from the screen after 60 seconds for a fresh animation.
Getting Started
To run this project on your local machine, follow these steps:

Prerequisites
Java Development Kit (JDK) 8 or higher
Clone the Repository
bash
Code kopieren
git clone https://github.com/yourusername/bouncing-balls-animation.git
cd bouncing-balls-animation
Compile and Run
Compile the Code

bash
Code kopieren
javac BouncingBall2.java
Run the Application

bash
Code kopieren
java BouncingBall2
Project Structure
BouncingBall2.java: Main class containing the animation logic and rendering code.
Ball class: Inner class defining properties and behavior of individual balls.
Code Breakdown
Initialization: Balls are created with random speeds and directions, each having a unique color.
Rendering: The paintComponent method updates the animation and draws trails using a BufferedImage.
Collision Handling: The updatePosition method ensures balls bounce off the circular boundary and remain within the circle.
Timed Removal: A javax.swing.Timer is used to clear balls after 60 seconds.
Contributing
Feel free to fork the repository, make changes, and submit pull requests. We welcome contributions and feedback!

Issues
If you encounter any issues or have suggestions, please open an issue on the GitHub Issues page.

License
This project is licensed under the MIT License. See the LICENSE file for details.

Acknowledgments
Java Swing for GUI and animation capabilities
BufferedImage for creating smooth trails
