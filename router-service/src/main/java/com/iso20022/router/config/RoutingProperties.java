package com.iso20022.router.config;

import com.iso20022.router.model.Route;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

/**
 * Configuration properties for routing settings.
 */
@Configuration
@ConfigurationProperties(prefix = "routing")
public class RoutingProperties {
    private List<Route> rules;
    private SettlementConfig settlement = new SettlementConfig();
    
    public List<Route> getRules() {
        return rules;
    }

    public void setRules(List<Route> rules) {
        this.rules = rules;
    }
    
    public SettlementConfig getSettlement() {
        return settlement;
    }

    public void setSettlement(SettlementConfig settlement) {
        this.settlement = settlement;
    }
    
    /**
     * Configuration for settlement service settings.
     */
    public static class SettlementConfig {
        private String baseUrl;
        private Endpoints endpoints = new Endpoints();
        
        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public Endpoints getEndpoints() {
            return endpoints;
        }

        public void setEndpoints(Endpoints endpoints) {
            this.endpoints = endpoints;
        }

        /**
         * Configuration for settlement service endpoints.
         */
        public static class Endpoints {
            private String processPayment;
            private String getStatus;
            
            public String getProcessPayment() {
                return processPayment;
            }
            
            public void setProcessPayment(String processPayment) {
                this.processPayment = processPayment;
            }
            
            public String getGetStatus() {
                return getStatus;
            }
            
            public void setGetStatus(String getStatus) {
                this.getStatus = getStatus;
            }
            
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Endpoints endpoints = (Endpoints) o;
                return Objects.equals(processPayment, endpoints.processPayment) &&
                       Objects.equals(getStatus, endpoints.getStatus);
            }
            
            @Override
            public int hashCode() {
                return Objects.hash(processPayment, getStatus);
            }
            
            @Override
            public String toString() {
                return "Endpoints{" +
                       "processPayment='" + processPayment + '\'' +
                       ", getStatus='" + getStatus + '\'' +
                       '}';
            }
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SettlementConfig that = (SettlementConfig) o;
            return Objects.equals(baseUrl, that.baseUrl) &&
                   Objects.equals(endpoints, that.endpoints);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(baseUrl, endpoints);
        }
        
        @Override
        public String toString() {
            return "SettlementConfig{" +
                   "baseUrl='" + baseUrl + '\'' +
                   ", endpoints=" + endpoints +
                   '}';
        }
    }
}
