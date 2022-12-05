package vglfront.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Utility class for creating environment files, similarly to Java with environment variables.
 *
 * In a dockerized or automated environment, this class may be of increased interest if a JavaScript application must
 * have access to environment variables when launching the application, which could be loaded from the environment file.
 *
 * Example : java FileEnvironmentConfigurator.java src/assets/environment.js %JSKEY% %JSVALUE% "window['environment']['%JSKEY%'] = %JSVALUE%;" testOne 0 testTwo 1
 */
public class FileEnvironmentConfigurator {

    /**
     * Configuration file.
     */
    private File file;

    /**
     * Key placeholder. This placeholder will be used to reference the current key in the replacement template.
     */
    private String keyPlaceholder;

    /**
     * Value placeholder. This placeholder will be used to reference the current value in the replacement template.
     */
    private String valuePlaceholder;

    /**
     * Content of the created environment file.
     */
    private String content;

    /**
     * Environment template.
     */
    private String environmentTemplate;

    /**
     * Constructor of FileEnvironmentConfigurator
     *
     * @param f File to load for the environment configuration
     * @param k Key placeholder
     * @param v Value placeholder
     * @param et Environment template
     */
    public FileEnvironmentConfigurator(File f, String k, String v, String et) {
        boolean error = false;

        try {
            this.file = Objects.requireNonNull(f);
            this.keyPlaceholder = k;
            this.valuePlaceholder = v;
            this.environmentTemplate = et;
            this.content = "";

            if (Objects.requireNonNull(k).length() == 0) {
                throw new Exception("The key placeholder can't be empty");
            }

            if (Objects.requireNonNull(v).length() == 0) {
                throw new Exception("The value placeholder can't be empty");
            }

            if (Objects.requireNonNull(et).length() == 0) {
                throw new Exception("The environment template can't be empty");
            }

            if (this.file.exists()) {
                if (this.file.isDirectory()) {
                    throw new Exception("File " + this.file.getAbsolutePath() + " is a directory");
                } else if (!this.file.canRead()) {
                    throw new Exception("Can't read " + this.file.getAbsolutePath());
                } else if (!this.file.canWrite()) {
                    throw new Exception("Can't write to " + this.file.getAbsolutePath());
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Can't pass null objects");
            error = true;
        } catch (IOException e) {
            System.err.println("Can't read file " + this.file.getAbsolutePath());
            error = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            error = true;
        }

        if (error) System.exit(0);
    }

    /**
     * Define an environment variable in the environment file
     *
     * @param key Current processed key
     * @param value Current processed value
     */
    public void setEnvironmentVariable(String key, String value) {
        if (this.content.length() != 0) {
            this.content += System.lineSeparator();
        }

        this.content += this.environmentTemplate.replace(this.keyPlaceholder, key).replace(this.valuePlaceholder, value);
    }

    /**
     * Save the created environment file.
     *
     * @return true if success, false otherwise
     */
    public boolean save() {
        FileOutputStream fo;
        try {
            fo = new FileOutputStream(this.file);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }

        try {
            fo.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Can't write the result to " + this.file.getAbsolutePath());
            return false;
        }

        try {
            fo.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("Usage : java FileEnvironmentConfigurator.java <Path to the environment file> <Key placeholder> <Value placeholder> <Environment template>");
            System.exit(1);
        }

        FileEnvironmentConfigurator ec = new FileEnvironmentConfigurator(new File(args[0]), args[1], args[2], args[3]);

        for (int i = 4; i < args.length; i += 2) {
            if (i+1 < args.length) {
                ec.setEnvironmentVariable(args[i], args[i+1]);
            }
        }

        ec.save();
    }
}