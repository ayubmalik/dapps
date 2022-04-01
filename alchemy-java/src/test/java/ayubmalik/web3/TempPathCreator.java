package ayubmalik.web3;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.nio.file.Files;
import java.nio.file.Path;

class TempPathCreator implements BeforeEachCallback, AfterEachCallback {

    private Path temp;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        temp = Files.createTempFile("alchemy-", ".tmp");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Files.deleteIfExists(temp);
    }

    public Path getPath() {
        return temp;
    }
}
