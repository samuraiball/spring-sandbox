package dev.hirooka.rsocket.conifg;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sample-app")
public class Properties {

    private final SampleApp sampleApp = new SampleApp();

    public static class SampleApp {
        public SampleApp() {
        }

        public SampleApp(String hostname, int port) {
            this.hostname = hostname;
            this.port = port;
        }

        private String hostname = "localhost";
        private int port = 8182;

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
