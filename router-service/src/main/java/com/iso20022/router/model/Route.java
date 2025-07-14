package com.iso20022.router.model;

import java.util.Objects;

public class Route {
    private String pattern;
    private String destination;
    private String endpoint;
    private String method;
    private int priority;
    private boolean enabled = true;
    private String description;

    public Route() {
    }

    public Route(String pattern, String destination, String endpoint, String method, 
                int priority, boolean enabled, String description) {
        this.pattern = pattern;
        this.destination = destination;
        this.endpoint = endpoint;
        this.method = method;
        this.priority = priority;
        this.enabled = enabled;
        this.description = description;
    }

    // Getters and Setters
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return priority == route.priority && 
               enabled == route.enabled && 
               Objects.equals(pattern, route.pattern) &&
               Objects.equals(destination, route.destination) &&
               Objects.equals(endpoint, route.endpoint) &&
               Objects.equals(method, route.method) &&
               Objects.equals(description, route.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, destination, endpoint, method, priority, enabled, description);
    }

    @Override
    public String toString() {
        return "Route{" +
               "pattern='" + pattern + '\'' +
               ", destination='" + destination + '\'' +
               ", endpoint='" + endpoint + '\'' +
               ", method='" + method + '\'' +
               ", priority=" + priority +
               ", enabled=" + enabled +
               ", description='" + description + '\'' +
               '}';
    }

    // Builder pattern implementation
    public static RouteBuilder builder() {
        return new RouteBuilder();
    }

    public static class RouteBuilder {
        private String pattern;
        private String destination;
        private String endpoint;
        private String method;
        private int priority;
        private boolean enabled = true;
        private String description;

        public RouteBuilder pattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public RouteBuilder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public RouteBuilder endpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public RouteBuilder method(String method) {
            this.method = method;
            return this;
        }

        public RouteBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public RouteBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public RouteBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Route build() {
            return new Route(pattern, destination, endpoint, method, priority, enabled, description);
        }
    }
}
