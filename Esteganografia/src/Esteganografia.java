import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Esteganografia {

    public static void main(String[] args) throws Exception {
        String codigoAtividade1 = "public class GeradorHash {\n" +
        "    public static void main(String[] args) {\n" +
        "        String caminhoArquivo = \"C:\\\\Users\\\\fernando.psousa\\\\Desktop\\\\Hashing\\\\Hashing.txt\";\n" +
        "        System.out.println(\"MD5: \" + gerarHash(caminhoArquivo, \"MD5\"));\n" +
        "        System.out.println(\"SHA-256: \" + gerarHash(caminhoArquivo, \"SHA-256\"));\n" +
        "    }\n" +
        "    private static String gerarHash(String caminhoArquivo, String algoritmo) {\n" +
        "        try {\n" +
        "            MessageDigest digest = MessageDigest.getInstance(algoritmo);\n" +
        "            digest.update(Files.readAllBytes(Paths.get(caminhoArquivo)));\n" +
        "            byte[] hash = digest.digest();\n" +
        "            return Base64.getEncoder().encodeToString(hash);\n" +
        "        } catch (Exception e) {\n" +
        "            throw new RuntimeException(\"Não foi possível gerar o hash\", e);\n" +
        "        }\n" +
        "    }\n" +
        "}";

        String mensagemCodificada = Base64.getEncoder().encodeToString(codigoAtividade1.getBytes(StandardCharsets.UTF_8));

        File arquivoImagemOriginal = new File("C:\\Users\\USER\\Desktop\\TrabalhoSegurança\\Esteganografia\\bin\\bloodborne.jpg");
        BufferedImage imagem = ImageIO.read(arquivoImagemOriginal);

        BufferedImage novaImagem = ocultarMensagem(imagem, mensagemCodificada);

        ImageIO.write(novaImagem, "png", new File("C:\\Users\\USER\\Desktop\\TrabalhoSegurança\\Esteganografia\\bin\\imagemComCodigoOculto.png"));
    }

    private static BufferedImage ocultarMensagem(BufferedImage imagem, String mensagem) {
        int tamanhoMensagem = mensagem.length();
        int larguraImagem = imagem.getWidth();
        int alturaImagem = imagem.getHeight();
        
        int[] pixels = imagem.getRGB(0, 0, larguraImagem, alturaImagem, null, 0, larguraImagem);
        
        // Vamos ocultar cada caractere da mensagem nos 8 bits menos significativos do pixel
        for (int i = 0; i < tamanhoMensagem; i++) {
            char c = mensagem.charAt(i);
            pixels[i] = (pixels[i] & 0xFFFFFF00) | c;
        }
        
        // Vamos ocultar o tamanho da mensagem no primeiro pixel
        pixels[0] = (pixels[0] & 0xFFFFFF00) | tamanhoMensagem;
        
        BufferedImage novaImagem = new BufferedImage(larguraImagem, alturaImagem, BufferedImage.TYPE_INT_ARGB);
        novaImagem.setRGB(0, 0, larguraImagem, alturaImagem, pixels, 0, larguraImagem);
        
        return novaImagem;
    }
}

