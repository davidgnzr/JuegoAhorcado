import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
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
		DataOutputStream dos1 = null;
		DataOutputStream dos2 = null;
		DataInputStream dis1 = null;
		DataInputStream dis2 = null;

		try {
			dos1= new DataOutputStream(this.sjug1.getOutputStream());
			dos2= new DataOutputStream(this.sjug2.getOutputStream());
			dis1 =  new DataInputStream(this.sjug1.getInputStream());
			dis2 =  new DataInputStream(this.sjug2.getInputStream());
			
			String adivinar="Adivinar\r\n";
			String elegir="Elegir\r\n";
			dos1.writeBytes(adivinar);
			dos2.writeBytes(elegir);//El servidor les manda sus roles.
			
			nombreJugador=dis1.readLine();
						
			String palabra=dis2.readLine();//Recibimos la palabra encriptada y sin encriptar.
			String palabraEncrip=dis2.readLine();
			dos1.writeBytes(palabraEncrip+"\r\n");
			boolean finalizado=false;
			int intentos=7;
			puntos=105;//Puntos iniciales.
			int fallosSeguidos=0;
			int aciertosSeguidos=0;
			String acertado="";
			while(!finalizado) {
				
				String letra=dis1.readLine();//Leemos letra del jugador.
				dos2.writeBytes(letra+"\r\n");
				acertado=dis2.readLine();//Obtenemos si es está.
				
				if(acertado.equalsIgnoreCase("NO")) {//Si la letra no esta contenida.
					intentos--;
					aciertosSeguidos=0;
					fallosSeguidos++;
					puntos=puntos-(fallosSeguidos*10);
					if(intentos==0) {//Si es el último intento terminamos la partida.
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
						dos1.writeBytes(palabraEncrip+"\r\n");//Se manda la palabra encriptada recibida, que será la misma que antes.
						dos2.writeBytes("OK"+"\r\n");
					}
				}else {//Si la letra está contenida.
					fallosSeguidos=0;
					aciertosSeguidos++;
					puntos=puntos+(aciertosSeguidos*10);					
					palabraEncrip=dis2.readLine();
					int lRestantes= palabraEncrip.indexOf("_");
					if (lRestantes==-1) {//Si la palabra ya se ha adivinado, se termina la partida.
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
						
						
					}else {//Si no se ha adivinado, se manda la nueva palabra encriptada con la letra visible.				
						dos1.writeBytes(palabraEncrip+"\r\n");
						dos2.writeBytes("OK"+"\r\n");
					}
				}
			}
			
			if(nombreJugador.equals("anonimo")==false) {//Si no es anonimo, se le guarda en la tabla de puntos.
				arbolDom(dos1);
			}else {
				dos1.writeBytes("Tu máxima puntuación es: "+puntos +"\r\n");
			}
			
		}catch (SocketException e) {
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			//Cerramos todo en el finally para que se ejecute siempre.
			if(dos1!=null) {
				try {dos1.close();}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(dis1!=null) {
				try {dis1.close();}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(dos2!=null) {
				try {dos2.close();}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(dis2!=null) {
				try {dis2.close();}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public void arbolDom(DataOutputStream dos) {
		//Segunda parte del ahorcado para guardar los puntos obtenidos por el jugador
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
				Document doc = db.parse(new File("src\\TablaPuntos.xml"));
				Element root= doc.getDocumentElement();//Para llegar a la raiz del xml. (Principal)
				NodeList hijos = root.getElementsByTagName("jugador");
				boolean encontrado=false;
				int i =0;
				
				while((i<hijos.getLength()) && (!encontrado)) {
						NamedNodeMap atributos= hijos.item(i).getAttributes();//Para sacar los atributos del Jugador (nombre).
						String nombre=new String(atributos.item(0).getNodeValue());//Nombre
						if (nombre.equals(nombreJugador) ){
							encontrado=true;
							int maxPuntos=puntos;
							Element puntosN =doc.createElement ("puntos");
							String p=String.valueOf(puntos);
							puntosN.setTextContent(p);
							hijos.item(i).appendChild(puntosN);
							
							NodeList puntos=((Element) hijos.item(i)).getElementsByTagName("puntos");
							//Sacamos los puntos del jugador, para ver su máxima puntuación..
							int punt=0;
							for(int ii=0;ii<puntos.getLength();ii++) {
								punt=Integer.parseInt(puntos.item(ii).getTextContent());
								if(maxPuntos<punt) {
									maxPuntos=punt;
								}
							}							
							dos.writeBytes("Tu máxima puntuación es: "+maxPuntos +"\r\n");
						}
						i++;
				}

				if(encontrado==false) {//Si el jugador no se encuentra en la tabla de puntos, se crea.
					Element Jugador = doc.createElement ("jugador");
					Jugador.setAttribute("name", nombreJugador);
					Element puntosN = doc.createElement ("puntos");
					String p=String.valueOf(puntos);
					puntosN.setTextContent(p);
					Jugador.appendChild(puntosN);
					root.appendChild(Jugador);
					dos.writeBytes("Tu máxima puntuación ha sido: "+puntos +"\r\n");
				}
				
				System.out.println("Se han añadido "+puntos+" puntos a: "+ nombreJugador);
								
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				DOMSource source = new DOMSource (doc);
				StreamResult result = new StreamResult(new File("src\\TablaPuntos.xml"));
				//transformer.setOutputProperty(OutputKeys.INDENT, "yes");//Esta linea y la siguiente para que no se cree todo en una linea.
				//transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				transformer.transform(source, result);
				
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
