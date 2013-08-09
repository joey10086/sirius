package de.unijena.bioinf.fteval;

import java.io.File;
import java.io.FilenameFilter;

public class EvalDB {

    final File root;

    public EvalDB(File root) {
        this.root = root;
        checkPath();
    }

    File profile(String name) {
        return new File(new File(root, "profiles"), name);
    }

    File profile(File name) {
        return new File(new File(root, "profiles"), removeExtName(name));
    }

    File[] msFiles() {
        return new File(root, "ms").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".ms");
            }
        });
    }

    String removeExtName(File name) {
        return name.getName().substring(0, name.getName().lastIndexOf('.'));
    }

    private void checkPath() {
        if (!root.exists() || !(new File(root, "profiles").exists()))
            throw new RuntimeException("Path '" + root.getAbsolutePath() + "' is no valid dataset path. Create a new" +
                    " evaluation dataset with\nfteval init <name>");
    }


}
