import java.util.Scanner;

public class Hospital {
    private final int WRcapacity;
    private final int doctorsN;
    private final int patientsN;

    public Hospital (int WRcapacity, int doctorsN, int patientsN) {
        this.WRcapacity = WRcapacity;
        this.doctorsN = doctorsN;
        this.patientsN = patientsN;
    }

    public void runHospital() {
        SharedResources.WaitingRoom waitingRoom =
                new SharedResources.WaitingRoom(WRcapacity);

        SharedResources.TreatmentRoomManager treatmentRooms =
                new SharedResources.TreatmentRoomManager(2);

        Doctor[] doctorsArray = new Doctor[doctorsN];
        for (int i = 0; i < doctorsN; i++) {
            doctorsArray[i] = new Doctor(i+1, waitingRoom, treatmentRooms);
            doctorsArray[i].start();

        }

        Patient[] patientsArray = new Patient[patientsN];
        for (int i = 0; i < patientsN; i++) {
            patientsArray[i] = new Patient(i+1, waitingRoom);
            patientsArray[i].start();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        for (Patient p : patientsArray) {
            try {
                p.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (Doctor d : doctorsArray) {
            try {
                d.interrupt();
                d.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("\nAll patients have been treated.\n" +
                "Simulation completed.");

    }

     static void main() {

        Scanner WRc = new Scanner(System.in);
        Scanner NOp = new Scanner(System.in);
        Scanner NOd = new Scanner(System.in);

        System.out.println("Please enter the waiting room capacity: ");
        int WRC = WRc.nextInt();

        System.out.println("Please enter the number of doctors: ");
        int NOD = NOd.nextInt();

        System.out.println("Please enter the number of patients: ");
        int NOP = NOp.nextInt();

        System.out.print("Patients arriving:\n");
        for (int i = 0; i < NOP; i++) {
            System.out.print("P"+ i+1 + " ");
        }
        System.out.print("\n");

        Hospital hospital = new Hospital(WRC, NOD, NOP);
        hospital.runHospital();
    }

}
