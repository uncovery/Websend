package com.github.websend;

import java.net.InetAddress;
import java.net.URL;

public class Settings {

    private String responseURL;
    private String password;
    private String salt = "";
    private String algorithm = "SHA-512";
    private int port = 4445;
    private boolean debugMode = false;
    private boolean gzipRequests = false;
    private boolean serverActive = false;
    private URL URL;
    private InetAddress serverBindIP = null;
    private boolean wrapCommandExecutor;
    private boolean sslEnabled = false;
    private String sslPassword = null;
    private boolean extendedPlayerDataEnabled;

    public URL getURL() {
        return URL;
    }

    public void setURL( URL URL ) {
        this.URL = URL;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode( boolean debugMode ) {
        this.debugMode = debugMode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort( int port ) {
        this.port = port;
    }

    public String getResponseURL() {
        return responseURL;
    }

    public void setResponseURL( String responseURL ) {
        this.responseURL = responseURL;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt( String salt ) {
        this.salt = salt;
    }

    public String getHashingAlgorithm() {
        return this.algorithm;
    }

    public void setHashingAlgorithm( String algorithm ) {
        this.algorithm = algorithm;
    }

    public boolean isServerActive() {
        return serverActive;
    }

    public void setServerActive( boolean serverActive ) {
        this.serverActive = serverActive;
    }

    public InetAddress getServerBindIP() {
        return serverBindIP;
    }

    public void setServerBindIP( InetAddress ip ) {
        this.serverBindIP = ip;
    }

    public boolean areRequestsGZipped() {
        return gzipRequests;
    }

    public void setGzipRequests( boolean gzipRequests ) {
        this.gzipRequests = gzipRequests;
    }

    public boolean areCommandExecutorsWrapped() {
        return wrapCommandExecutor;
    }

    public void setWrapCommandExecutor( boolean b ) {
        this.wrapCommandExecutor = b;
    }

    public boolean isSSLEnabled() {
        return sslEnabled;
    }

    public void setSSLEnabled( boolean sslEnabled ) {
        this.sslEnabled = sslEnabled;
    }

    public boolean isExtendedPlayerDataEnabled() {
        return extendedPlayerDataEnabled;
    }

    public void setExtendedPlayerDataEnabled( boolean enabled ) {
        this.extendedPlayerDataEnabled = enabled;
    }

    public String getSSLPassword() {
        return this.sslPassword;
    }

    public void setSslPassword( String sslPassword ) {
        this.sslPassword = sslPassword;
    }
}
