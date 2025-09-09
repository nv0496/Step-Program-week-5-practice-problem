import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SmartDevice {
    // --- Read-only properties ---
    private final String deviceId;
    private final LocalDateTime manufacturingDate;
    private final String serialNumber;

    // --- Write-only properties ---
    private int hashedEncryptionKey;
    private int hashedAdminPassword;

    // --- Read-write properties ---
    private String deviceName;
    private boolean isEnabled;

    // --- Internal private state ---
    private final LocalDateTime startupTime;

    // --- Constructor ---
    public SmartDevice(String deviceName) {
        this.deviceId = UUID.randomUUID().toString();
        this.manufacturingDate = LocalDateTime.now().minusYears(2); // Example: built 2 years ago
        this.serialNumber = "SN-" + UUID.randomUUID().toString().substring(0, 8);
        this.startupTime = LocalDateTime.now();
        this.deviceName = deviceName;
        this.isEnabled = true;
    }

    // --- Read-only methods ---
    public String getDeviceId() { return deviceId; }
    public LocalDateTime getManufacturingDate() { return manufacturingDate; }
    public String getSerialNumber() { return serialNumber; }

    public long getUptime() {
        return Duration.between(startupTime, LocalDateTime.now()).toSeconds();
    }

    public int getDeviceAge() {
        return LocalDateTime.now().getYear() - manufacturingDate.getYear();
    }

    // --- Write-only methods ---
    public void setEncryptionKey(String key) {
        if (key == null || key.length() < 8) {
            throw new IllegalArgumentException("Encryption key must be at least 8 characters.");
        }
        this.hashedEncryptionKey = key.hashCode();
    }

    public void setAdminPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        this.hashedAdminPassword = password.hashCode();
    }

    public boolean validateEncryptionKey(String key) {
        return key.hashCode() == hashedEncryptionKey;
    }

    public boolean validateAdminPassword(String password) {
        return password.hashCode() == hashedAdminPassword;
    }

    // --- Read-write methods ---
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public boolean isEnabled() { return isEnabled; }
    public void setEnabled(boolean enabled) { this.isEnabled = enabled; }

    // --- Utility methods ---
    public Map<String, String> getPropertyInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("deviceId", "Read-Only");
        info.put("manufacturingDate", "Read-Only");
        info.put("serialNumber", "Read-Only");
        info.put("uptime", "Computed Read-Only");
        info.put("deviceAge", "Computed Read-Only");
        info.put("encryptionKey", "Write-Only");
        info.put("adminPassword", "Write-Only");
        info.put("deviceName", "Read-Write");
        info.put("isEnabled", "Read-Write");
        return info;
    }

    public void resetDevice() {
        hashedEncryptionKey = 0;
        hashedAdminPassword = 0;
        isEnabled = false;
        System.out.println("Device reset: write-only properties cleared.");
    }

    // --- Main for demo ---
    public static void main(String[] args) {
        SmartDevice device = new SmartDevice("Smart Speaker");

        // --- Read-only properties ---
        System.out.println("Device ID: " + device.getDeviceId());
        System.out.println("Manufactured on: " + device.getManufacturingDate());
        System.out.println("Serial Number: " + device.getSerialNumber());
        System.out.println("Device Age: " + device.getDeviceAge() + " years");
        System.out.println("Uptime: " + device.getUptime() + " seconds");

        // --- Write-only properties ---
        device.setEncryptionKey("SuperSecureKey123");
        device.setAdminPassword("Admin@123");
        System.out.println("Encryption Key validation: " + device.validateEncryptionKey("SuperSecureKey123"));
        System.out.println("Admin Password validation: " + device.validateAdminPassword("Admin@123"));

        // --- Read-write properties ---
        System.out.println("Device Name: " + device.getDeviceName());
        device.setDeviceName("Smart Home Hub");
        device.setEnabled(false);
        System.out.println("Updated Device Name: " + device.getDeviceName());
        System.out.println("Enabled: " + device.isEnabled());

        // --- Utility ---
        System.out.println("\nProperty Info:");
        device.getPropertyInfo().forEach((k, v) -> System.out.println(k + " → " + v));

        // Reset device
        device.resetDevice();

        // Multiple devices
        SmartDevice device2 = new SmartDevice("Smart Camera");
        System.out.println("\nAnother device:");
        System.out.println("Device2 Name: " + device2.getDeviceName());
        System.out.println("Device2 Serial: " + device2.getSerialNumber());
    }
}
