import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import java.text.DecimalFormat;

import java.awt.*;

public class Contraste_Mesure implements PlugInFilter {
    DecimalFormat df = new DecimalFormat("0.000"); // Je prend un format de float à 3 chiffres derrière la virgule

    public int setup(String arg, ImagePlus imp) {
        return DOES_8G+NO_UNDO+NO_CHANGES; // On travaille sur des images 8 bits teintes de gris
    }

    public void run(ImageProcessor ip) {
        float sum = 0; // Somme
        float mean = 0; // Moyenne
        double ctrst = 0; // Contraste
        Rectangle r = ip.getRoi();

        // Calcul de la moyenne
        for (int y = r.y; y < (r.y+r.height); y++)
            for (int x = r.x; x < (r.x+r.width); x++)
                 sum += ip.getPixel(x, y);
        mean = sum / (r.height * r.width);
        IJ.log("Moyenne : "+df.format(mean));

        // Calcul du contraste
        for (int j = 0; j < r.height; j++)
            for (int i = 0; i < r.width; i++)
                 ctrst += Math.pow(ip.getPixel(i, j) - mean, 2);
        ctrst = ctrst / (r.width*r.height);
        ctrst = Math.sqrt(ctrst);
        IJ.log("Contraste mesure : "+df.format(ctrst));
    }

}
