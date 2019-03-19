
public class RandomNumberStart {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(Random.class);
            }
        }.start();
    }
}