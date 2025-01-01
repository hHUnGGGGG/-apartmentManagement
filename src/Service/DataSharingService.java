package Service;

public class DataSharingService {
    private static final DataSharingService instance = new DataSharingService();
    private Runnable onDataChanged;

    private DataSharingService() {}

    public static DataSharingService getInstance() {
        return instance;
    }

    public void setOnDataChanged(Runnable onDataChanged) {
        this.onDataChanged = onDataChanged;
    }

    public void notifyDataChanged() {
        if (onDataChanged != null) {
            onDataChanged.run();
        }
    }
}
