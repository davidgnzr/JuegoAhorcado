import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PartidaAhorcado implements Runnable {

	private Socket sjug1=null;
	private Socket sjug2=null;
	private String nombreJugador;
	private int puntos;
		
		
	public PartidaAhorcado(Socket sjug1,Socket sjug2) {
		Random r = new Random();
		int rol = r.nextInt(2);  //Se recibe un 0 o un 1 de forma aleatoria para asignar los roles.
		if (rol==0) {
			this.sjug1 = sjug1;
			this.sjug2 = sjug2;
		}else {
			this.sjug1 = sjug2;
			this.sjug2 = sjug1;
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		DataOutputStream dos1;
		DataOutputStream dos2;
		DataInputStream dis1;
		DataInputStream dis2;

		try {
			dos1= new DataOutputStream(this.sjug1.getOutputStream());
			dos2= new DataOutputStream(this.sjug2.getOutputStream());
			dis1 =  new DataInputStream(this.sjug1.getInputStream());
			dis2 =  new DataInputStream(this.sjug2.getInputStream());
			
			String adivinar="Adivinar\r\n";
			String elegir="Elegir\r\n";
			dos1.writeBytes(adivinar);
			dos2.writeBytes(elegir);
			
			nombreJugador=dis1.readLine();
						
			String palabra=dis2.readLine();
			String palabraEncrip=dis2.readLine();
			dos1.writeBytes(palabraEncrip+"\r\n");
			boolean finalizado=false;
			int intentos=7;
			puntos=105;
			int fallosSeguidos=0;
			int aciertosSeguidos=0;
			String acertado="";
			while(!finalizado) {
				
				String letra=dis1.readLine();
				dos2.writeBytes(letra+"\r\n");
				acertado=dis2.readLine();
				
				if(acertado.equalsIgnoreCase("NO")) {
					intentos--;
					aciertosSeguidos=0;
					fallosSeguidos++;
					puntos=puntos-(fallosSeguidos*10);
					
					if(intentos==0) {
						finalizado=true;
						puntos=0;
						dos2.writeBytes("ENDM"+"\r\n");
						dos1.writeBytes("ENDM"+"\r\n");
						dos2.writeBytes("Enhorabuena!! Has ganado."+"\r\n");
						dos1.writeBytes(palabra+"\r\n");
						dos1.writeBytes("Has perdido, no has conseguido adivinar la palabra."+"\r\n");
						dos2.writeInt(puntos);
						dos1.writeInt(puntos);
					}else {
						palabraEncrip=dis2.readLine();
						dos1.writeBytes(palabraEncrip+"\r\n");
						dos2.writeBytes("OK"+"\r\n");
					}
				}else {
					fallosSeguidos=0;
					aciertosSeguidos++;
					puntos=puntos+(aciertosSeguidos*10);					
					palabraEncrip=dis2.readLine();
					int lRestantes= palabraEncrip.indexOf("_");
					if (lRestantes==-1) {
						finalizado=true;
						dos2.writeBytes("ENDV"+"\r\n");
						dos1.writeBytes("ENDV"+"\r\n");
						dos2.writeBytes("Has perdido, el otro jugador ha acertado la palabra."+"\r\n");
						dos1.writeBytes(palabraEncrip+"\r\n");
						dos1.writeBytes("Enhorabuena!! Has ganado."+"\r\n");
						if(puntos<=0) {
							puntos=5;
						}
						dos2.writeInt(puntos);
						dos1.writeInt(puntos);
						
						
					}else {						
						dos1.writeBytes(palabraEncrip+"\r\n");
						dos2.writeBytes("OK"+"\r\n");
					}
				}
			}
			
			if(nombreJugador.equals("anonimo")==false) {
				arbolDom(dos1);
			}else {
				dos1.writeBytes("Tu m�xima puntuaci�n es: "+puntos +"\r\n");
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void arbolDom(DataOutputStream dos) {
		//Segunda parte del ahorcado para guardar los puntos obtenidos por el jugador
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
				Document doc = db.parse(new File("AhorcadoTrabajoDistribuidos/src/TablaPuntos.xml"));
				Element root= doc.getDocumentElement();//Para llegar a la raiz del xml. (Principal)
				NodeList hijos = root.getElementsByTagName("jugador");
				boolean encontrado=false;
				int i =0;
				
				System.out.println(hijos.getLength());
				while((i<hijos.getLength()) && (!encontrado)) {//El getChildNodes devuelve todo, hasta los saltos de linea. Por eso hay que coger solo los que son elementos.
						System.out.println(hijos.item(i).toString());
						NamedNodeMap atributos= hijos.item(i).getAttributes();//Para sacar lo satributos del Jugador (nombre)
						String nombre=new String(atributos.item(0).getNodeValue());//Nombre
						System.out.println(nombre.toString());
						if (nombre.equals(nombreJugador) ){
							encontrado=true;
							int maxPuntos=puntos;
							Element puntosN =doc.createElement ("puntos");
							String p=String.valueOf(puntos);
							puntosN.setTextContent(p);
							hijos.item(i).appendChild(puntosN);
							System.out.println("He a�adido los puntos "+puntos+" a: "+ nombre);
							
							NodeList puntos=((Element) hijos.item(i)).getElementsByTagName("puntos");//Sacamos los puntos del jugador.
							int punt=0;
							for(int ii=0;ii<puntos.getLength();ii++) {
								punt=Integer.parseInt(puntos.item(ii).getTextContent());
								System.out.println(punt);
								if(maxPuntos<punt) {
									maxPuntos=punt;
								}
							}							
							dos.writeBytes("Tu m�xima puntuaci�n es: "+maxPuntos +"\r\n");
							System.out.println("Tu m�xima puntuaci�n es: "+maxPuntos +"\r\n");
						}
						i++;
				}
				System.out.println("He salido del bucle");

				if(encontrado==false) {
					Element Jugador = doc.createElement ("jugador");
					Jugador.setAttribute("name", nombreJugador);
					Element puntosN = doc.createElement ("puntos");
					String p=String.valueOf(puntos);
					puntosN.setTextContent(p);
					Jugador.appendChild(puntosN);
					root.appendChild(Jugador);
					System.out.println("He creado un jugador nuevo");
					dos.writeBytes("Tu m�xima puntuaci�n ha sido: "+puntos +"\r\n");
				}
								
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				DOMSource source = new DOMSource (doc);
				StreamResult result = new StreamResult(new File("AhorcadoTrabajoDistribuidos/src/TablaPuntos.xml"));
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");//Esta linea y la siguiente para que no se cree todo en una linea.
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				transformer.transform(source, result);
				
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
