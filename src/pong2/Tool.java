package pong2;

import java.util.Random;

public class Tool {
  static int getRandom(int min, int max){ 
    int randNumber = new Random().nextInt(max - min + 1) + min;
	return randNumber;
  }
}
