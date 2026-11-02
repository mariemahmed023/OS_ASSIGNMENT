
import java.util.concurrent.CountDownLatch;

public class Patient extends Thread {

    private final int patientId;
    private final SharedResources.WaitingRoom waitingRoom;
    private final CountDownLatch treatmentFinished = new CountDownLatch(1);

    public Patient(int patientId, SharedResources.WaitingRoom waitingRoom) {
        this.patientId = patientId;
        this.waitingRoom = waitingRoom;
    }

    public int getPatientId() {
        return patientId;
    }

    public void finishTreatment() {
        treatmentFinished.countDown();
    }

    @Override
    public void run() {
        try {
            System.out.println("Patient " + patientId + " arrived.");

            System.out.println("Patient " + patientId
                    + " is trying to enter the waiting room.");
            if (waitingRoom.isFull()) {
            System.out.println("Patient " + patientId
            + " is waiting outside because the waiting room is full.");
        }
            waitingRoom.enter(this);

            System.out.println("Patient " + patientId
                    + " entered the waiting room.");

            treatmentFinished.await();

            System.out.println("Patient " + patientId
                    + " finished treatment and left the hospital.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Patient " + patientId + " was interrupted.");
        }
    }
}
