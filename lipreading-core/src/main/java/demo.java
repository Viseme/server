import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import com.googlecode.javacv.CanvasFrame;
 
public class demo {
  
    public static void main(String[] args) {
         
     //Load image img1 as IplImage
        final IplImage image = cvLoadImage("img1.png");
         
        //create canvas frame named 'Demo'
        final CanvasFrame canvas = new CanvasFrame("Demo");
         
        //Show image in canvas frame
        canvas.showImage(image);
         
        //This will close canvas frame on exit
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);        
    }    
}