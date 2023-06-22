package one.terenin.security.constants;

import io.grpc.Context;
import io.grpc.Metadata;

public class KeyConstant {
    public final static String TOKEN_PREFIX = "Bearer ";
    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization",
            Metadata.ASCII_STRING_MARSHALLER);
    // get it from user-entity or user response
    public static final Context.Key<String> CLIENT_ID_CONTEXT_KEY = Context.key("clientId");
}
