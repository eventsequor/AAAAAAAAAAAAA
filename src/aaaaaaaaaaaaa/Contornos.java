/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaaaaaaaaaaaa;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Eder Leandro Carbonero
 */
public class Contornos {
    public Contornos(){
        
    }
    public void detectar(){
        Mat imgOrig= Imgcodecs.imread("d4.png");
        Mat gris=new Mat();
        Mat gausian=new Mat();
        Mat erocion=new Mat();
        Mat threshold=new Mat();
        Imgproc.cvtColor(imgOrig, gris, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(gris, gausian,new Size(5,5), 0);
        Imgproc.GaussianBlur(gausian, gausian,new Size(5,5), 3); 
        Imgproc.threshold(gausian,threshold,0,255,Imgproc.THRESH_BINARY | Imgproc.THRESH_TRIANGLE);
        
        
        
        Mat kernel1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Mat dilatar=new Mat();
        Imgproc.dilate(threshold,dilatar, kernel1);
        Imgproc.dilate(dilatar,dilatar, kernel1);
        Mat kernel2 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3, 3));
        Imgproc.erode(dilatar,erocion,kernel2);
        for(int i=1;i<4;i++){
          Imgproc.erode(erocion,erocion,kernel2);
        }
        Mat canny=new Mat();
        Imgproc.Canny(erocion,canny,50, 150,3,false);
        
        List<MatOfPoint> contours = new ArrayList<>();
       Imgproc.findContours(canny,contours,new Mat(),Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
       int numCon=contours.size();
       int bordesPuntos=0;
       int bordesCuadrados=0;
       for(int j=0;j<numCon;j++){
           MatOfPoint lista=contours.get(j);
           Size y=lista.size();
           double x=y.area();  
           System.out.println(x);
           if( x>10 && x< 50){
               
               bordesPuntos++;
           }
           if (x>120){
               bordesCuadrados++;
           }          
       }
        System.out.println(bordesPuntos);
        System.out.println(bordesCuadrados);
        promediDados(bordesPuntos,bordesCuadrados);
        
        System.out.println(numCon+"  Numero de figuras");
        Imgcodecs.imwrite("imgPrueb.png",canny);
        HighGui.imshow("imagen",canny);
        HighGui.waitKey();
    }
    public void promediDados(int puntos,int dados){
        puntos=puntos/2;
        dados=dados/2;
        int respuesta=puntos/dados;
        System.out.println("El promedio de los dados es");
        System.out.println(respuesta);
    }
    
}
