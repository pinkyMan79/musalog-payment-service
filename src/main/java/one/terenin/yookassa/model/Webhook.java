package one.terenin.yookassa.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class Webhook {
    UUID id;
    String event;
    String url;
}
