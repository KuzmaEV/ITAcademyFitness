package by.mk_jd2_92_22.reportService.minio;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class MinioService {


    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {

        this.minioClient = minioClient;
    }

    @Value("${minio.bucket.name}")
    private String bucketName;

    public void put(String objectName, byte[] report) {
        try (InputStream inputStream = new ByteArrayInputStream(report)){

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, inputStream.available(), -1).build());


        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Не удалось сахранить обработанный отчет: " + e.getMessage());
        }
    }

    public byte[] get(String objectName) {
        try (InputStream stream = minioClient
                .getObject(GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build())) {
            return stream.readAllBytes();
        } catch (ErrorResponseException | InsufficientDataException |
                InternalException | InvalidKeyException | InvalidResponseException |
                IOException | NoSuchAlgorithmException | ServerException |
                XmlParserException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Не удалось получить файл из minio: " + e.getMessage());
        }
    }
}
