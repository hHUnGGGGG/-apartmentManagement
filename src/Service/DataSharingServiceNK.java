package Service;

public class DataSharingServiceNK {
    private static final DataSharingServiceNK instance = new DataSharingServiceNK();
    private Runnable onDataChanged;

    private DataSharingServiceNK() {}

    public static DataSharingServiceNK getInstance() {
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
