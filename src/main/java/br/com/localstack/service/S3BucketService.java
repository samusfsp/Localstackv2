package br.com.localstack.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;


@Service
public class S3BucketService {

    private final S3Client s3Client;

    public S3BucketService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // Método para criar um novo bucket no Amazon S3
    public void createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        s3Client.createBucket(createBucketRequest);
    }

    // Método para listar todos os buckets no Amazon S3
    public List<Bucket> listBuckets() {
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets();
        return listBucketsResponse.buckets();
    }

    // Método para enviar um objeto para o Amazon S3
    public void uploadObject(String bucketName, String key, byte[] fileBytes) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes));
    }




    // Método para listar objetos em um bucket
    public List<S3Object> listObjects(String bucketName) {
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
        return listObjectsResponse.contents();
    }

    // Método para recuperar um objeto do Amazon S3
    public String getObject(String bucketName, String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try {
            ResponseBytes<GetObjectResponse> getObjectResponseBytes = s3Client.getObjectAsBytes(getObjectRequest);
            byte[] contentBytes = getObjectResponseBytes.asByteArray();
            return new String(contentBytes);
        } catch (S3Exception e) {
            // Lida com erros do S3, se necessário
            e.printStackTrace();
            return null; // ou outra forma de indicar um erro
        }
    }


    // Método para excluir um objeto no Amazon S3
    public void deleteObject(String bucketName, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}