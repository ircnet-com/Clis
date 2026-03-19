package com.ircnet.service.clis;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

@Component
public class GitRevision {
    private final Properties props = new Properties();

    public GitRevision() {
        try(InputStream in = new ClassPathResource("git.properties").getInputStream()) {
            props.load(in);
        }
        catch(Exception ignored) {
        }
    }

    private String get(String key) {
        String value = props.getProperty(key);
        return (value == null || value.isBlank()) ? null : value.trim();
    }

    public String hash() {
        String hash = get("git.commit.id.abbrev");
        return (hash == null) ? "unknown" : hash;
    }

    public String commitDate() {
        String time = get("git.commit.time");
        return (time == null) ? "unknown-time" : time;
    }

    public boolean dirty() {
        return Boolean.parseBoolean(props.getProperty("git.dirty", "false"));
    }

    public String display() {
        return String.format("%s, %s%s",
            hash(),
            commitDate(),
            dirty() ? ", uncommitted changes" : ""
        );
    }
}
