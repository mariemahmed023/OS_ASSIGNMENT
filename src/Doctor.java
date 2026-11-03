
import java.util.concurrent.ThreadLocalRandom;

public class Doctor extends Thread {

    private int doctorId;
    private SharedResources.WaitingRoom waitingRoom;
    private SharedResources.TreatmentRoomManager treatmentRooms;

    public Doctor(int doctorId, SharedResources.WaitingRoom waitingRoom,
                  SharedResources.TreatmentRoomManager treatmentRooms) {
        this.doctorId = doctorId;
        this.waitingRoom = waitingRoom;
        this.treatmentRooms = treatmentRooms;
    }

    @Override
    public void run() {

        while (true) {
            try {
                Patient patient = waitingRoom.takePatient();

                System.out.println("Doctor " + doctorId
                        + " is assigned to Patient " + patient.getPatientId() + ".");

                System.out.println("Doctor " + doctorId
                        + " is trying to acquire a treatment room.");

                if (treatmentRooms.tryAcquireRoom()) {

                    System.out.println("Doctor " + doctorId
                            + " acquired a treatment room.");

                    System.out.println("Doctor " + doctorId
                            + " begins treating Patient "
                            + patient.getPatientId() + ".");

                    int time = ThreadLocalRandom.current()
                            .nextInt(1000, 5001);

                    Thread.sleep(time);

                    System.out.println("Doctor " + doctorId
                            + " finished treating Patient "
                            + patient.getPatientId() + ".");

                    treatmentRooms.releaseRoom();

                    System.out.println("Doctor " + doctorId
                            + " released the treatment room.");

                    patient.finishTreatment();

                    System.out.println("Doctor " + doctorId
                            + " is now available.");

                } else {

                    System.out.println("Doctor " + doctorId
                            + " could not acquire a treatment room.");

                    System.out.println("Doctor " + doctorId
                            + " is sending Patient "
                            + patient.getPatientId()
                            + " back to the waiting room.");

                    waitingRoom.returnPatient(patient);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
