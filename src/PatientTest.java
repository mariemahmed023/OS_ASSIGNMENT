public class PatientTest {
    public static void main(String[] args) throws InterruptedException {

        SharedResources.WaitingRoom waitingRoom =
                new SharedResources.WaitingRoom(1);

        Patient patient1 = new Patient(1, waitingRoom);
        Patient patient2 = new Patient(2, waitingRoom);

        patient1.start();
        Thread.sleep(200);

        patient2.start();
        Thread.sleep(500);

        System.out.println("TEST: Doctor is taking patient 1.");

        Patient first = waitingRoom.takePatient();
        first.finishTreatment();

        Thread.sleep(500);

        System.out.println("TEST: Doctor is taking patient 2.");

        Patient second = waitingRoom.takePatient();
        second.finishTreatment();

        patient1.join();
        patient2.join();

        System.out.println("TEST COMPLETED.");
    }
}