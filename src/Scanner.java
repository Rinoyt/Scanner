import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class Scanner {
    private final BufferedReader in;

    public Scanner(String string) {
        in = new BufferedReader(new StringReader(string));
    }

    public Scanner(InputStream inputStream) {
        in = new BufferedReader(new InputStreamReader(inputStream));
    }

    public Scanner(InputStream inputStream, String charsetName) throws IllegalArgumentException {
        try {
            in = new BufferedReader(new InputStreamReader(inputStream, charsetName));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Scanner construct received unsupported charset name", e);
        }
    }

    public Scanner(InputStream inputStream, Charset charset) {
        in = new BufferedReader(new InputStreamReader(inputStream, charset));
    }

    public Scanner(File file) throws FileNotFoundException {
        in = new BufferedReader(new FileReader(file));
    }

    public Scanner(File file, String charsetName) throws FileNotFoundException {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Scanner construct received unsupported charset name", e);
        }
    }

    public Scanner(File file, Charset charset) throws FileNotFoundException {
        in = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    public Scanner(Path path) throws FileNotFoundException {
        this(path.toFile());
    }

    public Scanner(Path path, String charsetName) throws FileNotFoundException {
        this(path.toFile(), charsetName);
    }
    
    public Scanner(Path path, Charset charset) throws FileNotFoundException {
        this(path.toFile(), charset);
    }
}
