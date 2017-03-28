package china.z.util;

import java.util.UUID;

public class UUIDGenerator {
    public UUIDGenerator(){}

    /**
     * 生成一个UUID
     * @return
     */
    public static String getUUID(){
        String tempUUID= UUID.randomUUID().toString();
        String uuid=tempUUID.substring(0,8)+tempUUID.substring(9,13)+tempUUID.substring(14,18)+tempUUID.substring(19,23)+tempUUID.substring(24);
        return uuid;
    }

    /**
     * 生成指定数量的UUID
     * @param number
     * @return
     */
    public static String[] getUUID(int number){
        if (number<1){
            return null;
        }
        String[] uuids=new String[number];
        for (int i = 0; i < number; i++) {
            uuids[i]=getUUID();
        }
        return uuids;
    }
}
