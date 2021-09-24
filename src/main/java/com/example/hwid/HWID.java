package com.example.hwid;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Mod(modid = "hwid", name = "HWID", version = "0.0.1")
public class HWID {
    private final Minecraft mc = Minecraft.getMinecraft();

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String hwidAuth = (bytesToHex(generateHWID()));
    String[] AllowedHWID = {"HWID1", "HWID2"};

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(Objects.equals(hwidAuth, AllowedHWID[0]) || Objects.equals(hwidAuth, AllowedHWID[1])) {
            return;
        }
        mc.shutdown();
    }

    public static byte[] generateHWID() {
        try {
            MessageDigest hash = MessageDigest.getInstance("MD5");
            StringBuilder s = new StringBuilder();
            s.append(System.getProperty("os.name"));
            s.append(System.getProperty("os.arch"));
            s.append(System.getProperty("os.version"));
            s.append(Runtime.getRuntime().availableProcessors());
            s.append(System.getenv("PROCESSOR_IDENTIFIER"));
            s.append(System.getenv("PROCESSOR_ARCHITECTURE"));
            s.append(System.getenv("PROCESSOR_ARCHITEW6432"));
            s.append(System.getenv("NUMBER_OF_PROCESSORS"));
            return hash.digest(s.toString().getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new Error("Algorithm wasn't found.", e);
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0xF];
        }
        return new String(hexChars);
    }
}

