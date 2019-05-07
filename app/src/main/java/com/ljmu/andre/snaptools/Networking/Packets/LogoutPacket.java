package com.ljmu.andre.snaptools.Networking.Packets;

import com.google.gson.annotations.SerializedName;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class LogoutPacket extends AuthResultPacket {
    @SerializedName("logout_state")
    public boolean logoutState;
}
