package others;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDExtractor {
    public static void main(String[] args) {
        String input = "systime=1682575098 freq=2480 addr=8e89bed6 delta_t=1.006 ms rssi=-49\n" +
                "46 22 b0 7c 20 18 51 77 03 03 6f fd 17 16 6f fd 31 a1 2f cc fe e8 41 3e a7 f2 d4 8a 23 62 cf 0b c3 62 08 66 45 35 78\n" +
                "Advertising / AA 8e89bed6 (valid)/ 34 bytes\n" +
                "    Channel Index: 39\n" +
                "    Type:  ADV_SCAN_IND\n" +
                "    AdvA:  77:51:18:20:7c:b0 (random)\n" +
                "    AdvData: 03 03 6f fd 17 16 6f fd 31 a1 2f cc fe e8 41 3e a7 f2 d4 8a 23 62 cf 0b c3 62 08 66\n" +
                "        Type 03 (16-bit Service UUIDs) \n" +
                "           fd6f\n" +
                "        Type 16 (Service Data)\n" +
                "           UUID: fd6f, Additional: 31 a1 2f cc fe e8 41 3e a7 f2 d4 8a 23 62 cf 0b c3 62 08 66\n" +
                "    Data:  b0 7c 20 18 51 77 03 03 6f fd 17 16 6f fd 31 a1 2f cc fe e8 41 3e a7 f2 d4 8a 23 62 cf 0b c3 62 08 66\n" +
                "    CRC:   45 35 78";

        Pattern pattern = Pattern.compile("UUID:\\s*([a-f0-9]{4})");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String uuid = matcher.group(1);
            System.out.println(uuid);
        }
    }
}
