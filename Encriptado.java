import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

/**
*Encriptador de ficheros de texto sobre otro fichero de texto
*
*@Author : Francisco Javier Valero Moreno
*
*
*/
public class Encriptado{

  /*
  *Separa las palabras del texto de manera en la que se pueda encriptar mas
  *facilmente
  *
  *@var: String text  variable que contiene la cadena de texto que va a ser odificada
  *
  *@return: String con la cadena ya codificada
  */

  public static String encript(String text){

    String [] cadena = parse(text).split(" ");
    String salida = "";

    for(String word : cadena){

      salida += code(word)+" ";

    }

    return salida.trim();

  }
  /*
  *Introduce la segunda fase del encriptado intercambiando las posiciones de los valores
  *
  *@var: String word Palabra que esta siendo mezclada
  *
  *@return: String de la palabra mezclada
  */
  public static String code(String word){

    String par = "", impar = "";
    for(int i = 0; i < word.length(); i++){
      if(i%2==0)
        par += word.charAt(i);
      else
        impar += word.charAt(i);
    }
    return par+impar;
  }

  /*
  *Primera fase del encriptado en la que se transforma cada caracter de una
  * palabra a Hexadecimal
  *
  *@var: String text palabra a transformar al Hexadecimal
  *
  *@return: Devuelve la palabra transformada en Hexadecimal
  *
  */
  public static String parse(String text){

    String salida="";
    for(int i = 0; i < text.length(); i++){

      salida += (text.charAt(i)==' ')? " ":String.format("%04x",(int) text.charAt(i));

    }

    return salida;

  }
  public static void fileEncript(File text, File dest){

    try{
      BufferedWriter bw = new BufferedWriter(new FileWriter(dest));
      BufferedReader br = new BufferedReader(new FileReader(text));
      String currentL = "";
      while((currentL = br.readLine()) != null ){

        bw.write(encript(currentL)+"\n");

      }
      br.close();
      bw.close();
    }catch(IOException e){
      System.out.println("ERROR DE LECTURA/ESCRITURA");
    }
  }
  public static String descript(String text){

    String [] cadena = text.split(" ");
    String salida = "";

    for (String word : cadena ) {

      salida += uncode(word)+" ";

    }

    return salida.trim();
  }

  public static String uncode(String word){

    String salida = "";
    for (int i = 0 ; i < word.length()/2 ; i++ ) {

      salida += word.charAt(i)+""+word.charAt(i+word.length()/2);

    }

    return unparse(salida);

  }

  public static String unparse(String hex){

    String salida = "";
    String caracter= "";
    int aux = 0;

    for (int i = 0; i < hex.length() ; i += 4 ){
      caracter = "0x"+hex.substring(i,i+4);
      aux = Integer.decode(caracter);
      salida += (char) aux+"";
    }

    return salida;

  }
  public static void fileDescript(File text, File dest){

    try{
      BufferedWriter bw = new BufferedWriter(new FileWriter(dest));
      BufferedReader br = new BufferedReader(new FileReader(text));
      String currentL = "";
      while((currentL = br.readLine()) != null ){

        bw.write(descript(currentL)+"\n");

      }
      br.close();
      bw.close();
    }catch(IOException e){
      System.out.println("ERROR DE LECTURA/ESCRITURA");
    }

  }

  public static void main(String[] args) {
    Scanner entrada = new Scanner(System.in), salida = new Scanner(System.in);
    boolean rutina = true;
    File in, out;
    String ent, sal;
    while(rutina){

      System.out.print("Seleccione una opcion a desarrollar:\n-1: Encriptar Fichero\n-2: Desencriptar Fichero\n-3: Salir del programa\n");
      int modo = entrada.nextInt();
      switch(modo){
        case 1 :
          System.out.print("Escriba la ruta del fichero de entrada: ");
          ent = entrada.next();
          in = new File(ent);
          System.out.print("\nEscriba la ruta del fichero de salida:");
          sal = salida.next();
          out = new File(sal);
          fileEncript(in,out);
          break;
        case 2 :
          System.out.print("Escriba la ruta del fichero de entrada: ");
          ent = entrada.next();
          in = new File(ent);
          System.out.print("\nEscriba la ruta del fichero de salida:");
          sal = salida.next();
          out = new File(sal);
          fileDescript(in,out);
          break;
        case 3 :
          rutina = false;
      }
    }
  }
}
