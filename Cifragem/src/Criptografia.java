import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Criptografia {

    public static void main(String[] args) throws Exception {
        String texto = "Criptografia é a prática de proteger informações por meio do uso de algoritmos codificados, hashes e assinaturas. "+
        "As informações podem estar em repouso (como um arquivo em um disco rígido), em trânsito (como comunicação eletrônica trocada entre duas ou mais partes) "+ 
        "ou em uso (durante a computação de dados).";

        // Cifragem AES
        SecretKey chaveAes = gerarChave("AES");
        String textoCifradoAes = cifrarTexto(texto, chaveAes, "AES");
        String textoDecifradoAes = decifrarTexto(textoCifradoAes, chaveAes, "AES");

        // Cifragem Blowfish
        SecretKey chaveBlowfish = gerarChave("Blowfish");
        String textoCifradoBlowfish = cifrarTexto(texto, chaveBlowfish, "Blowfish");
        String textoDecifradoBlowfish = decifrarTexto(textoCifradoBlowfish, chaveBlowfish, "Blowfish");

        System.out.println("\nTexto cifrado AES: " + textoCifradoAes);
        System.out.println("\nTexto decifrado AES: " + textoDecifradoAes);
        System.out.println("\nTexto cifrado Blowfish: " + textoCifradoBlowfish);
        System.out.println("\nTexto decifrado Blowfish: " + textoDecifradoBlowfish);
    }

    private static SecretKey gerarChave(String algoritmo) throws Exception {
        KeyGenerator geradorChave = KeyGenerator.getInstance(algoritmo);
        geradorChave.init(128);
        return geradorChave.generateKey();
    }

    private static String cifrarTexto(String texto, SecretKey chave, String algoritmo) throws Exception {
        Cipher cifra = Cipher.getInstance(algoritmo);
        cifra.init(Cipher.ENCRYPT_MODE, chave);
        byte[] cifrado = cifra.doFinal(texto.getBytes());
        return Base64.getEncoder().encodeToString(cifrado);
    }

    private static String decifrarTexto(String textoCifrado, SecretKey chave, String algoritmo) throws Exception {
        Cipher cifra = Cipher.getInstance(algoritmo);
        cifra.init(Cipher.DECRYPT_MODE, chave);
        byte[] original = cifra.doFinal(Base64.getDecoder().decode(textoCifrado));
        return new String(original);
    }
}

