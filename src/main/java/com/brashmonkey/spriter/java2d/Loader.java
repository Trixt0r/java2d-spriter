package com.brashmonkey.spriter.java2d;

import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.FileReference;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Trixt0r on 01.12.2016.
 */
public class Loader extends com.brashmonkey.spriter.Loader<BufferedImage> {

    public Loader(Data data) {
        super(data);
    }


    @Override
    protected BufferedImage loadResource(FileReference ref) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(super.root + "/" + data.getFile(ref).name));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return img;
        }
    }
}
