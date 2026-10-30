public class Semaphore {
    private int value = 0;
    public Semaphore(int initialValue){
        if ( initialValue < 0){
            throw new IllegalArgumentException("Initial permit value cannot be negative");
        }
        this.value = initialValue;
    }
    public synchronized void acquire() throws InterruptedException{
        this.value--;
        if(this.value < 0){
            wait();
        }
    }
    public synchronized boolean tryAcquire(long timeoutMillis) throws InterruptedException{
        long startTime = System.currentTimeMillis();
        long timeRemaining = timeoutMillis;
        while(this.value <= 0){
            if (timeRemaining <= 0){
                return false;
            }
            wait(timeRemaining);

            long elapsed = System.currentTimeMillis() - startTime;
            timeRemaining = timeoutMillis - elapsed;
        }
        this.value--;
        return true;
    }

    public synchronized void release(){
        this.value++;
        if (this.value <= 0){
            notify();
        }
    }

    public synchronized int availablePermits(){
        return this.value;
    }
}
