# AutoBoard
An android library for easily creating and using custom square boards (often useful as game boards). Whether you're working on a simple game like TicTacToe or a more complex board game like chess, AutoBoard is a useful feature.<return>
## Installation
1. Add this in your project's root build.gradle at the end of repositories:
```java
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the dependency in your app's build.gradle file
```java
dependencies {
    compile 'com.github.sanatkhurana:AutoBoard:a379638096'
}
  
```
## Usage
To begin, add an AutoBoardView to your xml layout file
```xml
<com.android.sanat.autoboard.AutoBoardView
    android:id="@+id/gameView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```
In your corresponding activity/fragment implement AutoBoardTouchInterface.
```java
public class MainActivity extends AppCompatActivity implements AutoBoardTouchInterface {
    ...
    @Override
    public void onAutoBoardTouch(int tX, int tY) {...}
}
```
Next you must setup the board color + dimensions, and initialize the touchListener. An example is shown below
```java
AutoBoardView autoBoardView;
@Override
protected void onCreate(Bundle savedInstanceState) {
    ...
    autoBoardView = (AutoBoardView)findViewById(R.id.gameView);
    autoBoardView.setBoardBackgroundColor(getColor(android.R.color.black));
    autoBoardView.setDimensions(10);
    autoBoardView.initializeAutoBoardTouch(this);
}
```
The `onAutoBoardTouch` method is used to listexn for touch events (on the board).You could change the state of the board on these touch events.
### Changing the board color
```java
autoBoardView.setBoardBackgroundColor(getColor(android.R.color.black));
```
You can also change the grid line color
```java
autoBoardView.setLineColor(getColor(android.R.color.white));
```
### Adding a colored rectangle at the given board location
You could add an opaque square which is treated as a board object
```java
autoBoardView.drawRect(getColor(R.color.colorAccent), tX, tY);
```
Or you could color the background of the square at location `(tX, tY)` such that the added rectangle is not treated as board object. In this case, other objects like bitmaps or text could be added on the colored square.
```java
autoBoardView.drawOverwritableRect(getColor(R.color.colorAccent), tX, tY);
```
### Add text
For a game like minesweeper or tictactoe, you may want to to add some text to a certain square when it is clicked. This can be done using `setCellText`.(Note: this is a board object)
```java
autoBoardView.setCellText(text, tX, tY);
```
You could also format the text to suit your app's needs. An example is shown below.
```java
autoBoardView.setCellTextFormat(getColor(android.R.color.tertiary_text_dark), 50);
```
### Add bitmap images
Add an image to a square. This could be used to represent an X in tictactoe, a flag in minesweeper, or a piece in chess. This, like text, is a board object.
```java
autoBoardView.setBitmap(R.mipmap.ic_launcher, tX, tY);
```
### Getting cell information
When designing the logic for a board game, it is often useful to have a means of getting information about the current board state. This can be done by getting some information about the contents of individual cells on the board. The `isCellEmpty` function returns a boolean value (true if the given cell has no board object). Note: a cell with only an overwritable rectangle is empty.
```java
autoBoardView.isCellEmpty(tX, tY)
```
Another useful function is the `getCellText` function, which returns thet text at the given cell location as a String, and returns null if there is no text there.
```java
autoBoardView.getCellText(tX, tY)
```
### Clearing cells
If a superior board object needs to be replaced by an inferior board object, this can be done by first clearing the cell and then adding the new inferior object. An example usage is shown below.
```java
autoBoardView.clearCell(tX, tY);
```
### Invalidation
Remember to invalidate the current state of the board when you want it to be refreshed to display changes.
```java
autoBoardView.invalidate();
```
## Board object superiority
A bitmap is superior to a text object on the board. This means that is a bitmap is added to a square with some text, then the bitmap will replace the text. However, if some text is added to a square that contains some bitmap, the text will not be added.
Similarly, an opaque rectangle is superior to a bitmap.
## Summary
The following are the board functions that were described in the usage section.
```java
setDimensions(int n);
setBoardBackgroundColor(int color);
setLineColor(int color);
drawRect(int color, int x, int y);
drawOverwritableRect(int color, int x, int y);
setCellTextFormat(int textColor, float textSize);
setCellText(String text, int x, int y);
getCellText(int x, int y); // returns string
setBitmap(int resourceID, int x, int y);
clearCell(int x, int y);
isCellEmpty(int x, int y); // returns boolean
```
