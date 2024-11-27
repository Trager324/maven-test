package constant;

import java.nio.file.Files;
import java.nio.file.Path;

public final class Constants {
    public static final Path PATH_ANTLR;

    static {
        var ws = Path.of(".");
        var rela = Path.of("tpantlr2/src/main/antlr4");
        while (Files.notExists(ws.resolve(rela))) {
            int nameCount = rela.getNameCount();
            if (nameCount == 0) {
                throw new AssertionError("Invalid working directory: " +
                                         ws.toAbsolutePath());
            }
            rela = rela.subpath(1, nameCount);
        }
        PATH_ANTLR = ws.resolve(rela);
    }
}
