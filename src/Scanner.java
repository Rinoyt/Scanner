import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public class Scanner implements Closeable {
    private final BufferedReader in;
    private Boolean closed = false;

    private String curLine = null;
    private String curWord = null;
    private Integer curInt = null;

    private int pos = 0;

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

    @Override
    public void close() throws IOException {
        closed = true;
        in.close();
    }

    public boolean hasNextLine() throws IOException {
        return parseLine();
    }

    public String nextLine() throws IOException {
        if (!hasNextLine()) {
            throw new NoSuchElementException("EOF reached");
        }
        String tmp = curLine;
        curLine = null;
        return tmp;
    }

    public boolean hasNextWord() throws IOException {
        return parseWord();
    }

    public String nextWord() throws IOException {
        if (!hasNextWord()) {
            throw new NoSuchElementException("No words were found");
        }
        String tmp = curWord;
        curWord = null;
        return tmp;
    }

    public boolean hasNextInt() throws IOException {
        return parseInt();
    }

    public int nextInt() throws IOException {
        if (!hasNextInt()) {
            throw new NoSuchElementException("No numbers were found");
        }
        int tmp = curInt;
        curInt = null;
        return tmp;
    }

    public int read() throws IOException {
        return getChar();
    }

    private void checkScannerState() {
        if (closed) {
            throw new IllegalStateException("Operation on closed Scanner");
        }
    }

    private void skipSpace() throws IOException {
        int chr = getChar();
        while (chr != -1 && (Character.isWhitespace(chr))) {
            chr = getChar();
        }
        if (chr != -1) {
            pos--;
        }
    }

    private boolean parseLine() throws IOException {
        if (curLine != null) {
            return true;
        }

        curLine = in.readLine();
        return curLine != null;
    }

    private int getChar() throws IOException {
        if (curLine == null || pos > curLine.length()) {
            curLine = null;
            pos = 0;
            return parseLine() ? getChar() : -1;
        }else if (pos == curLine.length()) {
            pos++;
            return '\n';
        } else {
            return curLine.charAt(pos++);
        }
    }

    private void returnChar() {
        if (pos > 0) {
            pos--;
        }
    }

    private boolean parseWord() throws IOException {
        if (curWord != null) {
            return true;
        }

        skipSpace();
        int startPos = pos;

        int chr = getChar();
        StringBuilder word = new StringBuilder();
        while (chr != -1 && !Character.isWhitespace(chr)) {
            word.append((char) chr);
            chr = getChar();
        }
        returnChar(); // current parser takes 1 extra char

        String res = word.toString();
        if (!res.isEmpty()) {
            curWord = res;
            return true;
        } else {
            pos = startPos;
            return false;
        }
    }

    private boolean parseInt() throws IOException {
        if (curInt != null) {
            return true;
        }

        skipSpace();
        int startPos = pos;

        int chr = getChar();

        // check for negative numbers
        int mult = 1;
        if ((char) chr == '-') {
            mult = -1;
            chr = getChar();
        }

        StringBuilder number = new StringBuilder();
        while (chr != -1 && Character.isDigit(chr)) {
            number.append(Character.toString(chr));
            chr = getChar();
        }
        returnChar(); // current parser takes 1 extra char

        String res = number.toString();
        if (!res.isEmpty()) {
            curInt = mult * Integer.parseInt(res);
            return true;
        } else {
            pos = startPos;
            return false;
        }
    }
}
