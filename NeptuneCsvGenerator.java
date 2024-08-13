import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class NeptuneCsvGenerator {

    public static void main(String[] args) throws IOException {
        String txtFilePath = "path/to/your/file.txt";
        String insertCsvPath = "path/to/output/insert.csv";

        String content = new String(Files.readAllBytes(Paths.get(txtFilePath)));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = mapper.readTree(content);

        ArrayNode conteudosArray = (ArrayNode) jsonObject.get("conteudos");

        try (CSVWriter insertWriter = new CSVWriter(new FileWriter(insertCsvPath))) {

            // Header for the insert CSV
            insertWriter.writeNext(new String[]{
                "~id", "~label", "data_criacao_conteudo:Date", "data_validade_conteudo:Date",
                "categoria_conteudo:String", "id_arquivo:String", "nome_arquivo:String",
                "descricao_arquivo:String", "parte_arquivo:int", "sha256_arquivo:String",
                "arquivo_certificado:int", "tamanho_arquivo:int", "id_referencia_arquivo_repositorio:String",
                "id_repositorio:int", "id_tipo_conteudo:int", "codigo_metadado:String", "valor_metadado:String",
                "id_processo:String"
            });

            for (JsonNode conteudoObj : conteudosArray) {
                String idConteudo = UUID.randomUUID().toString();
                String dataCriacaoConteudo = conteudoObj.get("data_criacao_conteudo").asText();
                String dataValidadeConteudo = conteudoObj.get("data_validade_conteudo").asText();
                String categoriaConteudo = conteudoObj.get("categoria_conteudo").asText();

                ArrayNode arquivosArray = (ArrayNode) conteudoObj.get("arquivos");
                for (JsonNode arquivoObj : arquivosArray) {
                    String idArquivo = arquivoObj.get("id_arquivo").asText();
                    String nomeArquivo = arquivoObj.get("nome_arquivo").asText();
                    String descricaoArquivo = arquivoObj.get("descricao_arquivo").asText();
                    String parteArquivo = String.valueOf(arquivoObj.get("parte_arquivo").asInt());
                    String sha256Arquivo = arquivoObj.get("sha256_arquivo").asText();
                    String arquivoCertificado = String.valueOf(arquivoObj.get("arquivo_certificado").asInt());
                    String tamanhoArquivo = String.valueOf(arquivoObj.get("tamanho_arquivo").asInt());

                    ArrayNode referenciasArray = (ArrayNode) arquivoObj.get("referencia_arquivos");
                    for (JsonNode referenciaObj : referenciasArray) {
                        String idReferenciaArquivoRepositorio = referenciaObj.get("id_referencia_arquivo_repositorio").asText();
                        String idRepositorio = String.valueOf(referenciaObj.get("id_repositorio").asInt());

                        ArrayNode metadadosArray = (ArrayNode) conteudoObj.get("metadados");
                        for (JsonNode metadadoObj : metadadosArray) {
                            String codigoMetadado = metadadoObj.get("codigo_metadado").asText();
                            String valorMetadado = metadadoObj.get("valor_metadado").asText();

                            String idProcesso = conteudoObj.get("id_processo").asText();

                            // Write to insert CSV
                            insertWriter.writeNext(new String[]{
                                idConteudo, "conteudo", dataCriacaoConteudo, dataValidadeConteudo,
                                categoriaConteudo, idArquivo, nomeArquivo, descricaoArquivo, parteArquivo,
                                sha256Arquivo, arquivoCertificado, tamanhoArquivo, idReferenciaArquivoRepositorio,
                                idRepositorio, "35", codigoMetadado, valorMetadado, idProcesso
                            });
                        }
                    }
                }
            }
        }
    }
}
