package src;

public class TokenGenerator extends Thread {
    private final Bucket bucket;
    private boolean running = true;

    public TokenGenerator(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public void run() {
        while (running) {
            if (bucket.addToken()) {
                System.out.println("Token added.");
            } else {
                System.out.println("Bucket full!");
            }
            try {
                Thread.sleep(500);  // Add token every 0.5s
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopRunning() {
        running = false;
    }
}
