import java.security.MessageDigest;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class GeradorHash {

    public static void main(String[] args) {
        String caminhoArquivo = "C:\\Users\\fernando.psousa\\Desktop\\Hashing\\Hashing.txt";
        System.out.println("MD5: " + gerarHash(caminhoArquivo, "MD5"));
        System.out.println("SHA-256: " + gerarHash(caminhoArquivo, "SHA-256"));
    }

    private static String gerarHash(String caminhoArquivo, String algoritmo) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algoritmo);
            digest.update(Files.readAllBytes(Paths.get(caminhoArquivo)));
            byte[] hash = digest.digest();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível gerar o hash", e);
        }
    }
}


