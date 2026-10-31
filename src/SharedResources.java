import java.util.LinkedList;
import java.util.Queue;

public class SharedResources {
    public static class WaitingRoom {
        private int capacity;
        private Queue<Patient> patients;
        private Semaphore emptySeats; 
        private Semaphore patientsCount; 

        public WaitingRoom(int capacity) {
            this.capacity = capacity;
            this.patients = new LinkedList<>();
            this.emptySeats = new Semaphore(capacity); 
            this.patientsCount = new Semaphore(0); 
        }

        public void enter(Patient p) throws InterruptedException {
            emptySeats.acquire(); 
            
            synchronized (patients) {
                patients.add(p);
            }
            
            patientsCount.release(); 
        }

        public Patient takePatient() throws InterruptedException {
            patientsCount.acquire(); 
            
            Patient p;
            synchronized (patients) {
                p = patients.poll();
            }
            
            emptySeats.release(); 
            return p;
        }

        public void returnPatient(Patient p) throws InterruptedException {
            emptySeats.acquire(); 
            
            synchronized (patients) {
                ((LinkedList<Patient>) patients).addFirst(p);
            }
            
            patientsCount.release(); 
        }
    }

    public static class TreatmentRoomManager {
        private Semaphore rooms;

        public TreatmentRoomManager(int numRooms) {
            this.rooms = new Semaphore(numRooms); 
        }

        public boolean tryAcquireRoom() throws InterruptedException {
            return rooms.tryAcquire(3000); 
        }

        public void releaseRoom() {
            rooms.release();
        }
    }
}