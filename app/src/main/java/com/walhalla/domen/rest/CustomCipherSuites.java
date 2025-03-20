package com.walhalla.domen.rest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class CustomCipherSuites {

    public CustomCipherSuites()  {

    }

    /**
     * Returns the VM's default SSL socket factory, using {@code trustManager} for trusted root
     * certificates.
     */
    public static SSLSocketFactory defaultSslSocketFactory(X509TrustManager trustManager)
            throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { trustManager }, null);

        return sslContext.getSocketFactory();
    }

    /** Returns a trust manager that trusts the VM's default certificate authorities. */
    public static X509TrustManager defaultTrustManager() throws GeneralSecurityException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init((KeyStore) null);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    public static String[] javaNames(List<CipherSuite> cipherSuites) {
        String[] result = new String[cipherSuites.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = cipherSuites.get(i).javaName();
        }
        return result;
    }

    /**
     * An SSL socket factory that forwards all calls to a delegate. Override {@link #configureSocket}
     * to customize a created socket before it is returned.
     */
    public static class DelegatingSSLSocketFactory extends SSLSocketFactory {
        protected final SSLSocketFactory delegate;

        public DelegatingSSLSocketFactory(SSLSocketFactory delegate) {
            this.delegate = delegate;
        }

        @Override public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override public Socket createSocket(
                Socket socket, String host, int port, boolean autoClose) throws IOException {
            return configureSocket((SSLSocket) delegate.createSocket(socket, host, port, autoClose));
        }

        @Override public Socket createSocket(String host, int port) throws IOException {
            return configureSocket((SSLSocket) delegate.createSocket(host, port));
        }

        @Override public Socket createSocket(
                String host, int port, InetAddress localHost, int localPort) throws IOException {
            return configureSocket((SSLSocket) delegate.createSocket(host, port, localHost, localPort));
        }

        @Override public Socket createSocket(InetAddress host, int port) throws IOException {
            return configureSocket((SSLSocket) delegate.createSocket(host, port));
        }

        @Override public Socket createSocket(
                InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return configureSocket((SSLSocket) delegate.createSocket(
                    address, port, localAddress, localPort));
        }

        protected SSLSocket configureSocket(SSLSocket socket) throws IOException {
            return socket;
        }
    }

}