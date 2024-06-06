package proyecto.socket;

public class HiloClienteParlante extends Thread {
    private static boolean client0finished = false;
    private final int id;

    public HiloClienteParlante(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        synchronized (HiloClienteParlante.class) {
            while (id == 1 && !client0finished) {
                try {
                    HiloClienteParlante.class.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            new ClientGUI(id);

            if (id == 0) {
                client0finished = true;
                HiloClienteParlante.class.notifyAll();
            }
        }
    }
}
