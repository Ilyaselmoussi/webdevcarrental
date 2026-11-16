package ma.uir.awd.ws.converter;

/** Lightweight request validation without an additional Maven dependency. */
public final class RequestValidation {
    private RequestValidation() {}

    public static void positiveId(Long value, String field) {
        if (value == null || value <= 0) throw new IllegalArgumentException(field + " doit etre positif");
    }

    public static void text(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " est obligatoire");
    }

    public static void nonNegative(double value, String field) {
        if (!Double.isFinite(value) || value < 0) throw new IllegalArgumentException(field + " doit etre un nombre positif ou nul");
    }

    public static void fuel(double value, String field) {
        if (!Double.isFinite(value) || value < 0 || value > 100) throw new IllegalArgumentException(field + " doit etre entre 0 et 100");
    }

    public static void present(Object value, String field) {
        if (value == null) throw new IllegalArgumentException(field + " est obligatoire");
    }
}
