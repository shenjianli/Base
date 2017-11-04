package com.shen.base.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by jerry shen on 2016/12/21.
 * 网络状态工具类
 */
public class NetUtils {

	/**
	 * 返回网络类型
	 */
	public static final int NETWORK_NONE = 0;
	public static final int NETWORK_WIFI = 1;
	public static final int NETWORK_MOBILE = 2;


	/**
	 * 防止静态类进行实例化
	 */
	private NetUtils(){

	}

	/**
	 * 检查是否连接网络
	 * @param context 上下文
	 * @return true:连接  false：没有网络连接
     */
	public static boolean checkNet(Context context) {
		boolean isMobile = checkConnection(context,ConnectivityManager.TYPE_MOBILE);
		boolean isWifi = checkConnection(context,ConnectivityManager.TYPE_WIFI);
		if (!isMobile && !isWifi) {
			return false;
		}
		return true;
	}

	/**
	 * 根据网络类型判断连接状态
	 * @param context 上下文
	 * @param netType 网络类型
     * @return 返回连接状态
     */
	private static boolean checkConnection(Context context,int netType) {
		ConnectivityManager cm = 
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getNetworkInfo(netType);
		boolean isConnected = networkInfo.isConnected();

		return isConnected;
	}


	/**
	 * 获取网络状态，表是wifi 还是移动
	 * @param context
	 * @return
     */
    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // Wifi
        State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if (state == State.CONNECTED || state == State.CONNECTING) {
            return NETWORK_WIFI;
        }

        // 3G
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        if (state == State.CONNECTED || state == State.CONNECTING) {
            return NETWORK_MOBILE;
        }
        return NETWORK_NONE;
    }

	/**
	 * 获取当前手机连接wifi的硬件详细信息
	 * @param context
	 * @return
     */
	public static String getWifiDeviceInfo(Context context){
		StringBuilder wifiDeviceId = new StringBuilder();
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		wifiDeviceId.append("SSID=");
		wifiDeviceId.append(wifiInfo.getSSID());
		wifiDeviceId.append("，MacAddress=");
		wifiDeviceId.append(wifiInfo.getMacAddress());
		wifiDeviceId.append("，HiddenSSID=");
		wifiDeviceId.append(wifiInfo.getHiddenSSID());
		wifiDeviceId.append("，NetworkId=");
		wifiDeviceId.append(wifiInfo.getNetworkId());
		wifiDeviceId.append("，Rssi=");
		wifiDeviceId.append(wifiInfo.getRssi());
		wifiDeviceId.append("，BSSID=");
		wifiDeviceId.append(wifiInfo.getBSSID());
		return wifiDeviceId.toString();
	}

}
