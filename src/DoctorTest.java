
public class DoctorTest {

    public static void main(String[] args) throws InterruptedException {

        SharedResources.WaitingRoom waitingRoom =
                new SharedResources.WaitingRoom(3);

        SharedResources.TreatmentRoomManager treatmentRooms =
                new SharedResources.TreatmentRoomManager(2);

        Doctor doctor = new Doctor(1, waitingRoom, treatmentRooms);

        Patient patient1 = new Patient(1, waitingRoom);
        Patient patient2 = new Patient(2, waitingRoom);

        doctor.start();

        patient1.start();
        patient2.start();

        patient1.join();
        patient2.join();

        doctor.interrupt();
        doctor.join();

        System.out.println("TEST COMPLETED.");
    }
}
